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

package com.liferay.frontend.taglib.clay.internal.jaxrs.context.provider;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = ThemeDisplayContextProvider.class)
@Provider
public class ThemeDisplayContextProvider
	implements ContextProvider<ThemeDisplay> {

	@Override
	public ThemeDisplay createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long plid = ParamUtil.getLong(httpServletRequest, "plid");

		if (plid > 0) {
			Layout layout = _layoutLocalService.fetchLayout(plid);

			themeDisplay.setLayout(layout);

			themeDisplay.setPlid(plid);
		}

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

		themeDisplay.setScopeGroupId(groupId);
		themeDisplay.setSiteGroupId(groupId);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = ParamUtil.getString(httpServletRequest, "portletId");

		portletDisplay.setId(portletId);

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return themeDisplay;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}