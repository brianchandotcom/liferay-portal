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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class DLFileShortcutPermission {

	public static void check(
			PermissionChecker permissionChecker, DLFileShortcut dlFileShortcut,
			String actionId)
		throws PortalException {

		_dlFileShortcutModelResourcePermission.check(
			permissionChecker, dlFileShortcut, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, FileShortcut fileShortcut,
			String actionId)
		throws PortalException {

		_fileShortcutModelResourcePermission.check(
			permissionChecker, fileShortcut, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long fileShortcutId,
			String actionId)
		throws PortalException {

		_dlFileShortcutModelResourcePermission.check(
			permissionChecker, fileShortcutId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFileShortcut dlFileShortcut,
			String actionId)
		throws PortalException {

		return _dlFileShortcutModelResourcePermission.contains(
			permissionChecker, dlFileShortcut, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, FileShortcut fileShortcut,
			String actionId)
		throws PortalException {

		return _fileShortcutModelResourcePermission.contains(
			permissionChecker, fileShortcut, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fileShortcutId,
			String actionId)
		throws PortalException {

		return _dlFileShortcutModelResourcePermission.contains(
			permissionChecker, fileShortcutId, actionId);
	}

	private static volatile ModelResourcePermission<DLFileShortcut>
		_dlFileShortcutModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, DLFileShortcutPermission.class,
				"_dlFileShortcutModelResourcePermission",
				"(model.class.name=" + DLFileShortcut.class.getName() + ")",
				true);
	private static volatile ModelResourcePermission<FileShortcut>
		_fileShortcutModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, DLFileShortcutPermission.class,
				"_fileShortcutModelResourcePermission",
				"(model.class.name=" + FileShortcut.class.getName() + ")",
				true);

}