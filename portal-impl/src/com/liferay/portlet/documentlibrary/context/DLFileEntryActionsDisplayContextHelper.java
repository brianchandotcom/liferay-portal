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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;

/**
 * @author Iván Zaera
 */
public class DLFileEntryActionsDisplayContextHelper {

	public DLFileEntryActionsDisplayContextHelper(
		PermissionChecker permissionChecker, FileEntry fileEntry,
		FileVersion fileVersion) {

		if ((fileEntry != null) && (fileVersion == null)) {
			throw new IllegalArgumentException(
				"File version cannot be null if file entry is not null");
		}

		_permissionChecker = permissionChecker;
		_fileEntry = fileEntry;
		_fileVersion = fileVersion;

		if (_fileEntry == null) {
			_setValuesForNullFileEntry();
		}
	}

	public FileEntry getFileEntry() {
		return _fileEntry;
	}

	public FileVersion getFileVersion() {
		return _fileVersion;
	}

	public boolean hasDeletePermission()
		throws PortalException, SystemException {

		if (_hasDeletePermission == null) {
			_hasDeletePermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.DELETE);
		}

		return _hasDeletePermission;
	}

	public boolean hasLock() {
		if (_hasLock == null) {
			_hasLock = _fileEntry.hasLock();
		}

		return _hasLock;
	}

	public boolean hasOverrideCheckoutPermission()
		throws PortalException, SystemException {

		if (_hasOverrideCheckoutPermission == null) {
			_hasOverrideCheckoutPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.OVERRIDE_CHECKOUT);
		}

		return _hasOverrideCheckoutPermission;
	}

	public boolean hasPermissionsPermission()
		throws PortalException, SystemException {

		if (_hasPermissionsPermission == null) {
			_hasPermissionsPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.PERMISSIONS);
		}

		return _hasPermissionsPermission;
	}

	public boolean hasUpdatePermission()
		throws PortalException, SystemException {

		if (_hasUpdatePermission == null) {
			_hasUpdatePermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.UPDATE);
		}

		return _hasUpdatePermission;
	}

	public boolean hasViewPermission() throws PortalException, SystemException {
		if (_hasViewPermission == null) {
			_hasViewPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	public boolean isApproved() {
		if (_approved == null) {
			_approved = _fileVersion.isApproved();
		}

		return _approved;
	}

	public boolean isCheckedOut() {
		if (_checkedOut == null) {
			_checkedOut = _fileEntry.isCheckedOut();
		}

		return _checkedOut;
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

	public boolean isDraft() {
		if (_draft == null) {
			_draft = _fileVersion.isDraft();
		}

		return _draft;
	}

	public boolean isLockedByMe() {
		if (hasLock()) {
			return true;
		}

		return false;
	}

	public boolean isOfficeDoc() {
		if (_officeDoc == null) {
			_officeDoc = DLUtil.isOfficeExtension(_fileVersion.getExtension());
		}

		return _officeDoc;
	}

	public boolean isPending() {
		if (_pending == null) {
			_pending = _fileVersion.isPending();
		}

		return _pending;
	}

	public boolean isSupportsLocking() {
		if (_supportsLocking == null) {
			_supportsLocking = _fileEntry.isSupportsLocking();
		}

		return _supportsLocking;
	}

	private void _setValuesForNullFileEntry() {
		_approved = false;
		_checkedOut = false;
		_dlFileEntry = true;
		_draft = false;
		_hasDeletePermission = false;
		_hasLock = false;
		_hasOverrideCheckoutPermission = false;
		_hasPermissionsPermission = true;
		_hasUpdatePermission = true;
		_hasViewPermission = false;
		_officeDoc = false;
		_pending = false;
		_supportsLocking = false;
	}

	private Boolean _approved;
	private Boolean _checkedOut;
	private Boolean _dlFileEntry;
	private Boolean _draft;
	private FileEntry _fileEntry;
	private FileVersion _fileVersion;
	private Boolean _hasDeletePermission;
	private Boolean _hasLock;
	private Boolean _hasOverrideCheckoutPermission;
	private Boolean _hasPermissionsPermission;
	private Boolean _hasUpdatePermission;
	private Boolean _hasViewPermission;
	private Boolean _officeDoc;
	private Boolean _pending;
	private PermissionChecker _permissionChecker;
	private Boolean _supportsLocking;

}