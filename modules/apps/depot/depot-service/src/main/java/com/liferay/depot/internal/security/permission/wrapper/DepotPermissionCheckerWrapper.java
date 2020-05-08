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

package com.liferay.depot.internal.security.permission.wrapper;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.wrapper.PermissionCheckerWrapper;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Cristina González
 */
public class DepotPermissionCheckerWrapper extends PermissionCheckerWrapper {

	public DepotPermissionCheckerWrapper(
		PermissionChecker permissionChecker,
		ModelResourcePermission<DepotEntry> depotEntryModelResourcePermission,
		GroupLocalService groupLocalService,
		UserGroupRoleLocalService userGroupRoleLocalService) {

		super(permissionChecker);

		_depotEntryModelResourcePermission = depotEntryModelResourcePermission;
		_groupLocalService = groupLocalService;
		_userGroupRoleLocalService = userGroupRoleLocalService;
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		return _hasPermission(
			name, primKey, actionId,
			() -> super.hasPermission(group, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return _hasPermission(
			name, GetterUtil.getLong(primKey), actionId,
			() -> super.hasPermission(group, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return _hasPermission(
			name, primKey, actionId,
			() -> super.hasPermission(groupId, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return _hasPermission(
			name, GetterUtil.getLong(primKey), actionId,
			() -> super.hasPermission(groupId, name, primKey, actionId));
	}

	private boolean _hasPermission(
		String name, long primKey, String actionId,
		Supplier<Boolean> hasPermissionSupplier) {

		try {
			Group depotGroup = null;

			if (StringUtil.equals(name, Group.class.getName())) {
				depotGroup = _groupLocalService.fetchGroup(primKey);

				if ((depotGroup != null) &&
					(depotGroup.getType() != GroupConstants.TYPE_DEPOT)) {

					depotGroup = null;
				}
			}

			if (depotGroup == null) {
				return hasPermissionSupplier.get();
			}

			if (!_supportedActionIds.contains(actionId)) {
				return false;
			}

			if (_userGroupRoleLocalService.hasUserGroupRole(
					getUserId(), depotGroup.getGroupId(),
					DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR, true) ||
				_userGroupRoleLocalService.hasUserGroupRole(
					getUserId(), depotGroup.getGroupId(),
					DepotRolesConstants.ASSET_LIBRARY_OWNER, true)) {

				return true;
			}

			Group parentGroup = depotGroup;

			while (!parentGroup.isRoot()) {
				parentGroup = parentGroup.getParentGroup();

				if (super.hasPermission(
						parentGroup, Group.class.getName(),
						String.valueOf(parentGroup.getGroupId()),
						ActionKeys.MANAGE_SUBGROUPS)) {

					return true;
				}
			}

			return _depotEntryModelResourcePermission.contains(
				this, depotGroup.getClassPK(), actionId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotPermissionCheckerWrapper.class);

	private static final Set<String> _supportedActionIds = new HashSet<>(
		Arrays.asList(
			ActionKeys.ASSIGN_MEMBERS, ActionKeys.ASSIGN_USER_ROLES,
			ActionKeys.DELETE, ActionKeys.UPDATE, ActionKeys.VIEW,
			ActionKeys.VIEW_MEMBERS, ActionKeys.VIEW_SITE_ADMINISTRATION));

	private final ModelResourcePermission<DepotEntry>
		_depotEntryModelResourcePermission;
	private final GroupLocalService _groupLocalService;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;

}