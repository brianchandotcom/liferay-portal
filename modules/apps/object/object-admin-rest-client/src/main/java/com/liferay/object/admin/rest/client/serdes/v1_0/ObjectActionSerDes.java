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

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class ObjectActionSerDes {

	public static ObjectAction toDTO(String json) {
		ObjectActionJSONParser objectActionJSONParser =
			new ObjectActionJSONParser();

		return objectActionJSONParser.parseToDTO(json);
	}

	public static ObjectAction[] toDTOs(String json) {
		ObjectActionJSONParser objectActionJSONParser =
			new ObjectActionJSONParser();

		return objectActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectAction objectAction) {
		if (objectAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectAction.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(objectAction.getActions()));
		}

		if (objectAction.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(objectAction.getActive());
		}

		if (objectAction.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(objectAction.getDateCreated()));

			sb.append("\"");
		}

		if (objectAction.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(objectAction.getDateModified()));

			sb.append("\"");
		}

		if (objectAction.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectAction.getId());
		}

		if (objectAction.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(objectAction.getName()));

			sb.append("\"");
		}

		if (objectAction.getObjectActionExecutorKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectActionExecutorKey\": ");

			sb.append("\"");

			sb.append(_escape(objectAction.getObjectActionExecutorKey()));

			sb.append("\"");
		}

		if (objectAction.getObjectActionTriggerKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectActionTriggerKey\": ");

			sb.append("\"");

			sb.append(_escape(objectAction.getObjectActionTriggerKey()));

			sb.append("\"");
		}

		if (objectAction.getParameters() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameters\": ");

			sb.append(_toJSON(objectAction.getParameters()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectActionJSONParser objectActionJSONParser =
			new ObjectActionJSONParser();

		return objectActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectAction objectAction) {
		if (objectAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectAction.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(objectAction.getActions()));
		}

		if (objectAction.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(objectAction.getActive()));
		}

		if (objectAction.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(objectAction.getDateCreated()));
		}

		if (objectAction.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(objectAction.getDateModified()));
		}

		if (objectAction.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectAction.getId()));
		}

		if (objectAction.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectAction.getName()));
		}

		if (objectAction.getObjectActionExecutorKey() == null) {
			map.put("objectActionExecutorKey", null);
		}
		else {
			map.put(
				"objectActionExecutorKey",
				String.valueOf(objectAction.getObjectActionExecutorKey()));
		}

		if (objectAction.getObjectActionTriggerKey() == null) {
			map.put("objectActionTriggerKey", null);
		}
		else {
			map.put(
				"objectActionTriggerKey",
				String.valueOf(objectAction.getObjectActionTriggerKey()));
		}

		if (objectAction.getParameters() == null) {
			map.put("parameters", null);
		}
		else {
			map.put("parameters", String.valueOf(objectAction.getParameters()));
		}

		return map;
	}

	public static class ObjectActionJSONParser
		extends BaseJSONParser<ObjectAction> {

		@Override
		protected ObjectAction createDTO() {
			return new ObjectAction();
		}

		@Override
		protected ObjectAction[] createDTOArray(int size) {
			return new ObjectAction[size];
		}

		@Override
		protected void setField(
			ObjectAction objectAction, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					objectAction.setActions(
						(Map)ObjectActionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					objectAction.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					objectAction.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					objectAction.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectAction.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectAction.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectActionExecutorKey")) {

				if (jsonParserFieldValue != null) {
					objectAction.setObjectActionExecutorKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectActionTriggerKey")) {

				if (jsonParserFieldValue != null) {
					objectAction.setObjectActionTriggerKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parameters")) {
				if (jsonParserFieldValue != null) {
					objectAction.setParameters(
						(Map)ObjectActionSerDes.toMap(
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