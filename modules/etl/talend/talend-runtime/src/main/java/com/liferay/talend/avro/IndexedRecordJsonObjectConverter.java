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

import com.liferay.talend.avro.exception.ConverterException;
import com.liferay.talend.common.schema.SchemaUtils;

import java.io.IOException;
import java.io.StringReader;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;

import org.talend.components.api.component.runtime.Result;
import org.talend.daikon.avro.AvroUtils;

/**
 * @author Igor Beslic
 */
public class IndexedRecordJsonObjectConverter extends RejectHandler {

	public IndexedRecordJsonObjectConverter(
		Boolean dieOnError, Schema schema, Schema rejectSchema, Result result) {

		super(dieOnError, new ArrayList<IndexedRecord>(), rejectSchema, result);

		_schema = schema;
	}

	public JsonObject toJsonObject(IndexedRecord indexedRecord)
		throws IOException {

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

		Map<String, JsonObjectBuilder> nestedJsonObjectBuilders =
			new HashMap<>();

		for (Schema.Field field : _schema.getFields()) {
			String fieldName = field.name();

			Schema.Field dataField = SchemaUtils.getField(
				fieldName, indexedRecord.getSchema());

			if ((dataField == null) ||
				(indexedRecord.get(dataField.pos()) == null)) {

				if (!AvroUtils.isNullable(field.schema())) {
					reject(
						indexedRecord,
						new ConverterException(
							"Missing required field " + fieldName));
				}

				continue;
			}

			Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

			JsonObjectBuilder currentJsonObjectBuilder = objectBuilder;

			if (_isNestedFieldName(fieldName)) {
				String[] nameParts = fieldName.split("_");

				if (!nestedJsonObjectBuilders.containsKey(nameParts[0])) {
					nestedJsonObjectBuilders.put(
						nameParts[0], Json.createObjectBuilder());
				}

				currentJsonObjectBuilder = nestedJsonObjectBuilders.get(
					nameParts[0]);

				fieldName = nameParts[1];
			}

			int fieldPos = dataField.pos();

			if (AvroUtils.isSameType(fieldSchema, AvroUtils._boolean())) {
				currentJsonObjectBuilder.add(
					fieldName, (boolean)indexedRecord.get(fieldPos));
			}
			else if (AvroUtils.isSameType(fieldSchema, AvroUtils._bytes())) {
				Base64.Encoder encoder = Base64.getEncoder();

				currentJsonObjectBuilder.add(
					fieldName,
					encoder.encodeToString(
						(byte[])indexedRecord.get(fieldPos)));
			}
			else if (AvroUtils.isSameType(
						fieldSchema, AvroUtils._logicalTimestamp()) ||
					 AvroUtils.isSameType(fieldSchema, AvroUtils._date())) {

				currentJsonObjectBuilder.add(
					fieldName, (Long)indexedRecord.get(fieldPos));
			}
			else if (AvroUtils.isSameType(fieldSchema, AvroUtils._decimal())) {
				currentJsonObjectBuilder.add(
					fieldName, (BigDecimal)indexedRecord.get(fieldPos));
			}
			else if (AvroUtils.isSameType(fieldSchema, AvroUtils._double())) {
				currentJsonObjectBuilder.add(
					fieldName, (double)indexedRecord.get(fieldPos));
			}
			else if (AvroUtils.isSameType(fieldSchema, AvroUtils._float())) {
				currentJsonObjectBuilder.add(
					fieldName, (float)indexedRecord.get(fieldPos));
			}
			else if (AvroUtils.isSameType(fieldSchema, AvroUtils._int())) {
				currentJsonObjectBuilder.add(
					fieldName, (int)indexedRecord.get(fieldPos));
			}
			else if (AvroUtils.isSameType(fieldSchema, AvroUtils._long())) {
				currentJsonObjectBuilder.add(
					fieldName, (long)indexedRecord.get(fieldPos));
			}
			else if (AvroUtils.isSameType(fieldSchema, AvroUtils._string())) {
				String stringFieldValue = (String)indexedRecord.get(fieldPos);

				if (Objects.equals("true", field.getProp("oas.dictionary")) ||
					Objects.equals("Dictionary", fieldSchema.getName()) ||
					_isJsonObjectFormattedString(stringFieldValue) ||
					_isJsonArrayFormattedString(stringFieldValue)) {

					StringReader stringReader = new StringReader(
						stringFieldValue);

					JsonReader jsonReader = Json.createReader(stringReader);

					currentJsonObjectBuilder.add(
						fieldName, jsonReader.readValue());

					jsonReader.close();

					continue;
				}

				currentJsonObjectBuilder.add(fieldName, stringFieldValue);
			}
			else {
				reject(
					indexedRecord,
					new ConverterException(
						String.format(
							"Unable to convert field %s of type %s", fieldName,
							fieldSchema.getType())));
			}
		}

		for (Map.Entry<String, JsonObjectBuilder> nestedJsonObjectBuilder :
				nestedJsonObjectBuilders.entrySet()) {

			objectBuilder.add(
				nestedJsonObjectBuilder.getKey(),
				nestedJsonObjectBuilder.getValue());
		}

		return objectBuilder.build();
	}

	private boolean _isJsonArrayFormattedString(String value) {
		if (value.startsWith("[") && value.endsWith("]")) {
			return true;
		}

		return false;
	}

	private boolean _isJsonObjectFormattedString(String value) {
		if (value.startsWith("{") && value.endsWith("}")) {
			return true;
		}

		return false;
	}

	private boolean _isNestedFieldName(String fieldName) {
		if (fieldName.contains("_")) {
			return true;
		}

		return false;
	}

	private final Schema _schema;

}