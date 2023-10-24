/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.staging.permission;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Jorge Ferrer
 */
public class StagingPermissionUtil {

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, Group group, String className,
		long classPK, String portletId, String actionId) {

		StagingPermission stagingPermission = _stagingPermissionSnapshot.get();

		return stagingPermission.hasPermission(
			permissionChecker, group, className, classPK, portletId, actionId);
	}

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String portletId, String actionId) {

		StagingPermission stagingPermission = _stagingPermissionSnapshot.get();

		return stagingPermission.hasPermission(
			permissionChecker, groupId, className, classPK, portletId,
			actionId);
	}

	private static final Snapshot<StagingPermission>
		_stagingPermissionSnapshot = new Snapshot<>(
			StagingPermissionUtil.class, StagingPermission.class);

}