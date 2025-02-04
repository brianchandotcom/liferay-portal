/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.space.client.serdes.v1_0;

import com.liferay.headless.space.client.dto.v1_0.Space;
import com.liferay.headless.space.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Roberto Díaz
 * @generated
 */
@Generated("")
public class SpaceSerDes {

	public static Space toDTO(String json) {
		SpaceJSONParser spaceJSONParser = new SpaceJSONParser();

		return spaceJSONParser.parseToDTO(json);
	}

	public static Space[] toDTOs(String json) {
		SpaceJSONParser spaceJSONParser = new SpaceJSONParser();

		return spaceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Space space) {
		if (space == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (space.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(space.getDateCreated()));

			sb.append("\"");
		}

		if (space.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(space.getDateModified()));

			sb.append("\"");
		}

		if (space.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(space.getDescription()));

			sb.append("\"");
		}

		if (space.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(space.getDescription_i18n()));
		}

		if (space.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(space.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (space.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(space.getId());
		}

		if (space.getLinkedSiteIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"linkedSiteIds\": ");

			sb.append("[");

			for (int i = 0; i < space.getLinkedSiteIds().length; i++) {
				sb.append(space.getLinkedSiteIds()[i]);

				if ((i + 1) < space.getLinkedSiteIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (space.getLinkedSitesExternalReferenceCodes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"linkedSitesExternalReferenceCodes\": ");

			sb.append("[");

			for (int i = 0;
				 i < space.getLinkedSitesExternalReferenceCodes().length; i++) {

				sb.append(
					_toJSON(space.getLinkedSitesExternalReferenceCodes()[i]));

				if ((i + 1) <
						space.getLinkedSitesExternalReferenceCodes().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (space.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(space.getName()));

			sb.append("\"");
		}

		if (space.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(space.getName_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SpaceJSONParser spaceJSONParser = new SpaceJSONParser();

		return spaceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Space space) {
		if (space == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (space.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(space.getDateCreated()));
		}

		if (space.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(space.getDateModified()));
		}

		if (space.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(space.getDescription()));
		}

		if (space.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(space.getDescription_i18n()));
		}

		if (space.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(space.getExternalReferenceCode()));
		}

		if (space.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(space.getId()));
		}

		if (space.getLinkedSiteIds() == null) {
			map.put("linkedSiteIds", null);
		}
		else {
			map.put("linkedSiteIds", String.valueOf(space.getLinkedSiteIds()));
		}

		if (space.getLinkedSitesExternalReferenceCodes() == null) {
			map.put("linkedSitesExternalReferenceCodes", null);
		}
		else {
			map.put(
				"linkedSitesExternalReferenceCodes",
				String.valueOf(space.getLinkedSitesExternalReferenceCodes()));
		}

		if (space.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(space.getName()));
		}

		if (space.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put("name_i18n", String.valueOf(space.getName_i18n()));
		}

		return map;
	}

	public static class SpaceJSONParser extends BaseJSONParser<Space> {

		@Override
		protected Space createDTO() {
			return new Space();
		}

		@Override
		protected Space[] createDTOArray(int size) {
			return new Space[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				return true;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "linkedSiteIds")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"linkedSitesExternalReferenceCodes")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				return true;
			}

			return false;
		}

		@Override
		protected void setField(
			Space space, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					space.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					space.setDateModified(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					space.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					space.setDescription_i18n(
						(Map<String, String>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					space.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					space.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "linkedSiteIds")) {
				if (jsonParserFieldValue != null) {
					space.setLinkedSiteIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"linkedSitesExternalReferenceCodes")) {

				if (jsonParserFieldValue != null) {
					space.setLinkedSitesExternalReferenceCodes(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					space.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					space.setName_i18n(
						(Map<String, String>)jsonParserFieldValue);
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