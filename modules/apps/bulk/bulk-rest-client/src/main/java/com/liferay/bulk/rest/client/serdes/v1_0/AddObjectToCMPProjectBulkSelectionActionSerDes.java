/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.AddObjectToCMPProjectBulkSelectionAction;
import com.liferay.bulk.rest.client.dto.v1_0.BulkActionItem;
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
public class AddObjectToCMPProjectBulkSelectionActionSerDes {

	public static AddObjectToCMPProjectBulkSelectionAction toDTO(String json) {
		AddObjectToCMPProjectBulkSelectionActionJSONParser
			addObjectToCMPProjectBulkSelectionActionJSONParser =
				new AddObjectToCMPProjectBulkSelectionActionJSONParser();

		return addObjectToCMPProjectBulkSelectionActionJSONParser.parseToDTO(
			json);
	}

	public static AddObjectToCMPProjectBulkSelectionAction[] toDTOs(
		String json) {

		AddObjectToCMPProjectBulkSelectionActionJSONParser
			addObjectToCMPProjectBulkSelectionActionJSONParser =
				new AddObjectToCMPProjectBulkSelectionActionJSONParser();

		return addObjectToCMPProjectBulkSelectionActionJSONParser.parseToDTOs(
			json);
	}

	public static String toJSON(
		AddObjectToCMPProjectBulkSelectionAction
			addObjectToCMPProjectBulkSelectionAction) {

		if (addObjectToCMPProjectBulkSelectionAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (addObjectToCMPProjectBulkSelectionAction.getProjectScopeKeys() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"projectScopeKeys\": ");

			sb.append("[");

			for (int i = 0;
				 i < addObjectToCMPProjectBulkSelectionAction.
					 getProjectScopeKeys().length;
				 i++) {

				sb.append(
					_toJSON(
						addObjectToCMPProjectBulkSelectionAction.
							getProjectScopeKeys()[i]));

				if ((i + 1) < addObjectToCMPProjectBulkSelectionAction.
						getProjectScopeKeys().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (addObjectToCMPProjectBulkSelectionAction.getBulkActionItems() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bulkActionItems\": ");

			sb.append("[");

			for (int i = 0;
				 i < addObjectToCMPProjectBulkSelectionAction.
					 getBulkActionItems().length;
				 i++) {

				sb.append(
					String.valueOf(
						addObjectToCMPProjectBulkSelectionAction.
							getBulkActionItems()[i]));

				if ((i + 1) < addObjectToCMPProjectBulkSelectionAction.
						getBulkActionItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (addObjectToCMPProjectBulkSelectionAction.getSelectionScope() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectionScope\": ");

			sb.append(
				String.valueOf(
					addObjectToCMPProjectBulkSelectionAction.
						getSelectionScope()));
		}

		if (addObjectToCMPProjectBulkSelectionAction.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(addObjectToCMPProjectBulkSelectionAction.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AddObjectToCMPProjectBulkSelectionActionJSONParser
			addObjectToCMPProjectBulkSelectionActionJSONParser =
				new AddObjectToCMPProjectBulkSelectionActionJSONParser();

		return addObjectToCMPProjectBulkSelectionActionJSONParser.parseToMap(
			json);
	}

	public static Map<String, String> toMap(
		AddObjectToCMPProjectBulkSelectionAction
			addObjectToCMPProjectBulkSelectionAction) {

		if (addObjectToCMPProjectBulkSelectionAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (addObjectToCMPProjectBulkSelectionAction.getProjectScopeKeys() ==
				null) {

			map.put("projectScopeKeys", null);
		}
		else {
			map.put(
				"projectScopeKeys",
				String.valueOf(
					addObjectToCMPProjectBulkSelectionAction.
						getProjectScopeKeys()));
		}

		if (addObjectToCMPProjectBulkSelectionAction.getBulkActionItems() ==
				null) {

			map.put("bulkActionItems", null);
		}
		else {
			map.put(
				"bulkActionItems",
				String.valueOf(
					addObjectToCMPProjectBulkSelectionAction.
						getBulkActionItems()));
		}

		if (addObjectToCMPProjectBulkSelectionAction.getSelectionScope() ==
				null) {

			map.put("selectionScope", null);
		}
		else {
			map.put(
				"selectionScope",
				String.valueOf(
					addObjectToCMPProjectBulkSelectionAction.
						getSelectionScope()));
		}

		if (addObjectToCMPProjectBulkSelectionAction.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put(
				"type",
				String.valueOf(
					addObjectToCMPProjectBulkSelectionAction.getType()));
		}

		return map;
	}

	public static class AddObjectToCMPProjectBulkSelectionActionJSONParser
		extends BaseJSONParser<AddObjectToCMPProjectBulkSelectionAction> {

		@Override
		protected AddObjectToCMPProjectBulkSelectionAction createDTO() {
			return new AddObjectToCMPProjectBulkSelectionAction();
		}

		@Override
		protected AddObjectToCMPProjectBulkSelectionAction[] createDTOArray(
			int size) {

			return new AddObjectToCMPProjectBulkSelectionAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "projectScopeKeys")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "bulkActionItems")) {
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
			AddObjectToCMPProjectBulkSelectionAction
				addObjectToCMPProjectBulkSelectionAction,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "projectScopeKeys")) {
				if (jsonParserFieldValue != null) {
					addObjectToCMPProjectBulkSelectionAction.
						setProjectScopeKeys(
							toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "bulkActionItems")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					BulkActionItem[] bulkActionItemsArray =
						new BulkActionItem[jsonParserFieldValues.length];

					for (int i = 0; i < bulkActionItemsArray.length; i++) {
						bulkActionItemsArray[i] = BulkActionItemSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					addObjectToCMPProjectBulkSelectionAction.setBulkActionItems(
						bulkActionItemsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectionScope")) {
				if (jsonParserFieldValue != null) {
					addObjectToCMPProjectBulkSelectionAction.setSelectionScope(
						SelectionScopeSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					addObjectToCMPProjectBulkSelectionAction.setType(
						AddObjectToCMPProjectBulkSelectionAction.Type.create(
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
// LIFERAY-REST-BUILDER-HASH:-203574900