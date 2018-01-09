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

package com.liferay.consumer.talend.avro;

import com.liferay.consumer.talend.runtime.client.ApioJsonLDUtils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.NameUtil;

/**
 * @author Zoltán Takács
 */
public class ResourceCollectionSchemaInferrer {

	/**
	 * Creates Runtime schema from incoming data.
	 * Schema is created in following way:
	 * 1. Gets the resource fields <code>List<String></code>
	 * 2. The same number of fields are created for Runtime schema
	 * 3. Field names are coming from the resource collection
	 * 4. Field types are String
	 *
	 * @param jsonNode - Resource collection JSON node
	 *
	 * @return Runtime AVRO schema
	 */
	public static Schema inferSchema(JsonNode jsonNode) {
		List<String> fields = ApioJsonLDUtils.getResourceFieldNames(jsonNode);

		int size = fields.size();

		List<Field> schemaFields = new ArrayList<>(size);

		Set<String> filedNames = new HashSet<>();

		for (int i = 0; i < size; i++) {
			String fieldName = NameUtil.correct(fields.get(i), i, filedNames);

			filedNames.add(fieldName);

			Field designField = new Field(
				fieldName, _StringSchema, null, (Object)null);

			schemaFields.add(i, designField);
		}

		Schema schema = Schema.createRecord(
			"Runtime", null, null, false, schemaFields);

		return schema;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ResourceCollectionSchemaInferrer.class);

	/**
	 * Default schema for dynamic fields are of type String
	 */
	private static final Schema _StringSchema = AvroUtils._string();

}