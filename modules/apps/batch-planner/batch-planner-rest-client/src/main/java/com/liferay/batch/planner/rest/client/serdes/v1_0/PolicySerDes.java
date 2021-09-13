/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.planner.rest.client.serdes.v1_0;

import com.liferay.batch.planner.rest.client.dto.v1_0.Policy;
import com.liferay.batch.planner.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public class PolicySerDes {

	public static Policy toDTO(String json) {
		PolicyJSONParser policyJSONParser = new PolicyJSONParser();

		return policyJSONParser.parseToDTO(json);
	}

	public static Policy[] toDTOs(String json) {
		PolicyJSONParser policyJSONParser = new PolicyJSONParser();

		return policyJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Policy policy) {
		if (policy == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (policy.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(policy.getId());
		}

		if (policy.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(policy.getName()));

			sb.append("\"");
		}

		if (policy.getPlanId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"planId\": ");

			sb.append(policy.getPlanId());
		}

		if (policy.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(policy.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PolicyJSONParser policyJSONParser = new PolicyJSONParser();

		return policyJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Policy policy) {
		if (policy == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (policy.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(policy.getId()));
		}

		if (policy.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(policy.getName()));
		}

		if (policy.getPlanId() == null) {
			map.put("planId", null);
		}
		else {
			map.put("planId", String.valueOf(policy.getPlanId()));
		}

		if (policy.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(policy.getValue()));
		}

		return map;
	}

	public static class PolicyJSONParser extends BaseJSONParser<Policy> {

		@Override
		protected Policy createDTO() {
			return new Policy();
		}

		@Override
		protected Policy[] createDTOArray(int size) {
			return new Policy[size];
		}

		@Override
		protected void setField(
			Policy policy, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					policy.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					policy.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "planId")) {
				if (jsonParserFieldValue != null) {
					policy.setPlanId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					policy.setValue((String)jsonParserFieldValue);
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