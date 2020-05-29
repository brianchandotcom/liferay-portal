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

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowAction;
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
public class AppWorkflowActionSerDes {

	public static AppWorkflowAction toDTO(String json) {
		AppWorkflowActionJSONParser appWorkflowActionJSONParser =
			new AppWorkflowActionJSONParser();

		return appWorkflowActionJSONParser.parseToDTO(json);
	}

	public static AppWorkflowAction[] toDTOs(String json) {
		AppWorkflowActionJSONParser appWorkflowActionJSONParser =
			new AppWorkflowActionJSONParser();

		return appWorkflowActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppWorkflowAction appWorkflowAction) {
		if (appWorkflowAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflowAction.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowAction.getName()));

			sb.append("\"");
		}

		if (appWorkflowAction.getPrimary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primary\": ");

			sb.append(appWorkflowAction.getPrimary());
		}

		if (appWorkflowAction.getTransitionTo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transitionTo\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowAction.getTransitionTo()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowActionJSONParser appWorkflowActionJSONParser =
			new AppWorkflowActionJSONParser();

		return appWorkflowActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AppWorkflowAction appWorkflowAction) {

		if (appWorkflowAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflowAction.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(appWorkflowAction.getName()));
		}

		if (appWorkflowAction.getPrimary() == null) {
			map.put("primary", null);
		}
		else {
			map.put("primary", String.valueOf(appWorkflowAction.getPrimary()));
		}

		if (appWorkflowAction.getTransitionTo() == null) {
			map.put("transitionTo", null);
		}
		else {
			map.put(
				"transitionTo",
				String.valueOf(appWorkflowAction.getTransitionTo()));
		}

		return map;
	}

	public static class AppWorkflowActionJSONParser
		extends BaseJSONParser<AppWorkflowAction> {

		@Override
		protected AppWorkflowAction createDTO() {
			return new AppWorkflowAction();
		}

		@Override
		protected AppWorkflowAction[] createDTOArray(int size) {
			return new AppWorkflowAction[size];
		}

		@Override
		protected void setField(
			AppWorkflowAction appWorkflowAction, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					appWorkflowAction.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primary")) {
				if (jsonParserFieldValue != null) {
					appWorkflowAction.setPrimary((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "transitionTo")) {
				if (jsonParserFieldValue != null) {
					appWorkflowAction.setTransitionTo(
						(String)jsonParserFieldValue);
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