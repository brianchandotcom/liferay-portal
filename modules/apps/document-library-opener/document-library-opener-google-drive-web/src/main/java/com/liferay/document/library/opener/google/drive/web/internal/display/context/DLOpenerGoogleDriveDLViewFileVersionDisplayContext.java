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

package com.liferay.document.library.opener.google.drive.web.internal.display.context;

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.opener.constants.DLOpenerFileEntryReferenceConstants;
import com.liferay.document.library.opener.google.drive.constants.DLOpenerGoogleDriveMimeTypes;
import com.liferay.document.library.opener.google.drive.web.internal.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveConstants;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseUIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptUIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DLOpenerGoogleDriveDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public DLOpenerGoogleDriveDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion,
		ResourceBundle resourceBundle,
		ModelResourcePermission<FileEntry> fileEntryModelResourcePermission,
		DLOpenerFileEntryReferenceLocalService
			dlOpenerFileEntryReferenceLocalService,
		DLOpenerGoogleDriveManager dlOpenerGoogleDriveManager, Portal portal) {

		super(
			_UUID, parentDLDisplayContext, httpServletRequest,
			httpServletResponse, fileVersion);

		_resourceBundle = resourceBundle;
		_fileEntryModelResourcePermission = fileEntryModelResourcePermission;
		_dlOpenerFileEntryReferenceLocalService =
			dlOpenerFileEntryReferenceLocalService;
		_dlOpenerGoogleDriveManager = dlOpenerGoogleDriveManager;
		_portal = portal;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();
	}

	@Override
	public Menu getMenu() throws PortalException {
		if (!isActionsVisible() ||
			!DLOpenerGoogleDriveMimeTypes.isGoogleMimeTypeSupported(
				fileVersion.getMimeType()) ||
			!_dlOpenerGoogleDriveManager.isConfigured(
				fileVersion.getCompanyId()) ||
			!_fileEntryModelResourcePermission.contains(
				_permissionChecker, fileVersion.getFileEntry(),
				ActionKeys.UPDATE)) {

			return super.getMenu();
		}

		Menu menu = super.getMenu();

		FileEntry fileEntry = fileVersion.getFileEntry();

		if (_isCheckedOutInGoogleDrive()) {
			if (fileEntry.hasLock()) {
				List<MenuItem> menuItems = menu.getMenuItems();

				_updateCancelCheckoutAndCheckinMenuItems(menuItems);

				_addEditInGoogleDocsUIItem(
					menuItems, _createEditInGoogleDocsMenuItem(Constants.EDIT));
			}

			return menu;
		}

		if (!_isCheckedOutByAnotherUser(fileEntry)) {
			_addEditInGoogleDocsUIItem(
				menu.getMenuItems(),
				_createEditInGoogleDocsMenuItem(Constants.CHECKOUT));
		}

		return menu;
	}

	/**
	 * @see com.liferay.frontend.image.editor.integration.document.library.internal.display.context.ImageEditorDLViewFileVersionDisplayContext#_addEditWithImageEditorUIItem
	 */
	private <T extends BaseUIItem> List<T> _addEditInGoogleDocsUIItem(
		List<T> uiItems, T editInGoogleDocsUIItem) {

		int i = 1;

		for (T uiItem : uiItems) {
			if (DLUIItemKeys.EDIT.equals(uiItem.getKey())) {
				break;
			}

			i++;
		}

		if (i >= uiItems.size()) {
			uiItems.add(editInGoogleDocsUIItem);
		}
		else {
			uiItems.add(i, editInGoogleDocsUIItem);
		}

		return uiItems;
	}

	private MenuItem _createEditInGoogleDocsMenuItem(String cmd)
		throws PortalException {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(LanguageUtil.get(_resourceBundle, _getLabelKey()));
		urlMenuItem.setMethod(HttpMethods.POST);
		urlMenuItem.setURL(_getActionURL(cmd));

		return urlMenuItem;
	}

	private String _getActionURL(String cmd) throws PortalException {
		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, _portal.getPortletId(request),
			PortletRequest.ACTION_PHASE);

		liferayPortletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/edit_in_google_docs");
		liferayPortletURL.setParameter(Constants.CMD, cmd);
		liferayPortletURL.setParameter(
			"fileEntryId", String.valueOf(fileVersion.getFileEntryId()));

		FileEntry fileEntry = fileVersion.getFileEntry();

		liferayPortletURL.setParameter(
			"folderId", String.valueOf(fileEntry.getFolderId()));

		liferayPortletURL.setParameter(
			"googleDocsRedirect", _portal.getCurrentURL(request));

		return liferayPortletURL.toString();
	}

	private String _getLabelKey() {
		String googleDocsMimeType =
			DLOpenerGoogleDriveMimeTypes.getGoogleDocsMimeType(
				fileVersion.getMimeType());

		if (DLOpenerGoogleDriveMimeTypes.
				APPLICATION_VND_GOOGLE_APPS_PRESENTATION.equals(
					googleDocsMimeType)) {

			return "edit-in-google-slides";
		}

		if (DLOpenerGoogleDriveMimeTypes.
				APPLICATION_VND_GOOGLE_APPS_SPREADSHEET.equals(
					googleDocsMimeType)) {

			return "edit-in-google-sheets";
		}

		return "edit-in-google-docs";
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private String _getNamespace() {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		return liferayPortletResponse.getNamespace();
	}

	private boolean _isCheckedOutByAnotherUser(FileEntry fileEntry) {
		if (fileEntry.isCheckedOut() && !fileEntry.hasLock()) {
			return true;
		}

		return false;
	}

	private boolean _isCheckedOutInGoogleDrive() throws PortalException {
		FileEntry fileEntry = fileVersion.getFileEntry();

		if (fileEntry.isCheckedOut() &&
			_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {

			return true;
		}

		return false;
	}

	private boolean _isCheckingInNewFile() throws PortalException {
		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(
					DLOpenerGoogleDriveConstants.GOOGLE_DRIVE_REFERENCE_TYPE,
					fileVersion.getFileEntry());

		if (dlOpenerFileEntryReference.getType() ==
				DLOpenerFileEntryReferenceConstants.TYPE_NEW) {

			return true;
		}

		return false;
	}

	private void _updateCancelCheckoutAndCheckinMenuItems(
			Collection<MenuItem> menuItems)
		throws PortalException {

		for (MenuItem menuItem : menuItems) {
			if (DLUIItemKeys.CHECKIN.equals(menuItem.getKey())) {
				if (menuItem instanceof JavaScriptUIItem) {
					JavaScriptUIItem javaScriptUIItem =
						(JavaScriptUIItem)menuItem;

					if (_isCheckingInNewFile()) {
						javaScriptUIItem.setOnClick(
							StringBundler.concat(
								"window.location.href = '",
								HtmlUtil.escapeJS(
									_getActionURL(Constants.CHECKIN)),
								"'"));
					}
					else {
						javaScriptUIItem.setOnClick(
							StringBundler.concat(
								_getNamespace(), "showVersionDetailsDialog('",
								HtmlUtil.escapeJS(
									_getActionURL(Constants.CHECKIN)),
								"');"));
					}
				}
			}
			else if (DLUIItemKeys.CANCEL_CHECKOUT.equals(menuItem.getKey())) {
				if (menuItem instanceof URLMenuItem) {
					URLMenuItem urlMenuItem = (URLMenuItem)menuItem;

					urlMenuItem.setMethod(HttpMethods.POST);
					urlMenuItem.setURL(
						_getActionURL(Constants.CANCEL_CHECKOUT));
				}
			}
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"c3a385d0-7551-11e8-9798-186590d14d8f");

	private final DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;
	private final DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;
	private final ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;
	private final PermissionChecker _permissionChecker;
	private final Portal _portal;
	private final ResourceBundle _resourceBundle;

}