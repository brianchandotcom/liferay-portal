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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"availableLanguages\": ");

		if (formStructure.getAvailableLanguages() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < formStructure.getAvailableLanguages().length;
				 i++) {

				sb.append("\"");
				sb.append(formStructure.getAvailableLanguages()[i]);
				sb.append("\"");

				if ((i + 1) < formStructure.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(formStructure.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(formStructure.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(formStructure.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(formStructure.getDescription());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"formPages\": ");

		if (formStructure.getFormPages() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < formStructure.getFormPages().length; i++) {
				sb.append(formStructure.getFormPages()[i]);

				if ((i + 1) < formStructure.getFormPages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(formStructure.getId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(formStructure.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"siteId\": ");

		sb.append(formStructure.getSiteId());
		sb.append(", ");

		sb.append("\"successPage\": ");

		sb.append(formStructure.getSuccessPage());

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<FormStructure> formStructures) {
		if (formStructures == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (FormStructure formStructure : formStructures) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(formStructure));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class FormStructureJSONParser
		extends BaseJSONParser<FormStructure> {

		protected FormStructure createDTO() {
			return new FormStructure();
		}

		protected FormStructure[] createDTOArray(int size) {
			return new FormStructure[size];
		}

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
					formStructure.setDateCreated((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					formStructure.setDateModified((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					formStructure.setDescription((String)jsonParserFieldValue);
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
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formStructure.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					formStructure.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					formStructure.setSiteId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "successPage")) {
				if (jsonParserFieldValue != null) {
					formStructure.setSuccessPage(
						SuccessPageSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}