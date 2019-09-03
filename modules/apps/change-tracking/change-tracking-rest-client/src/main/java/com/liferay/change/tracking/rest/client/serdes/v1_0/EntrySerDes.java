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
public class EntrySerDes {

	public static Entry toDTO(String json) {
		EntryJSONParser entryJSONParser = new EntryJSONParser();

		return entryJSONParser.parseToDTO(json);
	}

	public static Entry[] toDTOs(String json) {
		EntryJSONParser entryJSONParser = new EntryJSONParser();

		return entryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Entry entry) {
		if (entry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (entry.getAffectedByEntriesCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"affectedByEntriesCount\": ");

			sb.append(entry.getAffectedByEntriesCount());
		}

		if (entry.getChangeType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"changeType\": ");

			sb.append(entry.getChangeType());
		}

		if (entry.getClassNameId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classNameId\": ");

			sb.append(entry.getClassNameId());
		}

		if (entry.getClassPK() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classPK\": ");

			sb.append(entry.getClassPK());
		}

		if (entry.getCollision() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collision\": ");

			sb.append(entry.getCollision());
		}

		if (entry.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(entry.getContentType()));

			sb.append("\"");
		}

		if (entry.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(entry.getDateModified()));

			sb.append("\"");
		}

		if (entry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(entry.getId());
		}

		if (entry.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append(entry.getKey());
		}

		if (entry.getSiteName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteName\": ");

			sb.append("\"");

			sb.append(_escape(entry.getSiteName()));

			sb.append("\"");
		}

		if (entry.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(entry.getTitle()));

			sb.append("\"");
		}

		if (entry.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(entry.getUserName()));

			sb.append("\"");
		}

		if (entry.getVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append("\"");

			sb.append(_escape(entry.getVersion()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		EntryJSONParser entryJSONParser = new EntryJSONParser();

		return entryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Entry entry) {
		if (entry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (entry.getAffectedByEntriesCount() == null) {
			map.put("affectedByEntriesCount", null);
		}
		else {
			map.put(
				"affectedByEntriesCount",
				String.valueOf(entry.getAffectedByEntriesCount()));
		}

		if (entry.getChangeType() == null) {
			map.put("changeType", null);
		}
		else {
			map.put("changeType", String.valueOf(entry.getChangeType()));
		}

		if (entry.getClassNameId() == null) {
			map.put("classNameId", null);
		}
		else {
			map.put("classNameId", String.valueOf(entry.getClassNameId()));
		}

		if (entry.getClassPK() == null) {
			map.put("classPK", null);
		}
		else {
			map.put("classPK", String.valueOf(entry.getClassPK()));
		}

		if (entry.getCollision() == null) {
			map.put("collision", null);
		}
		else {
			map.put("collision", String.valueOf(entry.getCollision()));
		}

		if (entry.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put("contentType", String.valueOf(entry.getContentType()));
		}

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(entry.getDateModified()));

		if (entry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(entry.getId()));
		}

		if (entry.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(entry.getKey()));
		}

		if (entry.getSiteName() == null) {
			map.put("siteName", null);
		}
		else {
			map.put("siteName", String.valueOf(entry.getSiteName()));
		}

		if (entry.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(entry.getTitle()));
		}

		if (entry.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put("userName", String.valueOf(entry.getUserName()));
		}

		if (entry.getVersion() == null) {
			map.put("version", null);
		}
		else {
			map.put("version", String.valueOf(entry.getVersion()));
		}

		return map;
	}

	public static class EntryJSONParser extends BaseJSONParser<Entry> {

		@Override
		protected Entry createDTO() {
			return new Entry();
		}

		@Override
		protected Entry[] createDTOArray(int size) {
			return new Entry[size];
		}

		@Override
		protected void setField(
			Entry entry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "affectedByEntriesCount")) {
				if (jsonParserFieldValue != null) {
					entry.setAffectedByEntriesCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "changeType")) {
				if (jsonParserFieldValue != null) {
					entry.setChangeType(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "classNameId")) {
				if (jsonParserFieldValue != null) {
					entry.setClassNameId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "classPK")) {
				if (jsonParserFieldValue != null) {
					entry.setClassPK(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "collision")) {
				if (jsonParserFieldValue != null) {
					entry.setCollision((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					entry.setContentType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					entry.setDateModified(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					entry.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					entry.setKey(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteName")) {
				if (jsonParserFieldValue != null) {
					entry.setSiteName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					entry.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					entry.setUserName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "version")) {
				if (jsonParserFieldValue != null) {
					entry.setVersion((String)jsonParserFieldValue);
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