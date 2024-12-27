/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.client.serdes.v1_0;

import com.liferay.portal.language.rest.client.dto.v1_0.LearnMessage;
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
public class LearnMessageSerDes {

	public static LearnMessage toDTO(String json) {
		LearnMessageJSONParser learnMessageJSONParser =
			new LearnMessageJSONParser();

		return learnMessageJSONParser.parseToDTO(json);
	}

	public static LearnMessage[] toDTOs(String json) {
		LearnMessageJSONParser learnMessageJSONParser =
			new LearnMessageJSONParser();

		return learnMessageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LearnMessage learnMessage) {
		if (learnMessage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (learnMessage.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(learnMessage.getKey()));

			sb.append("\"");
		}

		if (learnMessage.getLearnMessageDetails() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"learnMessageDetails\": ");

			sb.append("[");

			for (int i = 0; i < learnMessage.getLearnMessageDetails().length;
				 i++) {

				sb.append(
					String.valueOf(learnMessage.getLearnMessageDetails()[i]));

				if ((i + 1) < learnMessage.getLearnMessageDetails().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LearnMessageJSONParser learnMessageJSONParser =
			new LearnMessageJSONParser();

		return learnMessageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(LearnMessage learnMessage) {
		if (learnMessage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (learnMessage.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(learnMessage.getKey()));
		}

		if (learnMessage.getLearnMessageDetails() == null) {
			map.put("learnMessageDetails", null);
		}
		else {
			map.put(
				"learnMessageDetails",
				String.valueOf(learnMessage.getLearnMessageDetails()));
		}

		return map;
	}

	public static class LearnMessageJSONParser
		extends BaseJSONParser<LearnMessage> {

		@Override
		protected LearnMessage createDTO() {
			return new LearnMessage();
		}

		@Override
		protected LearnMessage[] createDTOArray(int size) {
			return new LearnMessage[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "key")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "learnMessageDetails")) {

				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			LearnMessage learnMessage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					learnMessage.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "learnMessageDetails")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					LearnMessageDetail[] learnMessageDetailsArray =
						new LearnMessageDetail[jsonParserFieldValues.length];

					for (int i = 0; i < learnMessageDetailsArray.length; i++) {
						learnMessageDetailsArray[i] =
							LearnMessageDetailSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					learnMessage.setLearnMessageDetails(
						learnMessageDetailsArray);
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