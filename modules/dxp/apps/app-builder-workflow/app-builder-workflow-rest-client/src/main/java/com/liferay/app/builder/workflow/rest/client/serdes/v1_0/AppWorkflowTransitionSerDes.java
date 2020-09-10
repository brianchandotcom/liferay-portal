/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.rest.client.serdes.v1_0;

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTransition;
import com.liferay.app.builder.workflow.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowTransitionSerDes {

	public static AppWorkflowTransition toDTO(String json) {
		AppWorkflowTransitionJSONParser appWorkflowTransitionJSONParser =
			new AppWorkflowTransitionJSONParser();

		return appWorkflowTransitionJSONParser.parseToDTO(json);
	}

	public static AppWorkflowTransition[] toDTOs(String json) {
		AppWorkflowTransitionJSONParser appWorkflowTransitionJSONParser =
			new AppWorkflowTransitionJSONParser();

		return appWorkflowTransitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppWorkflowTransition appWorkflowTransition) {
		if (appWorkflowTransition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflowTransition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowTransition.getName()));

			sb.append("\"");
		}

		if (appWorkflowTransition.getPrimary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primary\": ");

			sb.append(appWorkflowTransition.getPrimary());
		}

		if (appWorkflowTransition.getTransitionTo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transitionTo\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowTransition.getTransitionTo()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowTransitionJSONParser appWorkflowTransitionJSONParser =
			new AppWorkflowTransitionJSONParser();

		return appWorkflowTransitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AppWorkflowTransition appWorkflowTransition) {

		if (appWorkflowTransition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflowTransition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(appWorkflowTransition.getName()));
		}

		if (appWorkflowTransition.getPrimary() == null) {
			map.put("primary", null);
		}
		else {
			map.put(
				"primary", String.valueOf(appWorkflowTransition.getPrimary()));
		}

		if (appWorkflowTransition.getTransitionTo() == null) {
			map.put("transitionTo", null);
		}
		else {
			map.put(
				"transitionTo",
				String.valueOf(appWorkflowTransition.getTransitionTo()));
		}

		return map;
	}

	public static class AppWorkflowTransitionJSONParser
		extends BaseJSONParser<AppWorkflowTransition> {

		@Override
		protected AppWorkflowTransition createDTO() {
			return new AppWorkflowTransition();
		}

		@Override
		protected AppWorkflowTransition[] createDTOArray(int size) {
			return new AppWorkflowTransition[size];
		}

		@Override
		protected void setField(
			AppWorkflowTransition appWorkflowTransition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					appWorkflowTransition.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primary")) {
				if (jsonParserFieldValue != null) {
					appWorkflowTransition.setPrimary(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "transitionTo")) {
				if (jsonParserFieldValue != null) {
					appWorkflowTransition.setTransitionTo(
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