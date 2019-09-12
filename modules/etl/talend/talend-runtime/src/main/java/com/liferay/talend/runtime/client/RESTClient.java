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

import com.liferay.talend.common.exception.MalformedURLException;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.runtime.client.exception.ClientException;
import com.liferay.talend.runtime.client.exception.ConnectionClientException;
import com.liferay.talend.runtime.client.exception.OAuth2AuthorizationClientException;
import com.liferay.talend.runtime.client.exception.RemoteExecutionClientException;

import java.io.StringWriter;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class RESTClient {

	public RESTClient(LiferayConnectionProperties liferayConnectionProperties) {
		this(liferayConnectionProperties, null);
	}

	public RESTClient(
		LiferayConnectionProperties liferayConnectionProperties,
		String target) {

		_liferayConnectionProperties = liferayConnectionProperties;

		String targetURL = target;

		if ((targetURL == null) || targetURL.isEmpty()) {
			targetURL = liferayConnectionProperties.getApiSpecURL();
		}

		_target = targetURL;

		_client = ClientBuilder.newClient(_getClientConfig());

		_client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);

		if (_logger.isDebugEnabled()) {
			_logger.debug("Created new REST Client for endpoint {}", target);
		}
	}

	public Response executeDeleteRequest() throws ClientException {
		return _execute(HttpMethod.DELETE, _createBuilder(_getTargetURI()));
	}

	public Response executeGetRequest() throws ClientException {
		return _execute(HttpMethod.GET, _createBuilder(_getTargetURI()));
	}

	public Response executePatchRequest(JsonObject jsonObject)
		throws ClientException {

		return _execute(
			HttpMethod.PATCH, _createBuilder(_getTargetURI()),
			Entity.json(_jsonObjectToPrettyString(jsonObject)));
	}

	public Response executePostRequest(JsonObject jsonObject)
		throws ClientException {

		return _execute(
			HttpMethod.POST, _createBuilder(_getTargetURI()),
			Entity.json(_jsonObjectToPrettyString(jsonObject)));
	}

	public Response executePutRequest(JsonObject jsonObject)
		throws ClientException {

		return _execute(
			HttpMethod.PUT, _createBuilder(_getTargetURI()),
			Entity.json(_jsonObjectToPrettyString(jsonObject)));
	}

	public boolean matches(String target) {
		if (Objects.equals(_target, target)) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return String.format("REST API Client [%s].", _getTarget());
	}

	private Invocation.Builder _createBuilder(URI targetURI)
		throws ConnectionClientException, OAuth2AuthorizationClientException {

		WebTarget webTarget = _client.target(targetURI);

		if (_logger.isDebugEnabled()) {
			_logger.debug("Target: {}", targetURI);
		}

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		builder.header(
			"Authorization",
			_getAuthorizationHeader(_liferayConnectionProperties));

		return builder;
	}

	private Response _execute(String httpMethod, Invocation.Builder builder) {
		return _execute(httpMethod, builder, null);
	}

	private Response _execute(
			String httpMethod, Invocation.Builder builder, Entity<?> entity)
		throws ConnectionClientException {

		Response response = _processRedirects(
			builder.method(httpMethod, entity));

		if (_responseHandler.isSuccess(response)) {
			return response;
		}

		String responseBody = "No response body available";

		if (response.hasEntity()) {
			responseBody = _responseHandler.asText(response);
		}

		throw new RemoteExecutionClientException(
			String.format(
				"Request failed with HTTP status %d and response %s",
				response.getStatus(), responseBody),
			response.getStatus());
	}

	private Response _executeAccessTokenPostRequest(
			LiferayConnectionProperties liferayConnectionProperties)
		throws ConnectionClientException {

		String serverInstanceURL = _extractServerInstanceURL(_getTarget());

		WebTarget webTarget = _client.target(
			serverInstanceURL + _LIFERAY_OAUTH2_ACCESS_TOKEN_ENDPOINT);

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		XWWWFormURLEncoder xWWWFormURLEncoder = new XWWWFormURLEncoder();

		Entity<Form> entity = Entity.form(
			xWWWFormURLEncoder.toForm(
				"client_id", liferayConnectionProperties.getOAuthClientId(),
				"client_secret",
				liferayConnectionProperties.getOAuthClientSecret(),
				"grant_type", "client_credentials", "response_type", "code"));

		return _execute(HttpMethod.POST, builder, entity);
	}

	private String _extractServerInstanceURL(String openAPISpecRef) {
		Matcher serverURLMatcher = _openAPISpecURLPattern.matcher(
			openAPISpecRef);

		if (!serverURLMatcher.matches()) {
			throw new MalformedURLException(
				"Unable to extract Open API endpoint from URL " +
					openAPISpecRef);
		}

		return serverURLMatcher.group(1);
	}

	private String _getAuthorizationHeader(
			LiferayConnectionProperties liferayConnectionProperties)
		throws ConnectionClientException, OAuth2AuthorizationClientException {

		if (liferayConnectionProperties.isOAuth2Authorization()) {
			return "Bearer " + _getBearerToken(liferayConnectionProperties);
		}

		return "Basic " + _getBasicToken(liferayConnectionProperties);
	}

	private String _getBasicToken(
		LiferayConnectionProperties liferayConnectionProperties) {

		Base64.Encoder base64Encoder = Base64.getEncoder();

		StringBuilder sb = new StringBuilder();

		sb.append(liferayConnectionProperties.getUserId());
		sb.append(":");
		sb.append(liferayConnectionProperties.getPassword());

		String base64Seed = sb.toString();

		return new String(base64Encoder.encode(base64Seed.getBytes()));
	}

	private String _getBearerToken(
			LiferayConnectionProperties liferayConnectionProperties)
		throws ConnectionClientException, OAuth2AuthorizationClientException {

		JsonObject authorizationJsonObject = _requestAuthorizationJsonObject(
			liferayConnectionProperties);

		String tokenType = authorizationJsonObject.getString("token_type");

		if (!Objects.equals(tokenType, "Bearer")) {
			throw new OAuth2AuthorizationClientException(
				"Unexpected token type received " + tokenType);
		}

		return authorizationJsonObject.getString("access_token");
	}

	private ClientConfig _getClientConfig() {
		ClientConfig clientConfig = new ClientConfig();

		clientConfig = clientConfig.property(
			ClientProperties.CONNECT_TIMEOUT,
			_liferayConnectionProperties.getConnectTimeout() * 1000);

		clientConfig = clientConfig.property(
			ClientProperties.READ_TIMEOUT,
			_liferayConnectionProperties.getReadTimeout() * 1000);

		return clientConfig;
	}

	private String _getTarget() {
		if (_liferayConnectionProperties.isForceHttps()) {
			return _toHttps(_target);
		}

		return _target;
	}

	private URI _getTargetURI() {
		try {
			return new URI(_getTarget());
		}
		catch (URISyntaxException urise) {
			_logger.error(
				"Unable to parse {} as a URI reference", _getTarget());
		}

		return null;
	}

	private String _jsonObjectToPrettyString(JsonObject jsonObject) {
		StringWriter stringWriter = new StringWriter();

		JsonWriter jsonWriter = Json.createWriter(stringWriter);

		jsonWriter.writeObject(jsonObject);

		stringWriter.flush();

		return stringWriter.toString();
	}

	private Response _processRedirects(Response response)
		throws ConnectionClientException {

		if (!_liferayConnectionProperties.isFollowRedirects()) {
			return response;
		}

		int count = 0;
		Response currentResponse = response;

		while (_responseHandler.isRedirect(currentResponse) && (count < 3)) {
			String location = currentResponse.getHeaderString(
				HttpHeaders.LOCATION);

			if (StringUtils.isEmpty(location)) {
				return currentResponse;
			}

			if (_logger.isDebugEnabled()) {
				_logger.debug("Redirect {}# to {}", count, location);
			}

			currentResponse.close();

			try {
				Invocation.Builder builder = _createBuilder(new URI(location));

				currentResponse = builder.get();
			}
			catch (URISyntaxException urise) {
				throw new ConnectionClientException(
					"Unable to redirect to location " + location,
					response.getStatus(), urise);
			}
		}

		return currentResponse;
	}

	private JsonObject _requestAuthorizationJsonObject(
			LiferayConnectionProperties liferayConnectionProperties)
		throws ConnectionClientException, OAuth2AuthorizationClientException {

		Response response = _executeAccessTokenPostRequest(
			liferayConnectionProperties);

		if (response == null) {
			throw new OAuth2AuthorizationClientException(
				"Authorization request failed for unresponsive OAuth 2.0 " +
					"endpoint");
		}

		if (response.getStatus() != 200) {
			throw new OAuth2AuthorizationClientException(
				String.format(
					"OAuth 2.0 check failed with response status {%s}",
					response.getStatus()),
				response.getStatus());
		}

		if (_responseHandler.isApplicationJsonContentType(response)) {
			return _responseHandler.asJsonObject(response);
		}

		List<String> contentTypeValues = _responseHandler.getContentType(
			response);

		throw new OAuth2AuthorizationClientException(
			String.format(
				"Unable to extract OAuth 2.0 credentials from response " +
					"content type {%s}",
				contentTypeValues.get(0)),
			response.getStatus());
	}

	private String _toHttps(String url) {
		String lowerCasedUrl = StringUtils.lowerCase(url);

		if (lowerCasedUrl.startsWith(_HTTPS)) {
			return url;
		}

		return _HTTPS.concat(url.substring(_HTTP.length()));
	}

	private static final String _HTTP = "http://";

	private static final String _HTTPS = "https://";

	private static final String _LIFERAY_OAUTH2_ACCESS_TOKEN_ENDPOINT =
		"/o/oauth2/token";

	private static final Logger _logger = LoggerFactory.getLogger(
		RESTClient.class);

	private static final Pattern _openAPISpecURLPattern = Pattern.compile(
		"(https?://.+(:\\d+)?)(/o/(.+)/)(v\\d+(.\\d+)*)/openapi\\.(yaml|json)");

	private final Client _client;
	private final LiferayConnectionProperties _liferayConnectionProperties;
	private final ResponseHandler _responseHandler = new ResponseHandler();
	private final String _target;

}