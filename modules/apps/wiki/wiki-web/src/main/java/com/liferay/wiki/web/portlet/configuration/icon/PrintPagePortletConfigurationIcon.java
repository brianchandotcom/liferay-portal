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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Roberto Díaz
 */
public class PrintPagePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PrintPagePortletConfigurationIcon(
		PortletRequest portletRequest, WikiNode node, WikiPage page) {

		super(portletRequest);

		_node = node;
		_page = page;
	}

	@Override
	public String getMessage() {
		return "print";
	}

	@Override
	public String getOnClick() {
		try {
			StringBundler sb = new StringBundler(5);

			sb.append("window.open('");

			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				portletRequest, WikiPortletKeys.WIKI_ADMIN,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcRenderCommandName", "/wiki/view");
			portletURL.setParameter("nodeName", _node.getName());
			portletURL.setParameter("title", _page.getTitle());
			portletURL.setParameter("viewMode", Constants.PRINT);
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			sb.append(portletURL.toString());

			sb.append("', '', 'directories=0,height=480,left=80,location=1,");
			sb.append("menubar=1,resizable=1,scrollbars=yes,status=0,");
			sb.append("toolbar=0,top=180,width=640');");

			return sb.toString();
		}
		catch (WindowStateException wse) {
		}

		return StringPool.BLANK;
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private final WikiNode _node;
	private final WikiPage _page;

}