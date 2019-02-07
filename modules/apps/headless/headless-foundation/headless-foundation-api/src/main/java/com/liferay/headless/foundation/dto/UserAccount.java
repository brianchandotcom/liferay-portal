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

package com.liferay.headless.foundation.dto;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "UserAccount")
public class UserAccount {

	public String getAdditionalName() {
		return _additionalName;
	}

	public String getAlternateName() {
		return _alternateName;
	}

	public Date getBirthDate() {
		return _birthDate;
	}

	public Object getContactInformation() {
		return _contactInformation;
	}

	public String getDashboardURL() {
		return _dashboardURL;
	}

	public String getEmail() {
		return _email;
	}

	public String getFamilyName() {
		return _familyName;
	}

	public String getGivenName() {
		return _givenName;
	}

	public String getHonorificPrefix() {
		return _honorificPrefix;
	}

	public String getHonorificSuffix() {
		return _honorificSuffix;
	}

	public Long getId() {
		return _id;
	}

	public String getImage() {
		return _image;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public Organization[] getMyOrganizations() {
		return _myOrganizations;
	}

	public Long[] getMyOrganizationsIds() {
		return _myOrganizationsIds;
	}

	public String getName() {
		return _name;
	}

	public String getProfileURL() {
		return _profileURL;
	}

	public Role[] getRoles() {
		return _roles;
	}

	public Long[] getRolesIds() {
		return _rolesIds;
	}

	public String getSelf() {
		return _self;
	}

	public String[] getTasksAssignedToMe() {
		return _tasksAssignedToMe;
	}

	public String[] getTasksAssignedToMyRoles() {
		return _tasksAssignedToMyRoles;
	}

	public void setAdditionalName(String additionalName) {
		_additionalName = additionalName;
	}

	public void setAlternateName(String alternateName) {
		_alternateName = alternateName;
	}

	public void setBirthDate(Date birthDate) {
		_birthDate = birthDate;
	}

	public void setContactInformation(Object contactInformation) {
		_contactInformation = contactInformation;
	}

	public void setDashboardURL(String dashboardURL) {
		_dashboardURL = dashboardURL;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public void setFamilyName(String familyName) {
		_familyName = familyName;
	}

	public void setGivenName(String givenName) {
		_givenName = givenName;
	}

	public void setHonorificPrefix(String honorificPrefix) {
		_honorificPrefix = honorificPrefix;
	}

	public void setHonorificSuffix(String honorificSuffix) {
		_honorificSuffix = honorificSuffix;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public void setMyOrganizations(Organization[] myOrganizations) {
		_myOrganizations = myOrganizations;
	}

	public void setMyOrganizationsIds(Long[] myOrganizationsIds) {
		_myOrganizationsIds = myOrganizationsIds;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setProfileURL(String profileURL) {
		_profileURL = profileURL;
	}

	public void setRoles(Role[] roles) {
		_roles = roles;
	}

	public void setRolesIds(Long[] rolesIds) {
		_rolesIds = rolesIds;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setTasksAssignedToMe(String[] tasksAssignedToMe) {
		_tasksAssignedToMe = tasksAssignedToMe;
	}

	public void setTasksAssignedToMyRoles(String[] tasksAssignedToMyRoles) {
		_tasksAssignedToMyRoles = tasksAssignedToMyRoles;
	}

	private String _additionalName;
	private String _alternateName;
	private Date _birthDate;
	private Object _contactInformation;
	private String _dashboardURL;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _honorificPrefix;
	private String _honorificSuffix;
	private Long _id;
	private String _image;
	private String _jobTitle;
	private Organization[] _myOrganizations;
	private Long[] _myOrganizationsIds;
	private String _name;
	private String _profileURL;
	private Role[] _roles;
	private Long[] _rolesIds;
	private String _self;
	private String[] _tasksAssignedToMe;
	private String[] _tasksAssignedToMyRoles;

}