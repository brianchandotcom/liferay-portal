/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.Message;
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
public class MessageSerDes {

	public static Message toDTO(String json) {
		MessageJSONParser messageJSONParser = new MessageJSONParser();

		return messageJSONParser.parseToDTO(json);
	}

	public static Message[] toDTOs(String json) {
		MessageJSONParser messageJSONParser = new MessageJSONParser();

		return messageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Message message) {
		if (message == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (message.getChat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"chat\": ");

			sb.append(String.valueOf(message.getChat()));
		}

		if (message.getChatbotExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"chatbotExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(message.getChatbotExternalReferenceCode()));

			sb.append("\"");
		}

		if (message.getContext() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"context\": ");

			sb.append(_toJSON(message.getContext()));
		}

		if (message.getInstructionDefinitionScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instructionDefinitionScope\": ");

			sb.append("\"");
			sb.append(message.getInstructionDefinitionScope());
			sb.append("\"");
		}

		if (message.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(message.getText()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MessageJSONParser messageJSONParser = new MessageJSONParser();

		return messageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Message message) {
		if (message == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (message.getChat() == null) {
			map.put("chat", null);
		}
		else {
			map.put("chat", String.valueOf(message.getChat()));
		}

		if (message.getChatbotExternalReferenceCode() == null) {
			map.put("chatbotExternalReferenceCode", null);
		}
		else {
			map.put(
				"chatbotExternalReferenceCode",
				String.valueOf(message.getChatbotExternalReferenceCode()));
		}

		if (message.getContext() == null) {
			map.put("context", null);
		}
		else {
			map.put("context", String.valueOf(message.getContext()));
		}

		if (message.getInstructionDefinitionScope() == null) {
			map.put("instructionDefinitionScope", null);
		}
		else {
			map.put(
				"instructionDefinitionScope",
				String.valueOf(message.getInstructionDefinitionScope()));
		}

		if (message.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(message.getText()));
		}

		return map;
	}

	public static class MessageJSONParser extends BaseJSONParser<Message> {

		@Override
		protected Message createDTO() {
			return new Message();
		}

		@Override
		protected Message[] createDTOArray(int size) {
			return new Message[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "chat")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "chatbotExternalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "context")) {
				return true;
			}
			else if (Objects.equals(
						jsonParserFieldName, "instructionDefinitionScope")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Message message, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "chat")) {
				if (jsonParserFieldValue != null) {
					message.setChat(
						ChatSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "chatbotExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					message.setChatbotExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "context")) {
				if (jsonParserFieldValue != null) {
					message.setContext((Map<String, ?>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "instructionDefinitionScope")) {

				if (jsonParserFieldValue != null) {
					message.setInstructionDefinitionScope(
						Message.InstructionDefinitionScope.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					message.setText((String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:-285599610