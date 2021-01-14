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

import com.liferay.headless.delivery.client.dto.v1_0.Experience;
import com.liferay.headless.delivery.client.dto.v1_0.Segment;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class ExperienceSerDes {

	public static Experience toDTO(String json) {
		ExperienceJSONParser experienceJSONParser = new ExperienceJSONParser();

		return experienceJSONParser.parseToDTO(json);
	}

	public static Experience[] toDTOs(String json) {
		ExperienceJSONParser experienceJSONParser = new ExperienceJSONParser();

		return experienceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Experience experience) {
		if (experience == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (experience.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(experience.getKey()));

			sb.append("\"");
		}

		if (experience.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(experience.getName()));

			sb.append("\"");
		}

		if (experience.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(experience.getName_i18n()));
		}

		if (experience.getSegments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"segments\": ");

			sb.append("[");

			for (int i = 0; i < experience.getSegments().length; i++) {
				sb.append(experience.getSegments()[i]);

				if ((i + 1) < experience.getSegments().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ExperienceJSONParser experienceJSONParser = new ExperienceJSONParser();

		return experienceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Experience experience) {
		if (experience == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (experience.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(experience.getKey()));
		}

		if (experience.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(experience.getName()));
		}

		if (experience.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put("name_i18n", String.valueOf(experience.getName_i18n()));
		}

		if (experience.getSegments() == null) {
			map.put("segments", null);
		}
		else {
			map.put("segments", String.valueOf(experience.getSegments()));
		}

		return map;
	}

	public static class ExperienceJSONParser
		extends BaseJSONParser<Experience> {

		@Override
		protected Experience createDTO() {
			return new Experience();
		}

		@Override
		protected Experience[] createDTOArray(int size) {
			return new Experience[size];
		}

		@Override
		protected void setField(
			Experience experience, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					experience.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					experience.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					experience.setName_i18n(
						(Map)ExperienceSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "segments")) {
				if (jsonParserFieldValue != null) {
					experience.setSegments((Segment[])jsonParserFieldValue);
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			sb.append("\":");

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
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}