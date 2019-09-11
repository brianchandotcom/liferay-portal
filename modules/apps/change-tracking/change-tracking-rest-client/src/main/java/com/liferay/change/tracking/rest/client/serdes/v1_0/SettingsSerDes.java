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

package com.liferay.change.tracking.rest.client.serdes.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.Settings;
import com.liferay.change.tracking.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class SettingsSerDes {

	public static Settings toDTO(String json) {
		SettingsJSONParser settingsJSONParser = new SettingsJSONParser();

		return settingsJSONParser.parseToDTO(json);
	}

	public static Settings[] toDTOs(String json) {
		SettingsJSONParser settingsJSONParser = new SettingsJSONParser();

		return settingsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Settings settings) {
		if (settings == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (settings.getChangeTrackingAllowed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"changeTrackingAllowed\": ");

			sb.append(settings.getChangeTrackingAllowed());
		}

		if (settings.getChangeTrackingEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"changeTrackingEnabled\": ");

			sb.append(settings.getChangeTrackingEnabled());
		}

		if (settings.getCheckoutCTCollectionConfirmationEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"checkoutCTCollectionConfirmationEnabled\": ");

			sb.append(settings.getCheckoutCTCollectionConfirmationEnabled());
		}

		if (settings.getCompanyId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"companyId\": ");

			sb.append(settings.getCompanyId());
		}

		if (settings.getSupportedContentTypeLanguageKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"supportedContentTypeLanguageKeys\": ");

			sb.append("[");

			for (int i = 0;
				 i < settings.getSupportedContentTypeLanguageKeys().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(settings.getSupportedContentTypeLanguageKeys()[i]));

				sb.append("\"");

				if ((i + 1) <
						settings.getSupportedContentTypeLanguageKeys().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (settings.getSupportedContentTypes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"supportedContentTypes\": ");

			sb.append("[");

			for (int i = 0; i < settings.getSupportedContentTypes().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(settings.getSupportedContentTypes()[i]));

				sb.append("\"");

				if ((i + 1) < settings.getSupportedContentTypes().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (settings.getUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(settings.getUserId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SettingsJSONParser settingsJSONParser = new SettingsJSONParser();

		return settingsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Settings settings) {
		if (settings == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (settings.getChangeTrackingAllowed() == null) {
			map.put("changeTrackingAllowed", null);
		}
		else {
			map.put(
				"changeTrackingAllowed",
				String.valueOf(settings.getChangeTrackingAllowed()));
		}

		if (settings.getChangeTrackingEnabled() == null) {
			map.put("changeTrackingEnabled", null);
		}
		else {
			map.put(
				"changeTrackingEnabled",
				String.valueOf(settings.getChangeTrackingEnabled()));
		}

		if (settings.getCheckoutCTCollectionConfirmationEnabled() == null) {
			map.put("checkoutCTCollectionConfirmationEnabled", null);
		}
		else {
			map.put(
				"checkoutCTCollectionConfirmationEnabled",
				String.valueOf(
					settings.getCheckoutCTCollectionConfirmationEnabled()));
		}

		if (settings.getCompanyId() == null) {
			map.put("companyId", null);
		}
		else {
			map.put("companyId", String.valueOf(settings.getCompanyId()));
		}

		if (settings.getSupportedContentTypeLanguageKeys() == null) {
			map.put("supportedContentTypeLanguageKeys", null);
		}
		else {
			map.put(
				"supportedContentTypeLanguageKeys",
				String.valueOf(settings.getSupportedContentTypeLanguageKeys()));
		}

		if (settings.getSupportedContentTypes() == null) {
			map.put("supportedContentTypes", null);
		}
		else {
			map.put(
				"supportedContentTypes",
				String.valueOf(settings.getSupportedContentTypes()));
		}

		if (settings.getUserId() == null) {
			map.put("userId", null);
		}
		else {
			map.put("userId", String.valueOf(settings.getUserId()));
		}

		return map;
	}

	public static class SettingsJSONParser extends BaseJSONParser<Settings> {

		@Override
		protected Settings createDTO() {
			return new Settings();
		}

		@Override
		protected Settings[] createDTOArray(int size) {
			return new Settings[size];
		}

		@Override
		protected void setField(
			Settings settings, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "changeTrackingAllowed")) {
				if (jsonParserFieldValue != null) {
					settings.setChangeTrackingAllowed(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "changeTrackingEnabled")) {

				if (jsonParserFieldValue != null) {
					settings.setChangeTrackingEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"checkoutCTCollectionConfirmationEnabled")) {

				if (jsonParserFieldValue != null) {
					settings.setCheckoutCTCollectionConfirmationEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "companyId")) {
				if (jsonParserFieldValue != null) {
					settings.setCompanyId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"supportedContentTypeLanguageKeys")) {

				if (jsonParserFieldValue != null) {
					settings.setSupportedContentTypeLanguageKeys(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "supportedContentTypes")) {

				if (jsonParserFieldValue != null) {
					settings.setSupportedContentTypes(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					settings.setUserId(
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