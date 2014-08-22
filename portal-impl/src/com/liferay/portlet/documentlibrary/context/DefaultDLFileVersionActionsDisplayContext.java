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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public class DefaultDLFileVersionActionsDisplayContext
	implements DLFileVersionActionsDisplayContext {

	public DefaultDLFileVersionActionsDisplayContext(
			PageContext pageContext, FileVersion fileVersion)
		throws PortalException {

		_pageContext = pageContext;
		_fileVersion = fileVersion;

		if (fileVersion != null) {
			_fileEntry = fileVersion.getFileEntry();
		}

		_request = (HttpServletRequest)pageContext.getRequest();

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_dlFileEntryActionsDisplayContextHelper =
			new DLFileEntryActionsDisplayContextHelper(
				_themeDisplay.getPermissionChecker(), _fileEntry, fileVersion);

		_fileEntryTypeId = ParamUtil.getLong(_request, "fileEntryTypeId", -1);

		if ((_fileEntryTypeId == -1) && (_fileEntry != null) &&
			(_fileEntry.getModel() instanceof DLFileEntry)) {

			DLFileEntry dlFileEntry = (DLFileEntry)_fileEntry.getModel();

			_fileEntryTypeId = dlFileEntry.getFileEntryTypeId();
		}

		_folderId = BeanParamUtil.getLong(_fileEntry, _request, "folderId");

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_portletId = portletDisplay.getId();

		if (_portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			_portletId = ParamUtil.getString(_request, "portletResource");
		}
	}

	@Override
	public List<MenuAction> getMenuActions() throws PortalException {
		List<MenuAction> menuActions = new ArrayList<MenuAction>();

		_addDownloadMenuAction(menuActions);
		_addOpenDocumentMenuAction(menuActions);
		_addEditMenuAction(menuActions);

		return menuActions;
	}

	private void _addEditMenuAction(List<MenuAction> menuActions)
		throws PortalException {

		DLPortletInstanceSettings dlPortletInstanceSettings =
			DLPortletInstanceSettings.getInstance(
				_themeDisplay.getLayout(), _portletId);

		DLActionsDisplayContext dlActionsDisplayContext =
			DLActionsDisplayContextUtil.getDLActionsDisplayContext(
				_pageContext, dlPortletInstanceSettings);

		if (dlActionsDisplayContext.isShowActions()) {
			if (isEditButtonVisible()) {
				LiferayPortletResponse liferayPortletResponse =
					_getLiferayPortletResponse();

				PortletURL editURL = liferayPortletResponse.createRenderURL();

				String currentURL = _getCurrentURL();

				editURL.setParameter(
					"struts_action", "/document_library/edit_file_entry");
				editURL.setParameter("redirect", currentURL);
				editURL.setParameter("backURL", currentURL);
				editURL.setParameter(
					"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

				menuActions.add(
					new URLMenuAction(
						DLMenuActions.MENU_ACTION_ID_EDIT, "icon-edit", "edit",
						editURL.toString()));
			}
		}
	}

	private String _getCurrentURL() {
		LiferayPortletRequest liferayPortletRequest =
			_getLiferayPortletRequest();

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		return portletURL.toString();
	}

	private LiferayPortletRequest _getLiferayPortletRequest() {
		PortletRequest portletRequest = (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		return PortalUtil.getLiferayPortletRequest(portletRequest);
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse =
				(PortletResponse)_request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private void _addOpenDocumentMenuAction(List<MenuAction> menuActions)
		throws PortalException {

		if (isOpenInMsOfficeButtonVisible()) {
			String webDavURL = DLUtil.getWebDavURL(
				_themeDisplay, _fileEntry.getFolder(), _fileEntry,
				PropsValues.
					DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED,
				true);

			LiferayPortletResponse liferayPortletResponse =
				_getLiferayPortletResponse();

			String onClick =
				liferayPortletResponse.getNamespace() + "openDocument('" +
					webDavURL + "');";

			menuActions.add(
				new JavascriptMenuAction(
					DLMenuActions.MENU_ACTION_ID_OPEN_DOCUMENT, "icon-file-alt",
					"open-in-ms-office", onClick,
					new _OpenDocumentJavascriptMenuActionRenderer()));
		}
	}

	private void _addDownloadMenuAction(List<MenuAction> menuActions)
		throws PortalException {

			if (isDownloadButtonVisible()) {
				String message =
					LanguageUtil.get(_request, "download") + " (" +
					TextFormatter.formatStorageSize(
						_fileEntry.getSize(), _themeDisplay.getLocale()) + ")";

				String url = DLUtil.getDownloadURL(
					_fileEntry, _fileVersion, _themeDisplay, StringPool.BLANK,
					false, true);

				menuActions.add(
					new URLMenuAction(
						DLMenuActions.MENU_ACTION_ID_DOWNLOAD, "icon-download",
						message, url, "_blank"));
			}
		}

	@Override
	public String getPublishButtonLabel() {
		String publishButtonLabel = "publish";

		if (_hasWorkflowDefinitionLink()) {
			publishButtonLabel = "submit-for-publication";
		}

		if (_isFileEntrySaveAsDraft()) {
			publishButtonLabel = "save";
		}

		return publishButtonLabel;
	}

	@Override
	public String getSaveButtonLabel() {
		String saveButtonLabel = "save";

		FileVersion fileVersion =
			_dlFileEntryActionsDisplayContextHelper.getFileVersion();

		if ((fileVersion == null) ||
			_dlFileEntryActionsDisplayContextHelper.isApproved() ||
			_dlFileEntryActionsDisplayContextHelper.isDraft()) {

			saveButtonLabel = "save-as-draft";
		}

		return saveButtonLabel;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isAssetMetadataVisible() {
		if (_portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			_portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN) ||
			_portletId.equals(PortletKeys.MEDIA_GALLERY_DISPLAY) ||
			_portletId.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ||
			_portletId.equals(PortletKeys.MY_WORKFLOW_INSTANCES) ||
			_portletId.equals(PortletKeys.MY_WORKFLOW_TASKS) ||
			_portletId.equals(PortletKeys.TRASH)) {

			return true;
		}

		return ParamUtil.getBoolean(_request, "showAssetMetadata");
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException {

		if (isCheckinButtonVisible() ||
			(_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			 _dlFileEntryActionsDisplayContextHelper.
				 hasOverrideCheckoutPermission())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isCheckinButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCheckinButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasUpdatePermission() &&
			_dlFileEntryActionsDisplayContextHelper.isLockedByMe() &&
			_dlFileEntryActionsDisplayContextHelper.isSupportsLocking()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasUpdatePermission() &&
			!_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			_dlFileEntryActionsDisplayContextHelper.isSupportsLocking()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isCheckoutDocumentDisabled() {
		return false;
	}

	@Override
	public boolean isDeleteButtonVisible() throws PortalException {
		if (_isFileEntryDeletable() && !_isFileEntryTrashable()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDownloadButtonVisible() throws PortalException {
		return _dlFileEntryActionsDisplayContextHelper.hasViewPermission();
	}

	@Override
	public boolean isEditButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasUpdatePermission() &&
			!_isFileEntryCheckedOutByOther()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMoveButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasUpdatePermission() &&
			!_isFileEntryCheckedOutByOther()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMoveToTheRecycleBinButtonVisible() throws PortalException {
		if (!isDeleteButtonVisible() && _isFileEntryDeletable()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isOpenInMsOfficeButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasViewPermission() &&
			_dlFileEntryActionsDisplayContextHelper.isOfficeDoc() &&
			_isWebDAVEnabled() && _isIEOnWin32()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPermissionsButtonVisible() throws PortalException {
		return
			_dlFileEntryActionsDisplayContextHelper.hasPermissionsPermission();
	}

	@Override
	public boolean isPublishButtonDisabled() {
		if ((_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			 !_dlFileEntryActionsDisplayContextHelper.isLockedByMe()) ||
			(_dlFileEntryActionsDisplayContextHelper.isPending() &&
			 _isDLFileEntryDraftsEnabled())) {

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
		if (_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			!_dlFileEntryActionsDisplayContextHelper.isLockedByMe()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isSaveButtonVisible() {
		return _isDLFileEntryDraftsEnabled();
	}

	@Override
	public boolean isViewButtonVisible() throws PortalException {
		return _dlFileEntryActionsDisplayContextHelper.hasViewPermission();
	}

	@Override
	public boolean isViewOriginalFileButtonVisible() throws PortalException {
		return _dlFileEntryActionsDisplayContextHelper.hasViewPermission();
	}

	private boolean _hasWorkflowDefinitionLink() {
		try {
			return DLUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
				_folderId, _fileEntryTypeId);
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
		if (_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			!_dlFileEntryActionsDisplayContextHelper.isLockedByMe()) {

			return true;
		}

		return false;
	}

	private boolean _isFileEntryDeletable() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasDeletePermission() &&
			!_isFileEntryCheckedOutByOther()) {

			return true;
		}

		return false;
	}

	private boolean _isFileEntrySaveAsDraft() {
		if ((_dlFileEntryActionsDisplayContextHelper.isCheckedOut() ||
			 _dlFileEntryActionsDisplayContextHelper.isPending()) &&
			!_isDLFileEntryDraftsEnabled()) {

			return true;
		}

		return false;
	}

	private boolean _isFileEntryTrashable() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.isDLFileEntry() &&
			_isTrashEnabled()) {

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

	private boolean _isTrashEnabled() throws PortalException {
		if (_trashEnabled == null) {
			_trashEnabled = TrashUtil.isTrashEnabled(
				_themeDisplay.getScopeGroupId());
		}

		return _trashEnabled;
	}

	private boolean _isWebDAVEnabled() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.isWebDAVEnabled();
	}

	private static final UUID _UUID = UUID.fromString(
		"85F6C50E-3893-4E32-9D63-208528A503FA");

	private class _OpenDocumentJavascriptMenuActionRenderer
		implements JavascriptMenuAction.Renderer {

		@Override
		public void render(PageContext pageContext)
			throws IOException, ServletException {

			pageContext.include(
				"/html/portlet/document_library/display_context/" +
				"open_document_js.jsp");
		}

	}

	private DLFileEntryActionsDisplayContextHelper
		_dlFileEntryActionsDisplayContextHelper;
	private FileEntry _fileEntry;
	private long _fileEntryTypeId;
	private FileVersion _fileVersion;
	private long _folderId;
	private Boolean _ieOnWin32;
	private PageContext _pageContext;
	private String _portletId;
	private HttpServletRequest _request;
	private ThemeDisplay _themeDisplay;
	private Boolean _trashEnabled;

}