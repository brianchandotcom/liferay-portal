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

package com.liferay.mule.internal.metadata;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.mule.internal.connection.LiferayConnection;
import com.liferay.mule.internal.json.JsonNodeReader;
import com.liferay.mule.internal.oas.OASFormat;
import com.liferay.mule.internal.oas.OASType;

import java.io.IOException;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectFieldTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;
import org.mule.metadata.api.model.MetadataFormat;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.FailureCode;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
public class LiferayOutputTypeResolver implements OutputTypeResolver<String> {

	@Override
	public String getCategoryName() {
		return "Liferay";
	}

	@Override
	public MetadataType getOutputType(
			MetadataContext metadataContext, String endpoint)
		throws ConnectionException, MetadataResolvingException {

		BaseTypeBuilder baseTypeBuilder = metadataContext.getTypeBuilder();

		ObjectTypeBuilder objectTypeBuilder = baseTypeBuilder.create(
			MetadataFormat.JSON
		).objectType();

		try {
			Optional<LiferayConnection> liferayConnectionOptional =
				metadataContext.getConnection();

			LiferayConnection liferayConnection =
				liferayConnectionOptional.get();

			HttpResponse openAPISpecHttpResponse =
				liferayConnection.getOpenAPISpec();

			JsonNode openAPISpecJsonNode = _jsonNodeReader.fromHttpResponse(
				openAPISpecHttpResponse);

			JsonNode referenceJsonNode = _getReferenceJsonNode(
				openAPISpecJsonNode, endpoint);

			String schemaName = _getSchemaName(referenceJsonNode.textValue());

			JsonNode schemaJsonNode = _getSchemaJsonNode(
				openAPISpecJsonNode, schemaName);

			JsonNode propertiesJsonNode = schemaJsonNode.get("properties");

			if (schemaName.startsWith("Page")) {
				referenceJsonNode = propertiesJsonNode.get(
					"items"
				).get(
					"items"
				).get(
					"$ref"
				);

				schemaName = _getSchemaName(referenceJsonNode.textValue());

				schemaJsonNode = _getSchemaJsonNode(
					openAPISpecJsonNode, schemaName);

				propertiesJsonNode = schemaJsonNode.get("properties");
			}

			JsonNode required = schemaJsonNode.get("required");

			return _resolveMetadataType(
				objectTypeBuilder, propertiesJsonNode, required);
		}
		catch (IOException ioe) {
			throw new MetadataResolvingException(
				ioe.getMessage(), FailureCode.NO_DYNAMIC_METADATA_AVAILABLE);
		}
		catch (TimeoutException te) {
			throw new MetadataResolvingException(
				te.getMessage(), FailureCode.CONNECTION_FAILURE);
		}
	}

	private void _getObjectFieldKey(
		ObjectFieldTypeBuilder objectFieldTypeBuilder,
		Map.Entry<String, JsonNode> propertyEntry) {

		objectFieldTypeBuilder.key(propertyEntry.getKey());
	}

	private void _getObjectFieldRequired(
		ObjectFieldTypeBuilder objectFieldTypeBuilder, String propertyName,
		JsonNode requiredJsonNode) {

		for (JsonNode propertyNameJsonNode : requiredJsonNode) {
			if (propertyName.equals(propertyNameJsonNode.textValue())) {
				objectFieldTypeBuilder.required(true);

				return;
			}
		}

		objectFieldTypeBuilder.required(false);
	}

	private void _getObjectFieldValue(
		ObjectFieldTypeBuilder objectFieldTypeBuilder,
		Map.Entry<String, JsonNode> propertyEntry) {

		JsonNode propertyJsonNode = propertyEntry.getValue();

		JsonNode typeJsonNode = propertyJsonNode.get("type");

		if (typeJsonNode == null) {
			objectFieldTypeBuilder.value(
			).objectType();

			return;
		}
		else if (Objects.equals(typeJsonNode.textValue(), "array")) {
			objectFieldTypeBuilder.value(
			).arrayType(
			).of(
			).objectType();

			return;
		}

		OASType oasType = OASType.fromDefinition(typeJsonNode.textValue());

		JsonNode formatJsonNode = propertyJsonNode.get("format");

		String oasFormatValue = null;

		if (formatJsonNode != null) {
			oasFormatValue = formatJsonNode.textValue();
		}

		OASFormat oasFormat = OASFormat.fromOpenAPITypeAndFormat(
			oasType, oasFormatValue);

		if (oasFormat == OASFormat.BIGDECIMAL) {
			objectFieldTypeBuilder.value(
			).numberType();
		}
		else if (oasFormat == OASFormat.BINARY) {
			objectFieldTypeBuilder.value(
			).binaryType();
		}
		else if (oasFormat == OASFormat.BOOLEAN) {
			objectFieldTypeBuilder.value(
			).booleanType();
		}
		else if (oasFormat == OASFormat.BYTE) {
			objectFieldTypeBuilder.value(
			).numberType();
		}
		else if (oasFormat == OASFormat.DATE) {
			objectFieldTypeBuilder.value(
			).dateType();
		}
		else if (oasFormat == OASFormat.DATE_TIME) {
			objectFieldTypeBuilder.value(
			).dateTimeType();
		}
		else if (oasFormat == OASFormat.DICTIONARY) {
			objectFieldTypeBuilder.value(
			).stringType();
		}
		else if (oasFormat == OASFormat.DOUBLE) {
			objectFieldTypeBuilder.value(
			).numberType();
		}
		else if (oasFormat == OASFormat.FLOAT) {
			objectFieldTypeBuilder.value(
			).numberType();
		}
		else if (oasFormat == OASFormat.INT32) {
			objectFieldTypeBuilder.value(
			).numberType();
		}
		else if (oasFormat == OASFormat.INT64) {
			objectFieldTypeBuilder.value(
			).numberType();
		}
		else if (oasFormat == OASFormat.STRING) {
			objectFieldTypeBuilder.value(
			).stringType();
		}
		else {
			objectFieldTypeBuilder.value(
			).nullType();
		}
	}

	private JsonNode _getReferenceJsonNode(
		JsonNode openAPISpecJsonNode, String endpoint) {

		return openAPISpecJsonNode.get(
			"paths"
		).get(
			endpoint
		).get(
			"get"
		).get(
			"responses"
		).get(
			"default"
		).get(
			"content"
		).get(
			"application/json"
		).get(
			"schema"
		).get(
			"$ref"
		);
	}

	private JsonNode _getSchemaJsonNode(
		JsonNode openAPISpecJsonNode, String schemaName) {

		return openAPISpecJsonNode.get(
			"components"
		).get(
			"schemas"
		).get(
			schemaName
		);
	}

	private String _getSchemaName(String reference) {
		return reference.replaceAll("#/components/schemas/", "");
	}

	private MetadataType _resolveMetadataType(
		ObjectTypeBuilder objectTypeBuilder, JsonNode propertiesJsonNode,
		JsonNode requiredJsonNode) {

		Iterator<Map.Entry<String, JsonNode>> propertiesIterator =
			propertiesJsonNode.fields();

		while (propertiesIterator.hasNext()) {
			Map.Entry<String, JsonNode> propertyEntry =
				propertiesIterator.next();

			ObjectFieldTypeBuilder objectFieldTypeBuilder =
				objectTypeBuilder.addField();

			_getObjectFieldKey(objectFieldTypeBuilder, propertyEntry);
			_getObjectFieldValue(objectFieldTypeBuilder, propertyEntry);
			_getObjectFieldRequired(
				objectFieldTypeBuilder, propertyEntry.getKey(),
				requiredJsonNode);
		}

		return objectTypeBuilder.build();
	}

	private final JsonNodeReader _jsonNodeReader = new JsonNodeReader();

}