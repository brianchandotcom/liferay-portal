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

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author László Csontos
 */
public class UserBagImpl implements UserBag {

	public UserBagImpl(
		long userId, Set<Group> userGroups, Set<Organization> userOrgs,
		Set<Group> userOrgGroups, Set<Role> userRoles) {

		_userId = userId;
		_userGroups = Collections.unmodifiableSet(userGroups);
		_userOrgs = Collections.unmodifiableSet(userOrgs);
		_userOrgGroups = Collections.unmodifiableSet(userOrgGroups);
		_userRoles = Collections.unmodifiableSet(userRoles);
	}

	@Override
	public Set<Group> getGroups() {
		if (_groups == null) {
			_groups = new HashSet<>();

			_groups.addAll(_userGroups);
			_groups.addAll(_userOrgGroups);

			_groups = Collections.unmodifiableSet(_groups);
		}

		return _groups;
	}

	@Override
	public Set<Role> getRoles() {
		return _userRoles;
	}

	@Override
	public Set<Group> getUserGroups() {
		return _userGroups;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public Set<Group> getUserOrgGroups() {
		return _userOrgGroups;
	}

	@Override
	public Set<Organization> getUserOrgs() {
		return _userOrgs;
	}

	@Override
	public boolean hasRole(Role role) {
		return _userRoles.contains(role);
	}

	private Set<Group> _groups;
	private final Set<Group> _userGroups;
	private final long _userId;
	private final Set<Group> _userOrgGroups;
	private final Set<Organization> _userOrgs;
	private final Set<Role> _userRoles;

}