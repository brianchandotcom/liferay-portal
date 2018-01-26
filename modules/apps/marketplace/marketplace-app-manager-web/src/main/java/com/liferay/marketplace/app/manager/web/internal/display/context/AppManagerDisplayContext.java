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

package com.liferay.marketplace.app.manager.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class AppManagerDisplayContext {

	public AppManagerDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse) {

		_request = request;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getModuleNavigationItems() {
		String pluginType = ParamUtil.getString(
			_request, "pluginType", "components");

		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem componentsNavigationItem = new NavigationItem();

		componentsNavigationItem.setActive(pluginType.equals("components"));
		componentsNavigationItem.setHref(_getViewModuleURL("components"));
		componentsNavigationItem.setLabel(
			LanguageUtil.get(_request, "components"));

		navigationItems.add(componentsNavigationItem);

		NavigationItem portletsNavigationItem = new NavigationItem();

		portletsNavigationItem.setActive(pluginType.equals("portlets"));
		portletsNavigationItem.setHref(_getViewModuleURL("portlets"));
		portletsNavigationItem.setLabel(LanguageUtil.get(_request, "portlets"));

		navigationItems.add(portletsNavigationItem);

		return navigationItems;
	}

	public List<NavigationItem> getNavigationItems(String url, String label) {
		NavigationItem navigationItem = new NavigationItem();

		navigationItem.setActive(true);
		navigationItem.setHref(url);
		navigationItem.setLabel(LanguageUtil.get(_request, label));

		return Arrays.asList(navigationItem);
	}

	private String _getViewModuleURL(String pluginType) {
		String app = ParamUtil.getString(_request, "app");
		String moduleGroup = ParamUtil.getString(_request, "moduleGroup");
		String symbolicName = ParamUtil.getString(_request, "symbolicName");
		String version = ParamUtil.getString(_request, "version");

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_module.jsp");
		portletURL.setParameter("app", app);
		portletURL.setParameter("moduleGroup", moduleGroup);
		portletURL.setParameter("symbolicName", symbolicName);
		portletURL.setParameter("version", version);
		portletURL.setParameter("pluginType", pluginType);

		return portletURL.toString();
	}

	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}