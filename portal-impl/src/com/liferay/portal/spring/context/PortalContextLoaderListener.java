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
import com.liferay.portal.cache.ehcache.ClearEhcacheThreadUtil;
import com.liferay.portal.deploy.hot.IndexerPostProcessorRegistry;
import com.liferay.portal.deploy.hot.ServiceWrapperRegistry;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.servlet.SerializableSessionAttributeListener;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.CharBufferPool;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ClearThreadLocalUtil;
import com.liferay.portal.kernel.util.ClearTimerThreadUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.security.lang.SecurityManagerUtil;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.spring.bean.BeanReferenceRefreshUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import java.beans.PropertyDescriptor;

import java.io.File;

import java.lang.reflect.Field;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

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

	public static String getPortalServlerContextName() {
		return _portalServlerContextName;
	}

	public static String getPortalServletContextPath() {
		return _portalServletContextPath;
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		PortalContextLoaderLifecycleThreadLocal.setDestroying(true);

		ThreadLocalCacheManager.destroy();

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
			ClearEhcacheThreadUtil.clearEhcacheReplicationThread();
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

		_indexerPostProcessorRegistry.close();
		_serviceWrapperRegistry.close();

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

		try {
			SettingsFactoryUtil.clearCache();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			super.contextDestroyed(servletContextEvent);

			try {
				ModuleFrameworkUtilAdapter.stopFramework();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
		finally {
			PortalContextLoaderLifecycleThreadLocal.setDestroying(false);

			SecurityManagerUtil.destroy();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		SystemProperties.reload();

		DBFactoryUtil.reset();
		DeployManagerUtil.reset();
		InstancePool.reset();
		MethodCache.reset();
		PortalBeanLocatorUtil.reset();
		PortletBagPool.reset();

		ReferenceRegistry.releaseReferences();

		InitUtil.init();

		final ServletContext servletContext =
			servletContextEvent.getServletContext();

		_portalServlerContextName = servletContext.getServletContextName();

		if (_portalServlerContextName == null) {
			_portalServlerContextName = StringPool.BLANK;
		}

		if (ServerDetector.isJetty() &&
			_portalServlerContextName.equals(StringPool.SLASH)) {

			_portalServlerContextName = StringPool.BLANK;
		}

		_portalServletContextPath = servletContext.getContextPath();

		if (ServerDetector.isWebSphere() &&
			_portalServletContextPath.isEmpty()) {

			_portalServlerContextName = StringPool.BLANK;
		}

		ClassPathUtil.initializeClassPaths(servletContext);

		CacheRegistryUtil.clear();
		CharBufferPool.cleanUp();
		PortletContextBagPool.clear();
		WebAppPool.clear();

		File tempDir = (File)servletContext.getAttribute(
			JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

		PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR =
			tempDir.getAbsolutePath();

		try {
			ModuleFrameworkUtilAdapter.startFramework();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

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
			BeanReferenceRefreshUtil.refresh(applicationContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (PropsValues.CACHE_CLEAR_ON_CONTEXT_INITIALIZATION) {
			FinderCacheUtil.clearCache();
			FinderCacheUtil.clearLocalCache();
			EntityCacheUtil.clearCache();
			EntityCacheUtil.clearLocalCache();
			PermissionCacheUtil.clearCache();
			PermissionCacheUtil.clearLocalCache();
			TemplateResourceLoaderUtil.clearCache();
			WikiCacheUtil.clearCache(0);

			ServletContextPool.clear();

			CacheUtil.clearCache();
			MultiVMPoolUtil.clear();
			SingleVMPoolUtil.clear();
			WebCachePoolUtil.clear();
		}

		ClassLoader portalClassLoader = ClassLoaderUtil.getPortalClassLoader();

		ClassLoaderPool.register(_portalServlerContextName, portalClassLoader);

		ServletContextPool.put(_portalServlerContextName, servletContext);

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

		_indexerPostProcessorRegistry = new IndexerPostProcessorRegistry();
		_serviceWrapperRegistry = new ServiceWrapperRegistry();

		try {
			PortalLifecycleUtil.register(
				new PortalLifecycle() {

					@Override
					public void portalInit() {
						ModuleFrameworkUtilAdapter.registerContext(
							servletContext);
					}

					@Override
					public void portalDestroy() {
					}

				});

			ModuleFrameworkUtilAdapter.registerContext(applicationContext);

			ModuleFrameworkUtilAdapter.startRuntime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		initListeners(servletContext);
	}

	protected void clearFilteredPropertyDescriptorsCache(
		AutowireCapableBeanFactory autowireCapableBeanFactory) {

		try {
			Map<Class<?>, PropertyDescriptor[]>
				filteredPropertyDescriptorsCache =
					(Map<Class<?>, PropertyDescriptor[]>)
						_filteredPropertyDescriptorsCacheField.get(
							autowireCapableBeanFactory);

			filteredPropertyDescriptorsCache.clear();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void initListeners(ServletContext servletContext) {
		if (PropsValues.SESSION_VERIFY_SERIALIZABLE_ATTRIBUTE) {
			servletContext.addListener(
				SerializableSessionAttributeListener.class);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalContextLoaderListener.class);

	private static Field _filteredPropertyDescriptorsCacheField;
	private static String _portalServlerContextName = StringPool.BLANK;
	private static String _portalServletContextPath = StringPool.SLASH;

	private IndexerPostProcessorRegistry _indexerPostProcessorRegistry;
	private ServiceWrapperRegistry _serviceWrapperRegistry;

	static {
		try {
			_filteredPropertyDescriptorsCacheField =
				ReflectionUtil.getDeclaredField(
					AbstractAutowireCapableBeanFactory.class,
					"filteredPropertyDescriptorsCache");
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

}