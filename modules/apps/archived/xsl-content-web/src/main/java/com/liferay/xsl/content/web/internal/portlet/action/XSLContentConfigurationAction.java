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

package com.liferay.xsl.content.web.internal.portlet.action;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.xsl.content.web.internal.configuration.XSLContentConfiguration;
import com.liferay.xsl.content.web.internal.constants.XSLContentPortletKeys;
import com.liferay.xsl.content.web.internal.util.XSLContentUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Samuel Kong
 */
@Component(
	configurationPid = "com.liferay.xsl.content.web.internal.configuration.XSLContentConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + XSLContentPortletKeys.XSL_CONTENT,
		"valid.url.prefixes=@portlet_context_url@"
	},
	service = ConfigurationAction.class
)
public class XSLContentConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			XSLContentConfiguration.class.getName(), _xslContentConfiguration);

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateUrls(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.xsl.content.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_xslContentConfiguration = ConfigurableUtil.createConfigurable(
			XSLContentConfiguration.class, properties);
	}

	protected String[] getValidUrlPrefixes(
		ThemeDisplay themeDisplay, String contextPath) {

		String validUrlPrefixes = XSLContentUtil.replaceUrlTokens(
			themeDisplay, contextPath,
			_xslContentConfiguration.validUrlPrefixes());

		return StringUtil.split(validUrlPrefixes);
	}

	protected boolean hasValidUrlPrefix(String[] validUrlPrefixes, String url) {
		if (validUrlPrefixes.length == 0) {
			return true;
		}

		for (String validUrlPrefix : validUrlPrefixes) {
			if (StringUtil.startsWith(url, validUrlPrefix)) {
				return true;
			}
		}

		return false;
	}

	protected void validateUrls(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] validUrlPrefixes = getValidUrlPrefixes(
			themeDisplay, actionRequest.getContextPath());

		String xmlUrl = getParameter(actionRequest, "xmlUrl");

		xmlUrl = XSLContentUtil.replaceUrlTokens(
			themeDisplay, actionRequest.getContextPath(), xmlUrl);

		if (!hasValidUrlPrefix(validUrlPrefixes, xmlUrl)) {
			SessionErrors.add(actionRequest, "xmlUrl");
		}

		String xslUrl = getParameter(actionRequest, "xslUrl");

		xslUrl = XSLContentUtil.replaceUrlTokens(
			themeDisplay, actionRequest.getContextPath(), xslUrl);

		if (!hasValidUrlPrefix(validUrlPrefixes, xslUrl)) {
			SessionErrors.add(actionRequest, "xslUrl");
		}
	}

	private volatile XSLContentConfiguration _xslContentConfiguration;

}