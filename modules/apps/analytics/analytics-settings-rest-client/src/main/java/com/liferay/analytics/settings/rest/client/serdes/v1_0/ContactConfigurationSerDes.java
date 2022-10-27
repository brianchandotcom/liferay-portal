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

package com.liferay.analytics.settings.rest.client.serdes.v1_0;

import com.liferay.analytics.settings.rest.client.dto.v1_0.ContactConfiguration;
import com.liferay.analytics.settings.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class ContactConfigurationSerDes {

	public static ContactConfiguration toDTO(String json) {
		ContactConfigurationJSONParser contactConfigurationJSONParser =
			new ContactConfigurationJSONParser();

		return contactConfigurationJSONParser.parseToDTO(json);
	}

	public static ContactConfiguration[] toDTOs(String json) {
		ContactConfigurationJSONParser contactConfigurationJSONParser =
			new ContactConfigurationJSONParser();

		return contactConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContactConfiguration contactConfiguration) {
		if (contactConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contactConfiguration.getSyncAllAccounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"syncAllAccounts\": ");

			sb.append(contactConfiguration.getSyncAllAccounts());
		}

		if (contactConfiguration.getSyncAllContacts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"syncAllContacts\": ");

			sb.append(contactConfiguration.getSyncAllContacts());
		}

		if (contactConfiguration.getSyncedAccountGroupIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"syncedAccountGroupIds\": ");

			sb.append("[");

			for (int i = 0;
				 i < contactConfiguration.getSyncedAccountGroupIds().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(
						contactConfiguration.getSyncedAccountGroupIds()[i]));

				sb.append("\"");

				if ((i + 1) <
						contactConfiguration.
							getSyncedAccountGroupIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contactConfiguration.getSyncedOrganizationIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"syncedOrganizationIds\": ");

			sb.append("[");

			for (int i = 0;
				 i < contactConfiguration.getSyncedOrganizationIds().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(
						contactConfiguration.getSyncedOrganizationIds()[i]));

				sb.append("\"");

				if ((i + 1) <
						contactConfiguration.
							getSyncedOrganizationIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contactConfiguration.getSyncedUserGroupIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"syncedUserGroupIds\": ");

			sb.append("[");

			for (int i = 0;
				 i < contactConfiguration.getSyncedUserGroupIds().length; i++) {

				sb.append("\"");

				sb.append(
					_escape(contactConfiguration.getSyncedUserGroupIds()[i]));

				sb.append("\"");

				if ((i + 1) <
						contactConfiguration.getSyncedUserGroupIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContactConfigurationJSONParser contactConfigurationJSONParser =
			new ContactConfigurationJSONParser();

		return contactConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ContactConfiguration contactConfiguration) {

		if (contactConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (contactConfiguration.getSyncAllAccounts() == null) {
			map.put("syncAllAccounts", null);
		}
		else {
			map.put(
				"syncAllAccounts",
				String.valueOf(contactConfiguration.getSyncAllAccounts()));
		}

		if (contactConfiguration.getSyncAllContacts() == null) {
			map.put("syncAllContacts", null);
		}
		else {
			map.put(
				"syncAllContacts",
				String.valueOf(contactConfiguration.getSyncAllContacts()));
		}

		if (contactConfiguration.getSyncedAccountGroupIds() == null) {
			map.put("syncedAccountGroupIds", null);
		}
		else {
			map.put(
				"syncedAccountGroupIds",
				String.valueOf(
					contactConfiguration.getSyncedAccountGroupIds()));
		}

		if (contactConfiguration.getSyncedOrganizationIds() == null) {
			map.put("syncedOrganizationIds", null);
		}
		else {
			map.put(
				"syncedOrganizationIds",
				String.valueOf(
					contactConfiguration.getSyncedOrganizationIds()));
		}

		if (contactConfiguration.getSyncedUserGroupIds() == null) {
			map.put("syncedUserGroupIds", null);
		}
		else {
			map.put(
				"syncedUserGroupIds",
				String.valueOf(contactConfiguration.getSyncedUserGroupIds()));
		}

		return map;
	}

	public static class ContactConfigurationJSONParser
		extends BaseJSONParser<ContactConfiguration> {

		@Override
		protected ContactConfiguration createDTO() {
			return new ContactConfiguration();
		}

		@Override
		protected ContactConfiguration[] createDTOArray(int size) {
			return new ContactConfiguration[size];
		}

		@Override
		protected void setField(
			ContactConfiguration contactConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "syncAllAccounts")) {
				if (jsonParserFieldValue != null) {
					contactConfiguration.setSyncAllAccounts(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "syncAllContacts")) {
				if (jsonParserFieldValue != null) {
					contactConfiguration.setSyncAllContacts(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "syncedAccountGroupIds")) {

				if (jsonParserFieldValue != null) {
					contactConfiguration.setSyncedAccountGroupIds(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "syncedOrganizationIds")) {

				if (jsonParserFieldValue != null) {
					contactConfiguration.setSyncedOrganizationIds(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "syncedUserGroupIds")) {

				if (jsonParserFieldValue != null) {
					contactConfiguration.setSyncedUserGroupIds(
						toStrings((Object[])jsonParserFieldValue));
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