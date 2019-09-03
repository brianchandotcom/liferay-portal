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

package com.liferay.change.tracking.rest.client.serdes.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.client.dto.v1_0.ProcessUser;
import com.liferay.change.tracking.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class ProcessUserSerDes {

	public static ProcessUser toDTO(String json) {
		ProcessUserJSONParser processUserJSONParser =
			new ProcessUserJSONParser();

		return processUserJSONParser.parseToDTO(json);
	}

	public static ProcessUser[] toDTOs(String json) {
		ProcessUserJSONParser processUserJSONParser =
			new ProcessUserJSONParser();

		return processUserJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ProcessUser processUser) {
		if (processUser == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (processUser.getUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(processUser.getUserId());
		}

		if (processUser.getUserInitials() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userInitials\": ");

			sb.append("\"");

			sb.append(_escape(processUser.getUserInitials()));

			sb.append("\"");
		}

		if (processUser.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(processUser.getUserName()));

			sb.append("\"");
		}

		if (processUser.getUserPortraitURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userPortraitURL\": ");

			sb.append("\"");

			sb.append(_escape(processUser.getUserPortraitURL()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProcessUserJSONParser processUserJSONParser =
			new ProcessUserJSONParser();

		return processUserJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ProcessUser processUser) {
		if (processUser == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (processUser.getUserId() == null) {
			map.put("userId", null);
		}
		else {
			map.put("userId", String.valueOf(processUser.getUserId()));
		}

		if (processUser.getUserInitials() == null) {
			map.put("userInitials", null);
		}
		else {
			map.put(
				"userInitials", String.valueOf(processUser.getUserInitials()));
		}

		if (processUser.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put("userName", String.valueOf(processUser.getUserName()));
		}

		if (processUser.getUserPortraitURL() == null) {
			map.put("userPortraitURL", null);
		}
		else {
			map.put(
				"userPortraitURL",
				String.valueOf(processUser.getUserPortraitURL()));
		}

		return map;
	}

	public static class ProcessUserJSONParser
		extends BaseJSONParser<ProcessUser> {

		@Override
		protected ProcessUser createDTO() {
			return new ProcessUser();
		}

		@Override
		protected ProcessUser[] createDTOArray(int size) {
			return new ProcessUser[size];
		}

		@Override
		protected void setField(
			ProcessUser processUser, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					processUser.setUserId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userInitials")) {
				if (jsonParserFieldValue != null) {
					processUser.setUserInitials((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					processUser.setUserName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userPortraitURL")) {
				if (jsonParserFieldValue != null) {
					processUser.setUserPortraitURL(
						(String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			sb.append("\":");

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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}