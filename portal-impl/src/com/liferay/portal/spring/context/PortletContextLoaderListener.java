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
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.concurrent.LockRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.StringPool;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Brian Wing Shun Chan
 * @see    PortletApplicationContext
 * @see    PortletContextLoader
 */
public class PortletContextLoaderListener extends ContextLoaderListener {

	public static String getLockKey(ServletContext servletContext) {
		return getLockKey(servletContext.getContextPath());
	}

	public static String getLockKey(String contextPath) {
		return PortletContextLoaderListener.class.getName().concat(
			StringPool.PERIOD).concat(contextPath);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

		ServletContext servletContext = servletContextEvent.getServletContext();

		try {
			Class<?> beanLocatorUtilClass = Class.forName(
				"com.liferay.util.bean.PortletBeanLocatorUtil", true,
				classLoader);

			Method setBeanLocatorMethod = beanLocatorUtilClass.getMethod(
				"setBeanLocator", new Class[] {BeanLocator.class});

			setBeanLocatorMethod.invoke(
				beanLocatorUtilClass, new Object[] {null});

			PortletBeanLocatorUtil.setBeanLocator(
				servletContext.getServletContextName(), null);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		super.contextDestroyed(servletContextEvent);
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		MethodCache.reset();

		ServletContext servletContext = servletContextEvent.getServletContext();

		Object previousApplicationContext = servletContext.getAttribute(
			WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		servletContext.removeAttribute(
			WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		try {
			super.contextInitialized(servletContextEvent);
		}
		finally {
			String lockKey = getLockKey(servletContext);

			LockRegistry.freeLock(lockKey, lockKey, true);
		}

		PortletBeanFactoryCleaner.readBeans();

		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

		ApplicationContext applicationContext =
			WebApplicationContextUtils.getWebApplicationContext(servletContext);

		BeanLocator beanLocator = new BeanLocatorImpl(
			classLoader, applicationContext);

		try {
			Class<?> beanLocatorUtilClass = Class.forName(
				"com.liferay.util.bean.PortletBeanLocatorUtil", true,
				classLoader);

			Method setBeanLocatorMethod = beanLocatorUtilClass.getMethod(
				"setBeanLocator", new Class[] {BeanLocator.class});

			setBeanLocatorMethod.invoke(
				beanLocatorUtilClass, new Object[] {beanLocator});

			PortletBeanLocatorUtil.setBeanLocator(
				servletContext.getServletContextName(), beanLocator);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (previousApplicationContext == null) {
			servletContext.removeAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		}
		else {
			servletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				previousApplicationContext);
		}
	}

	@Override
	protected ContextLoader createContextLoader() {
		return new PortletContextLoader();
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletContextLoaderListener.class);

}