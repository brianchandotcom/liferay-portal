/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.client.serdes.v1_0;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectFolderItem;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectFolderItemSerDes {

	public static ObjectFolderItem toDTO(String json) {
		ObjectFolderItemJSONParser objectFolderItemJSONParser =
			new ObjectFolderItemJSONParser();

		return objectFolderItemJSONParser.parseToDTO(json);
	}

	public static ObjectFolderItem[] toDTOs(String json) {
		ObjectFolderItemJSONParser objectFolderItemJSONParser =
			new ObjectFolderItemJSONParser();

		return objectFolderItemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectFolderItem objectFolderItem) {
		if (objectFolderItem == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectFolderItem.getLinkedObjectDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"linkedObjectDefinition\": ");

			sb.append(objectFolderItem.getLinkedObjectDefinition());
		}

		if (objectFolderItem.getObjectDefinitionExternalReferenceCode() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectDefinitionExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					objectFolderItem.
						getObjectDefinitionExternalReferenceCode()));

			sb.append("\"");
		}

		if (objectFolderItem.getPositionX() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"positionX\": ");

			sb.append(objectFolderItem.getPositionX());
		}

		if (objectFolderItem.getPositionY() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"positionY\": ");

			sb.append(objectFolderItem.getPositionY());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectFolderItemJSONParser objectFolderItemJSONParser =
			new ObjectFolderItemJSONParser();

		return objectFolderItemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectFolderItem objectFolderItem) {
		if (objectFolderItem == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectFolderItem.getLinkedObjectDefinition() == null) {
			map.put("linkedObjectDefinition", null);
		}
		else {
			map.put(
				"linkedObjectDefinition",
				String.valueOf(objectFolderItem.getLinkedObjectDefinition()));
		}

		if (objectFolderItem.getObjectDefinitionExternalReferenceCode() ==
				null) {

			map.put("objectDefinitionExternalReferenceCode", null);
		}
		else {
			map.put(
				"objectDefinitionExternalReferenceCode",
				String.valueOf(
					objectFolderItem.
						getObjectDefinitionExternalReferenceCode()));
		}

		if (objectFolderItem.getPositionX() == null) {
			map.put("positionX", null);
		}
		else {
			map.put(
				"positionX", String.valueOf(objectFolderItem.getPositionX()));
		}

		if (objectFolderItem.getPositionY() == null) {
			map.put("positionY", null);
		}
		else {
			map.put(
				"positionY", String.valueOf(objectFolderItem.getPositionY()));
		}

		return map;
	}

	public static class ObjectFolderItemJSONParser
		extends BaseJSONParser<ObjectFolderItem> {

		@Override
		protected ObjectFolderItem createDTO() {
			return new ObjectFolderItem();
		}

		@Override
		protected ObjectFolderItem[] createDTOArray(int size) {
			return new ObjectFolderItem[size];
		}

		@Override
		protected void setField(
			ObjectFolderItem objectFolderItem, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "linkedObjectDefinition")) {
				if (jsonParserFieldValue != null) {
					objectFolderItem.setLinkedObjectDefinition(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"objectDefinitionExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					objectFolderItem.setObjectDefinitionExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "positionX")) {
				if (jsonParserFieldValue != null) {
					objectFolderItem.setPositionX(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "positionY")) {
				if (jsonParserFieldValue != null) {
					objectFolderItem.setPositionY(
						Integer.valueOf((String)jsonParserFieldValue));
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

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}