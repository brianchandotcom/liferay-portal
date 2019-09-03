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

import com.liferay.headless.form.client.dto.v1_0.FormFieldValue;
import com.liferay.headless.form.client.dto.v1_0.FormRecord;
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
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(formRecord.getCreator()));
		}

		if (formRecord.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(formRecord.getDateCreated()));

			sb.append("\"");
		}

		if (formRecord.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(formRecord.getDateModified()));

			sb.append("\"");
		}

		if (formRecord.getDatePublished() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"datePublished\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(formRecord.getDatePublished()));

			sb.append("\"");
		}

		if (formRecord.getDraft() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"draft\": ");

			sb.append(formRecord.getDraft());
		}

		if (formRecord.getFormFieldValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formFieldValues\": ");

			sb.append("[");

			for (int i = 0; i < formRecord.getFormFieldValues().length; i++) {
				sb.append(String.valueOf(formRecord.getFormFieldValues()[i]));

				if ((i + 1) < formRecord.getFormFieldValues().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formRecord.getFormId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formId\": ");

			sb.append(formRecord.getFormId());
		}

		if (formRecord.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(formRecord.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormRecordJSONParser formRecordJSONParser = new FormRecordJSONParser();

		return formRecordJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormRecord formRecord) {
		if (formRecord == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (formRecord.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(formRecord.getCreator()));
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

		if (formRecord.getFormFieldValues() == null) {
			map.put("formFieldValues", null);
		}
		else {
			map.put(
				"formFieldValues",
				String.valueOf(formRecord.getFormFieldValues()));
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

	public static class FormRecordJSONParser
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
			else if (Objects.equals(jsonParserFieldName, "formFieldValues")) {
				if (jsonParserFieldValue != null) {
					formRecord.setFormFieldValues(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FormFieldValueSerDes.toDTO((String)object)
						).toArray(
							size -> new FormFieldValue[size]
						));
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

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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