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

import com.liferay.headless.delivery.client.dto.v1_0.SectionDefinition;
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
public class SectionDefinitionSerDes {

	public static SectionDefinition toDTO(String json) {
		SectionDefinitionJSONParser sectionDefinitionJSONParser =
			new SectionDefinitionJSONParser();

		return sectionDefinitionJSONParser.parseToDTO(json);
	}

	public static SectionDefinition[] toDTOs(String json) {
		SectionDefinitionJSONParser sectionDefinitionJSONParser =
			new SectionDefinitionJSONParser();

		return sectionDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SectionDefinition sectionDefinition) {
		if (sectionDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (sectionDefinition.getBackgroundColorCssClass() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundColorCssClass\": ");

			sb.append("\"");

			sb.append(_escape(sectionDefinition.getBackgroundColorCssClass()));

			sb.append("\"");
		}

		if (sectionDefinition.getBackgroundImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundImage\": ");

			sb.append(String.valueOf(sectionDefinition.getBackgroundImage()));
		}

		if (sectionDefinition.getContainerType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"containerType\": ");

			sb.append("\"");

			sb.append(sectionDefinition.getContainerType());

			sb.append("\"");
		}

		if (sectionDefinition.getPaddingBottom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingBottom\": ");

			sb.append(sectionDefinition.getPaddingBottom());
		}

		if (sectionDefinition.getPaddingHorizontal() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingHorizontal\": ");

			sb.append(sectionDefinition.getPaddingHorizontal());
		}

		if (sectionDefinition.getPaddingTop() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingTop\": ");

			sb.append(sectionDefinition.getPaddingTop());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SectionDefinitionJSONParser sectionDefinitionJSONParser =
			new SectionDefinitionJSONParser();

		return sectionDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SectionDefinition sectionDefinition) {

		if (sectionDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (sectionDefinition.getBackgroundColorCssClass() == null) {
			map.put("backgroundColorCssClass", null);
		}
		else {
			map.put(
				"backgroundColorCssClass",
				String.valueOf(sectionDefinition.getBackgroundColorCssClass()));
		}

		if (sectionDefinition.getBackgroundImage() == null) {
			map.put("backgroundImage", null);
		}
		else {
			map.put(
				"backgroundImage",
				String.valueOf(sectionDefinition.getBackgroundImage()));
		}

		if (sectionDefinition.getContainerType() == null) {
			map.put("containerType", null);
		}
		else {
			map.put(
				"containerType",
				String.valueOf(sectionDefinition.getContainerType()));
		}

		if (sectionDefinition.getPaddingBottom() == null) {
			map.put("paddingBottom", null);
		}
		else {
			map.put(
				"paddingBottom",
				String.valueOf(sectionDefinition.getPaddingBottom()));
		}

		if (sectionDefinition.getPaddingHorizontal() == null) {
			map.put("paddingHorizontal", null);
		}
		else {
			map.put(
				"paddingHorizontal",
				String.valueOf(sectionDefinition.getPaddingHorizontal()));
		}

		if (sectionDefinition.getPaddingTop() == null) {
			map.put("paddingTop", null);
		}
		else {
			map.put(
				"paddingTop",
				String.valueOf(sectionDefinition.getPaddingTop()));
		}

		return map;
	}

	public static class SectionDefinitionJSONParser
		extends BaseJSONParser<SectionDefinition> {

		@Override
		protected SectionDefinition createDTO() {
			return new SectionDefinition();
		}

		@Override
		protected SectionDefinition[] createDTOArray(int size) {
			return new SectionDefinition[size];
		}

		@Override
		protected void setField(
			SectionDefinition sectionDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "backgroundColorCssClass")) {

				if (jsonParserFieldValue != null) {
					sectionDefinition.setBackgroundColorCssClass(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "backgroundImage")) {
				if (jsonParserFieldValue != null) {
					sectionDefinition.setBackgroundImage(
						FragmentImageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "containerType")) {
				if (jsonParserFieldValue != null) {
					sectionDefinition.setContainerType(
						SectionDefinition.ContainerType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingBottom")) {
				if (jsonParserFieldValue != null) {
					sectionDefinition.setPaddingBottom(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingHorizontal")) {
				if (jsonParserFieldValue != null) {
					sectionDefinition.setPaddingHorizontal(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paddingTop")) {
				if (jsonParserFieldValue != null) {
					sectionDefinition.setPaddingTop(
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