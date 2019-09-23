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

/**
 * Provides a wrapper for {@link UserGroupGroupRoleService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRoleService
 * @generated
 */
public class UserGroupGroupRoleServiceWrapper
	implements ServiceWrapper<UserGroupGroupRoleService>,
			   UserGroupGroupRoleService {

	public UserGroupGroupRoleServiceWrapper(
		UserGroupGroupRoleService userGroupGroupRoleService) {

		_userGroupGroupRoleService = userGroupGroupRoleService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserGroupGroupRoleServiceUtil} to access the user group group role remote service. Add custom service methods to <code>com.liferay.portal.service.impl.UserGroupGroupRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void addUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupGroupRoleService.addUserGroupGroupRoles(
			userGroupId, groupId, roleIds);
	}

	@Override
	public void addUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupGroupRoleService.addUserGroupGroupRoles(
			userGroupIds, groupId, roleId);
	}

	@Override
	public void deleteUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupGroupRoleService.deleteUserGroupGroupRoles(
			userGroupId, groupId, roleIds);
	}

	@Override
	public void deleteUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupGroupRoleService.deleteUserGroupGroupRoles(
			userGroupIds, groupId, roleId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
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