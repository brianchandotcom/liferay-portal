/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.client.serdes.v1_0;

import com.liferay.mcp.server.rest.client.dto.v1_0.Prerequisite;
import com.liferay.mcp.server.rest.client.dto.v1_0.ToolSearchResult;
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
public class ToolSearchResultSerDes {

	public static ToolSearchResult toDTO(String json) {
		ToolSearchResultJSONParser toolSearchResultJSONParser =
			new ToolSearchResultJSONParser();

		return toolSearchResultJSONParser.parseToDTO(json);
	}

	public static ToolSearchResult[] toDTOs(String json) {
		ToolSearchResultJSONParser toolSearchResultJSONParser =
			new ToolSearchResultJSONParser();

		return toolSearchResultJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ToolSearchResult toolSearchResult) {
		if (toolSearchResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (toolSearchResult.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(toolSearchResult.getDescription()));

			sb.append("\"");
		}

		if (toolSearchResult.getPrerequisites() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prerequisites\": ");

			sb.append("[");

			for (int i = 0; i < toolSearchResult.getPrerequisites().length;
				 i++) {

				sb.append(
					String.valueOf(toolSearchResult.getPrerequisites()[i]));

				if ((i + 1) < toolSearchResult.getPrerequisites().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (toolSearchResult.getRequiredInputSchema() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"requiredInputSchema\": ");

			sb.append(_toJSON(toolSearchResult.getRequiredInputSchema()));
		}

		if (toolSearchResult.getToolName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"toolName\": ");

			sb.append("\"");

			sb.append(_escape(toolSearchResult.getToolName()));

			sb.append("\"");
		}

		if (toolSearchResult.getToolSetName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"toolSetName\": ");

			sb.append("\"");

			sb.append(_escape(toolSearchResult.getToolSetName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ToolSearchResultJSONParser toolSearchResultJSONParser =
			new ToolSearchResultJSONParser();

		return toolSearchResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ToolSearchResult toolSearchResult) {
		if (toolSearchResult == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (toolSearchResult.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(toolSearchResult.getDescription()));
		}

		if (toolSearchResult.getPrerequisites() == null) {
			map.put("prerequisites", null);
		}
		else {
			map.put(
				"prerequisites",
				String.valueOf(toolSearchResult.getPrerequisites()));
		}

		if (toolSearchResult.getRequiredInputSchema() == null) {
			map.put("requiredInputSchema", null);
		}
		else {
			map.put(
				"requiredInputSchema",
				String.valueOf(toolSearchResult.getRequiredInputSchema()));
		}

		if (toolSearchResult.getToolName() == null) {
			map.put("toolName", null);
		}
		else {
			map.put("toolName", String.valueOf(toolSearchResult.getToolName()));
		}

		if (toolSearchResult.getToolSetName() == null) {
			map.put("toolSetName", null);
		}
		else {
			map.put(
				"toolSetName",
				String.valueOf(toolSearchResult.getToolSetName()));
		}

		return map;
	}

	public static class ToolSearchResultJSONParser
		extends BaseJSONParser<ToolSearchResult> {

		@Override
		protected ToolSearchResult createDTO() {
			return new ToolSearchResult();
		}

		@Override
		protected ToolSearchResult[] createDTOArray(int size) {
			return new ToolSearchResult[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "prerequisites")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "requiredInputSchema")) {

				return true;
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
			ToolSearchResult toolSearchResult, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					toolSearchResult.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "prerequisites")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Prerequisite[] prerequisitesArray =
						new Prerequisite[jsonParserFieldValues.length];

					for (int i = 0; i < prerequisitesArray.length; i++) {
						prerequisitesArray[i] = PrerequisiteSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					toolSearchResult.setPrerequisites(prerequisitesArray);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "requiredInputSchema")) {

				if (jsonParserFieldValue != null) {
					toolSearchResult.setRequiredInputSchema(
						(Map<String, ?>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "toolName")) {
				if (jsonParserFieldValue != null) {
					toolSearchResult.setToolName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "toolSetName")) {
				if (jsonParserFieldValue != null) {
					toolSearchResult.setToolSetName(
						(String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:-581480302