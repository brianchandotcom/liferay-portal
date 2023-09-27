/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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