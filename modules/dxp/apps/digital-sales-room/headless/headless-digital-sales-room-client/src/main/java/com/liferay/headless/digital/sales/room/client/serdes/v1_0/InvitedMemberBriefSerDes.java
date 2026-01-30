/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.client.serdes.v1_0;

import com.liferay.headless.digital.sales.room.client.dto.v1_0.InvitedMemberBrief;
import com.liferay.headless.digital.sales.room.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Stefano Motta
 * @generated
 */
@Generated("")
public class InvitedMemberBriefSerDes {

	public static InvitedMemberBrief toDTO(String json) {
		InvitedMemberBriefJSONParser invitedMemberBriefJSONParser =
			new InvitedMemberBriefJSONParser();

		return invitedMemberBriefJSONParser.parseToDTO(json);
	}

	public static InvitedMemberBrief[] toDTOs(String json) {
		InvitedMemberBriefJSONParser invitedMemberBriefJSONParser =
			new InvitedMemberBriefJSONParser();

		return invitedMemberBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(InvitedMemberBrief invitedMemberBrief) {
		if (invitedMemberBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (invitedMemberBrief.getEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddress\": ");

			sb.append("\"");

			sb.append(_escape(invitedMemberBrief.getEmailAddress()));

			sb.append("\"");
		}

		if (invitedMemberBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(invitedMemberBrief.getId());
		}

		if (invitedMemberBrief.getRoleKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleKey\": ");

			sb.append("\"");

			sb.append(_escape(invitedMemberBrief.getRoleKey()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		InvitedMemberBriefJSONParser invitedMemberBriefJSONParser =
			new InvitedMemberBriefJSONParser();

		return invitedMemberBriefJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		InvitedMemberBrief invitedMemberBrief) {

		if (invitedMemberBrief == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (invitedMemberBrief.getEmailAddress() == null) {
			map.put("emailAddress", null);
		}
		else {
			map.put(
				"emailAddress",
				String.valueOf(invitedMemberBrief.getEmailAddress()));
		}

		if (invitedMemberBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(invitedMemberBrief.getId()));
		}

		if (invitedMemberBrief.getRoleKey() == null) {
			map.put("roleKey", null);
		}
		else {
			map.put("roleKey", String.valueOf(invitedMemberBrief.getRoleKey()));
		}

		return map;
	}

	public static class InvitedMemberBriefJSONParser
		extends BaseJSONParser<InvitedMemberBrief> {

		@Override
		protected InvitedMemberBrief createDTO() {
			return new InvitedMemberBrief();
		}

		@Override
		protected InvitedMemberBrief[] createDTOArray(int size) {
			return new InvitedMemberBrief[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "roleKey")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			InvitedMemberBrief invitedMemberBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				if (jsonParserFieldValue != null) {
					invitedMemberBrief.setEmailAddress(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					invitedMemberBrief.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleKey")) {
				if (jsonParserFieldValue != null) {
					invitedMemberBrief.setRoleKey((String)jsonParserFieldValue);
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