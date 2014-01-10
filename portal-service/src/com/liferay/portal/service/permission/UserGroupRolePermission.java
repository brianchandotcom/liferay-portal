/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
@ProviderType
public interface UserGroupRolePermission {

	public void check(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException, SystemException;

}