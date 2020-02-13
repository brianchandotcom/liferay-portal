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

package com.liferay.depot.internal.security.permissions;

import com.liferay.depot.internal.constants.DepotRolesConstants;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.security.permission.PermissionCacheUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author Cristina González
 */
public class DepotPermissionChecker implements PermissionChecker {

	public DepotPermissionChecker(
		PermissionChecker permissionChecker,
		GroupLocalService groupLocalService, RoleLocalService roleLocalService,
		UserGroupRoleLocalService userGroupRoleLocalService) {

		_permissionChecker = permissionChecker;
		_groupLocalService = groupLocalService;
		_roleLocalService = roleLocalService;
		_userGroupRoleLocalService = userGroupRoleLocalService;
	}

	@Override
	public PermissionChecker clone() {
		return new DepotPermissionChecker(
			_permissionChecker.clone(), _groupLocalService, _roleLocalService,
			_userGroupRoleLocalService);
	}

	@Override
	public long getCompanyId() {
		return _permissionChecker.getCompanyId();
	}

	@Override
	public long[] getGuestUserRoleIds() {
		return _permissionChecker.getGuestUserRoleIds();
	}

	@Override
	public long getOwnerRoleId() {
		return _permissionChecker.getOwnerRoleId();
	}

	@Override
	public Map<Object, Object> getPermissionChecksMap() {
		return _permissionChecker.getPermissionChecksMap();
	}

	@Override
	public long[] getRoleIds(long userId, long groupId) {
		return _permissionChecker.getRoleIds(userId, groupId);
	}

	@Override
	public User getUser() {
		return _permissionChecker.getUser();
	}

	@Override
	public UserBag getUserBag() throws Exception {
		return _permissionChecker.getUserBag();
	}

	@Override
	public long getUserId() {
		return _permissionChecker.getUserId();
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, long primKey, long ownerId,
		String actionId) {

		return _permissionChecker.hasOwnerPermission(
			companyId, name, primKey, ownerId, actionId);
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId) {

		return _permissionChecker.hasOwnerPermission(
			companyId, name, primKey, ownerId, actionId);
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		return _permissionChecker.hasPermission(group, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return _permissionChecker.hasPermission(group, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return _permissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return _permissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	@Override
	public void init(User user) {
		_permissionChecker.init(user);
	}

	@Override
	public boolean isCheckGuest() {
		return _permissionChecker.isCheckGuest();
	}

	@Override
	public boolean isCompanyAdmin() {
		return _permissionChecker.isCompanyAdmin();
	}

	@Override
	public boolean isCompanyAdmin(long companyId) {
		return _permissionChecker.isCompanyAdmin(companyId);
	}

	@Override
	public boolean isContentReviewer(long companyId, long groupId) {
		return _permissionChecker.isContentReviewer(companyId, groupId);
	}

	@Override
	public boolean isGroupAdmin(long groupId) {
		if (!isSignedIn()) {
			return false;
		}

		if (_permissionChecker.isGroupAdmin(groupId)) {
			return true;
		}

		if (groupId <= 0) {
			return false;
		}

		try {
			return _getOrAddToPermissionCache(
				_groupLocalService.fetchGroup(groupId), this::_isGroupAdmin,
				DepotRolesConstants.DEPOT_ADMINISTRATOR);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return false;
		}
	}

	@Override
	public boolean isGroupMember(long groupId) {
		if (!isSignedIn()) {
			return false;
		}

		if (groupId <= 0) {
			return false;
		}

		try {
			return _isGroupMember(_groupLocalService.getGroup(groupId));
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return false;
		}
	}

	@Override
	public boolean isGroupOwner(long groupId) {
		if (!isSignedIn()) {
			return false;
		}

		if (_permissionChecker.isGroupOwner(groupId)) {
			return true;
		}

		if (groupId <= 0) {
			return false;
		}

		try {
			return _getOrAddToPermissionCache(
				_groupLocalService.getGroup(groupId), this::_isGroupOwner,
				DepotRolesConstants.DEPOT_OWNER);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return false;
		}
	}

	@Override
	public boolean isOmniadmin() {
		return _permissionChecker.isOmniadmin();
	}

	@Override
	public boolean isOrganizationAdmin(long organizationId) {
		return _permissionChecker.isOrganizationAdmin(organizationId);
	}

	@Override
	public boolean isOrganizationOwner(long organizationId) {
		return _permissionChecker.isOrganizationOwner(organizationId);
	}

	@Override
	public boolean isSignedIn() {
		return _permissionChecker.isSignedIn();
	}

	private boolean _getOrAddToPermissionCache(
			Group group,
			UnsafeFunction<Group, Boolean, Exception> unsafeFunction,
			String roleName)
		throws Exception {

		if (group == null) {
			return false;
		}

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			getUserId(), group.getGroupId(), roleName);

		try {
			if (value == null) {
				value = unsafeFunction.apply(group);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), group.getGroupId(), roleName, value);
			}
		}
		catch (Exception exception) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), group.getGroupId(), roleName);

			throw exception;
		}

		return value;
	}

	private boolean _isGroupAdmin(Group group) throws Exception {
		if (Objects.equals(group.getType(), GroupConstants.TYPE_DEPOT)) {
			if (_userGroupRoleLocalService.hasUserGroupRole(
					getUserId(), group.getGroupId(),
					DepotRolesConstants.DEPOT_ADMINISTRATOR, true) ||
				_userGroupRoleLocalService.hasUserGroupRole(
					getUserId(), group.getGroupId(),
					DepotRolesConstants.DEPOT_OWNER, true)) {

				return true;
			}

			Group parentGroup = group;

			while (!parentGroup.isRoot()) {
				parentGroup = parentGroup.getParentGroup();

				if (_permissionChecker.hasPermission(
						parentGroup, Group.class.getName(),
						String.valueOf(parentGroup.getGroupId()),
						ActionKeys.MANAGE_SUBGROUPS)) {

					return true;
				}
			}
		}

		return false;
	}

	private boolean _isGroupMember(Group group) throws Exception {
		long[] roleIds = getRoleIds(getUserId(), group.getGroupId());

		Role role = _roleLocalService.getRole(
			group.getCompanyId(), DepotRolesConstants.DEPOT_MEMBER);

		if (Arrays.binarySearch(roleIds, role.getRoleId()) >= 0) {
			return true;
		}

		return _permissionChecker.isGroupMember(group.getGroupId());
	}

	private boolean _isGroupOwner(Group group) throws PortalException {
		if (Objects.equals(group.getType(), GroupConstants.TYPE_DEPOT) &&
			_userGroupRoleLocalService.hasUserGroupRole(
				getUserId(), group.getGroupId(),
				DepotRolesConstants.DEPOT_OWNER, true)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotPermissionChecker.class);

	private final GroupLocalService _groupLocalService;
	private final PermissionChecker _permissionChecker;
	private final RoleLocalService _roleLocalService;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;

}