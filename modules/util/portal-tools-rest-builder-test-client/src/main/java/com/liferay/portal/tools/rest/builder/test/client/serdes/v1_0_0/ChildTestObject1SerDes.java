/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.ChildTestObject1;
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
public class ChildTestObject1SerDes {

	public static ChildTestObject1 toDTO(String json) {
		ChildTestObject1JSONParser childTestObject1JSONParser =
			new ChildTestObject1JSONParser();

		return childTestObject1JSONParser.parseToDTO(json);
	}

	public static ChildTestObject1[] toDTOs(String json) {
		ChildTestObject1JSONParser childTestObject1JSONParser =
			new ChildTestObject1JSONParser();

		return childTestObject1JSONParser.parseToDTOs(json);
	}

	public static String toJSON(ChildTestObject1 childTestObject1) {
		if (childTestObject1 == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (childTestObject1.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					childTestObject1.getDateCreated()));

			sb.append("\"");
		}

		if (childTestObject1.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					childTestObject1.getDateModified()));

			sb.append("\"");
		}

		if (childTestObject1.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject1.getDescription()));

			sb.append("\"");
		}

		if (childTestObject1.getDocumentId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentId\": ");

			sb.append(childTestObject1.getDocumentId());
		}

		if (childTestObject1.getJsonProperty() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jsonProperty\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject1.getJsonProperty()));

			sb.append("\"");
		}

		if (childTestObject1.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject1.getName()));

			sb.append("\"");
		}

		if (childTestObject1.getNestedTestObject() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nestedTestObject\": ");

			sb.append(String.valueOf(childTestObject1.getNestedTestObject()));
		}

		if (childTestObject1.getSelf() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"self\": ");

			sb.append("\"");

			sb.append(_escape(childTestObject1.getSelf()));

			sb.append("\"");
		}

		if (childTestObject1.getTestObjects() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testObjects\": ");

			sb.append(String.valueOf(childTestObject1.getTestObjects()));
		}

		if (childTestObject1.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(childTestObject1.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ChildTestObject1JSONParser childTestObject1JSONParser =
			new ChildTestObject1JSONParser();

		return childTestObject1JSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ChildTestObject1 childTestObject1) {
		if (childTestObject1 == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (childTestObject1.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					childTestObject1.getDateCreated()));
		}

		if (childTestObject1.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					childTestObject1.getDateModified()));
		}

		if (childTestObject1.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(childTestObject1.getDescription()));
		}

		if (childTestObject1.getDocumentId() == null) {
			map.put("documentId", null);
		}
		else {
			map.put(
				"documentId", String.valueOf(childTestObject1.getDocumentId()));
		}

		if (childTestObject1.getJsonProperty() == null) {
			map.put("jsonProperty", null);
		}
		else {
			map.put(
				"jsonProperty",
				String.valueOf(childTestObject1.getJsonProperty()));
		}

		if (childTestObject1.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(childTestObject1.getName()));
		}

		if (childTestObject1.getNestedTestObject() == null) {
			map.put("nestedTestObject", null);
		}
		else {
			map.put(
				"nestedTestObject",
				String.valueOf(childTestObject1.getNestedTestObject()));
		}

		if (childTestObject1.getSelf() == null) {
			map.put("self", null);
		}
		else {
			map.put("self", String.valueOf(childTestObject1.getSelf()));
		}

		if (childTestObject1.getTestObjects() == null) {
			map.put("testObjects", null);
		}
		else {
			map.put(
				"testObjects",
				String.valueOf(childTestObject1.getTestObjects()));
		}

		if (childTestObject1.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(childTestObject1.getType()));
		}

		return map;
	}

	public static class ChildTestObject1JSONParser
		extends BaseJSONParser<ChildTestObject1> {

		@Override
		protected ChildTestObject1 createDTO() {
			return new ChildTestObject1();
		}

		@Override
		protected ChildTestObject1[] createDTOArray(int size) {
			return new ChildTestObject1[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
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
			ChildTestObject1 childTestObject1, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documentId")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setDocumentId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jsonProperty")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setJsonProperty(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nestedTestObject")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setNestedTestObject(
						NestedTestObjectSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "self")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setSelf((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "testObjects")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setTestObjects(
						TestObjectSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					childTestObject1.setType(
						ChildTestObject1.Type.create(
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