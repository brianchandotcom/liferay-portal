/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;

import java.util.Set;

/**
 * @author Sergio González
 */
public interface MembershipPolicy {

	public Set<Group> getForbiddenGroups(User user);

	public Set<Organization> getForbiddenOrganizations(User user);

	public Set<Role> getForbiddenRoles(Group group, User user);

	public Set<Role> getForbiddenRoles(Organization organization, User user);

	public Set<Role> getForbiddenRoles(User user);

	public Set<UserGroup> getForbiddenUserGroups(User user);

	public Set<Group> getMandatoryGroups(User user);

	public Set<Organization> getMandatoryOrganizations(User user);

	public Set<Role> getMandatoryRoles(Group group, User user);

	public Set<Role> getMandatoryRoles(Organization organization, User user);

	public Set<Role> getMandatoryRoles(User user);

	public Set<UserGroup> getMandatoryUserGroups(User user);

	public boolean isApplicableUser(User user);

	public boolean isMembershipAllowed(Group group, User user);

	public boolean isMembershipAllowed(Organization organization, User user);

	public boolean isMembershipAllowed(Role role, User user);

	public boolean isMembershipAllowed(Role role, User user, Group group);

	public boolean isMembershipAllowed(
		Role role, User user, Organization organization);

	public boolean isMembershipAllowed(UserGroup userGroup, User user);

}