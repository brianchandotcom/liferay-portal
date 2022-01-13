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

import com.liferay.search.experiences.rest.client.dto.v1_0.Hit;
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
public class HitSerDes {

	public static Hit toDTO(String json) {
		HitJSONParser hitJSONParser = new HitJSONParser();

		return hitJSONParser.parseToDTO(json);
	}

	public static Hit[] toDTOs(String json) {
		HitJSONParser hitJSONParser = new HitJSONParser();

		return hitJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Hit hit) {
		if (hit == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (hit.getDocumentFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentFields\": ");

			sb.append(_toJSON(hit.getDocumentFields()));
		}

		if (hit.getExplanation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"explanation\": ");

			sb.append("\"");

			sb.append(_escape(hit.getExplanation()));

			sb.append("\"");
		}

		if (hit.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(hit.getId()));

			sb.append("\"");
		}

		if (hit.getScore() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"score\": ");

			sb.append(hit.getScore());
		}

		if (hit.getVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append(hit.getVersion());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		HitJSONParser hitJSONParser = new HitJSONParser();

		return hitJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Hit hit) {
		if (hit == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (hit.getDocumentFields() == null) {
			map.put("documentFields", null);
		}
		else {
			map.put("documentFields", String.valueOf(hit.getDocumentFields()));
		}

		if (hit.getExplanation() == null) {
			map.put("explanation", null);
		}
		else {
			map.put("explanation", String.valueOf(hit.getExplanation()));
		}

		if (hit.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(hit.getId()));
		}

		if (hit.getScore() == null) {
			map.put("score", null);
		}
		else {
			map.put("score", String.valueOf(hit.getScore()));
		}

		if (hit.getVersion() == null) {
			map.put("version", null);
		}
		else {
			map.put("version", String.valueOf(hit.getVersion()));
		}

		return map;
	}

	public static class HitJSONParser extends BaseJSONParser<Hit> {

		@Override
		protected Hit createDTO() {
			return new Hit();
		}

		@Override
		protected Hit[] createDTOArray(int size) {
			return new Hit[size];
		}

		@Override
		protected void setField(
			Hit hit, String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "documentFields")) {
				if (jsonParserFieldValue != null) {
					hit.setDocumentFields(
						(Map)HitSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "explanation")) {
				if (jsonParserFieldValue != null) {
					hit.setExplanation((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					hit.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "score")) {
				if (jsonParserFieldValue != null) {
					hit.setScore(Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "version")) {
				if (jsonParserFieldValue != null) {
					hit.setVersion(Long.valueOf((String)jsonParserFieldValue));
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