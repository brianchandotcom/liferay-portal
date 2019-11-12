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

package com.liferay.document.library.web.internal.portlet.toolbar.contributor;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.portlet.toolbar.contributor.helper.DLPortletToolbarContributorHelper;
import com.liferay.document.library.web.internal.portlet.toolbar.contributor.helper.MenuItemProvider;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.render.command.name=-",
		"mvc.render.command.name=/image_gallery_display/view"
	},
	service = PortletToolbarContributor.class
)
public class IGPortletToolbarContributor extends BasePortletToolbarContributor {

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Folder folder = _dlPortletToolbarContributorHelper.getFolder(
			themeDisplay, portletRequest);

		List<MenuItem> menuItems = new ArrayList<>();

		_add(
			menuItems,
			_menuItemProvider.getAddFileMenuItem(
				folder, themeDisplay, portletRequest));

		_add(
			menuItems,
			_menuItemProvider.getAddMultipleFilesMenuItem(
				folder, themeDisplay, portletRequest));

		_add(
			menuItems,
			_menuItemProvider.getAddFolderMenuItem(
				folder, themeDisplay, portletRequest));

		_add(
			menuItems,
			_menuItemProvider.getAddShortcutMenuItem(
				folder, themeDisplay, portletRequest));

		return menuItems;
	}

	private void _add(List<MenuItem> menuItems, MenuItem menuItem) {
		if (menuItem != null) {
			menuItems.add(menuItem);
		}
	}

	@Reference
	private DLPortletToolbarContributorHelper
		_dlPortletToolbarContributorHelper;

	private final MenuItemProvider _menuItemProvider = new MenuItemProvider();

}