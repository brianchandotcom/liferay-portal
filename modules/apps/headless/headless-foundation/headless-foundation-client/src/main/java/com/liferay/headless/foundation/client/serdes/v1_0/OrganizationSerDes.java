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
import com.liferay.headless.foundation.client.dto.v1_0.Service;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"comment\": ");

		sb.append("\"");
		sb.append(organization.getComment());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"contactInformation\": ");

		sb.append(organization.getContactInformation());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(organization.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(organization.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(organization.getId());
		sb.append(", ");

		sb.append("\"image\": ");

		sb.append("\"");
		sb.append(organization.getImage());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"keywords\": ");

		if (organization.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < organization.getKeywords().length; i++) {
				sb.append("\"");
				sb.append(organization.getKeywords()[i]);
				sb.append("\"");

				if ((i + 1) < organization.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"location\": ");

		sb.append(organization.getLocation());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(organization.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"numberOfOrganizations\": ");

		sb.append(organization.getNumberOfOrganizations());
		sb.append(", ");

		sb.append("\"parentOrganization\": ");

		sb.append(organization.getParentOrganization());
		sb.append(", ");

		sb.append("\"parentOrganizationId\": ");

		sb.append(organization.getParentOrganizationId());
		sb.append(", ");

		sb.append("\"services\": ");

		if (organization.getServices() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < organization.getServices().length; i++) {
				sb.append(organization.getServices()[i]);

				if ((i + 1) < organization.getServices().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Organization> organizations) {
		if (organizations == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Organization organization : organizations) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(organization));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class OrganizationJSONParser
		extends BaseJSONParser<Organization> {

		protected Organization createDTO() {
			return new Organization();
		}

		protected Organization[] createDTOArray(int size) {
			return new Organization[size];
		}

		protected void setField(
			Organization organization, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "comment")) {
				if (jsonParserFieldValue != null) {
					organization.setComment((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "contactInformation")) {

				if (jsonParserFieldValue != null) {
					organization.setContactInformation(
						ContactInformationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					organization.setDateCreated((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					organization.setDateModified((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					organization.setId((Long)jsonParserFieldValue);
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
			else if (Objects.equals(
						jsonParserFieldName, "numberOfOrganizations")) {

				if (jsonParserFieldValue != null) {
					organization.setNumberOfOrganizations(
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentOrganization")) {

				if (jsonParserFieldValue != null) {
					organization.setParentOrganization(
						OrganizationSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentOrganizationId")) {

				if (jsonParserFieldValue != null) {
					organization.setParentOrganizationId(
						(Long)jsonParserFieldValue);
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
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}