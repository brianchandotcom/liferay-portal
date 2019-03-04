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

package com.liferay.headless.foundation.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Organization")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "Organization")
public class Organization {

	public String getComment() {
		return comment;
	}

	public ContactInformation getContactInformation() {
		return contactInformation;
	}

	public Long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public String getLogo() {
		return logo;
	}

	public UserAccount[] getMembers() {
		return members;
	}

	public Long[] getMembersIds() {
		return membersIds;
	}

	public String getName() {
		return name;
	}

	public Organization getParentOrganization() {
		return parentOrganization;
	}

	public Long getParentOrganizationId() {
		return parentOrganizationId;
	}

	public Services[] getServices() {
		return services;
	}

	public Organization[] getSubOrganization() {
		return subOrganization;
	}

	public Long[] getSubOrganizationIds() {
		return subOrganizationIds;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonIgnore
	public void setComment(
		UnsafeSupplier<String, Exception> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
	}

	@JsonIgnore
	public void setContactInformation(
		UnsafeSupplier<ContactInformation, Exception>
			contactInformationUnsafeSupplier) {

		try {
			contactInformation = contactInformationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@JsonIgnore
	public void setLocation(
		UnsafeSupplier<Location, Exception> locationUnsafeSupplier) {

		try {
			location = locationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@JsonIgnore
	public void setLogo(UnsafeSupplier<String, Exception> logoUnsafeSupplier) {
		try {
			logo = logoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@JsonIgnore
	public void setMembers(
		UnsafeSupplier<UserAccount[], Exception> membersUnsafeSupplier) {

		try {
			members = membersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setMembers(UserAccount[] members) {
		this.members = members;
	}

	public void setMembersIds(Long[] membersIds) {
		this.membersIds = membersIds;
	}

	@JsonIgnore
	public void setMembersIds(
		UnsafeSupplier<Long[], Exception> membersIdsUnsafeSupplier) {

		try {
			membersIds = membersIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}

	@JsonIgnore
	public void setParentOrganization(
		UnsafeSupplier<Organization, Exception>
			parentOrganizationUnsafeSupplier) {

		try {
			parentOrganization = parentOrganizationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setParentOrganizationId(Long parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	@JsonIgnore
	public void setParentOrganizationId(
		UnsafeSupplier<Long, Exception> parentOrganizationIdUnsafeSupplier) {

		try {
			parentOrganizationId = parentOrganizationIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setServices(Services[] services) {
		this.services = services;
	}

	@JsonIgnore
	public void setServices(
		UnsafeSupplier<Services[], Exception> servicesUnsafeSupplier) {

		try {
			services = servicesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSubOrganization(Organization[] subOrganization) {
		this.subOrganization = subOrganization;
	}

	@JsonIgnore
	public void setSubOrganization(
		UnsafeSupplier<Organization[], Exception>
			subOrganizationUnsafeSupplier) {

		try {
			subOrganization = subOrganizationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSubOrganizationIds(Long[] subOrganizationIds) {
		this.subOrganizationIds = subOrganizationIds;
	}

	@JsonIgnore
	public void setSubOrganizationIds(
		UnsafeSupplier<Long[], Exception> subOrganizationIdsUnsafeSupplier) {

		try {
			subOrganizationIds = subOrganizationIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(56);

		sb.append("{");

		sb.append("\"comment\": ");

		sb.append("\"");
		sb.append(comment);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"contactInformation\": ");

		sb.append(contactInformation);
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"location\": ");

		sb.append(location);
		sb.append(", ");

		sb.append("\"logo\": ");

		sb.append("\"");
		sb.append(logo);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"members\": ");

		sb.append("\"");
		sb.append(members);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"membersIds\": ");

		sb.append("\"");
		sb.append(membersIds);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"parentOrganization\": ");

		sb.append(parentOrganization);
		sb.append(", ");

		sb.append("\"parentOrganizationId\": ");

		sb.append(parentOrganizationId);
		sb.append(", ");

		sb.append("\"services\": ");

		sb.append("\"");
		sb.append(services);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"subOrganization\": ");

		sb.append("\"");
		sb.append(subOrganization);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"subOrganizationIds\": ");

		sb.append("\"");
		sb.append(subOrganizationIds);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String comment;

	@GraphQLField
	@JsonProperty
	protected ContactInformation contactInformation;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected Location location;

	@GraphQLField
	@JsonProperty
	protected String logo;

	@GraphQLField
	@JsonProperty
	protected UserAccount[] members;

	@GraphQLField
	@JsonProperty
	protected Long[] membersIds;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected Organization parentOrganization;

	@GraphQLField
	@JsonProperty
	protected Long parentOrganizationId;

	@GraphQLField
	@JsonProperty
	protected Services[] services;

	@GraphQLField
	@JsonProperty
	protected Organization[] subOrganization;

	@GraphQLField
	@JsonProperty
	protected Long[] subOrganizationIds;

}