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

import com.liferay.search.experiences.rest.client.dto.v1_0.KeywordQueryContributors;
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
public class KeywordQueryContributorsSerDes {

	public static KeywordQueryContributors toDTO(String json) {
		KeywordQueryContributorsJSONParser keywordQueryContributorsJSONParser =
			new KeywordQueryContributorsJSONParser();

		return keywordQueryContributorsJSONParser.parseToDTO(json);
	}

	public static KeywordQueryContributors[] toDTOs(String json) {
		KeywordQueryContributorsJSONParser keywordQueryContributorsJSONParser =
			new KeywordQueryContributorsJSONParser();

		return keywordQueryContributorsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		KeywordQueryContributors keywordQueryContributors) {

		if (keywordQueryContributors == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (keywordQueryContributors.getClassNames() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classNames\": ");

			sb.append("[");

			for (int i = 0; i < keywordQueryContributors.getClassNames().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(keywordQueryContributors.getClassNames()[i]));

				sb.append("\"");

				if ((i + 1) < keywordQueryContributors.getClassNames().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		KeywordQueryContributorsJSONParser keywordQueryContributorsJSONParser =
			new KeywordQueryContributorsJSONParser();

		return keywordQueryContributorsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		KeywordQueryContributors keywordQueryContributors) {

		if (keywordQueryContributors == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (keywordQueryContributors.getClassNames() == null) {
			map.put("classNames", null);
		}
		else {
			map.put(
				"classNames",
				String.valueOf(keywordQueryContributors.getClassNames()));
		}

		return map;
	}

	public static class KeywordQueryContributorsJSONParser
		extends BaseJSONParser<KeywordQueryContributors> {

		@Override
		protected KeywordQueryContributors createDTO() {
			return new KeywordQueryContributors();
		}

		@Override
		protected KeywordQueryContributors[] createDTOArray(int size) {
			return new KeywordQueryContributors[size];
		}

		@Override
		protected void setField(
			KeywordQueryContributors keywordQueryContributors,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "classNames")) {
				if (jsonParserFieldValue != null) {
					keywordQueryContributors.setClassNames(
						toStrings((Object[])jsonParserFieldValue));
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