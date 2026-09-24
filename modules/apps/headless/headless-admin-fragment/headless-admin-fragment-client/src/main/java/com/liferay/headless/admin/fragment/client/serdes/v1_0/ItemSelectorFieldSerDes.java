/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.serdes.v1_0;

import com.liferay.headless.admin.fragment.client.dto.v1_0.ItemSelectorField;
import com.liferay.headless.admin.fragment.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ItemSelectorFieldSerDes {

	public static ItemSelectorField toDTO(String json) {
		ItemSelectorFieldJSONParser itemSelectorFieldJSONParser =
			new ItemSelectorFieldJSONParser();

		return itemSelectorFieldJSONParser.parseToDTO(json);
	}

	public static ItemSelectorField[] toDTOs(String json) {
		ItemSelectorFieldJSONParser itemSelectorFieldJSONParser =
			new ItemSelectorFieldJSONParser();

		return itemSelectorFieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ItemSelectorField itemSelectorField) {
		if (itemSelectorField == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (itemSelectorField.getDefaultValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValue\": ");

			sb.append(String.valueOf(itemSelectorField.getDefaultValue()));
		}

		if (itemSelectorField.getTypeOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"typeOptions\": ");

			sb.append(String.valueOf(itemSelectorField.getTypeOptions()));
		}

		if (itemSelectorField.getDataType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataType\": ");

			sb.append("\"");
			sb.append(itemSelectorField.getDataType());
			sb.append("\"");
		}

		if (itemSelectorField.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(itemSelectorField.getDescription()));

			sb.append("\"");
		}

		if (itemSelectorField.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(itemSelectorField.getLabel()));

			sb.append("\"");
		}

		if (itemSelectorField.getLocalizable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"localizable\": ");

			sb.append(itemSelectorField.getLocalizable());
		}

		if (itemSelectorField.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(itemSelectorField.getName()));

			sb.append("\"");
		}

		if (itemSelectorField.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(itemSelectorField.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ItemSelectorFieldJSONParser itemSelectorFieldJSONParser =
			new ItemSelectorFieldJSONParser();

		return itemSelectorFieldJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ItemSelectorField itemSelectorField) {

		if (itemSelectorField == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (itemSelectorField.getDefaultValue() == null) {
			map.put("defaultValue", null);
		}
		else {
			map.put(
				"defaultValue",
				String.valueOf(itemSelectorField.getDefaultValue()));
		}

		if (itemSelectorField.getTypeOptions() == null) {
			map.put("typeOptions", null);
		}
		else {
			map.put(
				"typeOptions",
				String.valueOf(itemSelectorField.getTypeOptions()));
		}

		if (itemSelectorField.getDataType() == null) {
			map.put("dataType", null);
		}
		else {
			map.put(
				"dataType", String.valueOf(itemSelectorField.getDataType()));
		}

		if (itemSelectorField.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(itemSelectorField.getDescription()));
		}

		if (itemSelectorField.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(itemSelectorField.getLabel()));
		}

		if (itemSelectorField.getLocalizable() == null) {
			map.put("localizable", null);
		}
		else {
			map.put(
				"localizable",
				String.valueOf(itemSelectorField.getLocalizable()));
		}

		if (itemSelectorField.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(itemSelectorField.getName()));
		}

		if (itemSelectorField.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(itemSelectorField.getType()));
		}

		return map;
	}

	public static class ItemSelectorFieldJSONParser
		extends BaseJSONParser<ItemSelectorField> {

		@Override
		protected ItemSelectorField createDTO() {
			return new ItemSelectorField();
		}

		@Override
		protected ItemSelectorField[] createDTOArray(int size) {
			return new ItemSelectorField[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "defaultValue")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "typeOptions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dataType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "localizable")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ItemSelectorField itemSelectorField, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "defaultValue")) {
				if (jsonParserFieldValue != null) {
					itemSelectorField.setDefaultValue(
						ItemFragmentConfigurationFieldDefaultValueSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "typeOptions")) {
				if (jsonParserFieldValue != null) {
					itemSelectorField.setTypeOptions(
						ItemSelectorTypeOptionsSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataType")) {
				if (jsonParserFieldValue != null) {
					itemSelectorField.setDataType(
						ItemSelectorField.DataType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					itemSelectorField.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					itemSelectorField.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "localizable")) {
				if (jsonParserFieldValue != null) {
					itemSelectorField.setLocalizable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					itemSelectorField.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					itemSelectorField.setType(
						ItemSelectorField.Type.create(
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			return _toJSON(collection.toArray());
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:1463100040