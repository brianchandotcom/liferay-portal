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

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutColumn;
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
public class ObjectLayoutColumnSerDes {

	public static ObjectLayoutColumn toDTO(String json) {
		ObjectLayoutColumnJSONParser objectLayoutColumnJSONParser =
			new ObjectLayoutColumnJSONParser();

		return objectLayoutColumnJSONParser.parseToDTO(json);
	}

	public static ObjectLayoutColumn[] toDTOs(String json) {
		ObjectLayoutColumnJSONParser objectLayoutColumnJSONParser =
			new ObjectLayoutColumnJSONParser();

		return objectLayoutColumnJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectLayoutColumn objectLayoutColumn) {
		if (objectLayoutColumn == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectLayoutColumn.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectLayoutColumn.getId());
		}

		if (objectLayoutColumn.getObjectFieldId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFieldId\": ");

			sb.append(objectLayoutColumn.getObjectFieldId());
		}

		if (objectLayoutColumn.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(objectLayoutColumn.getPriority());
		}

		if (objectLayoutColumn.getSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(objectLayoutColumn.getSize());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectLayoutColumnJSONParser objectLayoutColumnJSONParser =
			new ObjectLayoutColumnJSONParser();

		return objectLayoutColumnJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ObjectLayoutColumn objectLayoutColumn) {

		if (objectLayoutColumn == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectLayoutColumn.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectLayoutColumn.getId()));
		}

		if (objectLayoutColumn.getObjectFieldId() == null) {
			map.put("objectFieldId", null);
		}
		else {
			map.put(
				"objectFieldId",
				String.valueOf(objectLayoutColumn.getObjectFieldId()));
		}

		if (objectLayoutColumn.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put(
				"priority", String.valueOf(objectLayoutColumn.getPriority()));
		}

		if (objectLayoutColumn.getSize() == null) {
			map.put("size", null);
		}
		else {
			map.put("size", String.valueOf(objectLayoutColumn.getSize()));
		}

		return map;
	}

	public static class ObjectLayoutColumnJSONParser
		extends BaseJSONParser<ObjectLayoutColumn> {

		@Override
		protected ObjectLayoutColumn createDTO() {
			return new ObjectLayoutColumn();
		}

		@Override
		protected ObjectLayoutColumn[] createDTOArray(int size) {
			return new ObjectLayoutColumn[size];
		}

		@Override
		protected void setField(
			ObjectLayoutColumn objectLayoutColumn, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectLayoutColumn.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectFieldId")) {
				if (jsonParserFieldValue != null) {
					objectLayoutColumn.setObjectFieldId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					objectLayoutColumn.setPriority(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				if (jsonParserFieldValue != null) {
					objectLayoutColumn.setSize(
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