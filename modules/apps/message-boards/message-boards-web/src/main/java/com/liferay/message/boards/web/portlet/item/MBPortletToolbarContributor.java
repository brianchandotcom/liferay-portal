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

package com.liferay.message.boards.web.portlet.item;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.util.MBUtil;

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
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"mvc.render.command.name=-",
		"mvc.render.command.name=/message_boards/view"
	},
	service = {MBPortletToolbarContributor.class, PortletToolbarContributor.class}
)
public class MBPortletToolbarContributor implements PortletToolbarContributor {

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

	protected void addPortletTitleAddCategoryMenuItem(
			List<MenuItem> menuItems, long categoryId,
			ThemeDisplay themeDisplay, PortletRequest portletRequest)
		throws PortalException {

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), categoryId,
				ActionKeys.ADD_CATEGORY)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		String key = "category[message-board]";

		if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			key = "subcategory[message-board]";
		}

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest), key));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/edit_category");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter("parentCategoryId", String.valueOf(categoryId));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addPortletTitleAddMessageMenuItem(
			List<MenuItem> menuItems, long categoryId,
			ThemeDisplay themeDisplay, PortletRequest portletRequest)
		throws PortalException {

		if (!containsPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), categoryId,
				ActionKeys.ADD_MESSAGE)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(portletRequest), "thread"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/edit_message");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));
		portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

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

		long categoryId = _getCategoryId(portletRequest);

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			addPortletTitleAddCategoryMenuItem(
				menuItems, categoryId, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add folder menu item", pe);
		}

		try {
			addPortletTitleAddMessageMenuItem(
				menuItems, categoryId, themeDisplay, portletRequest);
		}
		catch (PortalException pe) {
			_log.error("Unable to add shortcut menu item", pe);
		}

		return menuItems;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portlet.messageboards.model.MBCategory)",
		unbind = "-"
	)
	protected void setBaseModelPermissionChecker(
		BaseModelPermissionChecker baseModelPermissionChecker) {

		_baseModelPermissionChecker = baseModelPermissionChecker;
	}

	private long _getCategoryId(PortletRequest portletRequest) {
		MBCategory category = (MBCategory)portletRequest.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);

		return MBUtil.getCategoryId(
			PortalUtil.getHttpServletRequest(portletRequest), category);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBPortletToolbarContributor.class);

	private volatile BaseModelPermissionChecker _baseModelPermissionChecker;

}