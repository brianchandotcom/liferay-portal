/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.ChildTestObject1;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.ChildTestObject2;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.TestObject;
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
public class TestObjectSerDes {

	public static TestObject toDTO(String json) {
		TestObjectJSONParser testObjectJSONParser = new TestObjectJSONParser();

		return testObjectJSONParser.parseToDTO(json);
	}

	public static TestObject[] toDTOs(String json) {
		TestObjectJSONParser testObjectJSONParser = new TestObjectJSONParser();

		return testObjectJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TestObject testObject) {
		if (testObject == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (testObject.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(testObject.getDateCreated()));

			sb.append("\"");
		}

		if (testObject.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(testObject.getDateModified()));

			sb.append("\"");
		}

		if (testObject.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(testObject.getDescription()));

			sb.append("\"");
		}

		if (testObject.getDocumentId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentId\": ");

			sb.append(testObject.getDocumentId());
		}

		if (testObject.getJsonProperty() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jsonProperty\": ");

			sb.append("\"");

			sb.append(_escape(testObject.getJsonProperty()));

			sb.append("\"");
		}

		if (testObject.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(testObject.getName()));

			sb.append("\"");
		}

		if (testObject.getNestedTestObject() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nestedTestObject\": ");

			sb.append(String.valueOf(testObject.getNestedTestObject()));
		}

		if (testObject.getSelf() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"self\": ");

			sb.append("\"");

			sb.append(_escape(testObject.getSelf()));

			sb.append("\"");
		}

		if (testObject.getTestObjects() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testObjects\": ");

			sb.append(String.valueOf(testObject.getTestObjects()));
		}

		if (testObject.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(testObject.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TestObjectJSONParser testObjectJSONParser = new TestObjectJSONParser();

		return testObjectJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(TestObject testObject) {
		if (testObject == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (testObject.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(testObject.getDateCreated()));
		}

		if (testObject.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(testObject.getDateModified()));
		}

		if (testObject.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(testObject.getDescription()));
		}

		if (testObject.getDocumentId() == null) {
			map.put("documentId", null);
		}
		else {
			map.put("documentId", String.valueOf(testObject.getDocumentId()));
		}

		if (testObject.getJsonProperty() == null) {
			map.put("jsonProperty", null);
		}
		else {
			map.put(
				"jsonProperty", String.valueOf(testObject.getJsonProperty()));
		}

		if (testObject.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(testObject.getName()));
		}

		if (testObject.getNestedTestObject() == null) {
			map.put("nestedTestObject", null);
		}
		else {
			map.put(
				"nestedTestObject",
				String.valueOf(testObject.getNestedTestObject()));
		}

		if (testObject.getSelf() == null) {
			map.put("self", null);
		}
		else {
			map.put("self", String.valueOf(testObject.getSelf()));
		}

		if (testObject.getTestObjects() == null) {
			map.put("testObjects", null);
		}
		else {
			map.put("testObjects", String.valueOf(testObject.getTestObjects()));
		}

		if (testObject.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(testObject.getType()));
		}

		return map;
	}

	public static class TestObjectJSONParser
		extends BaseJSONParser<TestObject> {

		@Override
		protected TestObject createDTO() {
			return null;
		}

		@Override
		protected TestObject[] createDTOArray(int size) {
			return new TestObject[size];
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
		public TestObject parseToDTO(String json) {
			Map<String, Object> jsonMap = parseToMap(json);
			Object type = jsonMap.get("type");

			if (type != null) {
				String typeString = type.toString();

				if (typeString.equals("ChildTestObject1")) {
					return ChildTestObject1.toDTO(json);
				}
				else if (typeString.equals("ChildTestObject2")) {
					return ChildTestObject2.toDTO(json);
				}
				else {
					throw new IllegalArgumentException(
						"Unknown type '" + typeString + "'");
				}
			}
			else {
				throw new IllegalArgumentException("Missing type parameter");
			}
		}

		@Override
		protected void setField(
			TestObject testObject, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					testObject.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					testObject.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					testObject.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documentId")) {
				if (jsonParserFieldValue != null) {
					testObject.setDocumentId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jsonProperty")) {
				if (jsonParserFieldValue != null) {
					testObject.setJsonProperty((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					testObject.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nestedTestObject")) {
				if (jsonParserFieldValue != null) {
					testObject.setNestedTestObject(
						NestedTestObjectSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "self")) {
				if (jsonParserFieldValue != null) {
					testObject.setSelf((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "testObjects")) {
				if (jsonParserFieldValue != null) {
					testObject.setTestObjects(
						TestObjectSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					testObject.setType(
						TestObject.Type.create((String)jsonParserFieldValue));
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