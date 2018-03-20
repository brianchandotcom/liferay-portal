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
import com.liferay.talend.runtime.apio.ApioException;
import com.liferay.talend.runtime.apio.ApioResult;
import com.liferay.talend.runtime.apio.constants.JSONLDConstants;

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
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 */
public class RESTClient {

	/**
	 * A {@link MediaType} constant representing {@value #JSON-LD} media type.
	 */
	public static final MediaType APPLICATION_JSON_LD = new MediaType(
		"application", "ld+json");

	/**
	 * A {@code String} constant representing {@value #JSON-LD} media type.
	 */
	public static final String JSON_LD = "application/ld+json";

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

	public ApioResult executeDeleteRequest() throws ApioException {
		WebTarget webTarget = _client.target(getEndpointURI());

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", getEndpoint());
		}

		Invocation.Builder builder = webTarget.request(APPLICATION_JSON_LD);

		return _invokeBuilder(HttpMethod.DELETE, builder);
	}

	public ApioResult executeGetRequest() throws ApioException {
		URI decoratedURI = _updateWithQueryParameters(
			getEndpointURI(), _getQueryParametersMap());

		WebTarget webTarget = _client.target(decoratedURI);

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", decoratedURI.toASCIIString());
		}

		Invocation.Builder builder = webTarget.request(APPLICATION_JSON_LD);

		return _invokeBuilder(HttpMethod.GET, builder);
	}

	public ApioResult executePostRequest(JsonNode jsonNode)
		throws ApioException {

		WebTarget webTarget = _client.target(getEndpointURI());

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", getEndpoint());
		}

		Invocation.Builder builder = webTarget.request(APPLICATION_JSON_LD);

		Entity<String> entity = Entity.json(_jsonNodeToPrettyString(jsonNode));

		return _invokeBuilder(HttpMethod.POST, builder, entity);
	}

	public ApioResult executePutRequest(JsonNode jsonNode)
		throws ApioException {

		WebTarget webTarget = _client.target(getEndpointURI());

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", getEndpoint());
		}

		Invocation.Builder builder = webTarget.request(APPLICATION_JSON_LD);

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
		StatusType statusType = currentResponse.getStatusInfo();

		if (statusType.getFamily() != Response.Status.Family.REDIRECTION) {
			return currentResponse;
		}

		AtomicInteger counter = new AtomicInteger();
		Response response = currentResponse;

		while ((statusType.getFamily() ==
					Response.Status.Family.REDIRECTION) &&
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

			Invocation.Builder builder = webTarget.request(APPLICATION_JSON_LD);

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
			JSONLDConstants.PER_PAGE,
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

	private ApioResult _invokeBuilder(
			String httpMethod, Invocation.Builder builder)
		throws ApioException {

		return _invokeBuilder(httpMethod, builder, null);
	}

	private ApioResult _invokeBuilder(
			String httpMethod, Invocation.Builder builder,
			Entity<String> entity)
		throws ApioException {

		Response response = _handleResponse(httpMethod, builder, entity);

		String messageEntity = response.readEntity(String.class);
		int statusCode = response.getStatus();
		StatusType statusType = response.getStatusInfo();

		if (statusType.getFamily() == Response.Status.Family.SUCCESSFUL) {
			return new ApioResult(statusCode, messageEntity);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"{} request failed: {}. \n{}", httpMethod, statusCode,
					messageEntity);
			}

			throw new ApioException(
				statusCode, "Request failed: \n" + messageEntity);
		}
	}

	private String _jsonNodeToPrettyString(JsonNode jsonNode)
		throws ApioException {

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

			throw new ApioException(jpe);
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

	private URI _updateWithQueryParameters(
		URI uri, Map<String, String> queryParameters) {

		for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
			UriBuilder uriBuilder = UriBuilder.fromUri(uri);

			uri = uriBuilder.replaceQueryParam(
				parameter.getKey(), parameter.getValue()).build();
		}

		return uri;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		RESTClient.class);

	private final Client _client;
	private final String _endpoint;
	private final LiferayConnectionProperties _liferayConnectionProperties;
	private final String _password;
	private final String _userId;

}