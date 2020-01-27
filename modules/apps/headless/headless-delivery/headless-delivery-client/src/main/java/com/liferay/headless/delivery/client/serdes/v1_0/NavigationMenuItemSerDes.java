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

import com.liferay.headless.delivery.client.dto.v1_0.NavigationMenuItem;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class NavigationMenuItemSerDes {

	public static NavigationMenuItem toDTO(String json) {
		NavigationMenuItemJSONParser navigationMenuItemJSONParser =
			new NavigationMenuItemJSONParser();

		return navigationMenuItemJSONParser.parseToDTO(json);
	}

	public static NavigationMenuItem[] toDTOs(String json) {
		NavigationMenuItemJSONParser navigationMenuItemJSONParser =
			new NavigationMenuItemJSONParser();

		return navigationMenuItemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(NavigationMenuItem navigationMenuItem) {
		if (navigationMenuItem == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (navigationMenuItem.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(navigationMenuItem.getCreator()));
		}

		if (navigationMenuItem.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					navigationMenuItem.getDateCreated()));

			sb.append("\"");
		}

		if (navigationMenuItem.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					navigationMenuItem.getDateModified()));

			sb.append("\"");
		}

		if (navigationMenuItem.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(navigationMenuItem.getId());
		}

		if (navigationMenuItem.getLink() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"link\": ");

			sb.append("\"");

			sb.append(_escape(navigationMenuItem.getLink()));

			sb.append("\"");
		}

		if (navigationMenuItem.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(navigationMenuItem.getName()));

			sb.append("\"");
		}

		if (navigationMenuItem.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(navigationMenuItem.getName_i18n()));
		}

		if (navigationMenuItem.getNavigationMenuItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"navigationMenuItems\": ");

			sb.append("[");

			for (int i = 0;
				 i < navigationMenuItem.getNavigationMenuItems().length; i++) {

				sb.append(
					String.valueOf(
						navigationMenuItem.getNavigationMenuItems()[i]));

				if ((i + 1) <
						navigationMenuItem.getNavigationMenuItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (navigationMenuItem.getParentNavigationMenuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentNavigationMenuId\": ");

			sb.append(navigationMenuItem.getParentNavigationMenuId());
		}

		if (navigationMenuItem.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(navigationMenuItem.getType()));

			sb.append("\"");
		}

		if (navigationMenuItem.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(navigationMenuItem.getUrl()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		NavigationMenuItemJSONParser navigationMenuItemJSONParser =
			new NavigationMenuItemJSONParser();

		return navigationMenuItemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		NavigationMenuItem navigationMenuItem) {

		if (navigationMenuItem == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (navigationMenuItem.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(navigationMenuItem.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				navigationMenuItem.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				navigationMenuItem.getDateModified()));

		if (navigationMenuItem.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(navigationMenuItem.getId()));
		}

		if (navigationMenuItem.getLink() == null) {
			map.put("link", null);
		}
		else {
			map.put("link", String.valueOf(navigationMenuItem.getLink()));
		}

		if (navigationMenuItem.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(navigationMenuItem.getName()));
		}

		if (navigationMenuItem.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put(
				"name_i18n", String.valueOf(navigationMenuItem.getName_i18n()));
		}

		if (navigationMenuItem.getNavigationMenuItems() == null) {
			map.put("navigationMenuItems", null);
		}
		else {
			map.put(
				"navigationMenuItems",
				String.valueOf(navigationMenuItem.getNavigationMenuItems()));
		}

		if (navigationMenuItem.getParentNavigationMenuId() == null) {
			map.put("parentNavigationMenuId", null);
		}
		else {
			map.put(
				"parentNavigationMenuId",
				String.valueOf(navigationMenuItem.getParentNavigationMenuId()));
		}

		if (navigationMenuItem.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(navigationMenuItem.getType()));
		}

		if (navigationMenuItem.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(navigationMenuItem.getUrl()));
		}

		return map;
	}

	public static class NavigationMenuItemJSONParser
		extends BaseJSONParser<NavigationMenuItem> {

		@Override
		protected NavigationMenuItem createDTO() {
			return new NavigationMenuItem();
		}

		@Override
		protected NavigationMenuItem[] createDTOArray(int size) {
			return new NavigationMenuItem[size];
		}

		@Override
		protected void setField(
			NavigationMenuItem navigationMenuItem, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "link")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setLink((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setName_i18n(
						(Map)NavigationMenuItemSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "navigationMenuItems")) {

				if (jsonParserFieldValue != null) {
					navigationMenuItem.setNavigationMenuItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> NavigationMenuItemSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new NavigationMenuItem[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentNavigationMenuId")) {

				if (jsonParserFieldValue != null) {
					navigationMenuItem.setParentNavigationMenuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					navigationMenuItem.setUrl((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
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
			sb.append("\":");

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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}