/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.service.AccountLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.QuartzLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.sql.Connection;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.struts.Globals;

/**
 * @author Manuel de la Peña
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 */
public class SetupWizardUtil {

	public static final String PROPERTIES_FILE_NAME =
		"portal-setup-wizard.properties";

	public static boolean isDefaultDatabase(HttpServletRequest request) {
		boolean hsqldb = ParamUtil.getBoolean(
			request, "defaultDatabase",
			PropsValues.JDBC_DEFAULT_URL.contains("hsqldb"));

		boolean jndi = Validator.isNotNull(PropsValues.JDBC_DEFAULT_JNDI_NAME);

		return hsqldb && !jndi;
	}

	public static boolean isSetupFinished() {
		if (PropsValues.SETUP_WIZARD_ENABLED) {
			return _setupFinished;
		}

		return true;
	}

	public static void setSetupFinished(boolean setupFinished) {
		_setupFinished = setupFinished;
	}

	public static void testDatabase(HttpServletRequest request)
		throws Exception {

		String driverClassName = _getParameter(
			request, PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME);
		String url = _getParameter(request, PropsKeys.JDBC_DEFAULT_URL, null);
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

		unicodeProperties.setProperty(
			PropsKeys.LIFERAY_HOME,
			SystemProperties.get(PropsKeys.LIFERAY_HOME));

		boolean databaseConfigured = _isDatabaseConfigured(unicodeProperties);

		_processAdminProperties(request, unicodeProperties);
		_processDatabaseProperties(
			request, unicodeProperties, databaseConfigured);

		updateLanguage(request, response);

		unicodeProperties.put(
			PropsKeys.SETUP_WIZARD_ENABLED, String.valueOf(false));

		PropsUtil.addProperties(unicodeProperties);

		HttpSession session = request.getSession();

		session.setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES, unicodeProperties);

		boolean propertiesFileUpdated = _writePropertiesFile(unicodeProperties);

		session.setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES_UPDATED, propertiesFileUpdated);

		if (!databaseConfigured) {
			_reloadServletContext(request, unicodeProperties);
		}

		_updateCompany();
		_updateAdminUser(request);

		_initPlugins();
	}

	private static String _getParameter(
		HttpServletRequest request, String name, String defaultValue) {

		name = _PROPERTIES_PREFIX.concat(name).concat(StringPool.DOUBLE_DASH);

		return ParamUtil.getString(request, name, defaultValue);
	}

	/**
	 * @see {@link com.liferay.portal.servlet.MainServlet#initPlugins}
	 */
	private static void _initPlugins() {
		HotDeployUtil.setCapturePrematureEvents(false);

		PortalLifecycleUtil.flushInits();
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
			PropsValues.JDBC_DEFAULT_USERNAME.equals(defaultUsername) ) {

			return true;
		}

		return false;
	}

	private static void _processAdminProperties(
			HttpServletRequest request, UnicodeProperties unicodeProperties)
		throws Exception {

		String companyName = _getParameter(
			request, PropsKeys.COMPANY_DEFAULT_NAME, "Liferay");

		PropsValues.COMPANY_DEFAULT_NAME = companyName;

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String firstName = _getParameter(
			request, PropsKeys.DEFAULT_ADMIN_FIRST_NAME, "Test");

		PropsValues.DEFAULT_ADMIN_FIRST_NAME = firstName;

		String lastName = _getParameter(
			request, PropsKeys.DEFAULT_ADMIN_LAST_NAME, "Test");

		PropsValues.DEFAULT_ADMIN_LAST_NAME = lastName;

		String fullName = fullNameGenerator.getFullName(
			firstName, null, lastName);

		PropsValues.ADMIN_EMAIL_FROM_NAME = fullName;

		unicodeProperties.put(PropsKeys.ADMIN_EMAIL_FROM_NAME, fullName);

		String emailAddress = _getParameter(
			request, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.com");

		PropsValues.ADMIN_EMAIL_FROM_ADDRESS = emailAddress;
		PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS = emailAddress;

		unicodeProperties.put(
			PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS, emailAddress);

		ScreenNameGenerator screenNameGenerator =
			ScreenNameGeneratorFactory.getInstance();

		String screenName = null;

		try {
			screenName = screenNameGenerator.generate(0, 0, emailAddress);
		}
		catch (Exception e) {
			screenName = "test";
		}

		PropsValues.DEFAULT_ADMIN_SCREEN_NAME = screenName;

		unicodeProperties.put(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME, screenName);
	}

	private static void _processDatabaseProperties(
			HttpServletRequest request, UnicodeProperties unicodeProperties,
			boolean databaseConfigured)
		throws Exception {

		boolean defaultDatabase = ParamUtil.getBoolean(
			request, "defaultDatabase", true);

		if (defaultDatabase || databaseConfigured) {
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_URL);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_USERNAME);
			unicodeProperties.remove(PropsKeys.JDBC_DEFAULT_PASSWORD);
		}
	}

	private static void _reconfigurePersistenceBeans() throws Exception {
		@SuppressWarnings("rawtypes")
		Map<String, BasePersistenceImpl> beanPersistenceImpls =
			PortalBeanLocatorUtil.locate(BasePersistenceImpl.class);

		SessionFactory sessionFactory =
			(SessionFactory)PortalBeanLocatorUtil.locate(
				"liferaySessionFactory");

		for (String name : beanPersistenceImpls.keySet()) {
			BasePersistenceImpl<?> beanPersistenceImpl =
				beanPersistenceImpls.get(name);

			beanPersistenceImpl.setSessionFactory(sessionFactory);
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

		// Persistence beans

		_reconfigurePersistenceBeans();

		// Quartz

		QuartzLocalServiceUtil.checkQuartzTables();

		// Startup

		StartupAction startupAction = new StartupAction();

		startupAction.run(null);

		// Servlet context

		HttpSession session = request.getSession();

		PortalInstances.reload(session.getServletContext());
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

	private static void _updateAdminUser(HttpServletRequest request)
		throws Exception {

		// Email address

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = null;

		try {
			user = UserLocalServiceUtil.getUserByEmailAddress(
				PortalUtil.getDefaultCompanyId(),
				PropsValues.ADMIN_EMAIL_FROM_ADDRESS);
		}
		catch (NoSuchUserException nsue) {
			user = UserLocalServiceUtil.getUserByEmailAddress(
				PortalUtil.getDefaultCompanyId(), "test@liferay.com");

			user = UserLocalServiceUtil.updateEmailAddress(
				user.getUserId(), StringPool.BLANK,
				PropsValues.ADMIN_EMAIL_FROM_ADDRESS,
				PropsValues.ADMIN_EMAIL_FROM_ADDRESS);
		}

		// First and last name

		String greeting = LanguageUtil.format(
			themeDisplay.getLocale(), "welcome-x",
			" " + PropsValues.ADMIN_EMAIL_FROM_NAME, false);

		Contact contact = user.getContact();

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		user = UserLocalServiceUtil.updateUser(
			user.getUserId(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, false, user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), user.getScreenName(),
			user.getEmailAddress(), user.getFacebookId(), user.getOpenId(),
			themeDisplay.getLanguageId(), user.getTimeZoneId(), greeting,
			user.getComments(), PropsValues.DEFAULT_ADMIN_FIRST_NAME,
			user.getMiddleName(), PropsValues.DEFAULT_ADMIN_LAST_NAME,
			contact.getPrefixId(), contact.getSuffixId(), contact.isMale(),
			birthdayMonth, birthdayDay, birthdayYear, contact.getSmsSn(),
			contact.getAimSn(), contact.getFacebookSn(), contact.getIcqSn(),
			contact.getJabberSn(), contact.getMsnSn(), contact.getMySpaceSn(),
			contact.getSkypeSn(), contact.getTwitterSn(), contact.getYmSn(),
			contact.getJobTitle(), null, null, null, null, null,
			new ServiceContext());

		// Password

		user = UserLocalServiceUtil.updatePasswordReset(user.getUserId(), true);

		HttpSession session = request.getSession();

		session.setAttribute(WebKeys.SETUP_WIZARD_PASSWORD_UPDATED, true);
	}

	private static void _updateCompany() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompanyById(
			PortalInstances.getDefaultCompanyId());

		Account account = company.getAccount();

		String name = account.getName();

		if (!name.equals(PropsValues.COMPANY_DEFAULT_NAME)) {
			account.setName(PropsValues.COMPANY_DEFAULT_NAME);

			AccountLocalServiceUtil.updateAccount(account);
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

	private static final String _PROPERTIES_PREFIX = "properties--";

	private static Log _log = LogFactoryUtil.getLog(SetupWizardUtil.class);

	private static boolean _setupFinished = false;

}