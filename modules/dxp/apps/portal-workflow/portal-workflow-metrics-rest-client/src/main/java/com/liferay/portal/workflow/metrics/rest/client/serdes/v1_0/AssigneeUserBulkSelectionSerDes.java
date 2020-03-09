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

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUserBulkSelection;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class AssigneeUserBulkSelectionSerDes {

	public static AssigneeUserBulkSelection toDTO(String json) {
		AssigneeUserBulkSelectionJSONParser
			assigneeUserBulkSelectionJSONParser =
				new AssigneeUserBulkSelectionJSONParser();

		return assigneeUserBulkSelectionJSONParser.parseToDTO(json);
	}

	public static AssigneeUserBulkSelection[] toDTOs(String json) {
		AssigneeUserBulkSelectionJSONParser
			assigneeUserBulkSelectionJSONParser =
				new AssigneeUserBulkSelectionJSONParser();

		return assigneeUserBulkSelectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		AssigneeUserBulkSelection assigneeUserBulkSelection) {

		if (assigneeUserBulkSelection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (assigneeUserBulkSelection.getCompleted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"completed\": ");

			sb.append(assigneeUserBulkSelection.getCompleted());
		}

		if (assigneeUserBulkSelection.getDateEnd() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateEnd\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					assigneeUserBulkSelection.getDateEnd()));

			sb.append("\"");
		}

		if (assigneeUserBulkSelection.getDateStart() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateStart\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					assigneeUserBulkSelection.getDateStart()));

			sb.append("\"");
		}

		if (assigneeUserBulkSelection.getInstanceIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instanceIds\": ");

			sb.append("[");

			for (int i = 0;
				 i < assigneeUserBulkSelection.getInstanceIds().length; i++) {

				sb.append(assigneeUserBulkSelection.getInstanceIds()[i]);

				if ((i + 1) <
						assigneeUserBulkSelection.getInstanceIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (assigneeUserBulkSelection.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("\"");

			sb.append(_escape(assigneeUserBulkSelection.getKeywords()));

			sb.append("\"");
		}

		if (assigneeUserBulkSelection.getRoleIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleIds\": ");

			sb.append("[");

			for (int i = 0; i < assigneeUserBulkSelection.getRoleIds().length;
				 i++) {

				sb.append(assigneeUserBulkSelection.getRoleIds()[i]);

				if ((i + 1) < assigneeUserBulkSelection.getRoleIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (assigneeUserBulkSelection.getTaskKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskKeys\": ");

			sb.append("[");

			for (int i = 0; i < assigneeUserBulkSelection.getTaskKeys().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(assigneeUserBulkSelection.getTaskKeys()[i]));

				sb.append("\"");

				if ((i + 1) < assigneeUserBulkSelection.getTaskKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AssigneeUserBulkSelectionJSONParser
			assigneeUserBulkSelectionJSONParser =
				new AssigneeUserBulkSelectionJSONParser();

		return assigneeUserBulkSelectionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AssigneeUserBulkSelection assigneeUserBulkSelection) {

		if (assigneeUserBulkSelection == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (assigneeUserBulkSelection.getCompleted() == null) {
			map.put("completed", null);
		}
		else {
			map.put(
				"completed",
				String.valueOf(assigneeUserBulkSelection.getCompleted()));
		}

		map.put(
			"dateEnd",
			liferayToJSONDateFormat.format(
				assigneeUserBulkSelection.getDateEnd()));

		map.put(
			"dateStart",
			liferayToJSONDateFormat.format(
				assigneeUserBulkSelection.getDateStart()));

		if (assigneeUserBulkSelection.getInstanceIds() == null) {
			map.put("instanceIds", null);
		}
		else {
			map.put(
				"instanceIds",
				String.valueOf(assigneeUserBulkSelection.getInstanceIds()));
		}

		if (assigneeUserBulkSelection.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put(
				"keywords",
				String.valueOf(assigneeUserBulkSelection.getKeywords()));
		}

		if (assigneeUserBulkSelection.getRoleIds() == null) {
			map.put("roleIds", null);
		}
		else {
			map.put(
				"roleIds",
				String.valueOf(assigneeUserBulkSelection.getRoleIds()));
		}

		if (assigneeUserBulkSelection.getTaskKeys() == null) {
			map.put("taskKeys", null);
		}
		else {
			map.put(
				"taskKeys",
				String.valueOf(assigneeUserBulkSelection.getTaskKeys()));
		}

		return map;
	}

	public static class AssigneeUserBulkSelectionJSONParser
		extends BaseJSONParser<AssigneeUserBulkSelection> {

		@Override
		protected AssigneeUserBulkSelection createDTO() {
			return new AssigneeUserBulkSelection();
		}

		@Override
		protected AssigneeUserBulkSelection[] createDTOArray(int size) {
			return new AssigneeUserBulkSelection[size];
		}

		@Override
		protected void setField(
			AssigneeUserBulkSelection assigneeUserBulkSelection,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "completed")) {
				if (jsonParserFieldValue != null) {
					assigneeUserBulkSelection.setCompleted(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateEnd")) {
				if (jsonParserFieldValue != null) {
					assigneeUserBulkSelection.setDateEnd(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateStart")) {
				if (jsonParserFieldValue != null) {
					assigneeUserBulkSelection.setDateStart(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "instanceIds")) {
				if (jsonParserFieldValue != null) {
					assigneeUserBulkSelection.setInstanceIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					assigneeUserBulkSelection.setKeywords(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleIds")) {
				if (jsonParserFieldValue != null) {
					assigneeUserBulkSelection.setRoleIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "taskKeys")) {
				if (jsonParserFieldValue != null) {
					assigneeUserBulkSelection.setTaskKeys(
						toStrings((Object[])jsonParserFieldValue));
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