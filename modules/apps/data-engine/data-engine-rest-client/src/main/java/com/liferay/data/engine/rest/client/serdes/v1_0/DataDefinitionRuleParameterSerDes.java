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

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionRuleParameter;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionRuleParameterSerDes {

	public static DataDefinitionRuleParameter toDTO(String json) {
		DataDefinitionRuleParameterJSONParser
			dataDefinitionRuleParameterJSONParser =
				new DataDefinitionRuleParameterJSONParser();

		return dataDefinitionRuleParameterJSONParser.parseToDTO(json);
	}

	public static DataDefinitionRuleParameter[] toDTOs(String json) {
		DataDefinitionRuleParameterJSONParser
			dataDefinitionRuleParameterJSONParser =
				new DataDefinitionRuleParameterJSONParser();

		return dataDefinitionRuleParameterJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		DataDefinitionRuleParameter dataDefinitionRuleParameter) {

		if (dataDefinitionRuleParameter == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataDefinitionRuleParameter.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\":");

			sb.append("\"");

			sb.append(_escape(dataDefinitionRuleParameter.getKey()));

			sb.append("\"");
		}

		if (dataDefinitionRuleParameter.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\":");

			sb.append("\"");

			sb.append(_escape(dataDefinitionRuleParameter.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		DataDefinitionRuleParameter dataDefinitionRuleParameter) {

		if (dataDefinitionRuleParameter == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (dataDefinitionRuleParameter.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put(
				"key", String.valueOf(dataDefinitionRuleParameter.getKey()));
		}

		if (dataDefinitionRuleParameter.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put(
				"value",
				String.valueOf(dataDefinitionRuleParameter.getValue()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class DataDefinitionRuleParameterJSONParser
		extends BaseJSONParser<DataDefinitionRuleParameter> {

		@Override
		protected DataDefinitionRuleParameter createDTO() {
			return new DataDefinitionRuleParameter();
		}

		@Override
		protected DataDefinitionRuleParameter[] createDTOArray(int size) {
			return new DataDefinitionRuleParameter[size];
		}

		@Override
		protected void setField(
			DataDefinitionRuleParameter dataDefinitionRuleParameter,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionRuleParameter.setKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionRuleParameter.setValue(
						(Object)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}