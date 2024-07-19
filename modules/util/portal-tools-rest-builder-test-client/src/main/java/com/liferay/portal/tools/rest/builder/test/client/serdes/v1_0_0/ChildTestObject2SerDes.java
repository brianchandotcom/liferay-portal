/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.ChildTestObject2;
import com.liferay.portal.tools.rest.builder.test.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ChildTestObject2SerDes {

	public static ChildTestObject2 toDTO(String json) {
		ChildTestObject2JSONParser childTestObject2JSONParser =
			new ChildTestObject2JSONParser();

		return childTestObject2JSONParser.parseToDTO(json);
	}

	public static ChildTestObject2[] toDTOs(String json) {
		ChildTestObject2JSONParser childTestObject2JSONParser =
			new ChildTestObject2JSONParser();

		return childTestObject2JSONParser.parseToDTOs(json);
	}

	public static String toJSON(ChildTestObject2 childTestObject2) {
		if (childTestObject2 == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (childTestObject2.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject2.getText()));

			sb.append("\"");
		}

		if (childTestObject2.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					childTestObject2.getDateCreated()));

			sb.append("\"");
		}

		if (childTestObject2.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					childTestObject2.getDateModified()));

			sb.append("\"");
		}

		if (childTestObject2.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject2.getDescription()));

			sb.append("\"");
		}

		if (childTestObject2.getDocumentId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentId\": ");

			sb.append(childTestObject2.getDocumentId());
		}

		if (childTestObject2.getJsonProperty() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jsonProperty\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject2.getJsonProperty()));

			sb.append("\"");
		}

		if (childTestObject2.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject2.getName()));

			sb.append("\"");
		}

		if (childTestObject2.getNestedTestObject() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nestedTestObject\": ");

			sb.append(String.valueOf(childTestObject2.getNestedTestObject()));
		}

		if (childTestObject2.getSelf() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"self\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject2.getSelf()));

			sb.append("\"");
		}

		if (childTestObject2.getTestObjects() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testObjects\": ");

			sb.append(String.valueOf(childTestObject2.getTestObjects()));
		}

		if (childTestObject2.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(childTestObject2.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ChildTestObject2JSONParser childTestObject2JSONParser =
			new ChildTestObject2JSONParser();

		return childTestObject2JSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ChildTestObject2 childTestObject2) {
		if (childTestObject2 == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (childTestObject2.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(childTestObject2.getText()));
		}

		if (childTestObject2.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					childTestObject2.getDateCreated()));
		}

		if (childTestObject2.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					childTestObject2.getDateModified()));
		}

		if (childTestObject2.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(childTestObject2.getDescription()));
		}

		if (childTestObject2.getDocumentId() == null) {
			map.put("documentId", null);
		}
		else {
			map.put(
				"documentId", String.valueOf(childTestObject2.getDocumentId()));
		}

		if (childTestObject2.getJsonProperty() == null) {
			map.put("jsonProperty", null);
		}
		else {
			map.put(
				"jsonProperty",
				String.valueOf(childTestObject2.getJsonProperty()));
		}

		if (childTestObject2.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(childTestObject2.getName()));
		}

		if (childTestObject2.getNestedTestObject() == null) {
			map.put("nestedTestObject", null);
		}
		else {
			map.put(
				"nestedTestObject",
				String.valueOf(childTestObject2.getNestedTestObject()));
		}

		if (childTestObject2.getSelf() == null) {
			map.put("self", null);
		}
		else {
			map.put("self", String.valueOf(childTestObject2.getSelf()));
		}

		if (childTestObject2.getTestObjects() == null) {
			map.put("testObjects", null);
		}
		else {
			map.put(
				"testObjects",
				String.valueOf(childTestObject2.getTestObjects()));
		}

		if (childTestObject2.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(childTestObject2.getType()));
		}

		return map;
	}

	public static class ChildTestObject2JSONParser
		extends BaseJSONParser<ChildTestObject2> {

		@Override
		protected ChildTestObject2 createDTO() {
			return new ChildTestObject2();
		}

		@Override
		protected ChildTestObject2[] createDTOArray(int size) {
			return new ChildTestObject2[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "text")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "documentId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "jsonProperty")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "nestedTestObject")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "self")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "testObjects")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ChildTestObject2 childTestObject2, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setText((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documentId")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setDocumentId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jsonProperty")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setJsonProperty(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nestedTestObject")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setNestedTestObject(
						NestedTestObjectSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "self")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setSelf((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "testObjects")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setTestObjects(
						TestObjectSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					childTestObject2.setType(
						ChildTestObject2.Type.create(
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