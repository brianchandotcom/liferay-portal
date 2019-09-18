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
import com.liferay.mule.internal.oas.OASConstants;
import com.liferay.mule.internal.oas.OASFormat;
import com.liferay.mule.internal.oas.OASType;
import com.liferay.mule.internal.util.StringUtil;

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

/**
 * @author Matija Petanjek
 */
public class MetadataTypeBuilder {

	public MetadataType buildMetadataType(
			MetadataContext metadataContext, String endpoint, String operation)
		throws ConnectionException, MetadataResolvingException {

		JsonNode oasJsonNode = _getOASJsonNode(metadataContext.getConnection());

		JsonNode referenceJsonNode = _getReferenceJsonNode(
			oasJsonNode, endpoint, operation);

		String schemaName = _getSchemaName(referenceJsonNode.textValue());

		JsonNode schemaJsonNode = _getSchemaJsonNode(oasJsonNode, schemaName);

		if (schemaName.startsWith("Page")) {
			return _getSingleResultMetadataType(
				metadataContext, oasJsonNode,
				schemaJsonNode.get(OASConstants.PROPERTIES));
		}

		return _resolveMetadataType(
			metadataContext, schemaJsonNode.get(OASConstants.PROPERTIES),
			schemaJsonNode.get(OASConstants.REQUIRED));
	}

	private JsonNode _getOASJsonNode(
			Optional<LiferayConnection> liferayConnectionOptional)
		throws MetadataResolvingException {

		try {
			LiferayConnection liferayConnection =
				liferayConnectionOptional.get();

			return _jsonNodeReader.fromHttpResponse(
				liferayConnection.getOpenAPISpec());
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

	private void _getObjectFieldValue(
		ObjectFieldTypeBuilder objectFieldTypeBuilder,
		Map.Entry<String, JsonNode> propertyEntry) {

		JsonNode propertyJsonNode = propertyEntry.getValue();

		JsonNode typeJsonNode = propertyJsonNode.get(OASConstants.TYPE);

		if (typeJsonNode == null) {
			objectFieldTypeBuilder.value(
			).objectType();

			return;
		}
		else if (Objects.equals(typeJsonNode.textValue(), OASConstants.ARRAY)) {
			objectFieldTypeBuilder.value(
			).arrayType(
			).of(
			).objectType();

			return;
		}

		OASType oasType = OASType.fromDefinition(typeJsonNode.textValue());

		JsonNode formatJsonNode = propertyJsonNode.get(OASConstants.FORMAT);

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

	private ObjectTypeBuilder _getObjectTypeBuilder(
		MetadataContext metadataContext) {

		BaseTypeBuilder baseTypeBuilder = metadataContext.getTypeBuilder();

		return baseTypeBuilder.create(
			MetadataFormat.JSON
		).objectType();
	}

	private JsonNode _getReferenceJsonNode(
		JsonNode openAPISpecJsonNode, String endpoint, String operation) {

		String path = StringUtil.replace(
			OASConstants.
				PATH_REQUEST_BODY_CONTENT_APPLICATION_JSON_SCHEMA_PATTERN,
			"ENDPOINT_TPL", endpoint, "OPERATION_TPL", operation);

		return _jsonNodeReader.getDescendantJsonNode(openAPISpecJsonNode, path);
	}

	private JsonNode _getSchemaJsonNode(
		JsonNode openAPISpecJsonNode, String schemaName) {

		String path = StringUtil.replace(
			OASConstants.PATH_COMPONENTS_SCHEMAS_PATTERN, "SCHEMA_TPL",
			schemaName);

		return _jsonNodeReader.getDescendantJsonNode(openAPISpecJsonNode, path);
	}

	private String _getSchemaName(String reference) {
		return reference.replaceAll(OASConstants.PATH_SCHEMA_REFERENCE, "");
	}

	private MetadataType _getSingleResultMetadataType(
		MetadataContext metadataContext, JsonNode oasJsonNode,
		JsonNode propertiesJsonNode) {

		JsonNode referenceJsonNode = _jsonNodeReader.getDescendantJsonNode(
			propertiesJsonNode, OASConstants.PATH_ITEMS_ITEMS_REF);

		JsonNode schemaJsonNode = _getSchemaJsonNode(
			oasJsonNode, referenceJsonNode.textValue());

		propertiesJsonNode = schemaJsonNode.get(OASConstants.PROPERTIES);

		return _resolveMetadataType(
			metadataContext, propertiesJsonNode,
			schemaJsonNode.get(OASConstants.REQUIRED));
	}

	private MetadataType _resolveMetadataType(
		MetadataContext metadataContext, JsonNode propertiesJsonNode,
		JsonNode requiredJsonNode) {

		ObjectTypeBuilder objectTypeBuilder = _getObjectTypeBuilder(
			metadataContext);

		Iterator<Map.Entry<String, JsonNode>> propertiesIterator =
			propertiesJsonNode.fields();

		while (propertiesIterator.hasNext()) {
			Map.Entry<String, JsonNode> propertyEntry =
				propertiesIterator.next();

			ObjectFieldTypeBuilder objectFieldTypeBuilder =
				objectTypeBuilder.addField();

			_setObjectFieldKey(objectFieldTypeBuilder, propertyEntry);
			_getObjectFieldValue(objectFieldTypeBuilder, propertyEntry);
			_setObjectFieldRequired(
				objectFieldTypeBuilder, propertyEntry.getKey(),
				requiredJsonNode);
		}

		return objectTypeBuilder.build();
	}

	private void _setObjectFieldKey(
		ObjectFieldTypeBuilder objectFieldTypeBuilder,
		Map.Entry<String, JsonNode> propertyEntry) {

		objectFieldTypeBuilder.key(propertyEntry.getKey());
	}

	private void _setObjectFieldRequired(
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

	private final JsonNodeReader _jsonNodeReader = new JsonNodeReader();

}