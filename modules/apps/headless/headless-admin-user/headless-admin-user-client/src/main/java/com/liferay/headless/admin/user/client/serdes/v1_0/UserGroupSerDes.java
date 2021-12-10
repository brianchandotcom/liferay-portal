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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.UserGroup;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

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
public class UserGroupSerDes {

	public static UserGroup toDTO(String json) {
		UserGroupJSONParser userGroupJSONParser = new UserGroupJSONParser();

		return userGroupJSONParser.parseToDTO(json);
	}

	public static UserGroup[] toDTOs(String json) {
		UserGroupJSONParser userGroupJSONParser = new UserGroupJSONParser();

		return userGroupJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UserGroup userGroup) {
		if (userGroup == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (userGroup.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(userGroup.getActions()));
		}

		if (userGroup.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(userGroup.getDescription()));

			sb.append("\"");
		}

		if (userGroup.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(userGroup.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (userGroup.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(userGroup.getId());
		}

		if (userGroup.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(userGroup.getName()));

			sb.append("\"");
		}

		if (userGroup.getUsersCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"usersCount\": ");

			sb.append(userGroup.getUsersCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UserGroupJSONParser userGroupJSONParser = new UserGroupJSONParser();

		return userGroupJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(UserGroup userGroup) {
		if (userGroup == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (userGroup.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(userGroup.getActions()));
		}

		if (userGroup.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(userGroup.getDescription()));
		}

		if (userGroup.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(userGroup.getExternalReferenceCode()));
		}

		if (userGroup.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(userGroup.getId()));
		}

		if (userGroup.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(userGroup.getName()));
		}

		if (userGroup.getUsersCount() == null) {
			map.put("usersCount", null);
		}
		else {
			map.put("usersCount", String.valueOf(userGroup.getUsersCount()));
		}

		return map;
	}

	public static class UserGroupJSONParser extends BaseJSONParser<UserGroup> {

		@Override
		protected UserGroup createDTO() {
			return new UserGroup();
		}

		@Override
		protected UserGroup[] createDTOArray(int size) {
			return new UserGroup[size];
		}

		@Override
		protected void setField(
			UserGroup userGroup, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					userGroup.setActions(
						(Map)UserGroupSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					userGroup.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					userGroup.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					userGroup.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					userGroup.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "usersCount")) {
				if (jsonParserFieldValue != null) {
					userGroup.setUsersCount(
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