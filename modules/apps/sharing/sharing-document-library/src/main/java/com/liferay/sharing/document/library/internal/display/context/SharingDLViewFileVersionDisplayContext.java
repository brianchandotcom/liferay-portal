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

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.document.library.internal.display.context.logic.SharingDLDisplayContextHelper;

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
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, ResourceBundleLoader resourceBundleLoader) {

		super(_UUID, parentDLDisplayContext, request, response, fileVersion);

		_resourceBundleLoader = resourceBundleLoader;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			FileEntry fileEntry = null;

			if (fileVersion != null) {
				fileEntry = fileVersion.getFileEntry();
			}

			_fileEntry = fileEntry;

			_sharingDLDisplayContextHelper = new SharingDLDisplayContextHelper(
				fileEntry, request);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to create sharing document library view file version " +
					"display context for file version " + fileVersion,
				pe);
		}
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = super.getMenu();

		if (!_sharingDLDisplayContextHelper.isShowShareAction()) {
			return menu;
		}

		List<MenuItem> menuItems = menu.getMenuItems();

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(_themeDisplay.getLocale());

		SharingDLDisplayContextHelper sharingDLDisplayContextHelper =
			new SharingDLDisplayContextHelper(_fileEntry, request);

		menuItems.add(
			sharingDLDisplayContextHelper.
				getJavacriptEditWithImageEditorMenuItem(resourceBundle));

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = super.getToolbarItems();

		if (!_sharingDLDisplayContextHelper.isShowShareAction()) {
			return toolbarItems;
		}

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(_themeDisplay.getLocale());

		SharingDLDisplayContextHelper imageEditorDLDisplayContextHelper =
			new SharingDLDisplayContextHelper(_fileEntry, request);

		toolbarItems.add(
			imageEditorDLDisplayContextHelper.
				getJavacriptEditWithImageEditorToolbarItem(resourceBundle));

		return toolbarItems;
	}

	private static final UUID _UUID = UUID.fromString(
		"6d7d30de-01fa-49db-a422-d78748aa03a7");

	private final FileEntry _fileEntry;
	private final ResourceBundleLoader _resourceBundleLoader;
	private final SharingDLDisplayContextHelper _sharingDLDisplayContextHelper;
	private final ThemeDisplay _themeDisplay;

}