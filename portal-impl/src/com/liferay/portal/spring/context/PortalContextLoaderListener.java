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

package com.liferay.portal.spring.context;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.cache.ehcache.ClearEhcacheThreadUtil;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.servlet.DirectServletRegistry;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.CharBufferPool;
import com.liferay.portal.kernel.util.ClearThreadLocalUtil;
import com.liferay.portal.kernel.util.ClearTimerThreadUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.osgi.service.OSGiServiceUtil;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.velocity.LiferayResourceCacheUtil;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import java.beans.PropertyDescriptor;

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

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		HotDeployUtil.reset();
		InstancePool.reset();
		MethodCache.reset();
		PortletBagPool.reset();

		ReferenceRegistry.releaseReferences();

		InitUtil.init();

		ServletContext servletContext = servletContextEvent.getServletContext();

		ClassPathUtil.initializeClassPaths(servletContext);

		DirectServletRegistry.clearServlets();

		CacheRegistryUtil.clear();
		CharBufferPool.cleanUp();
		PortletContextBagPool.clear();
		WebAppPool.clear();

		try {
			OSGiServiceUtil.init();

			OSGiServiceUtil.start();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		PortalContextLoaderLifecycleThreadLocal.setInitializing(true);

		try {
			super.contextInitialized(servletContextEvent);
		}
		finally {
			PortalContextLoaderLifecycleThreadLocal.setInitializing(false);
		}

		FinderCacheUtil.clearCache();
		FinderCacheUtil.clearLocalCache();
		EntityCacheUtil.clearCache();
		EntityCacheUtil.clearLocalCache();
		LiferayResourceCacheUtil.clear();
		PermissionCacheUtil.clearCache();
		PermissionCacheUtil.clearLocalCache();
		WikiCacheUtil.clearCache(0);

		ServletContextPool.clear();

		CacheUtil.clearCache();
		MultiVMPoolUtil.clear();
		SingleVMPoolUtil.clear();
		WebCachePoolUtil.clear();

		ApplicationContext applicationContext =
			ContextLoader.getCurrentWebApplicationContext();

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		BeanLocator beanLocator = new BeanLocatorImpl(
			portalClassLoader, applicationContext);

		PortalBeanLocatorUtil.setBeanLocator(beanLocator);

		ClassLoader classLoader = portalClassLoader;

		while (classLoader != null) {
			CachedIntrospectionResults.clearClassLoader(classLoader);

			classLoader = classLoader.getParent();
		}

		AutowireCapableBeanFactory autowireCapableBeanFactory =
			applicationContext.getAutowireCapableBeanFactory();

		clearFilteredPropertyDescriptorsCache(autowireCapableBeanFactory);

		try {
			OSGiServiceUtil.registerContext(applicationContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		PortalContextLoaderLifecycleThreadLocal.setDestroying(true);

		try {
			OSGiServiceUtil.stop();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

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
			super.contextDestroyed(event);
		}
		finally {
			PortalContextLoaderLifecycleThreadLocal.setDestroying(false);
		}
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

	private static Field _filteredPropertyDescriptorsCacheField;

	private static Log _log = LogFactoryUtil.getLog(
		PortalContextLoaderListener.class);

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