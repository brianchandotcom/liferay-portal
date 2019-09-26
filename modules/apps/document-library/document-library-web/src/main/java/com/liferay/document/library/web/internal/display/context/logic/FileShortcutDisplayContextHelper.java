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

package com.liferay.document.library.web.internal.display.context.logic;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileShortcutPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;

/**
 * @author IL (Brian) Kim
 */
public class FileShortcutDisplayContextHelper {

	public FileShortcutDisplayContextHelper(
		PermissionChecker permissionChecker, FileShortcut fileShortcut) {

		_permissionChecker = permissionChecker;

		_fileShortcut = fileShortcut;

		if (_fileShortcut == null) {
			_setValuesForNullFileShortcut();
		}
	}

	public boolean hasDeletePermission() throws PortalException {
		if (_hasDeletePermission == null) {
			_hasDeletePermission = DLFileShortcutPermission.contains(
				_permissionChecker, _fileShortcut, ActionKeys.DELETE);
		}

		return _hasDeletePermission;
	}

	public boolean hasExportImportPermission() throws PortalException {
		if (_hasExportImportPermission == null) {
			_hasExportImportPermission = GroupPermissionUtil.contains(
				_permissionChecker, _fileShortcut.getGroupId(),
				ActionKeys.EXPORT_IMPORT_PORTLET_INFO);
		}

		return _hasExportImportPermission;
	}

	public boolean hasPermissionsPermission() throws PortalException {
		if (_hasPermissionsPermission == null) {
			_hasPermissionsPermission = DLFileShortcutPermission.contains(
				_permissionChecker, _fileShortcut, ActionKeys.PERMISSIONS);
		}

		return _hasPermissionsPermission;
	}

	public boolean hasUpdatePermission() throws PortalException {
		if (_hasUpdatePermission == null) {
			_hasUpdatePermission = DLFileShortcutPermission.contains(
				_permissionChecker, _fileShortcut, ActionKeys.UPDATE);
		}

		return _hasUpdatePermission;
	}

	public boolean isDLFileShortcut() {
		if (_dlFileShortcut == null) {
			if (_fileShortcut.getModel() instanceof DLFileShortcut) {
				_dlFileShortcut = true;
			}
			else {
				_dlFileShortcut = false;
			}
		}

		return _dlFileShortcut;
	}

	public boolean isEditActionAvailable() throws PortalException {
		return isUpdatable();
	}

	public boolean isFileShortcutDeletable() throws PortalException {
		return hasDeletePermission();
	}

	public boolean isMoveActionAvailable() throws PortalException {
		return isUpdatable();
	}

	public boolean isPermissionsButtonVisible() throws PortalException {
		return hasPermissionsPermission();
	}

	public boolean isUpdatable() throws PortalException {
		return hasUpdatePermission();
	}

	private void _setValuesForNullFileShortcut() {
		_hasDeletePermission = false;
		_hasExportImportPermission = false;
		_hasPermissionsPermission = false;
		_hasUpdatePermission = false;
	}

	private Boolean _dlFileShortcut;
	private final FileShortcut _fileShortcut;
	private Boolean _hasDeletePermission;
	private Boolean _hasExportImportPermission;
	private Boolean _hasPermissionsPermission;
	private Boolean _hasUpdatePermission;
	private final PermissionChecker _permissionChecker;

}