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