/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentElementValue;
import com.liferay.headless.admin.site.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class HTMLFragmentElementValueSerDes {

	public static HTMLFragmentElementValue toDTO(String json) {
		HTMLFragmentElementValueJSONParser htmlFragmentElementValueJSONParser =
			new HTMLFragmentElementValueJSONParser();

		return htmlFragmentElementValueJSONParser.parseToDTO(json);
	}

	public static HTMLFragmentElementValue[] toDTOs(String json) {
		HTMLFragmentElementValueJSONParser htmlFragmentElementValueJSONParser =
			new HTMLFragmentElementValueJSONParser();

		return htmlFragmentElementValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		HTMLFragmentElementValue htmlFragmentElementValue) {

		if (htmlFragmentElementValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (htmlFragmentElementValue.getHtml() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"html\": ");

			if (htmlFragmentElementValue.getHtml() instanceof String) {
				sb.append("\"");
				sb.append((String)htmlFragmentElementValue.getHtml());
				sb.append("\"");
			}
			else {
				sb.append(htmlFragmentElementValue.getHtml());
			}
		}

		if (htmlFragmentElementValue.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(htmlFragmentElementValue.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		HTMLFragmentElementValueJSONParser htmlFragmentElementValueJSONParser =
			new HTMLFragmentElementValueJSONParser();

		return htmlFragmentElementValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		HTMLFragmentElementValue htmlFragmentElementValue) {

		if (htmlFragmentElementValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (htmlFragmentElementValue.getHtml() == null) {
			map.put("html", null);
		}
		else {
			map.put("html", String.valueOf(htmlFragmentElementValue.getHtml()));
		}

		if (htmlFragmentElementValue.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(htmlFragmentElementValue.getType()));
		}

		return map;
	}

	public static class HTMLFragmentElementValueJSONParser
		extends BaseJSONParser<HTMLFragmentElementValue> {

		@Override
		protected HTMLFragmentElementValue createDTO() {
			return new HTMLFragmentElementValue();
		}

		@Override
		protected HTMLFragmentElementValue[] createDTOArray(int size) {
			return new HTMLFragmentElementValue[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "html")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			HTMLFragmentElementValue htmlFragmentElementValue,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "html")) {
				if (jsonParserFieldValue != null) {
					htmlFragmentElementValue.setHtml(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					htmlFragmentElementValue.setType(
						HTMLFragmentElementValue.Type.create(
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