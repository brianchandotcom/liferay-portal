/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.BulkActionItem;
import com.liferay.bulk.rest.client.dto.v1_0.UpdateValuesBulkAction;
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
public class UpdateValuesBulkActionSerDes {

	public static UpdateValuesBulkAction toDTO(String json) {
		UpdateValuesBulkActionJSONParser updateValuesBulkActionJSONParser =
			new UpdateValuesBulkActionJSONParser();

		return updateValuesBulkActionJSONParser.parseToDTO(json);
	}

	public static UpdateValuesBulkAction[] toDTOs(String json) {
		UpdateValuesBulkActionJSONParser updateValuesBulkActionJSONParser =
			new UpdateValuesBulkActionJSONParser();

		return updateValuesBulkActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UpdateValuesBulkAction updateValuesBulkAction) {
		if (updateValuesBulkAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (updateValuesBulkAction.getValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"values\": ");

			sb.append(_toJSON(updateValuesBulkAction.getValues()));
		}

		if (updateValuesBulkAction.getBulkActionItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bulkActionItems\": ");

			sb.append("[");

			for (int i = 0;
				 i < updateValuesBulkAction.getBulkActionItems().length; i++) {

				sb.append(
					String.valueOf(
						updateValuesBulkAction.getBulkActionItems()[i]));

				if ((i + 1) <
						updateValuesBulkAction.getBulkActionItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (updateValuesBulkAction.getSelectionScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectionScope\": ");

			sb.append(
				String.valueOf(updateValuesBulkAction.getSelectionScope()));
		}

		if (updateValuesBulkAction.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(updateValuesBulkAction.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UpdateValuesBulkActionJSONParser updateValuesBulkActionJSONParser =
			new UpdateValuesBulkActionJSONParser();

		return updateValuesBulkActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UpdateValuesBulkAction updateValuesBulkAction) {

		if (updateValuesBulkAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (updateValuesBulkAction.getValues() == null) {
			map.put("values", null);
		}
		else {
			map.put(
				"values", String.valueOf(updateValuesBulkAction.getValues()));
		}

		if (updateValuesBulkAction.getBulkActionItems() == null) {
			map.put("bulkActionItems", null);
		}
		else {
			map.put(
				"bulkActionItems",
				String.valueOf(updateValuesBulkAction.getBulkActionItems()));
		}

		if (updateValuesBulkAction.getSelectionScope() == null) {
			map.put("selectionScope", null);
		}
		else {
			map.put(
				"selectionScope",
				String.valueOf(updateValuesBulkAction.getSelectionScope()));
		}

		if (updateValuesBulkAction.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(updateValuesBulkAction.getType()));
		}

		return map;
	}

	public static class UpdateValuesBulkActionJSONParser
		extends BaseJSONParser<UpdateValuesBulkAction> {

		@Override
		protected UpdateValuesBulkAction createDTO() {
			return new UpdateValuesBulkAction();
		}

		@Override
		protected UpdateValuesBulkAction[] createDTOArray(int size) {
			return new UpdateValuesBulkAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "values")) {
				return true;
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
			UpdateValuesBulkAction updateValuesBulkAction,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "values")) {
				if (jsonParserFieldValue != null) {
					updateValuesBulkAction.setValues(
						(Map<String, Object>)jsonParserFieldValue);
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

					updateValuesBulkAction.setBulkActionItems(
						bulkActionItemsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectionScope")) {
				if (jsonParserFieldValue != null) {
					updateValuesBulkAction.setSelectionScope(
						SelectionScopeSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					updateValuesBulkAction.setType(
						UpdateValuesBulkAction.Type.create(
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