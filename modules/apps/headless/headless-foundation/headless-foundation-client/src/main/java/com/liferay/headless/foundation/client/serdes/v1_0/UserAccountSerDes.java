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

package com.liferay.headless.foundation.client.serdes.v1_0;

import com.liferay.headless.foundation.client.dto.v1_0.Organization;
import com.liferay.headless.foundation.client.dto.v1_0.Role;
import com.liferay.headless.foundation.client.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.client.json.BaseJSONParser;

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

		sb.append("\"");
		sb.append(userAccount.getAdditionalName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"alternateName\": ");

		sb.append("\"");
		sb.append(userAccount.getAlternateName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"birthDate\": ");

		sb.append("\"");
		sb.append(userAccount.getBirthDate());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"contactInformation\": ");

		sb.append(userAccount.getContactInformation());
		sb.append(", ");

		sb.append("\"dashboardURL\": ");

		sb.append("\"");
		sb.append(userAccount.getDashboardURL());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(userAccount.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(userAccount.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"email\": ");

		sb.append("\"");
		sb.append(userAccount.getEmail());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"familyName\": ");

		sb.append("\"");
		sb.append(userAccount.getFamilyName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"givenName\": ");

		sb.append("\"");
		sb.append(userAccount.getGivenName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"honorificPrefix\": ");

		sb.append("\"");
		sb.append(userAccount.getHonorificPrefix());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"honorificSuffix\": ");

		sb.append("\"");
		sb.append(userAccount.getHonorificSuffix());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(userAccount.getId());
		sb.append(", ");

		sb.append("\"image\": ");

		sb.append("\"");
		sb.append(userAccount.getImage());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"jobTitle\": ");

		sb.append("\"");
		sb.append(userAccount.getJobTitle());
		sb.append("\"");
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

		sb.append("\"myOrganizations\": ");

		if (userAccount.getMyOrganizations() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getMyOrganizations().length; i++) {
				sb.append(userAccount.getMyOrganizations()[i]);

				if ((i + 1) < userAccount.getMyOrganizations().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"myOrganizationsIds\": ");

		if (userAccount.getMyOrganizationsIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getMyOrganizationsIds().length;
				 i++) {

				sb.append(userAccount.getMyOrganizationsIds()[i]);

				if ((i + 1) < userAccount.getMyOrganizationsIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(userAccount.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"profileURL\": ");

		sb.append("\"");
		sb.append(userAccount.getProfileURL());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"roles\": ");

		if (userAccount.getRoles() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getRoles().length; i++) {
				sb.append(userAccount.getRoles()[i]);

				if ((i + 1) < userAccount.getRoles().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"rolesIds\": ");

		if (userAccount.getRolesIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getRolesIds().length; i++) {
				sb.append(userAccount.getRolesIds()[i]);

				if ((i + 1) < userAccount.getRolesIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"tasksAssignedToMe\": ");

		if (userAccount.getTasksAssignedToMe() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getTasksAssignedToMe().length;
				 i++) {

				sb.append("\"");
				sb.append(userAccount.getTasksAssignedToMe()[i]);
				sb.append("\"");

				if ((i + 1) < userAccount.getTasksAssignedToMe().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"tasksAssignedToMyRoles\": ");

		if (userAccount.getTasksAssignedToMyRoles() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < userAccount.getTasksAssignedToMyRoles().length;
				 i++) {

				sb.append("\"");
				sb.append(userAccount.getTasksAssignedToMyRoles()[i]);
				sb.append("\"");

				if ((i + 1) < userAccount.getTasksAssignedToMyRoles().length) {
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
					userAccount.setBirthDate((Date)jsonParserFieldValue);
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
					userAccount.setDateCreated((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					userAccount.setDateModified((Date)jsonParserFieldValue);
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
					userAccount.setId((Long)jsonParserFieldValue);
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
			else if (Objects.equals(jsonParserFieldName, "myOrganizations")) {
				if (jsonParserFieldValue != null) {
					userAccount.setMyOrganizations(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrganizationSerDes.toDTO((String)object)
						).toArray(
							size -> new Organization[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "myOrganizationsIds")) {

				if (jsonParserFieldValue != null) {
					userAccount.setMyOrganizationsIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					userAccount.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "profileURL")) {
				if (jsonParserFieldValue != null) {
					userAccount.setProfileURL((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roles")) {
				if (jsonParserFieldValue != null) {
					userAccount.setRoles(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RoleSerDes.toDTO((String)object)
						).toArray(
							size -> new Role[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rolesIds")) {
				if (jsonParserFieldValue != null) {
					userAccount.setRolesIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tasksAssignedToMe")) {
				if (jsonParserFieldValue != null) {
					userAccount.setTasksAssignedToMe(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "tasksAssignedToMyRoles")) {

				if (jsonParserFieldValue != null) {
					userAccount.setTasksAssignedToMyRoles(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}