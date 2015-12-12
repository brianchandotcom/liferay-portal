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

package com.liferay.portal.security.sso.openid.internal.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Enables the Sign In portlet to render a prompt for users to enter their
 * OpenIDs.
 * 
 * @author Michael C. Han
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + PortletKeys.LOGIN,
		"mvc.command.name=/login/openid"
	},
	service = MVCRenderCommand.class
)
public class OpenIdLoginMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(renderResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_openId.isEnabled(themeDisplay.getCompanyId()) ||
			themeDisplay.isSignedIn()) {

			return "/login.jsp";
		}

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(_JSP_PATH);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			_log.error("Unable to include JSP " + _JSP_PATH, e);

			throw new PortletException("Unable to include JSP " + _JSP_PATH, e);
		}

		return "/navigation.jsp";
	}

	@Reference(unbind = "-")
	protected void setOpenId(OpenId openId) {
		_openId = openId;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.security.sso.openid)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final String _JSP_PATH = "/com.liferay.login.web/openid.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdLoginMVCRenderCommand.class);

	private volatile OpenId _openId;
	private volatile ServletContext _servletContext;

}