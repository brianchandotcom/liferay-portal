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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.Language;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class LanguageSerDes {

	public static Language toDTO(String json) {
		LanguageJSONParser languageJSONParser = new LanguageJSONParser();

		return languageJSONParser.parseToDTO(json);
	}

	public static Language[] toDTOs(String json) {
		LanguageJSONParser languageJSONParser = new LanguageJSONParser();

		return languageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Language language) {
		if (language == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (language.getCountry() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"country\": ");

			sb.append("\"");

			sb.append(_escape(language.getCountry()));

			sb.append("\"");
		}

		if (language.getCountry_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"country_i18n\": ");

			sb.append(_toJSON(language.getCountry_i18n()));
		}

		if (language.getIsDefault() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"isDefault\": ");

			sb.append(language.getIsDefault());
		}

		if (language.getLanguage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"language\": ");

			sb.append("\"");

			sb.append(_escape(language.getLanguage()));

			sb.append("\"");
		}

		if (language.getLanguageId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"languageId\": ");

			sb.append("\"");

			sb.append(_escape(language.getLanguageId()));

			sb.append("\"");
		}

		if (language.getLanguage_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"language_i18n\": ");

			sb.append(_toJSON(language.getLanguage_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LanguageJSONParser languageJSONParser = new LanguageJSONParser();

		return languageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Language language) {
		if (language == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (language.getCountry() == null) {
			map.put("country", null);
		}
		else {
			map.put("country", String.valueOf(language.getCountry()));
		}

		if (language.getCountry_i18n() == null) {
			map.put("country_i18n", null);
		}
		else {
			map.put("country_i18n", String.valueOf(language.getCountry_i18n()));
		}

		if (language.getIsDefault() == null) {
			map.put("isDefault", null);
		}
		else {
			map.put("isDefault", String.valueOf(language.getIsDefault()));
		}

		if (language.getLanguage() == null) {
			map.put("language", null);
		}
		else {
			map.put("language", String.valueOf(language.getLanguage()));
		}

		if (language.getLanguageId() == null) {
			map.put("languageId", null);
		}
		else {
			map.put("languageId", String.valueOf(language.getLanguageId()));
		}

		if (language.getLanguage_i18n() == null) {
			map.put("language_i18n", null);
		}
		else {
			map.put(
				"language_i18n", String.valueOf(language.getLanguage_i18n()));
		}

		return map;
	}

	public static class LanguageJSONParser extends BaseJSONParser<Language> {

		@Override
		protected Language createDTO() {
			return new Language();
		}

		@Override
		protected Language[] createDTOArray(int size) {
			return new Language[size];
		}

		@Override
		protected void setField(
			Language language, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "country")) {
				if (jsonParserFieldValue != null) {
					language.setCountry((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "country_i18n")) {
				if (jsonParserFieldValue != null) {
					language.setCountry_i18n(
						(Map)LanguageSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "isDefault")) {
				if (jsonParserFieldValue != null) {
					language.setIsDefault((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "language")) {
				if (jsonParserFieldValue != null) {
					language.setLanguage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "languageId")) {
				if (jsonParserFieldValue != null) {
					language.setLanguageId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "language_i18n")) {
				if (jsonParserFieldValue != null) {
					language.setLanguage_i18n(
						(Map)LanguageSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}