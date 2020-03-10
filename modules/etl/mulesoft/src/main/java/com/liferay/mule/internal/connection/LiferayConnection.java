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

package com.liferay.mule.internal.connection;

import com.liferay.mule.internal.connection.authentication.BasicAuthentication;
import com.liferay.mule.internal.connection.authentication.HttpAuthentication;
import com.liferay.mule.internal.connection.authentication.OAuth2Authentication;
import com.liferay.mule.internal.error.LiferayError;
import com.liferay.mule.internal.oas.OASURLParser;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;

import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.client.HttpClientFactory;
import org.mule.runtime.http.api.client.proxy.ProxyConfig;
import org.mule.runtime.http.api.domain.entity.InputStreamHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matija Petanjek
 */
public final class LiferayConnection {

	public static LiferayConnection withBasicAuthentication(
			HttpService httpService, String openApiSpecPath, String username,
			String password, ProxyConfig proxyConfig)
		throws ConnectionException {

		return new LiferayConnection(
			httpService, openApiSpecPath,
			new BasicAuthentication(username, password), proxyConfig);
	}

	public static LiferayConnection withOAuth2Authentication(
			HttpService httpService, String openApiSpecPath, String consumerKey,
			String consumerSecret, ProxyConfig proxyConfig)
		throws ConnectionException {

		return new LiferayConnection(
			httpService, openApiSpecPath, consumerKey, consumerSecret,
			proxyConfig);
	}

	public HttpResponse delete(
			Map<String, String> pathParams,
			MultiMap<String, String> queryParams, String endpoint,
			long connectionTimeout)
		throws ModuleException {

		return send(
			HttpConstants.Method.DELETE, null, pathParams, queryParams,
			endpoint, connectionTimeout);
	}

	public HttpResponse get(
			Map<String, String> pathParams,
			MultiMap<String, String> queryParams, String endpoint,
			long connectionTimeout)
		throws ModuleException {

		return send(
			HttpConstants.Method.GET, null, pathParams, queryParams, endpoint,
			connectionTimeout);
	}

	public HttpResponse getOpenAPISpec() throws IOException, TimeoutException {
		return httpClient.send(
			getHttpRequest(
				HttpConstants.Method.GET, openAPISpecPath, new MultiMap<>(),
				null),
			10000, true, null);
	}

	public void invalidate() {
		httpClient.stop();
	}

	public HttpResponse patch(
			InputStream inputStream, Map<String, String> pathParams,
			MultiMap<String, String> queryParams, String endpoint,
			long connectionTimeout)
		throws ModuleException {

		return send(
			HttpConstants.Method.PATCH, inputStream, pathParams, queryParams,
			endpoint, connectionTimeout);
	}

	public HttpResponse post(
			InputStream inputStream, Map<String, String> pathParams,
			MultiMap<String, String> queryParams, String endpoint,
			long connectionTimeout)
		throws ModuleException {

		return send(
			HttpConstants.Method.POST, inputStream, pathParams, queryParams,
			endpoint, connectionTimeout);
	}

	private LiferayConnection(
			HttpService httpService, String openApiSpecPath,
			BasicAuthentication basicAuthentication, ProxyConfig proxyConfig)
		throws ConnectionException {

		openAPISpecPath = openApiSpecPath;
		serverBaseURL = getServerBaseURL(openApiSpecPath);
		httpAuthentication = basicAuthentication;

		initHttpClient(httpService, proxyConfig);
	}

	private LiferayConnection(
			HttpService httpService, String openApiSpecPath, String consumerKey,
			String consumerSecret, ProxyConfig proxyConfig)
		throws ConnectionException {

		openAPISpecPath = openApiSpecPath;
		serverBaseURL = getServerBaseURL(openApiSpecPath);

		initHttpClient(httpService, proxyConfig);

		try {
			httpAuthentication = new OAuth2Authentication(
				consumerKey, consumerSecret, httpClient, openAPISpecPath);
		}
		catch (MalformedURLException malformedURLException) {
			throw new ConnectionException(malformedURLException);
		}
	}

	private HttpRequest getHttpRequest(
			HttpConstants.Method method, String uri,
			MultiMap<String, String> queryParams, InputStream inputStream)
		throws ModuleException {

		HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();

		httpRequestBuilder.addHeader(
			"Authorization", httpAuthentication.getAuthorizationHeader()
		).addHeader(
			"Content-Type", "application/json"
		).method(
			method
		).queryParams(
			queryParams
		).uri(
			uri
		);

		if (inputStream != null) {
			httpRequestBuilder.entity(new InputStreamHttpEntity(inputStream));
		}

		return httpRequestBuilder.build();
	}

	private String getServerBaseURL(String openApiSpecPath)
		throws ConnectionException {

		try {
			OASURLParser oasURLParser = new OASURLParser(openApiSpecPath);

			return oasURLParser.getServerBaseURL();
		}
		catch (MalformedURLException malformedURLException) {
			throw new ConnectionException(malformedURLException);
		}
	}

	private void initHttpClient(
		HttpService httpService, ProxyConfig proxyConfig) {

		HttpClientConfiguration.Builder builder =
			new HttpClientConfiguration.Builder();

		if (proxyConfig != null) {
			builder.setProxyConfig(proxyConfig);
		}

		builder.setName("Liferay Http Client");

		HttpClientFactory httpClientFactory = httpService.getClientFactory();

		httpClient = httpClientFactory.create(builder.build());

		httpClient.start();
	}

	private void logHttpRequest(
		long connectionTimeout, HttpConstants.Method method,
		Map<String, String> pathParams, MultiMap<String, String> queryParams,
		String uri) {

		logger.debug(
			"Sending {} request to {} with path parameters {}, query " +
				"parameters {} and connection timeout {} ms",
			method, uri, pathParams, queryParams, connectionTimeout);
	}

	private String resolvePathParams(
		String endpoint, Map<String, String> pathParams) {

		for (Map.Entry<String, String> pathParam : pathParams.entrySet()) {
			endpoint = endpoint.replace(
				"{" + pathParam.getKey() + "}", pathParam.getValue());
		}

		return endpoint;
	}

	private HttpResponse send(
			HttpConstants.Method method, InputStream inputStream,
			Map<String, String> pathParams,
			MultiMap<String, String> queryParams, String endpoint,
			long connectionTimeout)
		throws ModuleException {

		String uri = serverBaseURL + resolvePathParams(endpoint, pathParams);

		HttpRequest httpRequest = getHttpRequest(
			method, uri, queryParams, inputStream);

		logHttpRequest(connectionTimeout, method, pathParams, queryParams, uri);

		try {
			return httpClient.send(
				httpRequest, (int)connectionTimeout, true, null);
		}
		catch (IOException ioException) {
			logger.error(ioException.getMessage(), ioException);

			throw new ModuleException(
				ioException.getMessage(), LiferayError.EXECUTION, ioException);
		}
		catch (TimeoutException timeoutException) {
			logger.error(timeoutException.getMessage(), timeoutException);

			throw new ModuleException(
				timeoutException.getMessage(), LiferayError.CONNECTION_TIMEOUT,
				timeoutException);
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(
		LiferayConnection.class);

	private final HttpAuthentication httpAuthentication;
	private HttpClient httpClient;
	private final String openAPISpecPath;
	private final String serverBaseURL;

}