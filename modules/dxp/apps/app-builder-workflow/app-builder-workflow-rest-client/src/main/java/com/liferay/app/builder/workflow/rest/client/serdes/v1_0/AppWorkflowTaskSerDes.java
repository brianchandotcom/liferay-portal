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

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowDataLayoutLink;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowRoleAssignment;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTransition;
import com.liferay.app.builder.workflow.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowTaskSerDes {

	public static AppWorkflowTask toDTO(String json) {
		AppWorkflowTaskJSONParser appWorkflowTaskJSONParser =
			new AppWorkflowTaskJSONParser();

		return appWorkflowTaskJSONParser.parseToDTO(json);
	}

	public static AppWorkflowTask[] toDTOs(String json) {
		AppWorkflowTaskJSONParser appWorkflowTaskJSONParser =
			new AppWorkflowTaskJSONParser();

		return appWorkflowTaskJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppWorkflowTask appWorkflowTask) {
		if (appWorkflowTask == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflowTask.getAppWorkflowDataLayoutLinks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowDataLayoutLinks\": ");

			sb.append("[");

			for (int i = 0;
				 i < appWorkflowTask.getAppWorkflowDataLayoutLinks().length;
				 i++) {

				sb.append(
					String.valueOf(
						appWorkflowTask.getAppWorkflowDataLayoutLinks()[i]));

				if ((i + 1) <
						appWorkflowTask.
							getAppWorkflowDataLayoutLinks().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowTask.getAppWorkflowRoleAssignments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowRoleAssignments\": ");

			sb.append("[");

			for (int i = 0;
				 i < appWorkflowTask.getAppWorkflowRoleAssignments().length;
				 i++) {

				sb.append(
					String.valueOf(
						appWorkflowTask.getAppWorkflowRoleAssignments()[i]));

				if ((i + 1) <
						appWorkflowTask.
							getAppWorkflowRoleAssignments().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowTask.getAppWorkflowTransitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowTransitions\": ");

			sb.append("[");

			for (int i = 0;
				 i < appWorkflowTask.getAppWorkflowTransitions().length; i++) {

				sb.append(
					String.valueOf(
						appWorkflowTask.getAppWorkflowTransitions()[i]));

				if ((i + 1) <
						appWorkflowTask.getAppWorkflowTransitions().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowTask.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowTask.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowTaskJSONParser appWorkflowTaskJSONParser =
			new AppWorkflowTaskJSONParser();

		return appWorkflowTaskJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AppWorkflowTask appWorkflowTask) {
		if (appWorkflowTask == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflowTask.getAppWorkflowDataLayoutLinks() == null) {
			map.put("appWorkflowDataLayoutLinks", null);
		}
		else {
			map.put(
				"appWorkflowDataLayoutLinks",
				String.valueOf(
					appWorkflowTask.getAppWorkflowDataLayoutLinks()));
		}

		if (appWorkflowTask.getAppWorkflowRoleAssignments() == null) {
			map.put("appWorkflowRoleAssignments", null);
		}
		else {
			map.put(
				"appWorkflowRoleAssignments",
				String.valueOf(
					appWorkflowTask.getAppWorkflowRoleAssignments()));
		}

		if (appWorkflowTask.getAppWorkflowTransitions() == null) {
			map.put("appWorkflowTransitions", null);
		}
		else {
			map.put(
				"appWorkflowTransitions",
				String.valueOf(appWorkflowTask.getAppWorkflowTransitions()));
		}

		if (appWorkflowTask.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(appWorkflowTask.getName()));
		}

		return map;
	}

	public static class AppWorkflowTaskJSONParser
		extends BaseJSONParser<AppWorkflowTask> {

		@Override
		protected AppWorkflowTask createDTO() {
			return new AppWorkflowTask();
		}

		@Override
		protected AppWorkflowTask[] createDTOArray(int size) {
			return new AppWorkflowTask[size];
		}

		@Override
		protected void setField(
			AppWorkflowTask appWorkflowTask, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "appWorkflowDataLayoutLinks")) {

				if (jsonParserFieldValue != null) {
					appWorkflowTask.setAppWorkflowDataLayoutLinks(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AppWorkflowDataLayoutLinkSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new AppWorkflowDataLayoutLink[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowRoleAssignments")) {

				if (jsonParserFieldValue != null) {
					appWorkflowTask.setAppWorkflowRoleAssignments(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AppWorkflowRoleAssignmentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new AppWorkflowRoleAssignment[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowTransitions")) {

				if (jsonParserFieldValue != null) {
					appWorkflowTask.setAppWorkflowTransitions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AppWorkflowTransitionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new AppWorkflowTransition[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					appWorkflowTask.setName((String)jsonParserFieldValue);
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