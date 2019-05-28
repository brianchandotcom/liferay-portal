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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

		if (formSuccessPage.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline\": ");

			sb.append("\"");

			sb.append(_escape(formSuccessPage.getHeadline()));

			sb.append("\"");
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

		Map<String, String> map = new HashMap<>();

		if (formSuccessPage.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(formSuccessPage.getDescription()));
		}

		if (formSuccessPage.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put("headline", String.valueOf(formSuccessPage.getHeadline()));
		}

		if (formSuccessPage.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formSuccessPage.getId()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replaceAll("\"", "\\\\\"");

		return string.replace("\\", "\\\\");
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class FormSuccessPageJSONParser
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
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					formSuccessPage.setHeadline((String)jsonParserFieldValue);
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

}