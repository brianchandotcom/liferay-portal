/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.serdes.v1_0_0;

import com.example.sample.client.dto.v1_0_0.PolymorphicSchema;
import com.example.sample.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class PolymorphicSchemaSerDes {

	public static PolymorphicSchema toDTO(String json) {
		PolymorphicSchemaJSONParser polymorphicSchemaJSONParser =
			new PolymorphicSchemaJSONParser();

		return polymorphicSchemaJSONParser.parseToDTO(json);
	}

	public static PolymorphicSchema[] toDTOs(String json) {
		PolymorphicSchemaJSONParser polymorphicSchemaJSONParser =
			new PolymorphicSchemaJSONParser();

		return polymorphicSchemaJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PolymorphicSchema polymorphicSchema) {
		if (polymorphicSchema == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (polymorphicSchema.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(polymorphicSchema.getName()));

			sb.append("\"");
		}

		if (polymorphicSchema.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(polymorphicSchema.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PolymorphicSchemaJSONParser polymorphicSchemaJSONParser =
			new PolymorphicSchemaJSONParser();

		return polymorphicSchemaJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PolymorphicSchema polymorphicSchema) {

		if (polymorphicSchema == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (polymorphicSchema.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(polymorphicSchema.getName()));
		}

		if (polymorphicSchema.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(polymorphicSchema.getType()));
		}

		return map;
	}

	public static class PolymorphicSchemaJSONParser
		extends BaseJSONParser<PolymorphicSchema> {

		@Override
		protected PolymorphicSchema createDTO() {
			return new PolymorphicSchema();
		}

		@Override
		protected PolymorphicSchema[] createDTOArray(int size) {
			return new PolymorphicSchema[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			PolymorphicSchema polymorphicSchema, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					polymorphicSchema.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					polymorphicSchema.setType(
						PolymorphicSchema.Type.create(
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