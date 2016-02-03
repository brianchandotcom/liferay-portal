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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.trash.kernel.util.TrashUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class DeletePagePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DeletePagePortletConfigurationIcon(
		PortletRequest portletRequest, WikiPage page) {

		super(portletRequest);

		_page = page;
	}

	@Override
	public String getMessage() {
		if (isTrashEnabled(themeDisplay.getScopeGroupId())) {
			return "move-to-the-recycle-bin";
		}

		return "delete";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, WikiPortletKeys.WIKI_ADMIN,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/edit_page");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);

		if (!_page.isDraft() &&
			isTrashEnabled(themeDisplay.getScopeGroupId())) {

			portletURL.setParameter(Constants.CMD, Constants.MOVE_TO_TRASH);
		}
		else {
			portletURL.setParameter(
				"version", String.valueOf(_page.getVersion()));
		}

		PortletURL redirectURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, WikiPortletKeys.WIKI_ADMIN,
			PortletRequest.ACTION_PHASE);

		redirectURL.setParameter("mvcRenderCommandName", "/wiki/view_pages");
		redirectURL.setParameter("navigation", "all-pages");
		redirectURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));

		portletURL.setParameter("redirect", redirectURL.toString());

		portletURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));
		portletURL.setParameter("title", _page.getTitle());

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			if (!_page.isDraft() &&
				WikiPagePermissionChecker.contains(
					themeDisplay.getPermissionChecker(), _page.getNodeId(),
					HtmlUtil.unescape(_page.getTitle()), ActionKeys.DELETE)) {

				return true;
			}
			else if (_page.isDraft() &&
					 WikiPagePermissionChecker.contains(
						 themeDisplay.getPermissionChecker(), _page,
						 ActionKeys.DELETE)) {

				return true;
			}
		}
		catch (PortalException pe) {
		}

		return false;
	}

	protected boolean isTrashEnabled(long groupId) {
		try {
			if (TrashUtil.isTrashEnabled(groupId)) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	private final WikiPage _page;

}