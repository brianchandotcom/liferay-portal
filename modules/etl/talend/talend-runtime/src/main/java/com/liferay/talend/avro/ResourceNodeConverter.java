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

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.avro.AvroUtils;

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

				if (fieldJsonNode.isMissingNode() && _log.isDebugEnabled()) {
					_log.debug(
						"{} field is not present in the response. It will be " +
							"ignored.",
						name);

					return;
				}

				Schema fieldSchema = AvroUtils.unwrapIfNullable(
					schemaEntry.schema());

				if (AvroUtils.isSameType(fieldSchema, AvroUtils._boolean())) {
					Object value =
						avroConverters[schemaEntry.pos()].convertToAvro(
							fieldJsonNode.asText());

					record.put(schemaEntry.pos(), value);
				}
				else if (AvroUtils.isSameType(
							fieldSchema, AvroUtils._bytes())) {

					Object value =
						avroConverters[schemaEntry.pos()].convertToAvro(
							fieldJsonNode.asText());

					record.put(schemaEntry.pos(), value);
				}
				else if (AvroUtils.isSameType(
							fieldSchema, AvroUtils._double())) {

					Object value =
						avroConverters[schemaEntry.pos()].convertToAvro(
							fieldJsonNode.asText("0"));

					record.put(schemaEntry.pos(), value);
				}
				else if (AvroUtils.isSameType(fieldSchema, AvroUtils._long())) {
					Object value =
						avroConverters[schemaEntry.pos()].convertToAvro(
							fieldJsonNode.asText("0"));

					record.put(schemaEntry.pos(), value);
				}
				else if (AvroUtils.isSameType(fieldSchema, AvroUtils._int())) {
					Object value =
						avroConverters[schemaEntry.pos()].convertToAvro(
							fieldJsonNode.asText("0"));

					record.put(schemaEntry.pos(), value);
				}
				else {
					Object value =
						avroConverters[schemaEntry.pos()].convertToAvro(
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
		if (name.contains("_")) {
			String[] nameParts = name.split("_");

			resourceJsonNode = resourceJsonNode.path(nameParts[0]);

			name = nameParts[1];
		}

		return resourceJsonNode.path(name);
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ResourceNodeConverter.class);

}