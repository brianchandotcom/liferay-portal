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

import com.liferay.search.experiences.rest.client.dto.v1_0.Rescore;
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
public class RescoreSerDes {

	public static Rescore toDTO(String json) {
		RescoreJSONParser rescoreJSONParser = new RescoreJSONParser();

		return rescoreJSONParser.parseToDTO(json);
	}

	public static Rescore[] toDTOs(String json) {
		RescoreJSONParser rescoreJSONParser = new RescoreJSONParser();

		return rescoreJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Rescore rescore) {
		if (rescore == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (rescore.getQuery() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"query\": ");

			sb.append("\"");

			sb.append(_escape(rescore.getQuery()));

			sb.append("\"");
		}

		if (rescore.getQueryWeight() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"queryWeight\": ");

			sb.append(rescore.getQueryWeight());
		}

		if (rescore.getRescoreQueryWeight() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rescoreQueryWeight\": ");

			sb.append(rescore.getRescoreQueryWeight());
		}

		if (rescore.getScoreMode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scoreMode\": ");

			sb.append("\"");

			sb.append(_escape(rescore.getScoreMode()));

			sb.append("\"");
		}

		if (rescore.getWindowSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"windowSize\": ");

			sb.append(rescore.getWindowSize());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RescoreJSONParser rescoreJSONParser = new RescoreJSONParser();

		return rescoreJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Rescore rescore) {
		if (rescore == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (rescore.getQuery() == null) {
			map.put("query", null);
		}
		else {
			map.put("query", String.valueOf(rescore.getQuery()));
		}

		if (rescore.getQueryWeight() == null) {
			map.put("queryWeight", null);
		}
		else {
			map.put("queryWeight", String.valueOf(rescore.getQueryWeight()));
		}

		if (rescore.getRescoreQueryWeight() == null) {
			map.put("rescoreQueryWeight", null);
		}
		else {
			map.put(
				"rescoreQueryWeight",
				String.valueOf(rescore.getRescoreQueryWeight()));
		}

		if (rescore.getScoreMode() == null) {
			map.put("scoreMode", null);
		}
		else {
			map.put("scoreMode", String.valueOf(rescore.getScoreMode()));
		}

		if (rescore.getWindowSize() == null) {
			map.put("windowSize", null);
		}
		else {
			map.put("windowSize", String.valueOf(rescore.getWindowSize()));
		}

		return map;
	}

	public static class RescoreJSONParser extends BaseJSONParser<Rescore> {

		@Override
		protected Rescore createDTO() {
			return new Rescore();
		}

		@Override
		protected Rescore[] createDTOArray(int size) {
			return new Rescore[size];
		}

		@Override
		protected void setField(
			Rescore rescore, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "query")) {
				if (jsonParserFieldValue != null) {
					rescore.setQuery((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "queryWeight")) {
				if (jsonParserFieldValue != null) {
					rescore.setQueryWeight(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "rescoreQueryWeight")) {

				if (jsonParserFieldValue != null) {
					rescore.setRescoreQueryWeight(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "scoreMode")) {
				if (jsonParserFieldValue != null) {
					rescore.setScoreMode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "windowSize")) {
				if (jsonParserFieldValue != null) {
					rescore.setWindowSize(
						Integer.valueOf((String)jsonParserFieldValue));
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