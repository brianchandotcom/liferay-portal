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

package com.liferay.change.tracking.rest.client.serdes.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class CollectionSerDes {

	public static Collection toDTO(String json) {
		CollectionJSONParser collectionJSONParser = new CollectionJSONParser();

		return collectionJSONParser.parseToDTO(json);
	}

	public static Collection[] toDTOs(String json) {
		CollectionJSONParser collectionJSONParser = new CollectionJSONParser();

		return collectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Collection collection) {
		if (collection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (collection.getAdditionCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"additionCount\": ");

			sb.append(collection.getAdditionCount());
		}

		if (collection.getCompanyId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"companyId\": ");

			sb.append(collection.getCompanyId());
		}

		if (collection.getDateStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateStatus\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(collection.getDateStatus()));

			sb.append("\"");
		}

		if (collection.getDeletionCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deletionCount\": ");

			sb.append(collection.getDeletionCount());
		}

		if (collection.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(collection.getDescription()));

			sb.append("\"");
		}

		if (collection.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(collection.getId());
		}

		if (collection.getModificationCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modificationCount\": ");

			sb.append(collection.getModificationCount());
		}

		if (collection.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(collection.getName()));

			sb.append("\"");
		}

		if (collection.getStatusByUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"statusByUserName\": ");

			sb.append("\"");

			sb.append(_escape(collection.getStatusByUserName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CollectionJSONParser collectionJSONParser = new CollectionJSONParser();

		return collectionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Collection collection) {
		if (collection == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (collection.getAdditionCount() == null) {
			map.put("additionCount", null);
		}
		else {
			map.put(
				"additionCount", String.valueOf(collection.getAdditionCount()));
		}

		if (collection.getCompanyId() == null) {
			map.put("companyId", null);
		}
		else {
			map.put("companyId", String.valueOf(collection.getCompanyId()));
		}

		map.put(
			"dateStatus",
			liferayToJSONDateFormat.format(collection.getDateStatus()));

		if (collection.getDeletionCount() == null) {
			map.put("deletionCount", null);
		}
		else {
			map.put(
				"deletionCount", String.valueOf(collection.getDeletionCount()));
		}

		if (collection.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(collection.getDescription()));
		}

		if (collection.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(collection.getId()));
		}

		if (collection.getModificationCount() == null) {
			map.put("modificationCount", null);
		}
		else {
			map.put(
				"modificationCount",
				String.valueOf(collection.getModificationCount()));
		}

		if (collection.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(collection.getName()));
		}

		if (collection.getStatusByUserName() == null) {
			map.put("statusByUserName", null);
		}
		else {
			map.put(
				"statusByUserName",
				String.valueOf(collection.getStatusByUserName()));
		}

		return map;
	}

	public static class CollectionJSONParser
		extends BaseJSONParser<Collection> {

		@Override
		protected Collection createDTO() {
			return new Collection();
		}

		@Override
		protected Collection[] createDTOArray(int size) {
			return new Collection[size];
		}

		@Override
		protected void setField(
			Collection collection, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "additionCount")) {
				if (jsonParserFieldValue != null) {
					collection.setAdditionCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "companyId")) {
				if (jsonParserFieldValue != null) {
					collection.setCompanyId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateStatus")) {
				if (jsonParserFieldValue != null) {
					collection.setDateStatus(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "deletionCount")) {
				if (jsonParserFieldValue != null) {
					collection.setDeletionCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					collection.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					collection.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modificationCount")) {
				if (jsonParserFieldValue != null) {
					collection.setModificationCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					collection.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "statusByUserName")) {
				if (jsonParserFieldValue != null) {
					collection.setStatusByUserName(
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