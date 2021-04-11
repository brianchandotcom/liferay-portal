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

package com.liferay.mule.internal.values;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.mule.internal.connection.LiferayConnection;
import com.liferay.mule.internal.oas.constants.OASConstants;
import com.liferay.mule.internal.util.JsonNodeReader;

import java.io.IOException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matija Petanjek
 */
public class ClassNameValueProvider implements ValueProvider {

	@Override
	public Set<Value> resolve() throws ValueResolvingException {
		try {
			JsonNode oasJsonNode = jsonNodeReader.fromHttpResponse(
				liferayConnection.getOpenAPISpecHttpResponse());

			return getClassNameValues(oasJsonNode);
		}
		catch (IOException ioException) {
			logger.error(
				"Unable to read OpenAPI document from Liferay Portal instance",
				ioException);

			throw new ValueResolvingException(
				ioException.getMessage(),
				ValueResolvingException.CONNECTION_FAILURE, ioException);
		}
		catch (TimeoutException timeoutException) {
			logger.error(
				"Unable to establish connection to Liferay Portal instance",
				timeoutException);

			throw new ValueResolvingException(
				timeoutException.getMessage(),
				ValueResolvingException.CONNECTION_FAILURE, timeoutException);
		}
	}

	protected Set<Value> getClassNameValues(JsonNode oasJsonNode) {
		Set<Value> classNameValues = new HashSet<>();

		JsonNode schemasJsonNode = jsonNodeReader.getDescendantJsonNode(
			oasJsonNode, OASConstants.PATH_COMPONENTS_SCHEMAS);

		Iterator<Map.Entry<String, JsonNode>> schemasIterator =
			schemasJsonNode.fields();

		while (schemasIterator.hasNext()) {
			Map.Entry<String, JsonNode> schemaEntry = schemasIterator.next();

			JsonNode schemaJsonNode = schemaEntry.getValue();

			JsonNode classNameJsonNode = jsonNodeReader.fetchDescendantJsonNode(
				schemaJsonNode,
				OASConstants.PATH_PROPERTIES_X_CLASS_NAME_DEFAULT);

			if (!classNameJsonNode.isNull()) {
				ValueBuilder valueBuilder = ValueBuilder.newValue(
					classNameJsonNode.asText());

				classNameValues.add(valueBuilder.build());
			}
		}

		return classNameValues;
	}

	private static final Logger logger = LoggerFactory.getLogger(
		ClassNameValueProvider.class);

	private final JsonNodeReader jsonNodeReader = new JsonNodeReader();

	@Connection
	private LiferayConnection liferayConnection;

}