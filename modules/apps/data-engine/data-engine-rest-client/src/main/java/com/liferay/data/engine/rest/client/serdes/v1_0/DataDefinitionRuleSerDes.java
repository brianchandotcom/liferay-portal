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

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionRule;
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
public class DataDefinitionRuleSerDes {

	public static DataDefinitionRule toDTO(String json) {
		DataDefinitionRuleJSONParser dataDefinitionRuleJSONParser =
			new DataDefinitionRuleJSONParser();

		return dataDefinitionRuleJSONParser.parseToDTO(json);
	}

	public static DataDefinitionRule[] toDTOs(String json) {
		DataDefinitionRuleJSONParser dataDefinitionRuleJSONParser =
			new DataDefinitionRuleJSONParser();

		return dataDefinitionRuleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataDefinitionRule dataDefinitionRule) {
		if (dataDefinitionRule == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataDefinitionRule.getDataDefinitionFieldNames() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionFieldNames\": ");

			sb.append("[");

			for (int i = 0;
				 i < dataDefinitionRule.getDataDefinitionFieldNames().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(
						dataDefinitionRule.getDataDefinitionFieldNames()[i]));

				sb.append("\"");

				if ((i + 1) <
						dataDefinitionRule.
							getDataDefinitionFieldNames().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataDefinitionRule.getDataDefinitionRuleParameters() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionRuleParameters\": ");

			sb.append(
				_toJSON(dataDefinitionRule.getDataDefinitionRuleParameters()));
		}

		if (dataDefinitionRule.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(dataDefinitionRule.getName()));

			sb.append("\"");
		}

		if (dataDefinitionRule.getRuleType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ruleType\": ");

			sb.append("\"");

			sb.append(_escape(dataDefinitionRule.getRuleType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataDefinitionRuleJSONParser dataDefinitionRuleJSONParser =
			new DataDefinitionRuleJSONParser();

		return dataDefinitionRuleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataDefinitionRule dataDefinitionRule) {

		if (dataDefinitionRule == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataDefinitionRule.getDataDefinitionFieldNames() == null) {
			map.put("dataDefinitionFieldNames", null);
		}
		else {
			map.put(
				"dataDefinitionFieldNames",
				String.valueOf(
					dataDefinitionRule.getDataDefinitionFieldNames()));
		}

		if (dataDefinitionRule.getDataDefinitionRuleParameters() == null) {
			map.put("dataDefinitionRuleParameters", null);
		}
		else {
			map.put(
				"dataDefinitionRuleParameters",
				String.valueOf(
					dataDefinitionRule.getDataDefinitionRuleParameters()));
		}

		if (dataDefinitionRule.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(dataDefinitionRule.getName()));
		}

		if (dataDefinitionRule.getRuleType() == null) {
			map.put("ruleType", null);
		}
		else {
			map.put(
				"ruleType", String.valueOf(dataDefinitionRule.getRuleType()));
		}

		return map;
	}

	public static class DataDefinitionRuleJSONParser
		extends BaseJSONParser<DataDefinitionRule> {

		@Override
		protected DataDefinitionRule createDTO() {
			return new DataDefinitionRule();
		}

		@Override
		protected DataDefinitionRule[] createDTOArray(int size) {
			return new DataDefinitionRule[size];
		}

		@Override
		protected void setField(
			DataDefinitionRule dataDefinitionRule, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "dataDefinitionFieldNames")) {

				if (jsonParserFieldValue != null) {
					dataDefinitionRule.setDataDefinitionFieldNames(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "dataDefinitionRuleParameters")) {

				if (jsonParserFieldValue != null) {
					dataDefinitionRule.setDataDefinitionRuleParameters(
						(Map)DataDefinitionRuleSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionRule.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ruleType")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionRule.setRuleType(
						(String)jsonParserFieldValue);
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