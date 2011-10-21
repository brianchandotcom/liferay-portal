/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.dao.jdbc.util.DataSourceSwapper;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.service.QuartzLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.sql.Connection;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.struts.Globals;

/**
 * @author Manuel de la Peña
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class SetupWizardUtil {

	public static final String PROPERTIES_FILE_NAME =
		"portal-setup-wizard.properties";

	public static boolean isSetupFinished(HttpServletRequest request) {
		if (!PropsValues.SETUP_WIZARD_ENABLED) {
			return true;
		}

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		Boolean setupWizardFinished = (Boolean)servletContext.getAttribute(
			WebKeys.SETUP_WIZARD_FINISHED);

		if (setupWizardFinished != null) {
			return setupWizardFinished;
		}

		return true;
	}

	public static void testDatabase(HttpServletRequest request)
		throws Exception {

		String driverClassName = _getParameter(
			request, PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME);
		String url = _getParameter(
			request, PropsKeys.JDBC_DEFAULT_URL, null);
		String userName = _getParameter(
			request, PropsKeys.JDBC_DEFAULT_USERNAME, null);
		String password = _getParameter(
			request, PropsKeys.JDBC_DEFAULT_PASSWORD, null);

		_testConnection(driverClassName, url, userName, password);
	}

	public static void updateLanguage(
		HttpServletRequest request, HttpServletResponse response) {

		String languageId = _getParameter(
			request, PropsKeys.COMPANY_DEFAULT_LOCALE,
			PropsValues.COMPANY_DEFAULT_LOCALE);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		List<Locale> availableLocales = ListUtil.fromArray(
			LanguageUtil.getAvailableLocales());

		if (!availableLocales.contains(locale)) {
			return;
		}

		PropsValues.COMPANY_DEFAULT_LOCALE = languageId;

		HttpSession session = request.getSession();

		session.setAttribute(Globals.LOCALE_KEY, locale);
		session.setAttribute(WebKeys.SETUP_WIZARD_DEFAULT_LOCALE, languageId);

		LanguageUtil.updateCookie(request, response, locale);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		themeDisplay.setLanguageId(languageId);
		themeDisplay.setLocale(locale);
	}

	public static void updateSetup(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		UnicodeProperties unicodeProperties =
			PropertiesParamUtil.getProperties(request, _PROPERTIES_PREFIX);

		_processAdminProperties(request, unicodeProperties);
		_processDatabaseProperties(request, unicodeProperties);

		updateLanguage(request, response);

		unicodeProperties.put(
			PropsKeys.SETUP_WIZARD_ENABLED, String.valueOf(false));

		HttpSession session = request.getSession();

		session.setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES, unicodeProperties);

		boolean propertiesFileUpdated = _writePropertiesFile(unicodeProperties);

		session.setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES_UPDATED, propertiesFileUpdated);

		_reloadServletContext(request, unicodeProperties);
		_resetAdminPassword(request);
	}

	private static String _getParameter(
		HttpServletRequest request, String name, String defaultValue) {

		name = _PROPERTIES_PREFIX.concat(name).concat(StringPool.DOUBLE_DASH);

		return ParamUtil.getString(request, name, defaultValue);
	}

	private static void _processAdminProperties(
			HttpServletRequest request, UnicodeProperties unicodeProperties)
		throws Exception {

		String webId = _getParameter(
			request, PropsKeys.COMPANY_DEFAULT_WEB_ID, "liferay.com");
		PropsValues.COMPANY_DEFAULT_WEB_ID = webId;

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String firstName = _getParameter(
			request, PropsKeys.DEFAULT_ADMIN_FIRST_NAME, "Test");

		PropsValues.DEFAULT_ADMIN_FIRST_NAME = firstName;

		String lastName = _getParameter(
			request, PropsKeys.DEFAULT_ADMIN_LAST_NAME, "Test");

		PropsValues.DEFAULT_ADMIN_LAST_NAME = lastName;

		String adminEmailFromName = fullNameGenerator.getFullName(
			firstName, null, lastName);

		PropsValues.ADMIN_EMAIL_FROM_NAME = adminEmailFromName;

		unicodeProperties.put(
			PropsKeys.ADMIN_EMAIL_FROM_NAME, adminEmailFromName);

		String defaultAdminEmailAddress = _getParameter(
			request, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.com");

		unicodeProperties.put(
			PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS, defaultAdminEmailAddress);

		PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS = defaultAdminEmailAddress;

		ScreenNameGenerator screenNameGenerator =
			ScreenNameGeneratorFactory.getInstance();

		String defaultAdminScreenName = null;

		try {
			defaultAdminScreenName = screenNameGenerator.generate(
				0, 0, defaultAdminEmailAddress);
		}
		catch (Exception e) {
			defaultAdminScreenName = "test";
		}

		PropsValues.DEFAULT_ADMIN_SCREEN_NAME = defaultAdminScreenName;

		unicodeProperties.put(
			PropsKeys.DEFAULT_ADMIN_SCREEN_NAME, defaultAdminScreenName);
	}

	private static void _processDatabaseProperties(
			HttpServletRequest request, UnicodeProperties unicodeProperties)
		throws Exception {

		boolean defaultDatabase = GetterUtil.getBoolean(
			_getParameter(request, "defaultDatabase", "true"));

		if (defaultDatabase) {
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_URL);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_USERNAME);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_PASSWORD);
		}
	}

	private static void _reloadServletContext(
			HttpServletRequest request, UnicodeProperties unicodeProperties)
		throws Exception {

		// Data sources

		Properties jdbcProperties = new Properties();

		jdbcProperties.putAll(unicodeProperties);

		jdbcProperties = PropertiesUtil.getProperties(
			jdbcProperties,"jdbc.default.",true);

		DataSourceSwapper.swapCounterDataSource(jdbcProperties);
		DataSourceSwapper.swapLiferayDataSource(jdbcProperties);

		// Caches

		CacheRegistryUtil.clear();
		MultiVMPoolUtil.clear();
		WebCachePoolUtil.clear();
		CentralizedThreadLocal.clearShortLivedThreadLocals();

		// Startup

		QuartzLocalServiceUtil.checkQuartzTables();

		StartupAction startupAction = new StartupAction();

		startupAction.run(null);

		HttpSession session = request.getSession();

		PortalInstances.reload(session.getServletContext());
	}

	private static void _resetAdminPassword(HttpServletRequest request)
		throws Exception {

		String defaultAdminEmailAddress = _getParameter(
			request, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.com");

		User user = null;

		try {
			user = UserLocalServiceUtil.getUserByEmailAddress(
				PortalUtil.getDefaultCompanyId(), defaultAdminEmailAddress);
		}
		catch (NoSuchUserException nsue) {
			user = UserLocalServiceUtil.getUserByEmailAddress(
				PortalUtil.getDefaultCompanyId(), "test@liferay.com");

			UserLocalServiceUtil.updateEmailAddress(
				user.getUserId(), StringPool.BLANK,
				defaultAdminEmailAddress, defaultAdminEmailAddress);
		}

		UserLocalServiceUtil.updatePasswordReset(user.getUserId(), true);

		HttpSession session = request.getSession();

		session.setAttribute(WebKeys.SETUP_WIZARD_PASSWORD_UPDATED, true);
	}

	private static void _testConnection(
			String driverClassName, String url, String userName,
			String password)
		throws Exception {

		Class.forName(driverClassName);

		Connection connection = null;

		try {
			DataSource dataSource = DataSourceFactoryUtil.initDataSource(
				driverClassName, url, userName, password);

			connection = dataSource.getConnection();
		}
		finally {
			DataAccess.cleanUp(connection);
		}
	}

	private static boolean _writePropertiesFile(
		UnicodeProperties unicodeProperties) {

		try {
			FileUtil.write(
				PropsValues.LIFERAY_HOME, PROPERTIES_FILE_NAME,
				unicodeProperties.toString());

			return true;
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			return false;
		}
	}

	private final static String _PROPERTIES_PREFIX = "properties--";

	private static Log _log = LogFactoryUtil.getLog(SetupWizardUtil.class);

}