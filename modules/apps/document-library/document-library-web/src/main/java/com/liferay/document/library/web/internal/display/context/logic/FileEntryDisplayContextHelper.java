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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Iván Zaera
 */
public class FileEntryDisplayContextHelper {

	public FileEntryDisplayContextHelper(
		PermissionChecker permissionChecker, FileEntry fileEntry) {

		_permissionChecker = permissionChecker;

		_fileEntry = fileEntry;

		if (_fileEntry == null) {
			_setValuesForNullFileEntry();
		}
	}

	public DLFileEntryType getDLFileEntryType() throws PortalException {
		if (isDLFileEntry()) {
			DLFileEntry dlFileEntry = (DLFileEntry)_fileEntry.getModel();

			return dlFileEntry.getDLFileEntryType();
		}

		return null;
	}

	public FileEntry getFileEntry() {
		return _fileEntry;
	}

	public boolean hasDeletePermission() throws PortalException {
		if (_hasDeletePermission == null) {
			_hasDeletePermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.DELETE);
		}

		return _hasDeletePermission;
	}

	public boolean hasExportImportPermission() throws PortalException {
		if (_hasExportImportPermission == null) {
			_hasExportImportPermission = GroupPermissionUtil.contains(
				_permissionChecker, _fileEntry.getGroupId(),
				ActionKeys.EXPORT_IMPORT_PORTLET_INFO);
		}

		return _hasExportImportPermission;
	}

	public boolean hasLock() {
		if (_hasLock == null) {
			_hasLock = _fileEntry.hasLock();
		}

		return _hasLock;
	}

	public boolean hasOverrideCheckoutPermission() throws PortalException {
		if (_hasOverrideCheckoutPermission == null) {
			_hasOverrideCheckoutPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.OVERRIDE_CHECKOUT);
		}

		return _hasOverrideCheckoutPermission;
	}

	public boolean hasPermissionsPermission() throws PortalException {
		if (_hasPermissionsPermission == null) {
			_hasPermissionsPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.PERMISSIONS);
		}

		return _hasPermissionsPermission;
	}

	public boolean hasUpdatePermission() throws PortalException {
		if (_hasUpdatePermission == null) {
			_hasUpdatePermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.UPDATE);
		}

		return _hasUpdatePermission;
	}

	public boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission == null) {
			_hasViewPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	public boolean isCancelCheckoutDocumentActionAvailable()
		throws PortalException {

		if ((isCheckinActionAvailable() ||
			 (isCheckedOut() && hasOverrideCheckoutPermission())) &&
			_hasPreviousVersions()) {

			return true;
		}

		return false;
	}

	public boolean isCheckedOut() {
		if (_checkedOut == null) {
			_checkedOut = _fileEntry.isCheckedOut();
		}

		return _checkedOut;
	}

	public boolean isCheckedOutByMe() {
		if (isCheckedOut() && isLockedByMe()) {
			return true;
		}

		return false;
	}

	public boolean isCheckedOutByOther() {
		if (isCheckedOut() && !isLockedByMe()) {
			return true;
		}

		return false;
	}

	public boolean isCheckinActionAvailable() throws PortalException {
		if (hasUpdatePermission() && isLockedByMe() && isSupportsLocking()) {
			return true;
		}

		return false;
	}

	public boolean isCheckoutDocumentActionAvailable() throws PortalException {
		if (hasUpdatePermission() && !isCheckedOut() && isSupportsLocking()) {
			return true;
		}

		return false;
	}

	public boolean isDLFileEntry() {
		if (_dlFileEntry == null) {
			if (_fileEntry.getModel() instanceof DLFileEntry) {
				_dlFileEntry = true;
			}
			else {
				_dlFileEntry = false;
			}
		}

		return _dlFileEntry;
	}

	public boolean isDownloadActionAvailable() throws PortalException {
		return hasViewPermission();
	}

	public boolean isEditActionAvailable() throws PortalException {
		return isUpdatable();
	}

	public boolean isFileEntryDeletable() throws PortalException {
		if (hasDeletePermission() && !isCheckedOutByOther()) {
			return true;
		}

		return false;
	}

	public boolean isLockedByMe() {
		if (hasLock()) {
			return true;
		}

		return false;
	}

	public boolean isMoveActionAvailable() throws PortalException {
		return isUpdatable();
	}

	public boolean isPermissionsButtonVisible() throws PortalException {
		return hasPermissionsPermission();
	}

	public boolean isSupportsLocking() {
		if (_supportsLocking == null) {
			_supportsLocking = _fileEntry.isSupportsLocking();
		}

		return _supportsLocking;
	}

	public boolean isUpdatable() throws PortalException {
		if (hasUpdatePermission() && !isCheckedOutByOther()) {
			return true;
		}

		return false;
	}

	private boolean _hasPreviousVersions() {
		if (_fileEntry == null) {
			return false;
		}

		if (_fileEntry.getFileVersionsCount(WorkflowConstants.STATUS_ANY) > 1) {
			return true;
		}

		return false;
	}

	private void _setValuesForNullFileEntry() {
		_checkedOut = false;
		_dlFileEntry = true;
		_hasDeletePermission = false;
		_hasExportImportPermission = false;
		_hasLock = false;
		_hasOverrideCheckoutPermission = false;
		_hasPermissionsPermission = true;
		_hasUpdatePermission = true;
		_hasViewPermission = false;
		_supportsLocking = false;
	}

	private Boolean _checkedOut;
	private Boolean _dlFileEntry;
	private final FileEntry _fileEntry;
	private Boolean _hasDeletePermission;
	private Boolean _hasExportImportPermission;
	private Boolean _hasLock;
	private Boolean _hasOverrideCheckoutPermission;
	private Boolean _hasPermissionsPermission;
	private Boolean _hasUpdatePermission;
	private Boolean _hasViewPermission;
	private final PermissionChecker _permissionChecker;
	private Boolean _supportsLocking;

}