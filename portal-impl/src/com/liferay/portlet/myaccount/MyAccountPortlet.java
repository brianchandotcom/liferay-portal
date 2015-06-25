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

package com.liferay.portlet.myaccount;

import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.pwd.PwdAuthenticator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.usersadmin.UsersAdminPortlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class MyAccountPortlet extends UsersAdminPortlet {

	@Override
	public void editUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		if (redirectToLogin(actionRequest, actionResponse)) {
			return;
		}

		String currentPassword = actionRequest.getParameter("password0");
		String newPassword = actionRequest.getParameter("password1");

		User user = PortalUtil.getSelectedUser(actionRequest);

		if (Validator.isNotNull(currentPassword)) {
			if (Validator.isNull(newPassword)) {
				throw new UserPasswordException.MustNotBeNull(user.getUserId());
			}

			Company company = PortalUtil.getCompany(actionRequest);

			String authType = company.getAuthType();

			String login = null;

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				login = user.getEmailAddress();
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				login = String.valueOf(user.getUserId());
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				login = user.getScreenName();
			}

			boolean validPassword = PwdAuthenticator.authenticate(
				login, currentPassword, user.getPassword());

			if (!validPassword) {
				throw new UserPasswordException.MustMatchCurrentPassword(
					user.getUserId());
			}
		}
		else if (Validator.isNotNull(newPassword)) {
			throw new UserPasswordException.MustNotBeNull(user.getUserId());
		}

		super.editUser(actionRequest, actionResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if ((renderRequest.getRemoteUser() == null) ||
			!renderRequest.getWindowState().equals(WindowState.MAXIMIZED)) {

			super.render(renderRequest, renderResponse);

			return;
		}

		try {
			User user = PortalUtil.getUser(renderRequest);

			RenderRequestImpl renderRequestImpl =
				(RenderRequestImpl)renderRequest;

			DynamicServletRequest dynamicRequest =
				(DynamicServletRequest)
					renderRequestImpl.getHttpServletRequest();

			dynamicRequest.setParameter(
				"p_u_i_d", String.valueOf(user.getUserId()));

			include(
				"/html/portlet/my_account/edit_user.jsp", renderRequest,
				renderResponse);
		}
		catch (PortalException pe) {
			super.render(renderRequest, renderResponse);
		}
	}

	protected boolean redirectToLogin(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		if (actionRequest.getRemoteUser() == null) {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);

			SessionErrors.add(request, PrincipalException.class.getName());

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			actionResponse.sendRedirect(themeDisplay.getURLSignIn());

			return true;
		}

		return false;
	}

}