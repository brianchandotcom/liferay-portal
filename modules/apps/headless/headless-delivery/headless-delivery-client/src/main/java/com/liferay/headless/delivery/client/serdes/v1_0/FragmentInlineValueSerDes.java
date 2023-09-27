/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentInlineValue;
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
public class FragmentInlineValueSerDes {

	public static FragmentInlineValue toDTO(String json) {
		FragmentInlineValueJSONParser fragmentInlineValueJSONParser =
			new FragmentInlineValueJSONParser();

		return fragmentInlineValueJSONParser.parseToDTO(json);
	}

	public static FragmentInlineValue[] toDTOs(String json) {
		FragmentInlineValueJSONParser fragmentInlineValueJSONParser =
			new FragmentInlineValueJSONParser();

		return fragmentInlineValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentInlineValue fragmentInlineValue) {
		if (fragmentInlineValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentInlineValue.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(fragmentInlineValue.getValue()));

			sb.append("\"");
		}

		if (fragmentInlineValue.getValue_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value_i18n\": ");

			sb.append(_toJSON(fragmentInlineValue.getValue_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentInlineValueJSONParser fragmentInlineValueJSONParser =
			new FragmentInlineValueJSONParser();

		return fragmentInlineValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentInlineValue fragmentInlineValue) {

		if (fragmentInlineValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentInlineValue.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(fragmentInlineValue.getValue()));
		}

		if (fragmentInlineValue.getValue_i18n() == null) {
			map.put("value_i18n", null);
		}
		else {
			map.put(
				"value_i18n",
				String.valueOf(fragmentInlineValue.getValue_i18n()));
		}

		return map;
	}

	public static class FragmentInlineValueJSONParser
		extends BaseJSONParser<FragmentInlineValue> {

		@Override
		protected FragmentInlineValue createDTO() {
			return new FragmentInlineValue();
		}

		@Override
		protected FragmentInlineValue[] createDTOArray(int size) {
			return new FragmentInlineValue[size];
		}

		@Override
		protected void setField(
			FragmentInlineValue fragmentInlineValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					fragmentInlineValue.setValue((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value_i18n")) {
				if (jsonParserFieldValue != null) {
					fragmentInlineValue.setValue_i18n(
						(Map)FragmentInlineValueSerDes.toMap(
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