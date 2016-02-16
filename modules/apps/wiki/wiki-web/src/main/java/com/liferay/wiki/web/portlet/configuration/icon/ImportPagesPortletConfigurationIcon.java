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

package com.liferay.wiki.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class ImportPagesPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ImportPagesPortletConfigurationIcon(
		PortletRequest portletRequest, WikiNode node) {

		super(portletRequest);

		_node = node;
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return "import-pages";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, WikiPortletKeys.WIKI_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/wiki/import_pages");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("nodeId", String.valueOf(_node.getNodeId()));

		return portletURL.toString();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return WikiNodePermissionChecker.contains(
			themeDisplay.getPermissionChecker(), _node, ActionKeys.IMPORT);
	}

	private final WikiNode _node;

}