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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class EditPagePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public EditPagePortletConfigurationIcon(
		PortletRequest portletRequest, WikiNode node, WikiPage page) {

		super(portletRequest);

		_node = node;
		_page = page;
	}

	@Override
	public String getMessage() {
		return "edit";
	}

	@Override
	public String getURL() {
		PortletURL editPageURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, WikiPortletKeys.WIKI_ADMIN,
			PortletRequest.RENDER_PHASE);

		editPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		editPageURL.setParameter("redirect", themeDisplay.getURLCurrent());
		editPageURL.setParameter("nodeId", String.valueOf(_node.getNodeId()));
		editPageURL.setParameter("title", _page.getTitle());

		return editPageURL.toString();
	}

	@Override
	public boolean isShow() {
		return WikiPagePermissionChecker.contains(
			themeDisplay.getPermissionChecker(), _page, ActionKeys.UPDATE);
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private final WikiNode _node;
	private final WikiPage _page;

}