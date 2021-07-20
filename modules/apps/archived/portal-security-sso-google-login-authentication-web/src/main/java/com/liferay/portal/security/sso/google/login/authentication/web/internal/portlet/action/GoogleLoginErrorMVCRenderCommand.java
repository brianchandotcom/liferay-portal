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

package com.liferay.portal.security.sso.google.login.authentication.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.google.GoogleAuthorization;
import com.liferay.portal.security.sso.google.exception.StrangersNotAllowedException;
import com.liferay.portal.security.sso.google.login.authentication.web.internal.struts.GoogleLoginStrutsAction;

import java.util.Map;

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
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + PortletKeys.LOGIN,
		"mvc.command.name=/portal_security_sso_google_login_authentication/google_login_error"
	},
	service = MVCRenderCommand.class
)
public class GoogleLoginErrorMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean googleAuthEnabled = _googleAuthorization.isEnabled(
			themeDisplay.getCompanyId());

		if (!googleAuthEnabled) {
			throw new PortletException(
				new PrincipalException.MustBeEnabled(
					themeDisplay.getCompanyId(),
					GoogleLoginStrutsAction.class.getName()));
		}

		String error = ParamUtil.getString(renderRequest, "error");

		Class<? extends Throwable> errorClass = _errorClasses.get(error);

		if (errorClass != null) {
			SessionErrors.add(renderRequest, errorClass);
		}
		else {
			SessionErrors.add(renderRequest, Exception.class);
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

	private static final Map<String, Class<? extends Throwable>> _errorClasses =
		HashMapBuilder.<String, Class<? extends Throwable>>put(
			StrangersNotAllowedException.class.getSimpleName(),
			StrangersNotAllowedException.class
		).put(
			UserEmailAddressException.MustNotUseCompanyMx.class.getSimpleName(),
			UserEmailAddressException.MustNotUseCompanyMx.class
		).build();

	@Reference
	private GoogleAuthorization _googleAuthorization;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.security.sso.google.login.authentication.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}