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

package com.liferay.mule.internal.valueprovider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.mule.internal.connection.LiferayConnection;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
public class EndpointValueProvider implements ValueProvider {

	@Override
	public Set<Value> resolve() throws ValueResolvingException {
		try {
			Set<Value> values = new HashSet<>();

			HttpResponse httpResponse = _connection.getOpenAPISpec();

			HttpEntity httpEntity = httpResponse.getEntity();

			InputStream inputStream = httpEntity.getContent();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode jsonNode = objectMapper.readTree(inputStream);

			jsonNode = jsonNode.get("paths");

			Iterator<String> iterator = jsonNode.fieldNames();

			while (iterator.hasNext()) {
				values.add(
					ValueBuilder.newValue(
						iterator.next()
					).build());
			}

			return values;
		}
		catch (IOException | TimeoutException e) {
			throw new ValueResolvingException(e.getMessage(), e.getMessage());
		}
	}

	@Connection
	private LiferayConnection _connection;

}