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

package com.liferay.portal.kernel.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Roberto Díaz
 */
public class BaseModelPermissionCheckerUtil {

	public static Boolean containsBaseModelPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String actionId) {

		ModelResourcePermission<?> modelResourcePermission =
			_modelPermissions.getService(className);

		if (modelResourcePermission != null) {
			try {
				PortletResourcePermission portletResourcePermission =
					modelResourcePermission.getPortletResourcePermission();

				if (portletResourcePermission == null) {
					return modelResourcePermission.contains(
						permissionChecker, classPK, actionId);
				}

				return ModelResourcePermissionHelper.contains(
					modelResourcePermission, permissionChecker, groupId,
					classPK, actionId);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}

				return false;
			}
		}

		BaseModelPermissionChecker baseModelPermissionChecker =
			_baseModelPermissionCheckers.getService(className);

		if (baseModelPermissionChecker == null) {
			return null;
		}

		try {
			baseModelPermissionChecker.checkBaseModel(
				permissionChecker, groupId, classPK, actionId);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseModelPermissionCheckerUtil.class);

	private static final ServiceTrackerMap<String, BaseModelPermissionChecker>
		_baseModelPermissionCheckers =
			ServiceTrackerCollections.openSingleValueMap(
				BaseModelPermissionChecker.class, "model.class.name");
	private static final ServiceTrackerMap<String, ModelResourcePermission>
		_modelPermissions = ServiceTrackerCollections.openSingleValueMap(
			ModelResourcePermission.class, "model.class.name");

}