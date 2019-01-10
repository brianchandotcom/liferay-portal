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

import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

/**
 * @author Zoltán Takács  Converts data row as List<Object> to {@link
 *         IndexedRecord} using schema to guess value type
 * @review
 */
@SuppressWarnings("rawtypes")
public class ResourceEntityConverter
	extends BaseConverter<List, IndexedRecord> {

	/**
	 * Constructor sets outgoing record schema and {@link List} class as datum
	 * class
	 *
	 * @param schema
	 * @review
	 */
	public ResourceEntityConverter(Schema schema) {
		super(List.class, schema);

		initConverters(schema);
	}

	@Override
	@SuppressWarnings("unchecked")
	public IndexedRecord convertToAvro(List row) {
		IndexedRecord indexedRecord = new GenericData.Record(getSchema());

		for (int i = 0; i < row.size(); i++) {
			Object value = avroConverters[i].convertToAvro(row.get(i));

			indexedRecord.put(i, value);
		}

		return indexedRecord;
	}

	@Override
	public List<Object> convertToDatum(IndexedRecord indexedRecord) {
		throw new UnsupportedOperationException();
	}

}