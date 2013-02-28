/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class PortletURLFactoryImpl implements PortletURLFactory {

	public LiferayPortletURL create(
		HttpServletRequest request, String portletId, long plid,
		String lifecycle) {

		return new PortletURLImpl(request, portletId, plid, lifecycle);
	}

	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, long plid,
		String lifecycle) {

		return new PortletURLImpl(portletRequest, portletId, plid, lifecycle);
	}

	public LiferayPortletURL createControlPanel(
		HttpServletRequest request, String portletId, long referrerPlid,
		String lifecycle) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = 0;

		try {
			plid = PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
		} catch (Exception e) {
			_log.error("Unable to determine control panel layout id", e);
		}

		LiferayPortletURL portletURL = new PortletURLImpl(
			request, portletId, plid, lifecycle);

		portletURL.setDoAsGroupId(themeDisplay.getScopeGroupId());
		portletURL.setRefererPlid(themeDisplay.getPlid());

		return portletURL;
	}

	public LiferayPortletURL createControlPanel(
		PortletRequest portletRequest, String portletId, long referrerPlid,
		String lifecycle) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = 0;

		try {
			plid = PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
		} catch (Exception e) {
			_log.error("Unable to determine control panel layout id", e);
		}

		LiferayPortletURL portletURL = new PortletURLImpl(
			portletRequest, portletId, plid, lifecycle);

		portletURL.setDoAsGroupId(themeDisplay.getScopeGroupId());
		portletURL.setRefererPlid(themeDisplay.getPlid());

		return portletURL;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletURLFactoryImpl.class);

}