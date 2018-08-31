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

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManager;
import com.liferay.portal.kernel.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;
import com.liferay.users.admin.kernel.util.UsersAdmin;

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
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ORGANIZATIONS,
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/update_password"
	},
	service = MVCActionCommand.class
)
public class UpdatePasswordMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			User user = _portal.getSelectedUser(actionRequest);

			String newPassword1 = actionRequest.getParameter("password1");
			String newPassword2 = actionRequest.getParameter("password2");

			boolean passwordReset = ParamUtil.getBoolean(
				actionRequest, "passwordReset");

			PasswordPolicy passwordPolicy = user.getPasswordPolicy();

			boolean ldapPasswordPolicyEnabled =
				LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId());

			if ((user.getLastLoginDate() == null) &&
				(((passwordPolicy == null) && !ldapPasswordPolicyEnabled) ||
				 ((passwordPolicy != null) && passwordPolicy.isChangeable() &&
				  passwordPolicy.isChangeRequired()))) {

				passwordReset = true;
			}

			String reminderQueryQuestion = BeanParamUtil.getString(
				user, actionRequest, "reminderQueryQuestion");

			if (reminderQueryQuestion.equals(UsersAdmin.CUSTOM_QUESTION)) {
				reminderQueryQuestion = BeanParamUtil.getStringSilent(
					user, actionRequest, "reminderQueryCustomQuestion");
			}

			String reminderQueryAnswer = BeanParamUtil.getString(
				user, actionRequest, "reminderQueryAnswer");

			boolean passwordChanged = false;

			if (Validator.isNotNull(newPassword1) ||
				Validator.isNotNull(newPassword2)) {

				_userLocalService.updatePassword(
					user.getUserId(), newPassword1, newPassword2,
					passwordReset);

				passwordChanged = true;
			}

			_userLocalService.updatePasswordReset(
				user.getUserId(), passwordReset);

			if (Validator.isNotNull(reminderQueryQuestion) &&
				Validator.isNotNull(reminderQueryAnswer)) {

				_userLocalService.updateReminderQuery(
					user.getUserId(), reminderQueryQuestion,
					reminderQueryAnswer);
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			if ((user.getUserId() == themeDisplay.getUserId()) &&
				passwordChanged) {

				String login = null;

				Company company = themeDisplay.getCompany();

				String authType = company.getAuthType();

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					login = user.getEmailAddress();
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					login = user.getScreenName();
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
					login = String.valueOf(user.getUserId());
				}

				HttpServletRequest originalRequest =
					_portal.getOriginalServletRequest(
						_portal.getHttpServletRequest(actionRequest));

				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(actionResponse);

				_authenticatedSessionManager.login(
					originalRequest, httpServletResponse, login, newPassword1,
					false, null);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof UserPasswordException) {
				SessionErrors.add(actionRequest, e.getClass(), e);

				String redirect = _portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
			else {
				throw e;
			}
		}
	}

	@Reference
	private AuthenticatedSessionManager _authenticatedSessionManager;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}