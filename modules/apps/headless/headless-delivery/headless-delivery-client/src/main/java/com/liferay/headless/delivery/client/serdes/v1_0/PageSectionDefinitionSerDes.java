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

import com.liferay.headless.delivery.client.dto.v1_0.PageSectionDefinition;
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
public class PageSectionDefinitionSerDes {

	public static PageSectionDefinition toDTO(String json) {
		PageSectionDefinitionJSONParser pageSectionDefinitionJSONParser =
			new PageSectionDefinitionJSONParser();

		return pageSectionDefinitionJSONParser.parseToDTO(json);
	}

	public static PageSectionDefinition[] toDTOs(String json) {
		PageSectionDefinitionJSONParser pageSectionDefinitionJSONParser =
			new PageSectionDefinitionJSONParser();

		return pageSectionDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageSectionDefinition pageSectionDefinition) {
		if (pageSectionDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageSectionDefinition.getBackgroundColor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundColor\": ");

			sb.append("\"");

			sb.append(_escape(pageSectionDefinition.getBackgroundColor()));

			sb.append("\"");
		}

		if (pageSectionDefinition.getBackgroundFragmentImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundFragmentImage\": ");

			sb.append(
				String.valueOf(
					pageSectionDefinition.getBackgroundFragmentImage()));
		}

		if (pageSectionDefinition.getBackgroundImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundImage\": ");

			sb.append(
				String.valueOf(pageSectionDefinition.getBackgroundImage()));
		}

		if (pageSectionDefinition.getFragmentLink() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentLink\": ");

			sb.append(String.valueOf(pageSectionDefinition.getFragmentLink()));
		}

		if (pageSectionDefinition.getLayout() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"layout\": ");

			sb.append(String.valueOf(pageSectionDefinition.getLayout()));
		}

		if (pageSectionDefinition.getStyles() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"styles\": ");

			sb.append(_toJSON(pageSectionDefinition.getStyles()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageSectionDefinitionJSONParser pageSectionDefinitionJSONParser =
			new PageSectionDefinitionJSONParser();

		return pageSectionDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageSectionDefinition pageSectionDefinition) {

		if (pageSectionDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageSectionDefinition.getBackgroundColor() == null) {
			map.put("backgroundColor", null);
		}
		else {
			map.put(
				"backgroundColor",
				String.valueOf(pageSectionDefinition.getBackgroundColor()));
		}

		if (pageSectionDefinition.getBackgroundFragmentImage() == null) {
			map.put("backgroundFragmentImage", null);
		}
		else {
			map.put(
				"backgroundFragmentImage",
				String.valueOf(
					pageSectionDefinition.getBackgroundFragmentImage()));
		}

		if (pageSectionDefinition.getBackgroundImage() == null) {
			map.put("backgroundImage", null);
		}
		else {
			map.put(
				"backgroundImage",
				String.valueOf(pageSectionDefinition.getBackgroundImage()));
		}

		if (pageSectionDefinition.getFragmentLink() == null) {
			map.put("fragmentLink", null);
		}
		else {
			map.put(
				"fragmentLink",
				String.valueOf(pageSectionDefinition.getFragmentLink()));
		}

		if (pageSectionDefinition.getLayout() == null) {
			map.put("layout", null);
		}
		else {
			map.put(
				"layout", String.valueOf(pageSectionDefinition.getLayout()));
		}

		if (pageSectionDefinition.getStyles() == null) {
			map.put("styles", null);
		}
		else {
			map.put(
				"styles", String.valueOf(pageSectionDefinition.getStyles()));
		}

		return map;
	}

	public static class PageSectionDefinitionJSONParser
		extends BaseJSONParser<PageSectionDefinition> {

		@Override
		protected PageSectionDefinition createDTO() {
			return new PageSectionDefinition();
		}

		@Override
		protected PageSectionDefinition[] createDTOArray(int size) {
			return new PageSectionDefinition[size];
		}

		@Override
		protected void setField(
			PageSectionDefinition pageSectionDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "backgroundColor")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setBackgroundColor(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "backgroundFragmentImage")) {

				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setBackgroundFragmentImage(
						FragmentImageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "backgroundImage")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setBackgroundImage(
						BackgroundImageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentLink")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setFragmentLink(
						FragmentLinkSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "layout")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setLayout(
						LayoutSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "styles")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setStyles(
						(Map)PageSectionDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}