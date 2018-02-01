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

import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.runtime.apio.ApioException;
import com.liferay.talend.runtime.apio.ApioResult;
import com.liferay.talend.runtime.apio.jsonld.ApioJsonLDConstants;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
public class RestClient {

	/**
	 * A {@link MediaType} constant representing {@value #JSON-LD} media type.
	 */
	public static final MediaType APPLICATION_JSON_LD = new MediaType(
		"application", "ld+json");

	/**
	 * A {@code String} constant representing {@value #JSON-LD} media type.
	 */
	public static final String JSON_LD = "application/ld+json";

	public RestClient(LiferayConnectionProperties liferayConnectionProperties) {
		this(
			liferayConnectionProperties.endpoint.getValue(),
			liferayConnectionProperties);
	}

	public RestClient(
		String endpoint,
		LiferayConnectionProperties liferayConnectionProperties) {

		this(
			endpoint, liferayConnectionProperties.password.getValue(),
			liferayConnectionProperties.userId.getValue(),
			liferayConnectionProperties);
	}

	public ApioResult executeGetRequest() throws ApioException {
		Client client = getClient();

		URI decoratedURI = _updateWithQueryParameters(
			getEndpointURI(), _getQueryParametersMap());

		WebTarget webTarget = client.target(decoratedURI);

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", decoratedURI.toASCIIString());
		}

		Invocation.Builder builder = webTarget.request(APPLICATION_JSON_LD);

		Response response = _follow3Redirects(client, builder.get());

		return _handleApioResponse(QueryMethod.GET, response);
	}

	public Client getClient() {
		return ClientBuilder.newClient(_getClientConfig());
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public URI getEndpointURI() {
		try {
			return new URI(_endpoint);
		}
		catch (URISyntaxException urise) {
			_log.error("Unable to parse {} as a URI reference", _endpoint);
		}

		return null;
	}

	public ClientConfig setCredentials(String userId, String password) {
		HttpAuthenticationFeature httpAuthenticationFeature =
			HttpAuthenticationFeature.basic(userId, password);

		ClientConfig clientConfig = new ClientConfig();

		clientConfig.register(httpAuthenticationFeature);

		return clientConfig;
	}

	@Override
	public String toString() {
		return String.format("REST API Client [%s].", getEndpoint());
	}

	protected static enum QueryMethod {

		GET, POST, DELETE

	}

	private RestClient(
		String endpoint, String password, String userId,
		LiferayConnectionProperties liferayConnectionProperties) {

		_endpoint = endpoint;
		_password = password;
		_userId = userId;
		_liferayConnectionProperties = liferayConnectionProperties;
	}

	private Response _follow3Redirects(
		Client client, Response currentResponse) {

		StatusType statusType = currentResponse.getStatusInfo();

		if (statusType.getFamily() != Response.Status.Family.REDIRECTION) {
			return currentResponse;
		}

		AtomicInteger atomicInteger = new AtomicInteger();
		Response response = currentResponse;

		while ((statusType.getFamily() ==
					Response.Status.Family.REDIRECTION) &&
			   (atomicInteger.incrementAndGet() <= 3)) {

			String location = response.getHeaderString(HttpHeaders.LOCATION);

			if (StringUtils.isEmpty(location)) {
				return response;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Redirect location {}#: {}", atomicInteger.get(), location);
			}

			response.close();

			WebTarget webTarget = client.target(location);

			Invocation.Builder builder = webTarget.request(APPLICATION_JSON_LD);

			response = builder.get();
		}

		return response;
	}

	private ClientConfig _getClientConfig() {
		ClientConfig clientConfig = setCredentials(_userId, _password);

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
			ApioJsonLDConstants.PER_PAGE,
			_liferayConnectionProperties.itemsPerPage.getStringValue());

		return parameters;
	}

	private ApioResult _handleApioResponse(
			QueryMethod queryMethod, Response response)
		throws ApioException {

		StatusType statusType = response.getStatusInfo();
		int statusCode = response.getStatus();

		if (statusType.getFamily() == Response.Status.Family.SUCCESSFUL) {
			String messageEntity = response.readEntity(String.class);

			return new ApioResult(statusCode, messageEntity);
		}
		else {
			_log.error("{} request failed: {}.", queryMethod, statusCode);

			throw new ApioException(
				statusCode,
				"Request failed, please check your request setting");
		}
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
		RestClient.class);

	private final String _endpoint;
	private final LiferayConnectionProperties _liferayConnectionProperties;
	private final String _password;
	private final String _userId;

}