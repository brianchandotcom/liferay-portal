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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.UserGroupInformation;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class UserGroupInformationSerDes {

	public static UserGroupInformation toDTO(String json) {
		UserGroupInformationJSONParser userGroupInformationJSONParser =
			new UserGroupInformationJSONParser();

		return userGroupInformationJSONParser.parseToDTO(json);
	}

	public static UserGroupInformation[] toDTOs(String json) {
		UserGroupInformationJSONParser userGroupInformationJSONParser =
			new UserGroupInformationJSONParser();

		return userGroupInformationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UserGroupInformation userGroupInformation) {
		if (userGroupInformation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (userGroupInformation.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(userGroupInformation.getId());
		}

		if (userGroupInformation.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(userGroupInformation.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UserGroupInformationJSONParser userGroupInformationJSONParser =
			new UserGroupInformationJSONParser();

		return userGroupInformationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UserGroupInformation userGroupInformation) {

		if (userGroupInformation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (userGroupInformation.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(userGroupInformation.getId()));
		}

		if (userGroupInformation.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(userGroupInformation.getName()));
		}

		return map;
	}

	public static class UserGroupInformationJSONParser
		extends BaseJSONParser<UserGroupInformation> {

		@Override
		protected UserGroupInformation createDTO() {
			return new UserGroupInformation();
		}

		@Override
		protected UserGroupInformation[] createDTOArray(int size) {
			return new UserGroupInformation[size];
		}

		@Override
		protected void setField(
			UserGroupInformation userGroupInformation,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					userGroupInformation.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					userGroupInformation.setName((String)jsonParserFieldValue);
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