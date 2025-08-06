/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.TaskReport;
import com.liferay.headless.cms.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class TaskReportSerDes {

	public static TaskReport toDTO(String json) {
		TaskReportJSONParser taskReportJSONParser = new TaskReportJSONParser();

		return taskReportJSONParser.parseToDTO(json);
	}

	public static TaskReport[] toDTOs(String json) {
		TaskReportJSONParser taskReportJSONParser = new TaskReportJSONParser();

		return taskReportJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaskReport taskReport) {
		if (taskReport == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (taskReport.getAction() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			sb.append("\"");

			sb.append(_escape(taskReport.getAction()));

			sb.append("\"");
		}

		if (taskReport.getAuthor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"author\": ");

			sb.append("\"");

			sb.append(_escape(taskReport.getAuthor()));

			sb.append("\"");
		}

		if (taskReport.getDateCompletion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCompletion\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(taskReport.getDateCompletion()));

			sb.append("\"");
		}

		if (taskReport.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(taskReport.getDateCreated()));

			sb.append("\"");
		}

		if (taskReport.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(taskReport.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (taskReport.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(taskReport.getId());
		}

		if (taskReport.getNumberOfItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfItems\": ");

			sb.append(taskReport.getNumberOfItems());
		}

		if (taskReport.getResult() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"result\": ");

			sb.append("\"");

			sb.append(_escape(taskReport.getResult()));

			sb.append("\"");
		}

		if (taskReport.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(taskReport.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TaskReportJSONParser taskReportJSONParser = new TaskReportJSONParser();

		return taskReportJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(TaskReport taskReport) {
		if (taskReport == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (taskReport.getAction() == null) {
			map.put("action", null);
		}
		else {
			map.put("action", String.valueOf(taskReport.getAction()));
		}

		if (taskReport.getAuthor() == null) {
			map.put("author", null);
		}
		else {
			map.put("author", String.valueOf(taskReport.getAuthor()));
		}

		if (taskReport.getDateCompletion() == null) {
			map.put("dateCompletion", null);
		}
		else {
			map.put(
				"dateCompletion",
				liferayToJSONDateFormat.format(taskReport.getDateCompletion()));
		}

		if (taskReport.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(taskReport.getDateCreated()));
		}

		if (taskReport.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(taskReport.getExternalReferenceCode()));
		}

		if (taskReport.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(taskReport.getId()));
		}

		if (taskReport.getNumberOfItems() == null) {
			map.put("numberOfItems", null);
		}
		else {
			map.put(
				"numberOfItems", String.valueOf(taskReport.getNumberOfItems()));
		}

		if (taskReport.getResult() == null) {
			map.put("result", null);
		}
		else {
			map.put("result", String.valueOf(taskReport.getResult()));
		}

		if (taskReport.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(taskReport.getType()));
		}

		return map;
	}

	public static class TaskReportJSONParser
		extends BaseJSONParser<TaskReport> {

		@Override
		protected TaskReport createDTO() {
			return new TaskReport();
		}

		@Override
		protected TaskReport[] createDTOArray(int size) {
			return new TaskReport[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "action")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "author")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCompletion")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfItems")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "result")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			TaskReport taskReport, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "action")) {
				if (jsonParserFieldValue != null) {
					taskReport.setAction((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "author")) {
				if (jsonParserFieldValue != null) {
					taskReport.setAuthor((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCompletion")) {
				if (jsonParserFieldValue != null) {
					taskReport.setDateCompletion(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					taskReport.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					taskReport.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					taskReport.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfItems")) {
				if (jsonParserFieldValue != null) {
					taskReport.setNumberOfItems(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "result")) {
				if (jsonParserFieldValue != null) {
					taskReport.setResult((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					taskReport.setType((String)jsonParserFieldValue);
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