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

package com.liferay.segments.asah.internal.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
	public String doGet(
		String url, MultivaluedMap<String, Object> parameters,
		Map<String, String> headers) {

		WebTarget webTarget = _client.target(_baseURI);

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
	public String getBaseURI() {
		return _baseURI;
	}

	@Override
	public void setBaseURI(String baseURI) {
		_baseURI = baseURI;
	}

	@Activate
	protected void activate() {
		_client = _clientBuilder.build();

		_client.register(JacksonJsonProvider.class);
	}

	private void _validateResponse(Response response) {
		int status = response.getStatus();

		if ((status < HttpServletResponse.SC_OK) ||
			(status >= HttpServletResponse.SC_MULTIPLE_CHOICES)) {

			throw new ClientErrorException(
				"Unexpected response status: " + status, status);
		}
	}

	private String _baseURI;
	private Client _client;

	@Reference
	private ClientBuilder _clientBuilder;

}