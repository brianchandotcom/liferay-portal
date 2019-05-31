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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.avro.NameUtil;

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
	public IndexedRecord convertToAvro(JsonNode resource) {
		IndexedRecord record = new GenericData.Record(getSchema());

		Iterator<Map.Entry<String, JsonNode>> jsonFields = resource.fields();

		// Already used names for the fields

		int i = 0;
		Set<String> normalizedJsonFieldNames = new HashSet<>();
		int pos = -1;

		while (jsonFields.hasNext()) {
			Map.Entry<String, JsonNode> field = jsonFields.next();

			String fieldName = NameUtil.correct(
				field.getKey(), i, normalizedJsonFieldNames);

			normalizedJsonFieldNames.add(fieldName);

			i++;

			for (Schema.Field schemaField : schemaFields) {
				if (fieldName.equals(schemaField.name())) {
					pos = schemaField.pos();

					break;
				}
			}

			if (pos >= 0) {
				JsonNode resourceJsonNode = field.getValue();

				Object value = avroConverters[pos].convertToAvro(
					resourceJsonNode.asText());

				record.put(pos, value);

				pos = -1;
			}
			else if (!_blacklistedJsonLDKeywords.contains(fieldName) &&
					 _log.isDebugEnabled()) {

				_log.debug(
					"{} is not present in the runtime schema. It will be " +
						"ignored.",
					fieldName);
			}
		}

		return record;
	}

	@Override
	public JsonNode convertToDatum(IndexedRecord value) {
		throw new UnsupportedOperationException();
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ResourceNodeConverter.class);

	private static final List<String> _blacklistedJsonLDKeywords =
		Arrays.asList("_context", "_type");

}