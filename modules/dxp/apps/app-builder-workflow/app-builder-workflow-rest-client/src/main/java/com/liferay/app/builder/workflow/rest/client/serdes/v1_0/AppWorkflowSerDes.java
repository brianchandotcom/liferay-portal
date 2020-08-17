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

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowState;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
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
public class AppWorkflowSerDes {

	public static AppWorkflow toDTO(String json) {
		AppWorkflowJSONParser appWorkflowJSONParser =
			new AppWorkflowJSONParser();

		return appWorkflowJSONParser.parseToDTO(json);
	}

	public static AppWorkflow[] toDTOs(String json) {
		AppWorkflowJSONParser appWorkflowJSONParser =
			new AppWorkflowJSONParser();

		return appWorkflowJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppWorkflow appWorkflow) {
		if (appWorkflow == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflow.getAppId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appId\": ");

			sb.append(appWorkflow.getAppId());
		}

		if (appWorkflow.getAppVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appVersion\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflow.getAppVersion()));

			sb.append("\"");
		}

		if (appWorkflow.getAppWorkflowDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowDefinitionId\": ");

			sb.append(appWorkflow.getAppWorkflowDefinitionId());
		}

		if (appWorkflow.getAppWorkflowStates() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowStates\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflow.getAppWorkflowStates().length;
				 i++) {

				sb.append(
					String.valueOf(appWorkflow.getAppWorkflowStates()[i]));

				if ((i + 1) < appWorkflow.getAppWorkflowStates().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflow.getAppWorkflowTasks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowTasks\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflow.getAppWorkflowTasks().length; i++) {
				sb.append(String.valueOf(appWorkflow.getAppWorkflowTasks()[i]));

				if ((i + 1) < appWorkflow.getAppWorkflowTasks().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowJSONParser appWorkflowJSONParser =
			new AppWorkflowJSONParser();

		return appWorkflowJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AppWorkflow appWorkflow) {
		if (appWorkflow == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflow.getAppId() == null) {
			map.put("appId", null);
		}
		else {
			map.put("appId", String.valueOf(appWorkflow.getAppId()));
		}

		if (appWorkflow.getAppVersion() == null) {
			map.put("appVersion", null);
		}
		else {
			map.put("appVersion", String.valueOf(appWorkflow.getAppVersion()));
		}

		if (appWorkflow.getAppWorkflowDefinitionId() == null) {
			map.put("appWorkflowDefinitionId", null);
		}
		else {
			map.put(
				"appWorkflowDefinitionId",
				String.valueOf(appWorkflow.getAppWorkflowDefinitionId()));
		}

		if (appWorkflow.getAppWorkflowStates() == null) {
			map.put("appWorkflowStates", null);
		}
		else {
			map.put(
				"appWorkflowStates",
				String.valueOf(appWorkflow.getAppWorkflowStates()));
		}

		if (appWorkflow.getAppWorkflowTasks() == null) {
			map.put("appWorkflowTasks", null);
		}
		else {
			map.put(
				"appWorkflowTasks",
				String.valueOf(appWorkflow.getAppWorkflowTasks()));
		}

		return map;
	}

	public static class AppWorkflowJSONParser
		extends BaseJSONParser<AppWorkflow> {

		@Override
		protected AppWorkflow createDTO() {
			return new AppWorkflow();
		}

		@Override
		protected AppWorkflow[] createDTOArray(int size) {
			return new AppWorkflow[size];
		}

		@Override
		protected void setField(
			AppWorkflow appWorkflow, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "appId")) {
				if (jsonParserFieldValue != null) {
					appWorkflow.setAppId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "appVersion")) {
				if (jsonParserFieldValue != null) {
					appWorkflow.setAppVersion((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowDefinitionId")) {

				if (jsonParserFieldValue != null) {
					appWorkflow.setAppWorkflowDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "appWorkflowStates")) {
				if (jsonParserFieldValue != null) {
					appWorkflow.setAppWorkflowStates(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AppWorkflowStateSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new AppWorkflowState[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "appWorkflowTasks")) {
				if (jsonParserFieldValue != null) {
					appWorkflow.setAppWorkflowTasks(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AppWorkflowTaskSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new AppWorkflowTask[size]
						));
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