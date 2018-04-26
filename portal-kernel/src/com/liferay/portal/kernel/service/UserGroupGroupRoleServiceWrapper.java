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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link UserGroupGroupRoleService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRoleService
 * @generated
 */
@ProviderType
public class UserGroupGroupRoleServiceWrapper
	implements UserGroupGroupRoleService,
		ServiceWrapper<UserGroupGroupRoleService> {
	public UserGroupGroupRoleServiceWrapper(
		UserGroupGroupRoleService userGroupGroupRoleService) {
		_userGroupGroupRoleService = userGroupGroupRoleService;
	}

	@Override
	public void addUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupGroupRoleService.addUserGroupGroupRoles(userGroupId, groupId,
			roleIds);
	}

	@Override
	public void addUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId) throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupGroupRoleService.addUserGroupGroupRoles(userGroupIds,
			groupId, roleId);
	}

	@Override
	public void deleteUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupGroupRoleService.deleteUserGroupGroupRoles(userGroupId,
			groupId, roleIds);
	}

	@Override
	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId) throws com.liferay.portal.kernel.exception.PortalException {
		_userGroupGroupRoleService.deleteUserGroupGroupRoles(userGroupIds,
			groupId, roleId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _userGroupGroupRoleService.getOSGiServiceIdentifier();
	}

	@Override
	public UserGroupGroupRoleService getWrappedService() {
		return _userGroupGroupRoleService;
	}

	@Override
	public void setWrappedService(
		UserGroupGroupRoleService userGroupGroupRoleService) {
		_userGroupGroupRoleService = userGroupGroupRoleService;
	}

	private UserGroupGroupRoleService _userGroupGroupRoleService;
}