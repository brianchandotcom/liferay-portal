/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.HoursAvailable;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

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
public class HoursAvailableSerDes {

	public static HoursAvailable toDTO(String json) {
		HoursAvailableJSONParser hoursAvailableJSONParser =
			new HoursAvailableJSONParser();

		return hoursAvailableJSONParser.parseToDTO(json);
	}

	public static HoursAvailable[] toDTOs(String json) {
		HoursAvailableJSONParser hoursAvailableJSONParser =
			new HoursAvailableJSONParser();

		return hoursAvailableJSONParser.parseToDTOs(json);
	}

	public static String toJSON(HoursAvailable hoursAvailable) {
		if (hoursAvailable == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (hoursAvailable.getCloses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"closes\": ");

			sb.append("\"");

			sb.append(_escape(hoursAvailable.getCloses()));

			sb.append("\"");
		}

		if (hoursAvailable.getDayOfWeek() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dayOfWeek\": ");

			sb.append("\"");

			sb.append(_escape(hoursAvailable.getDayOfWeek()));

			sb.append("\"");
		}

		if (hoursAvailable.getOpens() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"opens\": ");

			sb.append("\"");

			sb.append(_escape(hoursAvailable.getOpens()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		HoursAvailableJSONParser hoursAvailableJSONParser =
			new HoursAvailableJSONParser();

		return hoursAvailableJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(HoursAvailable hoursAvailable) {
		if (hoursAvailable == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (hoursAvailable.getCloses() == null) {
			map.put("closes", null);
		}
		else {
			map.put("closes", String.valueOf(hoursAvailable.getCloses()));
		}

		if (hoursAvailable.getDayOfWeek() == null) {
			map.put("dayOfWeek", null);
		}
		else {
			map.put("dayOfWeek", String.valueOf(hoursAvailable.getDayOfWeek()));
		}

		if (hoursAvailable.getOpens() == null) {
			map.put("opens", null);
		}
		else {
			map.put("opens", String.valueOf(hoursAvailable.getOpens()));
		}

		return map;
	}

	public static class HoursAvailableJSONParser
		extends BaseJSONParser<HoursAvailable> {

		@Override
		protected HoursAvailable createDTO() {
			return new HoursAvailable();
		}

		@Override
		protected HoursAvailable[] createDTOArray(int size) {
			return new HoursAvailable[size];
		}

		@Override
		protected void setField(
			HoursAvailable hoursAvailable, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "closes")) {
				if (jsonParserFieldValue != null) {
					hoursAvailable.setCloses((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dayOfWeek")) {
				if (jsonParserFieldValue != null) {
					hoursAvailable.setDayOfWeek((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "opens")) {
				if (jsonParserFieldValue != null) {
					hoursAvailable.setOpens((String)jsonParserFieldValue);
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