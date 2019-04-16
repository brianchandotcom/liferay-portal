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

import com.liferay.headless.form.client.dto.v1_0.FieldValue;
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
public class FormRecordSerDes {

	public static FormRecord toDTO(String json) {
		FormRecordJSONParser formRecordJSONParser = new FormRecordJSONParser();

		return formRecordJSONParser.parseToDTO(json);
	}

	public static FormRecord[] toDTOs(String json) {
		FormRecordJSONParser formRecordJSONParser = new FormRecordJSONParser();

		return formRecordJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormRecord formRecord) {
		if (formRecord == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (formRecord.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(formRecord.getCreator()));
		}

		if (formRecord.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(formRecord.getDateCreated()));

			sb.append("\"");
		}

		if (formRecord.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(formRecord.getDateModified()));

			sb.append("\"");
		}

		if (formRecord.getDatePublished() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"datePublished\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(formRecord.getDatePublished()));

			sb.append("\"");
		}

		if (formRecord.getDraft() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"draft\":");

			sb.append(formRecord.getDraft());
		}

		if (formRecord.getFieldValues() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"fieldValues\":");

			sb.append("[");

			for (int i = 0; i < formRecord.getFieldValues().length; i++) {
				sb.append(
					FieldValueSerDes.toJSON(formRecord.getFieldValues()[i]));

				if ((i + 1) < formRecord.getFieldValues().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formRecord.getForm() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"form\":");

			sb.append(FormSerDes.toJSON(formRecord.getForm()));
		}

		if (formRecord.getFormId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"formId\":");

			sb.append(formRecord.getFormId());
		}

		if (formRecord.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(formRecord.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(FormRecord formRecord) {
		if (formRecord == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (formRecord.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", CreatorSerDes.toJSON(formRecord.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(formRecord.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(formRecord.getDateModified()));

		map.put(
			"datePublished",
			liferayToJSONDateFormat.format(formRecord.getDatePublished()));

		if (formRecord.getDraft() == null) {
			map.put("draft", null);
		}
		else {
			map.put("draft", String.valueOf(formRecord.getDraft()));
		}

		if (formRecord.getFieldValues() == null) {
			map.put("fieldValues", null);
		}
		else {
			map.put("fieldValues", String.valueOf(formRecord.getFieldValues()));
		}

		if (formRecord.getForm() == null) {
			map.put("form", null);
		}
		else {
			map.put("form", FormSerDes.toJSON(formRecord.getForm()));
		}

		if (formRecord.getFormId() == null) {
			map.put("formId", null);
		}
		else {
			map.put("formId", String.valueOf(formRecord.getFormId()));
		}

		if (formRecord.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formRecord.getId()));
		}

		return map;
	}

	private static class FormRecordJSONParser
		extends BaseJSONParser<FormRecord> {

		@Override
		protected FormRecord createDTO() {
			return new FormRecord();
		}

		@Override
		protected FormRecord[] createDTOArray(int size) {
			return new FormRecord[size];
		}

		@Override
		protected void setField(
			FormRecord formRecord, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					formRecord.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					formRecord.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					formRecord.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "datePublished")) {
				if (jsonParserFieldValue != null) {
					formRecord.setDatePublished(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "draft")) {
				if (jsonParserFieldValue != null) {
					formRecord.setDraft((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldValues")) {
				if (jsonParserFieldValue != null) {
					formRecord.setFieldValues(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FieldValueSerDes.toDTO((String)object)
						).toArray(
							size -> new FieldValue[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "form")) {
				if (jsonParserFieldValue != null) {
					formRecord.setForm(
						FormSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formId")) {
				if (jsonParserFieldValue != null) {
					formRecord.setFormId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formRecord.setId(
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