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

import java.util.Objects;

import org.apache.avro.LogicalType;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.converter.AvroConverter;

/**
 * @author Zoltán Takács
 */
@SuppressWarnings("rawtypes")
public class ResourceNodeConverter
	extends BaseConverter<JsonNode, IndexedRecord> {

	public ResourceNodeConverter(Schema schema) {
		super(JsonNode.class, schema);

		initConverters(schema);
	}

	@Override
	@SuppressWarnings("unchecked")
	public IndexedRecord convertToAvro(JsonNode resourceJsonNode) {
		IndexedRecord record = new GenericData.Record(getSchema());

		schemaFields.forEach(
			schemaEntry -> {
				String name = schemaEntry.name();

				JsonNode fieldJsonNode = _getFieldJsonNode(
					resourceJsonNode, name);

				if (fieldJsonNode.isMissingNode()) {
					if (_log.isDebugEnabled()) {
						_log.debug("Ignoring response's absent field {}", name);
					}

					return;
				}

				AvroConverter avroConverter = avroConverters[schemaEntry.pos()];
				Schema fieldSchema = AvroUtils.unwrapIfNullable(
					schemaEntry.schema());

				if (AvroUtils.isSameType(fieldSchema, AvroUtils._boolean())) {
					Object value = avroConverter.convertToAvro(
						fieldJsonNode.asText());

					record.put(schemaEntry.pos(), value);
				}
				else if (AvroUtils.isSameType(
							fieldSchema, AvroUtils._bytes())) {

					Object value = avroConverter.convertToAvro(
						fieldJsonNode.asText());

					record.put(schemaEntry.pos(), value);
				}
				else if (AvroUtils.isSameType(
							fieldSchema, AvroUtils._double())) {

					Object value = avroConverter.convertToAvro(
						fieldJsonNode.asText("0"));

					record.put(schemaEntry.pos(), value);
				}
				else if (AvroUtils.isSameType(fieldSchema, AvroUtils._long())) {
					Object value = null;

					LogicalType logicalType = fieldSchema.getLogicalType();
					LogicalTypes.TimestampMillis timestampMillis =
						LogicalTypes.timestampMillis();

					if ((logicalType != null) &&
						Objects.equals(
							logicalType.getName(), timestampMillis.getName())) {

						String valueText = fieldJsonNode.asText();

						if ((valueText != null) && !valueText.equals("")) {
							value = avroConverter.convertToAvro(valueText);
						}
					}
					else {
						value = avroConverter.convertToAvro(
							fieldJsonNode.asText("0"));
					}

					record.put(schemaEntry.pos(), value);
				}
				else if (AvroUtils.isSameType(fieldSchema, AvroUtils._int())) {
					Object value = avroConverter.convertToAvro(
						fieldJsonNode.asText("0"));

					record.put(schemaEntry.pos(), value);
				}
				else {
					Object value = avroConverter.convertToAvro(
						fieldJsonNode.toString());

					record.put(schemaEntry.pos(), value);
				}
			});

		return record;
	}

	@Override
	public JsonNode convertToDatum(IndexedRecord value) {
		throw new UnsupportedOperationException();
	}

	private JsonNode _getFieldJsonNode(JsonNode resourceJsonNode, String name) {
		int index = name.indexOf("_");

		if (index != -1) {
			resourceJsonNode = resourceJsonNode.path(name.substring(0, index));

			name = name.substring(index + 1);
		}

		return resourceJsonNode.path(name);
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ResourceNodeConverter.class);

}