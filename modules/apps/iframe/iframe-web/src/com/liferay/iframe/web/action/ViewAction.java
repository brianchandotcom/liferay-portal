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

package com.liferay.iframe.web.action;

import com.liferay.iframe.web.util.IFrameUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAction {

	public String transformSrc(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String src = getSrc(renderRequest, renderResponse);

		boolean auth = GetterUtil.getBoolean(
			portletPreferences.getValue("auth", StringPool.BLANK));

		if (!auth) {
			return src;
		}

		String authType = portletPreferences.getValue(
			"authType", StringPool.BLANK);

		if (authType.equals("basic")) {
			String userName = getUserName(renderRequest, renderResponse);
			String password = getPassword(renderRequest, renderResponse);

			int pos = src.indexOf("://");

			String protocol = src.substring(0, pos + 3);
			String url = src.substring(pos + 3);

			src = protocol + userName + ":" + password + "@" + url;
		}
		else {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(
					com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

			String portletId = PortalUtil.getPortletId(renderRequest);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);

			src = "/proxy.jsp?p_l_id=" + themeDisplay.getPlid() +
				"&p_p_id=" + portletId;
		}

		return src;
	} protected String getSrc(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String src = portletPreferences.getValue("src", StringPool.BLANK);

		src = ParamUtil.getString(renderRequest, "src", src);

		return src;
	}

	protected String getUserName(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String userName = portletPreferences.getValue(
			"basicUserName", StringPool.BLANK);

		return IFrameUtil.getUserName(renderRequest, userName);
	}

	protected String getPassword(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String password = portletPreferences.getValue(
			"basicPassword", StringPool.BLANK);

		return IFrameUtil.getPassword(renderRequest, password);
	}

}