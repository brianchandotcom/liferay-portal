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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowLog;
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
public class WorkflowLogSerDes {

	public static WorkflowLog toDTO(String json) {
		WorkflowLogJSONParser workflowLogJSONParser =
			new WorkflowLogJSONParser();

		return workflowLogJSONParser.parseToDTO(json);
	}

	public static WorkflowLog[] toDTOs(String json) {
		WorkflowLogJSONParser workflowLogJSONParser =
			new WorkflowLogJSONParser();

		return workflowLogJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WorkflowLog workflowLog) {
		if (workflowLog == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (workflowLog.getAuditPerson() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"auditPerson\": ");

			sb.append(String.valueOf(workflowLog.getAuditPerson()));
		}

		if (workflowLog.getCommentLog() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"commentLog\": ");

			sb.append("\"");

			sb.append(_escape(workflowLog.getCommentLog()));

			sb.append("\"");
		}

		if (workflowLog.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(workflowLog.getDateCreated()));

			sb.append("\"");
		}

		if (workflowLog.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(workflowLog.getId());
		}

		if (workflowLog.getPerson() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"person\": ");

			sb.append(String.valueOf(workflowLog.getPerson()));
		}

		if (workflowLog.getPreviousPerson() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"previousPerson\": ");

			sb.append(String.valueOf(workflowLog.getPreviousPerson()));
		}

		if (workflowLog.getPreviousState() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"previousState\": ");

			sb.append("\"");

			sb.append(_escape(workflowLog.getPreviousState()));

			sb.append("\"");
		}

		if (workflowLog.getState() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"state\": ");

			sb.append("\"");

			sb.append(_escape(workflowLog.getState()));

			sb.append("\"");
		}

		if (workflowLog.getTaskId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskId\": ");

			sb.append(workflowLog.getTaskId());
		}

		if (workflowLog.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(workflowLog.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowLogJSONParser workflowLogJSONParser =
			new WorkflowLogJSONParser();

		return workflowLogJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(WorkflowLog workflowLog) {
		if (workflowLog == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (workflowLog.getAuditPerson() == null) {
			map.put("auditPerson", null);
		}
		else {
			map.put(
				"auditPerson", String.valueOf(workflowLog.getAuditPerson()));
		}

		if (workflowLog.getCommentLog() == null) {
			map.put("commentLog", null);
		}
		else {
			map.put("commentLog", String.valueOf(workflowLog.getCommentLog()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(workflowLog.getDateCreated()));

		if (workflowLog.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(workflowLog.getId()));
		}

		if (workflowLog.getPerson() == null) {
			map.put("person", null);
		}
		else {
			map.put("person", String.valueOf(workflowLog.getPerson()));
		}

		if (workflowLog.getPreviousPerson() == null) {
			map.put("previousPerson", null);
		}
		else {
			map.put(
				"previousPerson",
				String.valueOf(workflowLog.getPreviousPerson()));
		}

		if (workflowLog.getPreviousState() == null) {
			map.put("previousState", null);
		}
		else {
			map.put(
				"previousState",
				String.valueOf(workflowLog.getPreviousState()));
		}

		if (workflowLog.getState() == null) {
			map.put("state", null);
		}
		else {
			map.put("state", String.valueOf(workflowLog.getState()));
		}

		if (workflowLog.getTaskId() == null) {
			map.put("taskId", null);
		}
		else {
			map.put("taskId", String.valueOf(workflowLog.getTaskId()));
		}

		if (workflowLog.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(workflowLog.getType()));
		}

		return map;
	}

	public static class WorkflowLogJSONParser
		extends BaseJSONParser<WorkflowLog> {

		@Override
		protected WorkflowLog createDTO() {
			return new WorkflowLog();
		}

		@Override
		protected WorkflowLog[] createDTOArray(int size) {
			return new WorkflowLog[size];
		}

		@Override
		protected void setField(
			WorkflowLog workflowLog, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "auditPerson")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setAuditPerson(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "commentLog")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setCommentLog((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "person")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setPerson(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "previousPerson")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setPreviousPerson(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "previousState")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setPreviousState((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "state")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setState((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "taskId")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setTaskId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					workflowLog.setType((String)jsonParserFieldValue);
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
				sb.append(_escape(entry.getValue()));
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