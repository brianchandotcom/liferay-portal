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
import com.liferay.talend.common.json.JsonFinder;
import com.liferay.talend.common.oas.OASFormat;
import com.liferay.talend.common.oas.OASType;
import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.NameUtil;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.exception.TalendRuntimeException;

/**
 * @author Igor Beslic
 */
public class SchemaBuilder {

	public Schema build(
		String endpoint, String operation, JsonObject oasJsonObject) {

		_endpoint = endpoint;
		_oasJsonObject = oasJsonObject;
		_operation = operation;

		if (Objects.equals(_operation, OASConstants.OPERATION_DELETE)) {
			return _getDeleteSchema();
		}

		return _getSchema();
	}

	private static Schema _getDeleteSchema() {
		List<Schema.Field> schemaFields = new ArrayList<>(1);

		Schema.Field designField = new Schema.Field(
			AvroConstants.ID, AvroUtils._long(), null, (Object)null);

		designField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

		schemaFields.add(designField);

		return Schema.createRecord("Runtime", null, null, false, schemaFields);
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

	private String _extractEndpointSchemaName() {
		String schemaName = null;

		if (Objects.equals(_operation, OASConstants.OPERATION_GET)) {
			String jsonFinderPath = StringUtil.replace(
				OASConstants.
					PATH_RESPONSES_CONTENT_APPLICATION_JSON_SCHEMA_PATTERN,
				"ENDPOINT_TPL", _endpoint, "OPERATION_TPL", _operation);

			JsonObject schemaJsonObject = _jsonFinder.getDescendantJsonObject(
				jsonFinderPath, _oasJsonObject);

			schemaName = _stripSchemaName(
				schemaJsonObject.getString(OASConstants.REF));

			JsonObject schemaDefinitionJsonObject = _extractSchemaJsonObject(
				schemaName);

			JsonObject itemsPropertiesJsonObject =
				_jsonFinder.getDescendantJsonObject(
					OASConstants.PATH_PROPERTIES_ITEMS_ITEMS,
					schemaDefinitionJsonObject);

			if (!itemsPropertiesJsonObject.isEmpty() &&
				itemsPropertiesJsonObject.containsKey(OASConstants.REF)) {

				schemaName = _stripSchemaName(
					itemsPropertiesJsonObject.getString(OASConstants.REF));
			}

			return schemaName;
		}

		if (!Objects.equals(_operation, OASConstants.OPERATION_PATCH) &&
			!Objects.equals(_operation, OASConstants.OPERATION_POST)) {

			return null;
		}

		String jsonFinderPath = StringUtil.replace(
			OASConstants.
				PATH_REQUEST_BODY_CONTENT_APPLICATION_JSON_SCHEMA_PATTERN,
			"ENDPOINT_TPL", _endpoint, "OPERATION_TPL", _operation);

		JsonObject schemaJsonObject = _jsonFinder.getDescendantJsonObject(
			jsonFinderPath, _oasJsonObject);

		schemaName = _stripSchemaName(
			schemaJsonObject.getString(OASConstants.REF));

		return schemaName;
	}

	private JsonObject _extractSchemaJsonObject(String schemaName) {
		String jsonFinderPath = StringUtil.replace(
			OASConstants.PATH_COMPONENTS_SCHEMAS_PATTERN, "SCHEMA_TPL",
			schemaName);

		return _jsonFinder.getDescendantJsonObject(
			jsonFinderPath, _oasJsonObject);
	}

	private Schema.Field _getDesignField(
		String fieldName, JsonObject propertyJsonObject) {

		Schema.Field designField = new Schema.Field(
			fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
			(Object)null);

		OASType oasType = OASType.fromDefinition(
			propertyJsonObject.getString(OASConstants.TYPE));

		if (oasType == OASType.ARRAY) {
			return designField;
		}

		String openAPIFormatDefinition = null;

		if (propertyJsonObject.containsKey(OASConstants.FORMAT)) {
			openAPIFormatDefinition = propertyJsonObject.getString(
				OASConstants.FORMAT);
		}
		else if ((oasType == OASType.OBJECT) &&
				 propertyJsonObject.containsKey(
					 OASConstants.ADDITIONAL_PROPERTIES)) {

			JsonObject additionalPropertiesJsonObject =
				propertyJsonObject.getJsonObject(
					OASConstants.ADDITIONAL_PROPERTIES);

			if (additionalPropertiesJsonObject.containsKey(OASConstants.TYPE)) {
				openAPIFormatDefinition =
					additionalPropertiesJsonObject.getString(OASConstants.TYPE);
			}
		}

		OASFormat oasFormat = OASFormat.fromOpenAPITypeAndFormat(
			oasType, openAPIFormatDefinition);

		if (oasFormat == OASFormat.BOOLEAN) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._boolean()), null,
				(Object)null);
		}
		else if (oasFormat == OASFormat.BINARY) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._bytes()), null,
				(Object)null);
		}
		else if (oasFormat == OASFormat.DATE) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._date()), null,
				(Object)null);
		}
		else if (oasFormat == OASFormat.DATE_TIME) {
			designField = new Schema.Field(
				fieldName,
				AvroUtils.wrapAsNullable(AvroUtils._logicalTimestamp()), null,
				(Object)null);
		}
		else if (oasFormat == OASFormat.DICTIONARY) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(_getDictionarySchema()),
				null, (Object)null);
		}
		else if (oasFormat == OASFormat.DOUBLE) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._double()), null,
				(Object)null);
		}
		else if (oasFormat == OASFormat.FLOAT) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._float()), null,
				(Object)null);
		}
		else if (oasFormat == OASFormat.INT32) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._int()), null,
				(Object)null);
		}
		else if (oasFormat == OASFormat.INT64) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._long()), null,
				(Object)null);
		}
		else if (oasFormat == OASFormat.STRING) {
			designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
				(Object)null);
		}

		return designField;
	}

	private Schema _getDictionarySchema() {
		List<Schema.Field> fields = new ArrayList<>();

		fields.add(
			new Schema.Field("key", AvroUtils._string(), null, (Object)null));
		fields.add(
			new Schema.Field("value", AvroUtils._string(), null, (Object)null));

		Schema dictionarySchema = Schema.createRecord(
			"Dictionary", null, null, false, fields);

		dictionarySchema.addProp(SchemaConstants.JAVA_CLASS_FLAG, "true");

		return dictionarySchema;
	}

	private Schema _getSchema() {
		Set<String> previousFieldNames = new HashSet<>();

		String schemaName = _extractEndpointSchemaName();

		if (_logger.isDebugEnabled()) {
			_logger.debug("Schema name: {}", schemaName);
		}

		if (StringUtil.isEmpty(schemaName)) {
			throw TalendRuntimeException.createUnexpectedException(
				"Unable to determine the schema for the selected endpoint");
		}

		JsonObject schemaJsonObject = _extractSchemaJsonObject(schemaName);

		return _toSchema(schemaName, schemaJsonObject, previousFieldNames);
	}

	private String _stripSchemaName(String reference) {
		return reference.replaceAll(OASConstants.PATH_SCHEMA_REFERENCE, "");
	}

	private Schema _toSchema(
		String schemaName, JsonObject schemaJsonObject,
		Set<String> previousFieldNames) {

		List<Schema.Field> schemaFields = new ArrayList<>();

		Set<String> required = _asSet(
			schemaJsonObject.getJsonArray(OASConstants.REQUIRED));

		JsonObject schemaPropertiesJsonObject = schemaJsonObject.getJsonObject(
			OASConstants.PROPERTIES);

		Set<Map.Entry<String, JsonValue>> entries =
			schemaPropertiesJsonObject.entrySet();

		int fieldIdx = 0;

		for (Iterator<Map.Entry<String, JsonValue>> it = entries.iterator();
			 it.hasNext(); fieldIdx++) {

			Map.Entry<String, JsonValue> propertyEntry = it.next();

			JsonValue propertyJsonValue = propertyEntry.getValue();

			JsonObject propertyJsonObject = propertyJsonValue.asJsonObject();

			String fieldName = NameUtil.correct(
				propertyEntry.getKey(), fieldIdx, previousFieldNames);

			if (propertyJsonObject.containsKey(OASConstants.REF)) {
				String nestedSchemaName = _stripSchemaName(
					propertyJsonObject.getString(OASConstants.REF));

				JsonObject referenceSchemaJsonObject = _extractSchemaJsonObject(
					nestedSchemaName);

				Schema nestedSchema = _toSchema(
					nestedSchemaName, referenceSchemaJsonObject,
					previousFieldNames);

				nestedSchema.addProp(SchemaConstants.JAVA_CLASS_FLAG, "true");

				Schema.Field schemaField = new Schema.Field(
					fieldName, AvroUtils.wrapAsNullable(nestedSchema), null,
					(Object)null);

				schemaFields.add(schemaField);

				continue;
			}

			previousFieldNames.add(fieldName);

			Schema.Field designField = _getDesignField(
				fieldName, propertyJsonValue.asJsonObject());

			if (required.contains(fieldName)) {
				designField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");
			}

			schemaFields.add(designField);
		}

		return Schema.createRecord(schemaName, null, null, false, schemaFields);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SchemaBuilder.class);

	private static final JsonFinder _jsonFinder = new JsonFinder();

	private String _endpoint;
	private JsonObject _oasJsonObject;
	private String _operation;

}