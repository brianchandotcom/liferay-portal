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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class FileEntryDisplayContext {

	public FileEntryDisplayContext(
			HttpServletRequest request, FileEntry fileEntry)
		throws PortalException, SystemException {

		this(
			request, fileEntry,
			(fileEntry == null) ? null : fileEntry.getLatestFileVersion());
	}

	public FileEntryDisplayContext(
		HttpServletRequest request, FileEntry fileEntry,
		FileVersion fileVersion) {

		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_companyId = themeDisplay.getCompanyId();
		_fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);
		_folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");
		_permissionChecker = themeDisplay.getPermissionChecker();
		_portletDisplay = themeDisplay.getPortletDisplay();
		_scopeGroupId = themeDisplay.getScopeGroupId();

		_fileEntryChecker = new FileEntryChecker(
			_permissionChecker, fileEntry, fileVersion);
	}

	public String getPublishButtonLabel() throws SystemException {
		String publishButtonLabel = "publish";

		if (_hasWorkflowDefinitionLink()) {
			publishButtonLabel = "submit-for-publication";
		}

		if (_isFileEntrySaveAsDraft()) {
			publishButtonLabel = "save";
		}

		return publishButtonLabel;
	}

	public String getSaveButtonLabel() {
		String saveButtonLabel = "save";

		FileVersion fileVersion = _fileEntryChecker.getFileVersion();

		if ((fileVersion == null) || _fileEntryChecker.isDraft() ||
			_fileEntryChecker.isApproved()) {

			saveButtonLabel = "save-as-draft";
		}

		return saveButtonLabel;
	}

	public boolean isCancelCheckoutDocumentButtonDisabled() {
		return false;
	}

	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException, SystemException {

		if ((_fileEntryChecker.hasUpdatePermission() &&
			 _fileEntryChecker.isCheckedOut() && _isFileEntryLockedByMe()) ||
			(_fileEntryChecker.isCheckedOut() &&
			 _fileEntryChecker.hasOverrideCheckoutPermission())) {

			return true;
		}

		return false;
	}

	public boolean isCheckinButtonDisabled() {
		return false;
	}

	public boolean isCheckinButtonVisible()
		throws PortalException, SystemException {

		if (_fileEntryChecker.hasUpdatePermission() &&
			_isFileEntryLockedByMe()) {

			return true;
		}

		return false;
	}

	public boolean isCheckoutDocumentButtonVisible()
		throws PortalException, SystemException {

		if (_fileEntryChecker.hasUpdatePermission() &&
			_fileEntryChecker.isSupportsLocking() &&
			!_fileEntryChecker.isCheckedOut()) {

			return true;
		}

		return false;
	}

	public boolean isCheckoutDocumentDisabled() {
		return false;
	}

	public boolean isDeleteButtonVisible()
		throws PortalException, SystemException {

		if (_fileEntryChecker.hasDeletePermission() &&
			!_isFileEntryCheckedOutByOther() &&
			(!_fileEntryChecker.isDLFileEntry() || !_isTrashEnabled())) {

			return true;
		}

		return false;
	}

	public boolean isDownloadButtonVisible()
		throws PortalException, SystemException {

		return _fileEntryChecker.hasViewPermission();
	}

	public boolean isEditButtonVisible()
		throws PortalException, SystemException {

		if (_fileEntryChecker.hasUpdatePermission() &&
			!_isFileEntryCheckedOutByOther()) {

			return true;
		}

		return false;
	}

	public boolean isMoveButtonVisible()
		throws PortalException, SystemException {

		if (_fileEntryChecker.hasUpdatePermission() &&
			!_isFileEntryCheckedOutByOther()) {

			return true;
		}

		return false;
	}

	public boolean isMoveToTheRecycleBinButtonVisible()
		throws PortalException, SystemException {

		if (_fileEntryChecker.hasDeletePermission() &&
			!_isFileEntryCheckedOutByOther() &&
			_fileEntryChecker.isDLFileEntry() && _isTrashEnabled()) {

			return true;
		}

		return false;
	}

	public boolean isOpenInMsOfficeButtonVisible()
		throws PortalException, SystemException {

		if (_fileEntryChecker.hasViewPermission() &&
			_fileEntryChecker.isOfficeDoc() && _isWebDAVEnabled() &&
			_isIEOnWin32()) {

			return true;
		}

		return false;
	}

	public boolean isPermissionsButtonVisible()
		throws PortalException, SystemException {

		return _fileEntryChecker.hasPermissionsPermission();
	}

	public boolean isPublishButtonDisabled() {
		if ((_fileEntryChecker.isCheckedOut() && !_isFileEntryLockedByMe()) ||
			(_fileEntryChecker.isPending() &&
			 _isDLFileEntryDraftsEnabled())) {

			return true;
		}

		return false;
	}

	public boolean isPublishButtonVisible() {
		return true;
	}

	public boolean isSaveButtonDisabled() {
		return _fileEntryChecker.isCheckedOut() && !_isFileEntryLockedByMe();
	}

	public boolean isSaveButtonVisible() {
		return _isDLFileEntryDraftsEnabled();
	}

	private boolean _hasWorkflowDefinitionLink() throws SystemException {
		try {
			return DLUtil.hasWorkflowDefinitionLink(
				_companyId, _scopeGroupId, _folderId, _fileEntryTypeId);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to check if file entry has workflow definition link",
				e);
		}
	}

	private boolean _isDLFileEntryDraftsEnabled() {
		return PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED;
	}

	private boolean _isFileEntryCheckedOutByOther() {
		if (_fileEntryChecker.isCheckedOut() && !_isFileEntryLockedByMe()) {
			return true;
		}

		return false;
	}

	private boolean _isFileEntryLockedByMe() {
		if (_fileEntryChecker.hasLock()) {
			return true;
		}

		return false;
	}

	private boolean _isFileEntrySaveAsDraft() {
		if ((_fileEntryChecker.isCheckedOut() ||
			 _fileEntryChecker.isPending()) && !_isDLFileEntryDraftsEnabled()) {

			return true;
		}

		return false;
	}

	private boolean _isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(_request);
		}

		return _ieOnWin32;
	}

	private boolean _isTrashEnabled() throws PortalException, SystemException {
		if (_trashEnabled == null) {
			_trashEnabled = TrashUtil.isTrashEnabled(_scopeGroupId);
		}

		return _trashEnabled;
	}

	private boolean _isWebDAVEnabled() {
		return _portletDisplay.isWebDAVEnabled();
	}

	private long _companyId;
	private FileEntryChecker _fileEntryChecker;
	private long _fileEntryTypeId;
	private long _folderId;
	private Boolean _ieOnWin32;
	private PermissionChecker _permissionChecker;
	private PortletDisplay _portletDisplay;
	private HttpServletRequest _request;
	private long _scopeGroupId;
	private Boolean _trashEnabled;

}