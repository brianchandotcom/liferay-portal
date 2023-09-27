/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.ColumnViewportConfig;
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
public class ColumnViewportConfigSerDes {

	public static ColumnViewportConfig toDTO(String json) {
		ColumnViewportConfigJSONParser columnViewportConfigJSONParser =
			new ColumnViewportConfigJSONParser();

		return columnViewportConfigJSONParser.parseToDTO(json);
	}

	public static ColumnViewportConfig[] toDTOs(String json) {
		ColumnViewportConfigJSONParser columnViewportConfigJSONParser =
			new ColumnViewportConfigJSONParser();

		return columnViewportConfigJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ColumnViewportConfig columnViewportConfig) {
		if (columnViewportConfig == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (columnViewportConfig.getLandscapeMobile() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"landscapeMobile\": ");

			sb.append(
				String.valueOf(columnViewportConfig.getLandscapeMobile()));
		}

		if (columnViewportConfig.getPortraitMobile() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portraitMobile\": ");

			sb.append(String.valueOf(columnViewportConfig.getPortraitMobile()));
		}

		if (columnViewportConfig.getTablet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tablet\": ");

			sb.append(String.valueOf(columnViewportConfig.getTablet()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ColumnViewportConfigJSONParser columnViewportConfigJSONParser =
			new ColumnViewportConfigJSONParser();

		return columnViewportConfigJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ColumnViewportConfig columnViewportConfig) {

		if (columnViewportConfig == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (columnViewportConfig.getLandscapeMobile() == null) {
			map.put("landscapeMobile", null);
		}
		else {
			map.put(
				"landscapeMobile",
				String.valueOf(columnViewportConfig.getLandscapeMobile()));
		}

		if (columnViewportConfig.getPortraitMobile() == null) {
			map.put("portraitMobile", null);
		}
		else {
			map.put(
				"portraitMobile",
				String.valueOf(columnViewportConfig.getPortraitMobile()));
		}

		if (columnViewportConfig.getTablet() == null) {
			map.put("tablet", null);
		}
		else {
			map.put("tablet", String.valueOf(columnViewportConfig.getTablet()));
		}

		return map;
	}

	public static class ColumnViewportConfigJSONParser
		extends BaseJSONParser<ColumnViewportConfig> {

		@Override
		protected ColumnViewportConfig createDTO() {
			return new ColumnViewportConfig();
		}

		@Override
		protected ColumnViewportConfig[] createDTOArray(int size) {
			return new ColumnViewportConfig[size];
		}

		@Override
		protected void setField(
			ColumnViewportConfig columnViewportConfig,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "landscapeMobile")) {
				if (jsonParserFieldValue != null) {
					columnViewportConfig.setLandscapeMobile(
						LandscapeMobileSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "portraitMobile")) {
				if (jsonParserFieldValue != null) {
					columnViewportConfig.setPortraitMobile(
						PortraitMobileSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tablet")) {
				if (jsonParserFieldValue != null) {
					columnViewportConfig.setTablet(
						TabletSerDes.toDTO((String)jsonParserFieldValue));
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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}