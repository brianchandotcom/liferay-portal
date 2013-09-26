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

package com.liferay.portlet.dynamicdatamapping.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Bruno Basto
 */
public class DDMPermission {

	public static final String RESOURCE_NAME =
		"com.liferay.portlet.dynamicdatamapping";

	public static void check(
			PermissionChecker permissionChecker, long groupId, String name,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, name, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String name,
		String actionId) {

		Boolean hasPermission = null;

		if (actionId.equals(ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE)) {
			hasPermission = StagingPermissionUtil.hasPermission(
				permissionChecker, groupId, RESOURCE_NAME, groupId,
				PortletKeys.PORTLET_DISPLAY_TEMPLATES, actionId);
		}
		else {
			hasPermission = StagingPermissionUtil.hasPermission(
				permissionChecker, groupId, RESOURCE_NAME, groupId, name,
				actionId);
		}

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(
			groupId, name, groupId, actionId);
	}

}