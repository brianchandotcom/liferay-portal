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
import com.liferay.mule.internal.oas.OASURLParser;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;

import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.util.MultiMap;
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
		throws IOException, TimeoutException {

		return _httpClient.send(
			_getHttpRequest(
				HttpConstants.Method.DELETE,
				_serverBaseURL + _resolvePathParams(endpoint, pathParams),
				queryParams, null),
			(int)connectionTimeout, true, null);
	}

	public HttpResponse get(
			Map<String, String> pathParams,
			MultiMap<String, String> queryParams, String endpoint,
			long connectionTimeout)
		throws IOException, TimeoutException {

		return _httpClient.send(
			_getHttpRequest(
				HttpConstants.Method.GET,
				_serverBaseURL + _resolvePathParams(endpoint, pathParams),
				queryParams, null),
			(int)connectionTimeout, true, null);
	}

	public HttpResponse getOpenAPISpec() throws IOException, TimeoutException {
		return _httpClient.send(
			_getHttpRequest(
				HttpConstants.Method.GET, _openAPISpecPath, new MultiMap<>(),
				null),
			10000, true, null);
	}

	public void invalidate() {
		_httpClient.stop();
	}

	public HttpResponse patch(
			InputStream inputStream, Map<String, String> pathParams,
			MultiMap<String, String> queryParams, String endpoint,
			long connectionTimeout)
		throws IOException, TimeoutException {

		return _httpClient.send(
			_getHttpRequest(
				HttpConstants.Method.PATCH,
				_serverBaseURL + _resolvePathParams(endpoint, pathParams),
				queryParams, inputStream),
			(int)connectionTimeout, true, null);
	}

	public HttpResponse post(
			InputStream inputStream, Map<String, String> pathParams,
			MultiMap<String, String> queryParams, String endpoint,
			long connectionTimeout)
		throws IOException, TimeoutException {

		return _httpClient.send(
			_getHttpRequest(
				HttpConstants.Method.POST,
				_serverBaseURL + _resolvePathParams(endpoint, pathParams),
				queryParams, inputStream),
			(int)connectionTimeout, true, null);
	}

	private LiferayConnection(
			HttpService httpService, String openAPISpecPath,
			BasicAuthentication httpAuthentication, ProxyConfig proxyConfig)
		throws ConnectionException {

		_openAPISpecPath = openAPISpecPath;
		_serverBaseURL = _getServerBaseURL(openAPISpecPath);
		_httpAuthentication = httpAuthentication;

		_initHttpClient(httpService, proxyConfig);
	}

	private LiferayConnection(
			HttpService httpService, String openAPISpecPath, String consumerKey,
			String consumerSecret, ProxyConfig proxyConfig)
		throws ConnectionException {

		_openAPISpecPath = openAPISpecPath;
		_serverBaseURL = _getServerBaseURL(openAPISpecPath);

		_initHttpClient(httpService, proxyConfig);

		try {
			_httpAuthentication = new OAuth2Authentication(
				consumerKey, consumerSecret, _httpClient, _openAPISpecPath);
		}
		catch (MalformedURLException malformedURLException) {
			throw new ConnectionException(malformedURLException);
		}
	}

	private HttpRequest _getHttpRequest(
			HttpConstants.Method method, String uri,
			MultiMap<String, String> queryParams, InputStream inputStream)
		throws IOException, TimeoutException {

		HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();

		httpRequestBuilder.addHeader(
			"Authorization", _httpAuthentication.getAuthorizationHeader()
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

	private String _getServerBaseURL(String openApiSpecPath)
		throws ConnectionException {

		try {
			OASURLParser oasURLParser = new OASURLParser(openApiSpecPath);

			return oasURLParser.getServerBaseURL();
		}
		catch (MalformedURLException malformedURLException) {
			throw new ConnectionException(malformedURLException);
		}
	}

	private void _initHttpClient(
		HttpService httpService, ProxyConfig proxyConfig) {

		HttpClientConfiguration.Builder builder =
			new HttpClientConfiguration.Builder();

		if (proxyConfig != null) {
			builder.setProxyConfig(proxyConfig);
		}

		builder.setName("Liferay Http Client");

		HttpClientFactory httpClientFactory = httpService.getClientFactory();

		_httpClient = httpClientFactory.create(builder.build());

		_httpClient.start();
	}

	private String _resolvePathParams(
		String endpoint, Map<String, String> pathParams) {

		for (Map.Entry<String, String> pathParam : pathParams.entrySet()) {
			endpoint = endpoint.replace(
				"{" + pathParam.getKey() + "}", pathParam.getValue());
		}

		return endpoint;
	}

	private final HttpAuthentication _httpAuthentication;
	private HttpClient _httpClient;
	private final String _openAPISpecPath;
	private final String _serverBaseURL;

}