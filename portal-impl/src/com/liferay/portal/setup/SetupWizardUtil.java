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

import com.liferay.portal.dao.jdbc.util.DataSourceSwapUtil;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
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
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Manuel de la Peña
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class SetupWizardUtil {

	private static Log _log = LogFactoryUtil.getLog(SetupWizardUtil.class);

	public static final String _PROPERTIES_FILE_NAME =
		"portal-setup-wizard.properties";

	private final static String _PROPERTIES_PREFIX = "properties--";

	public static boolean isSetupFinished(HttpServletRequest request) {
		ServletContext servletContext =
			request.getSession().getServletContext();

		Boolean setupWizardFinished = (Boolean)servletContext.getAttribute(
			WebKeys.SETUP_WIZARD_FINISHED);

		return setupWizardFinished == null ||
			setupWizardFinished.booleanValue();
	}

	public static void processSetup(HttpServletRequest request)
		throws Exception {

		UnicodeProperties unicodeProperties =
			PropertiesParamUtil.getProperties(request, _PROPERTIES_PREFIX);

		_processAdminProperties(request, unicodeProperties);
		_processDatabaseProperties(request, unicodeProperties);

		unicodeProperties.put(
			PropsKeys.SETUP_WIZARD_ENABLED, String.valueOf(false));

		boolean updatedPropertiesFile = _writePropertiesFile(unicodeProperties);

		_processContextReload(request, unicodeProperties);
		_resetAdminPassword(request);

		request.getSession().setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES_UPDATED,
			String.valueOf(updatedPropertiesFile));

		request.getSession().setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES, unicodeProperties);
	}

	private static String _getParameter(
		HttpServletRequest request, String name, String defaultValue) {

		name = _PROPERTIES_PREFIX.concat(name).concat(StringPool.DOUBLE_DASH);

		return ParamUtil.getString(request, name);
	}

	private static void _resetAdminPassword(HttpServletRequest request) {
		String defaultAdminEmailAddress = _getParameter(
			request, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.com");

		try {
			User adminUser = UserLocalServiceUtil.getUserByEmailAddress(
				PortalUtil.getDefaultCompanyId(), defaultAdminEmailAddress);

			UserLocalServiceUtil.updatePasswordReset(
				adminUser.getUserId(), true);

			request.getSession().setAttribute(
				WebKeys.SETUP_WIZARD_PASSWORD_UPDATED,
				String.valueOf(true));
		}
		catch (Exception e) {
		}
	}

	private static void _processAdminProperties(
		HttpServletRequest request, UnicodeProperties properties) {

		String webId = _getParameter(
			request, PropsKeys.COMPANY_DEFAULT_WEB_ID, "liferay.com");
		PropsValues.COMPANY_DEFAULT_WEB_ID = webId;

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String firstName = _getParameter(
			request, PropsKeys.DEFAULT_ADMIN_FIRST_NAME, "Test");
		String lastName = _getParameter(
			request, PropsKeys.DEFAULT_ADMIN_LAST_NAME, "Test");

		String adminEmailFromName = fullNameGenerator.getFullName(
			firstName, null, lastName);

		properties.put(PropsKeys.ADMIN_EMAIL_FROM_NAME, adminEmailFromName);

		PropsValues.DEFAULT_ADMIN_FIRST_NAME = firstName;
		PropsValues.DEFAULT_ADMIN_LAST_NAME = lastName;
		PropsValues.ADMIN_EMAIL_FROM_NAME = adminEmailFromName;

		String defaultAdminEmailAddress = _getParameter(
			request, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.com");

		properties.put(
			PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS, defaultAdminEmailAddress);

		PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS = defaultAdminEmailAddress;

		ScreenNameGenerator screenNameGenerator =
			ScreenNameGeneratorFactory.getInstance();

		String defaultAdminScreenName = "test";

		try {
			defaultAdminScreenName = screenNameGenerator.generate(
				0, 0, defaultAdminEmailAddress);
		}
		catch (Exception e) {
		}

		properties.put(
			PropsKeys.DEFAULT_ADMIN_SCREEN_NAME, defaultAdminScreenName);

		PropsValues.DEFAULT_ADMIN_SCREEN_NAME = defaultAdminScreenName;
	}

	private static void _processContextReload(
			HttpServletRequest request, UnicodeProperties unicodeProperties)
		throws Exception {

		// Swap datasource

		Properties jdbcProperties = new Properties();

		jdbcProperties.putAll(unicodeProperties);
		jdbcProperties = PropertiesUtil.getProperties(
			jdbcProperties,"jdbc.default.",true);

		DataSourceSwapUtil.swapCounterDataSource(jdbcProperties);
		DataSourceSwapUtil.swapPortalDataSource(jdbcProperties);

		// Clear caches

		CacheRegistryUtil.clear();
		MultiVMPoolUtil.clear();
		WebCachePoolUtil.clear();
		CentralizedThreadLocal.clearShortLivedThreadLocals();

		// Rebuild database and loading data

		QuartzLocalServiceUtil.checkQuartzTables();

		new StartupAction().run(null);

		PortalInstances.reload(request.getSession().getServletContext());
	}

	private static void _processDatabaseProperties(
			HttpServletRequest request, UnicodeProperties properties)
		throws Exception {

		boolean defaultDatabase = ParamUtil.getBoolean(
			request, "defaultDatabase", true);

		if (defaultDatabase) {
			return;
		}

		String jdbcDefaultDriverClassName = null;
		String jdbcDefaultURL = null;

		String databaseType = ParamUtil.getString(
			request, "databaseType", "hsql");
		String databaseName = ParamUtil.getString(
			request, "databaseName", "lportal");

		if (databaseType.equals("db2")) {
			jdbcDefaultDriverClassName = "com.ibm.db2.jcc.DB2Driver";

			StringBundler sb = new StringBundler(5);

			sb.append("jdbc:db2://localhost:50000/");
			sb.append(databaseName);
			sb.append(":deferPrepares=false;fullyMaterializeInputStreams=");
			sb.append("true;fullyMaterializeLobData=true;");
			sb.append("progresssiveLocators=2;progressiveStreaming=2;");

			jdbcDefaultURL = sb.toString();
		}
		else if (databaseType.equals("derby")) {
			jdbcDefaultDriverClassName = "org.apache.derby.jdbc.EmbeddedDriver";
			jdbcDefaultURL = "jdbc:derby:" + databaseName;
		}
		else if (databaseType.equals("ingres")) {
			jdbcDefaultDriverClassName = "com.ingres.jdbc.IngresDriver";
			jdbcDefaultURL = "jdbc:ingres://localhost:II7/" + databaseName;
		}
		else if (databaseType.equals("mysql")) {
			jdbcDefaultDriverClassName = "com.mysql.jdbc.Driver";
			jdbcDefaultURL =
				"jdbc:mysql://localhost/" + databaseName +
					"?useUnicode=true&characterEncoding=UTF-8&" +
						"useFastDateParsing=false";
		}
		else if (databaseType.equals("oracle")) {
			jdbcDefaultDriverClassName =
				"oracle.jdbc.this.get_driver().OracleDriver";
			jdbcDefaultURL = "jdbc:oracle:thin:@localhost:1521:xe";
		}
		else if (databaseType.equals("p6spy")) {
			jdbcDefaultDriverClassName = "com.p6spy.engine.spy.P6SpyDriver";
			jdbcDefaultURL =
				"jdbc:mysql://localhost/" + databaseName +
					"?useUnicode=true&characterEncoding=UTF-8" +
						"&useFastDateParsing=false";
		}
		else if (databaseType.equals("postgresql")) {
			jdbcDefaultDriverClassName = "org.postgresql.Driver";
			jdbcDefaultURL = "jdbc:postgresql://localhost:5432/" + databaseName;
		}
		else if (databaseType.equals("sqlserver")) {
			jdbcDefaultDriverClassName = "net.sourceforge.jtds.jdbc.Driver";
			jdbcDefaultURL = "jdbc:jtds:sqlserver://localhost/" + databaseName;
		}
		else if (databaseType.equals("sybase")) {
			jdbcDefaultDriverClassName = "net.sourceforge.jtds.jdbc.Driver";
			jdbcDefaultURL =
				"jdbc:jtds:sybase://localhost:5000/" + databaseName;
		}

		properties.put(
			PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			jdbcDefaultDriverClassName);
		properties.put(PropsKeys.JDBC_DEFAULT_URL, jdbcDefaultURL);
	}

	private static boolean _writePropertiesFile(
		UnicodeProperties unicodeProperties) {

		try {
			FileUtil.write(
				PropsValues.LIFERAY_HOME, _PROPERTIES_FILE_NAME,
				unicodeProperties.toString());

			return true;
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			return false;
		}
	}

}