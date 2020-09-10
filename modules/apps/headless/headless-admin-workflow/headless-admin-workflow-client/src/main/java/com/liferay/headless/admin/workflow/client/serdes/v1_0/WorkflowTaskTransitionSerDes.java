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

import com.liferay.headless.admin.workflow.client.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskTransition;
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
public class WorkflowTaskTransitionSerDes {

	public static WorkflowTaskTransition toDTO(String json) {
		WorkflowTaskTransitionJSONParser workflowTaskTransitionJSONParser =
			new WorkflowTaskTransitionJSONParser();

		return workflowTaskTransitionJSONParser.parseToDTO(json);
	}

	public static WorkflowTaskTransition[] toDTOs(String json) {
		WorkflowTaskTransitionJSONParser workflowTaskTransitionJSONParser =
			new WorkflowTaskTransitionJSONParser();

		return workflowTaskTransitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WorkflowTaskTransition workflowTaskTransition) {
		if (workflowTaskTransition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (workflowTaskTransition.getTransitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transitions\": ");

			sb.append("[");

			for (int i = 0; i < workflowTaskTransition.getTransitions().length;
				 i++) {

				sb.append(
					String.valueOf(workflowTaskTransition.getTransitions()[i]));

				if ((i + 1) < workflowTaskTransition.getTransitions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (workflowTaskTransition.getWorkflowDefinitionVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowDefinitionVersion\": ");

			sb.append("\"");

			sb.append(
				_escape(workflowTaskTransition.getWorkflowDefinitionVersion()));

			sb.append("\"");
		}

		if (workflowTaskTransition.getWorkflowTaskLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskLabel\": ");

			sb.append("\"");

			sb.append(_escape(workflowTaskTransition.getWorkflowTaskLabel()));

			sb.append("\"");
		}

		if (workflowTaskTransition.getWorkflowTaskName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskName\": ");

			sb.append("\"");

			sb.append(_escape(workflowTaskTransition.getWorkflowTaskName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowTaskTransitionJSONParser workflowTaskTransitionJSONParser =
			new WorkflowTaskTransitionJSONParser();

		return workflowTaskTransitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WorkflowTaskTransition workflowTaskTransition) {

		if (workflowTaskTransition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (workflowTaskTransition.getTransitions() == null) {
			map.put("transitions", null);
		}
		else {
			map.put(
				"transitions",
				String.valueOf(workflowTaskTransition.getTransitions()));
		}

		if (workflowTaskTransition.getWorkflowDefinitionVersion() == null) {
			map.put("workflowDefinitionVersion", null);
		}
		else {
			map.put(
				"workflowDefinitionVersion",
				String.valueOf(
					workflowTaskTransition.getWorkflowDefinitionVersion()));
		}

		if (workflowTaskTransition.getWorkflowTaskLabel() == null) {
			map.put("workflowTaskLabel", null);
		}
		else {
			map.put(
				"workflowTaskLabel",
				String.valueOf(workflowTaskTransition.getWorkflowTaskLabel()));
		}

		if (workflowTaskTransition.getWorkflowTaskName() == null) {
			map.put("workflowTaskName", null);
		}
		else {
			map.put(
				"workflowTaskName",
				String.valueOf(workflowTaskTransition.getWorkflowTaskName()));
		}

		return map;
	}

	public static class WorkflowTaskTransitionJSONParser
		extends BaseJSONParser<WorkflowTaskTransition> {

		@Override
		protected WorkflowTaskTransition createDTO() {
			return new WorkflowTaskTransition();
		}

		@Override
		protected WorkflowTaskTransition[] createDTOArray(int size) {
			return new WorkflowTaskTransition[size];
		}

		@Override
		protected void setField(
			WorkflowTaskTransition workflowTaskTransition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "transitions")) {
				if (jsonParserFieldValue != null) {
					workflowTaskTransition.setTransitions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TransitionSerDes.toDTO((String)object)
						).toArray(
							size -> new Transition[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "workflowDefinitionVersion")) {

				if (jsonParserFieldValue != null) {
					workflowTaskTransition.setWorkflowDefinitionVersion(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "workflowTaskLabel")) {
				if (jsonParserFieldValue != null) {
					workflowTaskTransition.setWorkflowTaskLabel(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "workflowTaskName")) {
				if (jsonParserFieldValue != null) {
					workflowTaskTransition.setWorkflowTaskName(
						(String)jsonParserFieldValue);
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