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

import com.liferay.headless.delivery.client.dto.v1_0.PageElement;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class PageElementSerDes {

	public static PageElement toDTO(String json) {
		PageElementJSONParser pageElementJSONParser =
			new PageElementJSONParser();

		return pageElementJSONParser.parseToDTO(json);
	}

	public static PageElement[] toDTOs(String json) {
		PageElementJSONParser pageElementJSONParser =
			new PageElementJSONParser();

		return pageElementJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageElement pageElement) {
		if (pageElement == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageElement.getDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definition\": ");

			sb.append("\"");

			sb.append(_escape(pageElement.getDefinition()));

			sb.append("\"");
		}

		if (pageElement.getPageElements() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageElements\": ");

			sb.append("[");

			for (int i = 0; i < pageElement.getPageElements().length; i++) {
				sb.append(String.valueOf(pageElement.getPageElements()[i]));

				if ((i + 1) < pageElement.getPageElements().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageElement.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(pageElement.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageElementJSONParser pageElementJSONParser =
			new PageElementJSONParser();

		return pageElementJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PageElement pageElement) {
		if (pageElement == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageElement.getDefinition() == null) {
			map.put("definition", null);
		}
		else {
			map.put("definition", String.valueOf(pageElement.getDefinition()));
		}

		if (pageElement.getPageElements() == null) {
			map.put("pageElements", null);
		}
		else {
			map.put(
				"pageElements", String.valueOf(pageElement.getPageElements()));
		}

		if (pageElement.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(pageElement.getType()));
		}

		return map;
	}

	public static class PageElementJSONParser
		extends BaseJSONParser<PageElement> {

		@Override
		protected PageElement createDTO() {
			return new PageElement();
		}

		@Override
		protected PageElement[] createDTOArray(int size) {
			return new PageElement[size];
		}

		@Override
		protected void setField(
			PageElement pageElement, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "definition")) {
				if (jsonParserFieldValue != null) {
					pageElement.setDefinition((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageElements")) {
				if (jsonParserFieldValue != null) {
					pageElement.setPageElements(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PageElementSerDes.toDTO((String)object)
						).toArray(
							size -> new PageElement[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					pageElement.setType(
						PageElement.Type.create((String)jsonParserFieldValue));
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