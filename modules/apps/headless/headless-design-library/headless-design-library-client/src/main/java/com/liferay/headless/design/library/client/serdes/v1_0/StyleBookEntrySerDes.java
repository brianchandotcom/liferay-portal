/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.design.library.client.serdes.v1_0;

import com.liferay.headless.design.library.client.dto.v1_0.StyleBookEntry;
import com.liferay.headless.design.library.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Luis Ortiz
 * @generated
 */
@Generated("")
public class StyleBookEntrySerDes {

	public static StyleBookEntry toDTO(String json) {
		StyleBookEntryJSONParser styleBookEntryJSONParser =
			new StyleBookEntryJSONParser();

		return styleBookEntryJSONParser.parseToDTO(json);
	}

	public static StyleBookEntry[] toDTOs(String json) {
		StyleBookEntryJSONParser styleBookEntryJSONParser =
			new StyleBookEntryJSONParser();

		return styleBookEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(StyleBookEntry styleBookEntry) {
		if (styleBookEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (styleBookEntry.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(styleBookEntry.getActions()));
		}

		if (styleBookEntry.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(styleBookEntry.getCreator());
		}

		if (styleBookEntry.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					styleBookEntry.getDateCreated()));

			sb.append("\"");
		}

		if (styleBookEntry.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					styleBookEntry.getDateModified()));

			sb.append("\"");
		}

		if (styleBookEntry.getDefaultStyleBookEntry() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultStyleBookEntry\": ");

			sb.append(styleBookEntry.getDefaultStyleBookEntry());
		}

		if (styleBookEntry.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(styleBookEntry.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (styleBookEntry.getFrontendTokensValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"frontendTokensValues\": ");

			sb.append("\"");

			sb.append(_escape(styleBookEntry.getFrontendTokensValues()));

			sb.append("\"");
		}

		if (styleBookEntry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(styleBookEntry.getId());
		}

		if (styleBookEntry.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(styleBookEntry.getKey()));

			sb.append("\"");
		}

		if (styleBookEntry.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(styleBookEntry.getName()));

			sb.append("\"");
		}

		if (styleBookEntry.getPreviewFileEntryExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"previewFileEntryExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					styleBookEntry.getPreviewFileEntryExternalReferenceCode()));

			sb.append("\"");
		}

		if (styleBookEntry.getThemeId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"themeId\": ");

			sb.append("\"");

			sb.append(_escape(styleBookEntry.getThemeId()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		StyleBookEntryJSONParser styleBookEntryJSONParser =
			new StyleBookEntryJSONParser();

		return styleBookEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(StyleBookEntry styleBookEntry) {
		if (styleBookEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (styleBookEntry.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(styleBookEntry.getActions()));
		}

		if (styleBookEntry.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(styleBookEntry.getCreator()));
		}

		if (styleBookEntry.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					styleBookEntry.getDateCreated()));
		}

		if (styleBookEntry.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					styleBookEntry.getDateModified()));
		}

		if (styleBookEntry.getDefaultStyleBookEntry() == null) {
			map.put("defaultStyleBookEntry", null);
		}
		else {
			map.put(
				"defaultStyleBookEntry",
				String.valueOf(styleBookEntry.getDefaultStyleBookEntry()));
		}

		if (styleBookEntry.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(styleBookEntry.getExternalReferenceCode()));
		}

		if (styleBookEntry.getFrontendTokensValues() == null) {
			map.put("frontendTokensValues", null);
		}
		else {
			map.put(
				"frontendTokensValues",
				String.valueOf(styleBookEntry.getFrontendTokensValues()));
		}

		if (styleBookEntry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(styleBookEntry.getId()));
		}

		if (styleBookEntry.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(styleBookEntry.getKey()));
		}

		if (styleBookEntry.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(styleBookEntry.getName()));
		}

		if (styleBookEntry.getPreviewFileEntryExternalReferenceCode() == null) {
			map.put("previewFileEntryExternalReferenceCode", null);
		}
		else {
			map.put(
				"previewFileEntryExternalReferenceCode",
				String.valueOf(
					styleBookEntry.getPreviewFileEntryExternalReferenceCode()));
		}

		if (styleBookEntry.getThemeId() == null) {
			map.put("themeId", null);
		}
		else {
			map.put("themeId", String.valueOf(styleBookEntry.getThemeId()));
		}

		return map;
	}

	public static class StyleBookEntryJSONParser
		extends BaseJSONParser<StyleBookEntry> {

		@Override
		protected StyleBookEntry createDTO() {
			return new StyleBookEntry();
		}

		@Override
		protected StyleBookEntry[] createDTOArray(int size) {
			return new StyleBookEntry[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "actions")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultStyleBookEntry")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "frontendTokensValues")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"previewFileEntryExternalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "themeId")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			StyleBookEntry styleBookEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					styleBookEntry.setActions(
						(Map<String, Map<String, String>>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					styleBookEntry.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					styleBookEntry.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					styleBookEntry.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultStyleBookEntry")) {

				if (jsonParserFieldValue != null) {
					styleBookEntry.setDefaultStyleBookEntry(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					styleBookEntry.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "frontendTokensValues")) {

				if (jsonParserFieldValue != null) {
					styleBookEntry.setFrontendTokensValues(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					styleBookEntry.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					styleBookEntry.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					styleBookEntry.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"previewFileEntryExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					styleBookEntry.setPreviewFileEntryExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "themeId")) {
				if (jsonParserFieldValue != null) {
					styleBookEntry.setThemeId((String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:1765328696