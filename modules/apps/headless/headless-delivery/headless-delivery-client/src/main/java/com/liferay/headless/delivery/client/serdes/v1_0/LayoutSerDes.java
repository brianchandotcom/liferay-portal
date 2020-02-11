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

import com.liferay.headless.delivery.client.dto.v1_0.Layout;
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
public class LayoutSerDes {

	public static Layout toDTO(String json) {
		LayoutJSONParser layoutJSONParser = new LayoutJSONParser();

		return layoutJSONParser.parseToDTO(json);
	}

	public static Layout[] toDTOs(String json) {
		LayoutJSONParser layoutJSONParser = new LayoutJSONParser();

		return layoutJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Layout layout) {
		if (layout == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (layout.getContainerType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"containerType\": ");

			sb.append("\"");

			sb.append(layout.getContainerType());

			sb.append("\"");
		}

		if (layout.getPaddingBottom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingBottom\": ");

			sb.append(layout.getPaddingBottom());
		}

		if (layout.getPaddingHorizontal() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingHorizontal\": ");

			sb.append(layout.getPaddingHorizontal());
		}

		if (layout.getPaddingTop() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingTop\": ");

			sb.append(layout.getPaddingTop());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LayoutJSONParser layoutJSONParser = new LayoutJSONParser();

		return layoutJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Layout layout) {
		if (layout == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (layout.getContainerType() == null) {
			map.put("containerType", null);
		}
		else {
			map.put("containerType", String.valueOf(layout.getContainerType()));
		}

		if (layout.getPaddingBottom() == null) {
			map.put("paddingBottom", null);
		}
		else {
			map.put("paddingBottom", String.valueOf(layout.getPaddingBottom()));
		}

		if (layout.getPaddingHorizontal() == null) {
			map.put("paddingHorizontal", null);
		}
		else {
			map.put(
				"paddingHorizontal",
				String.valueOf(layout.getPaddingHorizontal()));
		}

		if (layout.getPaddingTop() == null) {
			map.put("paddingTop", null);
		}
		else {
			map.put("paddingTop", String.valueOf(layout.getPaddingTop()));
		}

		return map;
	}

	public static class LayoutJSONParser extends BaseJSONParser<Layout> {

		@Override
		protected Layout createDTO() {
			return new Layout();
		}

		@Override
		protected Layout[] createDTOArray(int size) {
			return new Layout[size];
		}

		@Override
		protected void setField(
			Layout layout, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "containerType")) {
				if (jsonParserFieldValue != null) {
					layout.setContainerType(
						Layout.ContainerType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingBottom")) {
				if (jsonParserFieldValue != null) {
					layout.setPaddingBottom(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingHorizontal")) {
				if (jsonParserFieldValue != null) {
					layout.setPaddingHorizontal(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingTop")) {
				if (jsonParserFieldValue != null) {
					layout.setPaddingTop(
						Integer.valueOf((String)jsonParserFieldValue));
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