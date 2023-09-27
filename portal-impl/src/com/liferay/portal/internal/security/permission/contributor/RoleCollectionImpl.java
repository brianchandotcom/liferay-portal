/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.security.permission.contributor;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.security.permission.contributor.RoleCollection;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class RoleCollectionImpl implements RoleCollection {

	public RoleCollectionImpl(
		User user, UserBag userBag, long[] roleIds, long groupId,
		PermissionChecker permissionChecker) {

		_user = user;
		_userBag = userBag;
		_roleIds = roleIds;
		_groupId = groupId;
		_permissionChecker = permissionChecker;
	}

	@Override
	public boolean addRoleId(long roleId) {
		if (hasRoleId(roleId)) {
			return false;
		}

		if (_addedRoleIds == null) {
			_addedRoleIds = new HashSet<>();
		}

		if (_removedRoleIds != null) {
			_removedRoleIds.remove(roleId);
		}

		_addedRoleIds.add(roleId);

		return true;
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
	public long[] getInitialRoleIds() {
		return _roleIds.clone();
	}

	public long[] getRoleIds() {
		if ((_addedRoleIds == null) && (_removedRoleIds == null)) {
			return _roleIds;
		}

		Set<Long> rolesIds = SetUtil.fromArray(_roleIds);

		if (_addedRoleIds != null) {
			rolesIds.addAll(_addedRoleIds);
		}

		if (_removedRoleIds != null) {
			rolesIds.removeAll(_removedRoleIds);
		}

		long[] roleIdsArray = ArrayUtil.toLongArray(rolesIds);

		Arrays.sort(roleIdsArray);

		return roleIdsArray;
	}

	@Override
	public User getUser() {
		return _user;
	}

	@Override
	public UserBag getUserBag() {
		return _userBag;
	}

	@Override
	public boolean hasRoleId(long roleId) {
		if ((_addedRoleIds != null) && _addedRoleIds.contains(roleId)) {
			return true;
		}

		if ((_removedRoleIds != null) && _removedRoleIds.contains(roleId)) {
			return false;
		}

		if (Arrays.binarySearch(_roleIds, roleId) >= 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSignedIn() {
		return _permissionChecker.isSignedIn();
	}

	@Override
	public boolean removeRoleId(long roleId) {
		if (!hasRoleId(roleId)) {
			return false;
		}

		if (_addedRoleIds != null) {
			_addedRoleIds.remove(roleId);
		}

		if (_removedRoleIds == null) {
			_removedRoleIds = new HashSet<>();
		}

		_removedRoleIds.add(roleId);

		return true;
	}

	private Set<Long> _addedRoleIds;
	private final long _groupId;
	private final PermissionChecker _permissionChecker;
	private Set<Long> _removedRoleIds;
	private final long[] _roleIds;
	private final User _user;
	private final UserBag _userBag;

}