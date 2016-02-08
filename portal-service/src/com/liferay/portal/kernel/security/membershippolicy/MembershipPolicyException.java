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

package com.liferay.portal.kernel.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MembershipPolicyException extends PortalException {

	public static final int ORGANIZATION_MEMBERSHIP_NOT_ALLOWED = 1;

	public static final int ORGANIZATION_MEMBERSHIP_REQUIRED = 2;

	public static final int ROLE_MEMBERSHIP_NOT_ALLOWED = 3;

	public static final int ROLE_MEMBERSHIP_REQUIRED = 4;

	public static final int SITE_MEMBERSHIP_NOT_ALLOWED = 5;

	public static final int SITE_MEMBERSHIP_REQUIRED = 6;

	public static final int USER_GROUP_MEMBERSHIP_NOT_ALLOWED = 7;

	public static final int USER_GROUP_MEMBERSHIP_REQUIRED = 8;

	public MembershipPolicyException(int type) {
		_type = type;
	}

	public void addGroup(Group group) {
		_groups.add(group);
	}

	public void addOrganization(Organization organization) {
		_organizations.add(organization);
	}

	public void addRole(Role role) {
		_roles.add(role);
	}

	public void addUser(User user) {
		_users.add(user);
	}

	public void addUserGroup(UserGroup userGroup) {
		_userGroups.add(userGroup);
	}

	public List<Group> getGroups() {
		return _groups;
	}

	public List<Organization> getOrganizations() {
		return _organizations;
	}

	public List<Role> getRoles() {
		return _roles;
	}

	public int getType() {
		return _type;
	}

	public List<UserGroup> getUserGroups() {
		return _userGroups;
	}

	public List<User> getUsers() {
		return _users;
	}

	private final List<Group> _groups = new ArrayList<>();
	private final List<Organization> _organizations = new ArrayList<>();
	private final List<Role> _roles = new ArrayList<>();
	private final int _type;
	private final List<UserGroup> _userGroups = new ArrayList<>();
	private final List<User> _users = new ArrayList<>();

}