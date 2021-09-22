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

import com.liferay.headless.delivery.client.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class DisplayPageTemplateSerDes {

	public static DisplayPageTemplate toDTO(String json) {
		DisplayPageTemplateJSONParser displayPageTemplateJSONParser =
			new DisplayPageTemplateJSONParser();

		return displayPageTemplateJSONParser.parseToDTO(json);
	}

	public static DisplayPageTemplate[] toDTOs(String json) {
		DisplayPageTemplateJSONParser displayPageTemplateJSONParser =
			new DisplayPageTemplateJSONParser();

		return displayPageTemplateJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DisplayPageTemplate displayPageTemplate) {
		if (displayPageTemplate == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (displayPageTemplate.getContentSubtype() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentSubtype\": ");

			sb.append(String.valueOf(displayPageTemplate.getContentSubtype()));
		}

		if (displayPageTemplate.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append(String.valueOf(displayPageTemplate.getContentType()));
		}

		if (displayPageTemplate.getDefaultTemplate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultTemplate\": ");

			sb.append(displayPageTemplate.getDefaultTemplate());
		}

		if (displayPageTemplate.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(displayPageTemplate.getKey()));

			sb.append("\"");
		}

		if (displayPageTemplate.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(displayPageTemplate.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DisplayPageTemplateJSONParser displayPageTemplateJSONParser =
			new DisplayPageTemplateJSONParser();

		return displayPageTemplateJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DisplayPageTemplate displayPageTemplate) {

		if (displayPageTemplate == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (displayPageTemplate.getContentSubtype() == null) {
			map.put("contentSubtype", null);
		}
		else {
			map.put(
				"contentSubtype",
				String.valueOf(displayPageTemplate.getContentSubtype()));
		}

		if (displayPageTemplate.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put(
				"contentType",
				String.valueOf(displayPageTemplate.getContentType()));
		}

		if (displayPageTemplate.getDefaultTemplate() == null) {
			map.put("defaultTemplate", null);
		}
		else {
			map.put(
				"defaultTemplate",
				String.valueOf(displayPageTemplate.getDefaultTemplate()));
		}

		if (displayPageTemplate.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(displayPageTemplate.getKey()));
		}

		if (displayPageTemplate.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(displayPageTemplate.getName()));
		}

		return map;
	}

	public static class DisplayPageTemplateJSONParser
		extends BaseJSONParser<DisplayPageTemplate> {

		@Override
		protected DisplayPageTemplate createDTO() {
			return new DisplayPageTemplate();
		}

		@Override
		protected DisplayPageTemplate[] createDTOArray(int size) {
			return new DisplayPageTemplate[size];
		}

		@Override
		protected void setField(
			DisplayPageTemplate displayPageTemplate, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentSubtype")) {
				if (jsonParserFieldValue != null) {
					displayPageTemplate.setContentSubtype(
						ContentSubtypeSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					displayPageTemplate.setContentType(
						ContentTypeSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultTemplate")) {
				if (jsonParserFieldValue != null) {
					displayPageTemplate.setDefaultTemplate(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					displayPageTemplate.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					displayPageTemplate.setName((String)jsonParserFieldValue);
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