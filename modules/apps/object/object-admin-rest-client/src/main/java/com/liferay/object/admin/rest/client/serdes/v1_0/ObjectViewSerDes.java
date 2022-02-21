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

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectViewColumn;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectViewSortColumn;
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
public class ObjectViewSerDes {

	public static ObjectView toDTO(String json) {
		ObjectViewJSONParser objectViewJSONParser = new ObjectViewJSONParser();

		return objectViewJSONParser.parseToDTO(json);
	}

	public static ObjectView[] toDTOs(String json) {
		ObjectViewJSONParser objectViewJSONParser = new ObjectViewJSONParser();

		return objectViewJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectView objectView) {
		if (objectView == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectView.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(objectView.getActions()));
		}

		if (objectView.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(objectView.getDateCreated()));

			sb.append("\"");
		}

		if (objectView.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(objectView.getDateModified()));

			sb.append("\"");
		}

		if (objectView.getDefaultObjectView() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultObjectView\": ");

			sb.append(objectView.getDefaultObjectView());
		}

		if (objectView.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectView.getId());
		}

		if (objectView.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(objectView.getName()));
		}

		if (objectView.getObjectDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectDefinitionId\": ");

			sb.append(objectView.getObjectDefinitionId());
		}

		if (objectView.getObjectViewColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectViewColumns\": ");

			sb.append("[");

			for (int i = 0; i < objectView.getObjectViewColumns().length; i++) {
				sb.append(String.valueOf(objectView.getObjectViewColumns()[i]));

				if ((i + 1) < objectView.getObjectViewColumns().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectView.getObjectViewSortColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectViewSortColumns\": ");

			sb.append("[");

			for (int i = 0; i < objectView.getObjectViewSortColumns().length;
				 i++) {

				sb.append(
					String.valueOf(objectView.getObjectViewSortColumns()[i]));

				if ((i + 1) < objectView.getObjectViewSortColumns().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectViewJSONParser objectViewJSONParser = new ObjectViewJSONParser();

		return objectViewJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectView objectView) {
		if (objectView == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectView.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(objectView.getActions()));
		}

		if (objectView.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(objectView.getDateCreated()));
		}

		if (objectView.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(objectView.getDateModified()));
		}

		if (objectView.getDefaultObjectView() == null) {
			map.put("defaultObjectView", null);
		}
		else {
			map.put(
				"defaultObjectView",
				String.valueOf(objectView.getDefaultObjectView()));
		}

		if (objectView.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectView.getId()));
		}

		if (objectView.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectView.getName()));
		}

		if (objectView.getObjectDefinitionId() == null) {
			map.put("objectDefinitionId", null);
		}
		else {
			map.put(
				"objectDefinitionId",
				String.valueOf(objectView.getObjectDefinitionId()));
		}

		if (objectView.getObjectViewColumns() == null) {
			map.put("objectViewColumns", null);
		}
		else {
			map.put(
				"objectViewColumns",
				String.valueOf(objectView.getObjectViewColumns()));
		}

		if (objectView.getObjectViewSortColumns() == null) {
			map.put("objectViewSortColumns", null);
		}
		else {
			map.put(
				"objectViewSortColumns",
				String.valueOf(objectView.getObjectViewSortColumns()));
		}

		return map;
	}

	public static class ObjectViewJSONParser
		extends BaseJSONParser<ObjectView> {

		@Override
		protected ObjectView createDTO() {
			return new ObjectView();
		}

		@Override
		protected ObjectView[] createDTOArray(int size) {
			return new ObjectView[size];
		}

		@Override
		protected void setField(
			ObjectView objectView, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					objectView.setActions(
						(Map)ObjectViewSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					objectView.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					objectView.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultObjectView")) {
				if (jsonParserFieldValue != null) {
					objectView.setDefaultObjectView(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectView.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectView.setName(
						(Map)ObjectViewSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectDefinitionId")) {

				if (jsonParserFieldValue != null) {
					objectView.setObjectDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectViewColumns")) {
				if (jsonParserFieldValue != null) {
					objectView.setObjectViewColumns(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectViewColumnSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ObjectViewColumn[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectViewSortColumns")) {

				if (jsonParserFieldValue != null) {
					objectView.setObjectViewSortColumns(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectViewSortColumnSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ObjectViewSortColumn[size]
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