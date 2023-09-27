/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.DDMOption;
import com.liferay.headless.commerce.delivery.catalog.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class DDMOptionSerDes {

	public static DDMOption toDTO(String json) {
		DDMOptionJSONParser ddmOptionJSONParser = new DDMOptionJSONParser();

		return ddmOptionJSONParser.parseToDTO(json);
	}

	public static DDMOption[] toDTOs(String json) {
		DDMOptionJSONParser ddmOptionJSONParser = new DDMOptionJSONParser();

		return ddmOptionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DDMOption ddmOption) {
		if (ddmOption == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (ddmOption.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(ddmOption.getKey()));

			sb.append("\"");
		}

		if (ddmOption.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(ddmOption.getRequired());
		}

		if (ddmOption.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("[");

			for (int i = 0; i < ddmOption.getValue().length; i++) {
				sb.append("\"");

				sb.append(_escape(ddmOption.getValue()[i]));

				sb.append("\"");

				if ((i + 1) < ddmOption.getValue().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DDMOptionJSONParser ddmOptionJSONParser = new DDMOptionJSONParser();

		return ddmOptionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DDMOption ddmOption) {
		if (ddmOption == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (ddmOption.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(ddmOption.getKey()));
		}

		if (ddmOption.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(ddmOption.getRequired()));
		}

		if (ddmOption.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(ddmOption.getValue()));
		}

		return map;
	}

	public static class DDMOptionJSONParser extends BaseJSONParser<DDMOption> {

		@Override
		protected DDMOption createDTO() {
			return new DDMOption();
		}

		@Override
		protected DDMOption[] createDTOArray(int size) {
			return new DDMOption[size];
		}

		@Override
		protected void setField(
			DDMOption ddmOption, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					ddmOption.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					ddmOption.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					ddmOption.setValue(
						toStrings((Object[])jsonParserFieldValue));
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