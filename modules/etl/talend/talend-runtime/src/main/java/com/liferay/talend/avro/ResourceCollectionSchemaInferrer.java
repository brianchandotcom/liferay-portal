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

import com.liferay.talend.runtime.apio.jsonld.ApioResourceCollection;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;

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
	 * Field names are coming from the resource collection.
	 * </li>
	 * <li>
	 * Field types are String.
	 * </li>
	 * </ol>
	 *
	 * @return Runtime AVRO schema
	 */
	public static Schema inferSchemaByResourceFields(
			ApioResourceCollection apioJsonLDResource)
		throws IOException {

		List<String> fieldNames =
			apioJsonLDResource.getResourceElementFieldNames();

		int size = fieldNames.size();

		if (size == 0) {
			throw new IOException(
				"Unable to determine the fields of the selected resource " +
					"because there were no entries for the given resource");
		}

		List<Field> schemaFields = new ArrayList<>(size);

		Set<String> filedNames = new HashSet<>();

		for (int i = 0; i < size; i++) {
			String fieldName = NameUtil.correct(
				fieldNames.get(i), i, filedNames);

			filedNames.add(fieldName);

			Field designField = new Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
				(Object)null);

			schemaFields.add(i, designField);
		}

		Schema schema = Schema.createRecord(
			"Runtime", null, null, false, schemaFields);

		return schema;
	}

}