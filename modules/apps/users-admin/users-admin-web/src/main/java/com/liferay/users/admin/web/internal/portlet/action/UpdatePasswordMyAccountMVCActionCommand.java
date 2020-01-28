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

package com.liferay.users.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.UserLockoutException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.security.auth.PasswordModificationThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
		"mvc.command.name=/users_admin/update_password"
	},
	service = MVCActionCommand.class
)
public class UpdatePasswordMyAccountMVCActionCommand
	extends BaseMVCActionCommand {

	protected void authenticateUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String currentPassword = actionRequest.getParameter("password0");
		String newPassword = actionRequest.getParameter("password1");

		User user = _portal.getSelectedUser(actionRequest);

		if (Validator.isNotNull(currentPassword)) {
			if (Validator.isNull(newPassword)) {
				throw new UserPasswordException.MustNotBeNull(user.getUserId());
			}

			Company company = _portal.getCompany(actionRequest);

			String authType = company.getAuthType();

			Map<String, String[]> headerMap = new HashMap<>();
			Map<String, String[]> parameterMap = new HashMap<>();
			Map<String, Object> resultsMap = new HashMap<>();

			int authResult = Authenticator.FAILURE;

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				authResult = _userLocalService.authenticateByEmailAddress(
					company.getCompanyId(), user.getEmailAddress(),
					currentPassword, headerMap, parameterMap, resultsMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				authResult = _userLocalService.authenticateByUserId(
					company.getCompanyId(), user.getUserId(), currentPassword,
					headerMap, parameterMap, resultsMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				authResult = _userLocalService.authenticateByScreenName(
					company.getCompanyId(), user.getScreenName(),
					currentPassword, headerMap, parameterMap, resultsMap);
			}

			if (authResult == Authenticator.FAILURE) {
				user = _portal.getSelectedUser(actionRequest);

				if (user.isLockout()) {
					HttpServletRequest originalHttpServletRequest =
						_portal.getOriginalServletRequest(
							_portal.getHttpServletRequest(actionRequest));
					HttpServletResponse httpServletResponse =
						_portal.getHttpServletResponse(actionResponse);

					AuthenticatedSessionManagerUtil.logout(
						originalHttpServletRequest, httpServletResponse);

					if (StringUtil.equals(
							originalHttpServletRequest.getMethod(),
							HttpMethods.GET)) {

						httpServletResponse.sendRedirect(
							_portal.getCurrentCompleteURL(
								originalHttpServletRequest));
					}
					else {
						httpServletResponse.sendRedirect(
							_portal.getPortalURL(originalHttpServletRequest));
					}

					throw new UserLockoutException.PasswordPolicyLockout(
						user, user.getPasswordPolicy());
				}

				throw new UserPasswordException.MustMatchCurrentPassword(
					user.getUserId());
			}
		}
		else if (Validator.isNotNull(newPassword)) {
			throw new UserPasswordException.MustNotBeNull(user.getUserId());
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PasswordModificationThreadLocal.setPasswordModified(true);

		try {
			authenticateUser(actionRequest, actionResponse);

			_mvcActionCommand.processAction(actionRequest, actionResponse);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchUserException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof UserPasswordException) {
				SessionErrors.add(
					actionRequest, exception.getClass(), exception);

				String redirect = _portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
			else {
				throw exception;
			}
		}
	}

	@Reference(
		target = "(component.name=com.liferay.users.admin.web.internal.portlet.action.UpdatePasswordMVCActionCommand)"
	)
	private MVCActionCommand _mvcActionCommand;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}