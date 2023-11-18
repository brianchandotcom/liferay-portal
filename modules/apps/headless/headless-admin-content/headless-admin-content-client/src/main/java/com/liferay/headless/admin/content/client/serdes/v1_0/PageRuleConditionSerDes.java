/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.content.client.serdes.v1_0;

import com.liferay.headless.admin.content.client.dto.v1_0.PageRuleCondition;
import com.liferay.headless.admin.content.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageRuleConditionSerDes {

	public static PageRuleCondition toDTO(String json) {
		PageRuleConditionJSONParser pageRuleConditionJSONParser =
			new PageRuleConditionJSONParser();

		return pageRuleConditionJSONParser.parseToDTO(json);
	}

	public static PageRuleCondition[] toDTOs(String json) {
		PageRuleConditionJSONParser pageRuleConditionJSONParser =
			new PageRuleConditionJSONParser();

		return pageRuleConditionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageRuleCondition pageRuleCondition) {
		if (pageRuleCondition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageRuleCondition.getCondition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"condition\": ");

			sb.append("\"");

			sb.append(_escape(pageRuleCondition.getCondition()));

			sb.append("\"");
		}

		if (pageRuleCondition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(pageRuleCondition.getId()));

			sb.append("\"");
		}

		if (pageRuleCondition.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(pageRuleCondition.getType()));

			sb.append("\"");
		}

		if (pageRuleCondition.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(pageRuleCondition.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageRuleConditionJSONParser pageRuleConditionJSONParser =
			new PageRuleConditionJSONParser();

		return pageRuleConditionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageRuleCondition pageRuleCondition) {

		if (pageRuleCondition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageRuleCondition.getCondition() == null) {
			map.put("condition", null);
		}
		else {
			map.put(
				"condition", String.valueOf(pageRuleCondition.getCondition()));
		}

		if (pageRuleCondition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(pageRuleCondition.getId()));
		}

		if (pageRuleCondition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(pageRuleCondition.getType()));
		}

		if (pageRuleCondition.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(pageRuleCondition.getValue()));
		}

		return map;
	}

	public static class PageRuleConditionJSONParser
		extends BaseJSONParser<PageRuleCondition> {

		@Override
		protected PageRuleCondition createDTO() {
			return new PageRuleCondition();
		}

		@Override
		protected PageRuleCondition[] createDTOArray(int size) {
			return new PageRuleCondition[size];
		}

		@Override
		protected void setField(
			PageRuleCondition pageRuleCondition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "condition")) {
				if (jsonParserFieldValue != null) {
					pageRuleCondition.setCondition(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					pageRuleCondition.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					pageRuleCondition.setType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					pageRuleCondition.setValue((String)jsonParserFieldValue);
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}