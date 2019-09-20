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

package com.liferay.sharing.document.library.internal.display.context;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseUIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;
import com.liferay.sharing.display.context.util.SharingToolbarItemFactory;
import com.liferay.sharing.security.permission.SharingPermission;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public class SharingDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public SharingDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileEntry fileEntry,
		FileVersion fileVersion, ResourceBundle resourceBundle,
		SharingMenuItemFactory sharingMenuItemFactory,
		SharingToolbarItemFactory sharingToolbarItemFactory,
		SharingPermission sharingPermission,
		SharingConfiguration sharingConfiguration) {

		super(
			_UUID, parentDLDisplayContext, httpServletRequest,
			httpServletResponse, fileVersion);

		_httpServletRequest = httpServletRequest;
		_fileEntry = fileEntry;
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_sharingMenuItemFactory = sharingMenuItemFactory;
		_sharingToolbarItemFactory = sharingToolbarItemFactory;
		_sharingPermission = sharingPermission;
		_sharingConfiguration = sharingConfiguration;
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = super.getMenu();

		if (!_isShowShareAction() || !_sharingConfiguration.isEnabled()) {
			return menu;
		}

		_addSharingUIItem(
			menu.getMenuItems(),
			_sharingMenuItemFactory.createShareMenuItem(
				DLFileEntryConstants.getClassName(),
				_fileEntry.getFileEntryId(), _httpServletRequest));

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = super.getToolbarItems();

		if (!_isShowShareAction() || !_sharingConfiguration.isEnabled()) {
			return toolbarItems;
		}

		_addSharingUIItem(
			toolbarItems,
			_sharingToolbarItemFactory.createShareToolbarItem(
				DLFileEntryConstants.getClassName(),
				_fileEntry.getFileEntryId(), _httpServletRequest));

		return toolbarItems;
	}

	@Override
	public boolean isSharingLinkVisible() throws PortalException {
		if (_sharingConfiguration.isEnabled() &&
			_sharingPermission.containsSharePermission(
				_themeDisplay.getPermissionChecker(),
				PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()),
				_fileEntry.getFileEntryId(), _themeDisplay.getScopeGroupId())) {

			return true;
		}

		return false;
	}

	/**
	 * @see com.liferay.frontend.image.editor.integration.document.library.internal.display.context.ImageEditorDLViewFileVersionDisplayContext#_addEditWithImageEditorUIItem
	 */
	private <T extends BaseUIItem> List<T> _addSharingUIItem(
		List<T> uiItems, T sharingUIItem) {

		int i = 1;

		for (T uiItem : uiItems) {
			if (DLUIItemKeys.DOWNLOAD.equals(uiItem.getKey())) {
				break;
			}

			i++;
		}

		if (i >= uiItems.size()) {
			uiItems.add(sharingUIItem);
		}
		else {
			uiItems.add(i, sharingUIItem);
		}

		return uiItems;
	}

	private boolean _isShowActions() throws PortalException {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return true;
		}

		Settings settings = SettingsFactoryUtil.getSettings(
			new PortletInstanceSettingsLocator(
				_themeDisplay.getLayout(), portletDisplay.getId()));

		TypedSettings typedSettings = new TypedSettings(settings);

		return typedSettings.getBooleanValue("showActions");
	}

	private boolean _isShowShareAction() throws PortalException {
		if (_showImageEditorAction != null) {
			return _showImageEditorAction;
		}

		_showImageEditorAction = false;

		if (_themeDisplay.isSignedIn() && _isShowActions() &&
			_sharingPermission.containsSharePermission(
				_themeDisplay.getPermissionChecker(),
				PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()),
				_fileEntry.getFileEntryId(), _themeDisplay.getScopeGroupId())) {

			_showImageEditorAction = true;
		}

		return _showImageEditorAction;
	}

	private static final UUID _UUID = UUID.fromString(
		"6d7d30de-01fa-49db-a422-d78748aa03a7");

	private final FileEntry _fileEntry;
	private final HttpServletRequest _httpServletRequest;
	private final SharingConfiguration _sharingConfiguration;
	private final SharingMenuItemFactory _sharingMenuItemFactory;
	private final SharingPermission _sharingPermission;
	private final SharingToolbarItemFactory _sharingToolbarItemFactory;
	private Boolean _showImageEditorAction;
	private final ThemeDisplay _themeDisplay;

}