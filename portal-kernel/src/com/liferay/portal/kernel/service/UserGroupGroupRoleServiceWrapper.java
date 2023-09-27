/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public UserGroupGroupRoleServiceWrapper() {
		this(null);
	}

	public UserGroupGroupRoleServiceWrapper(
		UserGroupGroupRoleService userGroupGroupRoleService) {

		_userGroupGroupRoleService = userGroupGroupRoleService;
	}

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