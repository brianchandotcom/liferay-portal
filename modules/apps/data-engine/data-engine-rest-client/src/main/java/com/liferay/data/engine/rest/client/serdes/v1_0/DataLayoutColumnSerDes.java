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

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutColumn;
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
public class DataLayoutColumnSerDes {

	public static DataLayoutColumn toDTO(String json) {
		DataLayoutColumnJSONParser dataLayoutColumnJSONParser =
			new DataLayoutColumnJSONParser();

		return dataLayoutColumnJSONParser.parseToDTO(json);
	}

	public static DataLayoutColumn[] toDTOs(String json) {
		DataLayoutColumnJSONParser dataLayoutColumnJSONParser =
			new DataLayoutColumnJSONParser();

		return dataLayoutColumnJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataLayoutColumn dataLayoutColumn) {
		if (dataLayoutColumn == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataLayoutColumn.getColumnSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"columnSize\": ");

			sb.append(dataLayoutColumn.getColumnSize());
		}

		if (dataLayoutColumn.getFieldNames() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldNames\": ");

			sb.append("[");

			for (int i = 0; i < dataLayoutColumn.getFieldNames().length; i++) {
				sb.append("\"");

				sb.append(_escape(dataLayoutColumn.getFieldNames()[i]));

				sb.append("\"");

				if ((i + 1) < dataLayoutColumn.getFieldNames().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataLayoutColumnJSONParser dataLayoutColumnJSONParser =
			new DataLayoutColumnJSONParser();

		return dataLayoutColumnJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DataLayoutColumn dataLayoutColumn) {
		if (dataLayoutColumn == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataLayoutColumn.getColumnSize() == null) {
			map.put("columnSize", null);
		}
		else {
			map.put(
				"columnSize", String.valueOf(dataLayoutColumn.getColumnSize()));
		}

		if (dataLayoutColumn.getFieldNames() == null) {
			map.put("fieldNames", null);
		}
		else {
			map.put(
				"fieldNames", String.valueOf(dataLayoutColumn.getFieldNames()));
		}

		return map;
	}

	public static class DataLayoutColumnJSONParser
		extends BaseJSONParser<DataLayoutColumn> {

		@Override
		protected DataLayoutColumn createDTO() {
			return new DataLayoutColumn();
		}

		@Override
		protected DataLayoutColumn[] createDTOArray(int size) {
			return new DataLayoutColumn[size];
		}

		@Override
		protected void setField(
			DataLayoutColumn dataLayoutColumn, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "columnSize")) {
				if (jsonParserFieldValue != null) {
					dataLayoutColumn.setColumnSize(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldNames")) {
				if (jsonParserFieldValue != null) {
					dataLayoutColumn.setFieldNames(
						toStrings((Object[])jsonParserFieldValue));
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