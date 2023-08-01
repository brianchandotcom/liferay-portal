/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Preston Crary
 */
public class ModelResourcePermissionUtil {

	public static void check(
			ModelResourcePermission<?> modelResourcePermission,
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		if (primaryKey == _DEFAULT_PARENT_PRIMARY_KEY) {
			PortletResourcePermission portletResourcePermission =
				modelResourcePermission.getPortletResourcePermission();

			portletResourcePermission.check(
				permissionChecker, groupId, actionId);
		}
		else {
			modelResourcePermission.check(
				permissionChecker, primaryKey, actionId);
		}
	}

	public static boolean contains(
			ModelResourcePermission<?> modelResourcePermission,
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		if (primaryKey == _DEFAULT_PARENT_PRIMARY_KEY) {
			PortletResourcePermission portletResourcePermission =
				modelResourcePermission.getPortletResourcePermission();

			return portletResourcePermission.contains(
				permissionChecker, groupId, actionId);
		}

		return modelResourcePermission.contains(
			permissionChecker, primaryKey, actionId);
	}

	public static Boolean contains(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String actionId) {

		ModelResourcePermission<?> modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				className);

		if (modelResourcePermission == null) {
			return null;
		}

		try {
			PortletResourcePermission portletResourcePermission =
				modelResourcePermission.getPortletResourcePermission();

			if (portletResourcePermission == null) {
				return modelResourcePermission.contains(
					permissionChecker, classPK, actionId);
			}

			return contains(
				modelResourcePermission, permissionChecker, groupId, classPK,
				actionId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return false;
		}
	}

	private static final long _DEFAULT_PARENT_PRIMARY_KEY = 0;

	private static final Log _log = LogFactoryUtil.getLog(
		ModelResourcePermissionUtil.class);

}