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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"auditPerson\": ");

		if (workflowLog.getAuditPerson() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getAuditPerson());
		}

		sb.append(", ");

		sb.append("\"commentLog\": ");

		if (workflowLog.getCommentLog() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getCommentLog());
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (workflowLog.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getDateCreated());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (workflowLog.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getId());
		}

		sb.append(", ");

		sb.append("\"person\": ");

		if (workflowLog.getPerson() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getPerson());
		}

		sb.append(", ");

		sb.append("\"previousPerson\": ");

		if (workflowLog.getPreviousPerson() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getPreviousPerson());
		}

		sb.append(", ");

		sb.append("\"previousState\": ");

		if (workflowLog.getPreviousState() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getPreviousState());
		}

		sb.append(", ");

		sb.append("\"state\": ");

		if (workflowLog.getState() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getState());
		}

		sb.append(", ");

		sb.append("\"taskId\": ");

		if (workflowLog.getTaskId() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getTaskId());
		}

		sb.append(", ");

		sb.append("\"type\": ");

		if (workflowLog.getType() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowLog.getType());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<WorkflowLog> workflowLogs) {
		if (workflowLogs == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (WorkflowLog workflowLog : workflowLogs) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(workflowLog));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class WorkflowLogJSONParser
		extends BaseJSONParser<WorkflowLog> {

		protected WorkflowLog createDTO() {
			return new WorkflowLog();
		}

		protected WorkflowLog[] createDTOArray(int size) {
			return new WorkflowLog[size];
		}

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
						_toDate((String)jsonParserFieldValue));
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

		private Date _toDate(String string) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");

				return dateFormat.parse(string);
			}
			catch (ParseException pe) {
				throw new IllegalArgumentException("Unable to parse " + string);
			}
		}

	}

}