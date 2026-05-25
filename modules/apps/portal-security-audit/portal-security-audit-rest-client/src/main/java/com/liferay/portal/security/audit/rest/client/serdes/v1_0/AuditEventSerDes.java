/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.rest.client.serdes.v1_0;

import com.liferay.portal.security.audit.rest.client.dto.v1_0.AuditEvent;
import com.liferay.portal.security.audit.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AuditEventSerDes {

	public static AuditEvent toDTO(String json) {
		AuditEventJSONParser auditEventJSONParser = new AuditEventJSONParser();

		return auditEventJSONParser.parseToDTO(json);
	}

	public static AuditEvent[] toDTOs(String json) {
		AuditEventJSONParser auditEventJSONParser = new AuditEventJSONParser();

		return auditEventJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AuditEvent auditEvent) {
		if (auditEvent == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (auditEvent.getAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(auditEvent.getAccountId());
		}

		if (auditEvent.getAdditionalInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"additionalInfo\": ");

			sb.append(_toJSON(auditEvent.getAdditionalInfo()));
		}

		if (auditEvent.getClientHost() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientHost\": ");

			sb.append("\"");

			sb.append(_escape(auditEvent.getClientHost()));

			sb.append("\"");
		}

		if (auditEvent.getClientIP() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientIP\": ");

			sb.append("\"");

			sb.append(_escape(auditEvent.getClientIP()));

			sb.append("\"");
		}

		if (auditEvent.getContextName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contextName\": ");

			sb.append("\"");

			sb.append(_escape(auditEvent.getContextName()));

			sb.append("\"");
		}

		if (auditEvent.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(auditEvent.getCreator());
		}

		if (auditEvent.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(auditEvent.getDateCreated()));

			sb.append("\"");
		}

		if (auditEvent.getEntityId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entityId\": ");

			sb.append(auditEvent.getEntityId());
		}

		if (auditEvent.getEntityType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entityType\": ");

			sb.append("\"");

			sb.append(_escape(auditEvent.getEntityType()));

			sb.append("\"");
		}

		if (auditEvent.getEventType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"eventType\": ");

			sb.append("\"");

			sb.append(_escape(auditEvent.getEventType()));

			sb.append("\"");
		}

		if (auditEvent.getGroupId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"groupId\": ");

			sb.append(auditEvent.getGroupId());
		}

		if (auditEvent.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(auditEvent.getId());
		}

		if (auditEvent.getServerName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"serverName\": ");

			sb.append("\"");

			sb.append(_escape(auditEvent.getServerName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AuditEventJSONParser auditEventJSONParser = new AuditEventJSONParser();

		return auditEventJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AuditEvent auditEvent) {
		if (auditEvent == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (auditEvent.getAccountId() == null) {
			map.put("accountId", null);
		}
		else {
			map.put("accountId", String.valueOf(auditEvent.getAccountId()));
		}

		if (auditEvent.getAdditionalInfo() == null) {
			map.put("additionalInfo", null);
		}
		else {
			map.put(
				"additionalInfo",
				String.valueOf(auditEvent.getAdditionalInfo()));
		}

		if (auditEvent.getClientHost() == null) {
			map.put("clientHost", null);
		}
		else {
			map.put("clientHost", String.valueOf(auditEvent.getClientHost()));
		}

		if (auditEvent.getClientIP() == null) {
			map.put("clientIP", null);
		}
		else {
			map.put("clientIP", String.valueOf(auditEvent.getClientIP()));
		}

		if (auditEvent.getContextName() == null) {
			map.put("contextName", null);
		}
		else {
			map.put("contextName", String.valueOf(auditEvent.getContextName()));
		}

		if (auditEvent.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(auditEvent.getCreator()));
		}

		if (auditEvent.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(auditEvent.getDateCreated()));
		}

		if (auditEvent.getEntityId() == null) {
			map.put("entityId", null);
		}
		else {
			map.put("entityId", String.valueOf(auditEvent.getEntityId()));
		}

		if (auditEvent.getEntityType() == null) {
			map.put("entityType", null);
		}
		else {
			map.put("entityType", String.valueOf(auditEvent.getEntityType()));
		}

		if (auditEvent.getEventType() == null) {
			map.put("eventType", null);
		}
		else {
			map.put("eventType", String.valueOf(auditEvent.getEventType()));
		}

		if (auditEvent.getGroupId() == null) {
			map.put("groupId", null);
		}
		else {
			map.put("groupId", String.valueOf(auditEvent.getGroupId()));
		}

		if (auditEvent.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(auditEvent.getId()));
		}

		if (auditEvent.getServerName() == null) {
			map.put("serverName", null);
		}
		else {
			map.put("serverName", String.valueOf(auditEvent.getServerName()));
		}

		return map;
	}

	public static class AuditEventJSONParser
		extends BaseJSONParser<AuditEvent> {

		@Override
		protected AuditEvent createDTO() {
			return new AuditEvent();
		}

		@Override
		protected AuditEvent[] createDTOArray(int size) {
			return new AuditEvent[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "accountId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "additionalInfo")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "clientHost")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "clientIP")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "contextName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "entityId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "entityType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "eventType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "groupId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "serverName")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AuditEvent auditEvent, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accountId")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "additionalInfo")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setAdditionalInfo(
						(Map<String, ?>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "clientHost")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setClientHost((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "clientIP")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setClientIP((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contextName")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setContextName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "entityId")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setEntityId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "entityType")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setEntityType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "eventType")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setEventType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "groupId")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setGroupId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "serverName")) {
				if (jsonParserFieldValue != null) {
					auditEvent.setServerName((String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:464100660