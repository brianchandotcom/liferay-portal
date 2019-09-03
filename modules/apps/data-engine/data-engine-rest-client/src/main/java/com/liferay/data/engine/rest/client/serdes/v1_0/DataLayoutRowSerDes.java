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
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutRow;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataLayoutRowSerDes {

	public static DataLayoutRow toDTO(String json) {
		DataLayoutRowJSONParser dataLayoutRowJSONParser =
			new DataLayoutRowJSONParser();

		return dataLayoutRowJSONParser.parseToDTO(json);
	}

	public static DataLayoutRow[] toDTOs(String json) {
		DataLayoutRowJSONParser dataLayoutRowJSONParser =
			new DataLayoutRowJSONParser();

		return dataLayoutRowJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataLayoutRow dataLayoutRow) {
		if (dataLayoutRow == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataLayoutRow.getDataLayoutColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutColumns\": ");

			sb.append("[");

			for (int i = 0; i < dataLayoutRow.getDataLayoutColumns().length;
				 i++) {

				sb.append(
					String.valueOf(dataLayoutRow.getDataLayoutColumns()[i]));

				if ((i + 1) < dataLayoutRow.getDataLayoutColumns().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataLayoutRowJSONParser dataLayoutRowJSONParser =
			new DataLayoutRowJSONParser();

		return dataLayoutRowJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DataLayoutRow dataLayoutRow) {
		if (dataLayoutRow == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataLayoutRow.getDataLayoutColumns() == null) {
			map.put("dataLayoutColumns", null);
		}
		else {
			map.put(
				"dataLayoutColumns",
				String.valueOf(dataLayoutRow.getDataLayoutColumns()));
		}

		return map;
	}

	public static class DataLayoutRowJSONParser
		extends BaseJSONParser<DataLayoutRow> {

		@Override
		protected DataLayoutRow createDTO() {
			return new DataLayoutRow();
		}

		@Override
		protected DataLayoutRow[] createDTOArray(int size) {
			return new DataLayoutRow[size];
		}

		@Override
		protected void setField(
			DataLayoutRow dataLayoutRow, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataLayoutColumns")) {
				if (jsonParserFieldValue != null) {
					dataLayoutRow.setDataLayoutColumns(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataLayoutColumnSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new DataLayoutColumn[size]
						));
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