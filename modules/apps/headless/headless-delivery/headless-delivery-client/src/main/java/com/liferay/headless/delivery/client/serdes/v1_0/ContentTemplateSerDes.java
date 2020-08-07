/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.ContentTemplate;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class ContentTemplateSerDes {

	public static ContentTemplate toDTO(String json) {
		ContentTemplateJSONParser contentTemplateJSONParser =
			new ContentTemplateJSONParser();

		return contentTemplateJSONParser.parseToDTO(json);
	}

	public static ContentTemplate[] toDTOs(String json) {
		ContentTemplateJSONParser contentTemplateJSONParser =
			new ContentTemplateJSONParser();

		return contentTemplateJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentTemplate contentTemplate) {
		if (contentTemplate == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (contentTemplate.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(contentTemplate.getActions()));
		}

		if (contentTemplate.getAssetLibraryKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assetLibraryKey\": ");

			sb.append("\"");

			sb.append(_escape(contentTemplate.getAssetLibraryKey()));

			sb.append("\"");
		}

		if (contentTemplate.getAvailableLanguages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\": ");

			sb.append("[");

			for (int i = 0; i < contentTemplate.getAvailableLanguages().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(contentTemplate.getAvailableLanguages()[i]));

				sb.append("\"");

				if ((i + 1) < contentTemplate.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentTemplate.getContentStructureId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentStructureId\": ");

			sb.append(contentTemplate.getContentStructureId());
		}

		if (contentTemplate.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(contentTemplate.getCreator()));
		}

		if (contentTemplate.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					contentTemplate.getDateCreated()));

			sb.append("\"");
		}

		if (contentTemplate.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					contentTemplate.getDateModified()));

			sb.append("\"");
		}

		if (contentTemplate.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(contentTemplate.getDescription()));

			sb.append("\"");
		}

		if (contentTemplate.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(contentTemplate.getDescription_i18n()));
		}

		if (contentTemplate.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(contentTemplate.getId()));

			sb.append("\"");
		}

		if (contentTemplate.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(contentTemplate.getName()));

			sb.append("\"");
		}

		if (contentTemplate.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(contentTemplate.getName_i18n()));
		}

		if (contentTemplate.getProgrammingLanguage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"programmingLanguage\": ");

			sb.append("\"");

			sb.append(_escape(contentTemplate.getProgrammingLanguage()));

			sb.append("\"");
		}

		if (contentTemplate.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(contentTemplate.getSiteId());
		}

		if (contentTemplate.getTemplateScript() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"templateScript\": ");

			sb.append("\"");

			sb.append(_escape(contentTemplate.getTemplateScript()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContentTemplateJSONParser contentTemplateJSONParser =
			new ContentTemplateJSONParser();

		return contentTemplateJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ContentTemplate contentTemplate) {
		if (contentTemplate == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (contentTemplate.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(contentTemplate.getActions()));
		}

		if (contentTemplate.getAssetLibraryKey() == null) {
			map.put("assetLibraryKey", null);
		}
		else {
			map.put(
				"assetLibraryKey",
				String.valueOf(contentTemplate.getAssetLibraryKey()));
		}

		if (contentTemplate.getAvailableLanguages() == null) {
			map.put("availableLanguages", null);
		}
		else {
			map.put(
				"availableLanguages",
				String.valueOf(contentTemplate.getAvailableLanguages()));
		}

		if (contentTemplate.getContentStructureId() == null) {
			map.put("contentStructureId", null);
		}
		else {
			map.put(
				"contentStructureId",
				String.valueOf(contentTemplate.getContentStructureId()));
		}

		if (contentTemplate.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(contentTemplate.getCreator()));
		}

		if (contentTemplate.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					contentTemplate.getDateCreated()));
		}

		if (contentTemplate.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					contentTemplate.getDateModified()));
		}

		if (contentTemplate.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(contentTemplate.getDescription()));
		}

		if (contentTemplate.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(contentTemplate.getDescription_i18n()));
		}

		if (contentTemplate.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(contentTemplate.getId()));
		}

		if (contentTemplate.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(contentTemplate.getName()));
		}

		if (contentTemplate.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put(
				"name_i18n", String.valueOf(contentTemplate.getName_i18n()));
		}

		if (contentTemplate.getProgrammingLanguage() == null) {
			map.put("programmingLanguage", null);
		}
		else {
			map.put(
				"programmingLanguage",
				String.valueOf(contentTemplate.getProgrammingLanguage()));
		}

		if (contentTemplate.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(contentTemplate.getSiteId()));
		}

		if (contentTemplate.getTemplateScript() == null) {
			map.put("templateScript", null);
		}
		else {
			map.put(
				"templateScript",
				String.valueOf(contentTemplate.getTemplateScript()));
		}

		return map;
	}

	public static class ContentTemplateJSONParser
		extends BaseJSONParser<ContentTemplate> {

		@Override
		protected ContentTemplate createDTO() {
			return new ContentTemplate();
		}

		@Override
		protected ContentTemplate[] createDTOArray(int size) {
			return new ContentTemplate[size];
		}

		@Override
		protected void setField(
			ContentTemplate contentTemplate, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setActions(
						(Map)ContentTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "assetLibraryKey")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setAssetLibraryKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "availableLanguages")) {

				if (jsonParserFieldValue != null) {
					contentTemplate.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "contentStructureId")) {

				if (jsonParserFieldValue != null) {
					contentTemplate.setContentStructureId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setDescription_i18n(
						(Map)ContentTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setName_i18n(
						(Map)ContentTemplateSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "programmingLanguage")) {

				if (jsonParserFieldValue != null) {
					contentTemplate.setProgrammingLanguage(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "templateScript")) {
				if (jsonParserFieldValue != null) {
					contentTemplate.setTemplateScript(
						(String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
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
			sb.append("\":");

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
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}