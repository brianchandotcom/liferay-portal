/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.BulkActionItem;
import com.liferay.bulk.rest.client.dto.v1_0.DueDateBulkAction;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class DueDateBulkActionSerDes {

	public static DueDateBulkAction toDTO(String json) {
		DueDateBulkActionJSONParser dueDateBulkActionJSONParser =
			new DueDateBulkActionJSONParser();

		return dueDateBulkActionJSONParser.parseToDTO(json);
	}

	public static DueDateBulkAction[] toDTOs(String json) {
		DueDateBulkActionJSONParser dueDateBulkActionJSONParser =
			new DueDateBulkActionJSONParser();

		return dueDateBulkActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DueDateBulkAction dueDateBulkAction) {
		if (dueDateBulkAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (dueDateBulkAction.getDueDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(dueDateBulkAction.getDueDate()));

			sb.append("\"");
		}

		if (dueDateBulkAction.getBulkActionItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bulkActionItems\": ");

			sb.append("[");

			for (int i = 0; i < dueDateBulkAction.getBulkActionItems().length;
				 i++) {

				sb.append(
					String.valueOf(dueDateBulkAction.getBulkActionItems()[i]));

				if ((i + 1) < dueDateBulkAction.getBulkActionItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dueDateBulkAction.getSelectionScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectionScope\": ");

			sb.append(String.valueOf(dueDateBulkAction.getSelectionScope()));
		}

		if (dueDateBulkAction.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(dueDateBulkAction.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DueDateBulkActionJSONParser dueDateBulkActionJSONParser =
			new DueDateBulkActionJSONParser();

		return dueDateBulkActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DueDateBulkAction dueDateBulkAction) {

		if (dueDateBulkAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (dueDateBulkAction.getDueDate() == null) {
			map.put("dueDate", null);
		}
		else {
			map.put(
				"dueDate",
				liferayToJSONDateFormat.format(dueDateBulkAction.getDueDate()));
		}

		if (dueDateBulkAction.getBulkActionItems() == null) {
			map.put("bulkActionItems", null);
		}
		else {
			map.put(
				"bulkActionItems",
				String.valueOf(dueDateBulkAction.getBulkActionItems()));
		}

		if (dueDateBulkAction.getSelectionScope() == null) {
			map.put("selectionScope", null);
		}
		else {
			map.put(
				"selectionScope",
				String.valueOf(dueDateBulkAction.getSelectionScope()));
		}

		if (dueDateBulkAction.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(dueDateBulkAction.getType()));
		}

		return map;
	}

	public static class DueDateBulkActionJSONParser
		extends BaseJSONParser<DueDateBulkAction> {

		@Override
		protected DueDateBulkAction createDTO() {
			return new DueDateBulkAction();
		}

		@Override
		protected DueDateBulkAction[] createDTOArray(int size) {
			return new DueDateBulkAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "dueDate")) {
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
			DueDateBulkAction dueDateBulkAction, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dueDate")) {
				if (jsonParserFieldValue != null) {
					dueDateBulkAction.setDueDate(
						toDate((String)jsonParserFieldValue));
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

					dueDateBulkAction.setBulkActionItems(bulkActionItemsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectionScope")) {
				if (jsonParserFieldValue != null) {
					dueDateBulkAction.setSelectionScope(
						SelectionScopeSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					dueDateBulkAction.setType(
						DueDateBulkAction.Type.create(
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