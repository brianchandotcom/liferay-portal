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

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("UserAccount")
@XmlRootElement(name = "UserAccount")
public class UserAccount {

	public String getAdditionalName() {
		return additionalName;
	}

	public String getAlternateName() {
		return alternateName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public ContactInformation getContactInformation() {
		return contactInformation;
	}

	public String getDashboardURL() {
		return dashboardURL;
	}

	public String getEmail() {
		return email;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getHonorificPrefix() {
		return honorificPrefix;
	}

	public String getHonorificSuffix() {
		return honorificSuffix;
	}

	public Long getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public Organization[] getMyOrganizations() {
		return myOrganizations;
	}

	public Long[] getMyOrganizationsIds() {
		return myOrganizationsIds;
	}

	public String getName() {
		return name;
	}

	public String getProfileURL() {
		return profileURL;
	}

	public Role[] getRoles() {
		return roles;
	}

	public Long[] getRolesIds() {
		return rolesIds;
	}

	public String[] getTasksAssignedToMe() {
		return tasksAssignedToMe;
	}

	public String[] getTasksAssignedToMyRoles() {
		return tasksAssignedToMyRoles;
	}

	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}

	public void setAdditionalNameWithSupplier(
		UnsafeSupplier<String, Throwable> additionalNameUnsafeSupplier) {

		try {
			additionalName = additionalNameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	public void setAlternateNameWithSupplier(
		UnsafeSupplier<String, Throwable> alternateNameUnsafeSupplier) {

		try {
			alternateName = alternateNameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setBirthDateWithSupplier(
		UnsafeSupplier<Date, Throwable> birthDateUnsafeSupplier) {

		try {
			birthDate = birthDateUnsafeSupplier.get();
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

	public void setDashboardURL(String dashboardURL) {
		this.dashboardURL = dashboardURL;
	}

	public void setDashboardURLWithSupplier(
		UnsafeSupplier<String, Throwable> dashboardURLUnsafeSupplier) {

		try {
			dashboardURL = dashboardURLUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailWithSupplier(
		UnsafeSupplier<String, Throwable> emailUnsafeSupplier) {

		try {
			email = emailUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public void setFamilyNameWithSupplier(
		UnsafeSupplier<String, Throwable> familyNameUnsafeSupplier) {

		try {
			familyName = familyNameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setGivenNameWithSupplier(
		UnsafeSupplier<String, Throwable> givenNameUnsafeSupplier) {

		try {
			givenName = givenNameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setHonorificPrefix(String honorificPrefix) {
		this.honorificPrefix = honorificPrefix;
	}

	public void setHonorificPrefixWithSupplier(
		UnsafeSupplier<String, Throwable> honorificPrefixUnsafeSupplier) {

		try {
			honorificPrefix = honorificPrefixUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setHonorificSuffix(String honorificSuffix) {
		this.honorificSuffix = honorificSuffix;
	}

	public void setHonorificSuffixWithSupplier(
		UnsafeSupplier<String, Throwable> honorificSuffixUnsafeSupplier) {

		try {
			honorificSuffix = honorificSuffixUnsafeSupplier.get();
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

	public void setImage(String image) {
		this.image = image;
	}

	public void setImageWithSupplier(
		UnsafeSupplier<String, Throwable> imageUnsafeSupplier) {

		try {
			image = imageUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setJobTitleWithSupplier(
		UnsafeSupplier<String, Throwable> jobTitleUnsafeSupplier) {

		try {
			jobTitle = jobTitleUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setMyOrganizations(Organization[] myOrganizations) {
		this.myOrganizations = myOrganizations;
	}

	public void setMyOrganizationsWithSupplier(
		UnsafeSupplier<Organization[], Throwable>
			myOrganizationsUnsafeSupplier) {

		try {
			myOrganizations = myOrganizationsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setMyOrganizationsIds(Long[] myOrganizationsIds) {
		this.myOrganizationsIds = myOrganizationsIds;
	}

	public void setMyOrganizationsIdsWithSupplier(
		UnsafeSupplier<Long[], Throwable> myOrganizationsIdsUnsafeSupplier) {

		try {
			myOrganizationsIds = myOrganizationsIdsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameWithSupplier(
		UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setProfileURL(String profileURL) {
		this.profileURL = profileURL;
	}

	public void setProfileURLWithSupplier(
		UnsafeSupplier<String, Throwable> profileURLUnsafeSupplier) {

		try {
			profileURL = profileURLUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setRoles(Role[] roles) {
		this.roles = roles;
	}

	public void setRolesWithSupplier(
		UnsafeSupplier<Role[], Throwable> rolesUnsafeSupplier) {

		try {
			roles = rolesUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setRolesIds(Long[] rolesIds) {
		this.rolesIds = rolesIds;
	}

	public void setRolesIdsWithSupplier(
		UnsafeSupplier<Long[], Throwable> rolesIdsUnsafeSupplier) {

		try {
			rolesIds = rolesIdsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setTasksAssignedToMe(String[] tasksAssignedToMe) {
		this.tasksAssignedToMe = tasksAssignedToMe;
	}

	public void setTasksAssignedToMeWithSupplier(
		UnsafeSupplier<String[], Throwable> tasksAssignedToMeUnsafeSupplier) {

		try {
			tasksAssignedToMe = tasksAssignedToMeUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setTasksAssignedToMyRoles(String[] tasksAssignedToMyRoles) {
		this.tasksAssignedToMyRoles = tasksAssignedToMyRoles;
	}

	public void setTasksAssignedToMyRolesWithSupplier(
		UnsafeSupplier<String[], Throwable>
			tasksAssignedToMyRolesUnsafeSupplier) {

		try {
			tasksAssignedToMyRoles = tasksAssignedToMyRolesUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected String additionalName;

	@GraphQLField
	protected String alternateName;

	@GraphQLField
	protected Date birthDate;

	@GraphQLField
	protected ContactInformation contactInformation;

	@GraphQLField
	protected String dashboardURL;

	@GraphQLField
	protected String email;

	@GraphQLField
	protected String familyName;

	@GraphQLField
	protected String givenName;

	@GraphQLField
	protected String honorificPrefix;

	@GraphQLField
	protected String honorificSuffix;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected String image;

	@GraphQLField
	protected String jobTitle;

	@GraphQLField
	protected Organization[] myOrganizations;

	@GraphQLField
	protected Long[] myOrganizationsIds;

	@GraphQLField
	protected String name;

	@GraphQLField
	protected String profileURL;

	@GraphQLField
	protected Role[] roles;

	@GraphQLField
	protected Long[] rolesIds;

	@GraphQLField
	protected String[] tasksAssignedToMe;

	@GraphQLField
	protected String[] tasksAssignedToMyRoles;

}