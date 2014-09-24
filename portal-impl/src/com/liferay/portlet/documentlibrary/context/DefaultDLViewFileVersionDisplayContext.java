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

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.DeleteMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavascriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
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
import com.liferay.portlet.documentlibrary.context.helper.FileEntryDisplayContextHelper;
import com.liferay.portlet.documentlibrary.context.helper.FileVersionDisplayContextHelper;
import com.liferay.portlet.documentlibrary.context.util.JSPRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultDLViewFileVersionDisplayContext
	implements DLViewFileVersionDisplayContext {

	public DefaultDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion)
		throws PortalException {

		_request = request;
		_fileVersion = fileVersion;

		if (fileVersion != null) {
			_fileEntry = fileVersion.getFileEntry();
		}

		_folderId = BeanParamUtil.getLong(_fileEntry, request, "folderId");

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
			_themeDisplay.getPermissionChecker(), _fileEntry);
		_fileVersionDisplayContextHelper = new FileVersionDisplayContextHelper(
			fileVersion);
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		if (_fileVersionDisplayContextHelper.isDLFileVersion()) {
			DLFileVersion dlFileVersion =
				(DLFileVersion)_fileVersion.getModel();

			return dlFileVersion.getDDMStructures();
		}

		return Collections.emptyList();
	}

	@Override
	public Fields getFields(DDMStructure ddmStructure) throws PortalException {
		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
				ddmStructure.getStructureId(), _fileVersion.getFileVersionId());

		return StorageEngineUtil.getFields(
			dlFileEntryMetadata.getDDMStorageId());
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<MenuItem>();

		if (_isShowActions()) {
			_addDownloadMenuItem(menuItems);

			_addOpenInMsOfficeMenuItem(menuItems);

			_addEditMenuItem(menuItems);

			_addMoveMenuItem(menuItems);

			_addCheckoutMenuItem(menuItems);

			_addCheckinMenuItem(menuItems);

			_addCancelCheckoutMenuItem(menuItems);

			_addPermissionsMenuItem(menuItems);

			_addDeleteMenuItem(menuItems);
		}

		return menuItems;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isAssetMetadataVisible() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(PortletKeys.ASSET_PUBLISHER) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN) ||
			portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ||
			portletName.equals(PortletKeys.MY_WORKFLOW_INSTANCES) ||
			portletName.equals(PortletKeys.MY_WORKFLOW_TASKS) ||
			portletName.equals(PortletKeys.TRASH)) {

			return true;
		}

		return ParamUtil.getBoolean(_request, "showAssetMetadata");
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException {

		return _fileEntryDisplayContextHelper.
			isCancelCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isCheckinButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isCheckinButtonVisible();
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isDeleteButtonVisible() throws PortalException {
		if (_fileEntryDisplayContextHelper.isFileEntryDeletable() &&
			!_isFileEntryTrashable()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isDownloadButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.hasViewPermission();
	}

	@Override
	public boolean isEditButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isUpdatable();
	}

	@Override
	public boolean isMoveButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isUpdatable();
	}

	@Override
	public boolean isMoveToTheRecycleBinButtonVisible() throws PortalException {
		if (!isDeleteButtonVisible() &&
			_fileEntryDisplayContextHelper.isFileEntryDeletable()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isOpenInMsOfficeButtonVisible() throws PortalException {
		if (_fileEntryDisplayContextHelper.hasViewPermission() &&
			_fileVersionDisplayContextHelper.isOfficeDoc() &&
			_isWebDAVEnabled() && _isIEOnWin32()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPermissionsButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.hasPermissionsPermission();
	}

	@Override
	public boolean isViewButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.hasViewPermission();
	}

	@Override
	public boolean isViewOriginalFileButtonVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.hasViewPermission();
	}

	@Override
	public void renderPreview(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		JSPRenderer jspRenderer = new JSPRenderer(
			"/html/portlet/document_library/view_file_entry_preview.jsp");

		jspRenderer.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, _fileVersion);

		jspRenderer.render(request, response);
	}

	protected String getActionURL(String strutsAction, String cmd) {
		LiferayPortletResponse liferayPortletResponse =
			getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("struts_action", strutsAction);
		portletURL.setParameter(Constants.CMD, cmd);
		portletURL.setParameter("redirect", _getCurrentURL());
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	protected LiferayPortletRequest getLiferayPortletRequest() {
		PortletRequest portletRequest =
			(PortletRequest)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		return PortalUtil.getLiferayPortletRequest(portletRequest);
	}

	protected LiferayPortletResponse getLiferayPortletResponse() {
		PortletResponse portletResponse =
			(PortletResponse)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	protected PortletURL getRenderURL(String strutsAction) {
		LiferayPortletResponse liferayPortletResponse =
			getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/document_library/edit_file_entry");
		portletURL.setParameter("redirect", _getCurrentURL());
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		return portletURL;
	}

	private void _addCancelCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentButtonVisible()) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIconCssClass("icon-remove");
		urlMenuItem.setKey(DLMenuItemKeys.CANCEL_CHECKOUT);
		urlMenuItem.setMessage("cancel-checkout[document]");
		urlMenuItem.setURL(
			getActionURL(
				"/document_library/edit_file_entry",
				Constants.CANCEL_CHECKOUT));

		menuItems.add(urlMenuItem);
	}

	private void _addCheckinMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isCheckinButtonVisible()) {
			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIconCssClass("icon-lock");
		urlMenuItem.setKey(DLMenuItemKeys.CHECKIN);
		urlMenuItem.setMessage("checkin");
		urlMenuItem.setURL(
			getActionURL(
				"/document_library/edit_file_entry", Constants.CHECKIN));

		menuItems.add(urlMenuItem);
	}

	private void _addCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isCheckoutDocumentButtonVisible()) {
			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIconCssClass("icon-unlock");
		urlMenuItem.setKey(DLMenuItemKeys.CHECKOUT);
		urlMenuItem.setMessage("checkout[document]");
		urlMenuItem.setURL(
			getActionURL(
				"/document_library/edit_file_entry", Constants.CHECKOUT));

		menuItems.add(urlMenuItem);
	}

	private void _addDeleteMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (isDeleteButtonVisible()) {
			DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

			deleteMenuItem.setKey(DLMenuItemKeys.DELETE);
			deleteMenuItem.setURL(
				getActionURL(
					"/document_library/edit_file_entry", Constants.DELETE));

			menuItems.add(deleteMenuItem);
		}
		else if (isMoveToTheRecycleBinButtonVisible()) {
			DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

			deleteMenuItem.setKey(DLMenuItemKeys.DELETE);
			deleteMenuItem.setTrash(true);
			deleteMenuItem.setURL(
				getActionURL(
					"/document_library/edit_file_entry",
					Constants.MOVE_TO_TRASH));

			menuItems.add(deleteMenuItem);
		}
	}

	private void _addDownloadMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!isDownloadButtonVisible()) {
			return;
		}

		String message = TextFormatter.formatStorageSize(
			_fileEntry.getSize(), _themeDisplay.getLocale());

		message = _themeDisplay.translate("download") + " (" + message + ")";

		String url = DLUtil.getDownloadURL(
			_fileEntry, _fileVersion, _themeDisplay, StringPool.BLANK, false,
			true);

		URLMenuItem urlMenuItem = addURLMenuItem(
			menuItems, "icon-download", DLMenuItemKeys.DOWNLOAD, message, url);

		urlMenuItem.setTarget("_blank");
	}

	private void _addEditMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!isEditButtonVisible()) {
			return;
		}

		PortletURL portletURL = getRenderURL(
			"/document_library/edit_file_entry");

		portletURL.setParameter("backURL", _getCurrentURL());

		addURLMenuItem(
			menuItems, "icon-edit", DLMenuItemKeys.EDIT, "edit",
			portletURL.toString());
	}

	private void _addMoveMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!isMoveButtonVisible()) {
			return;
		}

		LiferayPortletResponse liferayPortletResponse =
			getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/document_library/move_entry");

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectURL.setParameter("struts_action", "/document_library/view");
		redirectURL.setParameter("folderId", String.valueOf(_folderId));

		portletURL.setParameter("redirect", redirectURL.toString());

		portletURL.setParameter(
			"fileEntryIds", String.valueOf(_fileEntry.getFileEntryId()));

		addURLMenuItem(
			menuItems, "icon-move", DLMenuItemKeys.MOVE, "move",
			portletURL.toString());
	}

	private void _addOpenInMsOfficeMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!isOpenInMsOfficeButtonVisible()) {
			return;
		}

		JavascriptMenuItem javaScriptMenuItem = new JavascriptMenuItem();

		javaScriptMenuItem.setIconCssClass("icon-file-alt");
		javaScriptMenuItem.setKey(DLMenuItemKeys.OPEN_IN_MS_OFFICE);

		Map<String, String> context = new HashMap<String, String>();

		LiferayPortletResponse liferayPortletResponse =
			getLiferayPortletResponse();

		context.put(
			"errorMessage", UnicodeLanguageUtil.get(
				_request,
				"cannot-open-the-requested-document-due-to-the-following-" +
					"reason"));
		context.put("namespace", liferayPortletResponse.getNamespace());

		String javaScript = _processFreeMarkerTemplate(
			"/com/liferay/portlet/documentlibrary/context/dependencies" +
				"open_in_ms_office_js.ftl",
			context);

		javaScriptMenuItem.setJavascript(javaScript);

		javaScriptMenuItem.setMessage("open-in-ms-office");

		String webDavURL = DLUtil.getWebDavURL(
			_themeDisplay, _fileEntry.getFolder(), _fileEntry,
			PropsValues.
				DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED,
			true);

		String onClick =
			liferayPortletResponse.getNamespace() + "openDocument('" +
				webDavURL + "');";

		javaScriptMenuItem.setOnClick(onClick);

		menuItems.add(javaScriptMenuItem);
	}

	private void _addPermissionsMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!isPermissionsButtonVisible()) {
			return;
		}

		String url = null;

		try {
			url = PermissionsURLTag.doTag(
				null, DLFileEntryConstants.getClassName(),
				HtmlUtil.unescape(_fileEntry.getTitle()), null,
				String.valueOf(_fileEntry.getFileEntryId()),
				LiferayWindowState.POP_UP.toString(), null, _request);
		}
		catch (Exception e) {
			throw new SystemException("Unable to create permissions URL", e);
		}

		URLMenuItem urlMenuItem = addURLMenuItem(
			menuItems, "icon-lock", DLMenuItemKeys.PERMISSIONS, "permissions",
			url);

		urlMenuItem.setMethod("get");
		urlMenuItem.setUseDialog(true);
	}

	private String _getCurrentURL() {
		if (_currentURL != null) {
			return _currentURL;
		}

		LiferayPortletRequest liferayPortletRequest =
			getLiferayPortletRequest();

		LiferayPortletResponse liferayPortletResponse =
			getLiferayPortletResponse();

		PortletURL portletURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_currentURL = portletURL.toString();

		return _currentURL;
	}

	private DLActionsDisplayContext _getDLActionsDisplayContext()
		throws PortalException {

		if (_dlActionsDisplayContext != null) {
			return _dlActionsDisplayContext;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			portletId = ParamUtil.getString(_request, "portletResource");
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			DLPortletInstanceSettings.getInstance(
				_themeDisplay.getLayout(), portletId);

		_dlActionsDisplayContext = new DLActionsDisplayContext(
			_request, dlPortletInstanceSettings);

		return _dlActionsDisplayContext;
	}

	private boolean _isFileEntryTrashable() throws PortalException {
		if (_fileEntryDisplayContextHelper.isDLFileEntry() &&
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

	private boolean _isShowActions() throws PortalException {
		DLActionsDisplayContext dlActionsDisplayContext =
			_getDLActionsDisplayContext();

		return dlActionsDisplayContext.isShowActions();
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

	private String _processFreeMarkerTemplate(
		String name, Map<String, String> context) {

		try {
			return FreeMarkerUtil.process(name, context);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to process Freemarker template", e);
		}
	}

	private URLMenuItem addURLMenuItem(
		List<MenuItem> menuItems, String iconCssClass, String key,
		String message, String url) {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIconCssClass(iconCssClass);
		urlMenuItem.setKey(key);
		urlMenuItem.setMessage(message);
		urlMenuItem.setURL(url);

		menuItems.add(urlMenuItem);

		return urlMenuItem;
	}

	private static final UUID _UUID = UUID.fromString(
		"85F6C50E-3893-4E32-9D63-208528A503FA");

	private String _currentURL;
	private DLActionsDisplayContext _dlActionsDisplayContext;
	private FileEntry _fileEntry;
	private FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private FileVersion _fileVersion;
	private FileVersionDisplayContextHelper _fileVersionDisplayContextHelper;
	private long _folderId;
	private Boolean _ieOnWin32;
	private HttpServletRequest _request;
	private ThemeDisplay _themeDisplay;
	private Boolean _trashEnabled;

}