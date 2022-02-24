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

package com.liferay.headless.admin.workflow.client.serdes.v1_0;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class WorkflowTaskAssignToUserSerDes {

	public static WorkflowTaskAssignToUser toDTO(String json) {
		WorkflowTaskAssignToUserJSONParser workflowTaskAssignToUserJSONParser =
			new WorkflowTaskAssignToUserJSONParser();

		return workflowTaskAssignToUserJSONParser.parseToDTO(json);
	}

	public static WorkflowTaskAssignToUser[] toDTOs(String json) {
		WorkflowTaskAssignToUserJSONParser workflowTaskAssignToUserJSONParser =
			new WorkflowTaskAssignToUserJSONParser();

		return workflowTaskAssignToUserJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		WorkflowTaskAssignToUser workflowTaskAssignToUser) {

		if (workflowTaskAssignToUser == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (workflowTaskAssignToUser.getAssigneeId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assigneeId\": ");

			sb.append(workflowTaskAssignToUser.getAssigneeId());
		}

		if (workflowTaskAssignToUser.getComment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comment\": ");

			sb.append("\"");

			sb.append(_escape(workflowTaskAssignToUser.getComment()));

			sb.append("\"");
		}

		if (workflowTaskAssignToUser.getDueDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					workflowTaskAssignToUser.getDueDate()));

			sb.append("\"");
		}

		if (workflowTaskAssignToUser.getWorkflowTaskId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskId\": ");

			sb.append(workflowTaskAssignToUser.getWorkflowTaskId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowTaskAssignToUserJSONParser workflowTaskAssignToUserJSONParser =
			new WorkflowTaskAssignToUserJSONParser();

		return workflowTaskAssignToUserJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WorkflowTaskAssignToUser workflowTaskAssignToUser) {

		if (workflowTaskAssignToUser == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (workflowTaskAssignToUser.getAssigneeId() == null) {
			map.put("assigneeId", null);
		}
		else {
			map.put(
				"assigneeId",
				String.valueOf(workflowTaskAssignToUser.getAssigneeId()));
		}

		if (workflowTaskAssignToUser.getComment() == null) {
			map.put("comment", null);
		}
		else {
			map.put(
				"comment",
				String.valueOf(workflowTaskAssignToUser.getComment()));
		}

		if (workflowTaskAssignToUser.getDueDate() == null) {
			map.put("dueDate", null);
		}
		else {
			map.put(
				"dueDate",
				liferayToJSONDateFormat.format(
					workflowTaskAssignToUser.getDueDate()));
		}

		if (workflowTaskAssignToUser.getWorkflowTaskId() == null) {
			map.put("workflowTaskId", null);
		}
		else {
			map.put(
				"workflowTaskId",
				String.valueOf(workflowTaskAssignToUser.getWorkflowTaskId()));
		}

		return map;
	}

	public static class WorkflowTaskAssignToUserJSONParser
		extends BaseJSONParser<WorkflowTaskAssignToUser> {

		@Override
		protected WorkflowTaskAssignToUser createDTO() {
			return new WorkflowTaskAssignToUser();
		}

		@Override
		protected WorkflowTaskAssignToUser[] createDTOArray(int size) {
			return new WorkflowTaskAssignToUser[size];
		}

		@Override
		protected void setField(
			WorkflowTaskAssignToUser workflowTaskAssignToUser,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "assigneeId")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignToUser.setAssigneeId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "comment")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignToUser.setComment(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dueDate")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignToUser.setDueDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "workflowTaskId")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignToUser.setWorkflowTaskId(
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