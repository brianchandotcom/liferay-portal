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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.CreatorStatistics;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CreatorStatisticsSerDes {

	public static CreatorStatistics toDTO(String json) {
		CreatorStatisticsJSONParser creatorStatisticsJSONParser =
			new CreatorStatisticsJSONParser();

		return creatorStatisticsJSONParser.parseToDTO(json);
	}

	public static CreatorStatistics[] toDTOs(String json) {
		CreatorStatisticsJSONParser creatorStatisticsJSONParser =
			new CreatorStatisticsJSONParser();

		return creatorStatisticsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CreatorStatistics creatorStatistics) {
		if (creatorStatistics == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (creatorStatistics.getJoinDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"joinDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					creatorStatistics.getJoinDate()));

			sb.append("\"");
		}

		if (creatorStatistics.getLastPostDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPostDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					creatorStatistics.getLastPostDate()));

			sb.append("\"");
		}

		if (creatorStatistics.getPostsNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postsNumber\": ");

			sb.append(creatorStatistics.getPostsNumber());
		}

		if (creatorStatistics.getRank() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rank\": ");

			sb.append("\"");

			sb.append(_escape(creatorStatistics.getRank()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CreatorStatisticsJSONParser creatorStatisticsJSONParser =
			new CreatorStatisticsJSONParser();

		return creatorStatisticsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		CreatorStatistics creatorStatistics) {

		if (creatorStatistics == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (creatorStatistics.getJoinDate() == null) {
			map.put("joinDate", null);
		}
		else {
			map.put(
				"joinDate",
				liferayToJSONDateFormat.format(
					creatorStatistics.getJoinDate()));
		}

		if (creatorStatistics.getLastPostDate() == null) {
			map.put("lastPostDate", null);
		}
		else {
			map.put(
				"lastPostDate",
				liferayToJSONDateFormat.format(
					creatorStatistics.getLastPostDate()));
		}

		if (creatorStatistics.getPostsNumber() == null) {
			map.put("postsNumber", null);
		}
		else {
			map.put(
				"postsNumber",
				String.valueOf(creatorStatistics.getPostsNumber()));
		}

		if (creatorStatistics.getRank() == null) {
			map.put("rank", null);
		}
		else {
			map.put("rank", String.valueOf(creatorStatistics.getRank()));
		}

		return map;
	}

	public static class CreatorStatisticsJSONParser
		extends BaseJSONParser<CreatorStatistics> {

		@Override
		protected CreatorStatistics createDTO() {
			return new CreatorStatistics();
		}

		@Override
		protected CreatorStatistics[] createDTOArray(int size) {
			return new CreatorStatistics[size];
		}

		@Override
		protected void setField(
			CreatorStatistics creatorStatistics, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "joinDate")) {
				if (jsonParserFieldValue != null) {
					creatorStatistics.setJoinDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPostDate")) {
				if (jsonParserFieldValue != null) {
					creatorStatistics.setLastPostDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "postsNumber")) {
				if (jsonParserFieldValue != null) {
					creatorStatistics.setPostsNumber(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rank")) {
				if (jsonParserFieldValue != null) {
					creatorStatistics.setRank((String)jsonParserFieldValue);
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