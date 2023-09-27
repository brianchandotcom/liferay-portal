/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context.helper;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileShortcutPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.util.RepositoryUtil;

/**
 * @author IL (Brian) Kim
 */
public class FileShortcutDisplayContextHelper {

	public FileShortcutDisplayContextHelper(
		PermissionChecker permissionChecker, FileShortcut fileShortcut) {

		_permissionChecker = permissionChecker;
		_fileShortcut = fileShortcut;

		if (fileShortcut == null) {
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

	public boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission == null) {
			_hasViewPermission = DLFileShortcutPermission.contains(
				_permissionChecker, _fileShortcut, ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	public boolean isCopyActionAvailable() throws PortalException {
		if (hasViewPermission() && !_isExternalRepository()) {
			return true;
		}

		return false;
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

	private boolean _isExternalRepository() {
		if ((_fileShortcut != null) &&
			RepositoryUtil.isExternalRepository(
				_fileShortcut.getRepositoryId())) {

			return true;
		}

		return false;
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
	private Boolean _hasViewPermission;
	private final PermissionChecker _permissionChecker;

}