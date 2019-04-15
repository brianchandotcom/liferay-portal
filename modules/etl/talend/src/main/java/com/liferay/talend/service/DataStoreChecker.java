/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.talend.service;

import com.liferay.talend.dataset.RestDataSet;
import com.liferay.talend.datastore.AuthenticationMethod;
import com.liferay.talend.datastore.BasicDataStore;
import com.liferay.talend.datastore.InputDataStore;
import com.liferay.talend.datastore.OAuthDataStore;
import com.liferay.talend.http.client.exception.ConnectionException;
import com.liferay.talend.http.client.exception.OAuth2Exception;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.healthcheck.HealthCheckStatus;

/**
 * @author Igor Beslic
 */
@Service
public class DataStoreChecker {

	public HealthCheckStatus checkBasicDataStore(
		@Option InputDataStore inputDataStore) {

		BasicDataStore basicDataStore = inputDataStore.getBasicDataStore();

		if (isNull(basicDataStore.getUser()) ||
			isNull(basicDataStore.getPassword())) {

			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Username and password are required");
		}

		RestDataSet restDataSet = new RestDataSet();

		restDataSet.setEndpoint("/c/portal/login");
		restDataSet.setInputDataStore(inputDataStore);

		try {
			_connectionService.getData(restDataSet);
		}
		catch (ConnectionException ce) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Connection failed, received HTTP response status " +
					ce.getMessage());
		}

		return new HealthCheckStatus(
			HealthCheckStatus.Status.OK, "Connection succeeded!");
	}

	public HealthCheckStatus checkInputDataStore(
		InputDataStore inputDataStore) {

		if (inputDataStore.getServerURL() == null) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO, "Server URL is required");
		}

		if (inputDataStore.getAuthenticationMethod() ==
				AuthenticationMethod.OAUTH2) {

			return checkOAuthDataStore(inputDataStore);
		}

		return checkBasicDataStore(inputDataStore);
	}

	public HealthCheckStatus checkOAuthDataStore(
		InputDataStore inputDataStore) {

		OAuthDataStore oAuthDataStore = inputDataStore.getoAuthDataStore();

		if (isNull(oAuthDataStore.getConsumerKey()) ||
			isNull(oAuthDataStore.getConsumerSecret())) {

			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Consumer key and secret are required");
		}

		try {
			_connectionService.requestAuthorizationJsonObject(inputDataStore);
		}
		catch (OAuth2Exception oae) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO, oae.getMessage());
		}

		return new HealthCheckStatus(
			HealthCheckStatus.Status.OK, "Connection succeeded!");
	}

	protected boolean isNull(String value) {
		if ((value == null) || value.isEmpty()) {
			return true;
		}

		value = value.trim();

		if (value.isEmpty()) {
			return true;
		}

		return false;
	}

	@Service
	private ConnectionService _connectionService;

}