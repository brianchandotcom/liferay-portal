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

import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

/**
 * @author Igor Beslic
 */
public class OASDictionaryConverter {

	public OASDictionaryConverter(Schema schema) {
		_schema = schema;
	}

	public IndexedRecord toIndexedRecord(JsonObject contentJsonObject) {
		IndexedRecord record = new GenericData.Record(_schema);

		contentJsonObject.forEach(
			(entryKey, entryValue) -> {
				record.put(0, entryKey);
				record.put(1, _asText(entryValue));
			});

		return record;
	}

	private String _asText(JsonValue jsonValue) {
		JsonString jsonString = (JsonString)jsonValue;

		return jsonString.getString();
	}

	private final Schema _schema;

}