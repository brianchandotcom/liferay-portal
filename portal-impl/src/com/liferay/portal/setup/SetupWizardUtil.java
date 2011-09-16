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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
public class SetupWizardUtil {

	public static final String LIFERAY_SETUP_PROPERTIES_FILE =
		"portal-wizard.properties";

	public final static String PROPERTIES_PREFIX = "properties--";

	public static void processProperties (HttpServletRequest request) {
		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			request, "properties--");

		processAdminProperties(request, properties);
		processDatabaseProperties(request, properties);

		properties.put(PropsKeys.SETUP_WIZARD_ENABLED, String.valueOf(false));

		boolean propertiesFileCreated =
			SetupWizardUtil.writePropertiesFile(properties);

		request.setAttribute(
			"setup_wizard_propertiesFileCreated",
			String.valueOf(propertiesFileCreated));
		request.setAttribute("setup_wizard_properties", properties);
	}

	protected static String getParameter(
		HttpServletRequest request, String name, String defaultValue) {

		name = PROPERTIES_PREFIX.concat(name).concat(StringPool.DOUBLE_DASH);

		return ParamUtil.getString(request, name);
	}

	protected static void processAdminProperties(
		HttpServletRequest request, UnicodeProperties properties) {

		String firstName = getParameter(
			request, PropsKeys.DEFAULT_ADMIN_FIRST_NAME, "Test");
		String lastName = getParameter(
			request, PropsKeys.DEFAULT_ADMIN_LAST_NAME, "Test");

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String fullName = fullNameGenerator.getFullName(
			firstName, null, lastName);

		String adminEmail = getParameter(
			request, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.com");

		ScreenNameGenerator screenNameGenerator =
			ScreenNameGeneratorFactory.getInstance();

		String screenName = "test";

		try {
			screenName = screenNameGenerator.generate(0, 0, adminEmail);
		}
		catch(Exception e) {
		}

		properties.put(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME, screenName);
		properties.put(PropsKeys.ADMIN_EMAIL_FROM_NAME, fullName);
		properties.put(PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS, adminEmail);
	}

	protected static void processDatabaseProperties(
		HttpServletRequest request, UnicodeProperties properties) {

		boolean defaultDatabase = ParamUtil.getBoolean(
			request, "defaultDatabase", true);

		if (defaultDatabase) {
			return;
		}

		String databaseModel = ParamUtil.getString(
			request, "databaseType", "hsql");
		String databaseName = ParamUtil.getString(
			request, "databaseName", "lportal");

		String driverClassName = null;
		String url = null;

		if ("db2".equals(databaseModel)) {
			driverClassName = "com.ibm.db2.jcc.DB2Driver";

			url = "jdbc:db2://localhost:50000/" + databaseName +
				":deferPrepares=false;fullyMaterializeInputStreams=true;" +
				"fullyMaterializeLobData=true;progresssiveLocators=2;" +
				"progressiveStreaming=2;";
		}
		else if ("derby".equals(databaseModel)) {
			driverClassName = "org.apache.derby.jdbc.EmbeddedDriver";

			url = "jdbc:derby:" + databaseName;
		}
		else if ("ingres".equals(databaseModel)) {
			driverClassName = "com.ingres.jdbc.IngresDriver";

			url = "jdbc:ingres://localhost:II7/" + databaseName;
		}
		else if ("mysql".equals(databaseModel)) {
			driverClassName = "com.mysql.jdbc.Driver";

			url = "jdbc:mysql://localhost/" + databaseName +
				"?useUnicode=true&characterEncoding=UTF-8" +
				"&useFastDateParsing=false";
		}
		else if ("oracle".equals(databaseModel)) {
			driverClassName = "oracle.jdbc.this.get_driver().OracleDriver";

			url = "jdbc:oracle:thin:@localhost:1521:xe";
		}
		else if ("p6spy".equals(databaseModel)) {
			driverClassName = "com.p6spy.engine.spy.P6SpyDriver";

			url = "jdbc:mysql://localhost/" + databaseName +
				"?useUnicode=true&characterEncoding=UTF-8" +
				"&useFastDateParsing=false";
		}
		else if ("postgresql".equals(databaseModel)) {
			driverClassName = "org.postgresql.Driver";

			url = "jdbc:postgresql://localhost:5432/" + databaseName;
		}
		else if ("sqlserver".equals(databaseModel)) {
			driverClassName = "net.sourceforge.jtds.jdbc.Driver";

			url = "jdbc:jtds:sqlserver://localhost/" + databaseName;
		}
		else if ("sybase".equals(databaseModel)) {
			driverClassName = "net.sourceforge.jtds.jdbc.Driver";

			url = "jdbc:jtds:sybase://localhost:5000/" + databaseName;
		}

		properties.put(
			PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME, driverClassName);
		properties.put(PropsKeys.JDBC_DEFAULT_URL, url);
	}

	protected static boolean writePropertiesFile(UnicodeProperties properties) {
		boolean succesful = true;

		try {
			FileUtil.write(
				PropsValues.LIFERAY_HOME, LIFERAY_SETUP_PROPERTIES_FILE,
				properties.toString());
		}
		catch (IOException e) {
			succesful = false;

			_log.error(e, e);
		}

		return succesful;
	}

	private static Log _log = LogFactoryUtil.getLog(SetupWizardUtil.class);

}