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

package com.liferay.data.engine.rest.client.serdes.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionFieldSerDes {

	public static DataDefinitionField toDTO(String json) {
		DataDefinitionFieldJSONParser dataDefinitionFieldJSONParser =
			new DataDefinitionFieldJSONParser();

		return dataDefinitionFieldJSONParser.parseToDTO(json);
	}

	public static DataDefinitionField[] toDTOs(String json) {
		DataDefinitionFieldJSONParser dataDefinitionFieldJSONParser =
			new DataDefinitionFieldJSONParser();

		return dataDefinitionFieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataDefinitionField dataDefinitionField) {
		if (dataDefinitionField == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataDefinitionField.getCustomProperties() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customProperties\": ");

			sb.append(_toJSON(dataDefinitionField.getCustomProperties()));
		}

		if (dataDefinitionField.getDefaultValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValue\": ");

			sb.append(_toJSON(dataDefinitionField.getDefaultValue()));
		}

		if (dataDefinitionField.getFieldType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldType\": ");

			sb.append("\"");

			sb.append(_escape(dataDefinitionField.getFieldType()));

			sb.append("\"");
		}

		if (dataDefinitionField.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(dataDefinitionField.getId());
		}

		if (dataDefinitionField.getIndexable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexable\": ");

			sb.append(dataDefinitionField.getIndexable());
		}

		if (dataDefinitionField.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append(_toJSON(dataDefinitionField.getLabel()));
		}

		if (dataDefinitionField.getLocalizable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"localizable\": ");

			sb.append(dataDefinitionField.getLocalizable());
		}

		if (dataDefinitionField.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(dataDefinitionField.getName()));

			sb.append("\"");
		}

		if (dataDefinitionField.getRepeatable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"repeatable\": ");

			sb.append(dataDefinitionField.getRepeatable());
		}

		if (dataDefinitionField.getTip() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tip\": ");

			sb.append(_toJSON(dataDefinitionField.getTip()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataDefinitionFieldJSONParser dataDefinitionFieldJSONParser =
			new DataDefinitionFieldJSONParser();

		return dataDefinitionFieldJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataDefinitionField dataDefinitionField) {

		if (dataDefinitionField == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (dataDefinitionField.getCustomProperties() == null) {
			map.put("customProperties", null);
		}
		else {
			map.put(
				"customProperties",
				String.valueOf(dataDefinitionField.getCustomProperties()));
		}

		if (dataDefinitionField.getDefaultValue() == null) {
			map.put("defaultValue", null);
		}
		else {
			map.put(
				"defaultValue",
				String.valueOf(dataDefinitionField.getDefaultValue()));
		}

		if (dataDefinitionField.getFieldType() == null) {
			map.put("fieldType", null);
		}
		else {
			map.put(
				"fieldType",
				String.valueOf(dataDefinitionField.getFieldType()));
		}

		if (dataDefinitionField.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(dataDefinitionField.getId()));
		}

		if (dataDefinitionField.getIndexable() == null) {
			map.put("indexable", null);
		}
		else {
			map.put(
				"indexable",
				String.valueOf(dataDefinitionField.getIndexable()));
		}

		if (dataDefinitionField.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(dataDefinitionField.getLabel()));
		}

		if (dataDefinitionField.getLocalizable() == null) {
			map.put("localizable", null);
		}
		else {
			map.put(
				"localizable",
				String.valueOf(dataDefinitionField.getLocalizable()));
		}

		if (dataDefinitionField.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(dataDefinitionField.getName()));
		}

		if (dataDefinitionField.getRepeatable() == null) {
			map.put("repeatable", null);
		}
		else {
			map.put(
				"repeatable",
				String.valueOf(dataDefinitionField.getRepeatable()));
		}

		if (dataDefinitionField.getTip() == null) {
			map.put("tip", null);
		}
		else {
			map.put("tip", String.valueOf(dataDefinitionField.getTip()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class DataDefinitionFieldJSONParser
		extends BaseJSONParser<DataDefinitionField> {

		@Override
		protected DataDefinitionField createDTO() {
			return new DataDefinitionField();
		}

		@Override
		protected DataDefinitionField[] createDTOArray(int size) {
			return new DataDefinitionField[size];
		}

		@Override
		protected void setField(
			DataDefinitionField dataDefinitionField, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "customProperties")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setCustomProperties(
						DataDefinitionFieldSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultValue")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setDefaultValue(
						DataDefinitionFieldSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldType")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setFieldType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexable")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setIndexable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setLabel(
						DataDefinitionFieldSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "localizable")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setLocalizable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "repeatable")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setRepeatable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tip")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionField.setTip(
						DataDefinitionFieldSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}