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

package com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Option;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.OptionValue;
import com.liferay.headless.commerce.admin.catalog.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class OptionSerDes {

	public static Option toDTO(String json) {
		OptionJSONParser optionJSONParser = new OptionJSONParser();

		return optionJSONParser.parseToDTO(json);
	}

	public static Option[] toDTOs(String json) {
		OptionJSONParser optionJSONParser = new OptionJSONParser();

		return optionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Option option) {
		if (option == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (option.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(option.getActions()));
		}

		if (option.getCatalogId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogId\": ");

			sb.append(option.getCatalogId());
		}

		if (option.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(_toJSON(option.getDescription()));
		}

		if (option.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(option.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (option.getFacetable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"facetable\": ");

			sb.append(option.getFacetable());
		}

		if (option.getFieldType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldType\": ");

			sb.append("\"");

			sb.append(option.getFieldType());

			sb.append("\"");
		}

		if (option.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(option.getId());
		}

		if (option.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(option.getKey()));

			sb.append("\"");
		}

		if (option.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(option.getName()));
		}

		if (option.getOptionValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"optionValues\": ");

			sb.append("[");

			for (int i = 0; i < option.getOptionValues().length; i++) {
				sb.append(String.valueOf(option.getOptionValues()[i]));

				if ((i + 1) < option.getOptionValues().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (option.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(option.getPriority());
		}

		if (option.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(option.getRequired());
		}

		if (option.getSkuContributor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuContributor\": ");

			sb.append(option.getSkuContributor());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OptionJSONParser optionJSONParser = new OptionJSONParser();

		return optionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Option option) {
		if (option == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (option.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(option.getActions()));
		}

		if (option.getCatalogId() == null) {
			map.put("catalogId", null);
		}
		else {
			map.put("catalogId", String.valueOf(option.getCatalogId()));
		}

		if (option.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(option.getDescription()));
		}

		if (option.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(option.getExternalReferenceCode()));
		}

		if (option.getFacetable() == null) {
			map.put("facetable", null);
		}
		else {
			map.put("facetable", String.valueOf(option.getFacetable()));
		}

		if (option.getFieldType() == null) {
			map.put("fieldType", null);
		}
		else {
			map.put("fieldType", String.valueOf(option.getFieldType()));
		}

		if (option.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(option.getId()));
		}

		if (option.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(option.getKey()));
		}

		if (option.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(option.getName()));
		}

		if (option.getOptionValues() == null) {
			map.put("optionValues", null);
		}
		else {
			map.put("optionValues", String.valueOf(option.getOptionValues()));
		}

		if (option.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(option.getPriority()));
		}

		if (option.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(option.getRequired()));
		}

		if (option.getSkuContributor() == null) {
			map.put("skuContributor", null);
		}
		else {
			map.put(
				"skuContributor", String.valueOf(option.getSkuContributor()));
		}

		return map;
	}

	public static class OptionJSONParser extends BaseJSONParser<Option> {

		@Override
		protected Option createDTO() {
			return new Option();
		}

		@Override
		protected Option[] createDTOArray(int size) {
			return new Option[size];
		}

		@Override
		protected void setField(
			Option option, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					option.setActions(
						(Map)OptionSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "catalogId")) {
				if (jsonParserFieldValue != null) {
					option.setCatalogId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					option.setDescription(
						(Map)OptionSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					option.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "facetable")) {
				if (jsonParserFieldValue != null) {
					option.setFacetable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldType")) {
				if (jsonParserFieldValue != null) {
					option.setFieldType(
						Option.FieldType.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					option.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					option.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					option.setName(
						(Map)OptionSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "optionValues")) {
				if (jsonParserFieldValue != null) {
					option.setOptionValues(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OptionValueSerDes.toDTO((String)object)
						).toArray(
							size -> new OptionValue[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					option.setPriority(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					option.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuContributor")) {
				if (jsonParserFieldValue != null) {
					option.setSkuContributor((Boolean)jsonParserFieldValue);
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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