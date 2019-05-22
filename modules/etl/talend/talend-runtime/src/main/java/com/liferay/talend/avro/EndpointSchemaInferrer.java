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

package com.liferay.talend.avro;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.talend.avro.constants.AvroConstants;
import com.liferay.talend.openapi.constants.OpenApiConstants;
import com.liferay.talend.tliferayoutput.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.HttpMethod;

import org.apache.avro.Schema;

import org.talend.components.common.SchemaProperties;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.NameUtil;
import org.talend.daikon.avro.SchemaConstants;

/**
 * @author Zoltán Takács
 */
public class EndpointSchemaInferrer {

	public static Schema inferSchema(
		String endpoint, String operation, JsonNode apiSpecJsonNode) {

		operation = operation.toLowerCase(Locale.US);

		Schema schema = SchemaProperties.EMPTY_SCHEMA;

		if (operation.equals(Action.Delete.getMethodName())) {
			schema = _getDeleteSchema();
		}
		else {
			schema = _getSchema(endpoint, operation, apiSpecJsonNode);
		}

		return schema;
	}

	private static void _addIdSchemaField(
		List<Schema.Field> schemaFields, Set<String> previousFieldNames) {

		String safeIdFieldName = AvroConstants.ID;

		previousFieldNames.add(safeIdFieldName);

		Schema.Field designField = new Schema.Field(
			safeIdFieldName, AvroUtils.wrapAsNullable(AvroUtils._string()),
			null, (Object)null);

		// This is the first column in the schema

		schemaFields.add(0, designField);
	}

	private static String _extractEndpointSchemaName(
		String endpoint, String operation, JsonNode apiSpecJsonNode) {

		String schemaName = null;

		if (Objects.equals(operation, HttpMethod.GET.toLowerCase(Locale.US))) {
			JsonNode schemaRefJsonNode = apiSpecJsonNode.path(
				OpenApiConstants.PATHS
			).path(
				endpoint
			).path(
				operation
			).path(
				OpenApiConstants.RESPONSES
			).path(
				OpenApiConstants.DEFAULT
			).path(
				OpenApiConstants.CONTENT
			).path(
				OpenApiConstants.APPLICATION_JSON
			).path(
				OpenApiConstants.SCHEMA
			).path(
				OpenApiConstants.REF
			);

			schemaName = _stripSchemaName(schemaRefJsonNode);

			JsonNode schemaJsonNode = _extractSchemaJsonNode(
				schemaName, apiSpecJsonNode);

			JsonNode referenceSchemaJsonNode = schemaJsonNode.path(
				OpenApiConstants.PROPERTIES
			).path(
				OpenApiConstants.ITEMS
			).path(
				OpenApiConstants.ITEMS
			).path(
				OpenApiConstants.REF
			);

			if (!referenceSchemaJsonNode.isMissingNode()) {
				schemaName = _stripSchemaName(referenceSchemaJsonNode);
			}
		}

		return schemaName;
	}

	private static JsonNode _extractSchemaJsonNode(
		String schemaName, JsonNode apiSpecJsonNode) {

		return apiSpecJsonNode.path(
			OpenApiConstants.COMPONENTS
		).path(
			OpenApiConstants.SCHEMAS
		).path(
			schemaName
		);
	}

	private static Schema _getDeleteSchema() {
		List<Schema.Field> schemaFields = new ArrayList<>(1);

		Schema.Field designField = new Schema.Field(
			AvroConstants.ID, AvroUtils._long(), null, (Object)null);

		designField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

		schemaFields.add(designField);

		return Schema.createRecord("Runtime", null, null, false, schemaFields);
	}

	private static Set<String> _getRequiredPropertyNames(
		JsonNode schemaJsonNode) {

		JsonNode requiredJsonNode = schemaJsonNode.path(
			OpenApiConstants.REQUIRED);

		Set<String> requiredProperties = new HashSet<>();

		if (requiredJsonNode.isArray()) {
			for (JsonNode valueJsonNode : requiredJsonNode) {
				requiredProperties.add(valueJsonNode.asText());
			}
		}

		return Collections.unmodifiableSet(requiredProperties);
	}

	private static Schema _getSchema(
		String endpoint, String operation, JsonNode apiSpecJsonNode) {

		AtomicInteger index = new AtomicInteger();
		List<Schema.Field> schemaFields = new ArrayList<>();
		Set<String> previousFieldNames = new HashSet<>();

		if (operation.equals(Action.Update.getMethodName())) {
			_addIdSchemaField(schemaFields, previousFieldNames);

			index.incrementAndGet();
		}

		String schemaName = _extractEndpointSchemaName(
			endpoint, operation, apiSpecJsonNode);

		JsonNode schemaJsonNode = _extractSchemaJsonNode(
			schemaName, apiSpecJsonNode);

		_processSchemaJsonNode(
			null, schemaJsonNode, index, previousFieldNames, schemaFields,
			apiSpecJsonNode);

		return Schema.createRecord("Runtime", null, null, false, schemaFields);
	}

	private static void _processSchemaJsonNode(
		String parentPropertyName, JsonNode schemaJsonNode, AtomicInteger index,
		Set<String> previousFieldNames, List<Schema.Field> schemaFields,
		JsonNode apiSpecJsonNode) {

		Set<String> requiredPropertyNames = _getRequiredPropertyNames(
			schemaJsonNode);

		JsonNode schemaPropertiesJsonNode = schemaJsonNode.path(
			OpenApiConstants.PROPERTIES);

		for (Iterator<Map.Entry<String, JsonNode>> it =
				schemaPropertiesJsonNode.fields(); it.hasNext();
			 index.incrementAndGet()) {

			Map.Entry<String, JsonNode> propertyEntry = it.next();

			JsonNode propertyJsonNode = propertyEntry.getValue();

			JsonNode schemaRefJsonNode = propertyJsonNode.path(
				OpenApiConstants.REF);

			if (!schemaRefJsonNode.isMissingNode() &&
				(parentPropertyName == null)) {

				String referenceSchemaName = _stripSchemaName(
					schemaRefJsonNode);

				JsonNode referenceSchemaJsonNode = _extractSchemaJsonNode(
					referenceSchemaName, apiSpecJsonNode);

				_processSchemaJsonNode(
					propertyEntry.getKey(), referenceSchemaJsonNode, index,
					previousFieldNames, schemaFields, apiSpecJsonNode);

				continue;
			}

			String fieldName = NameUtil.correct(
				propertyEntry.getKey(), index.get(), previousFieldNames);

			if (parentPropertyName != null) {
				fieldName = NameUtil.correct(
					parentPropertyName + "_" + propertyEntry.getKey(),
					index.get(), previousFieldNames);
			}

			previousFieldNames.add(fieldName);

			Schema.Field designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
				(Object)null);

			if (requiredPropertyNames.contains(propertyEntry.getKey())) {
				designField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");
			}

			schemaFields.add(designField);
		}
	}

	private static String _stripSchemaName(JsonNode schemaRefJsonNode) {
		String reference = schemaRefJsonNode.asText();

		return reference.replaceAll(OpenApiConstants.PATH_SCHEMA_REFERENCE, "");
	}

}