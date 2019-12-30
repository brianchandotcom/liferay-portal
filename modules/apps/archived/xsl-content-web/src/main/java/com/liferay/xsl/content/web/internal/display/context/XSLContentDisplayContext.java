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

package com.liferay.xsl.content.web.internal.display.context;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.xsl.content.web.internal.configuration.XSLContentConfiguration;
import com.liferay.xsl.content.web.internal.configuration.XSLContentPortletInstanceConfiguration;
import com.liferay.xsl.content.web.internal.util.XSLContentUtil;

import java.net.URL;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class XSLContentDisplayContext {

	public XSLContentDisplayContext(
			HttpServletRequest httpServletRequest,
			XSLContentConfiguration xslContentConfiguration)
		throws ConfigurationException {

		_xslContentConfiguration = xslContentConfiguration;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_xslContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				XSLContentPortletInstanceConfiguration.class);
	}

	public String getContent(ServletRequest servletRequest) throws Exception {
		if (_content != null) {
			return _content;
		}

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		String contextPath = httpServletRequest.getContextPath();

		String xmlUrl = XSLContentUtil.replaceUrlTokens(
			_themeDisplay, contextPath,
			_xslContentPortletInstanceConfiguration.xmlUrl());

		String xslUrl = XSLContentUtil.replaceUrlTokens(
			_themeDisplay, contextPath,
			_xslContentPortletInstanceConfiguration.xslUrl());

		_content = XSLContentUtil.transform(
			_xslContentConfiguration, new URL(xmlUrl), new URL(xslUrl));

		return _content;
	}

	public XSLContentPortletInstanceConfiguration
		getXSLContentPortletInstanceConfiguration() {

		return _xslContentPortletInstanceConfiguration;
	}

	private String _content;
	private final ThemeDisplay _themeDisplay;
	private final XSLContentConfiguration _xslContentConfiguration;
	private final XSLContentPortletInstanceConfiguration
		_xslContentPortletInstanceConfiguration;

}