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

import com.liferay.talend.client.BasicAuthenticationClient;
import com.liferay.talend.datastore.AuthenticationMethod;
import com.liferay.talend.datastore.BasicDataStore;
import com.liferay.talend.datastore.InputDataStore;
import com.liferay.talend.datastore.OAuthDataStore;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.parser.OpenAPIV3Parser;

import java.net.URL;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.completion.SuggestionValues;
import org.talend.sdk.component.api.service.completion.Suggestions;
import org.talend.sdk.component.api.service.healthcheck.HealthCheck;
import org.talend.sdk.component.api.service.healthcheck.HealthCheckStatus;
import org.talend.sdk.component.api.service.http.Response;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 */
@Service
public class UIActionService {

	public HealthCheckStatus checkBasicDataStore(
		@Option InputDataStore inputDataStore,
		BasicAuthenticationClient basicAuthenticationClient) {

		BasicDataStore basicDataStore = inputDataStore.getBasicDataStore();

		if (_isNull(basicDataStore.getUser()) ||
			_isNull(basicDataStore.getPassword())) {

			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Username and Password are required");
		}

		URL serverURL = inputDataStore.getServerURL();

		if ((serverURL == null) || _isNull(serverURL.toString())) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO, "Server URL is required");
		}

		basicAuthenticationClient.base(serverURL.toString());

		Base64.Encoder base64Encoder = Base64.getEncoder();

		StringBuilder sb = new StringBuilder();

		sb.append(basicDataStore.getUser());
		sb.append(":");
		sb.append(basicDataStore.getPassword());

		String base64Seed = sb.toString();

		byte[] encode = base64Encoder.encode(base64Seed.getBytes());

		sb.setLength(0);

		sb.append("Basic ");
		sb.append(base64Encoder.encodeToString(encode));

		Response<JsonObject> jsonObjectResponse =
			basicAuthenticationClient.checkRequest(sb.toString(), "*/*");

		if (jsonObjectResponse.status() != 200) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Connection failed, received http response status " +
					jsonObjectResponse.status());
		}

		return new HealthCheckStatus(
			HealthCheckStatus.Status.OK, "Connection success");
	}

	@HealthCheck("checkInputDataStore")
	public HealthCheckStatus checkInputDataStore(
		@Option InputDataStore inputDataStore,
		final BasicAuthenticationClient basicAuthenticationClient) {

		if (AuthenticationMethod.BASIC ==
				inputDataStore.getAuthenticationMethod()) {

			return checkBasicDataStore(
				inputDataStore, basicAuthenticationClient);
		}

		return checkOAuthDataStore(inputDataStore);
	}

	public HealthCheckStatus checkOAuthDataStore(
		@Option InputDataStore inputDataStore) {

		OAuthDataStore oAuthDataStore = inputDataStore.getoAuthDataStore();

		if (_isNull(oAuthDataStore.getConsumerKey()) ||
			_isNull(oAuthDataStore.getConsumerSecret())) {

			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO,
				"Consumer key and secret are required");
		}

		if (inputDataStore.getServerURL() == null) {
			return new HealthCheckStatus(
				HealthCheckStatus.Status.KO, "Server URL is required");
		}

		// TODO perform connection check

		return new HealthCheckStatus(
			HealthCheckStatus.Status.OK, "Connection success");
	}

	@Suggestions("fetchEndpoints")
	public SuggestionValues fetchEndpoints(
		@Option("inputDataStore") final InputDataStore inputDataStore) {

		List<SuggestionValues.Item> items = new ArrayList<>();

		Map<String, String> endpoints = _getEndpointsMap(
			"com/liferay/talend/resource/rest-openapi.yaml");

		endpoints.forEach(
			(path, returnSchemaType) -> {
				items.add(new SuggestionValues.Item(path, returnSchemaType));
			});

		return new SuggestionValues(true, items);
	}

	private Map<String, String> _getEndpointsMap(String location) {
		OpenAPI openAPI = new OpenAPIV3Parser().read(location);

		Paths paths = openAPI.getPaths();

		return paths.entrySet(
		).stream(
		).filter(
			this::_isArrayTypePredicate
		).collect(
			Collectors.toMap(Map.Entry::getKey, this::_mapToArrayItemReferences)
		);
	}

	private Schema _getSchema(Map.Entry<String, PathItem> pathItemEntry) {
		PathItem pathItem = pathItemEntry.getValue();

		Operation getOperation = pathItem.getGet();

		ApiResponses apiResponses = getOperation.getResponses();

		ApiResponse apiResponse200Ok = apiResponses.get("200");

		Content content200OK = apiResponse200Ok.getContent();

		MediaType mediaType = content200OK.get("application/json");

		return mediaType.getSchema();
	}

	private boolean _isArrayTypePredicate(
		Map.Entry<String, PathItem> pathItemEntry) {

		Schema schema = _getSchema(pathItemEntry);

		if ("array".equals(schema.getType())) {
			return true;
		}

		return false;
	}

	private boolean _isNull(String value) {
		if ((value == null) || value.isEmpty()) {
			return true;
		}

		value = value.trim();

		if (value.isEmpty()) {
			return true;
		}

		return false;
	}

	private String _mapToArrayItemReferences(
		Map.Entry<String, PathItem> stringPathItemEntry) {

		Schema schema = _getSchema(stringPathItemEntry);

		Schema<?> items = ((ArraySchema)schema).getItems();

		return items.get$ref();
	}

}