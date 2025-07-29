/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.serdes.v1_0;

import com.liferay.headless.object.client.dto.v1_0.ObjectEntryCMSBulkAction;
import com.liferay.headless.object.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class ObjectEntryCMSBulkActionSerDes {

	public static ObjectEntryCMSBulkAction toDTO(String json) {
		ObjectEntryCMSBulkActionJSONParser objectEntryCMSBulkActionJSONParser =
			new ObjectEntryCMSBulkActionJSONParser();

		return objectEntryCMSBulkActionJSONParser.parseToDTO(json);
	}

	public static ObjectEntryCMSBulkAction[] toDTOs(String json) {
		ObjectEntryCMSBulkActionJSONParser objectEntryCMSBulkActionJSONParser =
			new ObjectEntryCMSBulkActionJSONParser();

		return objectEntryCMSBulkActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ObjectEntryCMSBulkAction objectEntryCMSBulkAction) {

		if (objectEntryCMSBulkAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectEntryCMSBulkAction.getIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ids\": ");

			sb.append("[");

			for (int i = 0; i < objectEntryCMSBulkAction.getIds().length; i++) {
				sb.append(objectEntryCMSBulkAction.getIds()[i]);

				if ((i + 1) < objectEntryCMSBulkAction.getIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectEntryCMSBulkAction.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("[");

			for (int i = 0; i < objectEntryCMSBulkAction.getKeywords().length;
				 i++) {

				sb.append(_toJSON(objectEntryCMSBulkAction.getKeywords()[i]));

				if ((i + 1) < objectEntryCMSBulkAction.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectEntryCMSBulkAction.getObjectEntryFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectEntryFolderId\": ");

			sb.append(objectEntryCMSBulkAction.getObjectEntryFolderId());
		}

		if (objectEntryCMSBulkAction.getPermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"permissions\": ");

			sb.append("[");

			for (int i = 0;
				 i < objectEntryCMSBulkAction.getPermissions().length; i++) {

				sb.append(objectEntryCMSBulkAction.getPermissions()[i]);

				if ((i + 1) <
						objectEntryCMSBulkAction.getPermissions().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectEntryCMSBulkAction.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(objectEntryCMSBulkAction.getStatus());
		}

		if (objectEntryCMSBulkAction.getTaxonomyCategoryIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryIds\": ");

			sb.append("[");

			for (int i = 0;
				 i < objectEntryCMSBulkAction.getTaxonomyCategoryIds().length;
				 i++) {

				sb.append(objectEntryCMSBulkAction.getTaxonomyCategoryIds()[i]);

				if ((i + 1) <
						objectEntryCMSBulkAction.
							getTaxonomyCategoryIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectEntryCMSBulkActionJSONParser objectEntryCMSBulkActionJSONParser =
			new ObjectEntryCMSBulkActionJSONParser();

		return objectEntryCMSBulkActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ObjectEntryCMSBulkAction objectEntryCMSBulkAction) {

		if (objectEntryCMSBulkAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectEntryCMSBulkAction.getIds() == null) {
			map.put("ids", null);
		}
		else {
			map.put("ids", String.valueOf(objectEntryCMSBulkAction.getIds()));
		}

		if (objectEntryCMSBulkAction.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put(
				"keywords",
				String.valueOf(objectEntryCMSBulkAction.getKeywords()));
		}

		if (objectEntryCMSBulkAction.getObjectEntryFolderId() == null) {
			map.put("objectEntryFolderId", null);
		}
		else {
			map.put(
				"objectEntryFolderId",
				String.valueOf(
					objectEntryCMSBulkAction.getObjectEntryFolderId()));
		}

		if (objectEntryCMSBulkAction.getPermissions() == null) {
			map.put("permissions", null);
		}
		else {
			map.put(
				"permissions",
				String.valueOf(objectEntryCMSBulkAction.getPermissions()));
		}

		if (objectEntryCMSBulkAction.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put(
				"status", String.valueOf(objectEntryCMSBulkAction.getStatus()));
		}

		if (objectEntryCMSBulkAction.getTaxonomyCategoryIds() == null) {
			map.put("taxonomyCategoryIds", null);
		}
		else {
			map.put(
				"taxonomyCategoryIds",
				String.valueOf(
					objectEntryCMSBulkAction.getTaxonomyCategoryIds()));
		}

		return map;
	}

	public static class ObjectEntryCMSBulkActionJSONParser
		extends BaseJSONParser<ObjectEntryCMSBulkAction> {

		@Override
		protected ObjectEntryCMSBulkAction createDTO() {
			return new ObjectEntryCMSBulkAction();
		}

		@Override
		protected ObjectEntryCMSBulkAction[] createDTOArray(int size) {
			return new ObjectEntryCMSBulkAction[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "ids")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectEntryFolderId")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "permissions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIds")) {

				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ObjectEntryCMSBulkAction objectEntryCMSBulkAction,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "ids")) {
				if (jsonParserFieldValue != null) {
					objectEntryCMSBulkAction.setIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					objectEntryCMSBulkAction.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectEntryFolderId")) {

				if (jsonParserFieldValue != null) {
					objectEntryCMSBulkAction.setObjectEntryFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "permissions")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					com.liferay.headless.object.client.permission.Permission[]
						permissionsArray = new
						com.liferay.headless.object.client.permission.Permission
							[jsonParserFieldValues.length];

					for (int i = 0; i < permissionsArray.length; i++) {
						permissionsArray[i] =
							com.liferay.headless.object.client.permission.
								Permission.toDTO(
									(String)jsonParserFieldValues[i]);
					}

					objectEntryCMSBulkAction.setPermissions(permissionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					objectEntryCMSBulkAction.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIds")) {

				if (jsonParserFieldValue != null) {
					objectEntryCMSBulkAction.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
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