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

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectValidationRule;
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
public class ObjectValidationRuleSerDes {

	public static ObjectValidationRule toDTO(String json) {
		ObjectValidationRuleJSONParser objectValidationRuleJSONParser =
			new ObjectValidationRuleJSONParser();

		return objectValidationRuleJSONParser.parseToDTO(json);
	}

	public static ObjectValidationRule[] toDTOs(String json) {
		ObjectValidationRuleJSONParser objectValidationRuleJSONParser =
			new ObjectValidationRuleJSONParser();

		return objectValidationRuleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectValidationRule objectValidationRule) {
		if (objectValidationRule == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectValidationRule.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(objectValidationRule.getActions()));
		}

		if (objectValidationRule.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(objectValidationRule.getActive());
		}

		if (objectValidationRule.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					objectValidationRule.getDateCreated()));

			sb.append("\"");
		}

		if (objectValidationRule.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					objectValidationRule.getDateModified()));

			sb.append("\"");
		}

		if (objectValidationRule.getEngine() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"engine\": ");

			sb.append("\"");

			sb.append(_escape(objectValidationRule.getEngine()));

			sb.append("\"");
		}

		if (objectValidationRule.getErrorLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorLabel\": ");

			sb.append(_toJSON(objectValidationRule.getErrorLabel()));
		}

		if (objectValidationRule.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectValidationRule.getId());
		}

		if (objectValidationRule.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(objectValidationRule.getName()));
		}

		if (objectValidationRule.getObjectDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectDefinitionId\": ");

			sb.append(objectValidationRule.getObjectDefinitionId());
		}

		if (objectValidationRule.getScript() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"script\": ");

			sb.append("\"");

			sb.append(_escape(objectValidationRule.getScript()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectValidationRuleJSONParser objectValidationRuleJSONParser =
			new ObjectValidationRuleJSONParser();

		return objectValidationRuleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ObjectValidationRule objectValidationRule) {

		if (objectValidationRule == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectValidationRule.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions", String.valueOf(objectValidationRule.getActions()));
		}

		if (objectValidationRule.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(objectValidationRule.getActive()));
		}

		if (objectValidationRule.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					objectValidationRule.getDateCreated()));
		}

		if (objectValidationRule.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					objectValidationRule.getDateModified()));
		}

		if (objectValidationRule.getEngine() == null) {
			map.put("engine", null);
		}
		else {
			map.put("engine", String.valueOf(objectValidationRule.getEngine()));
		}

		if (objectValidationRule.getErrorLabel() == null) {
			map.put("errorLabel", null);
		}
		else {
			map.put(
				"errorLabel",
				String.valueOf(objectValidationRule.getErrorLabel()));
		}

		if (objectValidationRule.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectValidationRule.getId()));
		}

		if (objectValidationRule.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectValidationRule.getName()));
		}

		if (objectValidationRule.getObjectDefinitionId() == null) {
			map.put("objectDefinitionId", null);
		}
		else {
			map.put(
				"objectDefinitionId",
				String.valueOf(objectValidationRule.getObjectDefinitionId()));
		}

		if (objectValidationRule.getScript() == null) {
			map.put("script", null);
		}
		else {
			map.put("script", String.valueOf(objectValidationRule.getScript()));
		}

		return map;
	}

	public static class ObjectValidationRuleJSONParser
		extends BaseJSONParser<ObjectValidationRule> {

		@Override
		protected ObjectValidationRule createDTO() {
			return new ObjectValidationRule();
		}

		@Override
		protected ObjectValidationRule[] createDTOArray(int size) {
			return new ObjectValidationRule[size];
		}

		@Override
		protected void setField(
			ObjectValidationRule objectValidationRule,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setActions(
						(Map)ObjectValidationRuleSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setActive(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "engine")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setEngine(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "errorLabel")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setErrorLabel(
						(Map)ObjectValidationRuleSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setName(
						(Map)ObjectValidationRuleSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectDefinitionId")) {

				if (jsonParserFieldValue != null) {
					objectValidationRule.setObjectDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "script")) {
				if (jsonParserFieldValue != null) {
					objectValidationRule.setScript(
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