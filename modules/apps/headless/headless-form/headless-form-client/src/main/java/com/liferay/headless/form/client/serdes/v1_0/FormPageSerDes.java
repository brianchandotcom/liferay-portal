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

import com.liferay.headless.form.client.dto.v1_0.FormField;
import com.liferay.headless.form.client.dto.v1_0.FormPage;
import com.liferay.headless.form.client.json.BaseJSONParser;

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
public class FormPageSerDes {

	public static FormPage toDTO(String json) {
		FormPageJSONParser formPageJSONParser = new FormPageJSONParser();

		return formPageJSONParser.parseToDTO(json);
	}

	public static FormPage[] toDTOs(String json) {
		FormPageJSONParser formPageJSONParser = new FormPageJSONParser();

		return formPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormPage formPage) {
		if (formPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formPage.getFormFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formFields\": ");

			sb.append("[");

			for (int i = 0; i < formPage.getFormFields().length; i++) {
				sb.append(String.valueOf(formPage.getFormFields()[i]));

				if ((i + 1) < formPage.getFormFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formPage.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline\": ");

			sb.append("\"");

			sb.append(_escape(formPage.getHeadline()));

			sb.append("\"");
		}

		if (formPage.getHeadline_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline_i18n\": ");

			sb.append(_toJSON(formPage.getHeadline_i18n()));
		}

		if (formPage.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(formPage.getId());
		}

		if (formPage.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(formPage.getText()));

			sb.append("\"");
		}

		if (formPage.getText_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text_i18n\": ");

			sb.append(_toJSON(formPage.getText_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormPageJSONParser formPageJSONParser = new FormPageJSONParser();

		return formPageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormPage formPage) {
		if (formPage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (formPage.getFormFields() == null) {
			map.put("formFields", null);
		}
		else {
			map.put("formFields", String.valueOf(formPage.getFormFields()));
		}

		if (formPage.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put("headline", String.valueOf(formPage.getHeadline()));
		}

		if (formPage.getHeadline_i18n() == null) {
			map.put("headline_i18n", null);
		}
		else {
			map.put(
				"headline_i18n", String.valueOf(formPage.getHeadline_i18n()));
		}

		if (formPage.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formPage.getId()));
		}

		if (formPage.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(formPage.getText()));
		}

		if (formPage.getText_i18n() == null) {
			map.put("text_i18n", null);
		}
		else {
			map.put("text_i18n", String.valueOf(formPage.getText_i18n()));
		}

		return map;
	}

	public static class FormPageJSONParser extends BaseJSONParser<FormPage> {

		@Override
		protected FormPage createDTO() {
			return new FormPage();
		}

		@Override
		protected FormPage[] createDTOArray(int size) {
			return new FormPage[size];
		}

		@Override
		protected void setField(
			FormPage formPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "formFields")) {
				if (jsonParserFieldValue != null) {
					formPage.setFormFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FormFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new FormField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					formPage.setHeadline((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline_i18n")) {
				if (jsonParserFieldValue != null) {
					formPage.setHeadline_i18n(
						(Map)FormPageSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formPage.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					formPage.setText((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text_i18n")) {
				if (jsonParserFieldValue != null) {
					formPage.setText_i18n(
						(Map)FormPageSerDes.toMap(
							(String)jsonParserFieldValue));
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