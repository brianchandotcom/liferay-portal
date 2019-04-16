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
import com.liferay.talend.dataset.InputDataSet;
import com.liferay.talend.http.client.LiferayHttpClient;
import com.liferay.talend.http.client.exception.ConnectionException;
import com.liferay.talend.http.client.exception.MalformedURLException;
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

	public static URL getServerURL(BaseDataStore baseDataStore) {
		URL openAPISpecURL = baseDataStore.getOpenAPISpecURL();

		String protocol = openAPISpecURL.getProtocol();
		String host = openAPISpecURL.getHost();
		int port = openAPISpecURL.getPort();

		URL serverURL = null;

		try {
			serverURL = new URL(protocol, host, port, "");
		}
		catch (java.net.MalformedURLException murle) {
			throw new MalformedURLException(murle);
		}

		return serverURL;
	}

	public JsonObject getResponseJsonObject(InputDataSet inputDataSet)
		throws ConnectionException {

		BaseDataStore baseDataStore = inputDataSet.getInputDataStore();

		String authorizationHeader = _getAuthorizationHeader(baseDataStore);

		URL serverURL = getServerURL(baseDataStore);

		_liferayHttpClient.base(serverURL.toString());

		Response<JsonObject> jsonObjectResponse =
			_liferayHttpClient.getJsonObjectResponse(
				authorizationHeader, "application/json",
				inputDataSet.getEndpoint());

		_validateResponse(jsonObjectResponse);

		return jsonObjectResponse.body();
	}

	public String getResponseRawString(InputDataSet inputDataSet)
		throws ConnectionException {

		BaseDataStore baseDataStore = inputDataSet.getInputDataStore();

		String authorizationHeader = _getAuthorizationHeader(baseDataStore);

		URL serverURL = getServerURL(baseDataStore);

		_liferayHttpClient.base(serverURL.toString());

		Response<String> response = _liferayHttpClient.getRawStringResponse(
			authorizationHeader, "*/*", inputDataSet.getEndpoint());

		_validateResponse(response);

		return response.body();
	}

	public JsonObject requestAuthorizationJsonObject(
			BaseDataStore baseDataStore)
		throws ConnectionException {

		URL serverURL = getServerURL(baseDataStore);

		_liferayHttpClient.base(serverURL.toString());

		Response<JsonObject> jsonObjectResponse =
			_liferayHttpClient.getAccessToken(
				"application/x-www-form-urlencoded",
				baseDataStore.getoAuthDataStore());

		if (jsonObjectResponse == null) {
			throw new OAuth2Exception(
				"Authorization request failed for unresponsive OAuth 2.0 " +
					"endpoint");
		}

		if (jsonObjectResponse.status() != 200) {
			throw new OAuth2Exception(
				String.format(
					"OAuth 2.0 check failed with response status {%s}",
					jsonObjectResponse.status()));
		}

		if (!_isApplicationJsonContentType(jsonObjectResponse.headers())) {
			List<String> contentTypeValues = _getContentType(
				jsonObjectResponse.headers());

			throw new OAuth2Exception(
				String.format(
					"OAuth 2.0 check failed with response status and {%s} " +
						"content type {%s}",
					jsonObjectResponse.status(), contentTypeValues.get(0)));
		}

		return jsonObjectResponse.body();
	}

	private String _getAuthorizationHeader(BaseDataStore baseDataStore) {
		if (baseDataStore.getAuthenticationMethod() ==
				AuthenticationMethod.OAUTH2) {

			return "Bearer " + _getBearerToken(baseDataStore);
		}

		return "Basic " + _getBasicToken(baseDataStore);
	}

	private String _getBasicToken(BaseDataStore baseDataStore) {
		BasicAuthDataStore basicAuthDataStore =
			baseDataStore.getBasicDataStore();

		Base64.Encoder base64Encoder = Base64.getEncoder();

		StringBuilder sb = new StringBuilder();

		sb.append(basicAuthDataStore.getUser());
		sb.append(":");
		sb.append(basicAuthDataStore.getPassword());

		String base64Seed = sb.toString();

		return new String(base64Encoder.encode(base64Seed.getBytes()));
	}

	private String _getBearerToken(BaseDataStore baseDataStore) {
		JsonObject authorizationJsonObject = requestAuthorizationJsonObject(
			baseDataStore);

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

	private void _validateResponse(Response<?> response) {
		if (response == null) {
			throw new ConnectionException(
				"Data request failed for unresponsive endpoint");
		}

		if (response.status() != 200) {
			throw new ConnectionException(
				"Request failed with response status " + response.status());
		}
	}

	@Service
	private LiferayHttpClient _liferayHttpClient;

}