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