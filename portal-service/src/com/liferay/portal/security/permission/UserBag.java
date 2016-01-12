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

package com.liferay.portal.security.permission;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;

import java.io.Serializable;

import java.util.Collection;

/**
 * @author László Csontos
 * @author Preston Crary
 */
@ProviderType
public interface UserBag extends Serializable {

	public Collection<Group> getGroups() throws PortalException;

	public long[] getRoleIds();

	public Collection<Role> getRoles() throws PortalException;

	public long[] getUserGroupIds();

	public Collection<Group> getUserGroups() throws PortalException;

	public long getUserId();

	public long[] getUserOrgGroupIds();

	public Collection<Group> getUserOrgGroups() throws PortalException;

	public long[] getUserOrgIds();

	public Collection<Organization> getUserOrgs() throws PortalException;

	public boolean hasRole(Role role);

	public boolean hasUserGroup(Group group);

	public boolean hasUserOrg(Organization organization);

	public boolean hasUserOrgGroup(Group group);

}