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

package com.liferay.portal.security.sso.opensso.auto.login;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.security.sso.OpenSSO;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.exportimport.UserImporterUtil;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConstants;
import com.liferay.portal.security.sso.opensso.module.configuration.OpenSSOConfiguration;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Participates in every unauthenticated HTTP request to Liferay Portal.
 * 
 * <p>
 * This class queries the OpenSSO server for the name of the OpenSSO token
 * cookie and any additional cookies. These are then extracted from the HTTP
 * request and forwarded to the OpenSSO server to validate the user's
 * authentication status.
 * </p>
 * 
 * <p>
 * If the cookies are validated, another request is made to the OpenSSO server
 * to retrieve all the user's attributes. These are mapped to Liferay Portal
 * user attributes using the configured mappings. If Import from LDAP is
 * enabled, then the user is imported and logged in. Otherwise a new user is
 * created and logged in.
 * </p>
 * 
 * @author Brian Wing Shun Chan
 * @author Prashant Dighe
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.opensso.module.configuration.OpenSSOConfiguration",
	immediate = true, service = AutoLogin.class
)
public class OpenSSOAutoLogin extends BaseAutoLogin {

	protected User addUser(
			long companyId, String firstName, String lastName,
			String emailAddress, String screenName, Locale locale)
		throws Exception {

		long creatorUserId = 0;
		boolean autoPassword = false;
		String password1 = PwdGenerator.getPassword();
		String password2 = password1;
		boolean autoScreenName = false;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String middleName = StringPool.BLANK;
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();

		return _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		OpenSSOConfiguration openSSOConfiguration = getOpenSSOConfiguration(
			companyId);

		if (!openSSOConfiguration.enabled()) {
			return null;
		}

		if (!_openSSO.isAuthenticated(
				request, openSSOConfiguration.serviceURL())) {

			return null;
		}

		Map<String, String> nameValues = _openSSO.getAttributes(
			request, openSSOConfiguration.serviceURL());

		String screenName = nameValues.get(
			openSSOConfiguration.screenNameAttr());
		String emailAddress = nameValues.get(
			openSSOConfiguration.emailAddressAttr());
		String firstName = nameValues.get(openSSOConfiguration.firstNameAttr());
		String lastName = nameValues.get(openSSOConfiguration.lastNameAttr());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Validating user information for " + firstName + " " +
					lastName + " with screen name " + screenName +
						" and email address " + emailAddress);
		}

		User user = null;

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE)) {

			user = _userLocalService.fetchUserByEmailAddress(
				companyId, emailAddress);

			if (user != null) {
				screenName = _screenNameGenerator.generate(
					companyId, user.getUserId(), emailAddress);
			}
		}

		if (openSSOConfiguration.importFromLDAP()) {
			try {
				String authType = PrefsPropsUtil.getString(
					companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
					PropsValues.COMPANY_SECURITY_AUTH_TYPE);

				if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					user = UserImporterUtil.importUser(
						companyId, StringPool.BLANK, screenName);
				}
				else {
					user = UserImporterUtil.importUser(
						companyId, emailAddress, StringPool.BLANK);
				}
			}
			catch (SystemException se) {
			}
		}
		else {
			if (Validator.isNull(emailAddress)) {
				return handleException(
					request, response, new Exception("Email address is null"));
			}
		}

		if (user == null) {
			user = _userLocalService.fetchUserByScreenName(
				companyId, screenName);
		}

		if (user == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Locale locale = LocaleUtil.getDefault();

			if (themeDisplay != null) {

				// ThemeDisplay should never be null, but some users complain of
				// this error. Cause is unknown.

				locale = themeDisplay.getLocale();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Adding user " + screenName);
			}

			user = addUser(
				companyId, firstName, lastName, emailAddress, screenName,
				locale);
		}

		String currentURL = PortalUtil.getCurrentURL(request);

		if (currentURL.contains("/portal/login")) {
			String redirect = ParamUtil.getString(request, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = PortalUtil.escapeRedirect(redirect);
			}
			else {
				redirect = PortalUtil.getPathMain();
			}

			request.setAttribute(AutoLogin.AUTO_LOGIN_REDIRECT, redirect);
		}

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	protected OpenSSOConfiguration getOpenSSOConfiguration(long companyId)
		throws Exception {

		return _configurationFactory.getConfiguration(
			OpenSSOConfiguration.class,
			new CompanyServiceSettingsLocator(
				companyId, OpenSSOConstants.SERVICE_NAME));
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	@Reference(unbind = "-")
	protected void setOpenSSO(OpenSSO openSSO) {
		_openSSO = openSSO;
	}

	@Reference(unbind = "-")
	protected void setScreenNameGenerator(
		ScreenNameGenerator screenNameGenerator) {

		_screenNameGenerator = screenNameGenerator;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenSSOAutoLogin.class);

	private volatile ConfigurationFactory _configurationFactory;
	private volatile OpenSSO _openSSO;
	private volatile ScreenNameGenerator _screenNameGenerator;
	private volatile UserLocalService _userLocalService;

}