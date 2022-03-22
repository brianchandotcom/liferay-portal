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

import com.liferay.petra.log4j.Log4JUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.configuration.ConfigurationFactoryImpl;
import com.liferay.portal.dao.db.DBManagerImpl;
import com.liferay.portal.dao.init.DBInitUtil;
import com.liferay.portal.dao.jdbc.DataSourceFactoryImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.SanitizerLogWrapper;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.module.framework.ModuleFrameworkUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.spring.bean.LiferayBeanFactory;
import com.liferay.portal.spring.compat.CompatBeanDefinitionRegistryPostProcessor;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;
import com.liferay.portal.spring.context.ArrayApplicationContext;
import com.liferay.portal.xml.SAXReaderImpl;

import java.lang.reflect.Field;

import java.util.List;
import java.util.zip.ZipFile;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Brian Wing Shun Chan
 */
public class InitUtil {

	public static synchronized void init() {
		if (_initialized) {
			return;
		}

		try {
			if (!OSDetector.isWindows() && !JavaDetector.isJDK11()) {
				Field field = ReflectionUtil.getDeclaredField(
					ZipFile.class, "usemmap");

				if ((boolean)field.get(null)) {
					field.setBoolean(null, false);
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
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

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		try {
			PortalClassLoaderUtil.setClassLoader(classLoader);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		// Properties

		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());

		// Shared log

		try {
			LogFactoryUtil.setLogFactory(new Log4jLogFactoryImpl());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		// Log4J

		if (GetterUtil.getBoolean(
				SystemProperties.get("log4j.configure.on.startup"), true)) {

			Log4JUtil.configureLog4J(InitUtil.class.getClassLoader());
		}

		// Log sanitizer

		SanitizerLogWrapper.init();

		// Configuration factory

		ConfigurationFactoryUtil.setConfigurationFactory(
			new ConfigurationFactoryImpl());

		// Data source factory

		DataSourceFactoryUtil.setDataSourceFactory(new DataSourceFactoryImpl());

		// DB manager

		DBManagerUtil.setDBManager(new DBManagerImpl());

		// File

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		// XML

		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			new SecureXMLFactoryProviderImpl());

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		unsecureSAXReaderUtil.setSAXReader(new SAXReaderImpl());

		if (_PRINT_TIME) {
			System.out.println(
				"InitAction takes " + stopWatch.getTime() + " ms");
		}

		_initialized = true;
	}

	public static synchronized void initWithSpring(
		boolean initModuleFramework, boolean registerContext) {

		List<String> configLocations = ListUtil.fromArray(
			PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));

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

				ModuleFrameworkUtil.initFramework();
			}

			DBInitUtil.init();

			ApplicationContext infrastructureApplicationContext =
				new ArrayApplicationContext(
					PropsValues.SPRING_INFRASTRUCTURE_CONFIGS);

			if (initModuleFramework) {
				ModuleFrameworkUtil.registerContext(
					infrastructureApplicationContext);

				ModuleFrameworkUtil.startFramework();
			}

			ConfigurableApplicationContext configurableApplicationContext =
				new ClassPathXmlApplicationContext(
					configLocations.toArray(new String[0]), false,
					infrastructureApplicationContext) {

					@Override
					protected DefaultListableBeanFactory createBeanFactory() {
						return new LiferayBeanFactory(
							getInternalParentBeanFactory());
					}

				};

			if (infrastructureApplicationContext.containsBean(
					"configurableApplicationContextConfigurator")) {

				ConfigurableApplicationContextConfigurator
					configurableApplicationContextConfigurator =
						infrastructureApplicationContext.getBean(
							"configurableApplicationContextConfigurator",
							ConfigurableApplicationContextConfigurator.class);

				configurableApplicationContextConfigurator.configure(
					configurableApplicationContext);
			}

			configurableApplicationContext.addBeanFactoryPostProcessor(
				new CompatBeanDefinitionRegistryPostProcessor());

			configurableApplicationContext.refresh();

			BeanLocator beanLocator = new BeanLocatorImpl(
				PortalClassLoaderUtil.getClassLoader(),
				configurableApplicationContext);

			PortalBeanLocatorUtil.setBeanLocator(beanLocator);

			_appApplicationContext = configurableApplicationContext;

			if (initModuleFramework && registerContext) {
				registerContext();
			}

			registerSpringInitialized();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		_initialized = true;
	}

	public static boolean isInitialized() {
		return _initialized;
	}

	public static void registerContext() {
		if (_appApplicationContext != null) {
			ModuleFrameworkUtil.registerContext(_appApplicationContext);
		}
	}

	public static void registerSpringInitialized() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		final ServiceRegistration<ModuleServiceLifecycle>
			moduleServiceLifecycleServiceRegistration =
				bundleContext.registerService(
					ModuleServiceLifecycle.class,
					new ModuleServiceLifecycle() {
					},
					HashMapDictionaryBuilder.<String, Object>put(
						"module.service.lifecycle", "spring.initialized"
					).put(
						"service.vendor", ReleaseInfo.getVendor()
					).put(
						"service.version", ReleaseInfo.getVersion()
					).build());

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

	private static final boolean _PRINT_TIME = false;

	private static final Log _log = LogFactoryUtil.getLog(InitUtil.class);

	private static ApplicationContext _appApplicationContext;
	private static boolean _initialized;

}