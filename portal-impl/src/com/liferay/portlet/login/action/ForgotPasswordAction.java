/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.login.action;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.RequiredReminderQueryException;
import com.liferay.portal.SendPasswordException;
import com.liferay.portal.UserActiveException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserReminderQueryException;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.login.util.LoginUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Tibor Kovacs
 */
public class ForgotPasswordAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isSendPassword() && !company.isSendPasswordResetLink()) {
			throw new PrincipalException();
		}

		try {
			if (PropsValues.USERS_REMINDER_QUERIES_ENABLED) {
				checkReminderQueries(actionRequest, actionResponse);
			}
			else {
				checkCaptcha(actionRequest);

				sendPassword(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof CaptchaTextException ||
				e instanceof NoSuchUserException ||
				e instanceof RequiredReminderQueryException ||
				e instanceof SendPasswordException ||
				e instanceof UserActiveException ||
				e instanceof UserEmailAddressException ||
				e instanceof UserReminderQueryException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isSendPassword() && !company.isSendPasswordResetLink()) {
			return actionMapping.findForward("portlet.login.login");
		}

		renderResponse.setTitle(themeDisplay.translate("forgot-password"));

		return actionMapping.findForward("portlet.login.forgot_password");
	}

	protected void checkCaptcha(ActionRequest actionRequest)
		throws CaptchaException {

		if (PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD) {
			CaptchaUtil.check(actionRequest);
		}
	}

	protected void checkReminderQueries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletSession portletSession = actionRequest.getPortletSession();

		int step = ParamUtil.getInteger(actionRequest, "step");

		if (step == 1) {
			checkCaptcha(actionRequest);

			portletSession.removeAttribute(
				WebKeys.FORGOT_PASSWORD_REMINDER_ATTEMPTS);
			portletSession.removeAttribute(
				WebKeys.FORGOT_PASSWORD_REMINDER_USER_EMAIL_ADDRESS);
		}

		User user = getUser(actionRequest);

		portletSession.setAttribute(
			WebKeys.FORGOT_PASSWORD_REMINDER_USER_EMAIL_ADDRESS,
			user.getEmailAddress());

		actionRequest.setAttribute(WebKeys.FORGOT_PASSWORD_REMINDER_USER, user);

		if (step == 2) {
			Integer reminderAttempts = (Integer)portletSession.getAttribute(
				WebKeys.FORGOT_PASSWORD_REMINDER_ATTEMPTS);

			if (reminderAttempts == null) {
				reminderAttempts = 0;
			}
			else if (reminderAttempts > 2) {
				checkCaptcha(actionRequest);
			}

			reminderAttempts++;

			portletSession.setAttribute(
				WebKeys.FORGOT_PASSWORD_REMINDER_ATTEMPTS, reminderAttempts);

			sendPassword(actionRequest, actionResponse);
		}
	}

	protected User getUser(ActionRequest actionRequest) throws Exception {
		PortletSession portletSession = actionRequest.getPortletSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String sessionEmailAddress = (String)portletSession.getAttribute(
			WebKeys.FORGOT_PASSWORD_REMINDER_USER_EMAIL_ADDRESS);

		User user = null;

		if (Validator.isNotNull(sessionEmailAddress)) {
			user = UserLocalServiceUtil.getUserByEmailAddress(
				themeDisplay.getCompanyId(), sessionEmailAddress);
		}
		else {
			long userId = ParamUtil.getLong(actionRequest, "userId");
			String screenName = ParamUtil.getString(
				actionRequest, "screenName");
			String emailAddress = ParamUtil.getString(
				actionRequest, "emailAddress");

			if (Validator.isNotNull(emailAddress)) {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);
			}
			else if (Validator.isNotNull(screenName)) {
				user = UserLocalServiceUtil.getUserByScreenName(
					themeDisplay.getCompanyId(), screenName);
			}
			else if (userId > 0) {
				user = UserLocalServiceUtil.getUserById(userId);
			}
			else {
				throw new NoSuchUserException();
			}
		}

		if (!user.isActive()) {
			throw new UserActiveException();
		}

		return user;
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void sendPassword(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		User user = getUser(actionRequest);

		if (PropsValues.USERS_REMINDER_QUERIES_ENABLED) {
			if (PropsValues.USERS_REMINDER_QUERIES_REQUIRED &&
				!user.hasReminderQuery()) {

				throw new RequiredReminderQueryException(
					"No reminder query or answer is defined for user " +
						user.getUserId());
			}

			String answer = ParamUtil.getString(actionRequest, "answer");

			if (!user.getReminderQueryAnswer().equals(answer)) {
				throw new UserReminderQueryException();
			}
		}

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		String languageId = LanguageUtil.getLanguageId(actionRequest);

		String emailFromName = portletPreferences.getValue(
			"emailFromName", null);
		String emailFromAddress = portletPreferences.getValue(
			"emailFromAddress", null);
		String emailToAddress = user.getEmailAddress();

		String emailParam = "emailPasswordSent";

		if (company.isSendPasswordResetLink()) {
			emailParam = "emailPasswordReset";
		}

		String subject = portletPreferences.getValue(
			emailParam + "Subject_" + languageId, null);
		String body = portletPreferences.getValue(
			emailParam + "Body_" + languageId, null);

		LoginUtil.sendPassword(
			actionRequest, emailFromName, emailFromAddress, emailToAddress,
			subject, body);

		sendRedirect(actionRequest, actionResponse);
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}