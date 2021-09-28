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

import com.liferay.search.experiences.rest.client.dto.v1_0.Clause;
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
public class ClauseSerDes {

	public static Clause toDTO(String json) {
		ClauseJSONParser clauseJSONParser = new ClauseJSONParser();

		return clauseJSONParser.parseToDTO(json);
	}

	public static Clause[] toDTOs(String json) {
		ClauseJSONParser clauseJSONParser = new ClauseJSONParser();

		return clauseJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Clause clause) {
		if (clause == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (clause.getContext() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"context\": ");

			sb.append("\"");

			sb.append(_escape(clause.getContext()));

			sb.append("\"");
		}

		if (clause.getOccur() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"occur\": ");

			sb.append("\"");

			sb.append(_escape(clause.getOccur()));

			sb.append("\"");
		}

		if (clause.getQuery() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"query\": ");

			sb.append("\"");

			sb.append(_escape(clause.getQuery()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ClauseJSONParser clauseJSONParser = new ClauseJSONParser();

		return clauseJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Clause clause) {
		if (clause == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (clause.getContext() == null) {
			map.put("context", null);
		}
		else {
			map.put("context", String.valueOf(clause.getContext()));
		}

		if (clause.getOccur() == null) {
			map.put("occur", null);
		}
		else {
			map.put("occur", String.valueOf(clause.getOccur()));
		}

		if (clause.getQuery() == null) {
			map.put("query", null);
		}
		else {
			map.put("query", String.valueOf(clause.getQuery()));
		}

		return map;
	}

	public static class ClauseJSONParser extends BaseJSONParser<Clause> {

		@Override
		protected Clause createDTO() {
			return new Clause();
		}

		@Override
		protected Clause[] createDTOArray(int size) {
			return new Clause[size];
		}

		@Override
		protected void setField(
			Clause clause, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "context")) {
				if (jsonParserFieldValue != null) {
					clause.setContext((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "occur")) {
				if (jsonParserFieldValue != null) {
					clause.setOccur((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "query")) {
				if (jsonParserFieldValue != null) {
					clause.setQuery((Object)jsonParserFieldValue);
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