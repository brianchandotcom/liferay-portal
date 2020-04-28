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

import com.liferay.data.engine.rest.client.dto.v2_0.DataRule;
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
public class DataRuleSerDes {

	public static DataRule toDTO(String json) {
		DataRuleJSONParser dataRuleJSONParser = new DataRuleJSONParser();

		return dataRuleJSONParser.parseToDTO(json);
	}

	public static DataRule[] toDTOs(String json) {
		DataRuleJSONParser dataRuleJSONParser = new DataRuleJSONParser();

		return dataRuleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataRule dataRule) {
		if (dataRule == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataRule.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append("[");

			for (int i = 0; i < dataRule.getActions().length; i++) {
				sb.append(dataRule.getActions()[i]);

				if ((i + 1) < dataRule.getActions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataRule.getConditions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"conditions\": ");

			sb.append("[");

			for (int i = 0; i < dataRule.getConditions().length; i++) {
				sb.append(dataRule.getConditions()[i]);

				if ((i + 1) < dataRule.getConditions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataRule.getLogicalOperator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"logicalOperator\": ");

			sb.append("\"");

			sb.append(_escape(dataRule.getLogicalOperator()));

			sb.append("\"");
		}

		if (dataRule.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(dataRule.getName()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataRuleJSONParser dataRuleJSONParser = new DataRuleJSONParser();

		return dataRuleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DataRule dataRule) {
		if (dataRule == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataRule.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(dataRule.getActions()));
		}

		if (dataRule.getConditions() == null) {
			map.put("conditions", null);
		}
		else {
			map.put("conditions", String.valueOf(dataRule.getConditions()));
		}

		if (dataRule.getLogicalOperator() == null) {
			map.put("logicalOperator", null);
		}
		else {
			map.put(
				"logicalOperator",
				String.valueOf(dataRule.getLogicalOperator()));
		}

		if (dataRule.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(dataRule.getName()));
		}

		return map;
	}

	public static class DataRuleJSONParser extends BaseJSONParser<DataRule> {

		@Override
		protected DataRule createDTO() {
			return new DataRule();
		}

		@Override
		protected DataRule[] createDTOArray(int size) {
			return new DataRule[size];
		}

		@Override
		protected void setField(
			DataRule dataRule, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					dataRule.setActions((Map[])jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "conditions")) {
				if (jsonParserFieldValue != null) {
					dataRule.setConditions((Map[])jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "logicalOperator")) {
				if (jsonParserFieldValue != null) {
					dataRule.setLogicalOperator((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					dataRule.setName(
						(Map)DataRuleSerDes.toMap(
							(String)jsonParserFieldValue));
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}