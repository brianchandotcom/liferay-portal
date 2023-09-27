/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.client.serdes.v1_0;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskIds;
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
public class WorkflowTaskIdsSerDes {

	public static WorkflowTaskIds toDTO(String json) {
		WorkflowTaskIdsJSONParser workflowTaskIdsJSONParser =
			new WorkflowTaskIdsJSONParser();

		return workflowTaskIdsJSONParser.parseToDTO(json);
	}

	public static WorkflowTaskIds[] toDTOs(String json) {
		WorkflowTaskIdsJSONParser workflowTaskIdsJSONParser =
			new WorkflowTaskIdsJSONParser();

		return workflowTaskIdsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WorkflowTaskIds workflowTaskIds) {
		if (workflowTaskIds == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (workflowTaskIds.getWorkflowTaskIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskIds\": ");

			sb.append("[");

			for (int i = 0; i < workflowTaskIds.getWorkflowTaskIds().length;
				 i++) {

				sb.append(workflowTaskIds.getWorkflowTaskIds()[i]);

				if ((i + 1) < workflowTaskIds.getWorkflowTaskIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowTaskIdsJSONParser workflowTaskIdsJSONParser =
			new WorkflowTaskIdsJSONParser();

		return workflowTaskIdsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(WorkflowTaskIds workflowTaskIds) {
		if (workflowTaskIds == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (workflowTaskIds.getWorkflowTaskIds() == null) {
			map.put("workflowTaskIds", null);
		}
		else {
			map.put(
				"workflowTaskIds",
				String.valueOf(workflowTaskIds.getWorkflowTaskIds()));
		}

		return map;
	}

	public static class WorkflowTaskIdsJSONParser
		extends BaseJSONParser<WorkflowTaskIds> {

		@Override
		protected WorkflowTaskIds createDTO() {
			return new WorkflowTaskIds();
		}

		@Override
		protected WorkflowTaskIds[] createDTOArray(int size) {
			return new WorkflowTaskIds[size];
		}

		@Override
		protected void setField(
			WorkflowTaskIds workflowTaskIds, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "workflowTaskIds")) {
				if (jsonParserFieldValue != null) {
					workflowTaskIds.setWorkflowTaskIds(
						toLongs((Object[])jsonParserFieldValue));
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