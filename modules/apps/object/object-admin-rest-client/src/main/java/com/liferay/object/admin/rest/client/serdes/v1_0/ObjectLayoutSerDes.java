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

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutTab;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class ObjectLayoutSerDes {

	public static ObjectLayout toDTO(String json) {
		ObjectLayoutJSONParser objectLayoutJSONParser =
			new ObjectLayoutJSONParser();

		return objectLayoutJSONParser.parseToDTO(json);
	}

	public static ObjectLayout[] toDTOs(String json) {
		ObjectLayoutJSONParser objectLayoutJSONParser =
			new ObjectLayoutJSONParser();

		return objectLayoutJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectLayout objectLayout) {
		if (objectLayout == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectLayout.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(objectLayout.getActions()));
		}

		if (objectLayout.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(objectLayout.getDateCreated()));

			sb.append("\"");
		}

		if (objectLayout.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(objectLayout.getDateModified()));

			sb.append("\"");
		}

		if (objectLayout.getDefaultObjectLayout() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultObjectLayout\": ");

			sb.append(objectLayout.getDefaultObjectLayout());
		}

		if (objectLayout.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectLayout.getId());
		}

		if (objectLayout.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(objectLayout.getName()));
		}

		if (objectLayout.getObjectDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectDefinitionId\": ");

			sb.append(objectLayout.getObjectDefinitionId());
		}

		if (objectLayout.getObjectLayoutTabs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectLayoutTabs\": ");

			sb.append("[");

			for (int i = 0; i < objectLayout.getObjectLayoutTabs().length;
				 i++) {

				sb.append(
					String.valueOf(objectLayout.getObjectLayoutTabs()[i]));

				if ((i + 1) < objectLayout.getObjectLayoutTabs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectLayoutJSONParser objectLayoutJSONParser =
			new ObjectLayoutJSONParser();

		return objectLayoutJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectLayout objectLayout) {
		if (objectLayout == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectLayout.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(objectLayout.getActions()));
		}

		if (objectLayout.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(objectLayout.getDateCreated()));
		}

		if (objectLayout.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(objectLayout.getDateModified()));
		}

		if (objectLayout.getDefaultObjectLayout() == null) {
			map.put("defaultObjectLayout", null);
		}
		else {
			map.put(
				"defaultObjectLayout",
				String.valueOf(objectLayout.getDefaultObjectLayout()));
		}

		if (objectLayout.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectLayout.getId()));
		}

		if (objectLayout.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectLayout.getName()));
		}

		if (objectLayout.getObjectDefinitionId() == null) {
			map.put("objectDefinitionId", null);
		}
		else {
			map.put(
				"objectDefinitionId",
				String.valueOf(objectLayout.getObjectDefinitionId()));
		}

		if (objectLayout.getObjectLayoutTabs() == null) {
			map.put("objectLayoutTabs", null);
		}
		else {
			map.put(
				"objectLayoutTabs",
				String.valueOf(objectLayout.getObjectLayoutTabs()));
		}

		return map;
	}

	public static class ObjectLayoutJSONParser
		extends BaseJSONParser<ObjectLayout> {

		@Override
		protected ObjectLayout createDTO() {
			return new ObjectLayout();
		}

		@Override
		protected ObjectLayout[] createDTOArray(int size) {
			return new ObjectLayout[size];
		}

		@Override
		protected void setField(
			ObjectLayout objectLayout, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					objectLayout.setActions(
						(Map)ObjectLayoutSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					objectLayout.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					objectLayout.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultObjectLayout")) {

				if (jsonParserFieldValue != null) {
					objectLayout.setDefaultObjectLayout(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectLayout.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectLayout.setName(
						(Map)ObjectLayoutSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectDefinitionId")) {

				if (jsonParserFieldValue != null) {
					objectLayout.setObjectDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectLayoutTabs")) {
				if (jsonParserFieldValue != null) {
					objectLayout.setObjectLayoutTabs(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectLayoutTabSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ObjectLayoutTab[size]
						));
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