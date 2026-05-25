/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.UserAccount;
import com.liferay.ai.hub.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class UserAccountSerDes {

	public static UserAccount toDTO(String json) {
		UserAccountJSONParser userAccountJSONParser =
			new UserAccountJSONParser();

		return userAccountJSONParser.parseToDTO(json);
	}

	public static UserAccount[] toDTOs(String json) {
		UserAccountJSONParser userAccountJSONParser =
			new UserAccountJSONParser();

		return userAccountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UserAccount userAccount) {
		if (userAccount == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (userAccount.getEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddress\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getEmailAddress()));

			sb.append("\"");
		}

		if (userAccount.getFirstName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstName\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getFirstName()));

			sb.append("\"");
		}

		if (userAccount.getLastName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastName\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getLastName()));

			sb.append("\"");
		}

		if (userAccount.getScreenName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"screenName\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getScreenName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UserAccountJSONParser userAccountJSONParser =
			new UserAccountJSONParser();

		return userAccountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(UserAccount userAccount) {
		if (userAccount == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (userAccount.getEmailAddress() == null) {
			map.put("emailAddress", null);
		}
		else {
			map.put(
				"emailAddress", String.valueOf(userAccount.getEmailAddress()));
		}

		if (userAccount.getFirstName() == null) {
			map.put("firstName", null);
		}
		else {
			map.put("firstName", String.valueOf(userAccount.getFirstName()));
		}

		if (userAccount.getLastName() == null) {
			map.put("lastName", null);
		}
		else {
			map.put("lastName", String.valueOf(userAccount.getLastName()));
		}

		if (userAccount.getScreenName() == null) {
			map.put("screenName", null);
		}
		else {
			map.put("screenName", String.valueOf(userAccount.getScreenName()));
		}

		return map;
	}

	public static class UserAccountJSONParser
		extends BaseJSONParser<UserAccount> {

		@Override
		protected UserAccount createDTO() {
			return new UserAccount();
		}

		@Override
		protected UserAccount[] createDTOArray(int size) {
			return new UserAccount[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "screenName")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			UserAccount userAccount, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				if (jsonParserFieldValue != null) {
					userAccount.setEmailAddress((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				if (jsonParserFieldValue != null) {
					userAccount.setFirstName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				if (jsonParserFieldValue != null) {
					userAccount.setLastName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "screenName")) {
				if (jsonParserFieldValue != null) {
					userAccount.setScreenName((String)jsonParserFieldValue);
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
		if (value == null) {
			return "null";
		}

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
// LIFERAY-REST-BUILDER-HASH:-1843872435