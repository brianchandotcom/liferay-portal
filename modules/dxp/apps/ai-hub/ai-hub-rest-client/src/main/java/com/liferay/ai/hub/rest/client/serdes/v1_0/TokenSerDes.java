/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.Token;
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
public class TokenSerDes {

	public static Token toDTO(String json) {
		TokenJSONParser tokenJSONParser = new TokenJSONParser();

		return tokenJSONParser.parseToDTO(json);
	}

	public static Token[] toDTOs(String json) {
		TokenJSONParser tokenJSONParser = new TokenJSONParser();

		return tokenJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Token token) {
		if (token == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (token.getAccessToken() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accessToken\": ");

			sb.append("\"");

			sb.append(_escape(token.getAccessToken()));

			sb.append("\"");
		}

		if (token.getScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scope\": ");

			sb.append("\"");

			sb.append(_escape(token.getScope()));

			sb.append("\"");
		}

		if (token.getUserToken() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userToken\": ");

			sb.append("\"");

			sb.append(_escape(token.getUserToken()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TokenJSONParser tokenJSONParser = new TokenJSONParser();

		return tokenJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Token token) {
		if (token == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (token.getAccessToken() == null) {
			map.put("accessToken", null);
		}
		else {
			map.put("accessToken", String.valueOf(token.getAccessToken()));
		}

		if (token.getScope() == null) {
			map.put("scope", null);
		}
		else {
			map.put("scope", String.valueOf(token.getScope()));
		}

		if (token.getUserToken() == null) {
			map.put("userToken", null);
		}
		else {
			map.put("userToken", String.valueOf(token.getUserToken()));
		}

		return map;
	}

	public static class TokenJSONParser extends BaseJSONParser<Token> {

		@Override
		protected Token createDTO() {
			return new Token();
		}

		@Override
		protected Token[] createDTOArray(int size) {
			return new Token[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "accessToken")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "scope")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "userToken")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Token token, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accessToken")) {
				if (jsonParserFieldValue != null) {
					token.setAccessToken((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "scope")) {
				if (jsonParserFieldValue != null) {
					token.setScope((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userToken")) {
				if (jsonParserFieldValue != null) {
					token.setUserToken((String)jsonParserFieldValue);
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