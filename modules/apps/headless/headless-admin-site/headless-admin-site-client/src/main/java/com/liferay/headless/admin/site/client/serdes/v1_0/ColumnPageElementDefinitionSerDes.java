/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.ColumnPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.ColumnViewport;
import com.liferay.headless.admin.site.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import jakarta.annotation.Generated;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ColumnPageElementDefinitionSerDes {

	public static ColumnPageElementDefinition toDTO(String json) {
		ColumnPageElementDefinitionJSONParser
			columnPageElementDefinitionJSONParser =
				new ColumnPageElementDefinitionJSONParser();

		return columnPageElementDefinitionJSONParser.parseToDTO(json);
	}

	public static ColumnPageElementDefinition[] toDTOs(String json) {
		ColumnPageElementDefinitionJSONParser
			columnPageElementDefinitionJSONParser =
				new ColumnPageElementDefinitionJSONParser();

		return columnPageElementDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ColumnPageElementDefinition columnPageElementDefinition) {

		if (columnPageElementDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (columnPageElementDefinition.getColumnViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"columnViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < columnPageElementDefinition.getColumnViewports().length;
				 i++) {

				sb.append(
					String.valueOf(
						columnPageElementDefinition.getColumnViewports()[i]));

				if ((i + 1) <
						columnPageElementDefinition.
							getColumnViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (columnPageElementDefinition.getSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(columnPageElementDefinition.getSize());
		}

		if (columnPageElementDefinition.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(columnPageElementDefinition.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ColumnPageElementDefinitionJSONParser
			columnPageElementDefinitionJSONParser =
				new ColumnPageElementDefinitionJSONParser();

		return columnPageElementDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ColumnPageElementDefinition columnPageElementDefinition) {

		if (columnPageElementDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (columnPageElementDefinition.getColumnViewports() == null) {
			map.put("columnViewports", null);
		}
		else {
			map.put(
				"columnViewports",
				String.valueOf(
					columnPageElementDefinition.getColumnViewports()));
		}

		if (columnPageElementDefinition.getSize() == null) {
			map.put("size", null);
		}
		else {
			map.put(
				"size", String.valueOf(columnPageElementDefinition.getSize()));
		}

		if (columnPageElementDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put(
				"type", String.valueOf(columnPageElementDefinition.getType()));
		}

		return map;
	}

	public static class ColumnPageElementDefinitionJSONParser
		extends BaseJSONParser<ColumnPageElementDefinition> {

		@Override
		protected ColumnPageElementDefinition createDTO() {
			return new ColumnPageElementDefinition();
		}

		@Override
		protected ColumnPageElementDefinition[] createDTOArray(int size) {
			return new ColumnPageElementDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "columnViewports")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ColumnPageElementDefinition columnPageElementDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "columnViewports")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ColumnViewport[] columnViewportsArray =
						new ColumnViewport[jsonParserFieldValues.length];

					for (int i = 0; i < columnViewportsArray.length; i++) {
						columnViewportsArray[i] = ColumnViewportSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					columnPageElementDefinition.setColumnViewports(
						columnViewportsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				if (jsonParserFieldValue != null) {
					columnPageElementDefinition.setSize(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					columnPageElementDefinition.setType(
						ColumnPageElementDefinition.Type.create(
							(String)jsonParserFieldValue));
				}
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
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}