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

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.context.helper.FileEntryDisplayContextHelper;
import com.liferay.portlet.documentlibrary.context.helper.FileVersionDisplayContextHelper;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultDLEditFileEntryDisplayContext
	implements DLEditFileEntryDisplayContext {

	public DefaultDLEditFileEntryDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileEntryType dlFileEntryType)
		throws PortalException {

		_init(request, dlFileEntryType, null);
	}

	public DefaultDLEditFileEntryDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileEntry fileEntry)
		throws PortalException {

		_init(request, null, fileEntry);
	}

	@Override
	public long getMaximumUploadSize() {
		long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if (fileMaxSize == 0) {
			fileMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		}

		return fileMaxSize;
	}

	@Override
	public String getPublishButtonLabel() {
		String publishButtonLabel = "publish";

		if (_hasFolderWorkflowDefinitionLink()) {
			publishButtonLabel = "submit-for-publication";
		}

		if (_dlPortletInstanceSettings.isEnableFileEntryDrafts()) {
			publishButtonLabel = "save";
		}

		return publishButtonLabel;
	}

	@Override
	public String getSaveButtonLabel() {
		String saveButtonLabel = "save";

		if ((_fileVersion == null) ||
			_fileVersionDisplayContextHelper.isApproved() ||
			_fileVersionDisplayContextHelper.isDraft()) {

			saveButtonLabel = "save-as-draft";
		}

		return saveButtonLabel;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException {

		return _fileEntryDisplayContextHelper.
			isCancelCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isCheckinButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCheckinButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isCheckinButtonVisible();
	}

	@Override
	public boolean isCheckoutDocumentButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isDDMStructureVisible(DDMStructure ddmStructure) {
		return true;
	}

	@Override
	public boolean isPublishButtonDisabled() {
		if (_fileEntryDisplayContextHelper.isCheckedOutByOther() ||
			(_fileVersionDisplayContextHelper.isPending() &&
			 _dlPortletInstanceSettings.isEnableFileEntryDrafts())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPublishButtonVisible() {
		return true;
	}

	@Override
	public boolean isSaveButtonDisabled() {
		if (_fileEntryDisplayContextHelper.isCheckedOutByOther()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSaveButtonVisible() {
		return _dlPortletInstanceSettings.isEnableFileEntryDrafts();
	}

	private boolean _hasFolderWorkflowDefinitionLink() {
		try {
			if (_dlFileEntryType == null) {
				return false;
			}

			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long folderId = BeanParamUtil.getLong(
				_fileEntry, _request, "folderId");

			return DLUtil.hasWorkflowDefinitionLink(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				folderId, _dlFileEntryType.getFileEntryTypeId());
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to check if folder has workflow definition link", e);
		}
	}

	private void _init(
			HttpServletRequest request, DLFileEntryType dlFileEntryType,
			FileEntry fileEntry)
		throws PortalException {

		_request = request;
		_dlFileEntryType = dlFileEntryType;
		_fileEntry = fileEntry;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_dlPortletInstanceSettings = DLPortletInstanceSettings.getInstance(
			themeDisplay.getLayout(), portletDisplay.getId());
		_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
			themeDisplay.getPermissionChecker(), _fileEntry);

		if ((_dlFileEntryType == null) && (_fileEntry != null)) {
			_dlFileEntryType =
				_fileEntryDisplayContextHelper.getDLFileEntryType();
		}

		if (fileEntry != null) {
			_fileVersion = fileEntry.getFileVersion();
		}

		_fileVersionDisplayContextHelper = new FileVersionDisplayContextHelper(
			_fileVersion);
	}

	private static final UUID _UUID = UUID.fromString(
		"63326141-02F6-42B5-AE38-ABC73FA72BB5");

	private DLFileEntryType _dlFileEntryType;
	private DLPortletInstanceSettings _dlPortletInstanceSettings;
	private FileEntry _fileEntry;
	private FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private FileVersion _fileVersion;
	private FileVersionDisplayContextHelper _fileVersionDisplayContextHelper;
	private HttpServletRequest _request;

}