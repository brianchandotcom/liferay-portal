/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.serdes.v1_0;

import com.liferay.headless.admin.fragment.client.dto.v1_0.Dependency;
import com.liferay.headless.admin.fragment.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Collection;
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
public class DependencySerDes {

	public static Dependency toDTO(String json) {
		DependencyJSONParser dependencyJSONParser = new DependencyJSONParser();

		return dependencyJSONParser.parseToDTO(json);
	}

	public static Dependency[] toDTOs(String json) {
		DependencyJSONParser dependencyJSONParser = new DependencyJSONParser();

		return dependencyJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Dependency dependency) {
		if (dependency == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dependency.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(dependency.getType());
			sb.append("\"");
		}

		if (dependency.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(dependency.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DependencyJSONParser dependencyJSONParser = new DependencyJSONParser();

		return dependencyJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Dependency dependency) {
		if (dependency == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dependency.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(dependency.getType()));
		}

		if (dependency.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(dependency.getValue()));
		}

		return map;
	}

	public static class DependencyJSONParser
		extends BaseJSONParser<Dependency> {

		@Override
		protected Dependency createDTO() {
			return new Dependency();
		}

		@Override
		protected Dependency[] createDTOArray(int size) {
			return new Dependency[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Dependency dependency, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					dependency.setType(
						Dependency.Type.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					dependency.setValue((String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:2142628136