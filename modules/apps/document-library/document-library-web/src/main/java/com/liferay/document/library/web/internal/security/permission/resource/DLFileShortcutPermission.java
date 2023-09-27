/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.security.permission.resource;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class DLFileShortcutPermission {

	public static void check(
			PermissionChecker permissionChecker, FileShortcut fileShortcut,
			String actionId)
		throws PortalException {

		ModelResourcePermission<FileShortcut> modelResourcePermission =
			_fileShortcutModelResourcePermissionSnapshot.get();

		modelResourcePermission.check(
			permissionChecker, fileShortcut, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long fileShortcutId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<FileShortcut> modelResourcePermission =
			_fileShortcutModelResourcePermissionSnapshot.get();

		modelResourcePermission.check(
			permissionChecker, fileShortcutId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFileShortcut dlFileShortcut,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DLFileShortcut> modelResourcePermission =
			_dlFileShortcutModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, dlFileShortcut, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, FileShortcut fileShortcut,
			String actionId)
		throws PortalException {

		ModelResourcePermission<FileShortcut> modelResourcePermission =
			_fileShortcutModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, fileShortcut, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fileShortcutId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<FileShortcut> modelResourcePermission =
			_fileShortcutModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, fileShortcutId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<DLFileShortcut>>
		_dlFileShortcutModelResourcePermissionSnapshot = new Snapshot<>(
			DLFileShortcutPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.document.library.kernel.model." +
				"DLFileShortcut)");
	private static final Snapshot<ModelResourcePermission<FileShortcut>>
		_fileShortcutModelResourcePermissionSnapshot = new Snapshot<>(
			DLFileShortcutPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.portal.kernel.repository.model." +
				"FileShortcut)");

}