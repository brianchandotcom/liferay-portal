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
import com.liferay.search.experiences.rest.client.dto.v1_0.QueryEntry;
import com.liferay.search.experiences.rest.client.dto.v1_0.Rescore;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class QueryEntrySerDes {

	public static QueryEntry toDTO(String json) {
		QueryEntryJSONParser queryEntryJSONParser = new QueryEntryJSONParser();

		return queryEntryJSONParser.parseToDTO(json);
	}

	public static QueryEntry[] toDTOs(String json) {
		QueryEntryJSONParser queryEntryJSONParser = new QueryEntryJSONParser();

		return queryEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(QueryEntry queryEntry) {
		if (queryEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (queryEntry.getClauses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauses\": ");

			sb.append("[");

			for (int i = 0; i < queryEntry.getClauses().length; i++) {
				sb.append(String.valueOf(queryEntry.getClauses()[i]));

				if ((i + 1) < queryEntry.getClauses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (queryEntry.getCondition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"condition\": ");

			sb.append(String.valueOf(queryEntry.getCondition()));
		}

		if (queryEntry.getEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enabled\": ");

			sb.append(queryEntry.getEnabled());
		}

		if (queryEntry.getPostFilterClauses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postFilterClauses\": ");

			sb.append("[");

			for (int i = 0; i < queryEntry.getPostFilterClauses().length; i++) {
				sb.append(String.valueOf(queryEntry.getPostFilterClauses()[i]));

				if ((i + 1) < queryEntry.getPostFilterClauses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (queryEntry.getRescores() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rescores\": ");

			sb.append("[");

			for (int i = 0; i < queryEntry.getRescores().length; i++) {
				sb.append(String.valueOf(queryEntry.getRescores()[i]));

				if ((i + 1) < queryEntry.getRescores().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		QueryEntryJSONParser queryEntryJSONParser = new QueryEntryJSONParser();

		return queryEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(QueryEntry queryEntry) {
		if (queryEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (queryEntry.getClauses() == null) {
			map.put("clauses", null);
		}
		else {
			map.put("clauses", String.valueOf(queryEntry.getClauses()));
		}

		if (queryEntry.getCondition() == null) {
			map.put("condition", null);
		}
		else {
			map.put("condition", String.valueOf(queryEntry.getCondition()));
		}

		if (queryEntry.getEnabled() == null) {
			map.put("enabled", null);
		}
		else {
			map.put("enabled", String.valueOf(queryEntry.getEnabled()));
		}

		if (queryEntry.getPostFilterClauses() == null) {
			map.put("postFilterClauses", null);
		}
		else {
			map.put(
				"postFilterClauses",
				String.valueOf(queryEntry.getPostFilterClauses()));
		}

		if (queryEntry.getRescores() == null) {
			map.put("rescores", null);
		}
		else {
			map.put("rescores", String.valueOf(queryEntry.getRescores()));
		}

		return map;
	}

	public static class QueryEntryJSONParser
		extends BaseJSONParser<QueryEntry> {

		@Override
		protected QueryEntry createDTO() {
			return new QueryEntry();
		}

		@Override
		protected QueryEntry[] createDTOArray(int size) {
			return new QueryEntry[size];
		}

		@Override
		protected void setField(
			QueryEntry queryEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "clauses")) {
				if (jsonParserFieldValue != null) {
					queryEntry.setClauses(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ClauseSerDes.toDTO((String)object)
						).toArray(
							size -> new Clause[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "condition")) {
				if (jsonParserFieldValue != null) {
					queryEntry.setCondition(
						ConditionSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "enabled")) {
				if (jsonParserFieldValue != null) {
					queryEntry.setEnabled((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "postFilterClauses")) {
				if (jsonParserFieldValue != null) {
					queryEntry.setPostFilterClauses(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ClauseSerDes.toDTO((String)object)
						).toArray(
							size -> new Clause[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rescores")) {
				if (jsonParserFieldValue != null) {
					queryEntry.setRescores(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RescoreSerDes.toDTO((String)object)
						).toArray(
							size -> new Rescore[size]
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