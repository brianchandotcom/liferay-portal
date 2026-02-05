/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.AssignStructureDefaultWorkflowBulkAction;
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
public class AssignStructureDefaultWorkflowBulkActionSerDes {

	public static AssignStructureDefaultWorkflowBulkAction toDTO(String json) {
		AssignStructureDefaultWorkflowBulkActionJSONParser
			assignStructureDefaultWorkflowBulkActionJSONParser =
				new AssignStructureDefaultWorkflowBulkActionJSONParser();

		return assignStructureDefaultWorkflowBulkActionJSONParser.parseToDTO(
			json);
	}

	public static AssignStructureDefaultWorkflowBulkAction[] toDTOs(
		String json) {

		AssignStructureDefaultWorkflowBulkActionJSONParser
			assignStructureDefaultWorkflowBulkActionJSONParser =
				new AssignStructureDefaultWorkflowBulkActionJSONParser();

		return assignStructureDefaultWorkflowBulkActionJSONParser.parseToDTOs(
			json);
	}

	public static String toJSON(
		AssignStructureDefaultWorkflowBulkAction
			assignStructureDefaultWorkflowBulkAction) {

		if (assignStructureDefaultWorkflowBulkAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (assignStructureDefaultWorkflowBulkAction.getWorkflow() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflow\": ");

			sb.append("\"");

			sb.append(
				_escape(
					assignStructureDefaultWorkflowBulkAction.getWorkflow()));

			sb.append("\"");
		}

		if (assignStructureDefaultWorkflowBulkAction.getBulkActionItems() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bulkActionItems\": ");

			sb.append("[");

			for (int i = 0;
				 i < assignStructureDefaultWorkflowBulkAction.
					 getBulkActionItems().length;
				 i++) {

				sb.append(
					String.valueOf(
						assignStructureDefaultWorkflowBulkAction.
							getBulkActionItems()[i]));

				if ((i + 1) < assignStructureDefaultWorkflowBulkAction.
						getBulkActionItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (assignStructureDefaultWorkflowBulkAction.getSelectionScope() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectionScope\": ");

			sb.append(
				String.valueOf(
					assignStructureDefaultWorkflowBulkAction.
						getSelectionScope()));
		}

		if (assignStructureDefaultWorkflowBulkAction.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(assignStructureDefaultWorkflowBulkAction.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AssignStructureDefaultWorkflowBulkActionJSONParser
			assignStructureDefaultWorkflowBulkActionJSONParser =
				new AssignStructureDefaultWorkflowBulkActionJSONParser();

		return assignStructureDefaultWorkflowBulkActionJSONParser.parseToMap(
			json);
	}

	public static Map<String, String> toMap(
		AssignStructureDefaultWorkflowBulkAction
			assignStructureDefaultWorkflowBulkAction) {

		if (assignStructureDefaultWorkflowBulkAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (assignStructureDefaultWorkflowBulkAction.getWorkflow() == null) {
			map.put("workflow", null);
		}
		else {
			map.put(
				"workflow",
				String.valueOf(
					assignStructureDefaultWorkflowBulkAction.getWorkflow()));
		}

		if (assignStructureDefaultWorkflowBulkAction.getBulkActionItems() ==
				null) {

			map.put("bulkActionItems", null);
		}
		else {
			map.put(
				"bulkActionItems",
				String.valueOf(
					assignStructureDefaultWorkflowBulkAction.
						getBulkActionItems()));
		}

		if (assignStructureDefaultWorkflowBulkAction.getSelectionScope() ==
				null) {

			map.put("selectionScope", null);
		}
		else {
			map.put(
				"selectionScope",
				String.valueOf(
					assignStructureDefaultWorkflowBulkAction.
						getSelectionScope()));
		}

		if (assignStructureDefaultWorkflowBulkAction.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put(
				"type",
				String.valueOf(
					assignStructureDefaultWorkflowBulkAction.getType()));
		}

		return map;
	}

	public static class AssignStructureDefaultWorkflowBulkActionJSONParser
		extends BaseJSONParser<AssignStructureDefaultWorkflowBulkAction> {

		@Override
		protected AssignStructureDefaultWorkflowBulkAction createDTO() {
			return new AssignStructureDefaultWorkflowBulkAction();
		}

		@Override
		protected AssignStructureDefaultWorkflowBulkAction[] createDTOArray(
			int size) {

			return new AssignStructureDefaultWorkflowBulkAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "workflow")) {
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
			AssignStructureDefaultWorkflowBulkAction
				assignStructureDefaultWorkflowBulkAction,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "workflow")) {
				if (jsonParserFieldValue != null) {
					assignStructureDefaultWorkflowBulkAction.setWorkflow(
						(String)jsonParserFieldValue);
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

					assignStructureDefaultWorkflowBulkAction.setBulkActionItems(
						bulkActionItemsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectionScope")) {
				if (jsonParserFieldValue != null) {
					assignStructureDefaultWorkflowBulkAction.setSelectionScope(
						SelectionScopeSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					assignStructureDefaultWorkflowBulkAction.setType(
						AssignStructureDefaultWorkflowBulkAction.Type.create(
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