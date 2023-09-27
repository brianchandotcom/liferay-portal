/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.client.serdes.v1_0;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskTransition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskTransitions;
import com.liferay.headless.admin.workflow.client.json.BaseJSONParser;

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
public class WorkflowTaskTransitionsSerDes {

	public static WorkflowTaskTransitions toDTO(String json) {
		WorkflowTaskTransitionsJSONParser workflowTaskTransitionsJSONParser =
			new WorkflowTaskTransitionsJSONParser();

		return workflowTaskTransitionsJSONParser.parseToDTO(json);
	}

	public static WorkflowTaskTransitions[] toDTOs(String json) {
		WorkflowTaskTransitionsJSONParser workflowTaskTransitionsJSONParser =
			new WorkflowTaskTransitionsJSONParser();

		return workflowTaskTransitionsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		WorkflowTaskTransitions workflowTaskTransitions) {

		if (workflowTaskTransitions == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (workflowTaskTransitions.getWorkflowTaskTransitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskTransitions\": ");

			sb.append("[");

			for (int i = 0;
				 i <
					 workflowTaskTransitions.
						 getWorkflowTaskTransitions().length;
				 i++) {

				sb.append(
					String.valueOf(
						workflowTaskTransitions.getWorkflowTaskTransitions()
							[i]));

				if ((i + 1) < workflowTaskTransitions.
						getWorkflowTaskTransitions().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowTaskTransitionsJSONParser workflowTaskTransitionsJSONParser =
			new WorkflowTaskTransitionsJSONParser();

		return workflowTaskTransitionsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WorkflowTaskTransitions workflowTaskTransitions) {

		if (workflowTaskTransitions == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (workflowTaskTransitions.getWorkflowTaskTransitions() == null) {
			map.put("workflowTaskTransitions", null);
		}
		else {
			map.put(
				"workflowTaskTransitions",
				String.valueOf(
					workflowTaskTransitions.getWorkflowTaskTransitions()));
		}

		return map;
	}

	public static class WorkflowTaskTransitionsJSONParser
		extends BaseJSONParser<WorkflowTaskTransitions> {

		@Override
		protected WorkflowTaskTransitions createDTO() {
			return new WorkflowTaskTransitions();
		}

		@Override
		protected WorkflowTaskTransitions[] createDTOArray(int size) {
			return new WorkflowTaskTransitions[size];
		}

		@Override
		protected void setField(
			WorkflowTaskTransitions workflowTaskTransitions,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "workflowTaskTransitions")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					WorkflowTaskTransition[] workflowTaskTransitionsArray =
						new WorkflowTaskTransition
							[jsonParserFieldValues.length];

					for (int i = 0; i < workflowTaskTransitionsArray.length;
						 i++) {

						workflowTaskTransitionsArray[i] =
							WorkflowTaskTransitionSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					workflowTaskTransitions.setWorkflowTaskTransitions(
						workflowTaskTransitionsArray);
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