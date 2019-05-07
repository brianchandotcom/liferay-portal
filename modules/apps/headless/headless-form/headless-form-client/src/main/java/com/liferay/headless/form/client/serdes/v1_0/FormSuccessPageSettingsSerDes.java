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

import com.liferay.headless.form.client.dto.v1_0.FormSuccessPageSettings;
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
public class FormSuccessPageSettingsSerDes {

	public static FormSuccessPageSettings toDTO(String json) {
		FormSuccessPageSettingsJSONParser formSuccessPageSettingsJSONParser =
			new FormSuccessPageSettingsJSONParser();

		return formSuccessPageSettingsJSONParser.parseToDTO(json);
	}

	public static FormSuccessPageSettings[] toDTOs(String json) {
		FormSuccessPageSettingsJSONParser formSuccessPageSettingsJSONParser =
			new FormSuccessPageSettingsJSONParser();

		return formSuccessPageSettingsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		FormSuccessPageSettings formSuccessPageSettings) {

		if (formSuccessPageSettings == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formSuccessPageSettings.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(formSuccessPageSettings.getDescription()));

			sb.append("\"");
		}

		if (formSuccessPageSettings.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline\": ");

			sb.append("\"");

			sb.append(_escape(formSuccessPageSettings.getHeadline()));

			sb.append("\"");
		}

		if (formSuccessPageSettings.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(formSuccessPageSettings.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormSuccessPageSettingsJSONParser formSuccessPageSettingsJSONParser =
			new FormSuccessPageSettingsJSONParser();

		return formSuccessPageSettingsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FormSuccessPageSettings formSuccessPageSettings) {

		if (formSuccessPageSettings == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (formSuccessPageSettings.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(formSuccessPageSettings.getDescription()));
		}

		if (formSuccessPageSettings.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put(
				"headline",
				String.valueOf(formSuccessPageSettings.getHeadline()));
		}

		if (formSuccessPageSettings.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formSuccessPageSettings.getId()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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

	private static class FormSuccessPageSettingsJSONParser
		extends BaseJSONParser<FormSuccessPageSettings> {

		@Override
		protected FormSuccessPageSettings createDTO() {
			return new FormSuccessPageSettings();
		}

		@Override
		protected FormSuccessPageSettings[] createDTOArray(int size) {
			return new FormSuccessPageSettings[size];
		}

		@Override
		protected void setField(
			FormSuccessPageSettings formSuccessPageSettings,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					formSuccessPageSettings.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					formSuccessPageSettings.setHeadline(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formSuccessPageSettings.setId(
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