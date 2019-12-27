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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignableUser;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignableUsersBag;
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
public class WorkflowTaskAssignableUsersBagSerDes {

	public static WorkflowTaskAssignableUsersBag toDTO(String json) {
		WorkflowTaskAssignableUsersBagJSONParser
			workflowTaskAssignableUsersBagJSONParser =
				new WorkflowTaskAssignableUsersBagJSONParser();

		return workflowTaskAssignableUsersBagJSONParser.parseToDTO(json);
	}

	public static WorkflowTaskAssignableUsersBag[] toDTOs(String json) {
		WorkflowTaskAssignableUsersBagJSONParser
			workflowTaskAssignableUsersBagJSONParser =
				new WorkflowTaskAssignableUsersBagJSONParser();

		return workflowTaskAssignableUsersBagJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag) {

		if (workflowTaskAssignableUsersBag == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (workflowTaskAssignableUsersBag.getWorkflowTaskAssignableUsers() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskAssignableUsers\": ");

			sb.append("[");

			for (int i = 0;
				 i < workflowTaskAssignableUsersBag.
					 getWorkflowTaskAssignableUsers().length;
				 i++) {

				sb.append(
					String.valueOf(
						workflowTaskAssignableUsersBag.
							getWorkflowTaskAssignableUsers()[i]));

				if ((i + 1) < workflowTaskAssignableUsersBag.
						getWorkflowTaskAssignableUsers().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowTaskAssignableUsersBagJSONParser
			workflowTaskAssignableUsersBagJSONParser =
				new WorkflowTaskAssignableUsersBagJSONParser();

		return workflowTaskAssignableUsersBagJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag) {

		if (workflowTaskAssignableUsersBag == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (workflowTaskAssignableUsersBag.getWorkflowTaskAssignableUsers() ==
				null) {

			map.put("workflowTaskAssignableUsers", null);
		}
		else {
			map.put(
				"workflowTaskAssignableUsers",
				String.valueOf(
					workflowTaskAssignableUsersBag.
						getWorkflowTaskAssignableUsers()));
		}

		return map;
	}

	public static class WorkflowTaskAssignableUsersBagJSONParser
		extends BaseJSONParser<WorkflowTaskAssignableUsersBag> {

		@Override
		protected WorkflowTaskAssignableUsersBag createDTO() {
			return new WorkflowTaskAssignableUsersBag();
		}

		@Override
		protected WorkflowTaskAssignableUsersBag[] createDTOArray(int size) {
			return new WorkflowTaskAssignableUsersBag[size];
		}

		@Override
		protected void setField(
			WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "workflowTaskAssignableUsers")) {

				if (jsonParserFieldValue != null) {
					workflowTaskAssignableUsersBag.
						setWorkflowTaskAssignableUsers(
							Stream.of(
								toStrings((Object[])jsonParserFieldValue)
							).map(
								object ->
									WorkflowTaskAssignableUserSerDes.toDTO(
										(String)object)
							).toArray(
								size -> new WorkflowTaskAssignableUser[size]
							));
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