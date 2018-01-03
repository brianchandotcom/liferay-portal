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

import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;

import org.talend.daikon.avro.AvroUtils;

/**
 * @author Zoltán Takács
 *
 * Creates (infers) {@link Schema} from data, which is read from data storage
 * This is used in case user specifies dynamic field in Design schema
 */
public class DelimitedStringSchemaInferrer {

	/**
	 * Constructors sets delimiter
	 */
	public DelimitedStringSchemaInferrer(String delimiter) {
		_delimiter = delimiter;
	}

	/**
	 * Creates Runtime schema from incoming data. <br>
	 * Schema is created in following way: <br>
	 * 1. Delimited string is splitted using <code>delimiter</code> to count
	 * number of fields in delimited string <br>
	 * 2. The same number of fields are created for Runtime schema <br>
	 * 3. Field names are {@code "column<Index>"} <br>
	 * 4. Field types are String
	 *
	 * @param delimitedString a line, which was read from file source
	 * @return Runtime avro schema
	 */
	public Schema inferSchema(String delimitedString) {
		String[] fields = delimitedString.split(_delimiter);

		int size = fields.length;

		List<Field> schemaFields = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			Field designField = new Field(
				"column" + i, _STRING_SCHEMA, null, (Object)null);

			schemaFields.add(i, designField);
		}

		Schema schema = Schema.createRecord(
			"Runtime", null, null, false, schemaFields);

		return schema;
	}

	/**
	 * Default schema for dynamic fields are of type String
	 */
	private static final Schema _STRING_SCHEMA = AvroUtils._string();

	/**
	 * Field delimiter which is used in string line
	 */
	private final String _delimiter;

}