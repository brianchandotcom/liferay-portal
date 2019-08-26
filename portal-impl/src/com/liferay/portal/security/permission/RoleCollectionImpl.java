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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.RoleCollection;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Raymond Augé
 */
public class RoleCollectionImpl implements RoleCollection {

	public RoleCollectionImpl(
		Collection<Role> roles, long groupId,
		PermissionChecker permissionChecker,
		RoleLocalService roleLocalService) {

		_roles = new ArrayList<>(roles);
		_groupId = groupId;
		_permissionChecker = permissionChecker;
		_roleLocalService = roleLocalService;
		_initialRoles = new ArrayList<>(roles);
	}

	@Override
	public boolean addAll(long[] roleIds) throws PortalException {
		boolean changed = false;

		for (long roleId : roleIds) {
			changed = changed | addRoleId(roleId);
		}

		return changed;
	}

	@Override
	public boolean addRoleId(long roleId) throws PortalException {
		return _roles.add(_roleLocalService.getRole(roleId));
	}

	@Override
	public long getCompanyId() {
		return _permissionChecker.getCompanyId();
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public List<Role> getInitialRoles() {
		return ListUtil.toList(_initialRoles, role -> (Role)role.clone());
	}

	@Override
	public long getUserId() {
		return _permissionChecker.getUserId();
	}

	@Override
	public boolean hasRoleId(long roleId) {
		Stream<Role> stream = _roles.stream();

		if (stream.anyMatch(role -> role.getRoleId() == roleId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSignedIn() {
		return _permissionChecker.isSignedIn();
	}

	@Override
	public boolean removeIf(Predicate<Role> predicate) {
		return _roles.removeIf(role -> predicate.test((Role)role.clone()));
	}

	protected List<Role> getRoleList() {
		return _roles;
	}

	private final long _groupId;
	private final List<Role> _initialRoles;
	private final PermissionChecker _permissionChecker;
	private final RoleLocalService _roleLocalService;
	private final List<Role> _roles;

}