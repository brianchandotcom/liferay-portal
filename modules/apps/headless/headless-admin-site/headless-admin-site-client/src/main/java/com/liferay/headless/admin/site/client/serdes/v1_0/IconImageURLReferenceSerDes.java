/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.IconImageURLReference;
import com.liferay.headless.admin.site.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class IconImageURLReferenceSerDes {

	public static IconImageURLReference toDTO(String json) {
		IconImageURLReferenceJSONParser iconImageURLReferenceJSONParser =
			new IconImageURLReferenceJSONParser();

		return iconImageURLReferenceJSONParser.parseToDTO(json);
	}

	public static IconImageURLReference[] toDTOs(String json) {
		IconImageURLReferenceJSONParser iconImageURLReferenceJSONParser =
			new IconImageURLReferenceJSONParser();

		return iconImageURLReferenceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(IconImageURLReference iconImageURLReference) {
		if (iconImageURLReference == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (iconImageURLReference.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(iconImageURLReference.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (iconImageURLReference.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(iconImageURLReference.getUrl()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		IconImageURLReferenceJSONParser iconImageURLReferenceJSONParser =
			new IconImageURLReferenceJSONParser();

		return iconImageURLReferenceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		IconImageURLReference iconImageURLReference) {

		if (iconImageURLReference == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (iconImageURLReference.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(
					iconImageURLReference.getExternalReferenceCode()));
		}

		if (iconImageURLReference.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(iconImageURLReference.getUrl()));
		}

		return map;
	}

	public static class IconImageURLReferenceJSONParser
		extends BaseJSONParser<IconImageURLReference> {

		@Override
		protected IconImageURLReference createDTO() {
			return new IconImageURLReference();
		}

		@Override
		protected IconImageURLReference[] createDTOArray(int size) {
			return new IconImageURLReference[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "externalReferenceCode")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			IconImageURLReference iconImageURLReference,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "externalReferenceCode")) {
				if (jsonParserFieldValue != null) {
					iconImageURLReference.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					iconImageURLReference.setUrl((String)jsonParserFieldValue);
				}
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
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}