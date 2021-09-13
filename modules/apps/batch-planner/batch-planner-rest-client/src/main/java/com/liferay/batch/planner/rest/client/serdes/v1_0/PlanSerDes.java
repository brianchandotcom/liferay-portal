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

import com.liferay.batch.planner.rest.client.dto.v1_0.Mapping;
import com.liferay.batch.planner.rest.client.dto.v1_0.Plan;
import com.liferay.batch.planner.rest.client.dto.v1_0.Policy;
import com.liferay.batch.planner.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public class PlanSerDes {

	public static Plan toDTO(String json) {
		PlanJSONParser planJSONParser = new PlanJSONParser();

		return planJSONParser.parseToDTO(json);
	}

	public static Plan[] toDTOs(String json) {
		PlanJSONParser planJSONParser = new PlanJSONParser();

		return planJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Plan plan) {
		if (plan == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (plan.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(plan.getActive());
		}

		if (plan.getExport() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"export\": ");

			sb.append(plan.getExport());
		}

		if (plan.getExternalType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalType\": ");

			sb.append("\"");

			sb.append(_escape(plan.getExternalType()));

			sb.append("\"");
		}

		if (plan.getExternalURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalURL\": ");

			sb.append("\"");

			sb.append(_escape(plan.getExternalURL()));

			sb.append("\"");
		}

		if (plan.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(plan.getId());
		}

		if (plan.getInternalClassName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"internalClassName\": ");

			sb.append("\"");

			sb.append(_escape(plan.getInternalClassName()));

			sb.append("\"");
		}

		if (plan.getMappings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mappings\": ");

			sb.append("[");

			for (int i = 0; i < plan.getMappings().length; i++) {
				sb.append(String.valueOf(plan.getMappings()[i]));

				if ((i + 1) < plan.getMappings().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (plan.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(plan.getName()));

			sb.append("\"");
		}

		if (plan.getPolicies() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"policies\": ");

			sb.append("[");

			for (int i = 0; i < plan.getPolicies().length; i++) {
				sb.append(String.valueOf(plan.getPolicies()[i]));

				if ((i + 1) < plan.getPolicies().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PlanJSONParser planJSONParser = new PlanJSONParser();

		return planJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Plan plan) {
		if (plan == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (plan.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(plan.getActive()));
		}

		if (plan.getExport() == null) {
			map.put("export", null);
		}
		else {
			map.put("export", String.valueOf(plan.getExport()));
		}

		if (plan.getExternalType() == null) {
			map.put("externalType", null);
		}
		else {
			map.put("externalType", String.valueOf(plan.getExternalType()));
		}

		if (plan.getExternalURL() == null) {
			map.put("externalURL", null);
		}
		else {
			map.put("externalURL", String.valueOf(plan.getExternalURL()));
		}

		if (plan.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(plan.getId()));
		}

		if (plan.getInternalClassName() == null) {
			map.put("internalClassName", null);
		}
		else {
			map.put(
				"internalClassName",
				String.valueOf(plan.getInternalClassName()));
		}

		if (plan.getMappings() == null) {
			map.put("mappings", null);
		}
		else {
			map.put("mappings", String.valueOf(plan.getMappings()));
		}

		if (plan.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(plan.getName()));
		}

		if (plan.getPolicies() == null) {
			map.put("policies", null);
		}
		else {
			map.put("policies", String.valueOf(plan.getPolicies()));
		}

		return map;
	}

	public static class PlanJSONParser extends BaseJSONParser<Plan> {

		@Override
		protected Plan createDTO() {
			return new Plan();
		}

		@Override
		protected Plan[] createDTOArray(int size) {
			return new Plan[size];
		}

		@Override
		protected void setField(
			Plan plan, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					plan.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "export")) {
				if (jsonParserFieldValue != null) {
					plan.setExport((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "externalType")) {
				if (jsonParserFieldValue != null) {
					plan.setExternalType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "externalURL")) {
				if (jsonParserFieldValue != null) {
					plan.setExternalURL((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					plan.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "internalClassName")) {
				if (jsonParserFieldValue != null) {
					plan.setInternalClassName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "mappings")) {
				if (jsonParserFieldValue != null) {
					plan.setMappings(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> MappingSerDes.toDTO((String)object)
						).toArray(
							size -> new Mapping[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					plan.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "policies")) {
				if (jsonParserFieldValue != null) {
					plan.setPolicies(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PolicySerDes.toDTO((String)object)
						).toArray(
							size -> new Policy[size]
						));
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