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

package com.liferay.bookmarks.web.portlet.toolbar.contributor;

import com.liferay.bookmarks.configuration.BookmarksGroupServiceOverriddenConfiguration;
import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.constants.BookmarksWebKeys;
import com.liferay.bookmarks.exception.NoSuchFolderException;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderService;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS,
		"mvc.render.command.name=-", "mvc.render.command.name=/bookmarks/view"
	},
	service = {
		BookmarksPortletToolbarContributor.class,
		PortletToolbarContributor.class
	}
)
public class BookmarksPortletToolbarContributor
	implements PortletToolbarContributor {

	@Override
	public List<Menu> getPortletTitleMenus(PortletRequest portletRequest) {
		List<Menu> menus = new ArrayList<>();

		Menu menu = new Menu();

		menu.setDirection("down");
		menu.setExtended(false);
		menu.setIcon("../aui/plus-sign-2");
		menu.setMenuItems(getPortletTitleMenuItems(portletRequest));
		menu.setShowArrow(false);

		menus.add(menu);

		return menus;
	}

	protected void addPortletTitleAddBookmarkMenuItem(
			List<MenuItem> menuItems, BookmarksFolder folder,
			ThemeDisplay themeDisplay, PortletRequest portletRequest)
		throws PortalException {

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_ENTRY)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest), "bookmark"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/bookmarks/edit_entry");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addPortletTitleAddFolderMenuItem(
			List<MenuItem> menuItems, BookmarksFolder folder,
			ThemeDisplay themeDisplay, PortletRequest portletRequest)
		throws PortalException {

		long folderId = _getFolderId(folder);

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId,
				ActionKeys.ADD_FOLDER)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest),
				(folder != null) ? "subfolder" : "folder"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/bookmarks/edit_folder");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter("parentFolderId", String.valueOf(folderId));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected boolean containsPermission(
		PermissionChecker permissionChecker, long groupId, long folderId,
		String actionId) {

		try {
			_baseModelPermissionChecker.checkBaseModel(
				permissionChecker, groupId, folderId, actionId);
		}
		catch (PortalException pe) {
			return false;
		}

		return true;
	}

	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BookmarksFolder folder = _getFolder(themeDisplay, portletRequest);

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			addPortletTitleAddFolderMenuItem(
				menuItems, folder, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add folder menu item", pe);
		}

		try {
			addPortletTitleAddBookmarkMenuItem(
				menuItems, folder, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add bookmark menu item", pe);
		}

		return menuItems;
	}

	@Reference(
		target = "(model.class.name=com.liferay.bookmarks.model.BookmarksFolder)",
		unbind = "-"
	)
	protected void setBaseModelPermissionChecker(
		BaseModelPermissionChecker baseModelPermissionChecker) {

		_baseModelPermissionChecker = baseModelPermissionChecker;
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderService(
		BookmarksFolderService bookmarksFolderService) {

		_bookmarksFolderService = bookmarksFolderService;
	}

	private BookmarksFolder _getFolder(
		ThemeDisplay themeDisplay, PortletRequest portletRequest) {

		long rootFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		try {
			BookmarksGroupServiceOverriddenConfiguration
				bookmarksGroupServiceOverriddenConfiguration =
					ConfigurationFactoryUtil.getConfiguration(
						BookmarksGroupServiceOverriddenConfiguration.class,
						new GroupServiceSettingsLocator(
							themeDisplay.getScopeGroupId(),
							BookmarksConstants.SERVICE_NAME));

			rootFolderId =
				bookmarksGroupServiceOverriddenConfiguration.rootFolderId();
		}
		catch (ConfigurationException ce) {
			_log.error(
				"Unable to obtain bookmarks root folder ID for group " +
					themeDisplay.getScopeGroupId());
		}

		BookmarksFolder folder = (BookmarksFolder)portletRequest.getAttribute(
			BookmarksWebKeys.BOOKMARKS_FOLDER);

		long folderId = BeanParamUtil.getLong(
			folder, portletRequest, "folderId", rootFolderId);

		if ((folder == null) &&
			(folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			try {
				folder = _bookmarksFolderService.getFolder(folderId);
			}
			catch (NoSuchFolderException nsfe) {
				folder = null;
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return folder;
	}

	private long _getFolderId(BookmarksFolder folder) {
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		return folderId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BookmarksPortletToolbarContributor.class);

	private volatile BaseModelPermissionChecker _baseModelPermissionChecker;
	private volatile BookmarksFolderService _bookmarksFolderService;

}