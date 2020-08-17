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

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowState;
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
public class AppWorkflowStateSerDes {

	public static AppWorkflowState toDTO(String json) {
		AppWorkflowStateJSONParser appWorkflowStateJSONParser =
			new AppWorkflowStateJSONParser();

		return appWorkflowStateJSONParser.parseToDTO(json);
	}

	public static AppWorkflowState[] toDTOs(String json) {
		AppWorkflowStateJSONParser appWorkflowStateJSONParser =
			new AppWorkflowStateJSONParser();

		return appWorkflowStateJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppWorkflowState appWorkflowState) {
		if (appWorkflowState == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflowState.getAppWorkflowTransitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowTransitions\": ");

			sb.append("[");

			for (int i = 0;
				 i < appWorkflowState.getAppWorkflowTransitions().length; i++) {

				sb.append(
					String.valueOf(
						appWorkflowState.getAppWorkflowTransitions()[i]));

				if ((i + 1) <
						appWorkflowState.getAppWorkflowTransitions().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowState.getInitial() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"initial\": ");

			sb.append(appWorkflowState.getInitial());
		}

		if (appWorkflowState.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowState.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowStateJSONParser appWorkflowStateJSONParser =
			new AppWorkflowStateJSONParser();

		return appWorkflowStateJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AppWorkflowState appWorkflowState) {
		if (appWorkflowState == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflowState.getAppWorkflowTransitions() == null) {
			map.put("appWorkflowTransitions", null);
		}
		else {
			map.put(
				"appWorkflowTransitions",
				String.valueOf(appWorkflowState.getAppWorkflowTransitions()));
		}

		if (appWorkflowState.getInitial() == null) {
			map.put("initial", null);
		}
		else {
			map.put("initial", String.valueOf(appWorkflowState.getInitial()));
		}

		if (appWorkflowState.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(appWorkflowState.getName()));
		}

		return map;
	}

	public static class AppWorkflowStateJSONParser
		extends BaseJSONParser<AppWorkflowState> {

		@Override
		protected AppWorkflowState createDTO() {
			return new AppWorkflowState();
		}

		@Override
		protected AppWorkflowState[] createDTOArray(int size) {
			return new AppWorkflowState[size];
		}

		@Override
		protected void setField(
			AppWorkflowState appWorkflowState, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "appWorkflowTransitions")) {
				if (jsonParserFieldValue != null) {
					appWorkflowState.setAppWorkflowTransitions(
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
			else if (Objects.equals(jsonParserFieldName, "initial")) {
				if (jsonParserFieldValue != null) {
					appWorkflowState.setInitial((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					appWorkflowState.setName((String)jsonParserFieldValue);
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