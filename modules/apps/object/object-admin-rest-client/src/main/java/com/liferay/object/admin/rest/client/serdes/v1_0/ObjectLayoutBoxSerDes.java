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

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutBox;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutRow;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectLayoutBoxSerDes {

	public static ObjectLayoutBox toDTO(String json) {
		ObjectLayoutBoxJSONParser objectLayoutBoxJSONParser =
			new ObjectLayoutBoxJSONParser();

		return objectLayoutBoxJSONParser.parseToDTO(json);
	}

	public static ObjectLayoutBox[] toDTOs(String json) {
		ObjectLayoutBoxJSONParser objectLayoutBoxJSONParser =
			new ObjectLayoutBoxJSONParser();

		return objectLayoutBoxJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectLayoutBox objectLayoutBox) {
		if (objectLayoutBox == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectLayoutBox.getCollapsable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collapsable\": ");

			sb.append(objectLayoutBox.getCollapsable());
		}

		if (objectLayoutBox.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectLayoutBox.getId());
		}

		if (objectLayoutBox.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(objectLayoutBox.getName()));
		}

		if (objectLayoutBox.getObjectLayoutRows() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectLayoutRows\": ");

			sb.append("[");

			for (int i = 0; i < objectLayoutBox.getObjectLayoutRows().length;
				 i++) {

				sb.append(
					String.valueOf(objectLayoutBox.getObjectLayoutRows()[i]));

				if ((i + 1) < objectLayoutBox.getObjectLayoutRows().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectLayoutBox.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(objectLayoutBox.getPriority());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectLayoutBoxJSONParser objectLayoutBoxJSONParser =
			new ObjectLayoutBoxJSONParser();

		return objectLayoutBoxJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectLayoutBox objectLayoutBox) {
		if (objectLayoutBox == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectLayoutBox.getCollapsable() == null) {
			map.put("collapsable", null);
		}
		else {
			map.put(
				"collapsable",
				String.valueOf(objectLayoutBox.getCollapsable()));
		}

		if (objectLayoutBox.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectLayoutBox.getId()));
		}

		if (objectLayoutBox.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectLayoutBox.getName()));
		}

		if (objectLayoutBox.getObjectLayoutRows() == null) {
			map.put("objectLayoutRows", null);
		}
		else {
			map.put(
				"objectLayoutRows",
				String.valueOf(objectLayoutBox.getObjectLayoutRows()));
		}

		if (objectLayoutBox.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(objectLayoutBox.getPriority()));
		}

		return map;
	}

	public static class ObjectLayoutBoxJSONParser
		extends BaseJSONParser<ObjectLayoutBox> {

		@Override
		protected ObjectLayoutBox createDTO() {
			return new ObjectLayoutBox();
		}

		@Override
		protected ObjectLayoutBox[] createDTOArray(int size) {
			return new ObjectLayoutBox[size];
		}

		@Override
		protected void setField(
			ObjectLayoutBox objectLayoutBox, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collapsable")) {
				if (jsonParserFieldValue != null) {
					objectLayoutBox.setCollapsable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectLayoutBox.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectLayoutBox.setName(
						(Map)ObjectLayoutBoxSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectLayoutRows")) {
				if (jsonParserFieldValue != null) {
					objectLayoutBox.setObjectLayoutRows(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectLayoutRowSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ObjectLayoutRow[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					objectLayoutBox.setPriority(
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