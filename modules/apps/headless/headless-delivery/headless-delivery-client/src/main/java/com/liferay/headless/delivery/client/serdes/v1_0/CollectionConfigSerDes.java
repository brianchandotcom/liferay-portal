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

import com.liferay.headless.delivery.client.dto.v1_0.CollectionConfig;
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
public class CollectionConfigSerDes {

	public static CollectionConfig toDTO(String json) {
		CollectionConfigJSONParser collectionConfigJSONParser =
			new CollectionConfigJSONParser();

		return collectionConfigJSONParser.parseToDTO(json);
	}

	public static CollectionConfig[] toDTOs(String json) {
		CollectionConfigJSONParser collectionConfigJSONParser =
			new CollectionConfigJSONParser();

		return collectionConfigJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CollectionConfig collectionConfig) {
		if (collectionConfig == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (collectionConfig.getCollectionReference() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionReference\": ");

			if (collectionConfig.getCollectionReference() instanceof String) {
				sb.append("\"");
				sb.append((String)collectionConfig.getCollectionReference());
				sb.append("\"");
			}
			else {
				sb.append(collectionConfig.getCollectionReference());
			}
		}

		if (collectionConfig.getCollectionType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionType\": ");

			sb.append("\"");

			sb.append(collectionConfig.getCollectionType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CollectionConfigJSONParser collectionConfigJSONParser =
			new CollectionConfigJSONParser();

		return collectionConfigJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CollectionConfig collectionConfig) {
		if (collectionConfig == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (collectionConfig.getCollectionReference() == null) {
			map.put("collectionReference", null);
		}
		else {
			map.put(
				"collectionReference",
				String.valueOf(collectionConfig.getCollectionReference()));
		}

		if (collectionConfig.getCollectionType() == null) {
			map.put("collectionType", null);
		}
		else {
			map.put(
				"collectionType",
				String.valueOf(collectionConfig.getCollectionType()));
		}

		return map;
	}

	public static class CollectionConfigJSONParser
		extends BaseJSONParser<CollectionConfig> {

		@Override
		protected CollectionConfig createDTO() {
			return new CollectionConfig();
		}

		@Override
		protected CollectionConfig[] createDTOArray(int size) {
			return new CollectionConfig[size];
		}

		@Override
		protected void setField(
			CollectionConfig collectionConfig, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collectionReference")) {
				if (jsonParserFieldValue != null) {
					collectionConfig.setCollectionReference(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "collectionType")) {
				if (jsonParserFieldValue != null) {
					collectionConfig.setCollectionType(
						CollectionConfig.CollectionType.create(
							(String)jsonParserFieldValue));
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