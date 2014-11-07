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

package com.liferay.portal.util;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.cache.CacheRegistryImpl;
import com.liferay.portal.configuration.ConfigurationFactoryImpl;
import com.liferay.portal.dao.db.DBFactoryImpl;
import com.liferay.portal.dao.jdbc.DataSourceFactoryImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.SanitizerLogWrapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.security.lang.SecurityManagerUtil;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.util.log4j.Log4JUtil;

import com.sun.syndication.io.XmlReader;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 */
public class InitUtil {

	public static synchronized void init() {
		if (_initialized) {
			return;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		// Set the default locale used by Liferay. This locale is no longer set
		// at the VM level. See LEP-2584.

		String userLanguage = SystemProperties.get("user.language");
		String userCountry = SystemProperties.get("user.country");
		String userVariant = SystemProperties.get("user.variant");

		LocaleUtil.setDefault(userLanguage, userCountry, userVariant);

		// Set the default time zone used by Liferay. This time zone is no
		// longer set at the VM level. See LEP-2584.

		String userTimeZone = SystemProperties.get("user.timezone");

		TimeZoneUtil.setDefault(userTimeZone);

		// Shared class loader

		try {
			PortalClassLoaderUtil.setClassLoader(
				ClassLoaderUtil.getContextClassLoader());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Properties

		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());

		// Log4J

		if (GetterUtil.getBoolean(
				SystemProperties.get("log4j.configure.on.startup"), true)) {

			ClassLoader classLoader = InitUtil.class.getClassLoader();

			Log4JUtil.configureLog4J(classLoader);
		}

		// Shared log

		try {
			LogFactoryUtil.setLogFactory(new Log4jLogFactoryImpl());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Log sanitizer

		SanitizerLogWrapper.init();

		// Java properties

		JavaDetector.isJDK5();

		// Security manager

		SecurityManagerUtil.init();

		if (SecurityManagerUtil.ENABLED) {
			com.liferay.portal.kernel.util.PropsUtil.setProps(
				DoPrivilegedUtil.wrap(
					com.liferay.portal.kernel.util.PropsUtil.getProps()));

			LogFactoryUtil.setLogFactory(
				DoPrivilegedUtil.wrap(LogFactoryUtil.getLogFactory()));
		}

		// Cache registry

		CacheRegistryUtil.setCacheRegistry(
			DoPrivilegedUtil.wrap(new CacheRegistryImpl()));

		// Configuration factory

		ConfigurationFactoryUtil.setConfigurationFactory(
			DoPrivilegedUtil.wrap(new ConfigurationFactoryImpl()));

		// Data source factory

		DataSourceFactoryUtil.setDataSourceFactory(
			DoPrivilegedUtil.wrap(new DataSourceFactoryImpl()));

		// DB factory

		DBFactoryUtil.setDBFactory(DoPrivilegedUtil.wrap(new DBFactoryImpl()));

		// ROME

		XmlReader.setDefaultEncoding(StringPool.UTF8);

		if (_PRINT_TIME) {
			System.out.println(
				"InitAction takes " + stopWatch.getTime() + " ms");
		}

		_initialized = true;
	}

	public synchronized static void initWithSpring(
		boolean initModuleFramework) {

		List<String> configLocations = ListUtil.fromArray(
			PropsUtil.getArray(
				com.liferay.portal.kernel.util.PropsKeys.SPRING_CONFIGS));

		initWithSpring(configLocations, initModuleFramework);
	}

	public synchronized static void initWithSpring(
		List<String> configLocations, boolean initModuleFramework) {

		if (_initialized) {
			return;
		}

		if (!_neverInitialized) {
			PropsUtil.reload();
		}
		else {
			_neverInitialized = false;
		}

		init();

		try {
			if (initModuleFramework) {
				PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR =
					System.getProperty(SystemProperties.TMP_DIR);

				ModuleFrameworkUtilAdapter.startFramework();
			}

			SpringUtil.loadContext(configLocations);

			if (initModuleFramework) {
				BeanLocatorImpl beanLocatorImpl =
					(BeanLocatorImpl)PortalBeanLocatorUtil.getBeanLocator();

				ModuleFrameworkUtilAdapter.registerContext(
					beanLocatorImpl.getApplicationContext());

				ModuleFrameworkUtilAdapter.startRuntime();
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_initialized = true;
	}

	public synchronized static void stopModuleFramework() {
		try {
			ModuleFrameworkUtilAdapter.stopFramework();
		}
		catch (Exception e) {
			new RuntimeException(e);
		}
	}

	private static final boolean _PRINT_TIME = false;

	private static boolean _initialized;
	private static boolean _neverInitialized = true;

}