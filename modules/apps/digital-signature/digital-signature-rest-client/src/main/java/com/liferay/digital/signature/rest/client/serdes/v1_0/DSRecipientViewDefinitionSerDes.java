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

package com.liferay.digital.signature.rest.client.serdes.v1_0;

import com.liferay.digital.signature.rest.client.dto.v1_0.DSRecipientViewDefinition;
import com.liferay.digital.signature.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author José Abelenda
 * @generated
 */
@Generated("")
public class DSRecipientViewDefinitionSerDes {

	public static DSRecipientViewDefinition toDTO(String json) {
		DSRecipientViewDefinitionJSONParser
			dsRecipientViewDefinitionJSONParser =
				new DSRecipientViewDefinitionJSONParser();

		return dsRecipientViewDefinitionJSONParser.parseToDTO(json);
	}

	public static DSRecipientViewDefinition[] toDTOs(String json) {
		DSRecipientViewDefinitionJSONParser
			dsRecipientViewDefinitionJSONParser =
				new DSRecipientViewDefinitionJSONParser();

		return dsRecipientViewDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		DSRecipientViewDefinition dsRecipientViewDefinition) {

		if (dsRecipientViewDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dsRecipientViewDefinition.getAuthenticationMethod() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"authenticationMethod\": ");

			sb.append("\"");

			sb.append(
				_escape(dsRecipientViewDefinition.getAuthenticationMethod()));

			sb.append("\"");
		}

		if (dsRecipientViewDefinition.getClientUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientUserId\": ");

			sb.append("\"");

			sb.append(_escape(dsRecipientViewDefinition.getClientUserId()));

			sb.append("\"");
		}

		if (dsRecipientViewDefinition.getEmail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(dsRecipientViewDefinition.getEmail()));

			sb.append("\"");
		}

		if (dsRecipientViewDefinition.getReturnUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"returnUrl\": ");

			sb.append("\"");

			sb.append(_escape(dsRecipientViewDefinition.getReturnUrl()));

			sb.append("\"");
		}

		if (dsRecipientViewDefinition.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(dsRecipientViewDefinition.getUserName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DSRecipientViewDefinitionJSONParser
			dsRecipientViewDefinitionJSONParser =
				new DSRecipientViewDefinitionJSONParser();

		return dsRecipientViewDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DSRecipientViewDefinition dsRecipientViewDefinition) {

		if (dsRecipientViewDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dsRecipientViewDefinition.getAuthenticationMethod() == null) {
			map.put("authenticationMethod", null);
		}
		else {
			map.put(
				"authenticationMethod",
				String.valueOf(
					dsRecipientViewDefinition.getAuthenticationMethod()));
		}

		if (dsRecipientViewDefinition.getClientUserId() == null) {
			map.put("clientUserId", null);
		}
		else {
			map.put(
				"clientUserId",
				String.valueOf(dsRecipientViewDefinition.getClientUserId()));
		}

		if (dsRecipientViewDefinition.getEmail() == null) {
			map.put("email", null);
		}
		else {
			map.put(
				"email", String.valueOf(dsRecipientViewDefinition.getEmail()));
		}

		if (dsRecipientViewDefinition.getReturnUrl() == null) {
			map.put("returnUrl", null);
		}
		else {
			map.put(
				"returnUrl",
				String.valueOf(dsRecipientViewDefinition.getReturnUrl()));
		}

		if (dsRecipientViewDefinition.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put(
				"userName",
				String.valueOf(dsRecipientViewDefinition.getUserName()));
		}

		return map;
	}

	public static class DSRecipientViewDefinitionJSONParser
		extends BaseJSONParser<DSRecipientViewDefinition> {

		@Override
		protected DSRecipientViewDefinition createDTO() {
			return new DSRecipientViewDefinition();
		}

		@Override
		protected DSRecipientViewDefinition[] createDTOArray(int size) {
			return new DSRecipientViewDefinition[size];
		}

		@Override
		protected void setField(
			DSRecipientViewDefinition dsRecipientViewDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "authenticationMethod")) {
				if (jsonParserFieldValue != null) {
					dsRecipientViewDefinition.setAuthenticationMethod(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "clientUserId")) {
				if (jsonParserFieldValue != null) {
					dsRecipientViewDefinition.setClientUserId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					dsRecipientViewDefinition.setEmail(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "returnUrl")) {
				if (jsonParserFieldValue != null) {
					dsRecipientViewDefinition.setReturnUrl(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					dsRecipientViewDefinition.setUserName(
						(String)jsonParserFieldValue);
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