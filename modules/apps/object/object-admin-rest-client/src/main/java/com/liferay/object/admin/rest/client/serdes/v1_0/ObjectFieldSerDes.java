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

package com.liferay.object.admin.rest.client.serdes.v1_0;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

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
public class ObjectFieldSerDes {

	public static ObjectField toDTO(String json) {
		ObjectFieldJSONParser objectFieldJSONParser =
			new ObjectFieldJSONParser();

		return objectFieldJSONParser.parseToDTO(json);
	}

	public static ObjectField[] toDTOs(String json) {
		ObjectFieldJSONParser objectFieldJSONParser =
			new ObjectFieldJSONParser();

		return objectFieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectField objectField) {
		if (objectField == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectField.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(objectField.getActions()));
		}

		if (objectField.getBusinessType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"businessType\": ");

			sb.append("\"");

			sb.append(objectField.getBusinessType());

			sb.append("\"");
		}

		if (objectField.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectField.getId());
		}

		if (objectField.getIndexed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexed\": ");

			sb.append(objectField.getIndexed());
		}

		if (objectField.getIndexedAsKeyword() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexedAsKeyword\": ");

			sb.append(objectField.getIndexedAsKeyword());
		}

		if (objectField.getIndexedLanguageId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexedLanguageId\": ");

			sb.append("\"");

			sb.append(_escape(objectField.getIndexedLanguageId()));

			sb.append("\"");
		}

		if (objectField.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append(_toJSON(objectField.getLabel()));
		}

		if (objectField.getListTypeDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listTypeDefinitionId\": ");

			sb.append(objectField.getListTypeDefinitionId());
		}

		if (objectField.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(objectField.getName()));

			sb.append("\"");
		}

		if (objectField.getRelationshipType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relationshipType\": ");

			sb.append("\"");

			sb.append(objectField.getRelationshipType());

			sb.append("\"");
		}

		if (objectField.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(objectField.getRequired());
		}

		if (objectField.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(objectField.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectFieldJSONParser objectFieldJSONParser =
			new ObjectFieldJSONParser();

		return objectFieldJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectField objectField) {
		if (objectField == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectField.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(objectField.getActions()));
		}

		if (objectField.getBusinessType() == null) {
			map.put("businessType", null);
		}
		else {
			map.put(
				"businessType", String.valueOf(objectField.getBusinessType()));
		}

		if (objectField.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectField.getId()));
		}

		if (objectField.getIndexed() == null) {
			map.put("indexed", null);
		}
		else {
			map.put("indexed", String.valueOf(objectField.getIndexed()));
		}

		if (objectField.getIndexedAsKeyword() == null) {
			map.put("indexedAsKeyword", null);
		}
		else {
			map.put(
				"indexedAsKeyword",
				String.valueOf(objectField.getIndexedAsKeyword()));
		}

		if (objectField.getIndexedLanguageId() == null) {
			map.put("indexedLanguageId", null);
		}
		else {
			map.put(
				"indexedLanguageId",
				String.valueOf(objectField.getIndexedLanguageId()));
		}

		if (objectField.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(objectField.getLabel()));
		}

		if (objectField.getListTypeDefinitionId() == null) {
			map.put("listTypeDefinitionId", null);
		}
		else {
			map.put(
				"listTypeDefinitionId",
				String.valueOf(objectField.getListTypeDefinitionId()));
		}

		if (objectField.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectField.getName()));
		}

		if (objectField.getRelationshipType() == null) {
			map.put("relationshipType", null);
		}
		else {
			map.put(
				"relationshipType",
				String.valueOf(objectField.getRelationshipType()));
		}

		if (objectField.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(objectField.getRequired()));
		}

		if (objectField.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(objectField.getType()));
		}

		return map;
	}

	public static class ObjectFieldJSONParser
		extends BaseJSONParser<ObjectField> {

		@Override
		protected ObjectField createDTO() {
			return new ObjectField();
		}

		@Override
		protected ObjectField[] createDTOArray(int size) {
			return new ObjectField[size];
		}

		@Override
		protected void setField(
			ObjectField objectField, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					objectField.setActions(
						(Map)ObjectFieldSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "businessType")) {
				if (jsonParserFieldValue != null) {
					objectField.setBusinessType(
						ObjectField.BusinessType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectField.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexed")) {
				if (jsonParserFieldValue != null) {
					objectField.setIndexed((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexedAsKeyword")) {
				if (jsonParserFieldValue != null) {
					objectField.setIndexedAsKeyword(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexedLanguageId")) {
				if (jsonParserFieldValue != null) {
					objectField.setIndexedLanguageId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					objectField.setLabel(
						(Map)ObjectFieldSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "listTypeDefinitionId")) {

				if (jsonParserFieldValue != null) {
					objectField.setListTypeDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectField.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "relationshipType")) {
				if (jsonParserFieldValue != null) {
					objectField.setRelationshipType(
						ObjectField.RelationshipType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					objectField.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					objectField.setType(
						ObjectField.Type.create((String)jsonParserFieldValue));
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