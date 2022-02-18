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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.CustomField;
import com.liferay.headless.admin.user.client.dto.v1_0.Organization;
import com.liferay.headless.admin.user.client.dto.v1_0.Service;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class OrganizationSerDes {

	public static Organization toDTO(String json) {
		OrganizationJSONParser organizationJSONParser =
			new OrganizationJSONParser();

		return organizationJSONParser.parseToDTO(json);
	}

	public static Organization[] toDTOs(String json) {
		OrganizationJSONParser organizationJSONParser =
			new OrganizationJSONParser();

		return organizationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Organization organization) {
		if (organization == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (organization.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(organization.getActions()));
		}

		if (organization.getChildOrganizations() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"childOrganizations\": ");

			sb.append("[");

			for (int i = 0; i < organization.getChildOrganizations().length;
				 i++) {

				sb.append(
					String.valueOf(organization.getChildOrganizations()[i]));

				if ((i + 1) < organization.getChildOrganizations().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (organization.getComment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comment\": ");

			sb.append("\"");

			sb.append(_escape(organization.getComment()));

			sb.append("\"");
		}

		if (organization.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append("[");

			for (int i = 0; i < organization.getCustomFields().length; i++) {
				sb.append(String.valueOf(organization.getCustomFields()[i]));

				if ((i + 1) < organization.getCustomFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (organization.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(organization.getDateCreated()));

			sb.append("\"");
		}

		if (organization.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(organization.getDateModified()));

			sb.append("\"");
		}

		if (organization.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(organization.getId()));

			sb.append("\"");
		}

		if (organization.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append("\"");

			sb.append(_escape(organization.getImage()));

			sb.append("\"");
		}

		if (organization.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("[");

			for (int i = 0; i < organization.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(_escape(organization.getKeywords()[i]));

				sb.append("\"");

				if ((i + 1) < organization.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (organization.getLocation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"location\": ");

			sb.append(String.valueOf(organization.getLocation()));
		}

		if (organization.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(organization.getName()));

			sb.append("\"");
		}

		if (organization.getNumberOfAccounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfAccounts\": ");

			sb.append(organization.getNumberOfAccounts());
		}

		if (organization.getNumberOfOrganizations() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfOrganizations\": ");

			sb.append(organization.getNumberOfOrganizations());
		}

		if (organization.getNumberOfUsers() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfUsers\": ");

			sb.append(organization.getNumberOfUsers());
		}

		if (organization.getOrganizationAccounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"organizationAccounts\": ");

			sb.append("[");

			for (int i = 0; i < organization.getOrganizationAccounts().length;
				 i++) {

				sb.append(
					String.valueOf(organization.getOrganizationAccounts()[i]));

				if ((i + 1) < organization.getOrganizationAccounts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (organization.getOrganizationContactInformation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"organizationContactInformation\": ");

			sb.append(
				String.valueOf(
					organization.getOrganizationContactInformation()));
		}

		if (organization.getParentOrganization() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentOrganization\": ");

			sb.append(String.valueOf(organization.getParentOrganization()));
		}

		if (organization.getServices() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"services\": ");

			sb.append("[");

			for (int i = 0; i < organization.getServices().length; i++) {
				sb.append(String.valueOf(organization.getServices()[i]));

				if ((i + 1) < organization.getServices().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (organization.getUserAccounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userAccounts\": ");

			sb.append("[");

			for (int i = 0; i < organization.getUserAccounts().length; i++) {
				sb.append(String.valueOf(organization.getUserAccounts()[i]));

				if ((i + 1) < organization.getUserAccounts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OrganizationJSONParser organizationJSONParser =
			new OrganizationJSONParser();

		return organizationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Organization organization) {
		if (organization == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (organization.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(organization.getActions()));
		}

		if (organization.getChildOrganizations() == null) {
			map.put("childOrganizations", null);
		}
		else {
			map.put(
				"childOrganizations",
				String.valueOf(organization.getChildOrganizations()));
		}

		if (organization.getComment() == null) {
			map.put("comment", null);
		}
		else {
			map.put("comment", String.valueOf(organization.getComment()));
		}

		if (organization.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields", String.valueOf(organization.getCustomFields()));
		}

		if (organization.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(organization.getDateCreated()));
		}

		if (organization.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(organization.getDateModified()));
		}

		if (organization.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(organization.getId()));
		}

		if (organization.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(organization.getImage()));
		}

		if (organization.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put("keywords", String.valueOf(organization.getKeywords()));
		}

		if (organization.getLocation() == null) {
			map.put("location", null);
		}
		else {
			map.put("location", String.valueOf(organization.getLocation()));
		}

		if (organization.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(organization.getName()));
		}

		if (organization.getNumberOfAccounts() == null) {
			map.put("numberOfAccounts", null);
		}
		else {
			map.put(
				"numberOfAccounts",
				String.valueOf(organization.getNumberOfAccounts()));
		}

		if (organization.getNumberOfOrganizations() == null) {
			map.put("numberOfOrganizations", null);
		}
		else {
			map.put(
				"numberOfOrganizations",
				String.valueOf(organization.getNumberOfOrganizations()));
		}

		if (organization.getNumberOfUsers() == null) {
			map.put("numberOfUsers", null);
		}
		else {
			map.put(
				"numberOfUsers",
				String.valueOf(organization.getNumberOfUsers()));
		}

		if (organization.getOrganizationAccounts() == null) {
			map.put("organizationAccounts", null);
		}
		else {
			map.put(
				"organizationAccounts",
				String.valueOf(organization.getOrganizationAccounts()));
		}

		if (organization.getOrganizationContactInformation() == null) {
			map.put("organizationContactInformation", null);
		}
		else {
			map.put(
				"organizationContactInformation",
				String.valueOf(
					organization.getOrganizationContactInformation()));
		}

		if (organization.getParentOrganization() == null) {
			map.put("parentOrganization", null);
		}
		else {
			map.put(
				"parentOrganization",
				String.valueOf(organization.getParentOrganization()));
		}

		if (organization.getServices() == null) {
			map.put("services", null);
		}
		else {
			map.put("services", String.valueOf(organization.getServices()));
		}

		if (organization.getUserAccounts() == null) {
			map.put("userAccounts", null);
		}
		else {
			map.put(
				"userAccounts", String.valueOf(organization.getUserAccounts()));
		}

		return map;
	}

	public static class OrganizationJSONParser
		extends BaseJSONParser<Organization> {

		@Override
		protected Organization createDTO() {
			return new Organization();
		}

		@Override
		protected Organization[] createDTOArray(int size) {
			return new Organization[size];
		}

		@Override
		protected void setField(
			Organization organization, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					organization.setActions(
						(Map)OrganizationSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "childOrganizations")) {

				if (jsonParserFieldValue != null) {
					organization.setChildOrganizations(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrganizationSerDes.toDTO((String)object)
						).toArray(
							size -> new Organization[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "comment")) {
				if (jsonParserFieldValue != null) {
					organization.setComment((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					organization.setCustomFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CustomFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new CustomField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					organization.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					organization.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					organization.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					organization.setImage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					organization.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "location")) {
				if (jsonParserFieldValue != null) {
					organization.setLocation(
						LocationSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					organization.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfAccounts")) {
				if (jsonParserFieldValue != null) {
					organization.setNumberOfAccounts(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfOrganizations")) {

				if (jsonParserFieldValue != null) {
					organization.setNumberOfOrganizations(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfUsers")) {
				if (jsonParserFieldValue != null) {
					organization.setNumberOfUsers(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "organizationAccounts")) {

				if (jsonParserFieldValue != null) {
					organization.setOrganizationAccounts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AccountSerDes.toDTO((String)object)
						).toArray(
							size -> new Account[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"organizationContactInformation")) {

				if (jsonParserFieldValue != null) {
					organization.setOrganizationContactInformation(
						OrganizationContactInformationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentOrganization")) {

				if (jsonParserFieldValue != null) {
					organization.setParentOrganization(
						OrganizationSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "services")) {
				if (jsonParserFieldValue != null) {
					organization.setServices(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ServiceSerDes.toDTO((String)object)
						).toArray(
							size -> new Service[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userAccounts")) {
				if (jsonParserFieldValue != null) {
					organization.setUserAccounts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> UserAccountSerDes.toDTO((String)object)
						).toArray(
							size -> new UserAccount[size]
						));
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