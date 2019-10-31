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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
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
public class WorkflowDefinitionSerDes {

	public static WorkflowDefinition toDTO(String json) {
		WorkflowDefinitionJSONParser workflowDefinitionJSONParser =
			new WorkflowDefinitionJSONParser();

		return workflowDefinitionJSONParser.parseToDTO(json);
	}

	public static WorkflowDefinition[] toDTOs(String json) {
		WorkflowDefinitionJSONParser workflowDefinitionJSONParser =
			new WorkflowDefinitionJSONParser();

		return workflowDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WorkflowDefinition workflowDefinition) {
		if (workflowDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (workflowDefinition.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(workflowDefinition.getActive());
		}

		if (workflowDefinition.getContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\": ");

			sb.append("\"");

			sb.append(_escape(workflowDefinition.getContent()));

			sb.append("\"");
		}

		if (workflowDefinition.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					workflowDefinition.getDateModified()));

			sb.append("\"");
		}

		if (workflowDefinition.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(workflowDefinition.getDescription()));

			sb.append("\"");
		}

		if (workflowDefinition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(workflowDefinition.getName()));

			sb.append("\"");
		}

		if (workflowDefinition.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(workflowDefinition.getTitle()));

			sb.append("\"");
		}

		if (workflowDefinition.getVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append("\"");

			sb.append(_escape(workflowDefinition.getVersion()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WorkflowDefinitionJSONParser workflowDefinitionJSONParser =
			new WorkflowDefinitionJSONParser();

		return workflowDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WorkflowDefinition workflowDefinition) {

		if (workflowDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (workflowDefinition.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(workflowDefinition.getActive()));
		}

		if (workflowDefinition.getContent() == null) {
			map.put("content", null);
		}
		else {
			map.put("content", String.valueOf(workflowDefinition.getContent()));
		}

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				workflowDefinition.getDateModified()));

		if (workflowDefinition.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(workflowDefinition.getDescription()));
		}

		if (workflowDefinition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(workflowDefinition.getName()));
		}

		if (workflowDefinition.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(workflowDefinition.getTitle()));
		}

		if (workflowDefinition.getVersion() == null) {
			map.put("version", null);
		}
		else {
			map.put("version", String.valueOf(workflowDefinition.getVersion()));
		}

		return map;
	}

	public static class WorkflowDefinitionJSONParser
		extends BaseJSONParser<WorkflowDefinition> {

		@Override
		protected WorkflowDefinition createDTO() {
			return new WorkflowDefinition();
		}

		@Override
		protected WorkflowDefinition[] createDTOArray(int size) {
			return new WorkflowDefinition[size];
		}

		@Override
		protected void setField(
			WorkflowDefinition workflowDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					workflowDefinition.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "content")) {
				if (jsonParserFieldValue != null) {
					workflowDefinition.setContent((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					workflowDefinition.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					workflowDefinition.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					workflowDefinition.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					workflowDefinition.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "version")) {
				if (jsonParserFieldValue != null) {
					workflowDefinition.setVersion((String)jsonParserFieldValue);
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