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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentViewportStyle;
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
public class FragmentViewportStyleSerDes {

	public static FragmentViewportStyle toDTO(String json) {
		FragmentViewportStyleJSONParser fragmentViewportStyleJSONParser =
			new FragmentViewportStyleJSONParser();

		return fragmentViewportStyleJSONParser.parseToDTO(json);
	}

	public static FragmentViewportStyle[] toDTOs(String json) {
		FragmentViewportStyleJSONParser fragmentViewportStyleJSONParser =
			new FragmentViewportStyleJSONParser();

		return fragmentViewportStyleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentViewportStyle fragmentViewportStyle) {
		if (fragmentViewportStyle == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentViewportStyle.getHidden() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hidden\": ");

			sb.append(fragmentViewportStyle.getHidden());
		}

		if (fragmentViewportStyle.getMarginBottom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginBottom\": ");

			sb.append("\"");

			sb.append(_escape(fragmentViewportStyle.getMarginBottom()));

			sb.append("\"");
		}

		if (fragmentViewportStyle.getMarginLeft() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginLeft\": ");

			sb.append("\"");

			sb.append(_escape(fragmentViewportStyle.getMarginLeft()));

			sb.append("\"");
		}

		if (fragmentViewportStyle.getMarginRight() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginRight\": ");

			sb.append("\"");

			sb.append(_escape(fragmentViewportStyle.getMarginRight()));

			sb.append("\"");
		}

		if (fragmentViewportStyle.getMarginTop() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginTop\": ");

			sb.append("\"");

			sb.append(_escape(fragmentViewportStyle.getMarginTop()));

			sb.append("\"");
		}

		if (fragmentViewportStyle.getPaddingBottom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingBottom\": ");

			sb.append("\"");

			sb.append(_escape(fragmentViewportStyle.getPaddingBottom()));

			sb.append("\"");
		}

		if (fragmentViewportStyle.getPaddingLeft() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingLeft\": ");

			sb.append("\"");

			sb.append(_escape(fragmentViewportStyle.getPaddingLeft()));

			sb.append("\"");
		}

		if (fragmentViewportStyle.getPaddingRight() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingRight\": ");

			sb.append("\"");

			sb.append(_escape(fragmentViewportStyle.getPaddingRight()));

			sb.append("\"");
		}

		if (fragmentViewportStyle.getPaddingTop() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingTop\": ");

			sb.append("\"");

			sb.append(_escape(fragmentViewportStyle.getPaddingTop()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentViewportStyleJSONParser fragmentViewportStyleJSONParser =
			new FragmentViewportStyleJSONParser();

		return fragmentViewportStyleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentViewportStyle fragmentViewportStyle) {

		if (fragmentViewportStyle == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentViewportStyle.getHidden() == null) {
			map.put("hidden", null);
		}
		else {
			map.put(
				"hidden", String.valueOf(fragmentViewportStyle.getHidden()));
		}

		if (fragmentViewportStyle.getMarginBottom() == null) {
			map.put("marginBottom", null);
		}
		else {
			map.put(
				"marginBottom",
				String.valueOf(fragmentViewportStyle.getMarginBottom()));
		}

		if (fragmentViewportStyle.getMarginLeft() == null) {
			map.put("marginLeft", null);
		}
		else {
			map.put(
				"marginLeft",
				String.valueOf(fragmentViewportStyle.getMarginLeft()));
		}

		if (fragmentViewportStyle.getMarginRight() == null) {
			map.put("marginRight", null);
		}
		else {
			map.put(
				"marginRight",
				String.valueOf(fragmentViewportStyle.getMarginRight()));
		}

		if (fragmentViewportStyle.getMarginTop() == null) {
			map.put("marginTop", null);
		}
		else {
			map.put(
				"marginTop",
				String.valueOf(fragmentViewportStyle.getMarginTop()));
		}

		if (fragmentViewportStyle.getPaddingBottom() == null) {
			map.put("paddingBottom", null);
		}
		else {
			map.put(
				"paddingBottom",
				String.valueOf(fragmentViewportStyle.getPaddingBottom()));
		}

		if (fragmentViewportStyle.getPaddingLeft() == null) {
			map.put("paddingLeft", null);
		}
		else {
			map.put(
				"paddingLeft",
				String.valueOf(fragmentViewportStyle.getPaddingLeft()));
		}

		if (fragmentViewportStyle.getPaddingRight() == null) {
			map.put("paddingRight", null);
		}
		else {
			map.put(
				"paddingRight",
				String.valueOf(fragmentViewportStyle.getPaddingRight()));
		}

		if (fragmentViewportStyle.getPaddingTop() == null) {
			map.put("paddingTop", null);
		}
		else {
			map.put(
				"paddingTop",
				String.valueOf(fragmentViewportStyle.getPaddingTop()));
		}

		return map;
	}

	public static class FragmentViewportStyleJSONParser
		extends BaseJSONParser<FragmentViewportStyle> {

		@Override
		protected FragmentViewportStyle createDTO() {
			return new FragmentViewportStyle();
		}

		@Override
		protected FragmentViewportStyle[] createDTOArray(int size) {
			return new FragmentViewportStyle[size];
		}

		@Override
		protected void setField(
			FragmentViewportStyle fragmentViewportStyle,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "hidden")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setHidden(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "marginBottom")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setMarginBottom(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "marginLeft")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setMarginLeft(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "marginRight")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setMarginRight(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "marginTop")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setMarginTop(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingBottom")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setPaddingBottom(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingLeft")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setPaddingLeft(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingRight")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setPaddingRight(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingTop")) {
				if (jsonParserFieldValue != null) {
					fragmentViewportStyle.setPaddingTop(
						(String)jsonParserFieldValue);
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