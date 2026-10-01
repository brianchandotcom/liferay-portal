/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.ExternalTestEntity3;
import com.liferay.portal.tools.rest.builder.test.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ExternalTestEntity3SerDes {

	public static ExternalTestEntity3 toDTO(String json) {
		ExternalTestEntity3JSONParser externalTestEntity3JSONParser =
			new ExternalTestEntity3JSONParser();

		return externalTestEntity3JSONParser.parseToDTO(json);
	}

	public static ExternalTestEntity3[] toDTOs(String json) {
		ExternalTestEntity3JSONParser externalTestEntity3JSONParser =
			new ExternalTestEntity3JSONParser();

		return externalTestEntity3JSONParser.parseToDTOs(json);
	}

	public static String toJSON(ExternalTestEntity3 externalTestEntity3) {
		if (externalTestEntity3 == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (externalTestEntity3.getProperty3() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"property3\": ");

			sb.append("\"");

			sb.append(_escape(externalTestEntity3.getProperty3()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ExternalTestEntity3JSONParser externalTestEntity3JSONParser =
			new ExternalTestEntity3JSONParser();

		return externalTestEntity3JSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ExternalTestEntity3 externalTestEntity3) {

		if (externalTestEntity3 == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (externalTestEntity3.getProperty3() == null) {
			map.put("property3", null);
		}
		else {
			map.put(
				"property3",
				String.valueOf(externalTestEntity3.getProperty3()));
		}

		return map;
	}

	public static class ExternalTestEntity3JSONParser
		extends BaseJSONParser<ExternalTestEntity3> {

		@Override
		protected ExternalTestEntity3 createDTO() {
			return new ExternalTestEntity3();
		}

		@Override
		protected ExternalTestEntity3[] createDTOArray(int size) {
			return new ExternalTestEntity3[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "property3")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ExternalTestEntity3 externalTestEntity3, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "property3")) {
				if (jsonParserFieldValue != null) {
					externalTestEntity3.setProperty3(
						(String)jsonParserFieldValue);
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

		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			return _toJSON(collection.toArray());
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
// LIFERAY-REST-BUILDER-HASH:2046263651