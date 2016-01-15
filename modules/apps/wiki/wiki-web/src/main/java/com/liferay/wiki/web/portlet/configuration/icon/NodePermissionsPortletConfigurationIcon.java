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

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import javax.portlet.PortletRequest;

/**
 * @author Roberto Díaz
 */
public class NodePermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public NodePermissionsPortletConfigurationIcon(
		PortletRequest portletRequest, WikiNode node) {

		super(portletRequest);

		_node = node;
	}

	@Override
	public String getMessage() {
		return "permissions";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			url = PermissionsURLTag.doTag(
				StringPool.BLANK, WikiNode.class.getName(), _node.getName(),
				null, String.valueOf(_node.getNodeId()),
				LiferayWindowState.POP_UP.toString(), null,
				themeDisplay.getRequest());
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public boolean isShow() {
		return WikiNodePermissionChecker.contains(
			themeDisplay.getPermissionChecker(), _node, ActionKeys.PERMISSIONS);
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	private final WikiNode _node;

}