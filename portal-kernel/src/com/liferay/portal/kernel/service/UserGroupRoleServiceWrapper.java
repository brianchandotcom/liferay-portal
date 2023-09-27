/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public UserGroupRoleServiceWrapper() {
		this(null);
	}

	public UserGroupRoleServiceWrapper(
		UserGroupRoleService userGroupRoleService) {

		_userGroupRoleService = userGroupRoleService;
	}

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