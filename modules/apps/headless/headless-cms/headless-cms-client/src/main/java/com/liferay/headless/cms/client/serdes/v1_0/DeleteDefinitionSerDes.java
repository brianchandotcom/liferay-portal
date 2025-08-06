/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.DeleteDefinition;
import com.liferay.headless.cms.client.dto.v1_0.EntryDefinition;
import com.liferay.headless.cms.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class DeleteDefinitionSerDes {

	public static DeleteDefinition toDTO(String json) {
		DeleteDefinitionJSONParser deleteDefinitionJSONParser =
			new DeleteDefinitionJSONParser();

		return deleteDefinitionJSONParser.parseToDTO(json);
	}

	public static DeleteDefinition[] toDTOs(String json) {
		DeleteDefinitionJSONParser deleteDefinitionJSONParser =
			new DeleteDefinitionJSONParser();

		return deleteDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DeleteDefinition deleteDefinition) {
		if (deleteDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (deleteDefinition.getEntryDefinitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entryDefinitions\": ");

			sb.append("[");

			for (int i = 0; i < deleteDefinition.getEntryDefinitions().length;
				 i++) {

				sb.append(
					String.valueOf(deleteDefinition.getEntryDefinitions()[i]));

				if ((i + 1) < deleteDefinition.getEntryDefinitions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (deleteDefinition.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(deleteDefinition.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DeleteDefinitionJSONParser deleteDefinitionJSONParser =
			new DeleteDefinitionJSONParser();

		return deleteDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DeleteDefinition deleteDefinition) {
		if (deleteDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (deleteDefinition.getEntryDefinitions() == null) {
			map.put("entryDefinitions", null);
		}
		else {
			map.put(
				"entryDefinitions",
				String.valueOf(deleteDefinition.getEntryDefinitions()));
		}

		if (deleteDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(deleteDefinition.getType()));
		}

		return map;
	}

	public static class DeleteDefinitionJSONParser
		extends BaseJSONParser<DeleteDefinition> {

		@Override
		protected DeleteDefinition createDTO() {
			return new DeleteDefinition();
		}

		@Override
		protected DeleteDefinition[] createDTOArray(int size) {
			return new DeleteDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "entryDefinitions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			DeleteDefinition deleteDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "entryDefinitions")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					EntryDefinition[] entryDefinitionsArray =
						new EntryDefinition[jsonParserFieldValues.length];

					for (int i = 0; i < entryDefinitionsArray.length; i++) {
						entryDefinitionsArray[i] = EntryDefinitionSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					deleteDefinition.setEntryDefinitions(entryDefinitionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					deleteDefinition.setType(
						DeleteDefinition.Type.create(
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