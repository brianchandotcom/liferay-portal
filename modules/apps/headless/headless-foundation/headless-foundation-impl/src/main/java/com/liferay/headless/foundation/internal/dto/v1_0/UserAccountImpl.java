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
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Role;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
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
public class UserAccountImpl implements UserAccount {

	public String getAdditionalName() {
			return additionalName;
	}

	public void setAdditionalName(
			String additionalName) {

			this.additionalName = (String)additionalName;
	}

	@JsonIgnore
	public void setAdditionalName(
			UnsafeSupplier<String, Throwable>
				additionalNameUnsafeSupplier) {

			try {
				additionalName =
					(String)additionalNameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String additionalName;
	public String getAlternateName() {
			return alternateName;
	}

	public void setAlternateName(
			String alternateName) {

			this.alternateName = (String)alternateName;
	}

	@JsonIgnore
	public void setAlternateName(
			UnsafeSupplier<String, Throwable>
				alternateNameUnsafeSupplier) {

			try {
				alternateName =
					(String)alternateNameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String alternateName;
	public Date getBirthDate() {
			return birthDate;
	}

	public void setBirthDate(
			Date birthDate) {

			this.birthDate = (Date)birthDate;
	}

	@JsonIgnore
	public void setBirthDate(
			UnsafeSupplier<Date, Throwable>
				birthDateUnsafeSupplier) {

			try {
				birthDate =
					(Date)birthDateUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date birthDate;
	public ContactInformation getContactInformation() {
			return contactInformation;
	}

	public void setContactInformation(
			ContactInformation contactInformation) {

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

	@GraphQLField
	@JsonProperty
	protected ContactInformationImpl contactInformation;
	public String getDashboardURL() {
			return dashboardURL;
	}

	public void setDashboardURL(
			String dashboardURL) {

			this.dashboardURL = (String)dashboardURL;
	}

	@JsonIgnore
	public void setDashboardURL(
			UnsafeSupplier<String, Throwable>
				dashboardURLUnsafeSupplier) {

			try {
				dashboardURL =
					(String)dashboardURLUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String dashboardURL;
	public String getEmail() {
			return email;
	}

	public void setEmail(
			String email) {

			this.email = (String)email;
	}

	@JsonIgnore
	public void setEmail(
			UnsafeSupplier<String, Throwable>
				emailUnsafeSupplier) {

			try {
				email =
					(String)emailUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String email;
	public String getFamilyName() {
			return familyName;
	}

	public void setFamilyName(
			String familyName) {

			this.familyName = (String)familyName;
	}

	@JsonIgnore
	public void setFamilyName(
			UnsafeSupplier<String, Throwable>
				familyNameUnsafeSupplier) {

			try {
				familyName =
					(String)familyNameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String familyName;
	public String getGivenName() {
			return givenName;
	}

	public void setGivenName(
			String givenName) {

			this.givenName = (String)givenName;
	}

	@JsonIgnore
	public void setGivenName(
			UnsafeSupplier<String, Throwable>
				givenNameUnsafeSupplier) {

			try {
				givenName =
					(String)givenNameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String givenName;
	public String getHonorificPrefix() {
			return honorificPrefix;
	}

	public void setHonorificPrefix(
			String honorificPrefix) {

			this.honorificPrefix = (String)honorificPrefix;
	}

	@JsonIgnore
	public void setHonorificPrefix(
			UnsafeSupplier<String, Throwable>
				honorificPrefixUnsafeSupplier) {

			try {
				honorificPrefix =
					(String)honorificPrefixUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String honorificPrefix;
	public String getHonorificSuffix() {
			return honorificSuffix;
	}

	public void setHonorificSuffix(
			String honorificSuffix) {

			this.honorificSuffix = (String)honorificSuffix;
	}

	@JsonIgnore
	public void setHonorificSuffix(
			UnsafeSupplier<String, Throwable>
				honorificSuffixUnsafeSupplier) {

			try {
				honorificSuffix =
					(String)honorificSuffixUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String honorificSuffix;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = (Long)id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					(Long)idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public String getImage() {
			return image;
	}

	public void setImage(
			String image) {

			this.image = (String)image;
	}

	@JsonIgnore
	public void setImage(
			UnsafeSupplier<String, Throwable>
				imageUnsafeSupplier) {

			try {
				image =
					(String)imageUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String image;
	public String getJobTitle() {
			return jobTitle;
	}

	public void setJobTitle(
			String jobTitle) {

			this.jobTitle = (String)jobTitle;
	}

	@JsonIgnore
	public void setJobTitle(
			UnsafeSupplier<String, Throwable>
				jobTitleUnsafeSupplier) {

			try {
				jobTitle =
					(String)jobTitleUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String jobTitle;
	public Organization[] getMyOrganizations() {
			return myOrganizations;
	}

	public void setMyOrganizations(
			Organization[] myOrganizations) {

			this.myOrganizations = (OrganizationImpl[])myOrganizations;
	}

	@JsonIgnore
	public void setMyOrganizations(
			UnsafeSupplier<Organization[], Throwable>
				myOrganizationsUnsafeSupplier) {

			try {
				myOrganizations =
					(OrganizationImpl[])myOrganizationsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected OrganizationImpl[] myOrganizations;
	public Long[] getMyOrganizationsIds() {
			return myOrganizationsIds;
	}

	public void setMyOrganizationsIds(
			Long[] myOrganizationsIds) {

			this.myOrganizationsIds = (Long[])myOrganizationsIds;
	}

	@JsonIgnore
	public void setMyOrganizationsIds(
			UnsafeSupplier<Long[], Throwable>
				myOrganizationsIdsUnsafeSupplier) {

			try {
				myOrganizationsIds =
					(Long[])myOrganizationsIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] myOrganizationsIds;
	public String getName() {
			return name;
	}

	public void setName(
			String name) {

			this.name = (String)name;
	}

	@JsonIgnore
	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier) {

			try {
				name =
					(String)nameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String name;
	public String getProfileURL() {
			return profileURL;
	}

	public void setProfileURL(
			String profileURL) {

			this.profileURL = (String)profileURL;
	}

	@JsonIgnore
	public void setProfileURL(
			UnsafeSupplier<String, Throwable>
				profileURLUnsafeSupplier) {

			try {
				profileURL =
					(String)profileURLUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String profileURL;
	public Role[] getRoles() {
			return roles;
	}

	public void setRoles(
			Role[] roles) {

			this.roles = (RoleImpl[])roles;
	}

	@JsonIgnore
	public void setRoles(
			UnsafeSupplier<Role[], Throwable>
				rolesUnsafeSupplier) {

			try {
				roles =
					(RoleImpl[])rolesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected RoleImpl[] roles;
	public Long[] getRolesIds() {
			return rolesIds;
	}

	public void setRolesIds(
			Long[] rolesIds) {

			this.rolesIds = (Long[])rolesIds;
	}

	@JsonIgnore
	public void setRolesIds(
			UnsafeSupplier<Long[], Throwable>
				rolesIdsUnsafeSupplier) {

			try {
				rolesIds =
					(Long[])rolesIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] rolesIds;
	public String[] getTasksAssignedToMe() {
			return tasksAssignedToMe;
	}

	public void setTasksAssignedToMe(
			String[] tasksAssignedToMe) {

			this.tasksAssignedToMe = (String[])tasksAssignedToMe;
	}

	@JsonIgnore
	public void setTasksAssignedToMe(
			UnsafeSupplier<String[], Throwable>
				tasksAssignedToMeUnsafeSupplier) {

			try {
				tasksAssignedToMe =
					(String[])tasksAssignedToMeUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] tasksAssignedToMe;
	public String[] getTasksAssignedToMyRoles() {
			return tasksAssignedToMyRoles;
	}

	public void setTasksAssignedToMyRoles(
			String[] tasksAssignedToMyRoles) {

			this.tasksAssignedToMyRoles = (String[])tasksAssignedToMyRoles;
	}

	@JsonIgnore
	public void setTasksAssignedToMyRoles(
			UnsafeSupplier<String[], Throwable>
				tasksAssignedToMyRolesUnsafeSupplier) {

			try {
				tasksAssignedToMyRoles =
					(String[])tasksAssignedToMyRolesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] tasksAssignedToMyRoles;

}