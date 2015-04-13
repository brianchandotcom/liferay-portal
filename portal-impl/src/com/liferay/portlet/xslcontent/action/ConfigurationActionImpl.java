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

package com.liferay.portlet.xslcontent.action;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.net.MalformedURLException;
import java.net.URL;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateUrls(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected boolean hasAllowedProtocol(String xmlURL) {
		try {
			URL url = new URL(xmlURL);

			String protocol = url.getProtocol();

			if (ArrayUtil.contains(_PROTOCOLS, protocol)) {
				return true;
			}
		}
		catch (MalformedURLException murle) {
			return false;
		}

		return false;
	}

	protected void validateUrls(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String xmlUrl = getParameter(actionRequest, "xmlUrl");

		xmlUrl = StringUtil.replace(
			xmlUrl, "@portal_url@", themeDisplay.getPortalURL());

		if (!hasAllowedProtocol(xmlUrl)) {
			SessionErrors.add(actionRequest, "xmlUrl");
		}

		String xslUrl = getParameter(actionRequest, "xslUrl");

		xslUrl = StringUtil.replace(
			xslUrl, "@portal_url@", themeDisplay.getPortalURL());

		if (!hasAllowedProtocol(xslUrl)) {
			SessionErrors.add(actionRequest, "xslUrl");
		}
	}

	private static final String[] _PROTOCOLS = {"http", "https"};

}