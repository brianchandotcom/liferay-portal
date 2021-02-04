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

package com.liferay.headless.admin.content.client.serdes.v1_0;

import com.liferay.headless.admin.content.client.dto.v1_0.SEOSettingsMapping;
import com.liferay.headless.admin.content.client.json.BaseJSONParser;

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
public class SEOSettingsMappingSerDes {

	public static SEOSettingsMapping toDTO(String json) {
		SEOSettingsMappingJSONParser seoSettingsMappingJSONParser =
			new SEOSettingsMappingJSONParser();

		return seoSettingsMappingJSONParser.parseToDTO(json);
	}

	public static SEOSettingsMapping[] toDTOs(String json) {
		SEOSettingsMappingJSONParser seoSettingsMappingJSONParser =
			new SEOSettingsMappingJSONParser();

		return seoSettingsMappingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SEOSettingsMapping seoSettingsMapping) {
		if (seoSettingsMapping == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (seoSettingsMapping.getDescriptionMappingFieldKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"descriptionMappingFieldKey\": ");

			sb.append("\"");

			sb.append(
				_escape(seoSettingsMapping.getDescriptionMappingFieldKey()));

			sb.append("\"");
		}

		if (seoSettingsMapping.getHtmlTitleMappingFieldKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"htmlTitleMappingFieldKey\": ");

			sb.append("\"");

			sb.append(
				_escape(seoSettingsMapping.getHtmlTitleMappingFieldKey()));

			sb.append("\"");
		}

		if (seoSettingsMapping.getRobots() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"robots\": ");

			sb.append("\"");

			sb.append(_escape(seoSettingsMapping.getRobots()));

			sb.append("\"");
		}

		if (seoSettingsMapping.getRobots_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"robots_i18n\": ");

			sb.append(_toJSON(seoSettingsMapping.getRobots_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SEOSettingsMappingJSONParser seoSettingsMappingJSONParser =
			new SEOSettingsMappingJSONParser();

		return seoSettingsMappingJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SEOSettingsMapping seoSettingsMapping) {

		if (seoSettingsMapping == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (seoSettingsMapping.getDescriptionMappingFieldKey() == null) {
			map.put("descriptionMappingFieldKey", null);
		}
		else {
			map.put(
				"descriptionMappingFieldKey",
				String.valueOf(
					seoSettingsMapping.getDescriptionMappingFieldKey()));
		}

		if (seoSettingsMapping.getHtmlTitleMappingFieldKey() == null) {
			map.put("htmlTitleMappingFieldKey", null);
		}
		else {
			map.put(
				"htmlTitleMappingFieldKey",
				String.valueOf(
					seoSettingsMapping.getHtmlTitleMappingFieldKey()));
		}

		if (seoSettingsMapping.getRobots() == null) {
			map.put("robots", null);
		}
		else {
			map.put("robots", String.valueOf(seoSettingsMapping.getRobots()));
		}

		if (seoSettingsMapping.getRobots_i18n() == null) {
			map.put("robots_i18n", null);
		}
		else {
			map.put(
				"robots_i18n",
				String.valueOf(seoSettingsMapping.getRobots_i18n()));
		}

		return map;
	}

	public static class SEOSettingsMappingJSONParser
		extends BaseJSONParser<SEOSettingsMapping> {

		@Override
		protected SEOSettingsMapping createDTO() {
			return new SEOSettingsMapping();
		}

		@Override
		protected SEOSettingsMapping[] createDTOArray(int size) {
			return new SEOSettingsMapping[size];
		}

		@Override
		protected void setField(
			SEOSettingsMapping seoSettingsMapping, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "descriptionMappingFieldKey")) {

				if (jsonParserFieldValue != null) {
					seoSettingsMapping.setDescriptionMappingFieldKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "htmlTitleMappingFieldKey")) {

				if (jsonParserFieldValue != null) {
					seoSettingsMapping.setHtmlTitleMappingFieldKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "robots")) {
				if (jsonParserFieldValue != null) {
					seoSettingsMapping.setRobots((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "robots_i18n")) {
				if (jsonParserFieldValue != null) {
					seoSettingsMapping.setRobots_i18n(
						(Map)SEOSettingsMappingSerDes.toMap(
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