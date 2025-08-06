/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.EntryDefinition;
import com.liferay.headless.cms.client.dto.v1_0.TagDefinition;
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
public class TagDefinitionSerDes {

	public static TagDefinition toDTO(String json) {
		TagDefinitionJSONParser tagDefinitionJSONParser =
			new TagDefinitionJSONParser();

		return tagDefinitionJSONParser.parseToDTO(json);
	}

	public static TagDefinition[] toDTOs(String json) {
		TagDefinitionJSONParser tagDefinitionJSONParser =
			new TagDefinitionJSONParser();

		return tagDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TagDefinition tagDefinition) {
		if (tagDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (tagDefinition.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("[");

			for (int i = 0; i < tagDefinition.getKeywords().length; i++) {
				sb.append(_toJSON(tagDefinition.getKeywords()[i]));

				if ((i + 1) < tagDefinition.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (tagDefinition.getEntryDefinitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entryDefinitions\": ");

			sb.append("[");

			for (int i = 0; i < tagDefinition.getEntryDefinitions().length;
				 i++) {

				sb.append(
					String.valueOf(tagDefinition.getEntryDefinitions()[i]));

				if ((i + 1) < tagDefinition.getEntryDefinitions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (tagDefinition.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(tagDefinition.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TagDefinitionJSONParser tagDefinitionJSONParser =
			new TagDefinitionJSONParser();

		return tagDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(TagDefinition tagDefinition) {
		if (tagDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (tagDefinition.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put("keywords", String.valueOf(tagDefinition.getKeywords()));
		}

		if (tagDefinition.getEntryDefinitions() == null) {
			map.put("entryDefinitions", null);
		}
		else {
			map.put(
				"entryDefinitions",
				String.valueOf(tagDefinition.getEntryDefinitions()));
		}

		if (tagDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(tagDefinition.getType()));
		}

		return map;
	}

	public static class TagDefinitionJSONParser
		extends BaseJSONParser<TagDefinition> {

		@Override
		protected TagDefinition createDTO() {
			return new TagDefinition();
		}

		@Override
		protected TagDefinition[] createDTOArray(int size) {
			return new TagDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "keywords")) {
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
			TagDefinition tagDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					tagDefinition.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
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

					tagDefinition.setEntryDefinitions(entryDefinitionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					tagDefinition.setType(
						TagDefinition.Type.create(
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