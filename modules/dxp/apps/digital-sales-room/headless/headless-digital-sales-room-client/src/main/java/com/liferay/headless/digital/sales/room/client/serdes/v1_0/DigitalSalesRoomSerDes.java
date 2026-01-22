/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.client.serdes.v1_0;

import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.UserAccountBrief;
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
public class DigitalSalesRoomSerDes {

	public static DigitalSalesRoom toDTO(String json) {
		DigitalSalesRoomJSONParser digitalSalesRoomJSONParser =
			new DigitalSalesRoomJSONParser();

		return digitalSalesRoomJSONParser.parseToDTO(json);
	}

	public static DigitalSalesRoom[] toDTOs(String json) {
		DigitalSalesRoomJSONParser digitalSalesRoomJSONParser =
			new DigitalSalesRoomJSONParser();

		return digitalSalesRoomJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DigitalSalesRoom digitalSalesRoom) {
		if (digitalSalesRoom == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (digitalSalesRoom.getAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(digitalSalesRoom.getAccountId());
		}

		if (digitalSalesRoom.getAccountName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountName\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getAccountName()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(digitalSalesRoom.getActions()));
		}

		if (digitalSalesRoom.getBanner() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"banner\": ");

			sb.append(String.valueOf(digitalSalesRoom.getBanner()));
		}

		if (digitalSalesRoom.getChannelId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelId\": ");

			sb.append(digitalSalesRoom.getChannelId());
		}

		if (digitalSalesRoom.getChannelName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelName\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getChannelName()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getClientLogo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientLogo\": ");

			sb.append(String.valueOf(digitalSalesRoom.getClientLogo()));
		}

		if (digitalSalesRoom.getClientName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientName\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getClientName()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					digitalSalesRoom.getCreateDate()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getDescription()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getDigitalSalesRoomTemplateId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"digitalSalesRoomTemplateId\": ");

			sb.append(digitalSalesRoom.getDigitalSalesRoomTemplateId());
		}

		if (digitalSalesRoom.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getFriendlyUrlPath() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyUrlPath\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getFriendlyUrlPath()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(digitalSalesRoom.getId());
		}

		if (digitalSalesRoom.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					digitalSalesRoom.getModifiedDate()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getName()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getOwnerId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ownerId\": ");

			sb.append(digitalSalesRoom.getOwnerId());
		}

		if (digitalSalesRoom.getOwnerName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ownerName\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getOwnerName()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getPrimaryColor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primaryColor\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getPrimaryColor()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getSecondaryColor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"secondaryColor\": ");

			sb.append("\"");

			sb.append(_escape(digitalSalesRoom.getSecondaryColor()));

			sb.append("\"");
		}

		if (digitalSalesRoom.getUserAccountBriefs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userAccountBriefs\": ");

			sb.append("[");

			for (int i = 0; i < digitalSalesRoom.getUserAccountBriefs().length;
				 i++) {

				sb.append(
					String.valueOf(digitalSalesRoom.getUserAccountBriefs()[i]));

				if ((i + 1) < digitalSalesRoom.getUserAccountBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DigitalSalesRoomJSONParser digitalSalesRoomJSONParser =
			new DigitalSalesRoomJSONParser();

		return digitalSalesRoomJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DigitalSalesRoom digitalSalesRoom) {
		if (digitalSalesRoom == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (digitalSalesRoom.getAccountId() == null) {
			map.put("accountId", null);
		}
		else {
			map.put(
				"accountId", String.valueOf(digitalSalesRoom.getAccountId()));
		}

		if (digitalSalesRoom.getAccountName() == null) {
			map.put("accountName", null);
		}
		else {
			map.put(
				"accountName",
				String.valueOf(digitalSalesRoom.getAccountName()));
		}

		if (digitalSalesRoom.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(digitalSalesRoom.getActions()));
		}

		if (digitalSalesRoom.getBanner() == null) {
			map.put("banner", null);
		}
		else {
			map.put("banner", String.valueOf(digitalSalesRoom.getBanner()));
		}

		if (digitalSalesRoom.getChannelId() == null) {
			map.put("channelId", null);
		}
		else {
			map.put(
				"channelId", String.valueOf(digitalSalesRoom.getChannelId()));
		}

		if (digitalSalesRoom.getChannelName() == null) {
			map.put("channelName", null);
		}
		else {
			map.put(
				"channelName",
				String.valueOf(digitalSalesRoom.getChannelName()));
		}

		if (digitalSalesRoom.getClientLogo() == null) {
			map.put("clientLogo", null);
		}
		else {
			map.put(
				"clientLogo", String.valueOf(digitalSalesRoom.getClientLogo()));
		}

		if (digitalSalesRoom.getClientName() == null) {
			map.put("clientName", null);
		}
		else {
			map.put(
				"clientName", String.valueOf(digitalSalesRoom.getClientName()));
		}

		if (digitalSalesRoom.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(
					digitalSalesRoom.getCreateDate()));
		}

		if (digitalSalesRoom.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(digitalSalesRoom.getDescription()));
		}

		if (digitalSalesRoom.getDigitalSalesRoomTemplateId() == null) {
			map.put("digitalSalesRoomTemplateId", null);
		}
		else {
			map.put(
				"digitalSalesRoomTemplateId",
				String.valueOf(
					digitalSalesRoom.getDigitalSalesRoomTemplateId()));
		}

		if (digitalSalesRoom.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(digitalSalesRoom.getExternalReferenceCode()));
		}

		if (digitalSalesRoom.getFriendlyUrlPath() == null) {
			map.put("friendlyUrlPath", null);
		}
		else {
			map.put(
				"friendlyUrlPath",
				String.valueOf(digitalSalesRoom.getFriendlyUrlPath()));
		}

		if (digitalSalesRoom.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(digitalSalesRoom.getId()));
		}

		if (digitalSalesRoom.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(
					digitalSalesRoom.getModifiedDate()));
		}

		if (digitalSalesRoom.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(digitalSalesRoom.getName()));
		}

		if (digitalSalesRoom.getOwnerId() == null) {
			map.put("ownerId", null);
		}
		else {
			map.put("ownerId", String.valueOf(digitalSalesRoom.getOwnerId()));
		}

		if (digitalSalesRoom.getOwnerName() == null) {
			map.put("ownerName", null);
		}
		else {
			map.put(
				"ownerName", String.valueOf(digitalSalesRoom.getOwnerName()));
		}

		if (digitalSalesRoom.getPrimaryColor() == null) {
			map.put("primaryColor", null);
		}
		else {
			map.put(
				"primaryColor",
				String.valueOf(digitalSalesRoom.getPrimaryColor()));
		}

		if (digitalSalesRoom.getSecondaryColor() == null) {
			map.put("secondaryColor", null);
		}
		else {
			map.put(
				"secondaryColor",
				String.valueOf(digitalSalesRoom.getSecondaryColor()));
		}

		if (digitalSalesRoom.getUserAccountBriefs() == null) {
			map.put("userAccountBriefs", null);
		}
		else {
			map.put(
				"userAccountBriefs",
				String.valueOf(digitalSalesRoom.getUserAccountBriefs()));
		}

		return map;
	}

	public static class DigitalSalesRoomJSONParser
		extends BaseJSONParser<DigitalSalesRoom> {

		@Override
		protected DigitalSalesRoom createDTO() {
			return new DigitalSalesRoom();
		}

		@Override
		protected DigitalSalesRoom[] createDTOArray(int size) {
			return new DigitalSalesRoom[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "accountId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "accountName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "banner")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "channelId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "channelName")) {
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
						jsonParserFieldName, "digitalSalesRoomTemplateId")) {

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
			else if (Objects.equals(jsonParserFieldName, "ownerId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "ownerName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "primaryColor")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "secondaryColor")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "userAccountBriefs")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			DigitalSalesRoom digitalSalesRoom, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accountId")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountName")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setAccountName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setActions(
						(Map<String, Map<String, String>>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "banner")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setBanner(
						FileEntrySerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelId")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setChannelId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelName")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setChannelName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "clientLogo")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setClientLogo(
						FileEntrySerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "clientName")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setClientName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "digitalSalesRoomTemplateId")) {

				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setDigitalSalesRoomTemplateId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrlPath")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setFriendlyUrlPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ownerId")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setOwnerId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ownerName")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setOwnerName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primaryColor")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setPrimaryColor(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "secondaryColor")) {
				if (jsonParserFieldValue != null) {
					digitalSalesRoom.setSecondaryColor(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userAccountBriefs")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					UserAccountBrief[] userAccountBriefsArray =
						new UserAccountBrief[jsonParserFieldValues.length];

					for (int i = 0; i < userAccountBriefsArray.length; i++) {
						userAccountBriefsArray[i] =
							UserAccountBriefSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					digitalSalesRoom.setUserAccountBriefs(
						userAccountBriefsArray);
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