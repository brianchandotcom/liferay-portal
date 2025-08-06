/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.DeleteAction;
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
public class DeleteActionSerDes {

	public static DeleteAction toDTO(String json) {
		DeleteActionJSONParser deleteActionJSONParser =
			new DeleteActionJSONParser();

		return deleteActionJSONParser.parseToDTO(json);
	}

	public static DeleteAction[] toDTOs(String json) {
		DeleteActionJSONParser deleteActionJSONParser =
			new DeleteActionJSONParser();

		return deleteActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DeleteAction deleteAction) {
		if (deleteAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (deleteAction.getAction() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			sb.append("\"");

			sb.append(deleteAction.getAction());

			sb.append("\"");
		}

		if (deleteAction.getSelectAll() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectAll\": ");

			sb.append(deleteAction.getSelectAll());
		}

		if (deleteAction.getSelections() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selections\": ");

			sb.append("[");

			for (int i = 0; i < deleteAction.getSelections().length; i++) {
				sb.append(String.valueOf(deleteAction.getSelections()[i]));

				if ((i + 1) < deleteAction.getSelections().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DeleteActionJSONParser deleteActionJSONParser =
			new DeleteActionJSONParser();

		return deleteActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DeleteAction deleteAction) {
		if (deleteAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (deleteAction.getAction() == null) {
			map.put("action", null);
		}
		else {
			map.put("action", String.valueOf(deleteAction.getAction()));
		}

		if (deleteAction.getSelectAll() == null) {
			map.put("selectAll", null);
		}
		else {
			map.put("selectAll", String.valueOf(deleteAction.getSelectAll()));
		}

		if (deleteAction.getSelections() == null) {
			map.put("selections", null);
		}
		else {
			map.put("selections", String.valueOf(deleteAction.getSelections()));
		}

		return map;
	}

	public static class DeleteActionJSONParser
		extends BaseJSONParser<DeleteAction> {

		@Override
		protected DeleteAction createDTO() {
			return new DeleteAction();
		}

		@Override
		protected DeleteAction[] createDTOArray(int size) {
			return new DeleteAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "action")) {
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
			DeleteAction deleteAction, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "action")) {
				if (jsonParserFieldValue != null) {
					deleteAction.setAction(
						DeleteAction.Action.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectAll")) {
				if (jsonParserFieldValue != null) {
					deleteAction.setSelectAll((Boolean)jsonParserFieldValue);
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

					deleteAction.setSelections(selectionsArray);
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