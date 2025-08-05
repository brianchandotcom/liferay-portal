/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.BulkActionDefinition;
import com.liferay.headless.cms.client.dto.v1_0.CMSEntryDefinition;
import com.liferay.headless.cms.client.dto.v1_0.CategoryDefinition;
import com.liferay.headless.cms.client.dto.v1_0.DeleteDefinition;
import com.liferay.headless.cms.client.dto.v1_0.MoveDefinition;
import com.liferay.headless.cms.client.dto.v1_0.PermissionDefinition;
import com.liferay.headless.cms.client.dto.v1_0.StatusDefinition;
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
public class BulkActionDefinitionSerDes {

	public static BulkActionDefinition toDTO(String json) {
		BulkActionDefinitionJSONParser bulkActionDefinitionJSONParser =
			new BulkActionDefinitionJSONParser();

		return bulkActionDefinitionJSONParser.parseToDTO(json);
	}

	public static BulkActionDefinition[] toDTOs(String json) {
		BulkActionDefinitionJSONParser bulkActionDefinitionJSONParser =
			new BulkActionDefinitionJSONParser();

		return bulkActionDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BulkActionDefinition bulkActionDefinition) {
		if (bulkActionDefinition == null) {
			return "null";
		}

		BulkActionDefinition.Type type = bulkActionDefinition.getType();

		if (type != null) {
			String typeString = type.toString();

			if (typeString.equals("Category")) {
				return CategoryDefinitionSerDes.toJSON(
					(CategoryDefinition)bulkActionDefinition);
			}

			if (typeString.equals("Delete")) {
				return DeleteDefinitionSerDes.toJSON(
					(DeleteDefinition)bulkActionDefinition);
			}

			if (typeString.equals("Move")) {
				return MoveDefinitionSerDes.toJSON(
					(MoveDefinition)bulkActionDefinition);
			}

			if (typeString.equals("Permission")) {
				return PermissionDefinitionSerDes.toJSON(
					(PermissionDefinition)bulkActionDefinition);
			}

			if (typeString.equals("Status")) {
				return StatusDefinitionSerDes.toJSON(
					(StatusDefinition)bulkActionDefinition);
			}

			if (typeString.equals("Tag")) {
				return TagDefinitionSerDes.toJSON(
					(TagDefinition)bulkActionDefinition);
			}

			throw new IllegalArgumentException("Unknown type " + typeString);
		}
		else {
			throw new IllegalArgumentException("Missing type parameter");
		}
	}

	public static Map<String, Object> toMap(String json) {
		BulkActionDefinitionJSONParser bulkActionDefinitionJSONParser =
			new BulkActionDefinitionJSONParser();

		return bulkActionDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		BulkActionDefinition bulkActionDefinition) {

		if (bulkActionDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (bulkActionDefinition.getCmsEntryDefinitions() == null) {
			map.put("cmsEntryDefinitions", null);
		}
		else {
			map.put(
				"cmsEntryDefinitions",
				String.valueOf(bulkActionDefinition.getCmsEntryDefinitions()));
		}

		if (bulkActionDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(bulkActionDefinition.getType()));
		}

		return map;
	}

	public static class BulkActionDefinitionJSONParser
		extends BaseJSONParser<BulkActionDefinition> {

		@Override
		protected BulkActionDefinition createDTO() {
			return null;
		}

		@Override
		protected BulkActionDefinition[] createDTOArray(int size) {
			return new BulkActionDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "cmsEntryDefinitions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		public BulkActionDefinition parseToDTO(String json) {
			Map<String, Object> jsonMap = parseToMap(json);

			Object type = jsonMap.get("type");

			if (type != null) {
				String typeString = type.toString();

				if (typeString.equals("Category")) {
					return CategoryDefinition.toDTO(json);
				}

				if (typeString.equals("Delete")) {
					return DeleteDefinition.toDTO(json);
				}

				if (typeString.equals("Move")) {
					return MoveDefinition.toDTO(json);
				}

				if (typeString.equals("Permission")) {
					return PermissionDefinition.toDTO(json);
				}

				if (typeString.equals("Status")) {
					return StatusDefinition.toDTO(json);
				}

				if (typeString.equals("Tag")) {
					return TagDefinition.toDTO(json);
				}

				throw new IllegalArgumentException(
					"Unknown type " + typeString);
			}
			else {
				throw new IllegalArgumentException("Missing type parameter");
			}
		}

		@Override
		protected void setField(
			BulkActionDefinition bulkActionDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "cmsEntryDefinitions")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					CMSEntryDefinition[] cmsEntryDefinitionsArray =
						new CMSEntryDefinition[jsonParserFieldValues.length];

					for (int i = 0; i < cmsEntryDefinitionsArray.length; i++) {
						cmsEntryDefinitionsArray[i] =
							CMSEntryDefinitionSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					bulkActionDefinition.setCmsEntryDefinitions(
						cmsEntryDefinitionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					bulkActionDefinition.setType(
						BulkActionDefinition.Type.create(
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