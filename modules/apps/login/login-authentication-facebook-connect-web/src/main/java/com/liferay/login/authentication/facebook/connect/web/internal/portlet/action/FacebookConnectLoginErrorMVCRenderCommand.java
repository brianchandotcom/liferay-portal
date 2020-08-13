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

package com.liferay.login.authentication.facebook.connect.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.facebook.connect.exception.MustVerifyEmailAddressException;
import com.liferay.portal.security.sso.facebook.connect.exception.StrangersNotAllowedException;
import com.liferay.portal.security.sso.facebook.connect.exception.UnknownErrorException;

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
 * @author Stian Sigvartsen
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + PortletKeys.LOGIN,
		"mvc.command.name=/login/facebook_connect_login_error"
	},
	service = MVCRenderCommand.class
)
@Deprecated
public class FacebookConnectLoginErrorMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_facebookConnect.isEnabled(themeDisplay.getCompanyId())) {
			throw new PortletException(
				new PrincipalException.MustBeEnabled(
					themeDisplay.getCompanyId(),
					FacebookConnect.class.getName()));
		}

		String error = ParamUtil.getString(renderRequest, "error");

		if (ArrayUtil.contains(_ERRORS, error)) {
			SessionErrors.add(renderRequest, error);
		}
		else {
			SessionErrors.add(
				renderRequest, UnknownErrorException.class.getSimpleName());
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/error.jsp");

			requestDispatcher.forward(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new PortletException(
				"Unable to include error.jsp", exception);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	private static final String[] _ERRORS = {
		UserEmailAddressException.MustNotUseCompanyMx.class.getSimpleName(),
		MustVerifyEmailAddressException.class.getSimpleName(),
		StrangersNotAllowedException.class.getSimpleName()
	};

	@Reference
	private FacebookConnect _facebookConnect;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.login.authentication.facebook.connect.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}