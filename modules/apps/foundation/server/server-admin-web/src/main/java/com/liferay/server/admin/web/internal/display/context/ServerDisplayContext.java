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

package com.liferay.server.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ServerDisplayContext {

	public ServerDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse) {

		_request = request;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getServerNavigationItems() {
		String tabs1 = ParamUtil.getString(_request, "tabs1", "resources");
		String tabs2 = ParamUtil.getString(_request, "tabs2");

		PortletURL serverURL = _renderResponse.createRenderURL();

		serverURL.setParameter("mvcRenderCommandName", "/server_admin/view");
		serverURL.setParameter("tabs1", tabs1);
		serverURL.setParameter("tabs2", tabs2);

		String[] tabs1Names = {
			"resources", "log-levels", "properties", "data-migration", "mail",
			"external-services", "script", "shutdown"
		};

		List<NavigationItem> navigationItems = new ArrayList<>();

		for (String tabs1Name : tabs1Names) {
			NavigationItem entriesNavigationItem = new NavigationItem();

			entriesNavigationItem.setActive(tabs1.equals(tabs1Name));

			serverURL.setParameter("tabs1", tabs1Name);

			entriesNavigationItem.setHref(serverURL.toString());

			entriesNavigationItem.setLabel(
				LanguageUtil.get(_request, tabs1Name));

			navigationItems.add(entriesNavigationItem);
		}

		return navigationItems;
	}

	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}