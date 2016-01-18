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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class MovePagePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public MovePagePortletConfigurationIcon(
		PortletRequest portletRequest, WikiPage page) {

		super(portletRequest);

		_page = page;
	}

	@Override
	public String getMessage() {
		return "move";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, WikiPortletKeys.WIKI_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/wiki/move_page");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));
		portletURL.setParameter("title", _page.getTitle());

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			if (WikiPagePermissionChecker.contains(
					themeDisplay.getPermissionChecker(), _page,
					ActionKeys.UPDATE) ||
				WikiNodePermissionChecker.contains(
					themeDisplay.getPermissionChecker(), _page.getNodeId(),
					ActionKeys.ADD_PAGE)) {

				return true;
			}
		}
		catch (PortalException pe) {
		}

		return false;
	}

	private final WikiPage _page;

}