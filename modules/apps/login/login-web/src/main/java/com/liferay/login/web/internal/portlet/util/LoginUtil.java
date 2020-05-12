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

import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.petra.content.ContentUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

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

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class LoginUtil {

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName, boolean showPasswordTerms) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = LinkedHashMapBuilder.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress)
		).put(
			"[$FROM_NAME$]", HtmlUtil.escape(emailFromName)
		).build();

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

	public static String getEmailTemplateXML(
		PortletPreferences portletPreferences, PortletRequest portletRequest,
		long companyId, String portletPreferencesTemplateKey,
		String companyPortletPreferencesTemplateKey,
		String portalPropertiesTemplateKey) {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId, true);

		String xml = LocalizationUtil.getLocalizationXmlFromPreferences(
			portletPreferences, portletRequest, portletPreferencesTemplateKey,
			"preferences", null);

		if (xml == null) {
			String defaultContent = ContentUtil.get(
				PortalClassLoaderUtil.getClassLoader(),
				PropsUtil.get(portalPropertiesTemplateKey));

			xml = LocalizationUtil.getLocalizationXmlFromPreferences(
				companyPortletPreferences, portletRequest,
				companyPortletPreferencesTemplateKey, "settings",
				defaultContent);
		}

		return xml;
	}

	public static String getLogin(
		HttpServletRequest httpServletRequest, String paramName,
		Company company) {

		String login = httpServletRequest.getParameter(paramName);

		if ((login == null) || login.equals(StringPool.NULL)) {
			login = CookieKeys.getCookie(
				httpServletRequest, CookieKeys.LOGIN, false);

			String authType = company.getAuthType();

			if (PropsValues.COMPANY_LOGIN_PREPOPULATE_DOMAIN &&
				Validator.isNull(login) &&
				authType.equals(CompanyConstants.AUTH_TYPE_EA)) {

				login = "@".concat(company.getMx());
			}
		}

		return login;
	}

	public static PortletURL getLoginURL(
			HttpServletRequest httpServletRequest, long plid)
		throws PortletModeException, WindowStateException {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, LoginPortletKeys.LOGIN, plid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		portletURL.setParameter("mvcRenderCommandName", "/login/login");
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(WindowState.MAXIMIZED);

		return portletURL;
	}

	public static void sendPassword(
			ActionRequest actionRequest, String fromName, String fromAddress,
			String toAddress, String subject, String body)
		throws Exception {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(actionRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isSendPasswordResetLink()) {
			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		UserLocalServiceUtil.sendPassword(
			company.getCompanyId(), toAddress, fromName, fromAddress, subject,
			body, serviceContext);
	}

}