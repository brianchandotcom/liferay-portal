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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryPermission;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Adolfo Pérez
 */
public class DLViewFileEntryDisplayContext {

	public DLViewFileEntryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public FileEntry getFileEntry() {
		if (_fileEntry != null) {
			return _fileEntry;
		}

		_fileEntry = (FileEntry)_renderRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

		return _fileEntry;
	}

	public FileVersion getFileVersion() throws PortalException {
		if (_fileVersion != null) {
			return _fileVersion;
		}

		_fileVersion = (FileVersion)_renderRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

		if (_fileVersion != null) {
			return _fileVersion;
		}

		FileEntry fileEntry = getFileEntry();

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		User user = permissionChecker.getUser();

		if ((_themeDisplay.getUserId() == fileEntry.getUserId()) ||
			permissionChecker.isContentReviewer(
				user.getCompanyId(), _themeDisplay.getScopeGroupId()) ||
			DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.UPDATE)) {

			_fileVersion = fileEntry.getLatestFileVersion();
		}
		else {
			_fileVersion = fileEntry.getFileVersion();
		}

		return _fileVersion;
	}

	public String getRedirect() throws PortalException {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		long parentFolderId = _getParentFolderId();

		String mvcRenderCommandName = "/document_library/view";

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			mvcRenderCommandName = "/document_library/view_folder";
		}

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
		portletURL.setParameter("folderId", String.valueOf(parentFolderId));

		_redirect = portletURL.toString();

		return _redirect;
	}

	public boolean isVersionSpecific() {
		if (_versionSpecific != null) {
			return _versionSpecific;
		}

		FileVersion fileVersion = (FileVersion)_renderRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

		if (fileVersion == null) {
			_versionSpecific = false;
		}
		else {
			_versionSpecific = true;
		}

		return _versionSpecific;
	}

	private long _getParentFolderId() throws PortalException {
		FileEntry fileEntry = getFileEntry();

		if (DLFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), fileEntry.getGroupId(),
				fileEntry.getFolderId(), ActionKeys.VIEW)) {

			return fileEntry.getFolderId();
		}

		return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}

	private FileEntry _fileEntry;
	private FileVersion _fileVersion;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private Boolean _versionSpecific;

}