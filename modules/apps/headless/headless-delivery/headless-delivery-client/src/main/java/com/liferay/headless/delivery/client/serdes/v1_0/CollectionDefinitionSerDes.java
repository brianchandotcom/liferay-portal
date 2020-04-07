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

import com.liferay.headless.delivery.client.dto.v1_0.CollectionDefinition;
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
public class CollectionDefinitionSerDes {

	public static CollectionDefinition toDTO(String json) {
		CollectionDefinitionJSONParser collectionDefinitionJSONParser =
			new CollectionDefinitionJSONParser();

		return collectionDefinitionJSONParser.parseToDTO(json);
	}

	public static CollectionDefinition[] toDTOs(String json) {
		CollectionDefinitionJSONParser collectionDefinitionJSONParser =
			new CollectionDefinitionJSONParser();

		return collectionDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CollectionDefinition collectionDefinition) {
		if (collectionDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (collectionDefinition.getCollectionConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionConfig\": ");

			sb.append("\"");

			sb.append(_escape(collectionDefinition.getCollectionConfig()));

			sb.append("\"");
		}

		if (collectionDefinition.getListFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listFormat\": ");

			sb.append("\"");

			sb.append(_escape(collectionDefinition.getListFormat()));

			sb.append("\"");
		}

		if (collectionDefinition.getNumberOfColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfColumns\": ");

			sb.append(collectionDefinition.getNumberOfColumns());
		}

		if (collectionDefinition.getNumberOfItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfItems\": ");

			sb.append(collectionDefinition.getNumberOfItems());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CollectionDefinitionJSONParser collectionDefinitionJSONParser =
			new CollectionDefinitionJSONParser();

		return collectionDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		CollectionDefinition collectionDefinition) {

		if (collectionDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (collectionDefinition.getCollectionConfig() == null) {
			map.put("collectionConfig", null);
		}
		else {
			map.put(
				"collectionConfig",
				String.valueOf(collectionDefinition.getCollectionConfig()));
		}

		if (collectionDefinition.getListFormat() == null) {
			map.put("listFormat", null);
		}
		else {
			map.put(
				"listFormat",
				String.valueOf(collectionDefinition.getListFormat()));
		}

		if (collectionDefinition.getNumberOfColumns() == null) {
			map.put("numberOfColumns", null);
		}
		else {
			map.put(
				"numberOfColumns",
				String.valueOf(collectionDefinition.getNumberOfColumns()));
		}

		if (collectionDefinition.getNumberOfItems() == null) {
			map.put("numberOfItems", null);
		}
		else {
			map.put(
				"numberOfItems",
				String.valueOf(collectionDefinition.getNumberOfItems()));
		}

		return map;
	}

	public static class CollectionDefinitionJSONParser
		extends BaseJSONParser<CollectionDefinition> {

		@Override
		protected CollectionDefinition createDTO() {
			return new CollectionDefinition();
		}

		@Override
		protected CollectionDefinition[] createDTOArray(int size) {
			return new CollectionDefinition[size];
		}

		@Override
		protected void setField(
			CollectionDefinition collectionDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collectionConfig")) {
				if (jsonParserFieldValue != null) {
					collectionDefinition.setCollectionConfig(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "listFormat")) {
				if (jsonParserFieldValue != null) {
					collectionDefinition.setListFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfColumns")) {
				if (jsonParserFieldValue != null) {
					collectionDefinition.setNumberOfColumns(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfItems")) {
				if (jsonParserFieldValue != null) {
					collectionDefinition.setNumberOfItems(
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