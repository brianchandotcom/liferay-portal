/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.TextMappedFragmentValue;
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
public class TextMappedFragmentValueSerDes {

	public static TextMappedFragmentValue toDTO(String json) {
		TextMappedFragmentValueJSONParser textMappedFragmentValueJSONParser =
			new TextMappedFragmentValueJSONParser();

		return textMappedFragmentValueJSONParser.parseToDTO(json);
	}

	public static TextMappedFragmentValue[] toDTOs(String json) {
		TextMappedFragmentValueJSONParser textMappedFragmentValueJSONParser =
			new TextMappedFragmentValueJSONParser();

		return textMappedFragmentValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		TextMappedFragmentValue textMappedFragmentValue) {

		if (textMappedFragmentValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (textMappedFragmentValue.getFragmentMappedValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentMappedValue\": ");

			sb.append(
				String.valueOf(
					textMappedFragmentValue.getFragmentMappedValue()));
		}

		if (textMappedFragmentValue.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(textMappedFragmentValue.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TextMappedFragmentValueJSONParser textMappedFragmentValueJSONParser =
			new TextMappedFragmentValueJSONParser();

		return textMappedFragmentValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		TextMappedFragmentValue textMappedFragmentValue) {

		if (textMappedFragmentValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (textMappedFragmentValue.getFragmentMappedValue() == null) {
			map.put("fragmentMappedValue", null);
		}
		else {
			map.put(
				"fragmentMappedValue",
				String.valueOf(
					textMappedFragmentValue.getFragmentMappedValue()));
		}

		if (textMappedFragmentValue.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(textMappedFragmentValue.getType()));
		}

		return map;
	}

	public static class TextMappedFragmentValueJSONParser
		extends BaseJSONParser<TextMappedFragmentValue> {

		@Override
		protected TextMappedFragmentValue createDTO() {
			return new TextMappedFragmentValue();
		}

		@Override
		protected TextMappedFragmentValue[] createDTOArray(int size) {
			return new TextMappedFragmentValue[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "fragmentMappedValue")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			TextMappedFragmentValue textMappedFragmentValue,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fragmentMappedValue")) {
				if (jsonParserFieldValue != null) {
					textMappedFragmentValue.setFragmentMappedValue(
						FragmentMappedValueSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					textMappedFragmentValue.setType(
						TextMappedFragmentValue.Type.create(
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