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
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
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
	immediate = true,
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

	@Override
	public List<MenuItem> getPortletTitleAddDocumentMenuItems(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		return _menuItemProvider.getAddDocumentTypesMenuItems(
			folder, themeDisplay, portletRequest);
	}

	@Override
	public MenuItem getPortletTitleAddFolderMenuItem(
		ThemeDisplay themeDisplay, PortletRequest portletRequest,
		Folder folder) {

		return _menuItemProvider.getAddFolderMenuItem(
			themeDisplay, portletRequest, folder);
	}

	@Override
	public MenuItem getPortletTitleAddMultipleDocumentsMenuItem(
		ThemeDisplay themeDisplay, PortletRequest portletRequest,
		Folder folder) {

		return _menuItemProvider.getAddMultipleFilesMenuItem(
			themeDisplay, portletRequest, folder);
	}

	public URLMenuItem getPortletTitleAddRepositoryMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		return _menuItemProvider.getAddRepositoryMenuItem(
			folder, themeDisplay, portletRequest);
	}

	public URLMenuItem getPortletTitleAddShortcutMenuItem(
		Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		return _menuItemProvider.getAddShortcutMenuItem(
			folder, themeDisplay, portletRequest);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlPortletToolbarContributorContexts = ServiceTrackerListFactory.open(
			bundleContext, DLPortletToolbarContributorContext.class);
	}

	protected void addPortletTitleAddDocumentMenuItems(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		menuItems.addAll(
			getPortletTitleAddDocumentMenuItems(
				folder, themeDisplay, portletRequest));
	}

	protected void addPortletTitleAddFolderMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		MenuItem portletTitleAddFolderMenuItem =
			getPortletTitleAddFolderMenuItem(
				themeDisplay, portletRequest, folder);

		if (portletTitleAddFolderMenuItem != null) {
			menuItems.add(portletTitleAddFolderMenuItem);
		}
	}

	protected void addPortletTitleAddMultipleDocumentsMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		MenuItem portletTitleAddMultipleDocumentsMenuItem =
			getPortletTitleAddMultipleDocumentsMenuItem(
				themeDisplay, portletRequest, folder);

		if (portletTitleAddMultipleDocumentsMenuItem != null) {
			menuItems.add(portletTitleAddMultipleDocumentsMenuItem);
		}
	}

	protected void addPortletTitleAddRepositoryMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		URLMenuItem portletTitleAddRepositoryMenuItem =
			getPortletTitleAddRepositoryMenuItem(
				folder, themeDisplay, portletRequest);

		if (portletTitleAddRepositoryMenuItem != null) {
			menuItems.add(portletTitleAddRepositoryMenuItem);
		}
	}

	protected void addPortletTitleAddShortcutMenuItem(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest) {

		URLMenuItem portletTitleAddShortcutMenuItem =
			getPortletTitleAddShortcutMenuItem(
				folder, themeDisplay, portletRequest);

		if (portletTitleAddShortcutMenuItem != null) {
			menuItems.add(portletTitleAddShortcutMenuItem);
		}
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

		Folder folder = _dlPortletToolbarContributorHelper.getFolder(
			themeDisplay, portletRequest);

		List<MenuItem> menuItems = new ArrayList<>();

		menuItems.add(
			_menuItemProvider.getAddBasicDocumentMenuItem(
				folder, themeDisplay, portletRequest));

		addPortletTitleAddFolderMenuItem(
			menuItems, folder, themeDisplay, portletRequest);

		addPortletTitleAddMultipleDocumentsMenuItem(
			menuItems, folder, themeDisplay, portletRequest);

		addPortletTitleAddRepositoryMenuItem(
			menuItems, folder, themeDisplay, portletRequest);

		addPortletTitleAddShortcutMenuItem(
			menuItems, folder, themeDisplay, portletRequest);

		MenuItem lastStaticMenuItem = menuItems.get(menuItems.size() - 1);

		for (DLPortletToolbarContributorContext
				dlPortletToolbarContributorContext :
					_dlPortletToolbarContributorContexts) {

			dlPortletToolbarContributorContext.updatePortletTitleMenuItems(
				menuItems, folder, themeDisplay, portletRequest,
				portletResponse);
		}

		addPortletTitleAddDocumentMenuItems(
			menuItems, folder, themeDisplay, portletRequest);

		if (lastStaticMenuItem != menuItems.get(menuItems.size() - 1)) {
			lastStaticMenuItem.setSeparator(true);
		}

		return menuItems;
	}

	private ServiceTrackerList
		<DLPortletToolbarContributorContext, DLPortletToolbarContributorContext>
			_dlPortletToolbarContributorContexts;

	@Reference
	private DLPortletToolbarContributorHelper
		_dlPortletToolbarContributorHelper;

	private final MenuItemProvider _menuItemProvider = new MenuItemProvider();

}