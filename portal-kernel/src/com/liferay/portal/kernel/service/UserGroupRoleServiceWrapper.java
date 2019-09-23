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
 * Provides a wrapper for {@link UserGroupRoleService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRoleService
 * @generated
 */
public class UserGroupRoleServiceWrapper
	implements ServiceWrapper<UserGroupRoleService>, UserGroupRoleService {

	public UserGroupRoleServiceWrapper(
		UserGroupRoleService userGroupRoleService) {

		_userGroupRoleService = userGroupRoleService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserGroupRoleServiceUtil} to access the user group role remote service. Add custom service methods to <code>com.liferay.portal.service.impl.UserGroupRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupRoleService.addUserGroupRoles(userId, groupId, roleIds);
	}

	@Override
	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupRoleService.addUserGroupRoles(userIds, groupId, roleId);
	}

	@Override
	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupRoleService.deleteUserGroupRoles(userId, groupId, roleIds);
	}

	@Override
	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupRoleService.deleteUserGroupRoles(userIds, groupId, roleId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _userGroupRoleService.getOSGiServiceIdentifier();
	}

	@Override
	public void updateUserGroupRoles(
			long userId, long groupId, long[] addedRoleIds,
			long[] deletedRoleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_userGroupRoleService.updateUserGroupRoles(
			userId, groupId, addedRoleIds, deletedRoleIds);
	}

	@Override
	public UserGroupRoleService getWrappedService() {
		return _userGroupRoleService;
	}

	@Override
	public void setWrappedService(UserGroupRoleService userGroupRoleService) {
		_userGroupRoleService = userGroupRoleService;
	}

	private UserGroupRoleService _userGroupRoleService;

}