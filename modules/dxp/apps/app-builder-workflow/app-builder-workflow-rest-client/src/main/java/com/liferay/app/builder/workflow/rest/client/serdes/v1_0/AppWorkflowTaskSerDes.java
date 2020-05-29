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

		if (appWorkflowTask.getAppId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appId\": ");

			sb.append(appWorkflowTask.getAppId());
		}

		if (appWorkflowTask.getAppWorkflowActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowActions\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowTask.getAppWorkflowActions().length;
				 i++) {

				sb.append(
					String.valueOf(appWorkflowTask.getAppWorkflowActions()[i]));

				if ((i + 1) < appWorkflowTask.getAppWorkflowActions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowTask.getDataLayoutIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutIds\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowTask.getDataLayoutIds().length;
				 i++) {

				sb.append(appWorkflowTask.getDataLayoutIds()[i]);

				if ((i + 1) < appWorkflowTask.getDataLayoutIds().length) {
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

		if (appWorkflowTask.getAppId() == null) {
			map.put("appId", null);
		}
		else {
			map.put("appId", String.valueOf(appWorkflowTask.getAppId()));
		}

		if (appWorkflowTask.getAppWorkflowActions() == null) {
			map.put("appWorkflowActions", null);
		}
		else {
			map.put(
				"appWorkflowActions",
				String.valueOf(appWorkflowTask.getAppWorkflowActions()));
		}

		if (appWorkflowTask.getDataLayoutIds() == null) {
			map.put("dataLayoutIds", null);
		}
		else {
			map.put(
				"dataLayoutIds",
				String.valueOf(appWorkflowTask.getDataLayoutIds()));
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

			if (Objects.equals(jsonParserFieldName, "appId")) {
				if (jsonParserFieldValue != null) {
					appWorkflowTask.setAppId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowActions")) {

				if (jsonParserFieldValue != null) {
					appWorkflowTask.setAppWorkflowActions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AppWorkflowActionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new AppWorkflowAction[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataLayoutIds")) {
				if (jsonParserFieldValue != null) {
					appWorkflowTask.setDataLayoutIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					appWorkflowTask.setName((String)jsonParserFieldValue);
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