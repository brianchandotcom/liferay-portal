/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.serdes.v1_0;

import com.liferay.headless.object.client.dto.v1_0.ObjectEntryFolderBrief;
import com.liferay.headless.object.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class ObjectEntryFolderBriefSerDes {

	public static ObjectEntryFolderBrief toDTO(String json) {
		ObjectEntryFolderBriefJSONParser objectEntryFolderBriefJSONParser =
			new ObjectEntryFolderBriefJSONParser();

		return objectEntryFolderBriefJSONParser.parseToDTO(json);
	}

	public static ObjectEntryFolderBrief[] toDTOs(String json) {
		ObjectEntryFolderBriefJSONParser objectEntryFolderBriefJSONParser =
			new ObjectEntryFolderBriefJSONParser();

		return objectEntryFolderBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectEntryFolderBrief objectEntryFolderBrief) {
		if (objectEntryFolderBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectEntryFolderBrief.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(objectEntryFolderBrief.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (objectEntryFolderBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectEntryFolderBrief.getId());
		}

		if (objectEntryFolderBrief.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(objectEntryFolderBrief.getLabel()));

			sb.append("\"");
		}

		if (objectEntryFolderBrief.getLabel_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label_i18n\": ");

			sb.append(_toJSON(objectEntryFolderBrief.getLabel_i18n()));
		}

		if (objectEntryFolderBrief.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(objectEntryFolderBrief.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectEntryFolderBriefJSONParser objectEntryFolderBriefJSONParser =
			new ObjectEntryFolderBriefJSONParser();

		return objectEntryFolderBriefJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ObjectEntryFolderBrief objectEntryFolderBrief) {

		if (objectEntryFolderBrief == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectEntryFolderBrief.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(
					objectEntryFolderBrief.getExternalReferenceCode()));
		}

		if (objectEntryFolderBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectEntryFolderBrief.getId()));
		}

		if (objectEntryFolderBrief.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(objectEntryFolderBrief.getLabel()));
		}

		if (objectEntryFolderBrief.getLabel_i18n() == null) {
			map.put("label_i18n", null);
		}
		else {
			map.put(
				"label_i18n",
				String.valueOf(objectEntryFolderBrief.getLabel_i18n()));
		}

		if (objectEntryFolderBrief.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectEntryFolderBrief.getName()));
		}

		return map;
	}

	public static class ObjectEntryFolderBriefJSONParser
		extends BaseJSONParser<ObjectEntryFolderBrief> {

		@Override
		protected ObjectEntryFolderBrief createDTO() {
			return new ObjectEntryFolderBrief();
		}

		@Override
		protected ObjectEntryFolderBrief[] createDTOArray(int size) {
			return new ObjectEntryFolderBrief[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "externalReferenceCode")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "label_i18n")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ObjectEntryFolderBrief objectEntryFolderBrief,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "externalReferenceCode")) {
				if (jsonParserFieldValue != null) {
					objectEntryFolderBrief.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectEntryFolderBrief.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					objectEntryFolderBrief.setLabel(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label_i18n")) {
				if (jsonParserFieldValue != null) {
					objectEntryFolderBrief.setLabel_i18n(
						(Map<String, String>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectEntryFolderBrief.setName(
						(String)jsonParserFieldValue);
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