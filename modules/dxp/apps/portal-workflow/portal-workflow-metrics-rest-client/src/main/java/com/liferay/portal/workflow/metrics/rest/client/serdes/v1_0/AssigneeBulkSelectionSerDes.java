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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeBulkSelection;
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
public class AssigneeBulkSelectionSerDes {

	public static AssigneeBulkSelection toDTO(String json) {
		AssigneeBulkSelectionJSONParser assigneeBulkSelectionJSONParser =
			new AssigneeBulkSelectionJSONParser();

		return assigneeBulkSelectionJSONParser.parseToDTO(json);
	}

	public static AssigneeBulkSelection[] toDTOs(String json) {
		AssigneeBulkSelectionJSONParser assigneeBulkSelectionJSONParser =
			new AssigneeBulkSelectionJSONParser();

		return assigneeBulkSelectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AssigneeBulkSelection assigneeBulkSelection) {
		if (assigneeBulkSelection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (assigneeBulkSelection.getCompleted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"completed\": ");

			sb.append(assigneeBulkSelection.getCompleted());
		}

		if (assigneeBulkSelection.getDateEnd() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateEnd\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					assigneeBulkSelection.getDateEnd()));

			sb.append("\"");
		}

		if (assigneeBulkSelection.getDateStart() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateStart\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					assigneeBulkSelection.getDateStart()));

			sb.append("\"");
		}

		if (assigneeBulkSelection.getInstanceIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instanceIds\": ");

			sb.append("[");

			for (int i = 0; i < assigneeBulkSelection.getInstanceIds().length;
				 i++) {

				sb.append(assigneeBulkSelection.getInstanceIds()[i]);

				if ((i + 1) < assigneeBulkSelection.getInstanceIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (assigneeBulkSelection.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("\"");

			sb.append(_escape(assigneeBulkSelection.getKeywords()));

			sb.append("\"");
		}

		if (assigneeBulkSelection.getRoleIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleIds\": ");

			sb.append("[");

			for (int i = 0; i < assigneeBulkSelection.getRoleIds().length;
				 i++) {

				sb.append(assigneeBulkSelection.getRoleIds()[i]);

				if ((i + 1) < assigneeBulkSelection.getRoleIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (assigneeBulkSelection.getTaskKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskKeys\": ");

			sb.append("[");

			for (int i = 0; i < assigneeBulkSelection.getTaskKeys().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(assigneeBulkSelection.getTaskKeys()[i]));

				sb.append("\"");

				if ((i + 1) < assigneeBulkSelection.getTaskKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AssigneeBulkSelectionJSONParser assigneeBulkSelectionJSONParser =
			new AssigneeBulkSelectionJSONParser();

		return assigneeBulkSelectionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AssigneeBulkSelection assigneeBulkSelection) {

		if (assigneeBulkSelection == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (assigneeBulkSelection.getCompleted() == null) {
			map.put("completed", null);
		}
		else {
			map.put(
				"completed",
				String.valueOf(assigneeBulkSelection.getCompleted()));
		}

		map.put(
			"dateEnd",
			liferayToJSONDateFormat.format(assigneeBulkSelection.getDateEnd()));

		map.put(
			"dateStart",
			liferayToJSONDateFormat.format(
				assigneeBulkSelection.getDateStart()));

		if (assigneeBulkSelection.getInstanceIds() == null) {
			map.put("instanceIds", null);
		}
		else {
			map.put(
				"instanceIds",
				String.valueOf(assigneeBulkSelection.getInstanceIds()));
		}

		if (assigneeBulkSelection.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put(
				"keywords",
				String.valueOf(assigneeBulkSelection.getKeywords()));
		}

		if (assigneeBulkSelection.getRoleIds() == null) {
			map.put("roleIds", null);
		}
		else {
			map.put(
				"roleIds", String.valueOf(assigneeBulkSelection.getRoleIds()));
		}

		if (assigneeBulkSelection.getTaskKeys() == null) {
			map.put("taskKeys", null);
		}
		else {
			map.put(
				"taskKeys",
				String.valueOf(assigneeBulkSelection.getTaskKeys()));
		}

		return map;
	}

	public static class AssigneeBulkSelectionJSONParser
		extends BaseJSONParser<AssigneeBulkSelection> {

		@Override
		protected AssigneeBulkSelection createDTO() {
			return new AssigneeBulkSelection();
		}

		@Override
		protected AssigneeBulkSelection[] createDTOArray(int size) {
			return new AssigneeBulkSelection[size];
		}

		@Override
		protected void setField(
			AssigneeBulkSelection assigneeBulkSelection,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "completed")) {
				if (jsonParserFieldValue != null) {
					assigneeBulkSelection.setCompleted(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateEnd")) {
				if (jsonParserFieldValue != null) {
					assigneeBulkSelection.setDateEnd(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateStart")) {
				if (jsonParserFieldValue != null) {
					assigneeBulkSelection.setDateStart(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "instanceIds")) {
				if (jsonParserFieldValue != null) {
					assigneeBulkSelection.setInstanceIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					assigneeBulkSelection.setKeywords(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleIds")) {
				if (jsonParserFieldValue != null) {
					assigneeBulkSelection.setRoleIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "taskKeys")) {
				if (jsonParserFieldValue != null) {
					assigneeBulkSelection.setTaskKeys(
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