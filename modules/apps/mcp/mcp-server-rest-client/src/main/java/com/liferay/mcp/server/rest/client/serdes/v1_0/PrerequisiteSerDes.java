/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.client.serdes.v1_0;

import com.liferay.mcp.server.rest.client.dto.v1_0.Prerequisite;
import com.liferay.mcp.server.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class PrerequisiteSerDes {

	public static Prerequisite toDTO(String json) {
		PrerequisiteJSONParser prerequisiteJSONParser =
			new PrerequisiteJSONParser();

		return prerequisiteJSONParser.parseToDTO(json);
	}

	public static Prerequisite[] toDTOs(String json) {
		PrerequisiteJSONParser prerequisiteJSONParser =
			new PrerequisiteJSONParser();

		return prerequisiteJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Prerequisite prerequisite) {
		if (prerequisite == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (prerequisite.getNote() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"note\": ");

			sb.append("\"");

			sb.append(_escape(prerequisite.getNote()));

			sb.append("\"");
		}

		if (prerequisite.getParameter() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameter\": ");

			sb.append("\"");

			sb.append(_escape(prerequisite.getParameter()));

			sb.append("\"");
		}

		if (prerequisite.getToolName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"toolName\": ");

			sb.append("\"");

			sb.append(_escape(prerequisite.getToolName()));

			sb.append("\"");
		}

		if (prerequisite.getToolSetName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"toolSetName\": ");

			sb.append("\"");

			sb.append(_escape(prerequisite.getToolSetName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PrerequisiteJSONParser prerequisiteJSONParser =
			new PrerequisiteJSONParser();

		return prerequisiteJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Prerequisite prerequisite) {
		if (prerequisite == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (prerequisite.getNote() == null) {
			map.put("note", null);
		}
		else {
			map.put("note", String.valueOf(prerequisite.getNote()));
		}

		if (prerequisite.getParameter() == null) {
			map.put("parameter", null);
		}
		else {
			map.put("parameter", String.valueOf(prerequisite.getParameter()));
		}

		if (prerequisite.getToolName() == null) {
			map.put("toolName", null);
		}
		else {
			map.put("toolName", String.valueOf(prerequisite.getToolName()));
		}

		if (prerequisite.getToolSetName() == null) {
			map.put("toolSetName", null);
		}
		else {
			map.put(
				"toolSetName", String.valueOf(prerequisite.getToolSetName()));
		}

		return map;
	}

	public static class PrerequisiteJSONParser
		extends BaseJSONParser<Prerequisite> {

		@Override
		protected Prerequisite createDTO() {
			return new Prerequisite();
		}

		@Override
		protected Prerequisite[] createDTOArray(int size) {
			return new Prerequisite[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "note")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "parameter")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "toolName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "toolSetName")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Prerequisite prerequisite, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "note")) {
				if (jsonParserFieldValue != null) {
					prerequisite.setNote((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parameter")) {
				if (jsonParserFieldValue != null) {
					prerequisite.setParameter((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "toolName")) {
				if (jsonParserFieldValue != null) {
					prerequisite.setToolName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "toolSetName")) {
				if (jsonParserFieldValue != null) {
					prerequisite.setToolSetName((String)jsonParserFieldValue);
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

		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			return _toJSON(collection.toArray());
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
// LIFERAY-REST-BUILDER-HASH:1047797528