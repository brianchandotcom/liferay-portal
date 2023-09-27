/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.client.serdes.v1_0;

import com.liferay.headless.admin.workflow.client.dto.v1_0.ChangeTransition;
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
public class ChangeTransitionSerDes {

	public static ChangeTransition toDTO(String json) {
		ChangeTransitionJSONParser changeTransitionJSONParser =
			new ChangeTransitionJSONParser();

		return changeTransitionJSONParser.parseToDTO(json);
	}

	public static ChangeTransition[] toDTOs(String json) {
		ChangeTransitionJSONParser changeTransitionJSONParser =
			new ChangeTransitionJSONParser();

		return changeTransitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ChangeTransition changeTransition) {
		if (changeTransition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (changeTransition.getComment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comment\": ");

			sb.append("\"");

			sb.append(_escape(changeTransition.getComment()));

			sb.append("\"");
		}

		if (changeTransition.getTransitionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transitionName\": ");

			sb.append("\"");

			sb.append(_escape(changeTransition.getTransitionName()));

			sb.append("\"");
		}

		if (changeTransition.getWorkflowTaskId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowTaskId\": ");

			sb.append(changeTransition.getWorkflowTaskId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ChangeTransitionJSONParser changeTransitionJSONParser =
			new ChangeTransitionJSONParser();

		return changeTransitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ChangeTransition changeTransition) {
		if (changeTransition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (changeTransition.getComment() == null) {
			map.put("comment", null);
		}
		else {
			map.put("comment", String.valueOf(changeTransition.getComment()));
		}

		if (changeTransition.getTransitionName() == null) {
			map.put("transitionName", null);
		}
		else {
			map.put(
				"transitionName",
				String.valueOf(changeTransition.getTransitionName()));
		}

		if (changeTransition.getWorkflowTaskId() == null) {
			map.put("workflowTaskId", null);
		}
		else {
			map.put(
				"workflowTaskId",
				String.valueOf(changeTransition.getWorkflowTaskId()));
		}

		return map;
	}

	public static class ChangeTransitionJSONParser
		extends BaseJSONParser<ChangeTransition> {

		@Override
		protected ChangeTransition createDTO() {
			return new ChangeTransition();
		}

		@Override
		protected ChangeTransition[] createDTOArray(int size) {
			return new ChangeTransition[size];
		}

		@Override
		protected void setField(
			ChangeTransition changeTransition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "comment")) {
				if (jsonParserFieldValue != null) {
					changeTransition.setComment((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "transitionName")) {
				if (jsonParserFieldValue != null) {
					changeTransition.setTransitionName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "workflowTaskId")) {
				if (jsonParserFieldValue != null) {
					changeTransition.setWorkflowTaskId(
						Long.valueOf((String)jsonParserFieldValue));
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