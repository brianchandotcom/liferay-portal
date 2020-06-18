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

package com.liferay.product.navigation.global.menu.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.product.navigation.global.menu.web.internal.constants.ProductNavigationGlobalMenuPortletKeys;

import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class GlobalMenuDisplayContext {

	public GlobalMenuDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public Map<String, Object> getGlobalMenuComponentData()
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		return HashMapBuilder.<String, Object>put(
			"companyName", HtmlUtil.escape(company.getName())
		).put(
			"logoURL",
			StringBundler.concat(
				themeDisplay.getPathImage(), "/company_logo?img_id=",
				company.getLogoId(), "&t=",
				WebServerServletTokenUtil.getToken(company.getLogoId()))
		).put(
			"panelAppsURL",
			() -> {
				LiferayPortletURL globalMenuPanelAppsURL =
					PortletURLFactoryUtil.create(
						_httpServletRequest,
						ProductNavigationGlobalMenuPortletKeys.
							PRODUCT_NAVIGATION_GLOBAL_MENU,
						PortletRequest.RESOURCE_PHASE);

				globalMenuPanelAppsURL.setResourceID("/global_menu/panel_apps");

				return globalMenuPanelAppsURL.toString();
			}
		).put(
			"selectedPortletId", themeDisplay.getPpid()
		).build();
	}

	private final HttpServletRequest _httpServletRequest;

}