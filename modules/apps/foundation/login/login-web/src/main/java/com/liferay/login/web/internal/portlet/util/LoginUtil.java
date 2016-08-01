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

package com.liferay.login.web.internal.portlet.util;

import com.liferay.login.web.internal.constants.LoginPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class LoginUtil {

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AuthenticatedSessionManagerUtil#getAuthenticatedUserId(
	 *             HttpServletRequest, String, String, String)}
	 */
	@Deprecated
	public static long getAuthenticatedUserId(
			HttpServletRequest request, String login, String password,
			String authType)
		throws PortalException {

		return AuthenticatedSessionManagerUtil.getAuthenticatedUserId(
			request, login, password, authType);
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName, boolean showPasswordTerms) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));

		if (showPasswordTerms) {
			definitionTerms.put(
				"[$PASSWORD_RESET_URL$]",
				LanguageUtil.get(
					themeDisplay.getLocale(), "the-password-reset-url"));
		}

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$REMOTE_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-browser's-remote-address"));
		definitionTerms.put(
			"[$REMOTE_HOST$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-browser's-remote-host"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));
		definitionTerms.put(
			"[$USER_ID$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-user-id"));

		if (showPasswordTerms) {
			definitionTerms.put(
				"[$USER_PASSWORD$]",
				LanguageUtil.get(
					themeDisplay.getLocale(), "the-user-password"));
		}

		definitionTerms.put(
			"[$USER_SCREENNAME$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-user-screen-name"));

		return definitionTerms;
	}

	public static String getEmailFromAddress(
		PortletPreferences preferences, long companyId) {

		return PortalUtil.getEmailFromAddress(
			preferences, companyId, PropsValues.LOGIN_EMAIL_FROM_ADDRESS);
	}

	public static String getEmailFromName(
		PortletPreferences preferences, long companyId) {

		return PortalUtil.getEmailFromName(
			preferences, companyId, PropsValues.LOGIN_EMAIL_FROM_NAME);
	}

	public static String getLogin(
		HttpServletRequest request, String paramName, Company company) {

		String login = request.getParameter(paramName);

		if ((login == null) || login.equals(StringPool.NULL)) {
			login = GetterUtil.getString(
				CookieKeys.getCookie(request, CookieKeys.LOGIN, false));

			if (PropsValues.COMPANY_LOGIN_PREPOPULATE_DOMAIN &&
				Validator.isNull(login) &&
				company.getAuthType().equals(CompanyConstants.AUTH_TYPE_EA)) {

				login = "@" + company.getMx();
			}
		}

		return login;
	}

	public static PortletURL getLoginURL(HttpServletRequest request, long plid)
		throws PortletModeException, WindowStateException {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, LoginPortletKeys.LOGIN, plid, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		portletURL.setParameter("mvcRenderCommandName", "/login/login");
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(WindowState.MAXIMIZED);

		return portletURL;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AuthenticatedSessionManagerUtil#login(HttpServletRequest,
	 *             HttpServletResponse, String, String, boolean, String)}
	 */
	@Deprecated
	public static void login(
			HttpServletRequest request, HttpServletResponse response,
			String login, String password, boolean rememberMe, String authType)
		throws Exception {

		AuthenticatedSessionManagerUtil.login(
			request, response, login, password, rememberMe, authType);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AuthenticatedSessionManagerUtil#renewSession(
	 *             HttpServletRequest, HttpSession)}
	 */
	@Deprecated
	public static HttpSession renewSession(
			HttpServletRequest request, HttpSession session)
		throws Exception {

		return AuthenticatedSessionManagerUtil.renewSession(request, session);
	}

	public static void sendPassword(ActionRequest actionRequest)
		throws Exception {

		String toAddress = ParamUtil.getString(actionRequest, "emailAddress");

		sendPassword(actionRequest, null, null, toAddress, null, null);
	}

	public static void sendPassword(
			ActionRequest actionRequest, String fromName, String fromAddress,
			String toAddress, String subject, String body)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isSendPassword() && !company.isSendPasswordResetLink()) {
			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		UserLocalServiceUtil.sendPassword(
			company.getCompanyId(), toAddress, fromName, fromAddress, subject,
			body, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AuthenticatedSessionManagerUtil#signOutSimultaneousLogins(
	 *             long)}
	 */
	@Deprecated
	public static void signOutSimultaneousLogins(long userId) throws Exception {
		AuthenticatedSessionManagerUtil.signOutSimultaneousLogins(userId);
	}

}