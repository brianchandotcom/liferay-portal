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

package com.liferay.talend.common.schema.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;

/**
 * @author Igor Beslic
 */
public class BatchSchemaConstants {

	public static final String FIELD_ENTITY_CLASS_NAME = "entityClassName";

	public static final String FIELD_ENTITY_VERSION = "entityVersion";

	public static final String FIELD_FILE_PATH = "filePath";

	public static final String NAMESPACE = "batch";

	public static final Schema SCHEMA;

	public static final List<Schema.Field> schemaFields =
		Collections.unmodifiableList(
			new ArrayList<Schema.Field>() {
				{
					Schema.Field field = new Schema.Field(
						FIELD_FILE_PATH, AvroUtils._string(), null,
						(Object)null);

					field.addProp(
						SchemaConstants.TALEND_FIELD_GENERATED, "true");
					field.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

					add(field);

					field = new Schema.Field(
						FIELD_ENTITY_CLASS_NAME, AvroUtils._string(), null,
						(Object)null);

					field.addProp(
						SchemaConstants.TALEND_FIELD_GENERATED, "true");
					field.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

					add(field);

					field = new Schema.Field(
						FIELD_ENTITY_VERSION, AvroUtils._string(), null,
						(Object)null);

					field.addProp(
						SchemaConstants.TALEND_FIELD_GENERATED, "true");
					field.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

					add(field);
				}
			});

	static {
		SCHEMA = Schema.createRecord(
			"schema", "Schema for Batch Components Collaboration", NAMESPACE,
			false);

		SCHEMA.setFields(schemaFields);
	}

	public static IndexedRecord asBatchSchemaIndexedRecord(
		String filePath, String entityClass, String entityVersion) {

		IndexedRecord indexedRecord = new GenericData.Record(SCHEMA);

		indexedRecord.put(_getFieldPos(FIELD_FILE_PATH), filePath);
		indexedRecord.put(_getFieldPos(FIELD_ENTITY_CLASS_NAME), entityClass);
		indexedRecord.put(_getFieldPos(FIELD_ENTITY_VERSION), entityVersion);

		return indexedRecord;
	}

	private static int _getFieldPos(String fieldName) {
		Schema.Field field = SCHEMA.getField(fieldName);

		return field.pos();
	}

}