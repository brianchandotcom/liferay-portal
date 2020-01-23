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

package com.liferay.data.engine.rest.client.serdes.v2_0;

import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionLayout;
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
public class DataDefinitionLayoutSerDes {

	public static DataDefinitionLayout toDTO(String json) {
		DataDefinitionLayoutJSONParser dataDefinitionLayoutJSONParser =
			new DataDefinitionLayoutJSONParser();

		return dataDefinitionLayoutJSONParser.parseToDTO(json);
	}

	public static DataDefinitionLayout[] toDTOs(String json) {
		DataDefinitionLayoutJSONParser dataDefinitionLayoutJSONParser =
			new DataDefinitionLayoutJSONParser();

		return dataDefinitionLayoutJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataDefinitionLayout dataDefinitionLayout) {
		if (dataDefinitionLayout == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataDefinitionLayout.getDataDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinition\": ");

			sb.append(String.valueOf(dataDefinitionLayout.getDataDefinition()));
		}

		if (dataDefinitionLayout.getDataLayout() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayout\": ");

			sb.append(String.valueOf(dataDefinitionLayout.getDataLayout()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataDefinitionLayoutJSONParser dataDefinitionLayoutJSONParser =
			new DataDefinitionLayoutJSONParser();

		return dataDefinitionLayoutJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataDefinitionLayout dataDefinitionLayout) {

		if (dataDefinitionLayout == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataDefinitionLayout.getDataDefinition() == null) {
			map.put("dataDefinition", null);
		}
		else {
			map.put(
				"dataDefinition",
				String.valueOf(dataDefinitionLayout.getDataDefinition()));
		}

		if (dataDefinitionLayout.getDataLayout() == null) {
			map.put("dataLayout", null);
		}
		else {
			map.put(
				"dataLayout",
				String.valueOf(dataDefinitionLayout.getDataLayout()));
		}

		return map;
	}

	public static class DataDefinitionLayoutJSONParser
		extends BaseJSONParser<DataDefinitionLayout> {

		@Override
		protected DataDefinitionLayout createDTO() {
			return new DataDefinitionLayout();
		}

		@Override
		protected DataDefinitionLayout[] createDTOArray(int size) {
			return new DataDefinitionLayout[size];
		}

		@Override
		protected void setField(
			DataDefinitionLayout dataDefinitionLayout,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataDefinition")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionLayout.setDataDefinition(
						DataDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataLayout")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionLayout.setDataLayout(
						DataLayoutSerDes.toDTO((String)jsonParserFieldValue));
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

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
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