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

import com.liferay.headless.delivery.client.dto.v1_0.PageCollectionDefinition;
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
public class PageCollectionDefinitionSerDes {

	public static PageCollectionDefinition toDTO(String json) {
		PageCollectionDefinitionJSONParser pageCollectionDefinitionJSONParser =
			new PageCollectionDefinitionJSONParser();

		return pageCollectionDefinitionJSONParser.parseToDTO(json);
	}

	public static PageCollectionDefinition[] toDTOs(String json) {
		PageCollectionDefinitionJSONParser pageCollectionDefinitionJSONParser =
			new PageCollectionDefinitionJSONParser();

		return pageCollectionDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		PageCollectionDefinition pageCollectionDefinition) {

		if (pageCollectionDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageCollectionDefinition.getCollectionConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionConfig\": ");

			sb.append("\"");

			sb.append(_escape(pageCollectionDefinition.getCollectionConfig()));

			sb.append("\"");
		}

		if (pageCollectionDefinition.getNumberOfColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfColumns\": ");

			sb.append(pageCollectionDefinition.getNumberOfColumns());
		}

		if (pageCollectionDefinition.getNumberOfItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfItems\": ");

			sb.append(pageCollectionDefinition.getNumberOfItems());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageCollectionDefinitionJSONParser pageCollectionDefinitionJSONParser =
			new PageCollectionDefinitionJSONParser();

		return pageCollectionDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageCollectionDefinition pageCollectionDefinition) {

		if (pageCollectionDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageCollectionDefinition.getCollectionConfig() == null) {
			map.put("collectionConfig", null);
		}
		else {
			map.put(
				"collectionConfig",
				String.valueOf(pageCollectionDefinition.getCollectionConfig()));
		}

		if (pageCollectionDefinition.getNumberOfColumns() == null) {
			map.put("numberOfColumns", null);
		}
		else {
			map.put(
				"numberOfColumns",
				String.valueOf(pageCollectionDefinition.getNumberOfColumns()));
		}

		if (pageCollectionDefinition.getNumberOfItems() == null) {
			map.put("numberOfItems", null);
		}
		else {
			map.put(
				"numberOfItems",
				String.valueOf(pageCollectionDefinition.getNumberOfItems()));
		}

		return map;
	}

	public static class PageCollectionDefinitionJSONParser
		extends BaseJSONParser<PageCollectionDefinition> {

		@Override
		protected PageCollectionDefinition createDTO() {
			return new PageCollectionDefinition();
		}

		@Override
		protected PageCollectionDefinition[] createDTOArray(int size) {
			return new PageCollectionDefinition[size];
		}

		@Override
		protected void setField(
			PageCollectionDefinition pageCollectionDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collectionConfig")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setCollectionConfig(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfColumns")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setNumberOfColumns(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfItems")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setNumberOfItems(
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