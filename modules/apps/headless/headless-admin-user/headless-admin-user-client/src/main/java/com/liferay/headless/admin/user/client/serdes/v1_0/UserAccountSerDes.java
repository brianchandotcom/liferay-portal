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

import com.liferay.headless.admin.user.client.dto.v1_0.CustomField;
import com.liferay.headless.admin.user.client.dto.v1_0.OrganizationBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.SiteBrief;
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
public class UserAccountSerDes {

	public static UserAccount toDTO(String json) {
		UserAccountJSONParser userAccountJSONParser =
			new UserAccountJSONParser();

		return userAccountJSONParser.parseToDTO(json);
	}

	public static UserAccount[] toDTOs(String json) {
		UserAccountJSONParser userAccountJSONParser =
			new UserAccountJSONParser();

		return userAccountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UserAccount userAccount) {
		if (userAccount == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (userAccount.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(userAccount.getActions()));
		}

		if (userAccount.getAdditionalName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"additionalName\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getAdditionalName()));

			sb.append("\"");
		}

		if (userAccount.getAlternateName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"alternateName\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getAlternateName()));

			sb.append("\"");
		}

		if (userAccount.getBirthDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"birthDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(userAccount.getBirthDate()));

			sb.append("\"");
		}

		if (userAccount.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append("[");

			for (int i = 0; i < userAccount.getCustomFields().length; i++) {
				sb.append(String.valueOf(userAccount.getCustomFields()[i]));

				if ((i + 1) < userAccount.getCustomFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userAccount.getDashboardURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dashboardURL\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getDashboardURL()));

			sb.append("\"");
		}

		if (userAccount.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(userAccount.getDateCreated()));

			sb.append("\"");
		}

		if (userAccount.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(userAccount.getDateModified()));

			sb.append("\"");
		}

		if (userAccount.getEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddress\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getEmailAddress()));

			sb.append("\"");
		}

		if (userAccount.getFamilyName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"familyName\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getFamilyName()));

			sb.append("\"");
		}

		if (userAccount.getGivenName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"givenName\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getGivenName()));

			sb.append("\"");
		}

		if (userAccount.getHonorificPrefix() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"honorificPrefix\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getHonorificPrefix()));

			sb.append("\"");
		}

		if (userAccount.getHonorificSuffix() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"honorificSuffix\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getHonorificSuffix()));

			sb.append("\"");
		}

		if (userAccount.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(userAccount.getId());
		}

		if (userAccount.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getImage()));

			sb.append("\"");
		}

		if (userAccount.getJobTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jobTitle\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getJobTitle()));

			sb.append("\"");
		}

		if (userAccount.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("[");

			for (int i = 0; i < userAccount.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(_escape(userAccount.getKeywords()[i]));

				sb.append("\"");

				if ((i + 1) < userAccount.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userAccount.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getName()));

			sb.append("\"");
		}

		if (userAccount.getOrganizationBriefs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"organizationBriefs\": ");

			sb.append("[");

			for (int i = 0; i < userAccount.getOrganizationBriefs().length;
				 i++) {

				sb.append(
					String.valueOf(userAccount.getOrganizationBriefs()[i]));

				if ((i + 1) < userAccount.getOrganizationBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userAccount.getProfileURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"profileURL\": ");

			sb.append("\"");

			sb.append(_escape(userAccount.getProfileURL()));

			sb.append("\"");
		}

		if (userAccount.getRoleBriefs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleBriefs\": ");

			sb.append("[");

			for (int i = 0; i < userAccount.getRoleBriefs().length; i++) {
				sb.append(String.valueOf(userAccount.getRoleBriefs()[i]));

				if ((i + 1) < userAccount.getRoleBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userAccount.getSiteBriefs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteBriefs\": ");

			sb.append("[");

			for (int i = 0; i < userAccount.getSiteBriefs().length; i++) {
				sb.append(String.valueOf(userAccount.getSiteBriefs()[i]));

				if ((i + 1) < userAccount.getSiteBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userAccount.getUserAccountContactInformation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userAccountContactInformation\": ");

			sb.append(
				String.valueOf(userAccount.getUserAccountContactInformation()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UserAccountJSONParser userAccountJSONParser =
			new UserAccountJSONParser();

		return userAccountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(UserAccount userAccount) {
		if (userAccount == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (userAccount.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(userAccount.getActions()));
		}

		if (userAccount.getAdditionalName() == null) {
			map.put("additionalName", null);
		}
		else {
			map.put(
				"additionalName",
				String.valueOf(userAccount.getAdditionalName()));
		}

		if (userAccount.getAlternateName() == null) {
			map.put("alternateName", null);
		}
		else {
			map.put(
				"alternateName",
				String.valueOf(userAccount.getAlternateName()));
		}

		map.put(
			"birthDate",
			liferayToJSONDateFormat.format(userAccount.getBirthDate()));

		if (userAccount.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields", String.valueOf(userAccount.getCustomFields()));
		}

		if (userAccount.getDashboardURL() == null) {
			map.put("dashboardURL", null);
		}
		else {
			map.put(
				"dashboardURL", String.valueOf(userAccount.getDashboardURL()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(userAccount.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(userAccount.getDateModified()));

		if (userAccount.getEmailAddress() == null) {
			map.put("emailAddress", null);
		}
		else {
			map.put(
				"emailAddress", String.valueOf(userAccount.getEmailAddress()));
		}

		if (userAccount.getFamilyName() == null) {
			map.put("familyName", null);
		}
		else {
			map.put("familyName", String.valueOf(userAccount.getFamilyName()));
		}

		if (userAccount.getGivenName() == null) {
			map.put("givenName", null);
		}
		else {
			map.put("givenName", String.valueOf(userAccount.getGivenName()));
		}

		if (userAccount.getHonorificPrefix() == null) {
			map.put("honorificPrefix", null);
		}
		else {
			map.put(
				"honorificPrefix",
				String.valueOf(userAccount.getHonorificPrefix()));
		}

		if (userAccount.getHonorificSuffix() == null) {
			map.put("honorificSuffix", null);
		}
		else {
			map.put(
				"honorificSuffix",
				String.valueOf(userAccount.getHonorificSuffix()));
		}

		if (userAccount.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(userAccount.getId()));
		}

		if (userAccount.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(userAccount.getImage()));
		}

		if (userAccount.getJobTitle() == null) {
			map.put("jobTitle", null);
		}
		else {
			map.put("jobTitle", String.valueOf(userAccount.getJobTitle()));
		}

		if (userAccount.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put("keywords", String.valueOf(userAccount.getKeywords()));
		}

		if (userAccount.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(userAccount.getName()));
		}

		if (userAccount.getOrganizationBriefs() == null) {
			map.put("organizationBriefs", null);
		}
		else {
			map.put(
				"organizationBriefs",
				String.valueOf(userAccount.getOrganizationBriefs()));
		}

		if (userAccount.getProfileURL() == null) {
			map.put("profileURL", null);
		}
		else {
			map.put("profileURL", String.valueOf(userAccount.getProfileURL()));
		}

		if (userAccount.getRoleBriefs() == null) {
			map.put("roleBriefs", null);
		}
		else {
			map.put("roleBriefs", String.valueOf(userAccount.getRoleBriefs()));
		}

		if (userAccount.getSiteBriefs() == null) {
			map.put("siteBriefs", null);
		}
		else {
			map.put("siteBriefs", String.valueOf(userAccount.getSiteBriefs()));
		}

		if (userAccount.getUserAccountContactInformation() == null) {
			map.put("userAccountContactInformation", null);
		}
		else {
			map.put(
				"userAccountContactInformation",
				String.valueOf(userAccount.getUserAccountContactInformation()));
		}

		return map;
	}

	public static class UserAccountJSONParser
		extends BaseJSONParser<UserAccount> {

		@Override
		protected UserAccount createDTO() {
			return new UserAccount();
		}

		@Override
		protected UserAccount[] createDTOArray(int size) {
			return new UserAccount[size];
		}

		@Override
		protected void setField(
			UserAccount userAccount, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					userAccount.setActions(
						(Map)UserAccountSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "additionalName")) {
				if (jsonParserFieldValue != null) {
					userAccount.setAdditionalName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "alternateName")) {
				if (jsonParserFieldValue != null) {
					userAccount.setAlternateName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "birthDate")) {
				if (jsonParserFieldValue != null) {
					userAccount.setBirthDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					userAccount.setCustomFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CustomFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new CustomField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dashboardURL")) {
				if (jsonParserFieldValue != null) {
					userAccount.setDashboardURL((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					userAccount.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					userAccount.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				if (jsonParserFieldValue != null) {
					userAccount.setEmailAddress((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "familyName")) {
				if (jsonParserFieldValue != null) {
					userAccount.setFamilyName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "givenName")) {
				if (jsonParserFieldValue != null) {
					userAccount.setGivenName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "honorificPrefix")) {
				if (jsonParserFieldValue != null) {
					userAccount.setHonorificPrefix(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "honorificSuffix")) {
				if (jsonParserFieldValue != null) {
					userAccount.setHonorificSuffix(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					userAccount.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					userAccount.setImage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jobTitle")) {
				if (jsonParserFieldValue != null) {
					userAccount.setJobTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					userAccount.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					userAccount.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "organizationBriefs")) {

				if (jsonParserFieldValue != null) {
					userAccount.setOrganizationBriefs(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrganizationBriefSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new OrganizationBrief[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "profileURL")) {
				if (jsonParserFieldValue != null) {
					userAccount.setProfileURL((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleBriefs")) {
				if (jsonParserFieldValue != null) {
					userAccount.setRoleBriefs(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RoleBriefSerDes.toDTO((String)object)
						).toArray(
							size -> new RoleBrief[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteBriefs")) {
				if (jsonParserFieldValue != null) {
					userAccount.setSiteBriefs(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> SiteBriefSerDes.toDTO((String)object)
						).toArray(
							size -> new SiteBrief[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "userAccountContactInformation")) {

				if (jsonParserFieldValue != null) {
					userAccount.setUserAccountContactInformation(
						UserAccountContactInformationSerDes.toDTO(
							(String)jsonParserFieldValue));
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