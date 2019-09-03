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

package com.liferay.headless.admin.taxonomy.client.serdes.v1_0;

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.ParentTaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

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
public class ParentTaxonomyCategorySerDes {

	public static ParentTaxonomyCategory toDTO(String json) {
		ParentTaxonomyCategoryJSONParser parentTaxonomyCategoryJSONParser =
			new ParentTaxonomyCategoryJSONParser();

		return parentTaxonomyCategoryJSONParser.parseToDTO(json);
	}

	public static ParentTaxonomyCategory[] toDTOs(String json) {
		ParentTaxonomyCategoryJSONParser parentTaxonomyCategoryJSONParser =
			new ParentTaxonomyCategoryJSONParser();

		return parentTaxonomyCategoryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ParentTaxonomyCategory parentTaxonomyCategory) {
		if (parentTaxonomyCategory == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (parentTaxonomyCategory.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(parentTaxonomyCategory.getId());
		}

		if (parentTaxonomyCategory.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(parentTaxonomyCategory.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ParentTaxonomyCategoryJSONParser parentTaxonomyCategoryJSONParser =
			new ParentTaxonomyCategoryJSONParser();

		return parentTaxonomyCategoryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ParentTaxonomyCategory parentTaxonomyCategory) {

		if (parentTaxonomyCategory == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (parentTaxonomyCategory.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(parentTaxonomyCategory.getId()));
		}

		if (parentTaxonomyCategory.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(parentTaxonomyCategory.getName()));
		}

		return map;
	}

	public static class ParentTaxonomyCategoryJSONParser
		extends BaseJSONParser<ParentTaxonomyCategory> {

		@Override
		protected ParentTaxonomyCategory createDTO() {
			return new ParentTaxonomyCategory();
		}

		@Override
		protected ParentTaxonomyCategory[] createDTOArray(int size) {
			return new ParentTaxonomyCategory[size];
		}

		@Override
		protected void setField(
			ParentTaxonomyCategory parentTaxonomyCategory,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					parentTaxonomyCategory.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					parentTaxonomyCategory.setName(
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

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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