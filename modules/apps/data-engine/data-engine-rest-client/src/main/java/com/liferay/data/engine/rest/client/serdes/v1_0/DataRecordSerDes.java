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

package com.liferay.data.engine.rest.client.serdes.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataRecordSerDes {

	public static DataRecord toDTO(String json) {
		DataRecordJSONParser dataRecordJSONParser = new DataRecordJSONParser();

		return dataRecordJSONParser.parseToDTO(json);
	}

	public static DataRecord[] toDTOs(String json) {
		DataRecordJSONParser dataRecordJSONParser = new DataRecordJSONParser();

		return dataRecordJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataRecord dataRecord) {
		if (dataRecord == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataRecord.getDataRecordCollectionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordCollectionId\": ");

			sb.append(dataRecord.getDataRecordCollectionId());
		}

		if (dataRecord.getDataRecordValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordValues\": ");

			sb.append(_toJSON(dataRecord.getDataRecordValues()));
		}

		if (dataRecord.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(dataRecord.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataRecordJSONParser dataRecordJSONParser = new DataRecordJSONParser();

		return dataRecordJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DataRecord dataRecord) {
		if (dataRecord == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataRecord.getDataRecordCollectionId() == null) {
			map.put("dataRecordCollectionId", null);
		}
		else {
			map.put(
				"dataRecordCollectionId",
				String.valueOf(dataRecord.getDataRecordCollectionId()));
		}

		if (dataRecord.getDataRecordValues() == null) {
			map.put("dataRecordValues", null);
		}
		else {
			map.put(
				"dataRecordValues",
				String.valueOf(dataRecord.getDataRecordValues()));
		}

		if (dataRecord.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(dataRecord.getId()));
		}

		return map;
	}

	public static class DataRecordJSONParser
		extends BaseJSONParser<DataRecord> {

		@Override
		protected DataRecord createDTO() {
			return new DataRecord();
		}

		@Override
		protected DataRecord[] createDTOArray(int size) {
			return new DataRecord[size];
		}

		@Override
		protected void setField(
			DataRecord dataRecord, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataRecordCollectionId")) {
				if (jsonParserFieldValue != null) {
					dataRecord.setDataRecordCollectionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataRecordValues")) {
				if (jsonParserFieldValue != null) {
					dataRecord.setDataRecordValues(
						(Map)DataRecordSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					dataRecord.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}