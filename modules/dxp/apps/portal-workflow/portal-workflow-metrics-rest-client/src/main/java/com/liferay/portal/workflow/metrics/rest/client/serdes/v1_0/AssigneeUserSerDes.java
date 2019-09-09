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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUser;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

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
public class AssigneeUserSerDes {

	public static AssigneeUser toDTO(String json) {
		AssigneeUserJSONParser assigneeUserJSONParser =
			new AssigneeUserJSONParser();

		return assigneeUserJSONParser.parseToDTO(json);
	}

	public static AssigneeUser[] toDTOs(String json) {
		AssigneeUserJSONParser assigneeUserJSONParser =
			new AssigneeUserJSONParser();

		return assigneeUserJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AssigneeUser assigneeUser) {
		if (assigneeUser == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (assigneeUser.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(assigneeUser.getId());
		}

		if (assigneeUser.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append("\"");

			sb.append(_escape(assigneeUser.getImage()));

			sb.append("\"");
		}

		if (assigneeUser.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(assigneeUser.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AssigneeUserJSONParser assigneeUserJSONParser =
			new AssigneeUserJSONParser();

		return assigneeUserJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AssigneeUser assigneeUser) {
		if (assigneeUser == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (assigneeUser.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(assigneeUser.getId()));
		}

		if (assigneeUser.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(assigneeUser.getImage()));
		}

		if (assigneeUser.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(assigneeUser.getName()));
		}

		return map;
	}

	public static class AssigneeUserJSONParser
		extends BaseJSONParser<AssigneeUser> {

		@Override
		protected AssigneeUser createDTO() {
			return new AssigneeUser();
		}

		@Override
		protected AssigneeUser[] createDTOArray(int size) {
			return new AssigneeUser[size];
		}

		@Override
		protected void setField(
			AssigneeUser assigneeUser, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					assigneeUser.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					assigneeUser.setImage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					assigneeUser.setName((String)jsonParserFieldValue);
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

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}