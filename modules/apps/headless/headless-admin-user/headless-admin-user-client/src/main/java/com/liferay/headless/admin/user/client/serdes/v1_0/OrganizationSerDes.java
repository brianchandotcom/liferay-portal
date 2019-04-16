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

import com.liferay.headless.admin.user.client.dto.v1_0.Organization;
import com.liferay.headless.admin.user.client.dto.v1_0.Service;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (organization.getComment() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"comment\":");

			sb.append("\"");

			sb.append(organization.getComment());

			sb.append("\"");
		}

		if (organization.getContactInformation() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"contactInformation\":");

			sb.append(
				ContactInformationSerDes.toJSON(
					organization.getContactInformation()));
		}

		if (organization.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(organization.getDateCreated()));

			sb.append("\"");
		}

		if (organization.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(organization.getDateModified()));

			sb.append("\"");
		}

		if (organization.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(organization.getId());
		}

		if (organization.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"image\":");

			sb.append("\"");

			sb.append(organization.getImage());

			sb.append("\"");
		}

		if (organization.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"keywords\":");

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

		if (organization.getLocation() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"location\":");

			sb.append(LocationSerDes.toJSON(organization.getLocation()));
		}

		if (organization.getName() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(organization.getName());

			sb.append("\"");
		}

		if (organization.getNumberOfOrganizations() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"numberOfOrganizations\":");

			sb.append(organization.getNumberOfOrganizations());
		}

		if (organization.getParentOrganization() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"parentOrganization\":");

			sb.append(
				OrganizationSerDes.toJSON(
					organization.getParentOrganization()));
		}

		if (organization.getServices() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"services\":");

			sb.append("[");

			for (int i = 0; i < organization.getServices().length; i++) {
				sb.append(ServiceSerDes.toJSON(organization.getServices()[i]));

				if ((i + 1) < organization.getServices().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Organization organization) {
		if (organization == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (organization.getComment() == null) {
			map.put("comment", null);
		}
		else {
			map.put("comment", String.valueOf(organization.getComment()));
		}

		if (organization.getContactInformation() == null) {
			map.put("contactInformation", null);
		}
		else {
			map.put(
				"contactInformation",
				ContactInformationSerDes.toJSON(
					organization.getContactInformation()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(organization.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(organization.getDateModified()));

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
			map.put(
				"location", LocationSerDes.toJSON(organization.getLocation()));
		}

		if (organization.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(organization.getName()));
		}

		if (organization.getNumberOfOrganizations() == null) {
			map.put("numberOfOrganizations", null);
		}
		else {
			map.put(
				"numberOfOrganizations",
				String.valueOf(organization.getNumberOfOrganizations()));
		}

		if (organization.getParentOrganization() == null) {
			map.put("parentOrganization", null);
		}
		else {
			map.put(
				"parentOrganization",
				OrganizationSerDes.toJSON(
					organization.getParentOrganization()));
		}

		if (organization.getServices() == null) {
			map.put("services", null);
		}
		else {
			map.put("services", String.valueOf(organization.getServices()));
		}

		return map;
	}

	private static class OrganizationJSONParser
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
					organization.setId(
						Long.valueOf((String)jsonParserFieldValue));
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
						Integer.valueOf((String)jsonParserFieldValue));
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
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}