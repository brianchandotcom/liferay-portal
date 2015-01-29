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

package com.liferay.portlet.documentlibrary.display.context.logic;

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.DeleteMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavascriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavascriptToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavascriptUIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLUIItem;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.documentlibrary.display.context.DLUIItemKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iv�n Zaera
 */
public class UIItemsBuilder {

	public UIItemsBuilder(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut)
		throws PortalException {

		this(
			request, response, dlFileShortcut.getFileVersion(), dlFileShortcut);
	}

	public UIItemsBuilder(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		this(request, response, fileVersion, null);
	}

	public void addCancelCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable()) {

			return;
		}

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-remove",
			DLUIItemKeys.CANCEL_CHECKOUT, "cancel-checkout[document]",
			_getActionURL(
				"/document_library/edit_file_entry",
				Constants.CANCEL_CHECKOUT));
	}

	public void addCancelCheckoutToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable()) {

			return;
		}

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-undo",
			DLUIItemKeys.CANCEL_CHECKOUT,
			UnicodeLanguageUtil.get(_request, "cancel-checkout[document]"),
			getSubmitFormJavascript(Constants.CANCEL_CHECKOUT, null));
	}

	public void addCheckinMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isCheckinActionAvailable()) {
			return;
		}

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-lock", DLUIItemKeys.CHECKIN,
			"checkin",
			_getActionURL(
				"/document_library/edit_file_entry", Constants.CHECKIN));
	}

	public void addCheckinToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isCheckinActionAvailable()) {
			return;
		}

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-lock",
			DLUIItemKeys.CHECKIN, UnicodeLanguageUtil.get(_request, "checkin"),
			getSubmitFormJavascript(Constants.CHECKIN, null));
	}

	public void addCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCheckoutDocumentActionAvailable()) {

			return;
		}

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-unlock", DLUIItemKeys.CHECKOUT,
			"checkout[document]",
			_getActionURL(
				"/document_library/edit_file_entry", Constants.CHECKOUT));
	}

	public void addCheckoutToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCheckoutDocumentActionAvailable()) {

			return;
		}

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-unlock",
			DLUIItemKeys.CHECKOUT,
			UnicodeLanguageUtil.get(_request, "checkout[document]"),
			getSubmitFormJavascript(Constants.CHECKOUT, null));
	}

	public void addDeleteMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (isDeleteActionAvailable()) {
			DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

			deleteMenuItem.setKey(DLUIItemKeys.DELETE);
			deleteMenuItem.setURL(
				_getActionURL(
					"/document_library/edit_file_entry", Constants.DELETE));

			menuItems.add(deleteMenuItem);
		}
		else if (isMoveToTheRecycleBinActionAvailable()) {
			DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

			deleteMenuItem.setKey(DLUIItemKeys.DELETE);
			deleteMenuItem.setTrash(true);
			deleteMenuItem.setURL(
				_getActionURL(
					"/document_library/edit_file_entry",
					Constants.MOVE_TO_TRASH));

			menuItems.add(deleteMenuItem);
		}
	}

	public void addDeleteToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isDeleteActionAvailable()) {
			return;
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/document_library/view");
		portletURL.setParameter(
			"folderId", String.valueOf(_fileEntry.getFolderId()));

		StringBundler sb = new StringBundler(5);

		sb.append("if (confirm('");
		sb.append(
			UnicodeLanguageUtil.get(
				_request, "are-you-sure-you-want-to-delete-this"));
		sb.append("')) {");
		sb.append(
			getSubmitFormJavascript(Constants.DELETE, portletURL.toString()));
		sb.append("}");

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-remove",
			DLUIItemKeys.DELETE, UnicodeLanguageUtil.get(_request, "delete"),
			sb.toString());
	}

	public void addDownloadMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isDownloadActionAvailable()) {
			return;
		}

		String label = TextFormatter.formatStorageSize(
			_fileEntry.getSize(), _themeDisplay.getLocale());

		label = _themeDisplay.translate("download") + " (" + label + ")";

		String url = DLUtil.getDownloadURL(
			_fileEntry, _fileVersion, _themeDisplay, StringPool.BLANK, false,
			true);

		URLMenuItem urlMenuItem = _addURLUIItem(
			new URLMenuItem(), menuItems, "icon-download",
			DLUIItemKeys.DOWNLOAD, label, url);

		urlMenuItem.setMethod("get");
		urlMenuItem.setTarget("_blank");
	}

	public void addDownloadToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isDownloadActionAvailable()) {
			return;
		}

		_addURLUIItem(
			new URLToolbarItem(), toolbarItems, "icon-download",
			DLUIItemKeys.DOWNLOAD,
			UnicodeLanguageUtil.get(_request, "download"),
			DLUtil.getDownloadURL(
				_fileEntry, _fileVersion, _themeDisplay, StringPool.BLANK));
	}

	public void addEditMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isEditActionAvailable()) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/edit_file_entry");

		portletURL.setParameter("backURL", _getCurrentURL());

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-edit", DLUIItemKeys.EDIT,
			"edit", portletURL.toString());
	}

	public void addEditToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isEditActionAvailable()) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/edit_file_entry");

		_addURLUIItem(
			new URLToolbarItem(), toolbarItems, "icon-edit", DLUIItemKeys.EDIT,
			UnicodeLanguageUtil.get(_request, "edit"), portletURL.toString());
	}

	public void addMoveMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isMoveActionAvailable()) {
			return;
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/document_library/move_entry");

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectURL.setParameter("struts_action", "/document_library/view");
		redirectURL.setParameter("folderId", String.valueOf(_folderId));

		portletURL.setParameter("redirect", redirectURL.toString());

		portletURL.setParameter(
			"fileEntryIds", String.valueOf(_fileEntry.getFileEntryId()));

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-move", DLUIItemKeys.MOVE,
			"move", portletURL.toString());
	}

	public void addMoveToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isMoveActionAvailable()) {
			return;
		}

		PortletURL portletURL = _getRenderURL("/document_library/move_entry");

		_addURLUIItem(
			new URLToolbarItem(), toolbarItems, "icon-move", DLUIItemKeys.MOVE,
			UnicodeLanguageUtil.get(_request, "move"), portletURL.toString());
	}

	public void addMoveToTheRecycleBinToolbarItem(
			List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isMoveToTheRecycleBinActionAvailable()) {
			return;
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/document_library/view");
		portletURL.setParameter(
			"folderId", String.valueOf(_fileEntry.getFolderId()));

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-trash",
			DLUIItemKeys.MOVE_TO_THE_RECYCLE_BIN,
			UnicodeLanguageUtil.get(_request, "move-to-the-recycle-bin"),
			getSubmitFormJavascript(
				Constants.MOVE_TO_TRASH, portletURL.toString()));
	}

	public void addOpenInMsOfficeMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!isOpenInMsOfficeActionAvailable()) {
			return;
		}

		String webDavURL = DLUtil.getWebDavURL(
			_themeDisplay, _fileEntry.getFolder(), _fileEntry,
			PropsValues.
				DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED,
			true);

		String onClick = getNamespace() + "openDocument('" + webDavURL + "');";

		JavascriptMenuItem javascriptMenuItem = _addJavascriptUIItem(
			new JavascriptMenuItem(), menuItems, "icon-file-alt",
			DLUIItemKeys.OPEN_IN_MS_OFFICE, "open-in-ms-office", onClick);

		String javaScript =
			"/com/liferay/portlet/documentlibrary/context/dependencies" +
				"/open_in_ms_office_js.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put(
			"errorMessage", UnicodeLanguageUtil.get(
				_request,
				"cannot-open-the-requested-document-due-to-the-following-" +
					"reason"));
		template.put("namespace", getNamespace());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		javascriptMenuItem.setJavascript(unsyncStringWriter.toString());
	}

	public void addOpenInMsOfficeToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isOpenInMsOfficeActionAvailable()) {
			return;
		}

		String webDavURL = DLUtil.getWebDavURL(
			_themeDisplay, _fileEntry.getFolder(), _fileEntry,
			PropsValues.
				DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED);

		StringBundler sb = new StringBundler(4);

		sb.append(getNamespace());
		sb.append("openDocument('");
		sb.append(webDavURL);
		sb.append("');");

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-file-alt",
			DLUIItemKeys.OPEN_IN_MS_OFFICE,
			UnicodeLanguageUtil.get(_request, "open-in-ms-office"),
			sb.toString());
	}

	public void addPermissionsMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isPermissionsButtonVisible()) {
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

		URLMenuItem urlMenuItem = _addURLUIItem(
			new URLMenuItem(), menuItems, "icon-lock", DLUIItemKeys.PERMISSIONS,
			"permissions", url);

		urlMenuItem.setMethod("get");
		urlMenuItem.setUseDialog(true);
	}

	public void addPermissionsToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isPermissionsButtonVisible()) {
			return;
		}

		String permissionsURL = null;

		try {
			permissionsURL = PermissionsURLTag.doTag(
				null, DLFileEntryConstants.getClassName(),
				HtmlUtil.unescape(_fileEntry.getTitle()), null,
				String.valueOf(_fileEntry.getFileEntryId()),
				LiferayWindowState.POP_UP.toString(), null, _request
			);
		}
		catch (Exception e) {
			throw new SystemException("Unable to create permissions URL", e);
		}

		StringBundler sb = new StringBundler(6);

		sb.append("Liferay.Util.openWindow({");
		sb.append("title: '");
		sb.append(UnicodeLanguageUtil.get(_request, "permissions"));
		sb.append("', uri: '");
		sb.append(permissionsURL);
		sb.append("'});");

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-lock",
			DLUIItemKeys.PERMISSIONS,
			UnicodeLanguageUtil.get(_request, "permissions"), sb.toString());
	}

	public void addViewOriginalFileMenuItem(List<MenuItem> menuItems) {
		if (_dlFileShortcut == null) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/view_file_entry");

		portletURL.setParameter("redirect", _getCurrentURL());
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_dlFileShortcut.getToFileEntryId()));

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-search",
			DLUIItemKeys.VIEW_ORIGINAL_FILE, "view-original-file",
			portletURL.toString());
	}

	protected String getNamespace() {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		return liferayPortletResponse.getNamespace();
	}

	protected String getSubmitFormJavascript(String cmd, String redirect) {
		StringBundler sb = new StringBundler(18);

		sb.append("document.");
		sb.append(getNamespace());
		sb.append("fm.");
		sb.append(getNamespace());
		sb.append(Constants.CMD);
		sb.append(".value = '");
		sb.append(cmd);
		sb.append("';");

		if (redirect != null) {
			sb.append("document.");
			sb.append(getNamespace());
			sb.append("fm.");
			sb.append(getNamespace());
			sb.append("redirect.value = '");
			sb.append(redirect);
			sb.append("';");
		}

		sb.append("submitForm(document.");
		sb.append(getNamespace());
		sb.append("fm);");

		return sb.toString();
	}

	protected boolean isDeleteActionAvailable() throws PortalException {
		if (_fileEntryDisplayContextHelper.isFileEntryDeletable() &&
			!_isFileEntryTrashable()) {

			return true;
		}

		return false;
	}

	protected boolean isMoveToTheRecycleBinActionAvailable()
		throws PortalException {

		if (!isDeleteActionAvailable() &&
			_fileEntryDisplayContextHelper.isFileEntryDeletable()) {

			return true;
		}

		return false;
	}

	protected boolean isOpenInMsOfficeActionAvailable() throws PortalException {
		if (_fileEntryDisplayContextHelper.hasViewPermission() &&
			_fileVersionDisplayContextHelper.isMsOffice() &&
			_isWebDAVEnabled() && _isIEOnWin32()) {

			return true;
		}

		return false;
	}

	private UIItemsBuilder(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, DLFileShortcut dlFileShortcut) {

		try {
			_request = request;
			_fileVersion = fileVersion;
			_dlFileShortcut = dlFileShortcut;

			FileEntry fileEntry = null;

			if (fileVersion != null) {
				fileEntry = fileVersion.getFileEntry();
			}

			_fileEntry = fileEntry;

			_folderId = BeanParamUtil.getLong(_fileEntry, request, "folderId");

			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				_themeDisplay.getPermissionChecker(), _fileEntry);
			_fileVersionDisplayContextHelper =
				new FileVersionDisplayContextHelper(fileVersion);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to build UIItemsBuilder for " + fileVersion, pe);
		}
	}

	private <T extends JavascriptUIItem> T _addJavascriptUIItem(
		T javascriptUIItem, List<? super T> javascriptUIItems, String icon,
		String key, String label, String onClick ) {

		javascriptUIItem.setIcon(icon);
		javascriptUIItem.setKey(key);
		javascriptUIItem.setLabel(label);
		javascriptUIItem.setOnClick(onClick);

		javascriptUIItems.add(javascriptUIItem);

		return javascriptUIItem;
	}

	private <T extends URLUIItem> T _addURLUIItem(
		T urlUIItem, List<? super T> urlUIItems, String icon, String key,
		String label, String url ) {

		urlUIItem.setIcon(icon);
		urlUIItem.setKey(key);
		urlUIItem.setLabel(label);
		urlUIItem.setURL(url);

		urlUIItems.add(urlUIItem);

		return urlUIItem;
	}

	private String _getActionURL(String strutsAction, String cmd) {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("struts_action", strutsAction);
		portletURL.setParameter(Constants.CMD, cmd);
		portletURL.setParameter("redirect", _getCurrentURL());
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	private String _getCurrentURL() {
		if (_currentURL != null) {
			return _currentURL;
		}

		LiferayPortletRequest liferayPortletRequest =
			_getLiferayPortletRequest();

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_currentURL = portletURL.toString();

		return _currentURL;
	}

	private LiferayPortletRequest _getLiferayPortletRequest() {
		PortletRequest portletRequest = (PortletRequest) _request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		return PortalUtil.getLiferayPortletRequest(portletRequest);
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse =
			(PortletResponse) _request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private PortletURL _getRenderURL(String strutsAction) {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", strutsAction);
		portletURL.setParameter("redirect", _getCurrentURL());
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		return portletURL;
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

	private String _currentURL;
	private DLFileShortcut _dlFileShortcut;
	private final FileEntry _fileEntry;
	private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private final FileVersion _fileVersion;
	private final FileVersionDisplayContextHelper
		_fileVersionDisplayContextHelper;
	private final long _folderId;
	private Boolean _ieOnWin32;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;
	private Boolean _trashEnabled;

}