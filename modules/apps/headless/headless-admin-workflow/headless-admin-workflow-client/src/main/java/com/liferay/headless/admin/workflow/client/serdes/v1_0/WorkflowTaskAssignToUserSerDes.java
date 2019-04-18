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

import java.util.HashMap;
import java.util.Map;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (workflowTaskAssignToUser.getAssigneeId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assigneeId\":");

			sb.append(workflowTaskAssignToUser.getAssigneeId());
		}

		if (workflowTaskAssignToUser.getComment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comment\":");

			sb.append("\"");

			sb.append(_escape(workflowTaskAssignToUser.getComment()));

			sb.append("\"");
		}

		if (workflowTaskAssignToUser.getDueDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueDate\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					workflowTaskAssignToUser.getDueDate()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		WorkflowTaskAssignToUser workflowTaskAssignToUser) {

		if (workflowTaskAssignToUser == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

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

		map.put(
			"dueDate",
			liferayToJSONDateFormat.format(
				workflowTaskAssignToUser.getDueDate()));

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class WorkflowTaskAssignToUserJSONParser
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
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}