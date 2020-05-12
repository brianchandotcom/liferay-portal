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

import com.liferay.headless.delivery.client.dto.v1_0.PageRowDefinition;
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
public class PageRowDefinitionSerDes {

	public static PageRowDefinition toDTO(String json) {
		PageRowDefinitionJSONParser pageRowDefinitionJSONParser =
			new PageRowDefinitionJSONParser();

		return pageRowDefinitionJSONParser.parseToDTO(json);
	}

	public static PageRowDefinition[] toDTOs(String json) {
		PageRowDefinitionJSONParser pageRowDefinitionJSONParser =
			new PageRowDefinitionJSONParser();

		return pageRowDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageRowDefinition pageRowDefinition) {
		if (pageRowDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageRowDefinition.getGutters() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"gutters\": ");

			sb.append(pageRowDefinition.getGutters());
		}

		if (pageRowDefinition.getModulesPerRow() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modulesPerRow\": ");

			sb.append(pageRowDefinition.getModulesPerRow());
		}

		if (pageRowDefinition.getNumberOfColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfColumns\": ");

			sb.append(pageRowDefinition.getNumberOfColumns());
		}

		if (pageRowDefinition.getReverseOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reverseOrder\": ");

			sb.append(pageRowDefinition.getReverseOrder());
		}

		if (pageRowDefinition.getVerticalAlignment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"verticalAlignment\": ");

			sb.append("\"");

			sb.append(_escape(pageRowDefinition.getVerticalAlignment()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageRowDefinitionJSONParser pageRowDefinitionJSONParser =
			new PageRowDefinitionJSONParser();

		return pageRowDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageRowDefinition pageRowDefinition) {

		if (pageRowDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageRowDefinition.getGutters() == null) {
			map.put("gutters", null);
		}
		else {
			map.put("gutters", String.valueOf(pageRowDefinition.getGutters()));
		}

		if (pageRowDefinition.getModulesPerRow() == null) {
			map.put("modulesPerRow", null);
		}
		else {
			map.put(
				"modulesPerRow",
				String.valueOf(pageRowDefinition.getModulesPerRow()));
		}

		if (pageRowDefinition.getNumberOfColumns() == null) {
			map.put("numberOfColumns", null);
		}
		else {
			map.put(
				"numberOfColumns",
				String.valueOf(pageRowDefinition.getNumberOfColumns()));
		}

		if (pageRowDefinition.getReverseOrder() == null) {
			map.put("reverseOrder", null);
		}
		else {
			map.put(
				"reverseOrder",
				String.valueOf(pageRowDefinition.getReverseOrder()));
		}

		if (pageRowDefinition.getVerticalAlignment() == null) {
			map.put("verticalAlignment", null);
		}
		else {
			map.put(
				"verticalAlignment",
				String.valueOf(pageRowDefinition.getVerticalAlignment()));
		}

		return map;
	}

	public static class PageRowDefinitionJSONParser
		extends BaseJSONParser<PageRowDefinition> {

		@Override
		protected PageRowDefinition createDTO() {
			return new PageRowDefinition();
		}

		@Override
		protected PageRowDefinition[] createDTOArray(int size) {
			return new PageRowDefinition[size];
		}

		@Override
		protected void setField(
			PageRowDefinition pageRowDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "gutters")) {
				if (jsonParserFieldValue != null) {
					pageRowDefinition.setGutters((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modulesPerRow")) {
				if (jsonParserFieldValue != null) {
					pageRowDefinition.setModulesPerRow(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfColumns")) {
				if (jsonParserFieldValue != null) {
					pageRowDefinition.setNumberOfColumns(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "reverseOrder")) {
				if (jsonParserFieldValue != null) {
					pageRowDefinition.setReverseOrder(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "verticalAlignment")) {
				if (jsonParserFieldValue != null) {
					pageRowDefinition.setVerticalAlignment(
						(String)jsonParserFieldValue);
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