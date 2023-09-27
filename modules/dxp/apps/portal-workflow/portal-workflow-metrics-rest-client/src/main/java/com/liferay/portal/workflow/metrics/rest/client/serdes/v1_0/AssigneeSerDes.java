/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Assignee;
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
public class AssigneeSerDes {

	public static Assignee toDTO(String json) {
		AssigneeJSONParser assigneeJSONParser = new AssigneeJSONParser();

		return assigneeJSONParser.parseToDTO(json);
	}

	public static Assignee[] toDTOs(String json) {
		AssigneeJSONParser assigneeJSONParser = new AssigneeJSONParser();

		return assigneeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Assignee assignee) {
		if (assignee == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (assignee.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(assignee.getId());
		}

		if (assignee.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append("\"");

			sb.append(_escape(assignee.getImage()));

			sb.append("\"");
		}

		if (assignee.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(assignee.getName()));

			sb.append("\"");
		}

		if (assignee.getReviewer() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reviewer\": ");

			sb.append(assignee.getReviewer());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AssigneeJSONParser assigneeJSONParser = new AssigneeJSONParser();

		return assigneeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Assignee assignee) {
		if (assignee == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (assignee.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(assignee.getId()));
		}

		if (assignee.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(assignee.getImage()));
		}

		if (assignee.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(assignee.getName()));
		}

		if (assignee.getReviewer() == null) {
			map.put("reviewer", null);
		}
		else {
			map.put("reviewer", String.valueOf(assignee.getReviewer()));
		}

		return map;
	}

	public static class AssigneeJSONParser extends BaseJSONParser<Assignee> {

		@Override
		protected Assignee createDTO() {
			return new Assignee();
		}

		@Override
		protected Assignee[] createDTOArray(int size) {
			return new Assignee[size];
		}

		@Override
		protected void setField(
			Assignee assignee, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					assignee.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					assignee.setImage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					assignee.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "reviewer")) {
				if (jsonParserFieldValue != null) {
					assignee.setReviewer((Boolean)jsonParserFieldValue);
				}
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
			sb.append("\": ");

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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}