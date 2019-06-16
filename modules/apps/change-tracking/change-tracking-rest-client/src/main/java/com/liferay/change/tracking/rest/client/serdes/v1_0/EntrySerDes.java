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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

		if (entry.getCtEntryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ctEntryId\": ");

			sb.append(entry.getCtEntryId());
		}

		if (entry.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(entry.getModifiedDate()));

			sb.append("\"");
		}

		if (entry.getResourcePrimKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"resourcePrimKey\": ");

			sb.append(entry.getResourcePrimKey());
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

		Map<String, String> map = new HashMap<>();

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

		if (entry.getCtEntryId() == null) {
			map.put("ctEntryId", null);
		}
		else {
			map.put("ctEntryId", String.valueOf(entry.getCtEntryId()));
		}

		map.put(
			"modifiedDate",
			liferayToJSONDateFormat.format(entry.getModifiedDate()));

		if (entry.getResourcePrimKey() == null) {
			map.put("resourcePrimKey", null);
		}
		else {
			map.put(
				"resourcePrimKey", String.valueOf(entry.getResourcePrimKey()));
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class EntryJSONParser extends BaseJSONParser<Entry> {

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
			else if (Objects.equals(jsonParserFieldName, "ctEntryId")) {
				if (jsonParserFieldValue != null) {
					entry.setCtEntryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					entry.setModifiedDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "resourcePrimKey")) {
				if (jsonParserFieldValue != null) {
					entry.setResourcePrimKey(
						Long.valueOf((String)jsonParserFieldValue));
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

}