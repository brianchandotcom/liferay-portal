/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for UserGroupGroupRole. This utility wraps
 * <code>com.liferay.portal.service.impl.UserGroupGroupRoleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRoleService
 * @generated
 */
public class UserGroupGroupRoleServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.UserGroupGroupRoleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws PortalException {

		getService().addUserGroupGroupRoles(userGroupId, groupId, roleIds);
	}

	public static void addUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws PortalException {

		getService().addUserGroupGroupRoles(userGroupIds, groupId, roleId);
	}

	public static void deleteUserGroupGroupRoles(
			long userGroupId, long groupId, long[] roleIds)
		throws PortalException {

		getService().deleteUserGroupGroupRoles(userGroupId, groupId, roleIds);
	}

	public static void deleteUserGroupGroupRoles(
			long[] userGroupIds, long groupId, long roleId)
		throws PortalException {

		getService().deleteUserGroupGroupRoles(userGroupIds, groupId, roleId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static UserGroupGroupRoleService getService() {
		return _service;
	}

	public static void setService(UserGroupGroupRoleService service) {
		_service = service;
	}

	private static volatile UserGroupGroupRoleService _service;

}