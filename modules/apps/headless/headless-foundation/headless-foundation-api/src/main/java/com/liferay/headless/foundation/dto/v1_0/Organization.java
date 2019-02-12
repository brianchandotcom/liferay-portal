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

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Organization")
public class Organization {

	public String getComment() {
		return _comment;
	}

	public ContactInformation getContactInformation() {
		return _contactInformation;
	}

	public Long getId() {
		return _id;
	}

	public Location getLocation() {
		return _location;
	}

	public String getLogo() {
		return _logo;
	}

	public UserAccount[] getMembers() {
		return _members;
	}

	public Long[] getMembersIds() {
		return _membersIds;
	}

	public String getName() {
		return _name;
	}

	public Organization getParentOrganization() {
		return _parentOrganization;
	}

	public Long getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public Services[] getServices() {
		return _services;
	}

	public Organization[] getSubOrganization() {
		return _subOrganization;
	}

	public Long[] getSubOrganizationIds() {
		return _subOrganizationIds;
	}

	public void setComment(String comment) {
		_comment = comment;
	}

	public void setContactInformation(ContactInformation contactInformation) {
		_contactInformation = contactInformation;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setLocation(Location location) {
		_location = location;
	}

	public void setLogo(String logo) {
		_logo = logo;
	}

	public void setMembers(UserAccount[] members) {
		_members = members;
	}

	public void setMembersIds(Long[] membersIds) {
		_membersIds = membersIds;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParentOrganization(Organization parentOrganization) {
		_parentOrganization = parentOrganization;
	}

	public void setParentOrganizationId(Long parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;
	}

	public void setServices(Services[] services) {
		_services = services;
	}

	public void setSubOrganization(Organization[] subOrganization) {
		_subOrganization = subOrganization;
	}

	public void setSubOrganizationIds(Long[] subOrganizationIds) {
		_subOrganizationIds = subOrganizationIds;
	}

	private String _comment;
	private ContactInformation _contactInformation;
	private Long _id;
	private Location _location;
	private String _logo;
	private UserAccount[] _members;
	private Long[] _membersIds;
	private String _name;
	private Organization _parentOrganization;
	private Long _parentOrganizationId;
	private Services[] _services;
	private Organization[] _subOrganization;
	private Long[] _subOrganizationIds;

}