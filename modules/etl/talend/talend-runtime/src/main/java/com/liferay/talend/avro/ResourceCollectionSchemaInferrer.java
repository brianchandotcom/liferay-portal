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

import com.liferay.talend.runtime.apio.form.Property;
import com.liferay.talend.runtime.apio.jsonld.ApioApiDocumentation;
import com.liferay.talend.runtime.apio.jsonld.ApioResourceCollection;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.avro.Schema;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.NameUtil;

/**
 * @author Zoltán Takács
 */
public class ResourceCollectionSchemaInferrer {

	/**
	 * Creates Runtime schema from incoming data.
	 *
	 * <p>
	 * Schema is created in following way:
	 * <p>
	 *
	 * <ol>
	 * <li>
	 * Gets the resource fields <code>List<String></code>
	 * </li>
	 * <li>
	 * The same number of fields are created for Runtime schema.
	 * </li>
	 * <li>
	 * Schema.Field names are coming from the resource collection.
	 * </li>
	 * <li>
	 * Schema.Field types are String.
	 * </li>
	 * </ol>
	 *
	 * @return Runtime AVRO schema
	 * @review
	 */
	public static Schema inferSchemaByResourceFields(
			ApioResourceCollection apioResourceCollection)
		throws IOException {

		List<String> fieldNames =
			apioResourceCollection.getResourceElementFieldNames();

		int size = fieldNames.size();

		if (size == 0) {
			throw new IOException(
				"Unable to determine the fields of the selected resource " +
					"because there were no entries for the given resource");
		}

		List<Schema.Field> schemaFields = new ArrayList<>(size);

		Set<String> filedNames = new HashSet<>();

		for (int i = 0; i < size; i++) {
			String fieldName = NameUtil.correct(
				fieldNames.get(i), i, filedNames);

			filedNames.add(fieldName);

			Schema.Field designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
				(Object)null);

			schemaFields.add(i, designField);
		}

		Schema schema = Schema.createRecord(
			"Runtime", null, null, false, schemaFields);

		return schema;
	}

	public static Schema inferSchemaByResourceType(
		ApioApiDocumentation.SupportedClass resourceSupportedClass) {

		List<Property> supportedProperties =
			resourceSupportedClass.getSupportedProperties();

		List<Schema.Field> schemaFields = new ArrayList<>(
			supportedProperties.size() + 1);

		// Already used names for the fields

		Set<String> fieldNames = new HashSet<>();

		_addIdSchemaField(schemaFields, fieldNames);

		int i = 1;

		for (Property supportedProperty : supportedProperties) {
			String fieldName = NameUtil.correct(
				supportedProperty.getName(), i, fieldNames);

			fieldNames.add(fieldName);

			Schema.Field designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
				(Object)null);

			schemaFields.add(i, designField);

			i++;
		}

		Schema schema = Schema.createRecord(
			"Runtime", null, null, false, schemaFields);

		return schema;
	}

	private static void _addIdSchemaField(
		List<Schema.Field> fields, Set<String> names) {

		String safeIdFieldName = "_id";

		names.add(safeIdFieldName);

		Schema.Field designField = new Schema.Field(
			safeIdFieldName, AvroUtils.wrapAsNullable(AvroUtils._string()),
			null, (Object)null);

		// This is the first column in the schema

		fields.add(0, designField);
	}

}