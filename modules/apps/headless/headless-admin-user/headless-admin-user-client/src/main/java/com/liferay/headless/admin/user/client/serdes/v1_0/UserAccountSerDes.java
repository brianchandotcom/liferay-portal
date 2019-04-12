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

import com.liferay.headless.admin.user.client.dto.v1_0.OrganizationBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.SiteBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"additionalName\": ");

		if (userAccount.getAdditionalName() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getAdditionalName());
		}

		sb.append(", ");

		sb.append("\"alternateName\": ");

		if (userAccount.getAlternateName() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getAlternateName());
		}

		sb.append(", ");

		sb.append("\"birthDate\": ");

		if (userAccount.getBirthDate() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getBirthDate());
		}

		sb.append(", ");

		sb.append("\"contactInformation\": ");

		if (userAccount.getContactInformation() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getContactInformation());
		}

		sb.append(", ");

		sb.append("\"dashboardURL\": ");

		if (userAccount.getDashboardURL() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getDashboardURL());
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (userAccount.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getDateCreated());
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (userAccount.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getDateModified());
		}

		sb.append(", ");

		sb.append("\"email\": ");

		if (userAccount.getEmail() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getEmail());
		}

		sb.append(", ");

		sb.append("\"familyName\": ");

		if (userAccount.getFamilyName() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getFamilyName());
		}

		sb.append(", ");

		sb.append("\"givenName\": ");

		if (userAccount.getGivenName() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getGivenName());
		}

		sb.append(", ");

		sb.append("\"honorificPrefix\": ");

		if (userAccount.getHonorificPrefix() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getHonorificPrefix());
		}

		sb.append(", ");

		sb.append("\"honorificSuffix\": ");

		if (userAccount.getHonorificSuffix() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getHonorificSuffix());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (userAccount.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getId());
		}

		sb.append(", ");

		sb.append("\"image\": ");

		if (userAccount.getImage() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getImage());
		}

		sb.append(", ");

		sb.append("\"jobTitle\": ");

		if (userAccount.getJobTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getJobTitle());
		}

		sb.append(", ");

		sb.append("\"keywords\": ");

		if (userAccount.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getKeywords().length; i++) {
				sb.append("\"");
				sb.append(userAccount.getKeywords()[i]);
				sb.append("\"");

				if ((i + 1) < userAccount.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (userAccount.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getName());
		}

		sb.append(", ");

		sb.append("\"organizationBriefs\": ");

		if (userAccount.getOrganizationBriefs() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getOrganizationBriefs().length;
				 i++) {

				sb.append(userAccount.getOrganizationBriefs()[i]);

				if ((i + 1) < userAccount.getOrganizationBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"profileURL\": ");

		if (userAccount.getProfileURL() == null) {
			sb.append("null");
		}
		else {
			sb.append(userAccount.getProfileURL());
		}

		sb.append(", ");

		sb.append("\"roleBriefs\": ");

		if (userAccount.getRoleBriefs() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getRoleBriefs().length; i++) {
				sb.append(userAccount.getRoleBriefs()[i]);

				if ((i + 1) < userAccount.getRoleBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"siteBriefs\": ");

		if (userAccount.getSiteBriefs() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getSiteBriefs().length; i++) {
				sb.append(userAccount.getSiteBriefs()[i]);

				if ((i + 1) < userAccount.getSiteBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<UserAccount> userAccounts) {
		if (userAccounts == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (UserAccount userAccount : userAccounts) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(userAccount));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class UserAccountJSONParser
		extends BaseJSONParser<UserAccount> {

		protected UserAccount createDTO() {
			return new UserAccount();
		}

		protected UserAccount[] createDTOArray(int size) {
			return new UserAccount[size];
		}

		protected void setField(
			UserAccount userAccount, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "additionalName")) {
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
						_toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "contactInformation")) {

				if (jsonParserFieldValue != null) {
					userAccount.setContactInformation(
						ContactInformationSerDes.toDTO(
							(String)jsonParserFieldValue));
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
						_toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					userAccount.setDateModified(
						_toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					userAccount.setEmail((String)jsonParserFieldValue);
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
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

		private Date _toDate(String string) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");

				return dateFormat.parse(string);
			}
			catch (ParseException pe) {
				throw new IllegalArgumentException("Unable to parse " + string);
			}
		}

	}

}