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

import com.liferay.headless.admin.workflow.client.dto.v1_0.Assignee;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignableUser;
import com.liferay.headless.admin.workflow.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WorkflowTaskAssignableUserSerDes {

	public static WorkflowTaskAssignableUser toDTO(String json) {
		WorkflowTaskAssignableUserJSONParser
			workflowTaskAssignableUserJSONParser =
				new WorkflowTaskAssignableUserJSONParser();

		return workflowTaskAssignableUserJSONParser.parseToDTO(json);
	}

	public static WorkflowTaskAssignableUser[] toDTOs(String json) {
		WorkflowTaskAssignableUserJSONParser
			workflowTaskAssignableUserJSONParser =
				new WorkflowTaskAssignableUserJSONParser();

		return workflowTaskAssignableUserJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		WorkflowTaskAssignableUser workflowTaskAssignableUser) {

		if (workflowTaskAssignableUser == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (workflowTaskAssignableUser.getAssignableUsers() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assignableUsers\": ");

			sb.append("[");

			for (int i = 0;
				 i < workflowTaskAssignableUser.getAssignableUsers().length;
				 i++) {

				sb.append(
					String.valueOf(
						workflowTaskAssignableUser.getAssignableUsers()[i]));

				if ((i + 1) <
						workflowTaskAssignableUser.
							getAssignableUsers().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (workflowTaskAssignableUser.getWorkflowTaskId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskId\": ");

			sb.append(workflowTaskAssignableUser.getWorkflowTaskId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowTaskAssignableUserJSONParser
			workflowTaskAssignableUserJSONParser =
				new WorkflowTaskAssignableUserJSONParser();

		return workflowTaskAssignableUserJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WorkflowTaskAssignableUser workflowTaskAssignableUser) {

		if (workflowTaskAssignableUser == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (workflowTaskAssignableUser.getAssignableUsers() == null) {
			map.put("assignableUsers", null);
		}
		else {
			map.put(
				"assignableUsers",
				String.valueOf(
					workflowTaskAssignableUser.getAssignableUsers()));
		}

		if (workflowTaskAssignableUser.getWorkflowTaskId() == null) {
			map.put("workflowTaskId", null);
		}
		else {
			map.put(
				"workflowTaskId",
				String.valueOf(workflowTaskAssignableUser.getWorkflowTaskId()));
		}

		return map;
	}

	public static class WorkflowTaskAssignableUserJSONParser
		extends BaseJSONParser<WorkflowTaskAssignableUser> {

		@Override
		protected WorkflowTaskAssignableUser createDTO() {
			return new WorkflowTaskAssignableUser();
		}

		@Override
		protected WorkflowTaskAssignableUser[] createDTOArray(int size) {
			return new WorkflowTaskAssignableUser[size];
		}

		@Override
		protected void setField(
			WorkflowTaskAssignableUser workflowTaskAssignableUser,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "assignableUsers")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignableUser.setAssignableUsers(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AssigneeSerDes.toDTO((String)object)
						).toArray(
							size -> new Assignee[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "workflowTaskId")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignableUser.setWorkflowTaskId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			sb.append("\":");

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
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}