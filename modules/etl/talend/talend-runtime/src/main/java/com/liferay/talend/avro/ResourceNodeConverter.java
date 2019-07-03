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

import java.util.Objects;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.converter.AvroConverter;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
@SuppressWarnings("rawtypes")
public class ResourceNodeConverter
	extends BaseConverter<JsonObject, IndexedRecord> {

	public ResourceNodeConverter(Schema schema) {
		super(JsonObject.class, schema);

		initConverters(schema);
	}

	@Override
	@SuppressWarnings("unchecked")
	public IndexedRecord convertToAvro(JsonObject contentJsonObject) {
		IndexedRecord record = new GenericData.Record(getSchema());

		schemaFields.forEach(
			schemaEntry -> {
				String valueFinderPath = _getValueFinderPath(
					schemaEntry.name());

				JsonValue jsonValue = _jsonFinder.getDescendantJsonValue(
					valueFinderPath, contentJsonObject);

				if (jsonValue == JsonValue.NULL) {
					if (AvroUtils.isNullable(schemaEntry.schema())) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Ignoring content's absent path {}",
								valueFinderPath);
						}

						return;
					}

					throw new OASException(
						"Missing non-nullable value at " + valueFinderPath);
				}

				AvroConverter avroConverter = avroConverters[schemaEntry.pos()];
				Schema fieldSchema = AvroUtils.unwrapIfNullable(
					schemaEntry.schema());

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
					record.put(schemaEntry.pos(), _asLong(jsonValue));
				}
				else if (AvroUtils.isSameType(fieldSchema, AvroUtils._int())) {
					record.put(schemaEntry.pos(), _asInteger(jsonValue));
				}
				else if (fieldSchema.getType() == Schema.Type.RECORD) {
					BaseConverter recordConverter = new OASDictionaryConverter(
						fieldSchema);

					if (!Objects.equals("Dictionary", fieldSchema.getName())) {
						recordConverter = new ResourceNodeConverter(
							fieldSchema);
					}

					record.put(
						schemaEntry.pos(),
						recordConverter.convertToAvro(
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

	@Override
	public JsonObject convertToDatum(IndexedRecord value) {
		throw new UnsupportedOperationException();
	}

	private Boolean _asBoolean(JsonValue jsonValue) {
		if (jsonValue == JsonValue.TRUE) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
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

	private static final Logger _log = LoggerFactory.getLogger(
		ResourceNodeConverter.class);

	private static final JsonFinder _jsonFinder = new JsonFinder();

}