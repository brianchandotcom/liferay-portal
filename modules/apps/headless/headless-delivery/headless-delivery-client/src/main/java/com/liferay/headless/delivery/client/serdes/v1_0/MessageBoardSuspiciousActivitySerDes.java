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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSuspiciousActivity;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class MessageBoardSuspiciousActivitySerDes {

	public static MessageBoardSuspiciousActivity toDTO(String json) {
		MessageBoardSuspiciousActivityJSONParser
			messageBoardSuspiciousActivityJSONParser =
				new MessageBoardSuspiciousActivityJSONParser();

		return messageBoardSuspiciousActivityJSONParser.parseToDTO(json);
	}

	public static MessageBoardSuspiciousActivity[] toDTOs(String json) {
		MessageBoardSuspiciousActivityJSONParser
			messageBoardSuspiciousActivityJSONParser =
				new MessageBoardSuspiciousActivityJSONParser();

		return messageBoardSuspiciousActivityJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity) {

		if (messageBoardSuspiciousActivity == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (messageBoardSuspiciousActivity.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(messageBoardSuspiciousActivity.getActions()));
		}

		if (messageBoardSuspiciousActivity.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardSuspiciousActivity.getCreateDate()));

			sb.append("\"");
		}

		if (messageBoardSuspiciousActivity.getMessageId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageId\": ");

			sb.append(messageBoardSuspiciousActivity.getMessageId());
		}

		if (messageBoardSuspiciousActivity.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardSuspiciousActivity.getModifiedDate()));

			sb.append("\"");
		}

		if (messageBoardSuspiciousActivity.getReason() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reason\": ");

			sb.append("\"");

			sb.append(_escape(messageBoardSuspiciousActivity.getReason()));

			sb.append("\"");
		}

		if (messageBoardSuspiciousActivity.getSuspiciousActivityId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"suspiciousActivityId\": ");

			sb.append(messageBoardSuspiciousActivity.getSuspiciousActivityId());
		}

		if (messageBoardSuspiciousActivity.getThreadId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"threadId\": ");

			sb.append(messageBoardSuspiciousActivity.getThreadId());
		}

		if (messageBoardSuspiciousActivity.getUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(messageBoardSuspiciousActivity.getUserId());
		}

		if (messageBoardSuspiciousActivity.getValidated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"validated\": ");

			sb.append(messageBoardSuspiciousActivity.getValidated());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MessageBoardSuspiciousActivityJSONParser
			messageBoardSuspiciousActivityJSONParser =
				new MessageBoardSuspiciousActivityJSONParser();

		return messageBoardSuspiciousActivityJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		MessageBoardSuspiciousActivity messageBoardSuspiciousActivity) {

		if (messageBoardSuspiciousActivity == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (messageBoardSuspiciousActivity.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions",
				String.valueOf(messageBoardSuspiciousActivity.getActions()));
		}

		if (messageBoardSuspiciousActivity.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(
					messageBoardSuspiciousActivity.getCreateDate()));
		}

		if (messageBoardSuspiciousActivity.getMessageId() == null) {
			map.put("messageId", null);
		}
		else {
			map.put(
				"messageId",
				String.valueOf(messageBoardSuspiciousActivity.getMessageId()));
		}

		if (messageBoardSuspiciousActivity.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(
					messageBoardSuspiciousActivity.getModifiedDate()));
		}

		if (messageBoardSuspiciousActivity.getReason() == null) {
			map.put("reason", null);
		}
		else {
			map.put(
				"reason",
				String.valueOf(messageBoardSuspiciousActivity.getReason()));
		}

		if (messageBoardSuspiciousActivity.getSuspiciousActivityId() == null) {
			map.put("suspiciousActivityId", null);
		}
		else {
			map.put(
				"suspiciousActivityId",
				String.valueOf(
					messageBoardSuspiciousActivity.getSuspiciousActivityId()));
		}

		if (messageBoardSuspiciousActivity.getThreadId() == null) {
			map.put("threadId", null);
		}
		else {
			map.put(
				"threadId",
				String.valueOf(messageBoardSuspiciousActivity.getThreadId()));
		}

		if (messageBoardSuspiciousActivity.getUserId() == null) {
			map.put("userId", null);
		}
		else {
			map.put(
				"userId",
				String.valueOf(messageBoardSuspiciousActivity.getUserId()));
		}

		if (messageBoardSuspiciousActivity.getValidated() == null) {
			map.put("validated", null);
		}
		else {
			map.put(
				"validated",
				String.valueOf(messageBoardSuspiciousActivity.getValidated()));
		}

		return map;
	}

	public static class MessageBoardSuspiciousActivityJSONParser
		extends BaseJSONParser<MessageBoardSuspiciousActivity> {

		@Override
		protected MessageBoardSuspiciousActivity createDTO() {
			return new MessageBoardSuspiciousActivity();
		}

		@Override
		protected MessageBoardSuspiciousActivity[] createDTOArray(int size) {
			return new MessageBoardSuspiciousActivity[size];
		}

		@Override
		protected void setField(
			MessageBoardSuspiciousActivity messageBoardSuspiciousActivity,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setActions(
						(Map)MessageBoardSuspiciousActivitySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "messageId")) {
				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setMessageId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "reason")) {
				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setReason(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "suspiciousActivityId")) {

				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setSuspiciousActivityId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "threadId")) {
				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setThreadId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setUserId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "validated")) {
				if (jsonParserFieldValue != null) {
					messageBoardSuspiciousActivity.setValidated(
						(Boolean)jsonParserFieldValue);
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