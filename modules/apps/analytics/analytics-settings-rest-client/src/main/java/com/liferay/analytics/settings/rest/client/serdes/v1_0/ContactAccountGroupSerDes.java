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

import com.liferay.analytics.settings.rest.client.dto.v1_0.ContactAccountGroup;
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
public class ContactAccountGroupSerDes {

	public static ContactAccountGroup toDTO(String json) {
		ContactAccountGroupJSONParser contactAccountGroupJSONParser =
			new ContactAccountGroupJSONParser();

		return contactAccountGroupJSONParser.parseToDTO(json);
	}

	public static ContactAccountGroup[] toDTOs(String json) {
		ContactAccountGroupJSONParser contactAccountGroupJSONParser =
			new ContactAccountGroupJSONParser();

		return contactAccountGroupJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContactAccountGroup contactAccountGroup) {
		if (contactAccountGroup == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contactAccountGroup.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(contactAccountGroup.getId());
		}

		if (contactAccountGroup.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(contactAccountGroup.getName()));

			sb.append("\"");
		}

		if (contactAccountGroup.getSelected() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selected\": ");

			sb.append(contactAccountGroup.getSelected());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContactAccountGroupJSONParser contactAccountGroupJSONParser =
			new ContactAccountGroupJSONParser();

		return contactAccountGroupJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ContactAccountGroup contactAccountGroup) {

		if (contactAccountGroup == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (contactAccountGroup.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(contactAccountGroup.getId()));
		}

		if (contactAccountGroup.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(contactAccountGroup.getName()));
		}

		if (contactAccountGroup.getSelected() == null) {
			map.put("selected", null);
		}
		else {
			map.put(
				"selected", String.valueOf(contactAccountGroup.getSelected()));
		}

		return map;
	}

	public static class ContactAccountGroupJSONParser
		extends BaseJSONParser<ContactAccountGroup> {

		@Override
		protected ContactAccountGroup createDTO() {
			return new ContactAccountGroup();
		}

		@Override
		protected ContactAccountGroup[] createDTOArray(int size) {
			return new ContactAccountGroup[size];
		}

		@Override
		protected void setField(
			ContactAccountGroup contactAccountGroup, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contactAccountGroup.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					contactAccountGroup.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selected")) {
				if (jsonParserFieldValue != null) {
					contactAccountGroup.setSelected(
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