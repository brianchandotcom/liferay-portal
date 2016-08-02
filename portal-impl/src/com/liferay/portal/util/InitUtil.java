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
import com.liferay.portal.configuration.ConfigurationFactoryImpl;
import com.liferay.portal.dao.db.DBManagerImpl;
import com.liferay.portal.dao.jdbc.DataSourceFactoryImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.SanitizerLogWrapper;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.dao.orm.UpgradeOptimizedConnectionProviderRegistryUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.security.lang.SecurityManagerUtil;
import com.liferay.portal.spring.context.ArrayApplicationContext;
import com.liferay.portal.upgrade.dao.orm.UpgradeOptimizedConnectionProviderRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.util.log4j.Log4JUtil;

import com.sun.syndication.io.XmlReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

		// Security manager

		SecurityManagerUtil.init();

		if (SecurityManagerUtil.ENABLED) {
			com.liferay.portal.kernel.util.PropsUtil.setProps(
				DoPrivilegedUtil.wrap(
					com.liferay.portal.kernel.util.PropsUtil.getProps()));

			LogFactoryUtil.setLogFactory(
				DoPrivilegedUtil.wrap(LogFactoryUtil.getLogFactory()));
		}

		// Configuration factory

		ConfigurationFactoryUtil.setConfigurationFactory(
			DoPrivilegedUtil.wrap(new ConfigurationFactoryImpl()));

		// Data source factory

		DataSourceFactoryUtil.setDataSourceFactory(
			DoPrivilegedUtil.wrap(new DataSourceFactoryImpl()));

		// DB manager

		DBManagerUtil.setDBManager(DoPrivilegedUtil.wrap(new DBManagerImpl()));

		// Upgrade optimized connection provider registry

		UpgradeOptimizedConnectionProviderRegistryUtil.
			setUpgradeOptimizedConnectionProviderRegistry(
				new UpgradeOptimizedConnectionProviderRegistryImpl());

		// ROME

		XmlReader.setDefaultEncoding(StringPool.UTF8);

		if (_PRINT_TIME) {
			System.out.println(
				"InitAction takes " + stopWatch.getTime() + " ms");
		}

		_initialized = true;
	}

	public static synchronized void initWithSpring(
		boolean initModuleFramework, boolean registerContext) {

		List<String> configLocations = ListUtil.fromArray(
			PropsUtil.getArray(
				com.liferay.portal.kernel.util.PropsKeys.SPRING_CONFIGS));

		initWithSpring(configLocations, initModuleFramework, registerContext);
	}

	public static synchronized void initWithSpring(
		List<String> configLocations, boolean initModuleFramework,
		boolean registerContext) {

		if (_initialized) {
			return;
		}

		init();

		try {
			if (initModuleFramework) {
				PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR =
					System.getProperty(SystemProperties.TMP_DIR);

				ModuleFrameworkUtilAdapter.initFramework();
			}

			ApplicationContext infrastructureApplicationContext =
				new ArrayApplicationContext(
					PropsValues.SPRING_INFRASTRUCTURE_CONFIGS);

			if (initModuleFramework) {
				ModuleFrameworkUtilAdapter.registerContext(
					infrastructureApplicationContext);

				ModuleFrameworkUtilAdapter.startFramework();
			}

			ApplicationContext appApplicationContext =
				new ClassPathXmlApplicationContext(
					configLocations.toArray(new String[configLocations.size()]),
					infrastructureApplicationContext);

			BeanLocator beanLocator = new BeanLocatorImpl(
				ClassLoaderUtil.getPortalClassLoader(), appApplicationContext);

			PortalBeanLocatorUtil.setBeanLocator(beanLocator);

			if (initModuleFramework) {
				ModuleFrameworkUtilAdapter.startRuntime();
			}

			_appApplicationContext = appApplicationContext;

			if (initModuleFramework && registerContext) {
				registerContext();
			}

			registerSpringInitialized();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_initialized = true;
	}

	public static boolean isInitialized() {
		return _initialized;
	}

	public static void registerContext() {
		if (_appApplicationContext != null) {
			ModuleFrameworkUtilAdapter.registerContext(_appApplicationContext);
		}
	}

	public static void registerSpringInitialized() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("module.service.lifecycle", "spring.initialized");
		properties.put("service.vendor", ReleaseInfo.getVendor());
		properties.put("service.version", ReleaseInfo.getVersion());

		final ServiceRegistration<ModuleServiceLifecycle>
			moduleServiceLifecycleServiceRegistration =
				registry.registerService(
					ModuleServiceLifecycle.class,
					new ModuleServiceLifecycle() {}, properties);

		PortalLifecycleUtil.register(
			new BasePortalLifecycle() {

				@Override
				protected void doPortalDestroy() {
					moduleServiceLifecycleServiceRegistration.unregister();
				}

				@Override
				protected void doPortalInit() {
				}

			},
			PortalLifecycle.METHOD_DESTROY);
	}

	public static synchronized void stopModuleFramework() {
		try {
			ModuleFrameworkUtilAdapter.stopFramework(0);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized void stopRuntime() {
		try {
			ModuleFrameworkUtilAdapter.stopRuntime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final boolean _PRINT_TIME = false;

	private static ApplicationContext _appApplicationContext;
	private static boolean _initialized;

}