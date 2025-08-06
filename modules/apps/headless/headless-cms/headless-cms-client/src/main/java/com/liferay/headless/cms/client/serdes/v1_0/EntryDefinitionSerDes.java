/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.EntryDefinition;
import com.liferay.headless.cms.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class EntryDefinitionSerDes {

	public static EntryDefinition toDTO(String json) {
		EntryDefinitionJSONParser entryDefinitionJSONParser =
			new EntryDefinitionJSONParser();

		return entryDefinitionJSONParser.parseToDTO(json);
	}

	public static EntryDefinition[] toDTOs(String json) {
		EntryDefinitionJSONParser entryDefinitionJSONParser =
			new EntryDefinitionJSONParser();

		return entryDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(EntryDefinition entryDefinition) {
		if (entryDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (entryDefinition.getClassName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"className\": ");

			sb.append("\"");

			sb.append(_escape(entryDefinition.getClassName()));

			sb.append("\"");
		}

		if (entryDefinition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(entryDefinition.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		EntryDefinitionJSONParser entryDefinitionJSONParser =
			new EntryDefinitionJSONParser();

		return entryDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(EntryDefinition entryDefinition) {
		if (entryDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (entryDefinition.getClassName() == null) {
			map.put("className", null);
		}
		else {
			map.put(
				"className", String.valueOf(entryDefinition.getClassName()));
		}

		if (entryDefinition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(entryDefinition.getId()));
		}

		return map;
	}

	public static class EntryDefinitionJSONParser
		extends BaseJSONParser<EntryDefinition> {

		@Override
		protected EntryDefinition createDTO() {
			return new EntryDefinition();
		}

		@Override
		protected EntryDefinition[] createDTOArray(int size) {
			return new EntryDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "className")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			EntryDefinition entryDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "className")) {
				if (jsonParserFieldValue != null) {
					entryDefinition.setClassName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					entryDefinition.setId(
						Long.valueOf((String)jsonParserFieldValue));
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