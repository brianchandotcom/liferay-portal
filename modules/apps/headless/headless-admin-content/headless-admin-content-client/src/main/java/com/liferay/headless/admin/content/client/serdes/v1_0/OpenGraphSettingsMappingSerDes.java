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

import com.liferay.headless.admin.content.client.dto.v1_0.OpenGraphSettingsMapping;
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
public class OpenGraphSettingsMappingSerDes {

	public static OpenGraphSettingsMapping toDTO(String json) {
		OpenGraphSettingsMappingJSONParser openGraphSettingsMappingJSONParser =
			new OpenGraphSettingsMappingJSONParser();

		return openGraphSettingsMappingJSONParser.parseToDTO(json);
	}

	public static OpenGraphSettingsMapping[] toDTOs(String json) {
		OpenGraphSettingsMappingJSONParser openGraphSettingsMappingJSONParser =
			new OpenGraphSettingsMappingJSONParser();

		return openGraphSettingsMappingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		OpenGraphSettingsMapping openGraphSettingsMapping) {

		if (openGraphSettingsMapping == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (openGraphSettingsMapping.getDescriptionMappingField() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"descriptionMappingField\": ");

			sb.append("\"");

			sb.append(
				_escape(openGraphSettingsMapping.getDescriptionMappingField()));

			sb.append("\"");
		}

		if (openGraphSettingsMapping.getImageAltMappingField() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"imageAltMappingField\": ");

			sb.append("\"");

			sb.append(
				_escape(openGraphSettingsMapping.getImageAltMappingField()));

			sb.append("\"");
		}

		if (openGraphSettingsMapping.getImageMappingField() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"imageMappingField\": ");

			sb.append("\"");

			sb.append(_escape(openGraphSettingsMapping.getImageMappingField()));

			sb.append("\"");
		}

		if (openGraphSettingsMapping.getTitleMappingField() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"titleMappingField\": ");

			sb.append("\"");

			sb.append(_escape(openGraphSettingsMapping.getTitleMappingField()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OpenGraphSettingsMappingJSONParser openGraphSettingsMappingJSONParser =
			new OpenGraphSettingsMappingJSONParser();

		return openGraphSettingsMappingJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		OpenGraphSettingsMapping openGraphSettingsMapping) {

		if (openGraphSettingsMapping == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (openGraphSettingsMapping.getDescriptionMappingField() == null) {
			map.put("descriptionMappingField", null);
		}
		else {
			map.put(
				"descriptionMappingField",
				String.valueOf(
					openGraphSettingsMapping.getDescriptionMappingField()));
		}

		if (openGraphSettingsMapping.getImageAltMappingField() == null) {
			map.put("imageAltMappingField", null);
		}
		else {
			map.put(
				"imageAltMappingField",
				String.valueOf(
					openGraphSettingsMapping.getImageAltMappingField()));
		}

		if (openGraphSettingsMapping.getImageMappingField() == null) {
			map.put("imageMappingField", null);
		}
		else {
			map.put(
				"imageMappingField",
				String.valueOf(
					openGraphSettingsMapping.getImageMappingField()));
		}

		if (openGraphSettingsMapping.getTitleMappingField() == null) {
			map.put("titleMappingField", null);
		}
		else {
			map.put(
				"titleMappingField",
				String.valueOf(
					openGraphSettingsMapping.getTitleMappingField()));
		}

		return map;
	}

	public static class OpenGraphSettingsMappingJSONParser
		extends BaseJSONParser<OpenGraphSettingsMapping> {

		@Override
		protected OpenGraphSettingsMapping createDTO() {
			return new OpenGraphSettingsMapping();
		}

		@Override
		protected OpenGraphSettingsMapping[] createDTOArray(int size) {
			return new OpenGraphSettingsMapping[size];
		}

		@Override
		protected void setField(
			OpenGraphSettingsMapping openGraphSettingsMapping,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "descriptionMappingField")) {

				if (jsonParserFieldValue != null) {
					openGraphSettingsMapping.setDescriptionMappingField(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "imageAltMappingField")) {

				if (jsonParserFieldValue != null) {
					openGraphSettingsMapping.setImageAltMappingField(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "imageMappingField")) {
				if (jsonParserFieldValue != null) {
					openGraphSettingsMapping.setImageMappingField(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "titleMappingField")) {
				if (jsonParserFieldValue != null) {
					openGraphSettingsMapping.setTitleMappingField(
						(String)jsonParserFieldValue);
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