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

import com.liferay.analytics.settings.rest.client.dto.v1_0.ContactOrganization;
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
public class ContactOrganizationSerDes {

	public static ContactOrganization toDTO(String json) {
		ContactOrganizationJSONParser contactOrganizationJSONParser =
			new ContactOrganizationJSONParser();

		return contactOrganizationJSONParser.parseToDTO(json);
	}

	public static ContactOrganization[] toDTOs(String json) {
		ContactOrganizationJSONParser contactOrganizationJSONParser =
			new ContactOrganizationJSONParser();

		return contactOrganizationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContactOrganization contactOrganization) {
		if (contactOrganization == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contactOrganization.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(contactOrganization.getId());
		}

		if (contactOrganization.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(contactOrganization.getName()));

			sb.append("\"");
		}

		if (contactOrganization.getSelected() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selected\": ");

			sb.append(contactOrganization.getSelected());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContactOrganizationJSONParser contactOrganizationJSONParser =
			new ContactOrganizationJSONParser();

		return contactOrganizationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ContactOrganization contactOrganization) {

		if (contactOrganization == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (contactOrganization.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(contactOrganization.getId()));
		}

		if (contactOrganization.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(contactOrganization.getName()));
		}

		if (contactOrganization.getSelected() == null) {
			map.put("selected", null);
		}
		else {
			map.put(
				"selected", String.valueOf(contactOrganization.getSelected()));
		}

		return map;
	}

	public static class ContactOrganizationJSONParser
		extends BaseJSONParser<ContactOrganization> {

		@Override
		protected ContactOrganization createDTO() {
			return new ContactOrganization();
		}

		@Override
		protected ContactOrganization[] createDTOArray(int size) {
			return new ContactOrganization[size];
		}

		@Override
		protected void setField(
			ContactOrganization contactOrganization, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contactOrganization.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					contactOrganization.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selected")) {
				if (jsonParserFieldValue != null) {
					contactOrganization.setSelected(
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