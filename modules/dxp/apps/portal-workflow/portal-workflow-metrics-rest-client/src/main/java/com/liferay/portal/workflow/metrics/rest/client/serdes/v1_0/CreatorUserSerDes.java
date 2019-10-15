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

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.CreatorUser;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class CreatorUserSerDes {

	public static CreatorUser toDTO(String json) {
		CreatorUserJSONParser creatorUserJSONParser =
			new CreatorUserJSONParser();

		return creatorUserJSONParser.parseToDTO(json);
	}

	public static CreatorUser[] toDTOs(String json) {
		CreatorUserJSONParser creatorUserJSONParser =
			new CreatorUserJSONParser();

		return creatorUserJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CreatorUser creatorUser) {
		if (creatorUser == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (creatorUser.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(creatorUser.getId());
		}

		if (creatorUser.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(creatorUser.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CreatorUserJSONParser creatorUserJSONParser =
			new CreatorUserJSONParser();

		return creatorUserJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CreatorUser creatorUser) {
		if (creatorUser == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (creatorUser.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(creatorUser.getId()));
		}

		if (creatorUser.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(creatorUser.getName()));
		}

		return map;
	}

	public static class CreatorUserJSONParser
		extends BaseJSONParser<CreatorUser> {

		@Override
		protected CreatorUser createDTO() {
			return new CreatorUser();
		}

		@Override
		protected CreatorUser[] createDTOArray(int size) {
			return new CreatorUser[size];
		}

		@Override
		protected void setField(
			CreatorUser creatorUser, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					creatorUser.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					creatorUser.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}