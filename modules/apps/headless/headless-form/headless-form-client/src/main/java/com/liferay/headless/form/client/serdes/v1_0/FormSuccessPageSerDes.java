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

import com.liferay.headless.form.client.dto.v1_0.FormSuccessPage;
import com.liferay.headless.form.client.json.BaseJSONParser;

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
public class FormSuccessPageSerDes {

	public static FormSuccessPage toDTO(String json) {
		FormSuccessPageJSONParser formSuccessPageJSONParser =
			new FormSuccessPageJSONParser();

		return formSuccessPageJSONParser.parseToDTO(json);
	}

	public static FormSuccessPage[] toDTOs(String json) {
		FormSuccessPageJSONParser formSuccessPageJSONParser =
			new FormSuccessPageJSONParser();

		return formSuccessPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormSuccessPage formSuccessPage) {
		if (formSuccessPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formSuccessPage.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(formSuccessPage.getDescription()));

			sb.append("\"");
		}

		if (formSuccessPage.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(formSuccessPage.getDescription_i18n()));
		}

		if (formSuccessPage.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline\": ");

			sb.append("\"");

			sb.append(_escape(formSuccessPage.getHeadline()));

			sb.append("\"");
		}

		if (formSuccessPage.getHeadline_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline_i18n\": ");

			sb.append(_toJSON(formSuccessPage.getHeadline_i18n()));
		}

		if (formSuccessPage.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(formSuccessPage.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormSuccessPageJSONParser formSuccessPageJSONParser =
			new FormSuccessPageJSONParser();

		return formSuccessPageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormSuccessPage formSuccessPage) {
		if (formSuccessPage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (formSuccessPage.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(formSuccessPage.getDescription()));
		}

		if (formSuccessPage.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(formSuccessPage.getDescription_i18n()));
		}

		if (formSuccessPage.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put("headline", String.valueOf(formSuccessPage.getHeadline()));
		}

		if (formSuccessPage.getHeadline_i18n() == null) {
			map.put("headline_i18n", null);
		}
		else {
			map.put(
				"headline_i18n",
				String.valueOf(formSuccessPage.getHeadline_i18n()));
		}

		if (formSuccessPage.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formSuccessPage.getId()));
		}

		return map;
	}

	public static class FormSuccessPageJSONParser
		extends BaseJSONParser<FormSuccessPage> {

		@Override
		protected FormSuccessPage createDTO() {
			return new FormSuccessPage();
		}

		@Override
		protected FormSuccessPage[] createDTOArray(int size) {
			return new FormSuccessPage[size];
		}

		@Override
		protected void setField(
			FormSuccessPage formSuccessPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					formSuccessPage.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					formSuccessPage.setDescription_i18n(
						(Map)FormSuccessPageSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					formSuccessPage.setHeadline((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline_i18n")) {
				if (jsonParserFieldValue != null) {
					formSuccessPage.setHeadline_i18n(
						(Map)FormSuccessPageSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formSuccessPage.setId(
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