/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.TimeRange;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class TimeRangeSerDes {

	public static TimeRange toDTO(String json) {
		TimeRangeJSONParser timeRangeJSONParser = new TimeRangeJSONParser();

		return timeRangeJSONParser.parseToDTO(json);
	}

	public static TimeRange[] toDTOs(String json) {
		TimeRangeJSONParser timeRangeJSONParser = new TimeRangeJSONParser();

		return timeRangeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TimeRange timeRange) {
		if (timeRange == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (timeRange.getDateEnd() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateEnd\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(timeRange.getDateEnd()));

			sb.append("\"");
		}

		if (timeRange.getDateStart() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateStart\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(timeRange.getDateStart()));

			sb.append("\"");
		}

		if (timeRange.getDefaultTimeRange() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultTimeRange\": ");

			sb.append(timeRange.getDefaultTimeRange());
		}

		if (timeRange.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(timeRange.getId());
		}

		if (timeRange.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(timeRange.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TimeRangeJSONParser timeRangeJSONParser = new TimeRangeJSONParser();

		return timeRangeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(TimeRange timeRange) {
		if (timeRange == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"dateEnd", liferayToJSONDateFormat.format(timeRange.getDateEnd()));

		map.put(
			"dateStart",
			liferayToJSONDateFormat.format(timeRange.getDateStart()));

		if (timeRange.getDefaultTimeRange() == null) {
			map.put("defaultTimeRange", null);
		}
		else {
			map.put(
				"defaultTimeRange",
				String.valueOf(timeRange.getDefaultTimeRange()));
		}

		if (timeRange.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(timeRange.getId()));
		}

		if (timeRange.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(timeRange.getName()));
		}

		return map;
	}

	public static class TimeRangeJSONParser extends BaseJSONParser<TimeRange> {

		@Override
		protected TimeRange createDTO() {
			return new TimeRange();
		}

		@Override
		protected TimeRange[] createDTOArray(int size) {
			return new TimeRange[size];
		}

		@Override
		protected void setField(
			TimeRange timeRange, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateEnd")) {
				if (jsonParserFieldValue != null) {
					timeRange.setDateEnd(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateStart")) {
				if (jsonParserFieldValue != null) {
					timeRange.setDateStart(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultTimeRange")) {
				if (jsonParserFieldValue != null) {
					timeRange.setDefaultTimeRange(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					timeRange.setId(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					timeRange.setName((String)jsonParserFieldValue);
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
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
				sb.append(entry.getValue());
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