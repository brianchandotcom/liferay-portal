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

package com.liferay.headless.workflow.client.serdes.v1_0;

import com.liferay.headless.workflow.client.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.workflow.client.json.BaseJSONParser;

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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"assigneeId\": ");

		if (workflowTaskAssignToUser.getAssigneeId() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowTaskAssignToUser.getAssigneeId());
		}

		sb.append(", ");

		sb.append("\"comment\": ");

		if (workflowTaskAssignToUser.getComment() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowTaskAssignToUser.getComment());
		}

		sb.append(", ");

		sb.append("\"dueDate\": ");

		if (workflowTaskAssignToUser.getDueDate() == null) {
			sb.append("null");
		}
		else {
			sb.append(workflowTaskAssignToUser.getDueDate());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<WorkflowTaskAssignToUser> workflowTaskAssignToUsers) {

		if (workflowTaskAssignToUsers == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (WorkflowTaskAssignToUser workflowTaskAssignToUser :
				workflowTaskAssignToUsers) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(workflowTaskAssignToUser));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class WorkflowTaskAssignToUserJSONParser
		extends BaseJSONParser<WorkflowTaskAssignToUser> {

		protected WorkflowTaskAssignToUser createDTO() {
			return new WorkflowTaskAssignToUser();
		}

		protected WorkflowTaskAssignToUser[] createDTOArray(int size) {
			return new WorkflowTaskAssignToUser[size];
		}

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
						_toDate((String)jsonParserFieldValue));
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