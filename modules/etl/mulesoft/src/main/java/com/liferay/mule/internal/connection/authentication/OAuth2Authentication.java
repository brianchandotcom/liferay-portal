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

package com.liferay.mule.internal.connection.authentication;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.mule.internal.json.JsonNodeReader;
import com.liferay.mule.internal.oas.OASURLParser;

import java.io.IOException;

import java.net.MalformedURLException;

import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
public class OAuth2Authentication implements HttpAuthentication {

	public OAuth2Authentication(
			String consumerKey, String consumerSecret, HttpClient httpClient,
			String openAPISpecPath)
		throws MalformedURLException {

		_httpClient = httpClient;
		_oAuth2AccessTokenURI = _getOAuth2AccessTokenURI(openAPISpecPath);

		_queryParams.put("client_id", consumerKey);
		_queryParams.put("client_secret", consumerSecret);
		_queryParams.put("grant_type", "client_credentials");
	}

	@Override
	public String getAuthorizationHeader()
		throws IOException, TimeoutException {

		JsonNode authorizationJsonNode = _getAuthorizationJsonNode();

		JsonNode tokenTypeJsonNode = authorizationJsonNode.get("token_type");
		JsonNode accessTokenJsonNode = authorizationJsonNode.get(
			"access_token");

		return String.format(
			"%s %s", tokenTypeJsonNode.textValue(),
			accessTokenJsonNode.textValue());
	}

	private JsonNode _getAuthorizationJsonNode()
		throws IOException, TimeoutException {

		HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();

		HttpResponse httpResponse = _httpClient.send(
			httpRequestBuilder.addHeader(
				"Content-Type", "application/x-www-form-urlencoded"
			).method(
				HttpConstants.Method.POST
			).queryParams(
				_queryParams
			).uri(
				_oAuth2AccessTokenURI
			).build(),
			10000, true, null);

		if (httpResponse == null) {
			throw new OAuth2Exception(
				"Unresponsive authorization server's OAuth 2.0 endpoint");
		}
		else if (httpResponse.getStatusCode() != 200) {
			throw new OAuth2Exception(
				String.format(
					"Unable to fetch access token from authorization server. " +
						"Request failed with status %d (%s)",
					httpResponse.getStatusCode(),
					httpResponse.getReasonPhrase()));
		}

		JsonNodeReader jsonNodeReader = new JsonNodeReader();

		return jsonNodeReader.fromHttpResponse(httpResponse);
	}

	private String _getOAuth2AccessTokenURI(String openAPISpecPath)
		throws MalformedURLException {

		OASURLParser oasURLParser = new OASURLParser(openAPISpecPath);

		return oasURLParser.getAuthorityWithScheme() + _OAUTH2_ENDPOINT;
	}

	private static final String _OAUTH2_ENDPOINT = "/o/oauth2/token";

	private final HttpClient _httpClient;
	private final String _oAuth2AccessTokenURI;
	private final MultiMap<String, String> _queryParams = new MultiMap<>();

}