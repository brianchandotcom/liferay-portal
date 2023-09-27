/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.groups.admin.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.permission.UserGroupPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Fabiano Nazar
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.UserGroup",
	service = ModelResourcePermission.class
)
public class UserGroupModelResourcePermission
	implements ModelResourcePermission<UserGroup> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		_userGroupPermission.check(permissionChecker, primaryKey, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, UserGroup userGroup,
			String actionId)
		throws PortalException {

		_userGroupPermission.check(
			permissionChecker, userGroup.getUserGroupId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		return _userGroupPermission.contains(
			permissionChecker, primaryKey, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, UserGroup userGroup,
			String actionId)
		throws PortalException {

		return _userGroupPermission.contains(
			permissionChecker, userGroup.getUserGroupId(), actionId);
	}

	@Override
	public String getModelName() {
		return UserGroup.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupPermission _userGroupPermission;

}