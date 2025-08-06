/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.PermissionAction;
import com.liferay.headless.cms.client.dto.v1_0.Selection;
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
public class PermissionActionSerDes {

	public static PermissionAction toDTO(String json) {
		PermissionActionJSONParser permissionActionJSONParser =
			new PermissionActionJSONParser();

		return permissionActionJSONParser.parseToDTO(json);
	}

	public static PermissionAction[] toDTOs(String json) {
		PermissionActionJSONParser permissionActionJSONParser =
			new PermissionActionJSONParser();

		return permissionActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PermissionAction permissionAction) {
		if (permissionAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (permissionAction.getPermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"permissions\": ");

			sb.append("[");

			for (int i = 0; i < permissionAction.getPermissions().length; i++) {
				sb.append(permissionAction.getPermissions()[i]);

				if ((i + 1) < permissionAction.getPermissions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (permissionAction.getAction() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			sb.append("\"");

			sb.append(permissionAction.getAction());

			sb.append("\"");
		}

		if (permissionAction.getSelectAll() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectAll\": ");

			sb.append(permissionAction.getSelectAll());
		}

		if (permissionAction.getSelections() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selections\": ");

			sb.append("[");

			for (int i = 0; i < permissionAction.getSelections().length; i++) {
				sb.append(String.valueOf(permissionAction.getSelections()[i]));

				if ((i + 1) < permissionAction.getSelections().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PermissionActionJSONParser permissionActionJSONParser =
			new PermissionActionJSONParser();

		return permissionActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PermissionAction permissionAction) {
		if (permissionAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (permissionAction.getPermissions() == null) {
			map.put("permissions", null);
		}
		else {
			map.put(
				"permissions",
				String.valueOf(permissionAction.getPermissions()));
		}

		if (permissionAction.getAction() == null) {
			map.put("action", null);
		}
		else {
			map.put("action", String.valueOf(permissionAction.getAction()));
		}

		if (permissionAction.getSelectAll() == null) {
			map.put("selectAll", null);
		}
		else {
			map.put(
				"selectAll", String.valueOf(permissionAction.getSelectAll()));
		}

		if (permissionAction.getSelections() == null) {
			map.put("selections", null);
		}
		else {
			map.put(
				"selections", String.valueOf(permissionAction.getSelections()));
		}

		return map;
	}

	public static class PermissionActionJSONParser
		extends BaseJSONParser<PermissionAction> {

		@Override
		protected PermissionAction createDTO() {
			return new PermissionAction();
		}

		@Override
		protected PermissionAction[] createDTOArray(int size) {
			return new PermissionAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "permissions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "action")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "selectAll")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "selections")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			PermissionAction permissionAction, String jsonParserFieldName,
			Object jsonParserFieldValue) {

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

					permissionAction.setPermissions(permissionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "action")) {
				if (jsonParserFieldValue != null) {
					permissionAction.setAction(
						PermissionAction.Action.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectAll")) {
				if (jsonParserFieldValue != null) {
					permissionAction.setSelectAll(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selections")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Selection[] selectionsArray =
						new Selection[jsonParserFieldValues.length];

					for (int i = 0; i < selectionsArray.length; i++) {
						selectionsArray[i] = SelectionSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					permissionAction.setSelections(selectionsArray);
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