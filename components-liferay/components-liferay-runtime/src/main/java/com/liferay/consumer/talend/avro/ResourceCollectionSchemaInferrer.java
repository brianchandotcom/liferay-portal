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

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
		List<String> fields = _getResourceFields(jsonNode);

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

	/**
	 * Parses the given jsonNode (Resource Collection) and looks for the members
	 * array. If it's located then the array node's first item will be checked
	 * to get the fields of the resource.
	 *
	 * @param jsonNode
	 * @return <code>List<String></code> The names of the resource fields
	 */
	private static List<String> _getResourceFields(JsonNode jsonNode) {
		JsonNode members = jsonNode.findPath("members");

		if (members.isMissingNode()) {
			_log.error("Cannot find the \"members\" ArrayNode!");

			return Collections.<String>emptyList();
		}

		if (!members.isArray() || (members.size() == 0)) {
			_log.error("The \"members\" ArrayNode is empty!");

			return Collections.<String>emptyList();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Size of the \"members\" ArrayNode: {}", members.size());
		}

		List<String> fieldNames = new ArrayList<>();

		JsonNode firstItem = members.get(0);

		Iterator<String> fieldIter = firstItem.fieldNames();

		while (fieldIter.hasNext()) {
			fieldNames.add(fieldIter.next());
		}

		return fieldNames;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		ResourceCollectionSchemaInferrer.class);

	/**
	 * Default schema for dynamic fields are of type String
	 */
	private static final Schema _StringSchema = AvroUtils._string();

}