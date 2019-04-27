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

package com.liferay.talend.service;

import com.liferay.talend.openapi.OpenAPIFormat;
import com.liferay.talend.openapi.OpenAPIType;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.talend.sdk.component.api.record.Schema;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

/**
 * @author Zoltán Takács
 * @author Matija Petanjek
 */
@Service
public class TalendService {

	public Schema getTalendSchema(
		JsonObject schemaJsonObject,
		RecordBuilderFactory recordBuilderFactory) {

		Schema.Builder schemaBuilder = recordBuilderFactory.newSchemaBuilder(
			Schema.Type.RECORD);

		JsonArray requiredJsonArray = schemaJsonObject.getJsonArray("required");

		String requiredJSON = requiredJsonArray.toString();

		JsonObject propertiesJsonObject = schemaJsonObject.getJsonObject(
			"properties");

		for (String propertyName : propertiesJsonObject.keySet()) {
			Schema.Entry.Builder entryBuilder =
				recordBuilderFactory.newEntryBuilder();

			entryBuilder.withName(propertyName);

			_addNullable(entryBuilder, propertyName, requiredJSON);

			_addEntryType(
				entryBuilder, propertiesJsonObject.getJsonObject(propertyName));

			schemaBuilder.withEntry(entryBuilder.build());
		}

		return schemaBuilder.build();
	}

	private Schema.Entry.Builder _addEntryType(
		Schema.Entry.Builder entryBuilder, JsonObject propertyJsonObject) {

		String type = propertyJsonObject.getString("type", null);

		if (type == null) {
			if (propertyJsonObject.containsKey("$ref")) {
				entryBuilder.withType(Schema.Type.STRING);
			}
		}
		else if (type.equals("array")) {
			entryBuilder.withType(Schema.Type.STRING);
		}
		else if (type.equals("object")) {
			entryBuilder.withType(Schema.Type.STRING);
		}
		else {
			_addJavaTypeRecordEntry(entryBuilder, propertyJsonObject);
		}

		return entryBuilder;
	}

	private Schema.Entry.Builder _addJavaTypeRecordEntry(
		Schema.Entry.Builder entryBuilder, JsonObject propertyJsonObject) {

		OpenAPIType openAPIType = OpenAPIType.fromDefinition(
			propertyJsonObject.getString("type"));

		OpenAPIFormat openAPIFormat = OpenAPIFormat.fromOpenAPITypeAndFormat(
			openAPIType, propertyJsonObject.getString("format", null));

		if (openAPIFormat == OpenAPIFormat.BOOLEAN) {
			entryBuilder.withType(Schema.Type.BOOLEAN);
		}
		else if (openAPIFormat == OpenAPIFormat.BINARY) {
			entryBuilder.withType(Schema.Type.BYTES);
		}
		else if (openAPIFormat == OpenAPIFormat.DATE) {
			entryBuilder.withType(Schema.Type.DATETIME);
		}
		else if (openAPIFormat == OpenAPIFormat.DATE_TIME) {
			entryBuilder.withType(Schema.Type.DATETIME);
		}
		else if (openAPIFormat == OpenAPIFormat.DOUBLE) {
			entryBuilder.withType(Schema.Type.DOUBLE);
		}
		else if (openAPIFormat == OpenAPIFormat.FLOAT) {
			entryBuilder.withType(Schema.Type.FLOAT);
		}
		else if (openAPIFormat == OpenAPIFormat.INT32) {
			entryBuilder.withType(Schema.Type.INT);
		}
		else if (openAPIFormat == OpenAPIFormat.INT64) {
			entryBuilder.withType(Schema.Type.LONG);
		}
		else if (openAPIFormat == OpenAPIFormat.STRING) {
			entryBuilder.withType(Schema.Type.STRING);
		}

		return entryBuilder;
	}

	private Schema.Entry.Builder _addNullable(
		Schema.Entry.Builder entryBuilder, String propertyName,
		String requiredFields) {

		if (requiredFields.contains("\"" + propertyName + "\"")) {
			entryBuilder.withNullable(false);
		}
		else {
			entryBuilder.withNullable(true);
		}

		return entryBuilder;
	}

}