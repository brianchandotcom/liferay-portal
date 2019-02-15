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

	public void setCommentWithSupplier(
		UnsafeSupplier<String, Throwable> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
	}

	public void setContactInformationWithSupplier(
		UnsafeSupplier<ContactInformation, Throwable>
			contactInformationUnsafeSupplier) {

		try {
			contactInformation = contactInformationUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdWithSupplier(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLocationWithSupplier(
		UnsafeSupplier<Location, Throwable> locationUnsafeSupplier) {

		try {
			location = locationUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setLogoWithSupplier(UnsafeSupplier<String, Throwable> logoUnsafeSupplier) {
		try {
			logo = logoUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setMembersWithSupplier(
		UnsafeSupplier<UserAccount[], Throwable> membersUnsafeSupplier) {

		try {
			members = membersUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setMembers(UserAccount[] members) {
		this.members = members;
	}

	public void setMembersIds(Long[] membersIds) {
		this.membersIds = membersIds;
	}

	public void setMembersIdsWithSupplier(
		UnsafeSupplier<Long[], Throwable> membersIdsUnsafeSupplier) {

		try {
			membersIds = membersIdsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameWithSupplier(UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}

	public void setParentOrganizationWithSupplier(
		UnsafeSupplier<Organization, Throwable>
			parentOrganizationUnsafeSupplier) {

		try {
			parentOrganization = parentOrganizationUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setParentOrganizationId(Long parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public void setParentOrganizationIdWithSupplier(
		UnsafeSupplier<Long, Throwable> parentOrganizationIdUnsafeSupplier) {

		try {
			parentOrganizationId = parentOrganizationIdUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setServices(Services[] services) {
		this.services = services;
	}

	public void setServicesWithSupplier(
		UnsafeSupplier<Services[], Throwable> servicesUnsafeSupplier) {

		try {
			services = servicesUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setSubOrganization(Organization[] subOrganization) {
		this.subOrganization = subOrganization;
	}

	public void setSubOrganizationWithSupplier(
		UnsafeSupplier<Organization[], Throwable>
			subOrganizationUnsafeSupplier) {

		try {
			subOrganization = subOrganizationUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setSubOrganizationIds(Long[] subOrganizationIds) {
		this.subOrganizationIds = subOrganizationIds;
	}

	public void setSubOrganizationIdsWithSupplier(
		UnsafeSupplier<Long[], Throwable> subOrganizationIdsUnsafeSupplier) {

		try {
			subOrganizationIds = subOrganizationIdsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected String comment;

	@GraphQLField
	protected ContactInformation contactInformation;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected Location location;

	@GraphQLField
	protected String logo;

	@GraphQLField
	protected UserAccount[] members;

	@GraphQLField
	protected Long[] membersIds;

	@GraphQLField
	protected String name;

	@GraphQLField
	protected Organization parentOrganization;

	@GraphQLField
	protected Long parentOrganizationId;

	@GraphQLField
	protected Services[] services;

	@GraphQLField
	protected Organization[] subOrganization;

	@GraphQLField
	protected Long[] subOrganizationIds;

}