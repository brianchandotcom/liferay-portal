/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.LandscapeMobile;
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
public class LandscapeMobileSerDes {

	public static LandscapeMobile toDTO(String json) {
		LandscapeMobileJSONParser landscapeMobileJSONParser =
			new LandscapeMobileJSONParser();

		return landscapeMobileJSONParser.parseToDTO(json);
	}

	public static LandscapeMobile[] toDTOs(String json) {
		LandscapeMobileJSONParser landscapeMobileJSONParser =
			new LandscapeMobileJSONParser();

		return landscapeMobileJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LandscapeMobile landscapeMobile) {
		if (landscapeMobile == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (landscapeMobile.getModulesPerRow() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modulesPerRow\": ");

			sb.append(landscapeMobile.getModulesPerRow());
		}

		if (landscapeMobile.getReverseOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reverseOrder\": ");

			sb.append(landscapeMobile.getReverseOrder());
		}

		if (landscapeMobile.getVerticalAlignment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"verticalAlignment\": ");

			sb.append("\"");

			sb.append(_escape(landscapeMobile.getVerticalAlignment()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LandscapeMobileJSONParser landscapeMobileJSONParser =
			new LandscapeMobileJSONParser();

		return landscapeMobileJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(LandscapeMobile landscapeMobile) {
		if (landscapeMobile == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (landscapeMobile.getModulesPerRow() == null) {
			map.put("modulesPerRow", null);
		}
		else {
			map.put(
				"modulesPerRow",
				String.valueOf(landscapeMobile.getModulesPerRow()));
		}

		if (landscapeMobile.getReverseOrder() == null) {
			map.put("reverseOrder", null);
		}
		else {
			map.put(
				"reverseOrder",
				String.valueOf(landscapeMobile.getReverseOrder()));
		}

		if (landscapeMobile.getVerticalAlignment() == null) {
			map.put("verticalAlignment", null);
		}
		else {
			map.put(
				"verticalAlignment",
				String.valueOf(landscapeMobile.getVerticalAlignment()));
		}

		return map;
	}

	public static class LandscapeMobileJSONParser
		extends BaseJSONParser<LandscapeMobile> {

		@Override
		protected LandscapeMobile createDTO() {
			return new LandscapeMobile();
		}

		@Override
		protected LandscapeMobile[] createDTOArray(int size) {
			return new LandscapeMobile[size];
		}

		@Override
		protected void setField(
			LandscapeMobile landscapeMobile, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "modulesPerRow")) {
				if (jsonParserFieldValue != null) {
					landscapeMobile.setModulesPerRow(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "reverseOrder")) {
				if (jsonParserFieldValue != null) {
					landscapeMobile.setReverseOrder(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "verticalAlignment")) {
				if (jsonParserFieldValue != null) {
					landscapeMobile.setVerticalAlignment(
						(String)jsonParserFieldValue);
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