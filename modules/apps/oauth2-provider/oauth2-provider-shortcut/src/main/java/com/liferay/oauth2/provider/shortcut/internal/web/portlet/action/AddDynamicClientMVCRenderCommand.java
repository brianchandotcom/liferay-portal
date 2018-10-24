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

package com.liferay.oauth2.provider.shortcut.internal.web.portlet.action;

import com.liferay.oauth2.provider.shortcut.internal.endpoint.register.DynamicClientRegistrationTokenService;
import com.liferay.oauth2.provider.shortcut.internal.web.portlet.constants.OAuth2AdminPortletWebKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.security.SignatureException;

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
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"javax.portlet.name=com_liferay_oauth2_provider_web_internal_portlet_OAuth2AdminPortlet",
		"mvc.command.name=/admin/add_dynamic_client"
	},
	service = MVCRenderCommand.class
)
public class AddDynamicClientMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		DynamicClientRegistrationTokenService.RegistrationToken
			registrationToken =
				new DynamicClientRegistrationTokenService.RegistrationToken();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		registrationToken.setUserId(themeDisplay.getUserId());

		try {
			renderRequest.setAttribute(
				OAuth2AdminPortletWebKeys.REGISTRATION_TOKEN,
				_dynamicClientRegistrationTokenService.toJWT(
					registrationToken));
		}
		catch (SignatureException se) {
			_log.error(
				"Unable to generate token string:" + se.getMessage(), se);

			throw new PortletException(se);
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(_JSP_PATH);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			_log.error("Unable to include JSP " + _JSP_PATH, e);

			throw new PortletException("Unable to include JSP " + _JSP_PATH, e);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	private static final String _JSP_PATH =
		"/com.liferay.oauth2.provider.web/add_dynamic_client.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		AddDynamicClientMVCRenderCommand.class);

	@Reference
	private DynamicClientRegistrationTokenService
		_dynamicClientRegistrationTokenService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.oauth2.provider.shortcut)"
	)
	private ServletContext _servletContext;

}