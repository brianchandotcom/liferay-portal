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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class AppManagerDisplayContext {

	public AppManagerDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getModuleNavigationItems() {
		String pluginType = ParamUtil.getString(
			_httpServletRequest, "pluginType", "components");

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(
							pluginType.equals("components"));
						navigationItem.setHref(_getViewModuleURL("components"));
						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "components"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(pluginType.equals("portlets"));
						navigationItem.setHref(_getViewModuleURL("portlets"));
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "portlets"));
					});
			}
		};
	}

	public List<NavigationItem> getNavigationItems(String url, String label) {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(url);
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, label));
					});
			}
		};
	}

	private String _getViewModuleURL(String pluginType) {
		String app = ParamUtil.getString(_httpServletRequest, "app");
		String moduleGroup = ParamUtil.getString(
			_httpServletRequest, "moduleGroup");
		String symbolicName = ParamUtil.getString(
			_httpServletRequest, "symbolicName");
		String version = ParamUtil.getString(_httpServletRequest, "version");

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_module.jsp");
		portletURL.setParameter("app", app);
		portletURL.setParameter("moduleGroup", moduleGroup);
		portletURL.setParameter("symbolicName", symbolicName);
		portletURL.setParameter("version", version);
		portletURL.setParameter("pluginType", pluginType);

		return portletURL.toString();
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}