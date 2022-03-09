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

import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLFilePicker;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.document.library.web.internal.display.context.helper.DLRequestHelper;
import com.liferay.document.library.web.internal.display.context.helper.FileEntryDisplayContextHelper;
import com.liferay.document.library.web.internal.display.context.helper.FileVersionDisplayContextHelper;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.RepositoryUtil;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultDLEditFileEntryDisplayContext
	implements DLEditFileEntryDisplayContext {

	public DefaultDLEditFileEntryDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DLFileEntryType dlFileEntryType, DLValidator dlValidator,
		StorageEngine storageEngine) {

		this(
			httpServletRequest, dlFileEntryType, dlValidator, null,
			storageEngine);
	}

	public DefaultDLEditFileEntryDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, DLValidator dlValidator,
		FileEntry fileEntry, StorageEngine storageEngine) {

		this(
			httpServletRequest, (DLFileEntryType)null, dlValidator, fileEntry,
			storageEngine);
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		return _storageEngine.getDDMFormValues(classPK);
	}

	@Override
	public DLFilePicker getDLFilePicker(String onFilePickCallback) {
		return null;
	}

	@Override
	public String getFriendlyURLBase() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(4);

		sb.append("/documents");

		String urlSeparatorFileEntry =
			FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY;

		sb.append(
			urlSeparatorFileEntry.substring(
				0, urlSeparatorFileEntry.length() - 1));

		Group group = themeDisplay.getScopeGroup();

		sb.append(group.getFriendlyURL());

		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	@Override
	public long getMaximumUploadRequestSize() {
		return UploadServletRequestConfigurationHelperUtil.getMaxSize();
	}

	@Override
	public long getMaximumUploadSize() {
		return _dlValidator.getMaxAllowableSize(_getMimeType());
	}

	@Override
	public String getPublishButtonLabel() {
		if (_hasFolderWorkflowDefinitionLink()) {
			return "submit-for-publication";
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		if (dlPortletInstanceSettings.isEnableFileEntryDrafts() ||
			_fileEntryDisplayContextHelper.isCheckedOut()) {

			return "save";
		}

		return "publish";
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
			isCancelCheckoutDocumentActionAvailable();
	}

	@Override
	public boolean isCheckinButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCheckinButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isCheckinActionAvailable();
	}

	@Override
	public boolean isCheckoutDocumentButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.
			isCheckoutDocumentActionAvailable();
	}

	@Override
	public boolean isDDMStructureVisible(DDMStructure ddmStructure) {
		DDMForm ddmForm = ddmStructure.getDDMForm();

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		return !ddmFormFields.isEmpty();
	}

	@Override
	public boolean isFolderSelectionVisible() {
		return _showSelectFolder;
	}

	@Override
	public boolean isNeverExpire() throws PortalException {
		if (_neverExpire != null) {
			return _neverExpire;
		}

		_neverExpire = ParamUtil.getBoolean(
			_httpServletRequest, "neverExpire", true);

		if (((_fileEntry != null) &&
			 (_fileEntry.getExpirationDate() != null)) ||
			((_fileVersion != null) &&
			 (_fileVersion.getExpirationDate() != null))) {

			_neverExpire = false;
		}

		return _neverExpire;
	}

	@Override
	public boolean isNeverReview() throws PortalException {
		if (_neverReview != null) {
			return _neverReview;
		}

		_neverReview = ParamUtil.getBoolean(
			_httpServletRequest, "neverReview", true);

		if (((_fileEntry != null) && (_fileEntry.getReviewDate() != null)) ||
			((_fileVersion != null) &&
			 (_fileVersion.getReviewDate() != null))) {

			_neverReview = false;
		}

		return _neverReview;
	}

	@Override
	public boolean isPermissionsVisible() {
		long repositoryId = ParamUtil.getLong(
			_dlRequestHelper.getRequest(), "repositoryId");

		if ((_fileEntry == null) &&
			!RepositoryUtil.isExternalRepository(repositoryId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPublishButtonDisabled() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		if (_fileEntryDisplayContextHelper.isCheckedOutByOther() ||
			(_fileVersionDisplayContextHelper.isPending() &&
			 dlPortletInstanceSettings.isEnableFileEntryDrafts())) {

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
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		return dlPortletInstanceSettings.isEnableFileEntryDrafts();
	}

	@Override
	public boolean isVersionInfoVisible() {
		return true;
	}

	private DefaultDLEditFileEntryDisplayContext(
		HttpServletRequest httpServletRequest, DLFileEntryType dlFileEntryType,
		DLValidator dlValidator, FileEntry fileEntry,
		StorageEngine storageEngine) {

		try {
			_httpServletRequest = httpServletRequest;
			_dlValidator = dlValidator;
			_fileEntry = fileEntry;
			_storageEngine = storageEngine;

			_dlRequestHelper = new DLRequestHelper(httpServletRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				themeDisplay.getPermissionChecker(), _fileEntry);

			if ((dlFileEntryType == null) && (fileEntry != null)) {
				_dlFileEntryType =
					_fileEntryDisplayContextHelper.getDLFileEntryType();
			}
			else {
				_dlFileEntryType = dlFileEntryType;
			}

			if (fileEntry != null) {
				_fileVersion = fileEntry.getFileVersion();
			}
			else {
				_fileVersion = null;
			}

			_fileVersionDisplayContextHelper =
				new FileVersionDisplayContextHelper(_fileVersion);

			_showSelectFolder = ParamUtil.getBoolean(
				httpServletRequest, "showSelectFolder");
		}
		catch (PortalException portalException) {
			throw new SystemException(
				"Unable to build DefaultDLEditFileEntryDisplayContext for " +
					fileEntry,
				portalException);
		}
	}

	private String _getMimeType() {
		if (_fileVersion == null) {
			return null;
		}

		return _fileVersion.getMimeType();
	}

	private boolean _hasFolderWorkflowDefinitionLink() {
		try {
			long folderId = BeanParamUtil.getLong(
				_fileEntry, _dlRequestHelper.getRequest(), "folderId");

			long fileEntryTypeId =
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT;

			if (_dlFileEntryType != null) {
				fileEntryTypeId = _dlFileEntryType.getFileEntryTypeId();
			}

			return DLUtil.hasWorkflowDefinitionLink(
				_dlRequestHelper.getCompanyId(),
				_dlRequestHelper.getScopeGroupId(), folderId, fileEntryTypeId);
		}
		catch (Exception exception) {
			throw new SystemException(
				"Unable to check if folder has workflow definition link",
				exception);
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"63326141-02F6-42B5-AE38-ABC73FA72BB5");

	private final DLFileEntryType _dlFileEntryType;
	private final DLRequestHelper _dlRequestHelper;
	private final DLValidator _dlValidator;
	private final FileEntry _fileEntry;
	private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private final FileVersion _fileVersion;
	private final FileVersionDisplayContextHelper
		_fileVersionDisplayContextHelper;
	private HttpServletRequest _httpServletRequest;
	private Boolean _neverExpire;
	private Boolean _neverReview;
	private final boolean _showSelectFolder;
	private final StorageEngine _storageEngine;

}