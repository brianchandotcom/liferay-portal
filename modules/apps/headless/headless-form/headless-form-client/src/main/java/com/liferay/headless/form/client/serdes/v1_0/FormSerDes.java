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

import com.liferay.headless.form.client.dto.v1_0.Form;
import com.liferay.headless.form.client.dto.v1_0.FormRecord;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormSerDes {

	public static Form toDTO(String json) {
		FormJSONParser formJSONParser = new FormJSONParser();

		return formJSONParser.parseToDTO(json);
	}

	public static Form[] toDTOs(String json) {
		FormJSONParser formJSONParser = new FormJSONParser();

		return formJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Form form) {
		if (form == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (form.getAvailableLanguages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\":");

			sb.append("[");

			for (int i = 0; i < form.getAvailableLanguages().length; i++) {
				sb.append("\"");

				sb.append(_escape(form.getAvailableLanguages()[i]));

				sb.append("\"");

				if ((i + 1) < form.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (form.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(form.getCreator()));
		}

		if (form.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(form.getDateCreated()));

			sb.append("\"");
		}

		if (form.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(form.getDateModified()));

			sb.append("\"");
		}

		if (form.getDatePublished() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"datePublished\":");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(form.getDatePublished()));

			sb.append("\"");
		}

		if (form.getDefaultLanguage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultLanguage\":");

			sb.append("\"");

			sb.append(_escape(form.getDefaultLanguage()));

			sb.append("\"");
		}

		if (form.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\":");

			sb.append("\"");

			sb.append(_escape(form.getDescription()));

			sb.append("\"");
		}

		if (form.getFormRecords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formRecords\":");

			sb.append("[");

			for (int i = 0; i < form.getFormRecords().length; i++) {
				sb.append(FormRecordSerDes.toJSON(form.getFormRecords()[i]));

				if ((i + 1) < form.getFormRecords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (form.getFormRecordsIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formRecordsIds\":");

			sb.append("[");

			for (int i = 0; i < form.getFormRecordsIds().length; i++) {
				sb.append(form.getFormRecordsIds()[i]);

				if ((i + 1) < form.getFormRecordsIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (form.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(form.getId());
		}

		if (form.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(_escape(form.getName()));

			sb.append("\"");
		}

		if (form.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\":");

			sb.append(form.getSiteId());
		}

		if (form.getStructure() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"structure\":");

			sb.append(FormStructureSerDes.toJSON(form.getStructure()));
		}

		if (form.getStructureId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"structureId\":");

			sb.append(form.getStructureId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Form form) {
		if (form == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (form.getAvailableLanguages() == null) {
			map.put("availableLanguages", null);
		}
		else {
			map.put(
				"availableLanguages",
				String.valueOf(form.getAvailableLanguages()));
		}

		if (form.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", CreatorSerDes.toJSON(form.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(form.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(form.getDateModified()));

		map.put(
			"datePublished",
			liferayToJSONDateFormat.format(form.getDatePublished()));

		if (form.getDefaultLanguage() == null) {
			map.put("defaultLanguage", null);
		}
		else {
			map.put(
				"defaultLanguage", String.valueOf(form.getDefaultLanguage()));
		}

		if (form.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(form.getDescription()));
		}

		if (form.getFormRecords() == null) {
			map.put("formRecords", null);
		}
		else {
			map.put("formRecords", String.valueOf(form.getFormRecords()));
		}

		if (form.getFormRecordsIds() == null) {
			map.put("formRecordsIds", null);
		}
		else {
			map.put("formRecordsIds", String.valueOf(form.getFormRecordsIds()));
		}

		if (form.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(form.getId()));
		}

		if (form.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(form.getName()));
		}

		if (form.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(form.getSiteId()));
		}

		if (form.getStructure() == null) {
			map.put("structure", null);
		}
		else {
			map.put(
				"structure", FormStructureSerDes.toJSON(form.getStructure()));
		}

		if (form.getStructureId() == null) {
			map.put("structureId", null);
		}
		else {
			map.put("structureId", String.valueOf(form.getStructureId()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class FormJSONParser extends BaseJSONParser<Form> {

		@Override
		protected Form createDTO() {
			return new Form();
		}

		@Override
		protected Form[] createDTOArray(int size) {
			return new Form[size];
		}

		@Override
		protected void setField(
			Form form, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "availableLanguages")) {
				if (jsonParserFieldValue != null) {
					form.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					form.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					form.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					form.setDateModified(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "datePublished")) {
				if (jsonParserFieldValue != null) {
					form.setDatePublished(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultLanguage")) {
				if (jsonParserFieldValue != null) {
					form.setDefaultLanguage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					form.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formRecords")) {
				if (jsonParserFieldValue != null) {
					form.setFormRecords(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FormRecordSerDes.toDTO((String)object)
						).toArray(
							size -> new FormRecord[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formRecordsIds")) {
				if (jsonParserFieldValue != null) {
					form.setFormRecordsIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					form.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					form.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					form.setSiteId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "structure")) {
				if (jsonParserFieldValue != null) {
					form.setStructure(
						FormStructureSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "structureId")) {
				if (jsonParserFieldValue != null) {
					form.setStructureId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}