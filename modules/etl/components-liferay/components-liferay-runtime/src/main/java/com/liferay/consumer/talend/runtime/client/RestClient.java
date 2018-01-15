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

package com.liferay.consumer.talend.runtime.client;

import com.liferay.consumer.talend.connection.LiferayConnectionProperties;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.apache.commons.lang3.StringUtils;

import org.glassfish.jersey.client.ClientConfig;
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

	public RestClient(LiferayConnectionProperties connection) {
		_endpoint = connection.endpoint.getValue();
		_userId = connection.userId.getValue();
		_password = connection.password.getValue();
	}

	public RestClient(
		LiferayConnectionProperties connection, String resourceURL) {

		this(connection);
		_endpoint = resourceURL;
	}

	public ApioResult executeGetRequest() throws ApioException {
		Client client = getClient();

		WebTarget webTarget = client.target(_endpoint);

		Invocation.Builder invocationBuilder = webTarget.request(
			APPLICATION_JSON_LD);

		Response response = _follow3Redirects(client, invocationBuilder.get());

		return _handleApioResponse(QueryMethod.GET, response);
	}

	public Client getClient() {
		return ClientBuilder.newClient(setCredentials(_userId, _password));
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public ClientConfig setCredentials(String userId, String password) {
		HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic(
			userId, password);

		ClientConfig clientConfig = new ClientConfig();

		clientConfig.register(authFeature);

		return clientConfig;
	}

	@Override
	public String toString() {
		return String.format("REST API Client [%s].", _endpoint);
	}

	protected static enum QueryMethod {

		GET, POST, DELETE
	}

	private Response _follow3Redirects(
		Client client, Response currentResponse) {

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

			WebTarget webTarget = client.target(location);

			Invocation.Builder invocationBuilder = webTarget.request(
				APPLICATION_JSON_LD);

			response = invocationBuilder.get();
		}

		return response;
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
				"Request failed! Please check your request setting!");
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		RestClient.class);

	private volatile String _endpoint;
	private final String _password;
	private final String _userId;

}