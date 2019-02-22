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

package com.liferay.headless.foundation.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.foundation.dto.v1_0.ContactInformation;
import com.liferay.headless.foundation.dto.v1_0.Location;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Services;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.petra.function.UnsafeSupplier;

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
@XmlRootElement(name = "Organization")
public class OrganizationImpl implements Organization {

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
		this.comment = (String)comment;
	}

	@JsonIgnore
	public void setComment(
		UnsafeSupplier<String, Throwable> commentUnsafeSupplier) {

		try {
			comment = (String)commentUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = (ContactInformationImpl)contactInformation;
	}

	@JsonIgnore
	public void setContactInformation(
		UnsafeSupplier<ContactInformation, Throwable>
			contactInformationUnsafeSupplier) {

		try {
			contactInformation =
				(ContactInformationImpl)contactInformationUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setId(Long id) {
		this.id = (Long)id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = (Long)idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setLocation(Location location) {
		this.location = (LocationImpl)location;
	}

	@JsonIgnore
	public void setLocation(
		UnsafeSupplier<Location, Throwable> locationUnsafeSupplier) {

		try {
			location = (LocationImpl)locationUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setLogo(String logo) {
		this.logo = (String)logo;
	}

	@JsonIgnore
	public void setLogo(UnsafeSupplier<String, Throwable> logoUnsafeSupplier) {
		try {
			logo = (String)logoUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@JsonIgnore
	public void setMembers(
		UnsafeSupplier<UserAccount[], Throwable> membersUnsafeSupplier) {

		try {
			members = (UserAccountImpl[])membersUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setMembers(UserAccount[] members) {
		this.members = (UserAccountImpl[])members;
	}

	public void setMembersIds(Long[] membersIds) {
		this.membersIds = (Long[])membersIds;
	}

	@JsonIgnore
	public void setMembersIds(
		UnsafeSupplier<Long[], Throwable> membersIdsUnsafeSupplier) {

		try {
			membersIds = (Long[])membersIdsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setName(String name) {
		this.name = (String)name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
		try {
			name = (String)nameUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = (OrganizationImpl)parentOrganization;
	}

	@JsonIgnore
	public void setParentOrganization(
		UnsafeSupplier<Organization, Throwable>
			parentOrganizationUnsafeSupplier) {

		try {
			parentOrganization =
				(OrganizationImpl)parentOrganizationUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setParentOrganizationId(Long parentOrganizationId) {
		this.parentOrganizationId = (Long)parentOrganizationId;
	}

	@JsonIgnore
	public void setParentOrganizationId(
		UnsafeSupplier<Long, Throwable> parentOrganizationIdUnsafeSupplier) {

		try {
			parentOrganizationId =
				(Long)parentOrganizationIdUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setServices(Services[] services) {
		this.services = (ServicesImpl[])services;
	}

	@JsonIgnore
	public void setServices(
		UnsafeSupplier<Services[], Throwable> servicesUnsafeSupplier) {

		try {
			services = (ServicesImpl[])servicesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setSubOrganization(Organization[] subOrganization) {
		this.subOrganization = (OrganizationImpl[])subOrganization;
	}

	@JsonIgnore
	public void setSubOrganization(
		UnsafeSupplier<Organization[], Throwable>
			subOrganizationUnsafeSupplier) {

		try {
			subOrganization =
				(OrganizationImpl[])subOrganizationUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setSubOrganizationIds(Long[] subOrganizationIds) {
		this.subOrganizationIds = (Long[])subOrganizationIds;
	}

	@JsonIgnore
	public void setSubOrganizationIds(
		UnsafeSupplier<Long[], Throwable> subOrganizationIdsUnsafeSupplier) {

		try {
			subOrganizationIds = (Long[])subOrganizationIdsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected String comment;

	@GraphQLField
	@JsonProperty
	protected ContactInformationImpl contactInformation;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected LocationImpl location;

	@GraphQLField
	@JsonProperty
	protected String logo;

	@GraphQLField
	@JsonProperty
	protected UserAccountImpl[] members;

	@GraphQLField
	@JsonProperty
	protected Long[] membersIds;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected OrganizationImpl parentOrganization;

	@GraphQLField
	@JsonProperty
	protected Long parentOrganizationId;

	@GraphQLField
	@JsonProperty
	protected ServicesImpl[] services;

	@GraphQLField
	@JsonProperty
	protected OrganizationImpl[] subOrganization;

	@GraphQLField
	@JsonProperty
	protected Long[] subOrganizationIds;

}