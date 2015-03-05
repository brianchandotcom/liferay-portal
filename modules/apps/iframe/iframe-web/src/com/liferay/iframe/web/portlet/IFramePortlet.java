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

package com.liferay.iframe.web.portlet;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.iframe.web.configuration.IFrameConfiguration;
import com.liferay.iframe.web.constants.IFrameWebKeys;
import com.liferay.iframe.web.upgrade.IFrameWebUpgrade;
import com.liferay.iframe.web.util.IFrameUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.iframe.web.configuration.IFrameConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-iframe",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=IFrame", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class IFramePortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			IFrameConfiguration.class.getName(), _iFrameConfiguration);

		String src = null;

		try {
			src = transformSrc(renderRequest, renderResponse);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		renderRequest.setAttribute(IFrameWebKeys.IFRAME_SRC, src);

		if (Validator.isNull(src) || src.equals(Http.HTTP_WITH_SLASH) ||
			src.equals(Http.HTTPS_WITH_SLASH)) {

			include("/portlet_not_setup.jsp", renderRequest, renderResponse);
		}
		else {
			super.doView(renderRequest, renderResponse);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_iFrameConfiguration = Configurable.createConfigurable(
			IFrameConfiguration.class, properties);
	}

	protected String getPassword(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String password = portletPreferences.getValue(
			"basicPassword", StringPool.BLANK);

		return IFrameUtil.getPassword(renderRequest, password);
	}

	protected String getSrc(
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

	@Reference(unbind = "-")
	protected void setIFrameWebUpgrade(IFrameWebUpgrade iFrameWebUpgrade) {
	}

	protected String transformSrc(
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

			src =
				"/proxy.jsp?p_l_id=" + themeDisplay.getPlid() + "&p_p_id=" +
					portletId;
		}

		return src;
	}

	private static final Log _log = LogFactoryUtil.getLog(IFramePortlet.class);

	private volatile IFrameConfiguration _iFrameConfiguration;

}