/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.admin.rest.client.serdes.v1_0;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectViewColumn;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

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
public class ObjectViewColumnSerDes {

	public static ObjectViewColumn toDTO(String json) {
		ObjectViewColumnJSONParser objectViewColumnJSONParser =
			new ObjectViewColumnJSONParser();

		return objectViewColumnJSONParser.parseToDTO(json);
	}

	public static ObjectViewColumn[] toDTOs(String json) {
		ObjectViewColumnJSONParser objectViewColumnJSONParser =
			new ObjectViewColumnJSONParser();

		return objectViewColumnJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectViewColumn objectViewColumn) {
		if (objectViewColumn == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectViewColumn.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectViewColumn.getId());
		}

		if (objectViewColumn.getObjectFieldName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFieldName\": ");

			sb.append("\"");

			sb.append(_escape(objectViewColumn.getObjectFieldName()));

			sb.append("\"");
		}

		if (objectViewColumn.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(objectViewColumn.getPriority());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectViewColumnJSONParser objectViewColumnJSONParser =
			new ObjectViewColumnJSONParser();

		return objectViewColumnJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectViewColumn objectViewColumn) {
		if (objectViewColumn == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectViewColumn.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectViewColumn.getId()));
		}

		if (objectViewColumn.getObjectFieldName() == null) {
			map.put("objectFieldName", null);
		}
		else {
			map.put(
				"objectFieldName",
				String.valueOf(objectViewColumn.getObjectFieldName()));
		}

		if (objectViewColumn.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(objectViewColumn.getPriority()));
		}

		return map;
	}

	public static class ObjectViewColumnJSONParser
		extends BaseJSONParser<ObjectViewColumn> {

		@Override
		protected ObjectViewColumn createDTO() {
			return new ObjectViewColumn();
		}

		@Override
		protected ObjectViewColumn[] createDTOArray(int size) {
			return new ObjectViewColumn[size];
		}

		@Override
		protected void setField(
			ObjectViewColumn objectViewColumn, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectViewColumn.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectFieldName")) {
				if (jsonParserFieldValue != null) {
					objectViewColumn.setObjectFieldName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					objectViewColumn.setPriority(
						Integer.valueOf((String)jsonParserFieldValue));
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