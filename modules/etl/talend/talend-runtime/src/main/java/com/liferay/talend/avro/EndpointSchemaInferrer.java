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
import com.liferay.talend.openapi.OpenAPIFormat;
import com.liferay.talend.openapi.OpenAPIType;
import com.liferay.talend.openapi.constants.OpenApiConstants;
import com.liferay.talend.tliferayoutput.Action;
import com.liferay.talend.utils.StringUtils;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.common.SchemaProperties;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.NameUtil;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.exception.TalendRuntimeException;

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
		else if (Objects.equals(
					operation, HttpMethod.PATCH.toLowerCase(Locale.US))) {

			JsonNode schemaRefJsonNode = apiSpecJsonNode.path(
				OpenApiConstants.PATHS
			).path(
				endpoint
			).path(
				operation
			).path(
				OpenApiConstants.REQUEST_BODY
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

	private static Schema.Field _getDesignField(
		String fieldName, Map.Entry<String, JsonNode> propertyEntry,
		Set<String> requiredPropertyNames) {

		Schema.Field designField = new Schema.Field(
			fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
			(Object)null);

		designField = _processFieldRequirements(
			designField, propertyEntry, requiredPropertyNames);

		JsonNode propertyJsonNode = propertyEntry.getValue();

		OpenAPIType openAPIType = OpenAPIType.fromDefinition(
			propertyJsonNode.path(
				OpenApiConstants.TYPE
			).asText());

		if ((openAPIType == OpenAPIType.ARRAY) ||
			(openAPIType == OpenAPIType.OBJECT)) {

			return designField;
		}

		JsonNode openAPIFormatDefinitionJsonNode = propertyJsonNode.path(
			OpenApiConstants.FORMAT);

		String openAPIFormatDefinition = null;

		if (!openAPIFormatDefinitionJsonNode.isMissingNode()) {
			openAPIFormatDefinition = openAPIFormatDefinitionJsonNode.asText();
		}

		OpenAPIFormat openAPIFormat = OpenAPIFormat.fromOpenAPITypeAndFormat(
			openAPIType, openAPIFormatDefinition);

		if (openAPIFormat == OpenAPIFormat.BOOLEAN) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._boolean()), null,
				(Object)null);
		}
		else if (openAPIFormat == OpenAPIFormat.BINARY) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._bytes()), null,
				(Object)null);
		}
		else if (openAPIFormat == OpenAPIFormat.DATE) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._date()), null,
				(Object)null);
		}
		else if (openAPIFormat == OpenAPIFormat.DATE_TIME) {
			designField = new Schema.Field(
				fieldName,
				AvroUtils.wrapAsNullable(AvroUtils._logicalTimestamp()), null,
				(Object)null);
		}
		else if (openAPIFormat == OpenAPIFormat.DOUBLE) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._double()), null,
				(Object)null);
		}
		else if (openAPIFormat == OpenAPIFormat.FLOAT) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._float()), null,
				(Object)null);
		}
		else if (openAPIFormat == OpenAPIFormat.INT32) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._int()), null,
				(Object)null);
		}
		else if (openAPIFormat == OpenAPIFormat.INT64) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._long()), null,
				(Object)null);
		}
		else if (openAPIFormat == OpenAPIFormat.STRING) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
				(Object)null);
		}

		designField = _processFieldRequirements(
			designField, propertyEntry, requiredPropertyNames);

		return designField;
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

		if (_log.isDebugEnabled()) {
			_log.debug("Schema name: {}", schemaName);
		}

		if (StringUtils.isEmpty(schemaName)) {
			throw TalendRuntimeException.createUnexpectedException(
				"Unable to determine the Schema for the selected endpoint");
		}

		JsonNode schemaJsonNode = _extractSchemaJsonNode(
			schemaName, apiSpecJsonNode);

		_processSchemaJsonNode(
			null, schemaJsonNode, index, previousFieldNames, schemaFields,
			apiSpecJsonNode);

		return Schema.createRecord("Runtime", null, null, false, schemaFields);
	}

	private static Schema.Field _processFieldRequirements(
		Schema.Field designField, Map.Entry<String, JsonNode> propertyEntry,
		Set<String> requiredPropertyNames) {

		if (requiredPropertyNames.contains(propertyEntry.getKey())) {
			designField = new Schema.Field(
				designField.name(),
				AvroUtils.unwrapIfNullable(designField.schema()), null,
				(Object)null);

			designField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");
		}

		return designField;
	}

	/**
	 * Creates the list of {@link Schema.Field} {@param schemaFields} which is
	 * used to create the Schema record for the endpoint
	 *
	 * @param parentPropertyName If the property has reference to another schema
	 *                           this parameter is used to pass the parent
	 *                           property's name
	 * @param schemaJsonNode It contains the actual OAS Schema object where the
	 *                       processing happens on its properties
	 * @param index It is used for generating Column / Field names in case there
	 *              are too many forbidden characters in the generated field
	 *              name
	 * @param previousFieldNames Contains all the unique field names which is
	 *                              being processed
	 * @param schemaFields The list of schema fields from which the Schema
	 *                     record is created at the end
	 * @param apiSpecJsonNode Holds the entire API Specification object
	 */
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

			Schema.Field designField = _getDesignField(
				fieldName, propertyEntry, requiredPropertyNames);

			schemaFields.add(designField);
		}
	}

	private static String _stripSchemaName(JsonNode schemaRefJsonNode) {
		String reference = schemaRefJsonNode.asText();

		return reference.replaceAll(OpenApiConstants.PATH_SCHEMA_REFERENCE, "");
	}

	private static final Logger _log = LoggerFactory.getLogger(
		EndpointSchemaInferrer.class);

}