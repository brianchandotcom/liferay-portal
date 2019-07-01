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

package com.liferay.iframe.web.internal.portlet.action;

import com.liferay.iframe.web.internal.constants.IFramePortletKeys;
import com.liferay.iframe.web.internal.util.IFrameUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + IFramePortletKeys.IFRAME,
	service = ConfigurationAction.class
)
public class IFrameConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String src = getParameter(actionRequest, "src");

		if (!src.startsWith("/") && !StringUtil.startsWith(src, "http://") &&
			!StringUtil.startsWith(src, "https://") &&
			!StringUtil.startsWith(src, "mhtml://")) {

			src = _http.getProtocol(actionRequest) + "://" + src;

			setPreference(actionRequest, "src", src);
		}

		String[] htmlAttributes = StringUtil.splitLines(
			getParameter(actionRequest, "htmlAttributes"));

		for (String htmlAttribute : htmlAttributes) {
			int pos = htmlAttribute.indexOf(CharPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			String key = htmlAttribute.substring(0, pos);
			String value = htmlAttribute.substring(pos + 1);

			setPreference(actionRequest, key, value);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.iframe.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void postProcess(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws PortalException {

		String formPassword = portletPreferences.getValue(
			"formPassword", StringPool.BLANK);

		if (Validator.isNotNull(formPassword) &&
			formPassword.contains("@password@") &&
			!IFrameUtil.isPasswordTokenEnabled(portletRequest)) {

			formPassword = formPassword.replaceAll("@password@", "");

			try {
				portletPreferences.setValue("formPassword", formPassword);
			}
			catch (ReadOnlyException roe) {
				throw new PortalException(roe);
			}
		}
	}

	@Reference
	private Http _http;

}