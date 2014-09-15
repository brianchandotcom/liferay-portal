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

package com.liferay.xsl.content.web.portlet.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_xsl_content_web_portlet_XSLContentPortlet"
	},
	service = ConfigurationAction.class
)
public class XSLContentConfigurationAction extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateUrls(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected boolean hasAllowedProtocol(
		String xmlURL, String[] allowedProtocols) {

		try {
			URL url = new URL(xmlURL);

			String protocol = url.getProtocol();

			if (ArrayUtil.contains(allowedProtocols, protocol)) {
				return true;
			}
		}
		catch (MalformedURLException e) {
			return false;
		}

		return false;
	}

	protected void validateUrls(ActionRequest actionRequest) {
		String xmlUrl = getParameter(actionRequest, "xmlUrl");
		String xslUrl = getParameter(actionRequest, "xslUrl");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		xmlUrl = StringUtil.replace(
			xmlUrl, "@portal_url@", themeDisplay.getPortalURL());

		xslUrl = StringUtil.replace(
			xslUrl, "@portal_url@", themeDisplay.getPortalURL());

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		Portlet xslPortlet = PortletLocalServiceUtil.getPortletById(
			portletResource);

		Map initParameters = xslPortlet.getInitParams();

		String[] allowedProtocols = GetterUtil.getStringValues(
			initParameters.get("allowed-protocols"), _ALLOWED_PROTOCOLS);

		if (!hasAllowedProtocol(xmlUrl, allowedProtocols)) {
			SessionErrors.add(actionRequest, "xmlUrl");
		}

		if (!hasAllowedProtocol(xslUrl, allowedProtocols)) {
			SessionErrors.add(actionRequest, "xslUrl");
		}
	}

	private static final String[] _ALLOWED_PROTOCOLS = {"http", "https"};

}