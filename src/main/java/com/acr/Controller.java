package com.acr;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acr.services.AcrServices;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.LogLevel;

@RestController
public class Controller {
	
	@Resource
	AcrServices services;

	
	@RequestMapping(path ="/pushimage/{client}/{tenant}/{key}/{subscriptionKey}/{rgName}/{acrName}", method = RequestMethod.GET)
	public String welcome(@PathVariable String client, @PathVariable String tenant,@PathVariable String key,@PathVariable String subscriptionKey,@PathVariable String rgName,@PathVariable String acrName) {
		ApplicationTokenCredentials credentials = new ApplicationTokenCredentials(
				client, tenant, key, AzureEnvironment.AZURE);

		Azure azure = Azure.configure().withLogLevel(LogLevel.NONE)
				.authenticate(credentials)
				.withSubscription(subscriptionKey);
		services.runSample(azure, client, key, rgName, acrName);
		
		return "Successfully pushed image to ACR.";
	}

}