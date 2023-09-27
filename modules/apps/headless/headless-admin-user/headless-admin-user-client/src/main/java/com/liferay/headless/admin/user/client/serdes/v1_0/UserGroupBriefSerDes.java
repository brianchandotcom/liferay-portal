/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.UserGroupBrief;
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
public class UserGroupBriefSerDes {

	public static UserGroupBrief toDTO(String json) {
		UserGroupBriefJSONParser userGroupBriefJSONParser =
			new UserGroupBriefJSONParser();

		return userGroupBriefJSONParser.parseToDTO(json);
	}

	public static UserGroupBrief[] toDTOs(String json) {
		UserGroupBriefJSONParser userGroupBriefJSONParser =
			new UserGroupBriefJSONParser();

		return userGroupBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UserGroupBrief userGroupBrief) {
		if (userGroupBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (userGroupBrief.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(userGroupBrief.getDescription()));

			sb.append("\"");
		}

		if (userGroupBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(userGroupBrief.getId());
		}

		if (userGroupBrief.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(userGroupBrief.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UserGroupBriefJSONParser userGroupBriefJSONParser =
			new UserGroupBriefJSONParser();

		return userGroupBriefJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(UserGroupBrief userGroupBrief) {
		if (userGroupBrief == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (userGroupBrief.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(userGroupBrief.getDescription()));
		}

		if (userGroupBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(userGroupBrief.getId()));
		}

		if (userGroupBrief.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(userGroupBrief.getName()));
		}

		return map;
	}

	public static class UserGroupBriefJSONParser
		extends BaseJSONParser<UserGroupBrief> {

		@Override
		protected UserGroupBrief createDTO() {
			return new UserGroupBrief();
		}

		@Override
		protected UserGroupBrief[] createDTOArray(int size) {
			return new UserGroupBrief[size];
		}

		@Override
		protected void setField(
			UserGroupBrief userGroupBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					userGroupBrief.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					userGroupBrief.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					userGroupBrief.setName((String)jsonParserFieldValue);
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