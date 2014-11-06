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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.xsl.content.web.configuration.XSLContentConfiguration;
import com.liferay.xsl.content.web.constants.XSLContentPortletKeys;
import com.liferay.xsl.content.web.util.XSLContentUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Samuel Kong
 */
@Component(
	configurationPid = "com.liferay.xsl.content.web", immediate = true,
	property = {
		"javax.portlet.name=" + XSLContentPortletKeys.XSL_CONTENT
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

	@Override
	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		renderRequest.setAttribute(
			XSLContentConfiguration.class.getName(), _xslContentConfiguration);

		return super.render(portletConfig, renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_xslContentConfiguration = Configurable.createConfigurable(
			XSLContentConfiguration.class, properties);
	}

	protected String getPortletContextUrl(ThemeDisplay themeDisplay) {
		Bundle bundle = FrameworkUtil.getBundle(XSLContentUtil.class);

		StringBundler sb = new StringBundler(3);

		sb.append(themeDisplay.getPortalURL());
		sb.append("/o/");
		sb.append(bundle.getBundleId());

		return sb.toString();
	}

	protected String[] getValidUrlPrefixes(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		Portlet xslPortlet = PortletLocalServiceUtil.getPortletById(
			portletResource);

		Map initParams = xslPortlet.getInitParams();

		String validUrlPrefixes = (String)initParams.get("valid.url.prefixes");

		validUrlPrefixes = replaceTokens(themeDisplay, validUrlPrefixes);

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

	protected String replaceTokens(ThemeDisplay themeDisplay, String url) {
		return StringUtil.replace(
			url, new String[] {"@portal_url@", "@portlet_context_url@"},
			new String[] {
				themeDisplay.getPortalURL(), getPortletContextUrl(themeDisplay)
			});
	}

	protected void validateUrls(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] validUrlPrefixes = getValidUrlPrefixes(actionRequest);

		String xmlUrl = getParameter(actionRequest, "xmlUrl");

		xmlUrl = replaceTokens(themeDisplay, xmlUrl);

		if (!hasValidUrlPrefix(validUrlPrefixes, xmlUrl)) {
			SessionErrors.add(actionRequest, "xmlUrl");
		}

		String xslUrl = getParameter(actionRequest, "xslUrl");

		xslUrl = replaceTokens(themeDisplay, xslUrl);

		if (!hasValidUrlPrefix(validUrlPrefixes, xslUrl)) {
			SessionErrors.add(actionRequest, "xslUrl");
		}
	}

	private volatile XSLContentConfiguration _xslContentConfiguration;

}