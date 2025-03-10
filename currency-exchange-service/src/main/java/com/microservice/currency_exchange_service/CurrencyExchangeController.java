package com.microservice.currency_exchange_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
    @Autowired
    private CurrencyExchangeRepository repository;

    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeValue(@PathVariable String from,
                                                 @PathVariable String to){
        //  INFO [currency-exchange,a09cd69a970594eb8252300e1a3b21b6,34272a9b36a8a0e2]  11108
        logger.info("retriveExchangeValue called with {} to {}",from,to);
        //CurrencyExchange currencyExchange=new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
        CurrencyExchange currencyExchange= repository.findByFromAndTo(from, to);
        if(currencyExchange==null){
            throw new  RuntimeException("Unable to find data for" + from + "to" + to);
        }
        String port=environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
