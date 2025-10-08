/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.BaseLayout;
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
public class BaseLayoutSerDes {

	public static BaseLayout toDTO(String json) {
		BaseLayoutJSONParser baseLayoutJSONParser = new BaseLayoutJSONParser();

		return baseLayoutJSONParser.parseToDTO(json);
	}

	public static BaseLayout[] toDTOs(String json) {
		BaseLayoutJSONParser baseLayoutJSONParser = new BaseLayoutJSONParser();

		return baseLayoutJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BaseLayout baseLayout) {
		if (baseLayout == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (baseLayout.getAlign() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"align\": ");

			sb.append("\"");

			sb.append(baseLayout.getAlign());

			sb.append("\"");
		}

		if (baseLayout.getFlexWrap() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"flexWrap\": ");

			sb.append("\"");

			sb.append(baseLayout.getFlexWrap());

			sb.append("\"");
		}

		if (baseLayout.getJustify() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"justify\": ");

			sb.append("\"");

			sb.append(baseLayout.getJustify());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		BaseLayoutJSONParser baseLayoutJSONParser = new BaseLayoutJSONParser();

		return baseLayoutJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(BaseLayout baseLayout) {
		if (baseLayout == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (baseLayout.getAlign() == null) {
			map.put("align", null);
		}
		else {
			map.put("align", String.valueOf(baseLayout.getAlign()));
		}

		if (baseLayout.getFlexWrap() == null) {
			map.put("flexWrap", null);
		}
		else {
			map.put("flexWrap", String.valueOf(baseLayout.getFlexWrap()));
		}

		if (baseLayout.getJustify() == null) {
			map.put("justify", null);
		}
		else {
			map.put("justify", String.valueOf(baseLayout.getJustify()));
		}

		return map;
	}

	public static class BaseLayoutJSONParser
		extends BaseJSONParser<BaseLayout> {

		@Override
		protected BaseLayout createDTO() {
			return new BaseLayout();
		}

		@Override
		protected BaseLayout[] createDTOArray(int size) {
			return new BaseLayout[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "align")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "flexWrap")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "justify")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			BaseLayout baseLayout, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "align")) {
				if (jsonParserFieldValue != null) {
					baseLayout.setAlign(
						BaseLayout.Align.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "flexWrap")) {
				if (jsonParserFieldValue != null) {
					baseLayout.setFlexWrap(
						BaseLayout.FlexWrap.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "justify")) {
				if (jsonParserFieldValue != null) {
					baseLayout.setJustify(
						BaseLayout.Justify.create(
							(String)jsonParserFieldValue));
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