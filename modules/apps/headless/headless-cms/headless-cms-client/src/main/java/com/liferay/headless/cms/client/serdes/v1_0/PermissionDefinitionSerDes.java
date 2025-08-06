/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.EntryDefinition;
import com.liferay.headless.cms.client.dto.v1_0.PermissionDefinition;
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
public class PermissionDefinitionSerDes {

	public static PermissionDefinition toDTO(String json) {
		PermissionDefinitionJSONParser permissionDefinitionJSONParser =
			new PermissionDefinitionJSONParser();

		return permissionDefinitionJSONParser.parseToDTO(json);
	}

	public static PermissionDefinition[] toDTOs(String json) {
		PermissionDefinitionJSONParser permissionDefinitionJSONParser =
			new PermissionDefinitionJSONParser();

		return permissionDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PermissionDefinition permissionDefinition) {
		if (permissionDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (permissionDefinition.getPermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"permissions\": ");

			sb.append("[");

			for (int i = 0; i < permissionDefinition.getPermissions().length;
				 i++) {

				sb.append(permissionDefinition.getPermissions()[i]);

				if ((i + 1) < permissionDefinition.getPermissions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (permissionDefinition.getEntryDefinitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entryDefinitions\": ");

			sb.append("[");

			for (int i = 0;
				 i < permissionDefinition.getEntryDefinitions().length; i++) {

				sb.append(
					String.valueOf(
						permissionDefinition.getEntryDefinitions()[i]));

				if ((i + 1) <
						permissionDefinition.getEntryDefinitions().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (permissionDefinition.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(permissionDefinition.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PermissionDefinitionJSONParser permissionDefinitionJSONParser =
			new PermissionDefinitionJSONParser();

		return permissionDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PermissionDefinition permissionDefinition) {

		if (permissionDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (permissionDefinition.getPermissions() == null) {
			map.put("permissions", null);
		}
		else {
			map.put(
				"permissions",
				String.valueOf(permissionDefinition.getPermissions()));
		}

		if (permissionDefinition.getEntryDefinitions() == null) {
			map.put("entryDefinitions", null);
		}
		else {
			map.put(
				"entryDefinitions",
				String.valueOf(permissionDefinition.getEntryDefinitions()));
		}

		if (permissionDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(permissionDefinition.getType()));
		}

		return map;
	}

	public static class PermissionDefinitionJSONParser
		extends BaseJSONParser<PermissionDefinition> {

		@Override
		protected PermissionDefinition createDTO() {
			return new PermissionDefinition();
		}

		@Override
		protected PermissionDefinition[] createDTOArray(int size) {
			return new PermissionDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "permissions")) {
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
			PermissionDefinition permissionDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "permissions")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					com.liferay.headless.cms.client.permission.Permission[]
						permissionsArray = new
						com.liferay.headless.cms.client.permission.Permission
							[jsonParserFieldValues.length];

					for (int i = 0; i < permissionsArray.length; i++) {
						permissionsArray[i] =
							com.liferay.headless.cms.client.permission.
								Permission.toDTO(
									(String)jsonParserFieldValues[i]);
					}

					permissionDefinition.setPermissions(permissionsArray);
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

					permissionDefinition.setEntryDefinitions(
						entryDefinitionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					permissionDefinition.setType(
						PermissionDefinition.Type.create(
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