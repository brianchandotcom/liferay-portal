/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.NoneActionExecutionResult;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class NoneActionExecutionResultSerDes {

	public static NoneActionExecutionResult toDTO(String json) {
		NoneActionExecutionResultJSONParser
			noneActionExecutionResultJSONParser =
				new NoneActionExecutionResultJSONParser();

		return noneActionExecutionResultJSONParser.parseToDTO(json);
	}

	public static NoneActionExecutionResult[] toDTOs(String json) {
		NoneActionExecutionResultJSONParser
			noneActionExecutionResultJSONParser =
				new NoneActionExecutionResultJSONParser();

		return noneActionExecutionResultJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		NoneActionExecutionResult noneActionExecutionResult) {

		if (noneActionExecutionResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (noneActionExecutionResult.getReload() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reload\": ");

			sb.append(noneActionExecutionResult.getReload());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		NoneActionExecutionResultJSONParser
			noneActionExecutionResultJSONParser =
				new NoneActionExecutionResultJSONParser();

		return noneActionExecutionResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		NoneActionExecutionResult noneActionExecutionResult) {

		if (noneActionExecutionResult == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (noneActionExecutionResult.getReload() == null) {
			map.put("reload", null);
		}
		else {
			map.put(
				"reload",
				String.valueOf(noneActionExecutionResult.getReload()));
		}

		return map;
	}

	public static class NoneActionExecutionResultJSONParser
		extends BaseJSONParser<NoneActionExecutionResult> {

		@Override
		protected NoneActionExecutionResult createDTO() {
			return new NoneActionExecutionResult();
		}

		@Override
		protected NoneActionExecutionResult[] createDTOArray(int size) {
			return new NoneActionExecutionResult[size];
		}

		@Override
		protected void setField(
			NoneActionExecutionResult noneActionExecutionResult,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "reload")) {
				if (jsonParserFieldValue != null) {
					noneActionExecutionResult.setReload(
						(Boolean)jsonParserFieldValue);
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