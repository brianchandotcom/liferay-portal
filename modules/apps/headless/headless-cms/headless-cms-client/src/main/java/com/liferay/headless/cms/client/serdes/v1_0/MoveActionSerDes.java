/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.MoveAction;
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
public class MoveActionSerDes {

	public static MoveAction toDTO(String json) {
		MoveActionJSONParser moveActionJSONParser = new MoveActionJSONParser();

		return moveActionJSONParser.parseToDTO(json);
	}

	public static MoveAction[] toDTOs(String json) {
		MoveActionJSONParser moveActionJSONParser = new MoveActionJSONParser();

		return moveActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MoveAction moveAction) {
		if (moveAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (moveAction.getObjectEntryFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectEntryFolderId\": ");

			sb.append(moveAction.getObjectEntryFolderId());
		}

		if (moveAction.getAction() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			sb.append("\"");

			sb.append(moveAction.getAction());

			sb.append("\"");
		}

		if (moveAction.getSelectAll() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectAll\": ");

			sb.append(moveAction.getSelectAll());
		}

		if (moveAction.getSelections() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selections\": ");

			sb.append("[");

			for (int i = 0; i < moveAction.getSelections().length; i++) {
				sb.append(String.valueOf(moveAction.getSelections()[i]));

				if ((i + 1) < moveAction.getSelections().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MoveActionJSONParser moveActionJSONParser = new MoveActionJSONParser();

		return moveActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(MoveAction moveAction) {
		if (moveAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (moveAction.getObjectEntryFolderId() == null) {
			map.put("objectEntryFolderId", null);
		}
		else {
			map.put(
				"objectEntryFolderId",
				String.valueOf(moveAction.getObjectEntryFolderId()));
		}

		if (moveAction.getAction() == null) {
			map.put("action", null);
		}
		else {
			map.put("action", String.valueOf(moveAction.getAction()));
		}

		if (moveAction.getSelectAll() == null) {
			map.put("selectAll", null);
		}
		else {
			map.put("selectAll", String.valueOf(moveAction.getSelectAll()));
		}

		if (moveAction.getSelections() == null) {
			map.put("selections", null);
		}
		else {
			map.put("selections", String.valueOf(moveAction.getSelections()));
		}

		return map;
	}

	public static class MoveActionJSONParser
		extends BaseJSONParser<MoveAction> {

		@Override
		protected MoveAction createDTO() {
			return new MoveAction();
		}

		@Override
		protected MoveAction[] createDTOArray(int size) {
			return new MoveAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "objectEntryFolderId")) {
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
			MoveAction moveAction, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "objectEntryFolderId")) {
				if (jsonParserFieldValue != null) {
					moveAction.setObjectEntryFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "action")) {
				if (jsonParserFieldValue != null) {
					moveAction.setAction(
						MoveAction.Action.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectAll")) {
				if (jsonParserFieldValue != null) {
					moveAction.setSelectAll((Boolean)jsonParserFieldValue);
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

					moveAction.setSelections(selectionsArray);
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