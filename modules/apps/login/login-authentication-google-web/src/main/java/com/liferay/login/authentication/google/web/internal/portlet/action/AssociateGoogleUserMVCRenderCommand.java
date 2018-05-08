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

package com.liferay.login.authentication.google.web.internal.portlet.action;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
		"mvc.command.name=/login/associate_google_user"
	},
	service = MVCRenderCommand.class
)
public class AssociateGoogleUserMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean googleAuthEnabled = PrefsPropsUtil.getBoolean(
			themeDisplay.getCompanyId(), "google-auth-enabled", true);

		if (!googleAuthEnabled) {
			throw new PortletException(
				new PrincipalException.MustBeEnabled(
					themeDisplay.getCompanyId(),
					GoogleLoginAction.class.getName()));
		}

		long googleIncompleteUserId = ParamUtil.getLong(
			renderRequest, "userId");

		if (googleIncompleteUserId != 0) {
			User user = _userLocalService.fetchUser(googleIncompleteUserId);

			return renderUpdateAccount(renderRequest, user);
		}

		// This return statement may be used if the user presses the browser's
		// back button

		return "/login.jsp";
	}

	protected String renderUpdateAccount(
			PortletRequest portletRequest, User user)
		throws PortletException {

		portletRequest.setAttribute("selUser", user);

		return "/update_account.jsp";
	}

	@Reference
	private UserLocalService _userLocalService;

}