/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.rest.client.serdes.v1_0;

import com.liferay.osb.testray.rest.client.dto.v1_0.CompareRuns;
import com.liferay.osb.testray.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author José Abelenda
 * @generated
 */
@Generated("")
public class CompareRunsSerDes {

	public static CompareRuns toDTO(String json) {
		CompareRunsJSONParser compareRunsJSONParser =
			new CompareRunsJSONParser();

		return compareRunsJSONParser.parseToDTO(json);
	}

	public static CompareRuns[] toDTOs(String json) {
		CompareRunsJSONParser compareRunsJSONParser =
			new CompareRunsJSONParser();

		return compareRunsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CompareRuns compareRuns) {
		if (compareRuns == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (compareRuns.getDueStatuses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueStatuses\": ");

			sb.append("[");

			for (int i = 0; i < compareRuns.getDueStatuses().length; i++) {
				sb.append("\"");

				sb.append(_escape(compareRuns.getDueStatuses()[i]));

				sb.append("\"");

				if ((i + 1) < compareRuns.getDueStatuses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (compareRuns.getValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"values\": ");

			if (compareRuns.getValues() instanceof String) {
				sb.append("\"");
				sb.append((String)compareRuns.getValues());
				sb.append("\"");
			}
			else {
				sb.append(compareRuns.getValues());
			}
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CompareRunsJSONParser compareRunsJSONParser =
			new CompareRunsJSONParser();

		return compareRunsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CompareRuns compareRuns) {
		if (compareRuns == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (compareRuns.getDueStatuses() == null) {
			map.put("dueStatuses", null);
		}
		else {
			map.put(
				"dueStatuses", String.valueOf(compareRuns.getDueStatuses()));
		}

		if (compareRuns.getValues() == null) {
			map.put("values", null);
		}
		else {
			map.put("values", String.valueOf(compareRuns.getValues()));
		}

		return map;
	}

	public static class CompareRunsJSONParser
		extends BaseJSONParser<CompareRuns> {

		@Override
		protected CompareRuns createDTO() {
			return new CompareRuns();
		}

		@Override
		protected CompareRuns[] createDTOArray(int size) {
			return new CompareRuns[size];
		}

		@Override
		protected void setField(
			CompareRuns compareRuns, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dueStatuses")) {
				if (jsonParserFieldValue != null) {
					compareRuns.setDueStatuses(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "values")) {
				if (jsonParserFieldValue != null) {
					compareRuns.setValues((Object)jsonParserFieldValue);
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