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

package com.liferay.portal.setup;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.sql.Connection;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

/**
 * @author Manuel de la Peña
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 */
public class SetupWizardUtil {

	public static final String PROPERTIES_FILE_NAME =
		"portal-setup-wizard.properties";

	public static String getDefaultLanguageId() {
		Locale defaultLocale = LocaleUtil.getDefault();

		return LocaleUtil.toLanguageId(defaultLocale);
	}

	public static String getDefaultTimeZoneId() {
		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				PortalInstances.getDefaultCompanyId());

			User defaultUser = company.getDefaultUser();

			return defaultUser.getTimeZoneId();
		}
		catch (Exception e) {
			return PropsValues.COMPANY_DEFAULT_TIME_ZONE;
		}
	}

	public static boolean isDefaultDatabase(
		HttpServletRequest httpServletRequest) {

		boolean hsqldb = ParamUtil.getBoolean(
			httpServletRequest, "defaultDatabase",
			PropsValues.JDBC_DEFAULT_URL.contains("hsqldb"));

		boolean jndi = Validator.isNotNull(PropsValues.JDBC_DEFAULT_JNDI_NAME);

		if (hsqldb && !jndi) {
			return true;
		}

		return false;
	}

	public static void testDatabase(HttpServletRequest httpServletRequest)
		throws Exception {

		String driverClassName = _getParameter(
			httpServletRequest, PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME);
		String url = _getParameter(
			httpServletRequest, PropsKeys.JDBC_DEFAULT_URL, null);
		String userName = _getParameter(
			httpServletRequest, PropsKeys.JDBC_DEFAULT_USERNAME, null);
		String password = _getParameter(
			httpServletRequest, PropsKeys.JDBC_DEFAULT_PASSWORD, null);

		String jndiName = StringPool.BLANK;

		if (Validator.isNotNull(PropsValues.JDBC_DEFAULT_JNDI_NAME)) {
			jndiName = PropsValues.JDBC_DEFAULT_JNDI_NAME;
		}

		_testConnection(driverClassName, url, userName, password, jndiName);
	}

	public static void updateLanguage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		String languageId = ParamUtil.getString(
			httpServletRequest, "companyLocale", getDefaultLanguageId());

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		if (!LanguageUtil.isAvailableLocale(locale)) {
			return;
		}

		String timeZoneId = ParamUtil.getString(
			httpServletRequest, "companyTimeZoneId", getDefaultTimeZoneId());

		CompanyLocalServiceUtil.updateDisplay(
			PortalInstances.getDefaultCompanyId(), languageId, timeZoneId);

		HttpSession session = httpServletRequest.getSession();

		session.setAttribute(WebKeys.LOCALE, locale);
		session.setAttribute(WebKeys.SETUP_WIZARD_DEFAULT_LOCALE, languageId);

		LanguageUtil.updateCookie(
			httpServletRequest, httpServletResponse, locale);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setLanguageId(languageId);
		themeDisplay.setLocale(locale);
	}

	public static void updateSetup(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		UnicodeProperties unicodeProperties = PropertiesParamUtil.getProperties(
			httpServletRequest, _PROPERTIES_PREFIX);

		unicodeProperties.setProperty(
			PropsKeys.LIFERAY_HOME,
			SystemProperties.get(PropsKeys.LIFERAY_HOME));

		boolean databaseConfigured = _isDatabaseConfigured(unicodeProperties);

		_processDatabaseProperties(
			httpServletRequest, unicodeProperties, databaseConfigured);

		_processOtherProperties(httpServletRequest, unicodeProperties);

		updateLanguage(httpServletRequest, httpServletResponse);

		unicodeProperties.put(
			PropsKeys.SETUP_WIZARD_ENABLED, Boolean.FALSE.toString());

		_updateCompany(httpServletRequest, unicodeProperties);

		_updateAdminUser(
			httpServletRequest, httpServletResponse, unicodeProperties);

		HttpSession session = httpServletRequest.getSession();

		session.setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES, unicodeProperties);
		session.setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES_FILE_CREATED,
			_writePropertiesFile(unicodeProperties));
	}

	private static String _getParameter(
		HttpServletRequest httpServletRequest, String name,
		String defaultValue) {

		name = _PROPERTIES_PREFIX.concat(
			name
		).concat(
			StringPool.DOUBLE_DASH
		);

		return ParamUtil.getString(httpServletRequest, name, defaultValue);
	}

	private static String _getUnicodePropertiesStringWithEmptyValue(
		UnicodeProperties unicodeProperties) {

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			if (Validator.isNull(entry.getValue())) {
				unicodeProperties.setProperty(entry.getKey(), _NULL_HOLDER);
			}
		}

		return StringUtil.replace(
			unicodeProperties.toString(), _NULL_HOLDER, StringPool.BLANK);
	}

	private static boolean _isDatabaseConfigured(
		UnicodeProperties unicodeProperties) {

		String defaultDriverClassName = unicodeProperties.get(
			PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME);
		String defaultPassword = unicodeProperties.get(
			PropsKeys.JDBC_DEFAULT_PASSWORD);
		String defaultURL = unicodeProperties.get(PropsKeys.JDBC_DEFAULT_URL);
		String defaultUsername = unicodeProperties.get(
			PropsKeys.JDBC_DEFAULT_USERNAME);

		if (PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME.equals(
				defaultDriverClassName) &&
			PropsValues.JDBC_DEFAULT_PASSWORD.equals(defaultPassword) &&
			PropsValues.JDBC_DEFAULT_URL.equals(defaultURL) &&
			PropsValues.JDBC_DEFAULT_USERNAME.equals(defaultUsername)) {

			return true;
		}

		return false;
	}

	private static void _processDatabaseProperties(
			HttpServletRequest httpServletRequest,
			UnicodeProperties unicodeProperties, boolean databaseConfigured)
		throws Exception {

		boolean defaultDatabase = ParamUtil.getBoolean(
			httpServletRequest, "defaultDatabase", true);

		if (defaultDatabase || databaseConfigured) {
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_URL);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_USERNAME);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_PASSWORD);
		}
	}

	private static void _processOtherProperties(
			HttpServletRequest httpServletRequest,
			UnicodeProperties unicodeProperties)
		throws Exception {

		_processProperty(
			httpServletRequest, unicodeProperties, "adminFirstName",
			PropsKeys.DEFAULT_ADMIN_FIRST_NAME,
			PropsValues.DEFAULT_ADMIN_FIRST_NAME);
		_processProperty(
			httpServletRequest, unicodeProperties, "adminLastName",
			PropsKeys.DEFAULT_ADMIN_LAST_NAME,
			PropsValues.DEFAULT_ADMIN_LAST_NAME);
		_processProperty(
			httpServletRequest, unicodeProperties, "companyName",
			PropsKeys.COMPANY_DEFAULT_NAME, PropsValues.COMPANY_DEFAULT_NAME);
	}

	private static void _processProperty(
			HttpServletRequest httpServletRequest,
			UnicodeProperties unicodeProperties, String parameterName,
			String propertyKey, String defaultValue)
		throws Exception {

		String value = ParamUtil.getString(
			httpServletRequest, parameterName, defaultValue);

		if (!value.equals(defaultValue)) {
			unicodeProperties.put(propertyKey, value);
		}
	}

	private static void _testConnection(
			String driverClassName, String url, String userName,
			String password, String jndiName)
		throws Exception {

		if (Validator.isNull(jndiName)) {
			Class.forName(driverClassName);
		}

		DataSource dataSource = null;
		Connection connection = null;

		try {
			dataSource = DataSourceFactoryUtil.initDataSource(
				driverClassName, url, userName, password, jndiName);

			connection = dataSource.getConnection();
		}
		finally {
			DataAccess.cleanUp(connection);
			DataSourceFactoryUtil.destroyDataSource(dataSource);
		}
	}

	private static void _updateAdminUser(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			UnicodeProperties unicodeProperties)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = CompanyLocalServiceUtil.getCompanyById(
			themeDisplay.getCompanyId());

		String emailAddress = ParamUtil.getString(
			httpServletRequest, "adminEmailAddress",
			PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + StringPool.AT +
				company.getMx());

		PropsValues.ADMIN_EMAIL_FROM_ADDRESS = emailAddress;

		unicodeProperties.put(PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, emailAddress);

		String firstName = ParamUtil.getString(
			httpServletRequest, "adminFirstName",
			PropsValues.DEFAULT_ADMIN_FIRST_NAME);
		String lastName = ParamUtil.getString(
			httpServletRequest, "adminLastName",
			PropsValues.DEFAULT_ADMIN_LAST_NAME);

		boolean passwordReset = false;

		PasswordPolicy passwordPolicy =
			PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(
				company.getCompanyId());

		if ((passwordPolicy != null) && passwordPolicy.isChangeable()) {
			passwordReset = true;
		}

		User user = SetupWizardSampleDataUtil.updateAdminUser(
			company, themeDisplay.getLocale(), themeDisplay.getLanguageId(),
			emailAddress, firstName, lastName, passwordReset);

		PropsValues.ADMIN_EMAIL_FROM_NAME = user.getFullName();

		unicodeProperties.put(
			PropsKeys.ADMIN_EMAIL_FROM_NAME, user.getFullName());

		int index = emailAddress.indexOf(CharPool.AT);

		unicodeProperties.put(
			PropsKeys.COMPANY_DEFAULT_WEB_ID,
			emailAddress.substring(index + 1));
		unicodeProperties.put(
			PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX,
			emailAddress.substring(0, index));

		HttpSession session = httpServletRequest.getSession();

		session.setAttribute(WebKeys.EMAIL_ADDRESS, emailAddress);
		session.setAttribute(
			WebKeys.SETUP_WIZARD_PASSWORD_UPDATED, Boolean.TRUE);
		session.setAttribute(WebKeys.USER, user);
		session.setAttribute(WebKeys.USER_ID, user.getUserId());

		EventsProcessorUtil.process(
			PropsKeys.LOGIN_EVENTS_POST, PropsValues.LOGIN_EVENTS_POST,
			httpServletRequest, httpServletResponse);
	}

	private static void _updateCompany(
			HttpServletRequest httpServletRequest,
			UnicodeProperties unicodeProperties)
		throws Exception {

		Company company = CompanyLocalServiceUtil.getCompanyById(
			PortalInstances.getDefaultCompanyId());

		String languageId = ParamUtil.getString(
			httpServletRequest, "companyLocale", getDefaultLanguageId());

		unicodeProperties.put(PropsKeys.COMPANY_DEFAULT_LOCALE, languageId);

		String timeZoneId = ParamUtil.getString(
			httpServletRequest, "companyTimeZoneId", getDefaultTimeZoneId());

		unicodeProperties.put(PropsKeys.COMPANY_DEFAULT_TIME_ZONE, timeZoneId);

		String companyName = ParamUtil.getString(
			httpServletRequest, "companyName",
			PropsValues.COMPANY_DEFAULT_NAME);

		SetupWizardSampleDataUtil.updateCompany(
			company, companyName, languageId, timeZoneId);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setCompany(company);
	}

	private static boolean _writePropertiesFile(
		UnicodeProperties unicodeProperties) {

		try {
			FileUtil.write(
				PropsValues.LIFERAY_HOME, PROPERTIES_FILE_NAME,
				_getUnicodePropertiesStringWithEmptyValue(unicodeProperties));

			if (FileUtil.exists(
					PropsValues.LIFERAY_HOME + StringPool.SLASH +
						PROPERTIES_FILE_NAME)) {

				return true;
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return false;
	}

	private static final String _NULL_HOLDER = "NULL_HOLDER";

	private static final String _PROPERTIES_PREFIX = "properties--";

	private static final Log _log = LogFactoryUtil.getLog(
		SetupWizardUtil.class);

}