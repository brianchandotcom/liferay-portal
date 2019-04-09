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

import java.util.Collection;
import java.util.Date;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"availableLanguages\": ");

		if (form.getAvailableLanguages() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < form.getAvailableLanguages().length; i++) {
				sb.append("\"");
				sb.append(form.getAvailableLanguages()[i]);
				sb.append("\"");

				if ((i + 1) < form.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(form.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(form.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(form.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"datePublished\": ");

		sb.append("\"");
		sb.append(form.getDatePublished());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"defaultLanguage\": ");

		sb.append("\"");
		sb.append(form.getDefaultLanguage());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(form.getDescription());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"formRecords\": ");

		if (form.getFormRecords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < form.getFormRecords().length; i++) {
				sb.append(form.getFormRecords()[i]);

				if ((i + 1) < form.getFormRecords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"formRecordsIds\": ");

		if (form.getFormRecordsIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < form.getFormRecordsIds().length; i++) {
				sb.append(form.getFormRecordsIds()[i]);

				if ((i + 1) < form.getFormRecordsIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(form.getId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(form.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"siteId\": ");

		sb.append(form.getSiteId());
		sb.append(", ");

		sb.append("\"structure\": ");

		sb.append(form.getStructure());
		sb.append(", ");

		sb.append("\"structureId\": ");

		sb.append(form.getStructureId());

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Form> forms) {
		if (forms == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Form form : forms) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(form));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class FormJSONParser extends BaseJSONParser<Form> {

		protected Form createDTO() {
			return new Form();
		}

		protected Form[] createDTOArray(int size) {
			return new Form[size];
		}

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
					form.setDateCreated((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					form.setDateModified((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "datePublished")) {
				if (jsonParserFieldValue != null) {
					form.setDatePublished((Date)jsonParserFieldValue);
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
					form.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					form.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					form.setSiteId((Long)jsonParserFieldValue);
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
					form.setStructureId((Long)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}