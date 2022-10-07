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

import com.liferay.notification.rest.client.dto.v1_0.NotificationTemplate;
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
public class NotificationTemplateSerDes {

	public static NotificationTemplate toDTO(String json) {
		NotificationTemplateJSONParser notificationTemplateJSONParser =
			new NotificationTemplateJSONParser();

		return notificationTemplateJSONParser.parseToDTO(json);
	}

	public static NotificationTemplate[] toDTOs(String json) {
		NotificationTemplateJSONParser notificationTemplateJSONParser =
			new NotificationTemplateJSONParser();

		return notificationTemplateJSONParser.parseToDTOs(json);
	}

	public static String toJSON(NotificationTemplate notificationTemplate) {
		if (notificationTemplate == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (notificationTemplate.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(notificationTemplate.getActions()));
		}

		if (notificationTemplate.getAttachmentObjectFieldIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attachmentObjectFieldIds\": ");

			sb.append("[");

			for (int i = 0;
				 i < notificationTemplate.getAttachmentObjectFieldIds().length;
				 i++) {

				sb.append(
					notificationTemplate.getAttachmentObjectFieldIds()[i]);

				if ((i + 1) <
						notificationTemplate.
							getAttachmentObjectFieldIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (notificationTemplate.getBcc() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bcc\": ");

			sb.append("\"");

			sb.append(_escape(notificationTemplate.getBcc()));

			sb.append("\"");
		}

		if (notificationTemplate.getBody() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"body\": ");

			sb.append(_toJSON(notificationTemplate.getBody()));
		}

		if (notificationTemplate.getCc() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cc\": ");

			sb.append("\"");

			sb.append(_escape(notificationTemplate.getCc()));

			sb.append("\"");
		}

		if (notificationTemplate.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					notificationTemplate.getDateCreated()));

			sb.append("\"");
		}

		if (notificationTemplate.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					notificationTemplate.getDateModified()));

			sb.append("\"");
		}

		if (notificationTemplate.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(notificationTemplate.getDescription()));

			sb.append("\"");
		}

		if (notificationTemplate.getFrom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"from\": ");

			sb.append("\"");

			sb.append(_escape(notificationTemplate.getFrom()));

			sb.append("\"");
		}

		if (notificationTemplate.getFromName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fromName\": ");

			sb.append(_toJSON(notificationTemplate.getFromName()));
		}

		if (notificationTemplate.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(notificationTemplate.getId());
		}

		if (notificationTemplate.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(notificationTemplate.getName()));

			sb.append("\"");
		}

		if (notificationTemplate.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(notificationTemplate.getName_i18n()));
		}

		if (notificationTemplate.getObjectDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectDefinitionId\": ");

			sb.append(notificationTemplate.getObjectDefinitionId());
		}

		if (notificationTemplate.getRecipientType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"recipientType\": ");

			sb.append("\"");

			sb.append(notificationTemplate.getRecipientType());

			sb.append("\"");
		}

		if (notificationTemplate.getSubject() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subject\": ");

			sb.append(_toJSON(notificationTemplate.getSubject()));
		}

		if (notificationTemplate.getTo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"to\": ");

			sb.append(_toJSON(notificationTemplate.getTo()));
		}

		if (notificationTemplate.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(notificationTemplate.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		NotificationTemplateJSONParser notificationTemplateJSONParser =
			new NotificationTemplateJSONParser();

		return notificationTemplateJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		NotificationTemplate notificationTemplate) {

		if (notificationTemplate == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (notificationTemplate.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions", String.valueOf(notificationTemplate.getActions()));
		}

		if (notificationTemplate.getAttachmentObjectFieldIds() == null) {
			map.put("attachmentObjectFieldIds", null);
		}
		else {
			map.put(
				"attachmentObjectFieldIds",
				String.valueOf(
					notificationTemplate.getAttachmentObjectFieldIds()));
		}

		if (notificationTemplate.getBcc() == null) {
			map.put("bcc", null);
		}
		else {
			map.put("bcc", String.valueOf(notificationTemplate.getBcc()));
		}

		if (notificationTemplate.getBody() == null) {
			map.put("body", null);
		}
		else {
			map.put("body", String.valueOf(notificationTemplate.getBody()));
		}

		if (notificationTemplate.getCc() == null) {
			map.put("cc", null);
		}
		else {
			map.put("cc", String.valueOf(notificationTemplate.getCc()));
		}

		if (notificationTemplate.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					notificationTemplate.getDateCreated()));
		}

		if (notificationTemplate.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					notificationTemplate.getDateModified()));
		}

		if (notificationTemplate.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(notificationTemplate.getDescription()));
		}

		if (notificationTemplate.getFrom() == null) {
			map.put("from", null);
		}
		else {
			map.put("from", String.valueOf(notificationTemplate.getFrom()));
		}

		if (notificationTemplate.getFromName() == null) {
			map.put("fromName", null);
		}
		else {
			map.put(
				"fromName", String.valueOf(notificationTemplate.getFromName()));
		}

		if (notificationTemplate.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(notificationTemplate.getId()));
		}

		if (notificationTemplate.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(notificationTemplate.getName()));
		}

		if (notificationTemplate.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put(
				"name_i18n",
				String.valueOf(notificationTemplate.getName_i18n()));
		}

		if (notificationTemplate.getObjectDefinitionId() == null) {
			map.put("objectDefinitionId", null);
		}
		else {
			map.put(
				"objectDefinitionId",
				String.valueOf(notificationTemplate.getObjectDefinitionId()));
		}

		if (notificationTemplate.getRecipientType() == null) {
			map.put("recipientType", null);
		}
		else {
			map.put(
				"recipientType",
				String.valueOf(notificationTemplate.getRecipientType()));
		}

		if (notificationTemplate.getSubject() == null) {
			map.put("subject", null);
		}
		else {
			map.put(
				"subject", String.valueOf(notificationTemplate.getSubject()));
		}

		if (notificationTemplate.getTo() == null) {
			map.put("to", null);
		}
		else {
			map.put("to", String.valueOf(notificationTemplate.getTo()));
		}

		if (notificationTemplate.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(notificationTemplate.getType()));
		}

		return map;
	}

	public static class NotificationTemplateJSONParser
		extends BaseJSONParser<NotificationTemplate> {

		@Override
		protected NotificationTemplate createDTO() {
			return new NotificationTemplate();
		}

		@Override
		protected NotificationTemplate[] createDTOArray(int size) {
			return new NotificationTemplate[size];
		}

		@Override
		protected void setField(
			NotificationTemplate notificationTemplate,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setActions(
						(Map)NotificationTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "attachmentObjectFieldIds")) {

				if (jsonParserFieldValue != null) {
					notificationTemplate.setAttachmentObjectFieldIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "bcc")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setBcc((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "body")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setBody(
						(Map)NotificationTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "cc")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setCc((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "from")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setFrom((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fromName")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setFromName(
						(Map)NotificationTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setName_i18n(
						(Map)NotificationTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectDefinitionId")) {

				if (jsonParserFieldValue != null) {
					notificationTemplate.setObjectDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "recipientType")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setRecipientType(
						NotificationTemplate.RecipientType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subject")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setSubject(
						(Map)NotificationTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "to")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setTo(
						(Map)NotificationTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					notificationTemplate.setType((String)jsonParserFieldValue);
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