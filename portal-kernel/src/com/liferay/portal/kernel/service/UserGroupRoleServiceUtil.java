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

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for UserGroupRole. This utility wraps
 * <code>com.liferay.portal.service.impl.UserGroupRoleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRoleService
 * @generated
 */
public class UserGroupRoleServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.UserGroupRoleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addUserGroupRoles(
			long userId, long groupId, long[] roleIds)
		throws PortalException {

		getService().addUserGroupRoles(userId, groupId, roleIds);
	}

	public static void addUserGroupRoles(
			long[] userIds, long groupId, long roleId)
		throws PortalException {

		getService().addUserGroupRoles(userIds, groupId, roleId);
	}

	public static void deleteUserGroupRoles(
			long userId, long groupId, long[] roleIds)
		throws PortalException {

		getService().deleteUserGroupRoles(userId, groupId, roleIds);
	}

	public static void deleteUserGroupRoles(
			long[] userIds, long groupId, long roleId)
		throws PortalException {

		getService().deleteUserGroupRoles(userIds, groupId, roleId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void updateUserGroupRoles(
			long userId, long groupId, long[] addedRoleIds,
			long[] deletedRoleIds)
		throws PortalException {

		getService().updateUserGroupRoles(
			userId, groupId, addedRoleIds, deletedRoleIds);
	}

	public static UserGroupRoleService getService() {
		return _service;
	}

	public static void setService(UserGroupRoleService service) {
		_service = service;
	}

	private static volatile UserGroupRoleService _service;

}