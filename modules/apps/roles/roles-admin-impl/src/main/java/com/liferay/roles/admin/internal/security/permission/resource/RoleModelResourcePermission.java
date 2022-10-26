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

package com.liferay.roles.admin.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.RolePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Queiroz
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Role",
	service = ModelResourcePermission.class
)
public class RoleModelResourcePermission
	implements ModelResourcePermission<Role> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long roleId, String actionId)
		throws PortalException {

		_rolePermission.check(permissionChecker, roleId, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Role role, String actionId)
		throws PortalException {

		_rolePermission.check(permissionChecker, role.getRoleId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long roleId, String actionId)
		throws PortalException {

		return _rolePermission.contains(permissionChecker, roleId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Role role, String actionId)
		throws PortalException {

		return _rolePermission.contains(
			permissionChecker, role.getRoleId(), actionId);
	}

	@Override
	public String getModelName() {
		return Role.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private RolePermission _rolePermission;

}