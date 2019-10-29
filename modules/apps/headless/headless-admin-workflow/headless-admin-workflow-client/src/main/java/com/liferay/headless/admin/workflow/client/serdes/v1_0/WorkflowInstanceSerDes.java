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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
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
public class WorkflowInstanceSerDes {

	public static WorkflowInstance toDTO(String json) {
		WorkflowInstanceJSONParser workflowInstanceJSONParser =
			new WorkflowInstanceJSONParser();

		return workflowInstanceJSONParser.parseToDTO(json);
	}

	public static WorkflowInstance[] toDTOs(String json) {
		WorkflowInstanceJSONParser workflowInstanceJSONParser =
			new WorkflowInstanceJSONParser();

		return workflowInstanceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WorkflowInstance workflowInstance) {
		if (workflowInstance == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (workflowInstance.getCompleted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"completed\": ");

			sb.append(workflowInstance.getCompleted());
		}

		if (workflowInstance.getDateCompletion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCompletion\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					workflowInstance.getDateCompletion()));

			sb.append("\"");
		}

		if (workflowInstance.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					workflowInstance.getDateCreated()));

			sb.append("\"");
		}

		if (workflowInstance.getDefinitionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definitionName\": ");

			sb.append("\"");

			sb.append(_escape(workflowInstance.getDefinitionName()));

			sb.append("\"");
		}

		if (workflowInstance.getDefinitionVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definitionVersion\": ");

			sb.append("\"");

			sb.append(_escape(workflowInstance.getDefinitionVersion()));

			sb.append("\"");
		}

		if (workflowInstance.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(workflowInstance.getId());
		}

		if (workflowInstance.getObjectReviewed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectReviewed\": ");

			sb.append(String.valueOf(workflowInstance.getObjectReviewed()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowInstanceJSONParser workflowInstanceJSONParser =
			new WorkflowInstanceJSONParser();

		return workflowInstanceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(WorkflowInstance workflowInstance) {
		if (workflowInstance == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (workflowInstance.getCompleted() == null) {
			map.put("completed", null);
		}
		else {
			map.put(
				"completed", String.valueOf(workflowInstance.getCompleted()));
		}

		map.put(
			"dateCompletion",
			liferayToJSONDateFormat.format(
				workflowInstance.getDateCompletion()));

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(workflowInstance.getDateCreated()));

		if (workflowInstance.getDefinitionName() == null) {
			map.put("definitionName", null);
		}
		else {
			map.put(
				"definitionName",
				String.valueOf(workflowInstance.getDefinitionName()));
		}

		if (workflowInstance.getDefinitionVersion() == null) {
			map.put("definitionVersion", null);
		}
		else {
			map.put(
				"definitionVersion",
				String.valueOf(workflowInstance.getDefinitionVersion()));
		}

		if (workflowInstance.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(workflowInstance.getId()));
		}

		if (workflowInstance.getObjectReviewed() == null) {
			map.put("objectReviewed", null);
		}
		else {
			map.put(
				"objectReviewed",
				String.valueOf(workflowInstance.getObjectReviewed()));
		}

		return map;
	}

	public static class WorkflowInstanceJSONParser
		extends BaseJSONParser<WorkflowInstance> {

		@Override
		protected WorkflowInstance createDTO() {
			return new WorkflowInstance();
		}

		@Override
		protected WorkflowInstance[] createDTOArray(int size) {
			return new WorkflowInstance[size];
		}

		@Override
		protected void setField(
			WorkflowInstance workflowInstance, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "completed")) {
				if (jsonParserFieldValue != null) {
					workflowInstance.setCompleted(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCompletion")) {
				if (jsonParserFieldValue != null) {
					workflowInstance.setDateCompletion(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					workflowInstance.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "definitionName")) {
				if (jsonParserFieldValue != null) {
					workflowInstance.setDefinitionName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "definitionVersion")) {
				if (jsonParserFieldValue != null) {
					workflowInstance.setDefinitionVersion(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					workflowInstance.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectReviewed")) {
				if (jsonParserFieldValue != null) {
					workflowInstance.setObjectReviewed(
						ObjectReviewedSerDes.toDTO(
							(String)jsonParserFieldValue));
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