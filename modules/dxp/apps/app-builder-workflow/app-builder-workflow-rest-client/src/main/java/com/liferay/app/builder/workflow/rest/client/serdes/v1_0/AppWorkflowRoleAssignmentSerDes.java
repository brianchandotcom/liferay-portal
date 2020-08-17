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

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowRoleAssignment;
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
public class AppWorkflowRoleAssignmentSerDes {

	public static AppWorkflowRoleAssignment toDTO(String json) {
		AppWorkflowRoleAssignmentJSONParser
			appWorkflowRoleAssignmentJSONParser =
				new AppWorkflowRoleAssignmentJSONParser();

		return appWorkflowRoleAssignmentJSONParser.parseToDTO(json);
	}

	public static AppWorkflowRoleAssignment[] toDTOs(String json) {
		AppWorkflowRoleAssignmentJSONParser
			appWorkflowRoleAssignmentJSONParser =
				new AppWorkflowRoleAssignmentJSONParser();

		return appWorkflowRoleAssignmentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		AppWorkflowRoleAssignment appWorkflowRoleAssignment) {

		if (appWorkflowRoleAssignment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflowRoleAssignment.getRoleId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleId\": ");

			sb.append(appWorkflowRoleAssignment.getRoleId());
		}

		if (appWorkflowRoleAssignment.getRoleName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleName\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowRoleAssignment.getRoleName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowRoleAssignmentJSONParser
			appWorkflowRoleAssignmentJSONParser =
				new AppWorkflowRoleAssignmentJSONParser();

		return appWorkflowRoleAssignmentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AppWorkflowRoleAssignment appWorkflowRoleAssignment) {

		if (appWorkflowRoleAssignment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflowRoleAssignment.getRoleId() == null) {
			map.put("roleId", null);
		}
		else {
			map.put(
				"roleId",
				String.valueOf(appWorkflowRoleAssignment.getRoleId()));
		}

		if (appWorkflowRoleAssignment.getRoleName() == null) {
			map.put("roleName", null);
		}
		else {
			map.put(
				"roleName",
				String.valueOf(appWorkflowRoleAssignment.getRoleName()));
		}

		return map;
	}

	public static class AppWorkflowRoleAssignmentJSONParser
		extends BaseJSONParser<AppWorkflowRoleAssignment> {

		@Override
		protected AppWorkflowRoleAssignment createDTO() {
			return new AppWorkflowRoleAssignment();
		}

		@Override
		protected AppWorkflowRoleAssignment[] createDTOArray(int size) {
			return new AppWorkflowRoleAssignment[size];
		}

		@Override
		protected void setField(
			AppWorkflowRoleAssignment appWorkflowRoleAssignment,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "roleId")) {
				if (jsonParserFieldValue != null) {
					appWorkflowRoleAssignment.setRoleId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				if (jsonParserFieldValue != null) {
					appWorkflowRoleAssignment.setRoleName(
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