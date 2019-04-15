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
import com.liferay.talend.http.client.LiferayHttpClient;
import com.liferay.talend.http.client.exception.ConnectionException;
import com.liferay.talend.http.client.exception.OAuth2Exception;

import java.net.URL;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.http.Response;

/**
 * @author Igor Beslic
 */
@Service
public class ConnectionService {

	public JsonObject getData(RestDataSet restDataSet)
		throws ConnectionException {

		InputDataStore inputDataStore = restDataSet.getInputDataStore();

		String authorizationHeader = _getAuthorizationHeader(inputDataStore);

		URL serverURL = inputDataStore.getServerURL();

		_liferayHttpClient.base(
			serverURL.toString() + restDataSet.getEndpoint());

		Response<JsonObject> jsonObjectResponse = _liferayHttpClient.getData(
			authorizationHeader, "application/json", restDataSet.getEndpoint());

		if (jsonObjectResponse == null) {
			throw new ConnectionException(
				"Data request failed. End point did not respond.");
		}

		if (jsonObjectResponse.status() != 200) {
			throw new ConnectionException(
				"Request failed. Received response status is " +
					jsonObjectResponse.status());
		}

		return jsonObjectResponse.body();
	}

	public JsonObject requestAuthorizationJsonObject(
			InputDataStore inputDataStore)
		throws ConnectionException {

		URL serverURL = inputDataStore.getServerURL();

		_liferayHttpClient.base(serverURL.toString());

		Response<JsonObject> jsonObjectResponse =
			_liferayHttpClient.getAccessToken(
				"application/x-www-form-urlencoded",
				inputDataStore.getoAuthDataStore());

		if (jsonObjectResponse == null) {
			throw new OAuth2Exception(
				"Authorization request failed. OAuth2 end point did not " +
					"respond.");
		}

		if (!_isApplicationJsonContentType(jsonObjectResponse.headers())) {
			List<String> contentTypeValues = _getContentType(
				jsonObjectResponse.headers());

			throw new OAuth2Exception(
				String.format(
					_OAUTH_FAILED_UNSUPPORTED_RESPONSE_CONTENT_TYPE,
					jsonObjectResponse.status(), contentTypeValues.get(0)));
		}

		return jsonObjectResponse.body();
	}

	private String _getAuthorizationHeader(InputDataStore inputDataStore) {
		if (inputDataStore.getAuthenticationMethod() ==
				AuthenticationMethod.OAUTH2) {

			return "Bearer " + _getBearerToken(inputDataStore);
		}

		return "Basic " + _getBasicToken(inputDataStore);
	}

	private String _getBasicToken(InputDataStore inputDataStore) {
		BasicDataStore basicDataStore = inputDataStore.getBasicDataStore();

		Base64.Encoder base64Encoder = Base64.getEncoder();

		StringBuilder sb = new StringBuilder();

		sb.append(basicDataStore.getUser());
		sb.append(":");
		sb.append(basicDataStore.getPassword());

		String base64Seed = sb.toString();

		return base64Encoder.encodeToString(
			base64Encoder.encode(base64Seed.getBytes()));
	}

	private String _getBearerToken(InputDataStore inputDataStore) {
		JsonObject authorizationJsonObject = requestAuthorizationJsonObject(
			inputDataStore);

		String tokenType = authorizationJsonObject.getString("token_type");

		if (!"Bearer".equals(tokenType)) {
			throw new OAuth2Exception(
				"Unexpected token type received " + tokenType);
		}

		return authorizationJsonObject.getString("access_token");
	}

	private List<String> _getContentType(Map<String, List<String>> headers) {
		List<String> headerValues = headers.get("Content-Type");

		if (headerValues == null) {
			return Collections.emptyList();
		}

		return headerValues;
	}

	private boolean _isApplicationJsonContentType(
		Map<String, List<String>> headers) {

		List<String> strings = _getContentType(headers);

		if (strings.contains("application/json")) {
			return true;
		}

		return false;
	}

	private static final String
		_OAUTH_FAILED_UNSUPPORTED_RESPONSE_CONTENT_TYPE =
			"OAuth check failed. Unsupported content type. Response status " +
				"{%s}  Content type {%s}";

	@Service
	private LiferayHttpClient _liferayHttpClient;

}