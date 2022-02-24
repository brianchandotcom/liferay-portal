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

import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class ListTypeDefinitionSerDes {

	public static ListTypeDefinition toDTO(String json) {
		ListTypeDefinitionJSONParser listTypeDefinitionJSONParser =
			new ListTypeDefinitionJSONParser();

		return listTypeDefinitionJSONParser.parseToDTO(json);
	}

	public static ListTypeDefinition[] toDTOs(String json) {
		ListTypeDefinitionJSONParser listTypeDefinitionJSONParser =
			new ListTypeDefinitionJSONParser();

		return listTypeDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ListTypeDefinition listTypeDefinition) {
		if (listTypeDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (listTypeDefinition.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(listTypeDefinition.getActions()));
		}

		if (listTypeDefinition.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					listTypeDefinition.getDateCreated()));

			sb.append("\"");
		}

		if (listTypeDefinition.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					listTypeDefinition.getDateModified()));

			sb.append("\"");
		}

		if (listTypeDefinition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(listTypeDefinition.getId());
		}

		if (listTypeDefinition.getListTypeEntries() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listTypeEntries\": ");

			sb.append("[");

			for (int i = 0; i < listTypeDefinition.getListTypeEntries().length;
				 i++) {

				sb.append(
					String.valueOf(listTypeDefinition.getListTypeEntries()[i]));

				if ((i + 1) < listTypeDefinition.getListTypeEntries().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (listTypeDefinition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(listTypeDefinition.getName()));

			sb.append("\"");
		}

		if (listTypeDefinition.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(listTypeDefinition.getName_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ListTypeDefinitionJSONParser listTypeDefinitionJSONParser =
			new ListTypeDefinitionJSONParser();

		return listTypeDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ListTypeDefinition listTypeDefinition) {

		if (listTypeDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (listTypeDefinition.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(listTypeDefinition.getActions()));
		}

		if (listTypeDefinition.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					listTypeDefinition.getDateCreated()));
		}

		if (listTypeDefinition.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					listTypeDefinition.getDateModified()));
		}

		if (listTypeDefinition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(listTypeDefinition.getId()));
		}

		if (listTypeDefinition.getListTypeEntries() == null) {
			map.put("listTypeEntries", null);
		}
		else {
			map.put(
				"listTypeEntries",
				String.valueOf(listTypeDefinition.getListTypeEntries()));
		}

		if (listTypeDefinition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(listTypeDefinition.getName()));
		}

		if (listTypeDefinition.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put(
				"name_i18n", String.valueOf(listTypeDefinition.getName_i18n()));
		}

		return map;
	}

	public static class ListTypeDefinitionJSONParser
		extends BaseJSONParser<ListTypeDefinition> {

		@Override
		protected ListTypeDefinition createDTO() {
			return new ListTypeDefinition();
		}

		@Override
		protected ListTypeDefinition[] createDTOArray(int size) {
			return new ListTypeDefinition[size];
		}

		@Override
		protected void setField(
			ListTypeDefinition listTypeDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					listTypeDefinition.setActions(
						(Map)ListTypeDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					listTypeDefinition.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					listTypeDefinition.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					listTypeDefinition.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "listTypeEntries")) {
				if (jsonParserFieldValue != null) {
					listTypeDefinition.setListTypeEntries(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ListTypeEntrySerDes.toDTO((String)object)
						).toArray(
							size -> new ListTypeEntry[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					listTypeDefinition.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					listTypeDefinition.setName_i18n(
						(Map)ListTypeDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
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