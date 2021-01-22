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

import com.liferay.headless.admin.content.client.dto.v1_0.DisplayPageTemplateSettings;
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
public class DisplayPageTemplateSettingsSerDes {

	public static DisplayPageTemplateSettings toDTO(String json) {
		DisplayPageTemplateSettingsJSONParser
			displayPageTemplateSettingsJSONParser =
				new DisplayPageTemplateSettingsJSONParser();

		return displayPageTemplateSettingsJSONParser.parseToDTO(json);
	}

	public static DisplayPageTemplateSettings[] toDTOs(String json) {
		DisplayPageTemplateSettingsJSONParser
			displayPageTemplateSettingsJSONParser =
				new DisplayPageTemplateSettingsJSONParser();

		return displayPageTemplateSettingsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		DisplayPageTemplateSettings displayPageTemplateSettings) {

		if (displayPageTemplateSettings == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (displayPageTemplateSettings.getContentAssociation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentAssociation\": ");

			sb.append(
				String.valueOf(
					displayPageTemplateSettings.getContentAssociation()));
		}

		if (displayPageTemplateSettings.getOpenGraphSettingsMapping() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"openGraphSettingsMapping\": ");

			sb.append(
				String.valueOf(
					displayPageTemplateSettings.getOpenGraphSettingsMapping()));
		}

		if (displayPageTemplateSettings.getSeoSettingsMapping() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"seoSettingsMapping\": ");

			sb.append(
				String.valueOf(
					displayPageTemplateSettings.getSeoSettingsMapping()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DisplayPageTemplateSettingsJSONParser
			displayPageTemplateSettingsJSONParser =
				new DisplayPageTemplateSettingsJSONParser();

		return displayPageTemplateSettingsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DisplayPageTemplateSettings displayPageTemplateSettings) {

		if (displayPageTemplateSettings == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (displayPageTemplateSettings.getContentAssociation() == null) {
			map.put("contentAssociation", null);
		}
		else {
			map.put(
				"contentAssociation",
				String.valueOf(
					displayPageTemplateSettings.getContentAssociation()));
		}

		if (displayPageTemplateSettings.getOpenGraphSettingsMapping() == null) {
			map.put("openGraphSettingsMapping", null);
		}
		else {
			map.put(
				"openGraphSettingsMapping",
				String.valueOf(
					displayPageTemplateSettings.getOpenGraphSettingsMapping()));
		}

		if (displayPageTemplateSettings.getSeoSettingsMapping() == null) {
			map.put("seoSettingsMapping", null);
		}
		else {
			map.put(
				"seoSettingsMapping",
				String.valueOf(
					displayPageTemplateSettings.getSeoSettingsMapping()));
		}

		return map;
	}

	public static class DisplayPageTemplateSettingsJSONParser
		extends BaseJSONParser<DisplayPageTemplateSettings> {

		@Override
		protected DisplayPageTemplateSettings createDTO() {
			return new DisplayPageTemplateSettings();
		}

		@Override
		protected DisplayPageTemplateSettings[] createDTOArray(int size) {
			return new DisplayPageTemplateSettings[size];
		}

		@Override
		protected void setField(
			DisplayPageTemplateSettings displayPageTemplateSettings,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentAssociation")) {
				if (jsonParserFieldValue != null) {
					displayPageTemplateSettings.setContentAssociation(
						ContentAssociationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "openGraphSettingsMapping")) {

				if (jsonParserFieldValue != null) {
					displayPageTemplateSettings.setOpenGraphSettingsMapping(
						OpenGraphSettingsMappingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "seoSettingsMapping")) {

				if (jsonParserFieldValue != null) {
					displayPageTemplateSettings.setSeoSettingsMapping(
						SEOSettingsMappingSerDes.toDTO(
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