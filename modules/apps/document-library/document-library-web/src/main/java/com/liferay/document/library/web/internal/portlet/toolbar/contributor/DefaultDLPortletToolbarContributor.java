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
import com.liferay.document.library.portlet.toolbar.contributor.DLPortletToolbarContributor;
import com.liferay.document.library.portlet.toolbar.contributor.DLPortletToolbarContributorContext;
import com.liferay.document.library.web.internal.portlet.toolbar.contributor.helper.DLPortletToolbarContributorHelper;
import com.liferay.document.library.web.internal.portlet.toolbar.contributor.helper.MenuItemProvider;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 * @author Mauro Mariuzzo
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"mvc.render.command.name=-",
		"mvc.render.command.name=/document_library/view",
		"mvc.render.command.name=/document_library/view_folder"
	},
	service = {
		DLPortletToolbarContributor.class, PortletToolbarContributor.class
	}
)
public class DefaultDLPortletToolbarContributor
	extends BasePortletToolbarContributor
	implements DLPortletToolbarContributor {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlPortletToolbarContributorContexts = ServiceTrackerListFactory.open(
			bundleContext, DLPortletToolbarContributorContext.class);
	}

	@Deactivate
	protected void deactivate() {
		_dlPortletToolbarContributorContexts.close();
	}

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_dlPortletToolbarContributorHelper.isShowActionsEnabled(
				themeDisplay, portletRequest)) {

			return null;
		}

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
			_menuItemProvider.getAddRepositoryMenuItem(
				folder, themeDisplay, portletRequest));

		_add(
			menuItems,
			_menuItemProvider.getAddShortcutMenuItem(
				folder, themeDisplay, portletRequest));

		MenuItem lastStaticMenuItem = null;

		if (!menuItems.isEmpty()) {
			lastStaticMenuItem = menuItems.get(menuItems.size() - 1);
		}

		for (DLPortletToolbarContributorContext
				dlPortletToolbarContributorContext :
					_dlPortletToolbarContributorContexts) {

			dlPortletToolbarContributorContext.updatePortletTitleMenuItems(
				menuItems, folder, themeDisplay, portletRequest,
				portletResponse);
		}

		MenuItem lastExtensionMenuItem = null;

		if (!menuItems.isEmpty()) {
			lastExtensionMenuItem = menuItems.get(menuItems.size() - 1);
		}

		menuItems.addAll(
			_menuItemProvider.getAddDocumentTypesMenuItems(
				folder, themeDisplay, portletRequest));

		if ((lastStaticMenuItem != null) &&
			(lastStaticMenuItem != menuItems.get(menuItems.size() - 1))) {

			lastStaticMenuItem.setSeparator(true);
		}

		if ((lastExtensionMenuItem != null) &&
			(lastExtensionMenuItem != menuItems.get(menuItems.size() - 1))) {

			lastExtensionMenuItem.setSeparator(true);
		}

		return menuItems;
	}

	private void _add(List<MenuItem> menuItems, MenuItem menuItem) {
		if (menuItem != null) {
			menuItems.add(menuItem);
		}
	}

	private ServiceTrackerList
		<DLPortletToolbarContributorContext, DLPortletToolbarContributorContext>
			_dlPortletToolbarContributorContexts;

	@Reference
	private DLPortletToolbarContributorHelper
		_dlPortletToolbarContributorHelper;

	private final MenuItemProvider _menuItemProvider = new MenuItemProvider();

}