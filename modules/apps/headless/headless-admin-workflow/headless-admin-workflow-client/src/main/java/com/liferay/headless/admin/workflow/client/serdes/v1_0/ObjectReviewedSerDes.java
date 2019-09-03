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

package com.liferay.headless.admin.workflow.client.serdes.v1_0;

import com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed;
import com.liferay.headless.admin.workflow.client.json.BaseJSONParser;

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
public class ObjectReviewedSerDes {

	public static ObjectReviewed toDTO(String json) {
		ObjectReviewedJSONParser objectReviewedJSONParser =
			new ObjectReviewedJSONParser();

		return objectReviewedJSONParser.parseToDTO(json);
	}

	public static ObjectReviewed[] toDTOs(String json) {
		ObjectReviewedJSONParser objectReviewedJSONParser =
			new ObjectReviewedJSONParser();

		return objectReviewedJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectReviewed objectReviewed) {
		if (objectReviewed == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectReviewed.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectReviewed.getId());
		}

		if (objectReviewed.getResourceType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"resourceType\": ");

			sb.append("\"");

			sb.append(_escape(objectReviewed.getResourceType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectReviewedJSONParser objectReviewedJSONParser =
			new ObjectReviewedJSONParser();

		return objectReviewedJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectReviewed objectReviewed) {
		if (objectReviewed == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectReviewed.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectReviewed.getId()));
		}

		if (objectReviewed.getResourceType() == null) {
			map.put("resourceType", null);
		}
		else {
			map.put(
				"resourceType",
				String.valueOf(objectReviewed.getResourceType()));
		}

		return map;
	}

	public static class ObjectReviewedJSONParser
		extends BaseJSONParser<ObjectReviewed> {

		@Override
		protected ObjectReviewed createDTO() {
			return new ObjectReviewed();
		}

		@Override
		protected ObjectReviewed[] createDTOArray(int size) {
			return new ObjectReviewed[size];
		}

		@Override
		protected void setField(
			ObjectReviewed objectReviewed, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectReviewed.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "resourceType")) {
				if (jsonParserFieldValue != null) {
					objectReviewed.setResourceType(
						(String)jsonParserFieldValue);
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