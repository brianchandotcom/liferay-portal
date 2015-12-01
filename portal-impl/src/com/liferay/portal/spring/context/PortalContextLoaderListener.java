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

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.dao.orm.hibernate.FieldInterceptionHelperUtil;
import com.liferay.portal.deploy.hot.CustomJspBagRegistryUtil;
import com.liferay.portal.deploy.hot.IndexerPostProcessorRegistry;
import com.liferay.portal.deploy.hot.ServiceWrapperRegistry;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.exception.LoggedExceptionInInitializerError;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.servlet.SerializableSessionAttributeListener;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ClearThreadLocalUtil;
import com.liferay.portal.kernel.util.ClearTimerThreadUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.security.lang.SecurityManagerUtil;
import com.liferay.portal.spring.bean.BeanReferenceRefreshUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.beans.PropertyDescriptor;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import java.lang.reflect.Field;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import javax.sql.DataSource;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
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
		PortalContextLoaderLifecycleThreadLocal.setDestroying(true);

		ThreadLocalCacheManager.destroy();

		if (_indexerPostProcessorRegistry != null) {
			_indexerPostProcessorRegistry.close();
		}

		if (_serviceWrapperRegistry != null) {
			_serviceWrapperRegistry.close();
		}

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
			ModuleFrameworkUtilAdapter.stopRuntime();
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

		closeDataSource("counterDataSourceImpl");

		closeDataSource("liferayDataSourceImpl");

		try {
			super.contextDestroyed(servletContextEvent);

			try {
				ModuleFrameworkUtilAdapter.stopFramework(
					PropsValues.MODULE_FRAMEWORK_STOP_WAIT_TIMEOUT);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			_arrayApplicationContext.close();
		}
		finally {
			PortalContextLoaderLifecycleThreadLocal.setDestroying(false);

			SecurityManagerUtil.destroy();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		Thread currentThread = Thread.currentThread();

		SystemProperties.load(currentThread.getContextClassLoader());

		DBFactoryUtil.reset();
		DeployManagerUtil.reset();
		InstancePool.reset();
		MethodCache.reset();
		PortalBeanLocatorUtil.reset();
		PortletBagPool.reset();

		ReferenceRegistry.releaseReferences();

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

		InitUtil.init();

		_portalServletContextName = servletContext.getServletContextName();

		if (_portalServletContextName == null) {
			_portalServletContextName = StringPool.BLANK;
		}

		if (ServerDetector.isJetty() &&
			_portalServletContextName.equals(StringPool.SLASH)) {

			_portalServletContextName = StringPool.BLANK;
		}

		_portalServletContextPath = servletContext.getContextPath();

		if (ServerDetector.isWebSphere() &&
			_portalServletContextPath.isEmpty()) {

			_portalServletContextName = StringPool.BLANK;
		}

		ClassPathUtil.initializeClassPaths(servletContext);

		File tempDir = (File)servletContext.getAttribute(
			JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

		PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR =
			tempDir.getAbsolutePath();

		try {
			ModuleFrameworkUtilAdapter.initFramework();

			_arrayApplicationContext = new ArrayApplicationContext(
				PropsValues.SPRING_INFRASTRUCTURE_CONFIGS);

			servletContext.setAttribute(
				PortalApplicationContext.PARENT_APPLICATION_CONTEXT,
				_arrayApplicationContext);

			ModuleFrameworkUtilAdapter.registerContext(
				_arrayApplicationContext);

			ModuleFrameworkUtilAdapter.startFramework();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void destroy() {
				}

				@Override
				public void dependenciesFulfilled() {
					_indexerPostProcessorRegistry =
						new IndexerPostProcessorRegistry();
					_serviceWrapperRegistry = new ServiceWrapperRegistry();
				}

			});

		serviceDependencyManager.registerDependencies(
			MessageBus.class, PortalExecutorManager.class,
			SchedulerEngineHelper.class,
			SingleDestinationMessageSenderFactory.class);

		ClassLoader portalClassLoader = ClassLoaderUtil.getPortalClassLoader();

		ClassLoaderPool.register(_portalServletContextName, portalClassLoader);

		PortalContextLoaderLifecycleThreadLocal.setInitializing(true);

		try {
			super.contextInitialized(servletContextEvent);
		}
		finally {
			PortalContextLoaderLifecycleThreadLocal.setInitializing(false);
		}

		ApplicationContext applicationContext =
			ContextLoader.getCurrentWebApplicationContext();

		try {
			BeanReferenceRefreshUtil.refresh(
				applicationContext.getAutowireCapableBeanFactory());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (PropsValues.CACHE_CLEAR_ON_CONTEXT_INITIALIZATION) {
			CacheRegistryUtil.clear();
			PortletContextBagPool.clear();
			WebAppPool.clear();

			TemplateResourceLoaderUtil.clearCache();

			ServletContextPool.clear();

			MultiVMPoolUtil.clear();
			SingleVMPoolUtil.clear();
		}

		ServletContextPool.put(_portalServletContextName, servletContext);

		BeanLocatorImpl beanLocatorImpl = new BeanLocatorImpl(
			portalClassLoader, applicationContext);

		PortalBeanLocatorUtil.setBeanLocator(beanLocatorImpl);

		ClassLoader classLoader = portalClassLoader;

		while (classLoader != null) {
			CachedIntrospectionResults.clearClassLoader(classLoader);

			classLoader = classLoader.getParent();
		}

		AutowireCapableBeanFactory autowireCapableBeanFactory =
			applicationContext.getAutowireCapableBeanFactory();

		clearFilteredPropertyDescriptorsCache(autowireCapableBeanFactory);

		try {
			ModuleFrameworkUtilAdapter.registerContext(applicationContext);

			ModuleFrameworkUtilAdapter.startRuntime();
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

		if (dataSource instanceof Closeable) {
			try {
				Closeable closeable = (Closeable)dataSource;

				closeable.close();
			}
			catch (IOException e) {
				_log.error(e, e);
			}
		}
	}

	protected void initListeners(ServletContext servletContext) {
		if (PropsValues.SESSION_VERIFY_SERIALIZABLE_ATTRIBUTE) {
			servletContext.addListener(
				SerializableSessionAttributeListener.class);
		}
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
	private IndexerPostProcessorRegistry _indexerPostProcessorRegistry;
	private ServiceWrapperRegistry _serviceWrapperRegistry;

}