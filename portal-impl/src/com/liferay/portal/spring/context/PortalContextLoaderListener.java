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

package com.liferay.portal.spring.context;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.dao.init.DBInitUtil;
import com.liferay.portal.dao.orm.hibernate.FieldInterceptionHelperUtil;
import com.liferay.portal.deploy.hot.CustomJspBagRegistryUtil;
import com.liferay.portal.deploy.hot.ServiceWrapperRegistry;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.exception.LoggedExceptionInInitializerError;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.servlet.PortletSessionListenerManager;
import com.liferay.portal.kernel.servlet.SerializableSessionAttributeListener;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ClearThreadLocalUtil;
import com.liferay.portal.kernel.util.ClearTimerThreadUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.servlet.PortalSessionListener;
import com.liferay.portal.spring.aop.DynamicProxyCreator;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalClassPathUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.beans.PropertyDescriptor;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import javax.sql.DataSource;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Michael Young
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class PortalContextLoaderListener extends ContextLoaderListener {

	public static String getPortalServletContextName() {
		return _portalServletContextName;
	}

	public static String getPortalServletContextPath() {
		return _portalServletContextPath;
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ApplicationContext applicationContext =
			ContextLoader.getCurrentWebApplicationContext();

		ModuleFrameworkUtilAdapter.unregisterContext(applicationContext);

		ThreadLocalCacheManager.destroy();

		if (_serviceWrapperRegistry != null) {
			_serviceWrapperRegistry.close();
		}

		try {
			DirectServletRegistryUtil.clearServlets();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			HotDeployUtil.reset();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			PortalLifecycleUtil.reset();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		closeDataSource("counterDataSource");

		closeDataSource("liferayDataSource");

		super.contextDestroyed(servletContextEvent);

		try {
			ModuleFrameworkUtilAdapter.stopRuntime();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			ModuleFrameworkUtilAdapter.stopFramework(
				PropsValues.MODULE_FRAMEWORK_STOP_WAIT_TIMEOUT);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		ModuleFrameworkUtilAdapter.unregisterContext(_arrayApplicationContext);

		_arrayApplicationContext.close();

		ClassLoaderPool.unregister(_portalServletContextName);
		ServletContextClassLoaderPool.unregister(_portalServletContextName);

		try {
			ClearThreadLocalUtil.clearThreadLocal();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			ClearTimerThreadUtil.clearTimerThread();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			Class.forName(SystemProperties.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}

		FieldInterceptionHelperUtil.initialize();

		final ServletContext servletContext =
			servletContextEvent.getServletContext();

		String portalLibDir = servletContext.getRealPath("/WEB-INF/lib");

		portalLibDir = StringUtil.replace(
			portalLibDir, CharPool.BACK_SLASH, CharPool.FORWARD_SLASH);

		if (Validator.isNotNull(portalLibDir)) {
			SystemProperties.set(
				PropsKeys.LIFERAY_LIB_PORTAL_DIR, portalLibDir);
		}

		PortalClassPathUtil.initializeClassPaths(servletContext);

		InitUtil.init();

		// Log JVM arguments after Log4j is initialized

		_logJVMArguments();

		_portalServletContextName = servletContext.getServletContextName();

		if (_portalServletContextName == null) {
			_portalServletContextName = StringPool.BLANK;
		}

		_portalServletContextPath = servletContext.getContextPath();

		File tempDir = (File)servletContext.getAttribute(
			JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

		PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR =
			tempDir.getAbsolutePath();

		Path tempDirPath = Paths.get(System.getProperty("java.io.tmpdir"));

		if (!Files.exists(tempDirPath)) {
			try {
				Files.createDirectories(tempDirPath);
			}
			catch (IOException ioe) {
				_log.error("Unable to create " + tempDirPath, ioe);
			}
		}

		try {
			ModuleFrameworkUtilAdapter.initFramework();

			DBInitUtil.init();

			_arrayApplicationContext = new ArrayApplicationContext(
				PropsValues.SPRING_INFRASTRUCTURE_CONFIGS);

			servletContext.setAttribute(
				PortalApplicationContext.PARENT_APPLICATION_CONTEXT,
				_arrayApplicationContext);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		ClassLoaderPool.register(_portalServletContextName, portalClassLoader);
		ServletContextClassLoaderPool.register(
			_portalServletContextName, portalClassLoader);

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					_serviceWrapperRegistry = new ServiceWrapperRegistry();
				}

				@Override
				public void destroy() {
				}

			});

		serviceDependencyManager.registerDependencies(
			MessageBus.class, PortalExecutorManager.class,
			SchedulerEngineHelper.class,
			SingleDestinationMessageSenderFactory.class);

		FutureTask<Void> springInitTask = null;

		if (PropsValues.MODULE_FRAMEWORK_CONCURRENT_STARTUP_ENABLED) {
			springInitTask = new FutureTask<>(
				() -> {
					super.contextInitialized(servletContextEvent);

					return null;
				});

			Thread springInitThread = new Thread(
				springInitTask, "Portal Spring Init Thread");

			springInitThread.setDaemon(true);

			springInitThread.start();
		}

		try {
			ModuleFrameworkUtilAdapter.registerContext(
				_arrayApplicationContext);

			ModuleFrameworkUtilAdapter.startFramework();

			ModuleFrameworkUtilAdapter.startRuntime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (springInitTask == null) {
			super.contextInitialized(servletContextEvent);
		}
		else {
			try {
				springInitTask.get();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		InitUtil.registerSpringInitialized();

		ServletContextPool.put(_portalServletContextName, servletContext);

		ApplicationContext applicationContext =
			ContextLoader.getCurrentWebApplicationContext();

		BeanLocatorImpl beanLocatorImpl = new BeanLocatorImpl(
			portalClassLoader, applicationContext);

		PortalBeanLocatorUtil.setBeanLocator(beanLocatorImpl);

		ClassLoader classLoader = portalClassLoader;

		while (classLoader != null) {
			CachedIntrospectionResults.clearClassLoader(classLoader);

			classLoader = classLoader.getParent();
		}

		clearFilteredPropertyDescriptorsCache(
			applicationContext.getAutowireCapableBeanFactory());

		DynamicProxyCreator dynamicProxyCreator =
			DynamicProxyCreator.getDynamicProxyCreator();

		dynamicProxyCreator.clear();

		try {
			ModuleFrameworkUtilAdapter.registerContext(applicationContext);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		CustomJspBagRegistryUtil.getCustomJspBags();

		initListeners(servletContext);
	}

	protected void clearFilteredPropertyDescriptorsCache(
		AutowireCapableBeanFactory autowireCapableBeanFactory) {

		try {
			Map<Class<?>, PropertyDescriptor[]>
				filteredPropertyDescriptorsCache =
					(Map<Class<?>, PropertyDescriptor[]>)
						_FILTERED_PROPERTY_DESCRIPTORS_CACHE_FIELD.get(
							autowireCapableBeanFactory);

			filteredPropertyDescriptorsCache.clear();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void closeDataSource(String name) {
		DataSource dataSource = (DataSource)PortalBeanLocatorUtil.locate(name);

		if (dataSource instanceof DelegatingDataSource) {
			DelegatingDataSource delegatingDataSource =
				(DelegatingDataSource)dataSource;

			dataSource = delegatingDataSource.getTargetDataSource();
		}

		if (dataSource instanceof Closeable) {
			try {
				Closeable closeable = (Closeable)dataSource;

				closeable.close();
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}
	}

	@Override
	protected void customizeContext(
		ServletContext servletContext,
		ConfigurableWebApplicationContext configurableWebApplicationContext) {

		ConfigurableApplicationContextConfigurator
			configurableApplicationContextConfigurator =
				_arrayApplicationContext.getBean(
					"configurableApplicationContextConfigurator",
					ConfigurableApplicationContextConfigurator.class);

		configurableApplicationContextConfigurator.configure(
			configurableWebApplicationContext);
	}

	protected void initListeners(ServletContext servletContext) {
		if (PropsValues.SESSION_VERIFY_SERIALIZABLE_ATTRIBUTE) {
			servletContext.addListener(
				SerializableSessionAttributeListener.class);
		}

		servletContext.addListener(PortalSessionListener.class);
		servletContext.addListener(PortletSessionListenerManager.class);
	}

	private void _logJVMArguments() {
		if (!_log.isInfoEnabled()) {
			return;
		}

		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		List<String> inputArguments = runtimeMXBean.getInputArguments();

		StringBundler sb = new StringBundler(inputArguments.size() * 2);

		sb.append("JVM arguments: ");

		for (String inputArgument : inputArguments) {
			sb.append(inputArgument);
			sb.append(StringPool.SPACE);
		}

		if (!inputArguments.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		_log.info(sb.toString());
	}

	private static final Field _FILTERED_PROPERTY_DESCRIPTORS_CACHE_FIELD;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalContextLoaderListener.class);

	private static String _portalServletContextName = StringPool.BLANK;
	private static String _portalServletContextPath = StringPool.SLASH;

	static {
		try {
			_FILTERED_PROPERTY_DESCRIPTORS_CACHE_FIELD =
				ReflectionUtil.getDeclaredField(
					AbstractAutowireCapableBeanFactory.class,
					"filteredPropertyDescriptorsCache");
		}
		catch (Exception e) {
			throw new LoggedExceptionInInitializerError(e);
		}
	}

	private ArrayApplicationContext _arrayApplicationContext;
	private ServiceWrapperRegistry _serviceWrapperRegistry;

}