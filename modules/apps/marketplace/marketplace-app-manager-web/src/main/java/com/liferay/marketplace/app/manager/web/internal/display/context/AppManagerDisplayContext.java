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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

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

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(pluginType.equals("components"));
				navigationItem.setHref(_getViewModuleURL("components"));
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "components"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(pluginType.equals("portlets"));
				navigationItem.setHref(_getViewModuleURL("portlets"));
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "portlets"));
			}
		).build();
	}

	private String _getViewModuleURL(String pluginType) {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view_module.jsp"
		).setParameter(
			"app", ParamUtil.getString(_httpServletRequest, "app")
		).setParameter(
			"moduleGroup",
			ParamUtil.getString(_httpServletRequest, "moduleGroup")
		).setParameter(
			"pluginType", pluginType
		).setParameter(
			"symbolicName",
			ParamUtil.getString(_httpServletRequest, "symbolicName")
		).setParameter(
			"version", ParamUtil.getString(_httpServletRequest, "version")
		).buildString();
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}