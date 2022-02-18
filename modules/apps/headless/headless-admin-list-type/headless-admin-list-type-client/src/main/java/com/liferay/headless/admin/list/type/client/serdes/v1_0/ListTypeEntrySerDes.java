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

package com.liferay.headless.admin.list.type.client.serdes.v1_0;

import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.client.json.BaseJSONParser;

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
public class ListTypeEntrySerDes {

	public static ListTypeEntry toDTO(String json) {
		ListTypeEntryJSONParser listTypeEntryJSONParser =
			new ListTypeEntryJSONParser();

		return listTypeEntryJSONParser.parseToDTO(json);
	}

	public static ListTypeEntry[] toDTOs(String json) {
		ListTypeEntryJSONParser listTypeEntryJSONParser =
			new ListTypeEntryJSONParser();

		return listTypeEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ListTypeEntry listTypeEntry) {
		if (listTypeEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (listTypeEntry.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(listTypeEntry.getActions()));
		}

		if (listTypeEntry.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(listTypeEntry.getDateCreated()));

			sb.append("\"");
		}

		if (listTypeEntry.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					listTypeEntry.getDateModified()));

			sb.append("\"");
		}

		if (listTypeEntry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(listTypeEntry.getId());
		}

		if (listTypeEntry.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(listTypeEntry.getKey()));

			sb.append("\"");
		}

		if (listTypeEntry.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(listTypeEntry.getName()));

			sb.append("\"");
		}

		if (listTypeEntry.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(listTypeEntry.getName_i18n()));
		}

		if (listTypeEntry.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(listTypeEntry.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ListTypeEntryJSONParser listTypeEntryJSONParser =
			new ListTypeEntryJSONParser();

		return listTypeEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ListTypeEntry listTypeEntry) {
		if (listTypeEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (listTypeEntry.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(listTypeEntry.getActions()));
		}

		if (listTypeEntry.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(listTypeEntry.getDateCreated()));
		}

		if (listTypeEntry.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					listTypeEntry.getDateModified()));
		}

		if (listTypeEntry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(listTypeEntry.getId()));
		}

		if (listTypeEntry.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(listTypeEntry.getKey()));
		}

		if (listTypeEntry.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(listTypeEntry.getName()));
		}

		if (listTypeEntry.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put("name_i18n", String.valueOf(listTypeEntry.getName_i18n()));
		}

		if (listTypeEntry.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(listTypeEntry.getType()));
		}

		return map;
	}

	public static class ListTypeEntryJSONParser
		extends BaseJSONParser<ListTypeEntry> {

		@Override
		protected ListTypeEntry createDTO() {
			return new ListTypeEntry();
		}

		@Override
		protected ListTypeEntry[] createDTOArray(int size) {
			return new ListTypeEntry[size];
		}

		@Override
		protected void setField(
			ListTypeEntry listTypeEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					listTypeEntry.setActions(
						(Map)ListTypeEntrySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					listTypeEntry.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					listTypeEntry.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					listTypeEntry.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					listTypeEntry.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					listTypeEntry.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					listTypeEntry.setName_i18n(
						(Map)ListTypeEntrySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					listTypeEntry.setType((String)jsonParserFieldValue);
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