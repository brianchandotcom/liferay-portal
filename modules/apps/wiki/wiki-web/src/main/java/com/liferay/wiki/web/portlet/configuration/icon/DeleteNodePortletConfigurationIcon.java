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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.trash.kernel.util.TrashUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class DeleteNodePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DeleteNodePortletConfigurationIcon(
		PortletRequest portletRequest, WikiNode node) {

		super(portletRequest);

		_node = node;
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

		portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/edit_node");

		if (isTrashEnabled(themeDisplay.getScopeGroupId())) {
			portletURL.setParameter(Constants.CMD, Constants.MOVE_TO_TRASH);
		}
		else {
			portletURL.setParameter(Constants.CMD, Constants.DELETE);
		}

		PortletURL viewNodesURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, WikiPortletKeys.WIKI_ADMIN,
			PortletRequest.RENDER_PHASE);

		viewNodesURL.setParameter("mvcRenderCommandName", "/wiki_admin/view");

		portletURL.setParameter("redirect", viewNodesURL.toString());
		portletURL.setParameter("nodeId", String.valueOf(_node.getNodeId()));

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		if (WikiNodePermissionChecker.contains(
				themeDisplay.getPermissionChecker(), _node,
				ActionKeys.DELETE) &&
			(WikiNodeServiceUtil.getNodesCount(
				themeDisplay.getScopeGroupId()) > 1)) {

			return true;
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

	private final WikiNode _node;

}