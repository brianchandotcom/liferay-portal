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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.xslcontent.util.XSLContentUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Samuel Kong
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

	protected String[] getValidUrlPrefixes(ThemeDisplay themeDisplay) {
		String validUrlPrefixes = PropsUtil.get(
			PropsKeys.XSL_CONTENT_VALID_URL_PREFIXES);

		validUrlPrefixes = XSLContentUtil.replaceUrlTokens(
			themeDisplay, validUrlPrefixes);

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

		String[] validUrlPrefixes = getValidUrlPrefixes(themeDisplay);

		String xmlUrl = getParameter(actionRequest, "xmlUrl");

		xmlUrl = XSLContentUtil.replaceUrlTokens(themeDisplay, xmlUrl);

		if (!hasValidUrlPrefix(validUrlPrefixes, xmlUrl)) {
			SessionErrors.add(actionRequest, "xmlUrl");
		}

		String xslUrl = getParameter(actionRequest, "xslUrl");

		xslUrl = XSLContentUtil.replaceUrlTokens(themeDisplay, xslUrl);

		if (!hasValidUrlPrefix(validUrlPrefixes, xslUrl)) {
			SessionErrors.add(actionRequest, "xslUrl");
		}
	}

}