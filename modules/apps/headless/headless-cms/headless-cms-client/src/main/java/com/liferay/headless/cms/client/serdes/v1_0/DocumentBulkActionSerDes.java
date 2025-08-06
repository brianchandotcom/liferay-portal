/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.DeleteAction;
import com.liferay.headless.cms.client.dto.v1_0.DocumentBulkAction;
import com.liferay.headless.cms.client.dto.v1_0.MoveAction;
import com.liferay.headless.cms.client.dto.v1_0.PermissionAction;
import com.liferay.headless.cms.client.dto.v1_0.Selection;
import com.liferay.headless.cms.client.dto.v1_0.StatusAction;
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
public class DocumentBulkActionSerDes {

	public static DocumentBulkAction toDTO(String json) {
		DocumentBulkActionJSONParser documentBulkActionJSONParser =
			new DocumentBulkActionJSONParser();

		return documentBulkActionJSONParser.parseToDTO(json);
	}

	public static DocumentBulkAction[] toDTOs(String json) {
		DocumentBulkActionJSONParser documentBulkActionJSONParser =
			new DocumentBulkActionJSONParser();

		return documentBulkActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DocumentBulkAction documentBulkAction) {
		if (documentBulkAction == null) {
			return "null";
		}

		DocumentBulkAction.Type type = documentBulkAction.getType();

		if (type != null) {
			String typeString = type.toString();

			if (typeString.equals("CategoryAction")) {
				return CategoryActionSerDes.toJSON(
					(CategoryAction)documentBulkAction);
			}

			if (typeString.equals("DeleteAction")) {
				return DeleteActionSerDes.toJSON(
					(DeleteAction)documentBulkAction);
			}

			if (typeString.equals("MoveAction")) {
				return MoveActionSerDes.toJSON((MoveAction)documentBulkAction);
			}

			if (typeString.equals("PermissionAction")) {
				return PermissionActionSerDes.toJSON(
					(PermissionAction)documentBulkAction);
			}

			if (typeString.equals("StatusAction")) {
				return StatusActionSerDes.toJSON(
					(StatusAction)documentBulkAction);
			}

			if (typeString.equals("TagAction")) {
				return TagActionSerDes.toJSON((TagAction)documentBulkAction);
			}

			throw new IllegalArgumentException("Unknown type " + typeString);
		}
		else {
			throw new IllegalArgumentException("Missing type parameter");
		}
	}

	public static Map<String, Object> toMap(String json) {
		DocumentBulkActionJSONParser documentBulkActionJSONParser =
			new DocumentBulkActionJSONParser();

		return documentBulkActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DocumentBulkAction documentBulkAction) {

		if (documentBulkAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (documentBulkAction.getAction() == null) {
			map.put("action", null);
		}
		else {
			map.put("action", String.valueOf(documentBulkAction.getAction()));
		}

		if (documentBulkAction.getSelectAll() == null) {
			map.put("selectAll", null);
		}
		else {
			map.put(
				"selectAll", String.valueOf(documentBulkAction.getSelectAll()));
		}

		if (documentBulkAction.getSelections() == null) {
			map.put("selections", null);
		}
		else {
			map.put(
				"selections",
				String.valueOf(documentBulkAction.getSelections()));
		}

		return map;
	}

	public static class DocumentBulkActionJSONParser
		extends BaseJSONParser<DocumentBulkAction> {

		@Override
		protected DocumentBulkAction createDTO() {
			return null;
		}

		@Override
		protected DocumentBulkAction[] createDTOArray(int size) {
			return new DocumentBulkAction[size];
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
		public DocumentBulkAction parseToDTO(String json) {
			Map<String, Object> jsonMap = parseToMap(json);

			Object type = jsonMap.get("type");

			if (type != null) {
				String typeString = type.toString();

				if (typeString.equals("CategoryAction")) {
					return CategoryAction.toDTO(json);
				}

				if (typeString.equals("DeleteAction")) {
					return DeleteAction.toDTO(json);
				}

				if (typeString.equals("MoveAction")) {
					return MoveAction.toDTO(json);
				}

				if (typeString.equals("PermissionAction")) {
					return PermissionAction.toDTO(json);
				}

				if (typeString.equals("StatusAction")) {
					return StatusAction.toDTO(json);
				}

				if (typeString.equals("TagAction")) {
					return TagAction.toDTO(json);
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
			DocumentBulkAction documentBulkAction, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "action")) {
				if (jsonParserFieldValue != null) {
					documentBulkAction.setAction(
						DocumentBulkAction.Action.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectAll")) {
				if (jsonParserFieldValue != null) {
					documentBulkAction.setSelectAll(
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

					documentBulkAction.setSelections(selectionsArray);
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