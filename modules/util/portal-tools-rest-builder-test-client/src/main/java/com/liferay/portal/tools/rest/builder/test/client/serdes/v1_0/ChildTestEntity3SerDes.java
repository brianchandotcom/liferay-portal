/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.ChildTestEntity3;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.TestEntity;
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
public class ChildTestEntity3SerDes {

	public static ChildTestEntity3 toDTO(String json) {
		ChildTestEntity3JSONParser childTestEntity3JSONParser =
			new ChildTestEntity3JSONParser();

		return childTestEntity3JSONParser.parseToDTO(json);
	}

	public static ChildTestEntity3[] toDTOs(String json) {
		ChildTestEntity3JSONParser childTestEntity3JSONParser =
			new ChildTestEntity3JSONParser();

		return childTestEntity3JSONParser.parseToDTOs(json);
	}

	public static String toJSON(ChildTestEntity3 childTestEntity3) {
		if (childTestEntity3 == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (childTestEntity3.getCreatorId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creatorId\": ");

			sb.append(childTestEntity3.getCreatorId());
		}

		if (childTestEntity3.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			if (childTestEntity3.getCustomFields() instanceof String) {
				sb.append("\"");
				sb.append((String)childTestEntity3.getCustomFields());
				sb.append("\"");
			}
			else {
				sb.append(childTestEntity3.getCustomFields());
			}
		}

		if (childTestEntity3.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					childTestEntity3.getDateCreated()));

			sb.append("\"");
		}

		if (childTestEntity3.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					childTestEntity3.getDateModified()));

			sb.append("\"");
		}

		if (childTestEntity3.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(childTestEntity3.getDescription()));

			sb.append("\"");
		}

		if (childTestEntity3.getDocumentId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentId\": ");

			sb.append(childTestEntity3.getDocumentId());
		}

		if (childTestEntity3.getExpirationDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					childTestEntity3.getExpirationDate()));

			sb.append("\"");
		}

		if (childTestEntity3.getFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"folderId\": ");

			sb.append(childTestEntity3.getFolderId());
		}

		if (childTestEntity3.getFriendlyUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyUrl\": ");

			sb.append("\"");

			sb.append(_escape(childTestEntity3.getFriendlyUrl()));

			sb.append("\"");
		}

		if (childTestEntity3.getGroupId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"groupId\": ");

			sb.append(childTestEntity3.getGroupId());
		}

		if (childTestEntity3.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(childTestEntity3.getId());
		}

		if (childTestEntity3.getJsonProperty() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jsonProperty\": ");

			sb.append("\"");

			sb.append(_escape(childTestEntity3.getJsonProperty()));

			sb.append("\"");
		}

		if (childTestEntity3.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(childTestEntity3.getName()));

			sb.append("\"");
		}

		if (childTestEntity3.getNestedTestEntity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nestedTestEntity\": ");

			sb.append(String.valueOf(childTestEntity3.getNestedTestEntity()));
		}

		if (childTestEntity3.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(childTestEntity3.getPriority());
		}

		if (childTestEntity3.getSelf() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"self\": ");

			sb.append("\"");

			sb.append(_escape(childTestEntity3.getSelf()));

			sb.append("\"");
		}

		if (childTestEntity3.getTestEntities() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testEntities\": ");

			sb.append("[");

			for (int i = 0; i < childTestEntity3.getTestEntities().length;
				 i++) {

				sb.append(
					String.valueOf(childTestEntity3.getTestEntities()[i]));

				if ((i + 1) < childTestEntity3.getTestEntities().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (childTestEntity3.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(childTestEntity3.getTitle()));

			sb.append("\"");
		}

		if (childTestEntity3.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(childTestEntity3.getType());

			sb.append("\"");
		}

		if (childTestEntity3.getViewCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewCount\": ");

			sb.append(childTestEntity3.getViewCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ChildTestEntity3JSONParser childTestEntity3JSONParser =
			new ChildTestEntity3JSONParser();

		return childTestEntity3JSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ChildTestEntity3 childTestEntity3) {
		if (childTestEntity3 == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (childTestEntity3.getCreatorId() == null) {
			map.put("creatorId", null);
		}
		else {
			map.put(
				"creatorId", String.valueOf(childTestEntity3.getCreatorId()));
		}

		if (childTestEntity3.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields",
				String.valueOf(childTestEntity3.getCustomFields()));
		}

		if (childTestEntity3.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					childTestEntity3.getDateCreated()));
		}

		if (childTestEntity3.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					childTestEntity3.getDateModified()));
		}

		if (childTestEntity3.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(childTestEntity3.getDescription()));
		}

		if (childTestEntity3.getDocumentId() == null) {
			map.put("documentId", null);
		}
		else {
			map.put(
				"documentId", String.valueOf(childTestEntity3.getDocumentId()));
		}

		if (childTestEntity3.getExpirationDate() == null) {
			map.put("expirationDate", null);
		}
		else {
			map.put(
				"expirationDate",
				liferayToJSONDateFormat.format(
					childTestEntity3.getExpirationDate()));
		}

		if (childTestEntity3.getFolderId() == null) {
			map.put("folderId", null);
		}
		else {
			map.put("folderId", String.valueOf(childTestEntity3.getFolderId()));
		}

		if (childTestEntity3.getFriendlyUrl() == null) {
			map.put("friendlyUrl", null);
		}
		else {
			map.put(
				"friendlyUrl",
				String.valueOf(childTestEntity3.getFriendlyUrl()));
		}

		if (childTestEntity3.getGroupId() == null) {
			map.put("groupId", null);
		}
		else {
			map.put("groupId", String.valueOf(childTestEntity3.getGroupId()));
		}

		if (childTestEntity3.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(childTestEntity3.getId()));
		}

		if (childTestEntity3.getJsonProperty() == null) {
			map.put("jsonProperty", null);
		}
		else {
			map.put(
				"jsonProperty",
				String.valueOf(childTestEntity3.getJsonProperty()));
		}

		if (childTestEntity3.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(childTestEntity3.getName()));
		}

		if (childTestEntity3.getNestedTestEntity() == null) {
			map.put("nestedTestEntity", null);
		}
		else {
			map.put(
				"nestedTestEntity",
				String.valueOf(childTestEntity3.getNestedTestEntity()));
		}

		if (childTestEntity3.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(childTestEntity3.getPriority()));
		}

		if (childTestEntity3.getSelf() == null) {
			map.put("self", null);
		}
		else {
			map.put("self", String.valueOf(childTestEntity3.getSelf()));
		}

		if (childTestEntity3.getTestEntities() == null) {
			map.put("testEntities", null);
		}
		else {
			map.put(
				"testEntities",
				String.valueOf(childTestEntity3.getTestEntities()));
		}

		if (childTestEntity3.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(childTestEntity3.getTitle()));
		}

		if (childTestEntity3.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(childTestEntity3.getType()));
		}

		if (childTestEntity3.getViewCount() == null) {
			map.put("viewCount", null);
		}
		else {
			map.put(
				"viewCount", String.valueOf(childTestEntity3.getViewCount()));
		}

		return map;
	}

	public static class ChildTestEntity3JSONParser
		extends BaseJSONParser<ChildTestEntity3> {

		@Override
		protected ChildTestEntity3 createDTO() {
			return new ChildTestEntity3();
		}

		@Override
		protected ChildTestEntity3[] createDTOArray(int size) {
			return new ChildTestEntity3[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "creatorId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
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
			else if (Objects.equals(jsonParserFieldName, "expirationDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "folderId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrl")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "groupId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "jsonProperty")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "nestedTestEntity")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "self")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "testEntities")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "viewCount")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ChildTestEntity3 childTestEntity3, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creatorId")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setCreatorId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setCustomFields(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documentId")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setDocumentId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expirationDate")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setExpirationDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "folderId")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrl")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setFriendlyUrl(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "groupId")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setGroupId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jsonProperty")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setJsonProperty(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nestedTestEntity")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setNestedTestEntity(
						NestedTestEntitySerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setPriority(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "self")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setSelf((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "testEntities")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					TestEntity[] testEntitiesArray =
						new TestEntity[jsonParserFieldValues.length];

					for (int i = 0; i < testEntitiesArray.length; i++) {
						testEntitiesArray[i] = TestEntitySerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					childTestEntity3.setTestEntities(testEntitiesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setType(
						ChildTestEntity3.Type.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewCount")) {
				if (jsonParserFieldValue != null) {
					childTestEntity3.setViewCount(
						Long.valueOf((String)jsonParserFieldValue));
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