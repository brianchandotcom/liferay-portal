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
import com.liferay.talend.common.util.URIUtil;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.runtime.client.exception.ConnectionException;
import com.liferay.talend.runtime.client.exception.OAuth2Exception;

import java.io.StringReader;
import java.io.StringWriter;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.properties.property.Property;

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

		if (_log.isDebugEnabled()) {
			_log.debug("Created new REST Client for endpoint {}", target);
		}
	}

	public Response executeDeleteRequest() {
		return _execute(HttpMethod.DELETE, _createBuilder(_getTargetURI()));
	}

	public Response executeGetRequest() {
		URI decoratedURI = URIUtil.updateWithQueryParameters(
			_getTargetURI(), _getQueryParametersMap());

		return _execute(HttpMethod.GET, _createBuilder(decoratedURI));
	}

	public Response executePatchRequest(JsonObject jsonNode) {
		return _execute(
			HttpMethod.PATCH, _createBuilder(_getTargetURI()),
			Entity.json(_jsonNodeToPrettyString(jsonNode)));
	}

	public Response executePostRequest(JsonObject jsonNode) {
		return _execute(
			HttpMethod.POST, _createBuilder(_getTargetURI()),
			Entity.json(_jsonNodeToPrettyString(jsonNode)));
	}

	public Response executePutRequest(JsonObject jsonNode) {
		return _execute(
			HttpMethod.PUT, _createBuilder(_getTargetURI()),
			Entity.json(_jsonNodeToPrettyString(jsonNode)));
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

	private JsonObject _asJsonNode(Response response) {
		try {
			String entity = response.readEntity(String.class);

			JsonReader jsonReader = Json.createReader(new StringReader(entity));

			return jsonReader.readObject();
		}
		catch (Throwable t) {
			throw TalendRuntimeException.createUnexpectedException(t);
		}
	}

	private Invocation.Builder _createBuilder(URI targetURI) {
		WebTarget webTarget = _client.target(targetURI);

		if (_log.isDebugEnabled()) {
			_log.debug("Target: {}", targetURI);
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
		String httpMethod, Invocation.Builder builder, Entity<?> entity) {

		Response response = _processRedirects(
			builder.method(httpMethod, entity));

		if (_isSuccess(response)) {
			return response;
		}

		String responseBody = "No response body available";

		if (response.hasEntity()) {
			responseBody = response.readEntity(String.class);
		}

		throw TalendRuntimeException.createUnexpectedException(
			String.format(
				"Request failed with HTTP status %d and response %s",
				response.getStatus(), responseBody));
	}

	private Response _executeAccessTokenPostRequest(
		LiferayConnectionProperties liferayConnectionProperties) {

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
		LiferayConnectionProperties liferayConnectionProperties) {

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
		LiferayConnectionProperties liferayConnectionProperties) {

		JsonObject authorizationJsonObject = _requestAuthorizationJsonNode(
			liferayConnectionProperties);

		String tokenType = authorizationJsonObject.getString("token_type");

		if (!Objects.equals(tokenType, "Bearer")) {
			throw new OAuth2Exception(
				"Unexpected token type received " + tokenType);
		}

		return authorizationJsonObject.getString("access_token");
	}

	private ClientConfig _getClientConfig() {
		ClientConfig clientConfig = new ClientConfig();

		clientConfig = clientConfig.property(
			ClientProperties.CONNECT_TIMEOUT,
			_liferayConnectionProperties.connectTimeout.getValue() * 1000);

		clientConfig = clientConfig.property(
			ClientProperties.READ_TIMEOUT,
			_liferayConnectionProperties.readTimeout.getValue() * 1000);

		return clientConfig;
	}

	private List<String> _getContentType(Response response) {
		String contentTypeHeader = response.getHeaderString("Content-Type");

		if ((contentTypeHeader == null) || contentTypeHeader.isEmpty()) {
			return Collections.emptyList();
		}

		String[] headers = contentTypeHeader.split(",");

		List<String> headerValues = new ArrayList<>();

		for (String header : headers) {
			headerValues.add(header);
		}

		return headerValues;
	}

	private Map<String, String> _getQueryParametersMap() {
		Map<String, String> parameters = new HashMap<>();

		parameters.put(
			"pageSize",
			_liferayConnectionProperties.itemsPerPage.getStringValue());

		return parameters;
	}

	private Response.Status.Family _getResponseStatusFamily(Response response) {
		Response.StatusType statusType = response.getStatusInfo();

		return statusType.getFamily();
	}

	private String _getTarget() {
		if (_getValue(_liferayConnectionProperties.forceHttps)) {
			return _toHttps(_target);
		}

		return _target;
	}

	private URI _getTargetURI() {
		try {
			return new URI(_getTarget());
		}
		catch (URISyntaxException urise) {
			_log.error("Unable to parse {} as a URI reference", _getTarget());
		}

		return null;
	}

	private <T> T _getValue(Property<T> property) {
		return property.getValue();
	}

	private boolean _isApplicationJsonContentType(Response response) {
		List<String> strings = _getContentType(response);

		if (strings.contains("application/json")) {
			return true;
		}

		return false;
	}

	private boolean _isRedirect(Response response) {
		if (_getResponseStatusFamily(response) ==
				Response.Status.Family.REDIRECTION) {

			return true;
		}

		return false;
	}

	private boolean _isSuccess(Response response) {
		if (_getResponseStatusFamily(response) ==
				Response.Status.Family.SUCCESSFUL) {

			return true;
		}

		return false;
	}

	private String _jsonNodeToPrettyString(JsonObject jsonNode) {
		StringWriter stringWriter = new StringWriter();

		JsonWriter jsonWriter = Json.createWriter(stringWriter);

		jsonWriter.writeObject(jsonNode);

		stringWriter.flush();

		return stringWriter.toString();
	}

	private Response _processRedirects(Response response) {
		if (!_getValue(_liferayConnectionProperties.followRedirects)) {
			return response;
		}

		int count = 0;
		Response currentResponse = response;

		while (_isRedirect(currentResponse) && (count < 3)) {
			String location = currentResponse.getHeaderString(
				HttpHeaders.LOCATION);

			if (StringUtils.isEmpty(location)) {
				return currentResponse;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Redirect {}# to {}", count, location);
			}

			currentResponse.close();

			try {
				Invocation.Builder builder = _createBuilder(new URI(location));

				currentResponse = builder.get();
			}
			catch (URISyntaxException urise) {
				throw TalendRuntimeException.createUnexpectedException(urise);
			}
		}

		return currentResponse;
	}

	private JsonObject _requestAuthorizationJsonNode(
			LiferayConnectionProperties liferayConnectionProperties)
		throws ConnectionException {

		Response response = _executeAccessTokenPostRequest(
			liferayConnectionProperties);

		if (response == null) {
			throw new OAuth2Exception(
				"Authorization request failed for unresponsive OAuth 2.0 " +
					"endpoint");
		}

		if (response.getStatus() != 200) {
			throw new OAuth2Exception(
				String.format(
					"OAuth 2.0 check failed with response status {%s}",
					response.getStatus()));
		}

		if (!_isApplicationJsonContentType(response)) {
			List<String> contentTypeValues = _getContentType(response);

			throw new OAuth2Exception(
				String.format(
					"OAuth 2.0 check failed with response status and {%s} " +
						"content type {%s}",
					response.getStatus(), contentTypeValues.get(0)));
		}

		return _asJsonNode(response);
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

	private static final Logger _log = LoggerFactory.getLogger(
		RESTClient.class);

	private static final Pattern _openAPISpecURLPattern = Pattern.compile(
		"(https?://.+(:\\d+)?)(/o/(.+)/)(v\\d+(.\\d+)*)/openapi\\.(yaml|json)");

	private final Client _client;
	private final LiferayConnectionProperties _liferayConnectionProperties;
	private final String _target;

}