/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.SecondExternalTestEntity;
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
public class SecondExternalTestEntitySerDes {

	public static SecondExternalTestEntity toDTO(String json) {
		SecondExternalTestEntityJSONParser secondExternalTestEntityJSONParser =
			new SecondExternalTestEntityJSONParser();

		return secondExternalTestEntityJSONParser.parseToDTO(json);
	}

	public static SecondExternalTestEntity[] toDTOs(String json) {
		SecondExternalTestEntityJSONParser secondExternalTestEntityJSONParser =
			new SecondExternalTestEntityJSONParser();

		return secondExternalTestEntityJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		SecondExternalTestEntity secondExternalTestEntity) {

		if (secondExternalTestEntity == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (secondExternalTestEntity.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(secondExternalTestEntity.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SecondExternalTestEntityJSONParser secondExternalTestEntityJSONParser =
			new SecondExternalTestEntityJSONParser();

		return secondExternalTestEntityJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SecondExternalTestEntity secondExternalTestEntity) {

		if (secondExternalTestEntity == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (secondExternalTestEntity.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(secondExternalTestEntity.getName()));
		}

		return map;
	}

	public static class SecondExternalTestEntityJSONParser
		extends BaseJSONParser<SecondExternalTestEntity> {

		@Override
		protected SecondExternalTestEntity createDTO() {
			return new SecondExternalTestEntity();
		}

		@Override
		protected SecondExternalTestEntity[] createDTOArray(int size) {
			return new SecondExternalTestEntity[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			SecondExternalTestEntity secondExternalTestEntity,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					secondExternalTestEntity.setName(
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
// LIFERAY-REST-BUILDER-HASH:1505959331