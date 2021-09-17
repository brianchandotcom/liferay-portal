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

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.Framework_configuration;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Framework_configurationSerDes {

	public static Framework_configuration toDTO(String json) {
		Framework_configurationJSONParser framework_configurationJSONParser =
			new Framework_configurationJSONParser();

		return framework_configurationJSONParser.parseToDTO(json);
	}

	public static Framework_configuration[] toDTOs(String json) {
		Framework_configurationJSONParser framework_configurationJSONParser =
			new Framework_configurationJSONParser();

		return framework_configurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		Framework_configuration framework_configuration) {

		if (framework_configuration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (framework_configuration.getApply_indexer_clauses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"apply_indexer_clauses\": ");

			sb.append(framework_configuration.getApply_indexer_clauses());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		Framework_configurationJSONParser framework_configurationJSONParser =
			new Framework_configurationJSONParser();

		return framework_configurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		Framework_configuration framework_configuration) {

		if (framework_configuration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (framework_configuration.getApply_indexer_clauses() == null) {
			map.put("apply_indexer_clauses", null);
		}
		else {
			map.put(
				"apply_indexer_clauses",
				String.valueOf(
					framework_configuration.getApply_indexer_clauses()));
		}

		return map;
	}

	public static class Framework_configurationJSONParser
		extends BaseJSONParser<Framework_configuration> {

		@Override
		protected Framework_configuration createDTO() {
			return new Framework_configuration();
		}

		@Override
		protected Framework_configuration[] createDTOArray(int size) {
			return new Framework_configuration[size];
		}

		@Override
		protected void setField(
			Framework_configuration framework_configuration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "apply_indexer_clauses")) {
				if (jsonParserFieldValue != null) {
					framework_configuration.setApply_indexer_clauses(
						(Boolean)jsonParserFieldValue);
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