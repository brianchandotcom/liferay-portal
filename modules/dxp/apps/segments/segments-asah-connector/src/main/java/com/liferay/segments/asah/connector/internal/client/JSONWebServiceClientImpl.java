/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import com.liferay.petra.string.StringBundler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = JSONWebServiceClient.class)
public class JSONWebServiceClientImpl implements JSONWebServiceClient {

	@Override
	public String doDelete(
		String baseURI, String url, Map<String, String> parameters,
		Map<String, String> headers) {

		WebTarget webTarget = _client.target(baseURI);

		webTarget = webTarget.path(url);

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
		}

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

		Response response = builder.delete();

		_validateResponse(response);

		return response.readEntity(String.class);
	}

	@Override
	public String doGet(
		String baseURI, String url, MultivaluedMap<String, Object> parameters,
		Map<String, String> headers) {

		WebTarget webTarget = _client.target(baseURI);

		webTarget = webTarget.path(url);

		for (MultivaluedMap.Entry<String, List<Object>> entry :
				parameters.entrySet()) {

			for (Object value : entry.getValue()) {
				webTarget = webTarget.queryParam(entry.getKey(), value);
			}
		}

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

		Response response = builder.get();

		_validateResponse(response);

		return response.readEntity(String.class);
	}

	@Override
	public <T> void doPatch(
		String baseURI, String url, T object, Map<String, String> headers) {

		WebTarget webTarget = _client.target(baseURI);

		webTarget = webTarget.path(url);

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

		Response response = builder.method(
			HttpMethod.PATCH,
			Entity.entity(object, MediaType.APPLICATION_JSON_TYPE));

		_validateResponse(response);
	}

	@Override
	public <T, V> V doPost(
		Class<V> clazz, String baseURI, String url, T object,
		Map<String, String> headers) {

		WebTarget webTarget = _client.target(baseURI);

		webTarget = webTarget.path(url);

		Invocation.Builder builder = webTarget.request();

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

		Response response = builder.post(
			Entity.entity(object, MediaType.APPLICATION_JSON_TYPE));

		_validateResponse(response);

		return response.readEntity(clazz);
	}

	@Override
	public <T> void doPut(
		String baseURI, String url, T object, Map<String, String> headers) {

		WebTarget webTarget = _client.target(baseURI);

		webTarget = webTarget.path(url);

		Invocation.Builder builder = webTarget.request(
			MediaType.APPLICATION_JSON_TYPE);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

		Response response = builder.put(
			Entity.entity(object, MediaType.APPLICATION_JSON_TYPE));

		_validateResponse(response);
	}

	@Activate
	protected void activate() {
		_client = _clientBuilder.build();

		JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();

		jacksonJsonProvider.configure(
			SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		jacksonJsonProvider.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		_client.register(jacksonJsonProvider);
	}

	private void _validateResponse(Response response) {
		int status = response.getStatus();

		if ((status < HttpServletResponse.SC_OK) ||
			(status >= HttpServletResponse.SC_MULTIPLE_CHOICES)) {

			throw new ClientErrorException(
				StringBundler.concat(
					"Unexpected response status ", status,
					" with response message: ",
					response.readEntity(String.class)),
				status);
		}
	}

	private Client _client;

	@Reference
	private ClientBuilder _clientBuilder;

}