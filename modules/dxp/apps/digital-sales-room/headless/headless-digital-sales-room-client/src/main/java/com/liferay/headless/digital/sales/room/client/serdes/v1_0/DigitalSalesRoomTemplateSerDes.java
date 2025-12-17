/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.client.serdes.v1_0;

import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoomTemplate;
import com.liferay.headless.digital.sales.room.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Stefano Motta
 * @generated
 */
@Generated("")
public class DigitalSalesRoomTemplateSerDes {

	public static DigitalSalesRoomTemplate toDTO(String json) {
		DigitalSalesRoomTemplateJSONParser digitalSalesRoomTemplateJSONParser =
			new DigitalSalesRoomTemplateJSONParser();

		return digitalSalesRoomTemplateJSONParser.parseToDTO(json);
	}

	public static DigitalSalesRoomTemplate[] toDTOs(String json) {
		DigitalSalesRoomTemplateJSONParser digitalSalesRoomTemplateJSONParser =
			new DigitalSalesRoomTemplateJSONParser();

		return digitalSalesRoomTemplateJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		DigitalSalesRoomTemplate digitalSalesRoomTemplate) {

		if (digitalSalesRoomTemplate == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (digitalSalesRoomTemplate.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(digitalSalesRoomTemplate.getActions()));
		}

		if (digitalSalesRoomTemplate.getBanner() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"banner\": ");

			sb.append(String.valueOf(digitalSalesRoomTemplate.getBanner()));
		}

		if (digitalSalesRoomTemplate.getClientLogo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientLogo\": ");

			sb.append(String.valueOf(digitalSalesRoomTemplate.getClientLogo()));
		}

		if (digitalSalesRoomTemplate.getClientName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientName\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoomTemplate.getClientName()));

			sb.append("\"");
		}

		if (digitalSalesRoomTemplate.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					digitalSalesRoomTemplate.getCreateDate()));

			sb.append("\"");
		}

		if (digitalSalesRoomTemplate.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoomTemplate.getDescription()));

			sb.append("\"");
		}

		if (digitalSalesRoomTemplate.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(digitalSalesRoomTemplate.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (digitalSalesRoomTemplate.getFriendlyUrlPath() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyUrlPath\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoomTemplate.getFriendlyUrlPath()));

			sb.append("\"");
		}

		if (digitalSalesRoomTemplate.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(digitalSalesRoomTemplate.getId());
		}

		if (digitalSalesRoomTemplate.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					digitalSalesRoomTemplate.getModifiedDate()));

			sb.append("\"");
		}

		if (digitalSalesRoomTemplate.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoomTemplate.getName()));

			sb.append("\"");
		}

		if (digitalSalesRoomTemplate.getPrimaryColor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primaryColor\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoomTemplate.getPrimaryColor()));

			sb.append("\"");
		}

		if (digitalSalesRoomTemplate.getSecondaryColor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"secondaryColor\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoomTemplate.getSecondaryColor()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DigitalSalesRoomTemplateJSONParser digitalSalesRoomTemplateJSONParser =
			new DigitalSalesRoomTemplateJSONParser();

		return digitalSalesRoomTemplateJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DigitalSalesRoomTemplate digitalSalesRoomTemplate) {

		if (digitalSalesRoomTemplate == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (digitalSalesRoomTemplate.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions",
				String.valueOf(digitalSalesRoomTemplate.getActions()));
		}

		if (digitalSalesRoomTemplate.getBanner() == null) {
			map.put("banner", null);
		}
		else {
			map.put(
				"banner", String.valueOf(digitalSalesRoomTemplate.getBanner()));
		}

		if (digitalSalesRoomTemplate.getClientLogo() == null) {
			map.put("clientLogo", null);
		}
		else {
			map.put(
				"clientLogo",
				String.valueOf(digitalSalesRoomTemplate.getClientLogo()));
		}

		if (digitalSalesRoomTemplate.getClientName() == null) {
			map.put("clientName", null);
		}
		else {
			map.put(
				"clientName",
				String.valueOf(digitalSalesRoomTemplate.getClientName()));
		}

		if (digitalSalesRoomTemplate.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(
					digitalSalesRoomTemplate.getCreateDate()));
		}

		if (digitalSalesRoomTemplate.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(digitalSalesRoomTemplate.getDescription()));
		}

		if (digitalSalesRoomTemplate.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(
					digitalSalesRoomTemplate.getExternalReferenceCode()));
		}

		if (digitalSalesRoomTemplate.getFriendlyUrlPath() == null) {
			map.put("friendlyUrlPath", null);
		}
		else {
			map.put(
				"friendlyUrlPath",
				String.valueOf(digitalSalesRoomTemplate.getFriendlyUrlPath()));
		}

		if (digitalSalesRoomTemplate.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(digitalSalesRoomTemplate.getId()));
		}

		if (digitalSalesRoomTemplate.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(
					digitalSalesRoomTemplate.getModifiedDate()));
		}

		if (digitalSalesRoomTemplate.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(digitalSalesRoomTemplate.getName()));
		}

		if (digitalSalesRoomTemplate.getPrimaryColor() == null) {
			map.put("primaryColor", null);
		}
		else {
			map.put(
				"primaryColor",
				String.valueOf(digitalSalesRoomTemplate.getPrimaryColor()));
		}

		if (digitalSalesRoomTemplate.getSecondaryColor() == null) {
			map.put("secondaryColor", null);
		}
		else {
			map.put(
				"secondaryColor",
				String.valueOf(digitalSalesRoomTemplate.getSecondaryColor()));
		}

		return map;
	}

	public static class DigitalSalesRoomTemplateJSONParser
		extends BaseJSONParser<DigitalSalesRoomTemplate> {

		@Override
		protected DigitalSalesRoomTemplate createDTO() {
			return new DigitalSalesRoomTemplate();
		}

		@Override
		protected DigitalSalesRoomTemplate[] createDTOArray(int size) {
			return new DigitalSalesRoomTemplate[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "actions")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "banner")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "clientLogo")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "clientName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrlPath")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "primaryColor")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "secondaryColor")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			DigitalSalesRoomTemplate digitalSalesRoomTemplate,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setActions(
						(Map<String, Map<String, String>>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "banner")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setBanner(
						FileEntrySerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "clientLogo")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setClientLogo(
						FileEntrySerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "clientName")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setClientName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrlPath")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setFriendlyUrlPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primaryColor")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setPrimaryColor(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "secondaryColor")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoomTemplate.setSecondaryColor(
						(String)jsonParserFieldValue);
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
		if (value == null) {
			return "null";
		}

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