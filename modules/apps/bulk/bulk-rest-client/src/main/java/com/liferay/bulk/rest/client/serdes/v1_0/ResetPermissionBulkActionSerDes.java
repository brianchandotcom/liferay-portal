/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.BulkActionItem;
import com.liferay.bulk.rest.client.dto.v1_0.ResetPermissionBulkAction;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ResetPermissionBulkActionSerDes {

	public static ResetPermissionBulkAction toDTO(String json) {
		ResetPermissionBulkActionJSONParser
			resetPermissionBulkActionJSONParser =
				new ResetPermissionBulkActionJSONParser();

		return resetPermissionBulkActionJSONParser.parseToDTO(json);
	}

	public static ResetPermissionBulkAction[] toDTOs(String json) {
		ResetPermissionBulkActionJSONParser
			resetPermissionBulkActionJSONParser =
				new ResetPermissionBulkActionJSONParser();

		return resetPermissionBulkActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ResetPermissionBulkAction resetPermissionBulkAction) {

		if (resetPermissionBulkAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (resetPermissionBulkAction.getBulkActionItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bulkActionItems\": ");

			sb.append("[");

			for (int i = 0;
				 i < resetPermissionBulkAction.getBulkActionItems().length;
				 i++) {

				sb.append(
					String.valueOf(
						resetPermissionBulkAction.getBulkActionItems()[i]));

				if ((i + 1) <
						resetPermissionBulkAction.getBulkActionItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (resetPermissionBulkAction.getSelectionScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectionScope\": ");

			sb.append(
				String.valueOf(resetPermissionBulkAction.getSelectionScope()));
		}

		if (resetPermissionBulkAction.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(resetPermissionBulkAction.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ResetPermissionBulkActionJSONParser
			resetPermissionBulkActionJSONParser =
				new ResetPermissionBulkActionJSONParser();

		return resetPermissionBulkActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ResetPermissionBulkAction resetPermissionBulkAction) {

		if (resetPermissionBulkAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (resetPermissionBulkAction.getBulkActionItems() == null) {
			map.put("bulkActionItems", null);
		}
		else {
			map.put(
				"bulkActionItems",
				String.valueOf(resetPermissionBulkAction.getBulkActionItems()));
		}

		if (resetPermissionBulkAction.getSelectionScope() == null) {
			map.put("selectionScope", null);
		}
		else {
			map.put(
				"selectionScope",
				String.valueOf(resetPermissionBulkAction.getSelectionScope()));
		}

		if (resetPermissionBulkAction.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put(
				"type", String.valueOf(resetPermissionBulkAction.getType()));
		}

		return map;
	}

	public static class ResetPermissionBulkActionJSONParser
		extends BaseJSONParser<ResetPermissionBulkAction> {

		@Override
		protected ResetPermissionBulkAction createDTO() {
			return new ResetPermissionBulkAction();
		}

		@Override
		protected ResetPermissionBulkAction[] createDTOArray(int size) {
			return new ResetPermissionBulkAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "bulkActionItems")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "selectionScope")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ResetPermissionBulkAction resetPermissionBulkAction,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "bulkActionItems")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					BulkActionItem[] bulkActionItemsArray =
						new BulkActionItem[jsonParserFieldValues.length];

					for (int i = 0; i < bulkActionItemsArray.length; i++) {
						bulkActionItemsArray[i] = BulkActionItemSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					resetPermissionBulkAction.setBulkActionItems(
						bulkActionItemsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectionScope")) {
				if (jsonParserFieldValue != null) {
					resetPermissionBulkAction.setSelectionScope(
						SelectionScopeSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					resetPermissionBulkAction.setType(
						ResetPermissionBulkAction.Type.create(
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