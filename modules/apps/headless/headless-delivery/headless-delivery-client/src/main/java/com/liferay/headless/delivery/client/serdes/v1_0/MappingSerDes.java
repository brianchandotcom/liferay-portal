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

import com.liferay.headless.delivery.client.dto.v1_0.Mapping;
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
public class MappingSerDes {

	public static Mapping toDTO(String json) {
		MappingJSONParser mappingJSONParser = new MappingJSONParser();

		return mappingJSONParser.parseToDTO(json);
	}

	public static Mapping[] toDTOs(String json) {
		MappingJSONParser mappingJSONParser = new MappingJSONParser();

		return mappingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Mapping mapping) {
		if (mapping == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (mapping.getCollectionItemFieldKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionItemFieldKey\": ");

			sb.append("\"");

			sb.append(_escape(mapping.getCollectionItemFieldKey()));

			sb.append("\"");
		}

		if (mapping.getFieldKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldKey\": ");

			sb.append("\"");

			sb.append(_escape(mapping.getFieldKey()));

			sb.append("\"");
		}

		if (mapping.getItemClassName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"itemClassName\": ");

			sb.append("\"");

			sb.append(_escape(mapping.getItemClassName()));

			sb.append("\"");
		}

		if (mapping.getItemClassPK() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"itemClassPK\": ");

			sb.append(mapping.getItemClassPK());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MappingJSONParser mappingJSONParser = new MappingJSONParser();

		return mappingJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Mapping mapping) {
		if (mapping == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (mapping.getCollectionItemFieldKey() == null) {
			map.put("collectionItemFieldKey", null);
		}
		else {
			map.put(
				"collectionItemFieldKey",
				String.valueOf(mapping.getCollectionItemFieldKey()));
		}

		if (mapping.getFieldKey() == null) {
			map.put("fieldKey", null);
		}
		else {
			map.put("fieldKey", String.valueOf(mapping.getFieldKey()));
		}

		if (mapping.getItemClassName() == null) {
			map.put("itemClassName", null);
		}
		else {
			map.put(
				"itemClassName", String.valueOf(mapping.getItemClassName()));
		}

		if (mapping.getItemClassPK() == null) {
			map.put("itemClassPK", null);
		}
		else {
			map.put("itemClassPK", String.valueOf(mapping.getItemClassPK()));
		}

		return map;
	}

	public static class MappingJSONParser extends BaseJSONParser<Mapping> {

		@Override
		protected Mapping createDTO() {
			return new Mapping();
		}

		@Override
		protected Mapping[] createDTOArray(int size) {
			return new Mapping[size];
		}

		@Override
		protected void setField(
			Mapping mapping, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collectionItemFieldKey")) {
				if (jsonParserFieldValue != null) {
					mapping.setCollectionItemFieldKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldKey")) {
				if (jsonParserFieldValue != null) {
					mapping.setFieldKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "itemClassName")) {
				if (jsonParserFieldValue != null) {
					mapping.setItemClassName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "itemClassPK")) {
				if (jsonParserFieldValue != null) {
					mapping.setItemClassPK(
						Long.valueOf((String)jsonParserFieldValue));
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