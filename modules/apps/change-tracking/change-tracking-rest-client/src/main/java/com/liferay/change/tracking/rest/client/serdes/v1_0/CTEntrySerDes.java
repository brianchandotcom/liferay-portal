/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.client.serdes.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.CTEntry;
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
 * @author David Truong
 * @generated
 */
@Generated("")
public class CTEntrySerDes {

	public static CTEntry toDTO(String json) {
		CTEntryJSONParser ctEntryJSONParser = new CTEntryJSONParser();

		return ctEntryJSONParser.parseToDTO(json);
	}

	public static CTEntry[] toDTOs(String json) {
		CTEntryJSONParser ctEntryJSONParser = new CTEntryJSONParser();

		return ctEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CTEntry ctEntry) {
		if (ctEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (ctEntry.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(ctEntry.getActions()));
		}

		if (ctEntry.getChangeType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"changeType\": ");

			sb.append(ctEntry.getChangeType());
		}

		if (ctEntry.getCtCollectionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ctCollectionId\": ");

			sb.append(ctEntry.getCtCollectionId());
		}

		if (ctEntry.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(ctEntry.getDateCreated()));

			sb.append("\"");
		}

		if (ctEntry.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(ctEntry.getDateModified()));

			sb.append("\"");
		}

		if (ctEntry.getHideable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hideable\": ");

			sb.append(ctEntry.getHideable());
		}

		if (ctEntry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(ctEntry.getId());
		}

		if (ctEntry.getModelClassNameId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modelClassNameId\": ");

			sb.append(ctEntry.getModelClassNameId());
		}

		if (ctEntry.getModelClassPK() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modelClassPK\": ");

			sb.append(ctEntry.getModelClassPK());
		}

		if (ctEntry.getOwnerName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ownerName\": ");

			sb.append("\"");

			sb.append(_escape(ctEntry.getOwnerName()));

			sb.append("\"");
		}

		if (ctEntry.getSiteName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteName\": ");

			sb.append("\"");

			sb.append(_escape(ctEntry.getSiteName()));

			sb.append("\"");
		}

		if (ctEntry.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(String.valueOf(ctEntry.getStatus()));
		}

		if (ctEntry.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(ctEntry.getTitle()));

			sb.append("\"");
		}

		if (ctEntry.getTypeName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"typeName\": ");

			sb.append("\"");

			sb.append(_escape(ctEntry.getTypeName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CTEntryJSONParser ctEntryJSONParser = new CTEntryJSONParser();

		return ctEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CTEntry ctEntry) {
		if (ctEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (ctEntry.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(ctEntry.getActions()));
		}

		if (ctEntry.getChangeType() == null) {
			map.put("changeType", null);
		}
		else {
			map.put("changeType", String.valueOf(ctEntry.getChangeType()));
		}

		if (ctEntry.getCtCollectionId() == null) {
			map.put("ctCollectionId", null);
		}
		else {
			map.put(
				"ctCollectionId", String.valueOf(ctEntry.getCtCollectionId()));
		}

		if (ctEntry.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(ctEntry.getDateCreated()));
		}

		if (ctEntry.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(ctEntry.getDateModified()));
		}

		if (ctEntry.getHideable() == null) {
			map.put("hideable", null);
		}
		else {
			map.put("hideable", String.valueOf(ctEntry.getHideable()));
		}

		if (ctEntry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(ctEntry.getId()));
		}

		if (ctEntry.getModelClassNameId() == null) {
			map.put("modelClassNameId", null);
		}
		else {
			map.put(
				"modelClassNameId",
				String.valueOf(ctEntry.getModelClassNameId()));
		}

		if (ctEntry.getModelClassPK() == null) {
			map.put("modelClassPK", null);
		}
		else {
			map.put("modelClassPK", String.valueOf(ctEntry.getModelClassPK()));
		}

		if (ctEntry.getOwnerName() == null) {
			map.put("ownerName", null);
		}
		else {
			map.put("ownerName", String.valueOf(ctEntry.getOwnerName()));
		}

		if (ctEntry.getSiteName() == null) {
			map.put("siteName", null);
		}
		else {
			map.put("siteName", String.valueOf(ctEntry.getSiteName()));
		}

		if (ctEntry.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(ctEntry.getStatus()));
		}

		if (ctEntry.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(ctEntry.getTitle()));
		}

		if (ctEntry.getTypeName() == null) {
			map.put("typeName", null);
		}
		else {
			map.put("typeName", String.valueOf(ctEntry.getTypeName()));
		}

		return map;
	}

	public static class CTEntryJSONParser extends BaseJSONParser<CTEntry> {

		@Override
		protected CTEntry createDTO() {
			return new CTEntry();
		}

		@Override
		protected CTEntry[] createDTOArray(int size) {
			return new CTEntry[size];
		}

		@Override
		protected void setField(
			CTEntry ctEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setActions(
						(Map)CTEntrySerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "changeType")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setChangeType(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ctCollectionId")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setCtCollectionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "hideable")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setHideable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modelClassNameId")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setModelClassNameId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modelClassPK")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setModelClassPK(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ownerName")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setOwnerName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteName")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setSiteName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setStatus(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "typeName")) {
				if (jsonParserFieldValue != null) {
					ctEntry.setTypeName((String)jsonParserFieldValue);
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