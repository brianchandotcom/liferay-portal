/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.display.context;

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.opener.constants.DLOpenerFileEntryReferenceConstants;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveMimeTypes;
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
 * @author Cristina González
 */
public class DLOpenerOneDriveDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public DLOpenerOneDriveDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion,
		ResourceBundle resourceBundle,
		ModelResourcePermission<FileEntry> fileEntryModelResourcePermission,
		DLOpenerFileEntryReferenceLocalService
			dlOpenerFileEntryReferenceLocalService,
		DLOpenerOneDriveManager dlOpenerOneDriveManager, Portal portal) {

		super(
			_UUID, parentDLDisplayContext, httpServletRequest,
			httpServletResponse, fileVersion);

		_resourceBundle = resourceBundle;
		_fileEntryModelResourcePermission = fileEntryModelResourcePermission;
		_dlOpenerFileEntryReferenceLocalService =
			dlOpenerFileEntryReferenceLocalService;
		_dlOpenerOneDriveManager = dlOpenerOneDriveManager;
		_portal = portal;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();
	}

	@Override
	public Menu getMenu() throws PortalException {
		if (!isActionsVisible() ||
			!DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				fileVersion.getMimeType()) ||
			!_dlOpenerOneDriveManager.isConfigured(
				fileVersion.getCompanyId()) ||
			!_fileEntryModelResourcePermission.contains(
				_permissionChecker, fileVersion.getFileEntry(),
				ActionKeys.UPDATE)) {

			return super.getMenu();
		}

		Menu menu = super.getMenu();

		if (_isCheckedOutInOneDrive()) {
			FileEntry fileEntry = fileVersion.getFileEntry();

			if (fileEntry.hasLock()) {
				List<MenuItem> menuItems = menu.getMenuItems();

				_updateCancelCheckoutAndCheckinMenuItems(menuItems);

				_addEditInOffice365UIItem(
					menuItems, _createEditInOffice365MenuItem(Constants.EDIT));
			}

			return menu;
		}

		_addEditInOffice365UIItem(
			menu.getMenuItems(),
			_createEditInOffice365MenuItem(Constants.CHECKOUT));

		return menu;
	}

	/**
	 * @see com.liferay.frontend.image.editor.integration.document.library.internal.display.context.ImageEditorDLViewFileVersionDisplayContext#_addEditWithImageEditorUIItem
	 */
	private <T extends BaseUIItem> List<T> _addEditInOffice365UIItem(
		List<T> uiItems, T editInOffice365UIItem) {

		int i = 1;

		for (T uiItem : uiItems) {
			if (DLUIItemKeys.EDIT.equals(uiItem.getKey())) {
				break;
			}

			i++;
		}

		if (i >= uiItems.size()) {
			uiItems.add(editInOffice365UIItem);
		}
		else {
			uiItems.add(i, editInOffice365UIItem);
		}

		return uiItems;
	}

	private MenuItem _createEditInOffice365MenuItem(String cmd)
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
			ActionRequest.ACTION_NAME, "/document_library/edit_in_office365");
		liferayPortletURL.setParameter(Constants.CMD, cmd);
		liferayPortletURL.setParameter(
			"fileEntryId", String.valueOf(fileVersion.getFileEntryId()));

		FileEntry fileEntry = fileVersion.getFileEntry();

		liferayPortletURL.setParameter(
			"folderId", String.valueOf(fileEntry.getFolderId()));

		return liferayPortletURL.toString();
	}

	private String _getLabelKey() {
		String office365MimeType =
			DLOpenerOneDriveMimeTypes.getOffice365MimeType(
				fileVersion.getMimeType());

		if (DLOpenerMimeTypes.APPLICATION_VND_PPTX.equals(office365MimeType)) {
			return "edit-in-office365";
		}

		if (DLOpenerMimeTypes.APPLICATION_VND_XLSX.equals(office365MimeType)) {
			return "edit-in-office365";
		}

		return "edit-in-office365";
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

	private boolean _isCheckedOutInOneDrive() throws PortalException {
		FileEntry fileEntry = fileVersion.getFileEntry();

		if (fileEntry.isCheckedOut() &&
			_dlOpenerOneDriveManager.isOneDriveFile(fileEntry)) {

			return true;
		}

		return false;
	}

	private boolean _isCheckingInNewFile() throws PortalException {
		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(fileVersion.getFileEntry());

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
								_getActionURL(Constants.CHECKIN), "'"));
					}
					else {
						javaScriptUIItem.setOnClick(
							StringBundler.concat(
								_getNamespace(), "showVersionDetailsDialog('",
								_getActionURL(Constants.CHECKIN), "');"));
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
		"9ad58b57-aa3c-44f9-91bd-9385f0a925bf");

	private final DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;
	private final DLOpenerOneDriveManager _dlOpenerOneDriveManager;
	private final ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;
	private final PermissionChecker _permissionChecker;
	private final Portal _portal;
	private final ResourceBundle _resourceBundle;

}