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

package com.liferay.headless.admin.content.client.serdes.v1_0;

import com.liferay.headless.admin.content.client.dto.v1_0.PageDefinition;
import com.liferay.headless.admin.content.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageDefinitionSerDes {

	public static PageDefinition toDTO(String json) {
		PageDefinitionJSONParser pageDefinitionJSONParser =
			new PageDefinitionJSONParser();

		return pageDefinitionJSONParser.parseToDTO(json);
	}

	public static PageDefinition[] toDTOs(String json) {
		PageDefinitionJSONParser pageDefinitionJSONParser =
			new PageDefinitionJSONParser();

		return pageDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageDefinition pageDefinition) {
		if (pageDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageDefinition.getPageElement() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageElement\": ");

			sb.append(pageDefinition.getPageElement());
		}

		if (pageDefinition.getSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"settings\": ");

			sb.append(pageDefinition.getSettings());
		}

		if (pageDefinition.getVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append(pageDefinition.getVersion());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageDefinitionJSONParser pageDefinitionJSONParser =
			new PageDefinitionJSONParser();

		return pageDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PageDefinition pageDefinition) {
		if (pageDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageDefinition.getPageElement() == null) {
			map.put("pageElement", null);
		}
		else {
			map.put(
				"pageElement", String.valueOf(pageDefinition.getPageElement()));
		}

		if (pageDefinition.getSettings() == null) {
			map.put("settings", null);
		}
		else {
			map.put("settings", String.valueOf(pageDefinition.getSettings()));
		}

		if (pageDefinition.getVersion() == null) {
			map.put("version", null);
		}
		else {
			map.put("version", String.valueOf(pageDefinition.getVersion()));
		}

		return map;
	}

	public static class PageDefinitionJSONParser
		extends BaseJSONParser<PageDefinition> {

		@Override
		protected PageDefinition createDTO() {
			return new PageDefinition();
		}

		@Override
		protected PageDefinition[] createDTOArray(int size) {
			return new PageDefinition[size];
		}

		@Override
		protected void setField(
			PageDefinition pageDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "pageElement")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setPageElement(
						PageElementSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "settings")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setSettings(
						SettingsSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "version")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setVersion(
						Double.valueOf((String)jsonParserFieldValue));
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