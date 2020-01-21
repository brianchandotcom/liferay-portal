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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.FormPage;
import com.liferay.headless.form.client.dto.v1_0.FormStructure;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormStructureSerDes {

	public static FormStructure toDTO(String json) {
		FormStructureJSONParser formStructureJSONParser =
			new FormStructureJSONParser();

		return formStructureJSONParser.parseToDTO(json);
	}

	public static FormStructure[] toDTOs(String json) {
		FormStructureJSONParser formStructureJSONParser =
			new FormStructureJSONParser();

		return formStructureJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormStructure formStructure) {
		if (formStructure == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (formStructure.getAvailableLanguages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\": ");

			sb.append("[");

			for (int i = 0; i < formStructure.getAvailableLanguages().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(formStructure.getAvailableLanguages()[i]));

				sb.append("\"");

				if ((i + 1) < formStructure.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formStructure.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(formStructure.getCreator()));
		}

		if (formStructure.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(formStructure.getDateCreated()));

			sb.append("\"");
		}

		if (formStructure.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					formStructure.getDateModified()));

			sb.append("\"");
		}

		if (formStructure.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(formStructure.getDescription()));

			sb.append("\"");
		}

		if (formStructure.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(formStructure.getDescription_i18n()));
		}

		if (formStructure.getFormPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formPages\": ");

			sb.append("[");

			for (int i = 0; i < formStructure.getFormPages().length; i++) {
				sb.append(String.valueOf(formStructure.getFormPages()[i]));

				if ((i + 1) < formStructure.getFormPages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formStructure.getFormSuccessPage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formSuccessPage\": ");

			sb.append(String.valueOf(formStructure.getFormSuccessPage()));
		}

		if (formStructure.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(formStructure.getId());
		}

		if (formStructure.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(formStructure.getName()));

			sb.append("\"");
		}

		if (formStructure.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(formStructure.getName_i18n()));
		}

		if (formStructure.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(formStructure.getSiteId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormStructureJSONParser formStructureJSONParser =
			new FormStructureJSONParser();

		return formStructureJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormStructure formStructure) {
		if (formStructure == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (formStructure.getAvailableLanguages() == null) {
			map.put("availableLanguages", null);
		}
		else {
			map.put(
				"availableLanguages",
				String.valueOf(formStructure.getAvailableLanguages()));
		}

		if (formStructure.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(formStructure.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(formStructure.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(formStructure.getDateModified()));

		if (formStructure.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(formStructure.getDescription()));
		}

		if (formStructure.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(formStructure.getDescription_i18n()));
		}

		if (formStructure.getFormPages() == null) {
			map.put("formPages", null);
		}
		else {
			map.put("formPages", String.valueOf(formStructure.getFormPages()));
		}

		if (formStructure.getFormSuccessPage() == null) {
			map.put("formSuccessPage", null);
		}
		else {
			map.put(
				"formSuccessPage",
				String.valueOf(formStructure.getFormSuccessPage()));
		}

		if (formStructure.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formStructure.getId()));
		}

		if (formStructure.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(formStructure.getName()));
		}

		if (formStructure.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put("name_i18n", String.valueOf(formStructure.getName_i18n()));
		}

		if (formStructure.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(formStructure.getSiteId()));
		}

		return map;
	}

	public static class FormStructureJSONParser
		extends BaseJSONParser<FormStructure> {

		@Override
		protected FormStructure createDTO() {
			return new FormStructure();
		}

		@Override
		protected FormStructure[] createDTOArray(int size) {
			return new FormStructure[size];
		}

		@Override
		protected void setField(
			FormStructure formStructure, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "availableLanguages")) {
				if (jsonParserFieldValue != null) {
					formStructure.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					formStructure.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					formStructure.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					formStructure.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					formStructure.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					formStructure.setDescription_i18n(
						(Map)FormStructureSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formPages")) {
				if (jsonParserFieldValue != null) {
					formStructure.setFormPages(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FormPageSerDes.toDTO((String)object)
						).toArray(
							size -> new FormPage[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formSuccessPage")) {
				if (jsonParserFieldValue != null) {
					formStructure.setFormSuccessPage(
						FormSuccessPageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formStructure.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					formStructure.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					formStructure.setName_i18n(
						(Map)FormStructureSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					formStructure.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}