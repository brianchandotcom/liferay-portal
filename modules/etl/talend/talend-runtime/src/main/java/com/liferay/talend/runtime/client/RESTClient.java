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

package com.liferay.talend.runtime.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.utils.URIUtils;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.exception.TalendRuntimeException;

/**
 * @author Zoltán Takács
 */
public class RESTClient {

	public RESTClient(LiferayConnectionProperties liferayConnectionProperties) {
		this(
			liferayConnectionProperties.endpoint.getValue(),
			liferayConnectionProperties);
	}

	public RESTClient(
		String endpoint,
		LiferayConnectionProperties liferayConnectionProperties) {

		this(
			endpoint, liferayConnectionProperties.password.getValue(),
			liferayConnectionProperties.userId.getValue(),
			liferayConnectionProperties);
	}

	public Response executeDeleteRequest() {
		WebTarget webTarget = _client.target(getEndpointURI());

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", getEndpoint());
		}

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		return _invokeBuilder(HttpMethod.DELETE, builder);
	}

	public Response executeGetRequest() {
		URI decoratedURI = URIUtils.updateWithQueryParameters(
			getEndpointURI(), _getQueryParametersMap());

		WebTarget webTarget = _client.target(decoratedURI);

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", decoratedURI.toASCIIString());
		}

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		return _invokeBuilder(HttpMethod.GET, builder);
	}

	public Response executePostRequest(JsonNode jsonNode) {
		WebTarget webTarget = _client.target(getEndpointURI());

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", getEndpoint());
		}

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		Entity<String> entity = Entity.json(_jsonNodeToPrettyString(jsonNode));

		return _invokeBuilder(HttpMethod.POST, builder, entity);
	}

	public Response executePutRequest(JsonNode jsonNode) {
		WebTarget webTarget = _client.target(getEndpointURI());

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", getEndpoint());
		}

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		Entity<String> entity = Entity.json(_jsonNodeToPrettyString(jsonNode));

		return _invokeBuilder(HttpMethod.PUT, builder, entity);
	}

	public String getEndpoint() {
		boolean forceHttps = _liferayConnectionProperties.forceHttps.getValue();

		if (forceHttps) {
			return _replaceHttpSchemeWithHttps(_endpoint);
		}

		return _endpoint;
	}

	public URI getEndpointURI() {
		try {
			return new URI(getEndpoint());
		}
		catch (URISyntaxException urise) {
			_log.error("Unable to parse {} as a URI reference", getEndpoint());
		}

		return null;
	}

	@Override
	public String toString() {
		return String.format("REST API Client [%s].", getEndpoint());
	}

	protected static final String HTTP = "http://";

	protected static final String HTTPS = "https://";

	protected final ObjectMapper objectMapper = new ObjectMapper();

	private RESTClient(
		String endpoint, String password, String userId,
		LiferayConnectionProperties liferayConnectionProperties) {

		_endpoint = endpoint;
		_password = password;
		_userId = userId;
		_liferayConnectionProperties = liferayConnectionProperties;

		_client = _getClient();
	}

	private Response _follow3Redirects(Response currentResponse) {
		Response.StatusType statusType = currentResponse.getStatusInfo();

		if (statusType.getFamily() != Response.Status.Family.REDIRECTION) {
			return currentResponse;
		}

		AtomicInteger counter = new AtomicInteger();
		Response response = currentResponse;

		while ((statusType.getFamily() == Response.Status.Family.REDIRECTION) &&
			   (counter.incrementAndGet() <= 3)) {

			String location = response.getHeaderString(HttpHeaders.LOCATION);

			if (StringUtils.isEmpty(location)) {
				return response;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Redirect location {}#: {}", counter.get(), location);
			}

			response.close();

			WebTarget webTarget = _client.target(location);

			Invocation.Builder builder = webTarget.request(
				MediaType.APPLICATION_JSON_TYPE);

			response = builder.get();
		}

		return response;
	}

	private Client _getClient() {
		return ClientBuilder.newClient(_getClientConfig());
	}

	private ClientConfig _getClientConfig() {
		ClientConfig clientConfig = _setCredentials(_userId, _password);

		clientConfig = clientConfig.property(
			ClientProperties.CONNECT_TIMEOUT,
			_liferayConnectionProperties.connectTimeout.getValue() * 1000);

		clientConfig = clientConfig.property(
			ClientProperties.READ_TIMEOUT,
			_liferayConnectionProperties.readTimeout.getValue() * 1000);

		return clientConfig;
	}

	private Map<String, String> _getQueryParametersMap() {
		Map<String, String> parameters = new HashMap<>();

		parameters.put(
			"pageSize",
			_liferayConnectionProperties.itemsPerPage.getStringValue());

		return parameters;
	}

	private Response _handleResponse(
		String httpMethod, Invocation.Builder builder, Entity<String> entity) {

		boolean followRedirects =
			_liferayConnectionProperties.followRedirects.getValue();
		Response response;

		if (entity == null) {
			if (followRedirects) {
				response = _follow3Redirects(builder.method(httpMethod));
			}
			else {
				response = builder.method(httpMethod);
			}
		}
		else {
			if (followRedirects) {
				response = _follow3Redirects(
					builder.method(httpMethod, entity));
			}
			else {
				response = builder.method(httpMethod, entity);
			}
		}

		return response;
	}

	private Response _invokeBuilder(
		String httpMethod, Invocation.Builder builder) {

		return _invokeBuilder(httpMethod, builder, null);
	}

	private Response _invokeBuilder(
		String httpMethod, Invocation.Builder builder, Entity<String> entity) {

		Response response = _handleResponse(httpMethod, builder, entity);

		String messageEntity = response.readEntity(String.class);
		int statusCode = response.getStatus();

		Response.StatusType statusType = response.getStatusInfo();

		if (statusType.getFamily() == Response.Status.Family.SUCCESSFUL) {
			return response;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"{} request failed: {}. \n{}", httpMethod, statusCode,
				messageEntity);
		}

		throw TalendRuntimeException.createUnexpectedException(
			"HTTP Code: " + statusCode + "\nRequest failed: \n" +
				messageEntity);
	}

	private String _jsonNodeToPrettyString(JsonNode jsonNode) {
		String json;

		try {
			ObjectWriter objectWriter =
				objectMapper.writerWithDefaultPrettyPrinter();

			json = objectWriter.writeValueAsString(jsonNode);
		}
		catch (JsonProcessingException jpe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to convert JsonNode to a String representation");
			}

			throw TalendRuntimeException.createUnexpectedException(jpe);
		}

		return json;
	}

	private String _replaceHttpSchemeWithHttps(String url) {
		String lowerCasedUrl = StringUtils.lowerCase(url);

		if (lowerCasedUrl.startsWith(HTTP)) {
			return HTTPS.concat(url.substring(HTTP.length()));
		}

		return url;
	}

	private ClientConfig _setCredentials(String userId, String password) {
		HttpAuthenticationFeature httpAuthenticationFeature =
			HttpAuthenticationFeature.basic(userId, password);

		ClientConfig clientConfig = new ClientConfig();

		clientConfig.register(httpAuthenticationFeature);

		return clientConfig;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		RESTClient.class);

	private final Client _client;
	private final String _endpoint;
	private final LiferayConnectionProperties _liferayConnectionProperties;
	private final String _password;
	private final String _userId;

}