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

import com.liferay.talend.data.store.AuthenticationMethod;
import com.liferay.talend.data.store.BaseDataStore;
import com.liferay.talend.data.store.BasicAuthDataStore;
import com.liferay.talend.data.store.OAuth2DataStore;
import com.liferay.talend.dataset.InputDataSet;
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
		@Option BaseDataStore baseDataStore) {

		BasicAuthDataStore basicAuthDataStore =
			baseDataStore.getBasicDataStore();

		if (!basicAuthDataStore.isAnonymous() &&
			(isNull(basicAuthDataStore.getUser()) ||
			 isNull(basicAuthDataStore.getPassword()))) {

			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Username and password are required");
		}

		InputDataSet inputDataSet = new InputDataSet();

		inputDataSet.setEndpoint("/c/portal/login");
		inputDataSet.setInputDataStore(baseDataStore);

		try {
			_connectionService.getResponseRawString(inputDataSet);
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

	public HealthCheckStatus checkInputDataStore(BaseDataStore baseDataStore) {
		if (baseDataStore.getOpenAPISpecURL() == null) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"OpenAPI Specification URL is required");
		}

		if (baseDataStore.getAuthenticationMethod() ==
				AuthenticationMethod.OAUTH2) {

			return checkOAuthDataStore(baseDataStore);
		}

		return checkBasicDataStore(baseDataStore);
	}

	public HealthCheckStatus checkOAuthDataStore(BaseDataStore baseDataStore) {
		OAuth2DataStore oAuth2DataStore = baseDataStore.getoAuthDataStore();

		if (isNull(oAuth2DataStore.getConsumerKey()) ||
			isNull(oAuth2DataStore.getConsumerSecret())) {

			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Consumer key and secret are required");
		}

		try {
			_connectionService.requestAuthorizationJsonObject(baseDataStore);
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