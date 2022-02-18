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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignToRole;
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
public class WorkflowTaskAssignToRoleSerDes {

	public static WorkflowTaskAssignToRole toDTO(String json) {
		WorkflowTaskAssignToRoleJSONParser workflowTaskAssignToRoleJSONParser =
			new WorkflowTaskAssignToRoleJSONParser();

		return workflowTaskAssignToRoleJSONParser.parseToDTO(json);
	}

	public static WorkflowTaskAssignToRole[] toDTOs(String json) {
		WorkflowTaskAssignToRoleJSONParser workflowTaskAssignToRoleJSONParser =
			new WorkflowTaskAssignToRoleJSONParser();

		return workflowTaskAssignToRoleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		WorkflowTaskAssignToRole workflowTaskAssignToRole) {

		if (workflowTaskAssignToRole == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (workflowTaskAssignToRole.getComment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comment\": ");

			sb.append("\"");

			sb.append(_escape(workflowTaskAssignToRole.getComment()));

			sb.append("\"");
		}

		if (workflowTaskAssignToRole.getDueDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					workflowTaskAssignToRole.getDueDate()));

			sb.append("\"");
		}

		if (workflowTaskAssignToRole.getRoleId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleId\": ");

			sb.append(workflowTaskAssignToRole.getRoleId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowTaskAssignToRoleJSONParser workflowTaskAssignToRoleJSONParser =
			new WorkflowTaskAssignToRoleJSONParser();

		return workflowTaskAssignToRoleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WorkflowTaskAssignToRole workflowTaskAssignToRole) {

		if (workflowTaskAssignToRole == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (workflowTaskAssignToRole.getComment() == null) {
			map.put("comment", null);
		}
		else {
			map.put(
				"comment",
				String.valueOf(workflowTaskAssignToRole.getComment()));
		}

		if (workflowTaskAssignToRole.getDueDate() == null) {
			map.put("dueDate", null);
		}
		else {
			map.put(
				"dueDate",
				liferayToJSONDateFormat.format(
					workflowTaskAssignToRole.getDueDate()));
		}

		if (workflowTaskAssignToRole.getRoleId() == null) {
			map.put("roleId", null);
		}
		else {
			map.put(
				"roleId", String.valueOf(workflowTaskAssignToRole.getRoleId()));
		}

		return map;
	}

	public static class WorkflowTaskAssignToRoleJSONParser
		extends BaseJSONParser<WorkflowTaskAssignToRole> {

		@Override
		protected WorkflowTaskAssignToRole createDTO() {
			return new WorkflowTaskAssignToRole();
		}

		@Override
		protected WorkflowTaskAssignToRole[] createDTOArray(int size) {
			return new WorkflowTaskAssignToRole[size];
		}

		@Override
		protected void setField(
			WorkflowTaskAssignToRole workflowTaskAssignToRole,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "comment")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignToRole.setComment(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dueDate")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignToRole.setDueDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleId")) {
				if (jsonParserFieldValue != null) {
					workflowTaskAssignToRole.setRoleId(
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