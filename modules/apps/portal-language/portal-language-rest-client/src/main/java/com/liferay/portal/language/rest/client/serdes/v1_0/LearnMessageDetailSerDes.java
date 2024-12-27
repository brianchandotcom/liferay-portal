/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.client.serdes.v1_0;

import com.liferay.portal.language.rest.client.dto.v1_0.LearnMessageDetail;
import com.liferay.portal.language.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Thiago Buarque
 * @generated
 */
@Generated("")
public class LearnMessageDetailSerDes {

	public static LearnMessageDetail toDTO(String json) {
		LearnMessageDetailJSONParser learnMessageDetailJSONParser =
			new LearnMessageDetailJSONParser();

		return learnMessageDetailJSONParser.parseToDTO(json);
	}

	public static LearnMessageDetail[] toDTOs(String json) {
		LearnMessageDetailJSONParser learnMessageDetailJSONParser =
			new LearnMessageDetailJSONParser();

		return learnMessageDetailJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LearnMessageDetail learnMessageDetail) {
		if (learnMessageDetail == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (learnMessageDetail.getLanguageId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"languageId\": ");

			sb.append("\"");

			sb.append(_escape(learnMessageDetail.getLanguageId()));

			sb.append("\"");
		}

		if (learnMessageDetail.getMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"message\": ");

			sb.append("\"");

			sb.append(_escape(learnMessageDetail.getMessage()));

			sb.append("\"");
		}

		if (learnMessageDetail.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(learnMessageDetail.getUrl()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LearnMessageDetailJSONParser learnMessageDetailJSONParser =
			new LearnMessageDetailJSONParser();

		return learnMessageDetailJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		LearnMessageDetail learnMessageDetail) {

		if (learnMessageDetail == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (learnMessageDetail.getLanguageId() == null) {
			map.put("languageId", null);
		}
		else {
			map.put(
				"languageId",
				String.valueOf(learnMessageDetail.getLanguageId()));
		}

		if (learnMessageDetail.getMessage() == null) {
			map.put("message", null);
		}
		else {
			map.put("message", String.valueOf(learnMessageDetail.getMessage()));
		}

		if (learnMessageDetail.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(learnMessageDetail.getUrl()));
		}

		return map;
	}

	public static class LearnMessageDetailJSONParser
		extends BaseJSONParser<LearnMessageDetail> {

		@Override
		protected LearnMessageDetail createDTO() {
			return new LearnMessageDetail();
		}

		@Override
		protected LearnMessageDetail[] createDTOArray(int size) {
			return new LearnMessageDetail[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "languageId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "message")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			LearnMessageDetail learnMessageDetail, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "languageId")) {
				if (jsonParserFieldValue != null) {
					learnMessageDetail.setLanguageId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "message")) {
				if (jsonParserFieldValue != null) {
					learnMessageDetail.setMessage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					learnMessageDetail.setUrl((String)jsonParserFieldValue);
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}