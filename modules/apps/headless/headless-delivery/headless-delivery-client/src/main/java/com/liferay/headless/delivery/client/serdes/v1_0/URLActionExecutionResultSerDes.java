/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.URLActionExecutionResult;
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
public class URLActionExecutionResultSerDes {

	public static URLActionExecutionResult toDTO(String json) {
		URLActionExecutionResultJSONParser urlActionExecutionResultJSONParser =
			new URLActionExecutionResultJSONParser();

		return urlActionExecutionResultJSONParser.parseToDTO(json);
	}

	public static URLActionExecutionResult[] toDTOs(String json) {
		URLActionExecutionResultJSONParser urlActionExecutionResultJSONParser =
			new URLActionExecutionResultJSONParser();

		return urlActionExecutionResultJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		URLActionExecutionResult urlActionExecutionResult) {

		if (urlActionExecutionResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (urlActionExecutionResult.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append(String.valueOf(urlActionExecutionResult.getUrl()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		URLActionExecutionResultJSONParser urlActionExecutionResultJSONParser =
			new URLActionExecutionResultJSONParser();

		return urlActionExecutionResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		URLActionExecutionResult urlActionExecutionResult) {

		if (urlActionExecutionResult == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (urlActionExecutionResult.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(urlActionExecutionResult.getUrl()));
		}

		return map;
	}

	public static class URLActionExecutionResultJSONParser
		extends BaseJSONParser<URLActionExecutionResult> {

		@Override
		protected URLActionExecutionResult createDTO() {
			return new URLActionExecutionResult();
		}

		@Override
		protected URLActionExecutionResult[] createDTOArray(int size) {
			return new URLActionExecutionResult[size];
		}

		@Override
		protected void setField(
			URLActionExecutionResult urlActionExecutionResult,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					urlActionExecutionResult.setUrl(
						FragmentInlineValueSerDes.toDTO(
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