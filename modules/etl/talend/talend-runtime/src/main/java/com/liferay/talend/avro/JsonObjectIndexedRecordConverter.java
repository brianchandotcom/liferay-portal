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

import com.liferay.talend.common.json.JsonFinder;
import com.liferay.talend.common.oas.OASException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import javax.xml.bind.DatatypeConverter;

import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.converter.AvroConverter;
import org.talend.daikon.avro.converter.string.StringBooleanConverter;
import org.talend.daikon.avro.converter.string.StringDoubleConverter;
import org.talend.daikon.avro.converter.string.StringFloatConverter;
import org.talend.daikon.avro.converter.string.StringIntConverter;
import org.talend.daikon.avro.converter.string.StringLongConverter;
import org.talend.daikon.avro.converter.string.StringStringConverter;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class JsonObjectIndexedRecordConverter {

	public JsonObjectIndexedRecordConverter(Schema schema) {
		_schema = schema;
	}

	public IndexedRecord toIndexedRecord(JsonObject contentJsonObject) {
		IndexedRecord record = new GenericData.Record(_schema);

		List<Schema.Field> schemaFields = _schema.getFields();

		schemaFields.forEach(
			schemaEntry -> {
				String valueFinderPath = _getValueFinderPath(
					schemaEntry.name());

				JsonValue jsonValue = _jsonFinder.getDescendantJsonValue(
					valueFinderPath, contentJsonObject);

				if (jsonValue == JsonValue.NULL) {
					if (AvroUtils.isNullable(schemaEntry.schema())) {
						if (_logger.isDebugEnabled()) {
							_logger.debug(
								"Ignoring content's absent path {}",
								valueFinderPath);
						}

						return;
					}

					throw new OASException(
						"Missing non-nullable value at " + valueFinderPath);
				}

				Schema fieldSchema = AvroUtils.unwrapIfNullable(
					schemaEntry.schema());

				AvroConverter avroConverter = _converterRegistry.get(
					fieldSchema.getType());

				if (AvroUtils.isSameType(fieldSchema, AvroUtils._boolean())) {
					record.put(schemaEntry.pos(), _asBoolean(jsonValue));
				}
				else if (AvroUtils.isSameType(
							fieldSchema, AvroUtils._bytes())) {

					record.put(
						schemaEntry.pos(),
						avroConverter.convertToAvro(_asText(jsonValue)));
				}
				else if (AvroUtils.isSameType(
							fieldSchema, AvroUtils._double())) {

					record.put(schemaEntry.pos(), _asDouble(jsonValue));
				}
				else if (AvroUtils.isSameType(fieldSchema, AvroUtils._long())) {
					if (fieldSchema.getLogicalType() ==
							LogicalTypes.timestampMillis()) {

						record.put(
							schemaEntry.pos(),
							_asDateTimeInMilliseconds(jsonValue));
					}
					else {
						record.put(schemaEntry.pos(), _asLong(jsonValue));
					}
				}
				else if (AvroUtils.isSameType(fieldSchema, AvroUtils._int())) {
					record.put(schemaEntry.pos(), _asInteger(jsonValue));
				}
				else if (fieldSchema.getType() == Schema.Type.MAP) {
					OASDictionaryConverter oasDictionaryConverter =
						new OASDictionaryConverter(fieldSchema);

					record.put(
						schemaEntry.pos(),
						oasDictionaryConverter.toIndexedRecord(
							jsonValue.asJsonObject()));
				}
				else if (fieldSchema.getType() == Schema.Type.RECORD) {
					JsonObjectIndexedRecordConverter dictionaryConverter =
						new JsonObjectIndexedRecordConverter(fieldSchema);

					record.put(
						schemaEntry.pos(),
						dictionaryConverter.toIndexedRecord(
							jsonValue.asJsonObject()));
				}
				else {
					if (jsonValue instanceof JsonString) {
						record.put(
							schemaEntry.pos(),
							avroConverter.convertToAvro(_asText(jsonValue)));

						return;
					}

					record.put(
						schemaEntry.pos(),
						avroConverter.convertToAvro(jsonValue.toString()));
				}
			});

		return record;
	}

	private Boolean _asBoolean(JsonValue jsonValue) {
		if (jsonValue == JsonValue.TRUE) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	private Long _asDateTimeInMilliseconds(JsonValue jsonValue) {
		String iso8601Date = _asText(jsonValue);

		Calendar calendar = DatatypeConverter.parseDateTime(iso8601Date);

		return calendar.getTimeInMillis();
	}

	private Double _asDouble(JsonValue jsonValue) {
		JsonNumber jsonNumber = _asJsonNumber(jsonValue);

		return jsonNumber.doubleValue();
	}

	private Integer _asInteger(JsonValue jsonValue) {
		JsonNumber jsonNumber = _asJsonNumber(jsonValue);

		return jsonNumber.intValue();
	}

	private JsonNumber _asJsonNumber(JsonValue jsonValue) {
		return (JsonNumber)jsonValue;
	}

	private Long _asLong(JsonValue jsonValue) {
		JsonNumber jsonNumber = _asJsonNumber(jsonValue);

		return jsonNumber.longValue();
	}

	private String _asText(JsonValue jsonValue) {
		JsonString jsonString = (JsonString)jsonValue;

		return jsonString.getString();
	}

	private String _getValueFinderPath(String name) {
		if (name.indexOf("_") == -1) {
			return name;
		}

		return name.replaceFirst("_", ">");
	}

	private static Logger _logger = LoggerFactory.getLogger(
		JsonObjectIndexedRecordConverter.class);

	private static final Map<Schema.Type, AvroConverter> _converterRegistry =
		new HashMap<Schema.Type, AvroConverter>() {
			{
				put(Schema.Type.BOOLEAN, new StringBooleanConverter());
				put(Schema.Type.DOUBLE, new StringDoubleConverter());
				put(Schema.Type.FLOAT, new StringFloatConverter());
				put(Schema.Type.INT, new StringIntConverter());
				put(Schema.Type.LONG, new StringLongConverter());
				put(Schema.Type.STRING, new StringStringConverter());
			}
		};
	private static final JsonFinder _jsonFinder = new JsonFinder();

	private final Schema _schema;

}