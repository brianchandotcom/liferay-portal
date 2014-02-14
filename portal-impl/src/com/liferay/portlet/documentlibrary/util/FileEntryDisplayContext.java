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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class FileEntryDisplayContext {

	public FileEntryDisplayContext(
		HttpServletRequest request, FileEntry fileEntry,
		FileVersion fileVersion) {

		_request = request;
		_fileEntry = fileEntry;
		_fileVersion = fileVersion;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_companyId = themeDisplay.getCompanyId();
		_fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);
		_folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");
		_permissionChecker = themeDisplay.getPermissionChecker();
		_portletDisplay = themeDisplay.getPortletDisplay();
		_scopeGroupId = themeDisplay.getScopeGroupId();
	}

	public String getPublishButtonLabel() throws SystemException {
		String publishButtonLabel = "publish";

		if (_hasWorkflowDefinitionLink()) {
			publishButtonLabel = "submit-for-publication";
		}

		if (isSaveAsDraft()) {
			publishButtonLabel = "save";
		}

		return publishButtonLabel;
	}

	public String getSaveButtonLabel() {
		String saveButtonLabel = "save";

		if ((_fileVersion == null) || _isDraft() || _isApproved()) {
			saveButtonLabel = "save-as-draft";
		}

		return saveButtonLabel;
	}

	public boolean isCancelCheckoutDocumentButtonDisabled() {
		return false;
	}

	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException, SystemException {

		if ((_hasUpdatePermission() && _isCheckedOut() && _isLockedByMe()) ||
			(_isCheckedOut() && _hasOverrideCheckoutPermission())) {

			return true;
		}

		return false;
	}

	public boolean isCheckinButtonDisabled() {
		return false;
	}

	public boolean isCheckinButtonVisible()
		throws PortalException, SystemException {

		if (_hasUpdatePermission() && _isLockedByMe()) {
			return true;
		}

		return false;
	}

	public boolean isCheckoutDocumentButtonVisible()
		throws PortalException, SystemException {

		if (_hasUpdatePermission() && !_isCheckedOut()) {
			return true;
		}

		return false;
	}

	public boolean isCheckoutDocumentDisabled() {
		return false;
	}

	public boolean isDeleteButtonVisible()
		throws PortalException, SystemException {

		if (_hasDeletePermission() && !_isCheckedOutByOther() &&
			(!_isDLFileEntry() || !_isTrashEnabled())) {

			return true;
		}

		return false;
	}

	public boolean isDownloadButtonVisible()
		throws PortalException, SystemException {

		return _hasViewPermission();
	}

	public boolean isEditButtonVisible()
		throws PortalException, SystemException {

		if (_hasUpdatePermission() && !_isCheckedOutByOther()) {
			return true;
		}

		return false;
	}

	public boolean isMoveButtonVisible()
		throws PortalException, SystemException {

		if (_hasUpdatePermission() && !_isCheckedOutByOther()) {
			return true;
		}

		return false;
	}

	public boolean isMoveToTheRecycleBinButtonVisible()
		throws PortalException, SystemException {

		if (_hasDeletePermission() && !_isCheckedOutByOther() &&
			_isDLFileEntry() && _isTrashEnabled()) {

			return true;
		}

		return false;
	}

	public boolean isOpenInMsOfficeButtonVisible()
		throws PortalException, SystemException {

		if (_hasViewPermission() && _isOfficeDoc() && _isWebDAVEnabled() &&
			_isIEOnWin32()) {

			return true;
		}

		return false;
	}

	public boolean isPermissionsButtonVisible()
		throws PortalException, SystemException {

		return _hasPermissionsPermission();
	}

	public boolean isPublishButtonDisabled() {
		if ((_isCheckedOut() && !_isLockedByMe()) || (_isPending() &&
			 _isDLFileEntryDraftsEnabled())) {

			return true;
		}

		return false;
	}

	public boolean isPublishButtonVisible() {
		return true;
	}

	public boolean isSaveAsDraft() {
		if ((_isCheckedOut() || _isPending()) &&
			!_isDLFileEntryDraftsEnabled()) {

			return true;
		}

		return false;
	}

	public boolean isSaveButtonDisabled() {
		return _isCheckedOut() && !_isLockedByMe();
	}

	public boolean isSaveButtonVisible() {
		return _isDLFileEntryDraftsEnabled();
	}

	private boolean _hasDeletePermission()
		throws PortalException, SystemException {

		if (_hasDeletePermission == null) {
			_hasDeletePermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.DELETE);
		}

		return _hasDeletePermission;
	}

	private boolean _hasOverrideCheckoutPermission()
		throws PortalException, SystemException {

		if (_hasOverrideCheckoutPermission == null) {
			_hasOverrideCheckoutPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.OVERRIDE_CHECKOUT);
		}

		return _hasOverrideCheckoutPermission;
	}

	private boolean _hasPermissionsPermission()
		throws PortalException, SystemException {

		if (_hasPermissionsPermission == null) {
			_hasPermissionsPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.PERMISSIONS);
		}

		return _hasPermissionsPermission;
	}

	private boolean _hasUpdatePermission()
		throws PortalException, SystemException {

		if (_hasUpdatePermission == null) {
			_hasUpdatePermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.UPDATE);
		}

		return _hasUpdatePermission;
	}

	private boolean _hasViewPermission()
		throws PortalException, SystemException {

		if (_hasViewPermission == null) {
			_hasViewPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.VIEW);
		}

		return _hasViewPermission;
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

	private boolean _isApproved() {
		return _fileVersion.isApproved();
	}

	private boolean _isCheckedOut() {
		return _fileEntry.isCheckedOut();
	}

	private boolean _isCheckedOutByOther() {
		return (_isCheckedOut() && !_isLockedByMe());
	}

	private boolean _isDLFileEntry() {
		if (_fileEntry.getModel() instanceof DLFileEntry) {
			return true;
		}

		return false;
	}

	private boolean _isDLFileEntryDraftsEnabled() {
		return PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED;
	}

	private boolean _isDraft() {
		return _fileVersion.isDraft();
	}

	private boolean _isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(_request);
		}

		return _ieOnWin32;
	}

	private boolean _isLockedByMe() {
		return _fileEntry.hasLock();
	}

	private boolean _isOfficeDoc() {
		if (_officeDoc == null) {
			_officeDoc = DLUtil.isOfficeExtension(_fileVersion.getExtension());
		}

		return _officeDoc;
	}

	private boolean _isPending() {
		return _fileVersion.isPending();
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
	private FileEntry _fileEntry;
	private long _fileEntryTypeId;
	private FileVersion _fileVersion;
	private long _folderId;
	private Boolean _hasDeletePermission;
	private Boolean _hasOverrideCheckoutPermission;
	private Boolean _hasPermissionsPermission;
	private Boolean _hasUpdatePermission;
	private Boolean _hasViewPermission;
	private Boolean _ieOnWin32;
	private Boolean _officeDoc;
	private PermissionChecker _permissionChecker;
	private PortletDisplay _portletDisplay;
	private HttpServletRequest _request;
	private long _scopeGroupId;
	private Boolean _trashEnabled;

}