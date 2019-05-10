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

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
@ProviderType
public interface UserGroupRolePermission {

	public void check(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException;

}