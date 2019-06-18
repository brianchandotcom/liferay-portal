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

import com.liferay.talend.avro.constants.AvroConstants;
import com.liferay.talend.commons.oas.OpenAPIFormat;
import com.liferay.talend.commons.oas.OpenAPIType;
import com.liferay.talend.commons.oas.constants.OpenAPIConstants;
import com.liferay.talend.commons.util.StringUtils;
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

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

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

	public Schema inferSchema(
		String endpoint, String operation, JsonObject apiSpecJsonObject) {

		operation = operation.toLowerCase(Locale.US);

		Schema schema = SchemaProperties.EMPTY_SCHEMA;

		if (operation.equals(Action.Delete.getMethodName())) {
			schema = _getDeleteSchema();
		}
		else {
			schema = _getSchema(endpoint, operation, apiSpecJsonObject);
		}

		return schema;
	}

	private static String _extractEndpointSchemaName(
		String endpoint, String operation, JsonObject apiSpecJsonNode) {

		String schemaName = null;

		if (Objects.equals(operation, HttpMethod.GET.toLowerCase(Locale.US))) {
			JsonObject schemaJsonObject = apiSpecJsonNode.getJsonObject(
				OpenAPIConstants.PATHS
			).getJsonObject(
				endpoint
			).getJsonObject(
				operation
			).getJsonObject(
				OpenAPIConstants.RESPONSES
			).getJsonObject(
				OpenAPIConstants.DEFAULT
			).getJsonObject(
				OpenAPIConstants.CONTENT
			).getJsonObject(
				OpenAPIConstants.APPLICATION_JSON
			).getJsonObject(
				OpenAPIConstants.SCHEMA
			);

			schemaName = _stripSchemaName(
				schemaJsonObject.getString(OpenAPIConstants.REF));

			JsonObject schemaJsonNode = _extractSchemaJsonObject(
				schemaName, apiSpecJsonNode);

			JsonObject itemsPropertiesJsonNode = schemaJsonNode.getJsonObject(
				OpenAPIConstants.PROPERTIES
			).getJsonObject(
				OpenAPIConstants.ITEMS
			);

			if ((itemsPropertiesJsonNode != null) &&
				itemsPropertiesJsonNode.containsKey(OpenAPIConstants.REF)) {

				schemaName = _stripSchemaName(
					itemsPropertiesJsonNode.getString(OpenAPIConstants.REF));
			}

			return schemaName;
		}

		if (!Objects.equals(
				operation, HttpMethod.PATCH.toLowerCase(Locale.US)) &&
			!Objects.equals(
				operation, HttpMethod.POST.toLowerCase(Locale.US))) {

			return null;
		}

		JsonObject schemaJsonNode = apiSpecJsonNode.getJsonObject(
			OpenAPIConstants.PATHS
		).getJsonObject(
			endpoint
		).getJsonObject(
			operation
		).getJsonObject(
			OpenAPIConstants.REQUEST_BODY
		).getJsonObject(
			OpenAPIConstants.CONTENT
		).getJsonObject(
			OpenAPIConstants.APPLICATION_JSON
		).getJsonObject(
			OpenAPIConstants.SCHEMA
		);

		schemaName = _stripSchemaName(
			schemaJsonNode.getString(OpenAPIConstants.REF));

		return schemaName;
	}

	private static JsonObject _extractSchemaJsonObject(
		String schemaName, JsonObject apiSpecJsonNode) {

		return apiSpecJsonNode.getJsonObject(
			OpenAPIConstants.COMPONENTS
		).getJsonObject(
			OpenAPIConstants.SCHEMAS
		).getJsonObject(
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
		String fieldName, JsonObject propertyJsonObject) {

		Schema.Field designField = new Schema.Field(
			fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
			(Object)null);

		OpenAPIType openAPIType = OpenAPIType.fromDefinition(
			propertyJsonObject.getString(OpenAPIConstants.TYPE));

		if (openAPIType == OpenAPIType.ARRAY) {
			return designField;
		}

		String openAPIFormatDefinition = null;

		if (propertyJsonObject.containsKey(OpenAPIConstants.FORMAT)) {
			openAPIFormatDefinition = propertyJsonObject.getString(
				OpenAPIConstants.FORMAT);
		}
		else if ((openAPIType == OpenAPIType.OBJECT) &&
				 propertyJsonObject.containsKey(
					 OpenAPIConstants.ADDITIONAL_PROPERTIES)) {

			JsonObject additionalPropertiesJsonObject =
				propertyJsonObject.getJsonObject(
					OpenAPIConstants.ADDITIONAL_PROPERTIES);

			if (additionalPropertiesJsonObject.containsKey(
					OpenAPIConstants.TYPE)) {

				openAPIFormatDefinition =
					additionalPropertiesJsonObject.getString(
						OpenAPIConstants.TYPE);
			}
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
		else if (openAPIFormat == OpenAPIFormat.DICTIONARY) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
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

		return designField;
	}

	private static String _stripSchemaName(String reference) {
		return reference.replaceAll(OpenAPIConstants.PATH_SCHEMA_REFERENCE, "");
	}

	private Set<String> _asSet(JsonArray jsonArray) {
		if ((jsonArray == null) || jsonArray.isEmpty()) {
			return Collections.emptySet();
		}

		List<JsonString> jsonStrings = jsonArray.getValuesAs(JsonString.class);

		Set<String> strings = new HashSet<>();

		for (JsonString jsonString : jsonStrings) {
			strings.add(jsonString.getString());
		}

		return strings;
	}

	private Schema _getSchema(
		String endpoint, String operation, JsonObject apiSpecJsonObject) {

		AtomicInteger index = new AtomicInteger();
		List<Schema.Field> schemaFields = new ArrayList<>();
		Set<String> previousFieldNames = new HashSet<>();

		String schemaName = _extractEndpointSchemaName(
			endpoint, operation, apiSpecJsonObject);

		if (_log.isDebugEnabled()) {
			_log.debug("Schema name: {}", schemaName);
		}

		if (StringUtils.isEmpty(schemaName)) {
			throw TalendRuntimeException.createUnexpectedException(
				"Unable to determine the Schema for the selected endpoint");
		}

		JsonObject schemaJsonObject = _extractSchemaJsonObject(
			schemaName, apiSpecJsonObject);

		_processSchemaJsonObject(
			null, schemaJsonObject, index, previousFieldNames, schemaFields,
			apiSpecJsonObject);

		return Schema.createRecord("Runtime", null, null, false, schemaFields);
	}

	private void _processSchemaJsonObject(
		String parentPropertyName, JsonObject schemaJsonObject,
		AtomicInteger index, Set<String> previousFieldNames,
		List<Schema.Field> schemaFields, JsonObject apiSpecJsonObject) {

		Set<String> required = _asSet(
			schemaJsonObject.getJsonArray(OpenAPIConstants.REQUIRED));

		JsonObject schemaPropertiesJsonObject = schemaJsonObject.getJsonObject(
			OpenAPIConstants.PROPERTIES);

		Set<Map.Entry<String, JsonValue>> entries =
			schemaPropertiesJsonObject.entrySet();

		for (Iterator<Map.Entry<String, JsonValue>> it = entries.iterator();
			 it.hasNext(); index.incrementAndGet()) {

			Map.Entry<String, JsonValue> propertyEntry = it.next();

			JsonValue propertyJsonValue = propertyEntry.getValue();

			JsonObject propertyJsonObject = propertyJsonValue.asJsonObject();

			if (propertyJsonObject.containsKey(OpenAPIConstants.REF) &&
				(parentPropertyName == null)) {

				String referenceSchemaName = _stripSchemaName(
					propertyJsonObject.getString(OpenAPIConstants.REF));

				JsonObject referenceSchemaJsonNode = _extractSchemaJsonObject(
					referenceSchemaName, apiSpecJsonObject);

				_processSchemaJsonObject(
					propertyEntry.getKey(), referenceSchemaJsonNode, index,
					previousFieldNames, schemaFields, apiSpecJsonObject);

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
				fieldName, propertyJsonValue.asJsonObject());

			if (required.contains(fieldName)) {
				designField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");
			}

			schemaFields.add(designField);
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		EndpointSchemaInferrer.class);

}