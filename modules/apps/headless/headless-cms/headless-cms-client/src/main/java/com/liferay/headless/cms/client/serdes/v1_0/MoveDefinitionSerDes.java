/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.EntryDefinition;
import com.liferay.headless.cms.client.dto.v1_0.MoveDefinition;
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
public class MoveDefinitionSerDes {

	public static MoveDefinition toDTO(String json) {
		MoveDefinitionJSONParser moveDefinitionJSONParser =
			new MoveDefinitionJSONParser();

		return moveDefinitionJSONParser.parseToDTO(json);
	}

	public static MoveDefinition[] toDTOs(String json) {
		MoveDefinitionJSONParser moveDefinitionJSONParser =
			new MoveDefinitionJSONParser();

		return moveDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MoveDefinition moveDefinition) {
		if (moveDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (moveDefinition.getObjectEntryFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectEntryFolderId\": ");

			sb.append(moveDefinition.getObjectEntryFolderId());
		}

		if (moveDefinition.getEntryDefinitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entryDefinitions\": ");

			sb.append("[");

			for (int i = 0; i < moveDefinition.getEntryDefinitions().length;
				 i++) {

				sb.append(
					String.valueOf(moveDefinition.getEntryDefinitions()[i]));

				if ((i + 1) < moveDefinition.getEntryDefinitions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (moveDefinition.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(moveDefinition.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MoveDefinitionJSONParser moveDefinitionJSONParser =
			new MoveDefinitionJSONParser();

		return moveDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(MoveDefinition moveDefinition) {
		if (moveDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (moveDefinition.getObjectEntryFolderId() == null) {
			map.put("objectEntryFolderId", null);
		}
		else {
			map.put(
				"objectEntryFolderId",
				String.valueOf(moveDefinition.getObjectEntryFolderId()));
		}

		if (moveDefinition.getEntryDefinitions() == null) {
			map.put("entryDefinitions", null);
		}
		else {
			map.put(
				"entryDefinitions",
				String.valueOf(moveDefinition.getEntryDefinitions()));
		}

		if (moveDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(moveDefinition.getType()));
		}

		return map;
	}

	public static class MoveDefinitionJSONParser
		extends BaseJSONParser<MoveDefinition> {

		@Override
		protected MoveDefinition createDTO() {
			return new MoveDefinition();
		}

		@Override
		protected MoveDefinition[] createDTOArray(int size) {
			return new MoveDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "objectEntryFolderId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "entryDefinitions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			MoveDefinition moveDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "objectEntryFolderId")) {
				if (jsonParserFieldValue != null) {
					moveDefinition.setObjectEntryFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "entryDefinitions")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					EntryDefinition[] entryDefinitionsArray =
						new EntryDefinition[jsonParserFieldValues.length];

					for (int i = 0; i < entryDefinitionsArray.length; i++) {
						entryDefinitionsArray[i] = EntryDefinitionSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					moveDefinition.setEntryDefinitions(entryDefinitionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					moveDefinition.setType(
						MoveDefinition.Type.create(
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