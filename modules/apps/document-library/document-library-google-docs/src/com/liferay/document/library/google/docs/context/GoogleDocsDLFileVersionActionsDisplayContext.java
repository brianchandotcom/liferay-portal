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

package com.liferay.document.library.google.docs.context;

import com.liferay.document.library.google.docs.util.GoogleDocsConstants;
import com.liferay.document.library.google.docs.util.GoogleDocsMetadataHelper;
import com.liferay.document.library.google.docs.util.ResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.context.BaseDLFileVersionActionsDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLFileVersionActionsDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLMenuItems;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class GoogleDocsDLFileVersionActionsDisplayContext
	extends BaseDLFileVersionActionsDisplayContext {

	public GoogleDocsDLFileVersionActionsDisplayContext(
		DLFileVersionActionsDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(_UUID, parentDLDisplayContext, request, response, fileVersion);
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		List<MenuItem> menuItems = super.getMenuItems();

		_removeMenuItem(menuItems, DLMenuItems.MENU_ITEM_ID_DOWNLOAD);
		_removeMenuItem(menuItems, DLMenuItems.MENU_ITEM_ID_OPEN_IN_MS_OFFICE);

		_insertEditInGoogleMenuItem(menuItems);

		return menuItems;
	}

	private int _getMenuItemIndex(List<MenuItem> menuItems, String menuItemId) {
		for (int i = 0; i < menuItems.size(); i++) {
			MenuItem menuItem = menuItems.get(i);

			String currentMenuItemId = menuItem.getId();

			if (currentMenuItemId.equals(menuItemId)) {
				return i;
			}
		}

		return -1;
	}

	private void _insertEditInGoogleMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		int menuItemIndex = _getMenuItemIndex(
			menuItems, DLMenuItems.MENU_ITEM_ID_EDIT);

		GoogleDocsMetadataHelper googleDocsMetadataHelper =
			new GoogleDocsMetadataHelper(fileVersion);

		String editURL = googleDocsMetadataHelper.getFieldValue(
			GoogleDocsConstants.DDM_FIELD_NAME_EDIT_URL);

		URLMenuItem urlMenuItem = new URLMenuItem();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceUtil.getResourceBundle(
			themeDisplay.getLocale());

		String message = LanguageUtil.get(resourceBundle, "edit-in-google");

		urlMenuItem.setId(GoogleDocsMenuItems.MENU_ITEM_ID_EDIT_IN_GOOGLE);
		urlMenuItem.setMessage(message);
		urlMenuItem.setIconCssClass("icon-edit");
		urlMenuItem.setURL(editURL);
		urlMenuItem.setTarget("_blank");

		menuItems.set(menuItemIndex, urlMenuItem);
	}

	private void _removeMenuItem(List<MenuItem> menuItems, String menuItemId) {
		int index = _getMenuItemIndex(menuItems, menuItemId);

		if (index != -1) {
			menuItems.remove(index);
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"7B61EA79-83AE-4FFD-A77A-1D47E06EBBE9");

}