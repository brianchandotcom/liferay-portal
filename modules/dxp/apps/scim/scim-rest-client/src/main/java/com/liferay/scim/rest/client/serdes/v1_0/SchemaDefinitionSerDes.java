/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.client.serdes.v1_0;

import com.liferay.scim.rest.client.dto.v1_0.Attribute;
import com.liferay.scim.rest.client.dto.v1_0.SchemaDefinition;
import com.liferay.scim.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Olivér Kecskeméty
 * @generated
 */
@Generated("")
public class SchemaDefinitionSerDes {

	public static SchemaDefinition toDTO(String json) {
		SchemaDefinitionJSONParser schemaDefinitionJSONParser =
			new SchemaDefinitionJSONParser();

		return schemaDefinitionJSONParser.parseToDTO(json);
	}

	public static SchemaDefinition[] toDTOs(String json) {
		SchemaDefinitionJSONParser schemaDefinitionJSONParser =
			new SchemaDefinitionJSONParser();

		return schemaDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SchemaDefinition schemaDefinition) {
		if (schemaDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (schemaDefinition.getAttributes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attributes\": ");

			sb.append("[");

			for (int i = 0; i < schemaDefinition.getAttributes().length; i++) {
				sb.append(String.valueOf(schemaDefinition.getAttributes()[i]));

				if ((i + 1) < schemaDefinition.getAttributes().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (schemaDefinition.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(schemaDefinition.getDescription()));

			sb.append("\"");
		}

		if (schemaDefinition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(schemaDefinition.getId()));

			sb.append("\"");
		}

		if (schemaDefinition.getMeta() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"meta\": ");

			sb.append(String.valueOf(schemaDefinition.getMeta()));
		}

		if (schemaDefinition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(schemaDefinition.getName()));

			sb.append("\"");
		}

		if (schemaDefinition.getSchemas() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"schemas\": ");

			sb.append("[");

			for (int i = 0; i < schemaDefinition.getSchemas().length; i++) {
				sb.append(_toJSON(schemaDefinition.getSchemas()[i]));

				if ((i + 1) < schemaDefinition.getSchemas().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SchemaDefinitionJSONParser schemaDefinitionJSONParser =
			new SchemaDefinitionJSONParser();

		return schemaDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SchemaDefinition schemaDefinition) {
		if (schemaDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (schemaDefinition.getAttributes() == null) {
			map.put("attributes", null);
		}
		else {
			map.put(
				"attributes", String.valueOf(schemaDefinition.getAttributes()));
		}

		if (schemaDefinition.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(schemaDefinition.getDescription()));
		}

		if (schemaDefinition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(schemaDefinition.getId()));
		}

		if (schemaDefinition.getMeta() == null) {
			map.put("meta", null);
		}
		else {
			map.put("meta", String.valueOf(schemaDefinition.getMeta()));
		}

		if (schemaDefinition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(schemaDefinition.getName()));
		}

		if (schemaDefinition.getSchemas() == null) {
			map.put("schemas", null);
		}
		else {
			map.put("schemas", String.valueOf(schemaDefinition.getSchemas()));
		}

		return map;
	}

	public static class SchemaDefinitionJSONParser
		extends BaseJSONParser<SchemaDefinition> {

		@Override
		protected SchemaDefinition createDTO() {
			return new SchemaDefinition();
		}

		@Override
		protected SchemaDefinition[] createDTOArray(int size) {
			return new SchemaDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "attributes")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "meta")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "schemas")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			SchemaDefinition schemaDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "attributes")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Attribute[] attributesArray =
						new Attribute[jsonParserFieldValues.length];

					for (int i = 0; i < attributesArray.length; i++) {
						attributesArray[i] = AttributeSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					schemaDefinition.setAttributes(attributesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					schemaDefinition.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					schemaDefinition.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "meta")) {
				if (jsonParserFieldValue != null) {
					schemaDefinition.setMeta(
						MetaSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					schemaDefinition.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "schemas")) {
				if (jsonParserFieldValue != null) {
					schemaDefinition.setSchemas(
						toStrings((Object[])jsonParserFieldValue));
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