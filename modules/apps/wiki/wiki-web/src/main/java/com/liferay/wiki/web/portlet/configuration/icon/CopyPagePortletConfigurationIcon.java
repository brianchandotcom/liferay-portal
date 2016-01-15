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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class CopyPagePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public CopyPagePortletConfigurationIcon(
		PortletRequest portletRequest, WikiNode node, WikiPage page) {

		super(portletRequest);

		_node = node;
		_page = page;
	}

	@Override
	public String getMessage() {
		return "copy";
	}

	@Override
	public String getURL() {
		PortletURL copyURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, WikiPortletKeys.WIKI_ADMIN,
			PortletRequest.RENDER_PHASE);

		copyURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		copyURL.setParameter("redirect", themeDisplay.getURLCurrent());
		copyURL.setParameter("nodeId", String.valueOf(_node.getNodeId()));
		copyURL.setParameter("title", StringPool.BLANK);
		copyURL.setParameter("editTitle", "1");
		copyURL.setParameter(
			"templateNodeId", String.valueOf(_page.getNodeId()));
		copyURL.setParameter(
			"templateTitle", HtmlUtil.unescape(_page.getTitle()));

		return copyURL.toString();
	}

	@Override
	public boolean isShow() {
		return false;
	}

	private final WikiNode _node;
	private final WikiPage _page;

}