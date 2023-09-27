/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.TaskBulkSelection;
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
public class TaskBulkSelectionSerDes {

	public static TaskBulkSelection toDTO(String json) {
		TaskBulkSelectionJSONParser taskBulkSelectionJSONParser =
			new TaskBulkSelectionJSONParser();

		return taskBulkSelectionJSONParser.parseToDTO(json);
	}

	public static TaskBulkSelection[] toDTOs(String json) {
		TaskBulkSelectionJSONParser taskBulkSelectionJSONParser =
			new TaskBulkSelectionJSONParser();

		return taskBulkSelectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaskBulkSelection taskBulkSelection) {
		if (taskBulkSelection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (taskBulkSelection.getAssigneeIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assigneeIds\": ");

			sb.append("[");

			for (int i = 0; i < taskBulkSelection.getAssigneeIds().length;
				 i++) {

				sb.append(taskBulkSelection.getAssigneeIds()[i]);

				if ((i + 1) < taskBulkSelection.getAssigneeIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taskBulkSelection.getInstanceIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instanceIds\": ");

			sb.append("[");

			for (int i = 0; i < taskBulkSelection.getInstanceIds().length;
				 i++) {

				sb.append(taskBulkSelection.getInstanceIds()[i]);

				if ((i + 1) < taskBulkSelection.getInstanceIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taskBulkSelection.getProcessId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"processId\": ");

			sb.append(taskBulkSelection.getProcessId());
		}

		if (taskBulkSelection.getSlaStatuses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"slaStatuses\": ");

			sb.append("[");

			for (int i = 0; i < taskBulkSelection.getSlaStatuses().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(taskBulkSelection.getSlaStatuses()[i]));

				sb.append("\"");

				if ((i + 1) < taskBulkSelection.getSlaStatuses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taskBulkSelection.getTaskNames() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskNames\": ");

			sb.append("[");

			for (int i = 0; i < taskBulkSelection.getTaskNames().length; i++) {
				sb.append("\"");

				sb.append(_escape(taskBulkSelection.getTaskNames()[i]));

				sb.append("\"");

				if ((i + 1) < taskBulkSelection.getTaskNames().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TaskBulkSelectionJSONParser taskBulkSelectionJSONParser =
			new TaskBulkSelectionJSONParser();

		return taskBulkSelectionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		TaskBulkSelection taskBulkSelection) {

		if (taskBulkSelection == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (taskBulkSelection.getAssigneeIds() == null) {
			map.put("assigneeIds", null);
		}
		else {
			map.put(
				"assigneeIds",
				String.valueOf(taskBulkSelection.getAssigneeIds()));
		}

		if (taskBulkSelection.getInstanceIds() == null) {
			map.put("instanceIds", null);
		}
		else {
			map.put(
				"instanceIds",
				String.valueOf(taskBulkSelection.getInstanceIds()));
		}

		if (taskBulkSelection.getProcessId() == null) {
			map.put("processId", null);
		}
		else {
			map.put(
				"processId", String.valueOf(taskBulkSelection.getProcessId()));
		}

		if (taskBulkSelection.getSlaStatuses() == null) {
			map.put("slaStatuses", null);
		}
		else {
			map.put(
				"slaStatuses",
				String.valueOf(taskBulkSelection.getSlaStatuses()));
		}

		if (taskBulkSelection.getTaskNames() == null) {
			map.put("taskNames", null);
		}
		else {
			map.put(
				"taskNames", String.valueOf(taskBulkSelection.getTaskNames()));
		}

		return map;
	}

	public static class TaskBulkSelectionJSONParser
		extends BaseJSONParser<TaskBulkSelection> {

		@Override
		protected TaskBulkSelection createDTO() {
			return new TaskBulkSelection();
		}

		@Override
		protected TaskBulkSelection[] createDTOArray(int size) {
			return new TaskBulkSelection[size];
		}

		@Override
		protected void setField(
			TaskBulkSelection taskBulkSelection, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "assigneeIds")) {
				if (jsonParserFieldValue != null) {
					taskBulkSelection.setAssigneeIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "instanceIds")) {
				if (jsonParserFieldValue != null) {
					taskBulkSelection.setInstanceIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "processId")) {
				if (jsonParserFieldValue != null) {
					taskBulkSelection.setProcessId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "slaStatuses")) {
				if (jsonParserFieldValue != null) {
					taskBulkSelection.setSlaStatuses(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "taskNames")) {
				if (jsonParserFieldValue != null) {
					taskBulkSelection.setTaskNames(
						toStrings((Object[])jsonParserFieldValue));
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