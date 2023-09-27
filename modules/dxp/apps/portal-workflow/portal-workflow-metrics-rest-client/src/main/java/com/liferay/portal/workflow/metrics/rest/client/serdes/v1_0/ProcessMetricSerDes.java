/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.ProcessMetric;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class ProcessMetricSerDes {

	public static ProcessMetric toDTO(String json) {
		ProcessMetricJSONParser processMetricJSONParser =
			new ProcessMetricJSONParser();

		return processMetricJSONParser.parseToDTO(json);
	}

	public static ProcessMetric[] toDTOs(String json) {
		ProcessMetricJSONParser processMetricJSONParser =
			new ProcessMetricJSONParser();

		return processMetricJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ProcessMetric processMetric) {
		if (processMetric == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (processMetric.getInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instanceCount\": ");

			sb.append(processMetric.getInstanceCount());
		}

		if (processMetric.getOnTimeInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onTimeInstanceCount\": ");

			sb.append(processMetric.getOnTimeInstanceCount());
		}

		if (processMetric.getOverdueInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"overdueInstanceCount\": ");

			sb.append(processMetric.getOverdueInstanceCount());
		}

		if (processMetric.getProcess() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"process\": ");

			sb.append(String.valueOf(processMetric.getProcess()));
		}

		if (processMetric.getUntrackedInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"untrackedInstanceCount\": ");

			sb.append(processMetric.getUntrackedInstanceCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProcessMetricJSONParser processMetricJSONParser =
			new ProcessMetricJSONParser();

		return processMetricJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ProcessMetric processMetric) {
		if (processMetric == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (processMetric.getInstanceCount() == null) {
			map.put("instanceCount", null);
		}
		else {
			map.put(
				"instanceCount",
				String.valueOf(processMetric.getInstanceCount()));
		}

		if (processMetric.getOnTimeInstanceCount() == null) {
			map.put("onTimeInstanceCount", null);
		}
		else {
			map.put(
				"onTimeInstanceCount",
				String.valueOf(processMetric.getOnTimeInstanceCount()));
		}

		if (processMetric.getOverdueInstanceCount() == null) {
			map.put("overdueInstanceCount", null);
		}
		else {
			map.put(
				"overdueInstanceCount",
				String.valueOf(processMetric.getOverdueInstanceCount()));
		}

		if (processMetric.getProcess() == null) {
			map.put("process", null);
		}
		else {
			map.put("process", String.valueOf(processMetric.getProcess()));
		}

		if (processMetric.getUntrackedInstanceCount() == null) {
			map.put("untrackedInstanceCount", null);
		}
		else {
			map.put(
				"untrackedInstanceCount",
				String.valueOf(processMetric.getUntrackedInstanceCount()));
		}

		return map;
	}

	public static class ProcessMetricJSONParser
		extends BaseJSONParser<ProcessMetric> {

		@Override
		protected ProcessMetric createDTO() {
			return new ProcessMetric();
		}

		@Override
		protected ProcessMetric[] createDTOArray(int size) {
			return new ProcessMetric[size];
		}

		@Override
		protected void setField(
			ProcessMetric processMetric, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "instanceCount")) {
				if (jsonParserFieldValue != null) {
					processMetric.setInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "onTimeInstanceCount")) {

				if (jsonParserFieldValue != null) {
					processMetric.setOnTimeInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "overdueInstanceCount")) {

				if (jsonParserFieldValue != null) {
					processMetric.setOverdueInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "process")) {
				if (jsonParserFieldValue != null) {
					processMetric.setProcess(
						ProcessSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "untrackedInstanceCount")) {

				if (jsonParserFieldValue != null) {
					processMetric.setUntrackedInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
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