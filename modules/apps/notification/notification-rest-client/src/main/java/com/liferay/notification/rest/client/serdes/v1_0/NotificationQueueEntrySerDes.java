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

package com.liferay.notification.rest.client.serdes.v1_0;

import com.liferay.notification.rest.client.dto.v1_0.NotificationQueueEntry;
import com.liferay.notification.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class NotificationQueueEntrySerDes {

	public static NotificationQueueEntry toDTO(String json) {
		NotificationQueueEntryJSONParser notificationQueueEntryJSONParser =
			new NotificationQueueEntryJSONParser();

		return notificationQueueEntryJSONParser.parseToDTO(json);
	}

	public static NotificationQueueEntry[] toDTOs(String json) {
		NotificationQueueEntryJSONParser notificationQueueEntryJSONParser =
			new NotificationQueueEntryJSONParser();

		return notificationQueueEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(NotificationQueueEntry notificationQueueEntry) {
		if (notificationQueueEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (notificationQueueEntry.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(notificationQueueEntry.getActions()));
		}

		if (notificationQueueEntry.getBcc() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bcc\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getBcc()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getBody() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"body\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getBody()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getCc() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cc\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getCc()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getFrom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"from\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getFrom()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getFromName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fromName\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getFromName()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(notificationQueueEntry.getId());
		}

		if (notificationQueueEntry.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(notificationQueueEntry.getPriority());
		}

		if (notificationQueueEntry.getSentDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sentDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					notificationQueueEntry.getSentDate()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(notificationQueueEntry.getStatus());
		}

		if (notificationQueueEntry.getSubject() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subject\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getSubject()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getTo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"to\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getTo()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getToName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"toName\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getToName()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getTriggerBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"triggerBy\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getTriggerBy()));

			sb.append("\"");
		}

		if (notificationQueueEntry.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(notificationQueueEntry.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		NotificationQueueEntryJSONParser notificationQueueEntryJSONParser =
			new NotificationQueueEntryJSONParser();

		return notificationQueueEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		NotificationQueueEntry notificationQueueEntry) {

		if (notificationQueueEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (notificationQueueEntry.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions", String.valueOf(notificationQueueEntry.getActions()));
		}

		if (notificationQueueEntry.getBcc() == null) {
			map.put("bcc", null);
		}
		else {
			map.put("bcc", String.valueOf(notificationQueueEntry.getBcc()));
		}

		if (notificationQueueEntry.getBody() == null) {
			map.put("body", null);
		}
		else {
			map.put("body", String.valueOf(notificationQueueEntry.getBody()));
		}

		if (notificationQueueEntry.getCc() == null) {
			map.put("cc", null);
		}
		else {
			map.put("cc", String.valueOf(notificationQueueEntry.getCc()));
		}

		if (notificationQueueEntry.getFrom() == null) {
			map.put("from", null);
		}
		else {
			map.put("from", String.valueOf(notificationQueueEntry.getFrom()));
		}

		if (notificationQueueEntry.getFromName() == null) {
			map.put("fromName", null);
		}
		else {
			map.put(
				"fromName",
				String.valueOf(notificationQueueEntry.getFromName()));
		}

		if (notificationQueueEntry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(notificationQueueEntry.getId()));
		}

		if (notificationQueueEntry.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put(
				"priority",
				String.valueOf(notificationQueueEntry.getPriority()));
		}

		if (notificationQueueEntry.getSentDate() == null) {
			map.put("sentDate", null);
		}
		else {
			map.put(
				"sentDate",
				liferayToJSONDateFormat.format(
					notificationQueueEntry.getSentDate()));
		}

		if (notificationQueueEntry.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put(
				"status", String.valueOf(notificationQueueEntry.getStatus()));
		}

		if (notificationQueueEntry.getSubject() == null) {
			map.put("subject", null);
		}
		else {
			map.put(
				"subject", String.valueOf(notificationQueueEntry.getSubject()));
		}

		if (notificationQueueEntry.getTo() == null) {
			map.put("to", null);
		}
		else {
			map.put("to", String.valueOf(notificationQueueEntry.getTo()));
		}

		if (notificationQueueEntry.getToName() == null) {
			map.put("toName", null);
		}
		else {
			map.put(
				"toName", String.valueOf(notificationQueueEntry.getToName()));
		}

		if (notificationQueueEntry.getTriggerBy() == null) {
			map.put("triggerBy", null);
		}
		else {
			map.put(
				"triggerBy",
				String.valueOf(notificationQueueEntry.getTriggerBy()));
		}

		if (notificationQueueEntry.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(notificationQueueEntry.getType()));
		}

		return map;
	}

	public static class NotificationQueueEntryJSONParser
		extends BaseJSONParser<NotificationQueueEntry> {

		@Override
		protected NotificationQueueEntry createDTO() {
			return new NotificationQueueEntry();
		}

		@Override
		protected NotificationQueueEntry[] createDTOArray(int size) {
			return new NotificationQueueEntry[size];
		}

		@Override
		protected void setField(
			NotificationQueueEntry notificationQueueEntry,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setActions(
						(Map)NotificationQueueEntrySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "bcc")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setBcc((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "body")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setBody(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "cc")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setCc((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "from")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setFrom(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fromName")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setFromName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setPriority(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sentDate")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setSentDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subject")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setSubject(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "to")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setTo((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "toName")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setToName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "triggerBy")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setTriggerBy(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					notificationQueueEntry.setType(
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