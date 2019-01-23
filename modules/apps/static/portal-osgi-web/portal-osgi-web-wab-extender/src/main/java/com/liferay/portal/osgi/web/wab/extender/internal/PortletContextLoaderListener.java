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

package com.liferay.portal.osgi.web.wab.extender.internal;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.felix.utils.log.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Brian Wing Shun Chan
 * @see    PortletApplicationContext
 */
public class PortletContextLoaderListener extends ContextLoaderListener {

	public PortletContextLoaderListener(Logger logger) {
		_logger = logger;
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();

		ClassLoader classLoader = ServletContextClassLoaderPool.getClassLoader(
			servletContext.getServletContextName());

		if (classLoader == null) {
			throw new IllegalStateException(
				"Unable to find the class loader for servlet context " +
					servletContext.getServletContextName());
		}

		try {
			Class<?> beanLocatorUtilClass = Class.forName(
				"com.liferay.util.bean.PortletBeanLocatorUtil", true,
				classLoader);

			Method setBeanLocatorMethod = beanLocatorUtilClass.getMethod(
				"setBeanLocator", new Class<?>[] {BeanLocator.class});

			setBeanLocatorMethod.invoke(
				beanLocatorUtilClass, new Object[] {null});

			PortletBeanLocatorUtil.setBeanLocator(
				servletContext.getServletContextName(), null);
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_WARNING, e.getMessage(), e);
		}

		super.contextDestroyed(servletContextEvent);
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		MethodKey.resetCache();

		ServletContext servletContext = servletContextEvent.getServletContext();

		Object previousApplicationContext = servletContext.getAttribute(
			WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		servletContext.removeAttribute(
			WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		ClassLoader classLoader = ServletContextClassLoaderPool.getClassLoader(
			servletContext.getServletContextName());

		if (classLoader == null) {
			throw new IllegalStateException(
				"Unable to find the class loader for servlet context " +
					servletContext.getServletContextName());
		}

		PortletClassLoaderUtil.setServletContextName(
			servletContext.getServletContextName());

		try {
			super.contextInitialized(servletContextEvent);
		}
		finally {
			PortletClassLoaderUtil.setServletContextName(null);
		}

		ApplicationContext applicationContext =
			WebApplicationContextUtils.getWebApplicationContext(servletContext);

		ModuleFrameworkUtilAdapter.registerContext(applicationContext);

		BeanLocatorImpl beanLocatorImpl = new BeanLocatorImpl(
			classLoader, applicationContext);

		try {
			Class<?> beanLocatorUtilClass = Class.forName(
				"com.liferay.util.bean.PortletBeanLocatorUtil", true,
				classLoader);

			Method setBeanLocatorMethod = beanLocatorUtilClass.getMethod(
				"setBeanLocator", new Class<?>[] {BeanLocator.class});

			setBeanLocatorMethod.invoke(
				beanLocatorUtilClass, new Object[] {beanLocatorImpl});

			PortletBeanLocatorUtil.setBeanLocator(
				servletContext.getServletContextName(), beanLocatorImpl);
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
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
	protected WebApplicationContext createWebApplicationContext(
		ServletContext servletContext) {

		return new PortletApplicationContext(_logger);
	}

	@Override
	protected void customizeContext(
		ServletContext servletContext,
		ConfigurableWebApplicationContext configurableWebApplicationContext) {

		String configLocation = servletContext.getInitParameter(
			_PORTAL_CONFIG_LOCATION_PARAM);

		configurableWebApplicationContext.setConfigLocation(configLocation);

		configurableWebApplicationContext.addApplicationListener(
			applicationEvent -> {
				if (applicationEvent instanceof ContextClosedEvent) {
					ContextClosedEvent contextClosedEvent =
						(ContextClosedEvent)applicationEvent;

					ModuleFrameworkUtilAdapter.unregisterContext(
						contextClosedEvent.getApplicationContext());
				}
			});

		configurableWebApplicationContext.addBeanFactoryPostProcessor(
			configurableListableBeanFactory -> {
				if ((configurableListableBeanFactory.getBeanDefinitionCount() >
						0) &&
					!configurableListableBeanFactory.containsBean(
						"liferayDataSource")) {

					configurableListableBeanFactory.registerSingleton(
						"liferayDataSource",
						InfrastructureUtil.getDataSource());
				}
			});

		ConfigurableApplicationContextConfigurator
			configurableApplicationContextConfigurator =
				(ConfigurableApplicationContextConfigurator)
					PortalBeanLocatorUtil.locate(
						"configurableApplicationContextConfigurator");

		configurableApplicationContextConfigurator.configure(
			configurableWebApplicationContext);
	}

	@Override
	protected ApplicationContext loadParentContext(
		ServletContext servletContext) {

		return super.loadParentContext(servletContext);
	}

	private static final String _PORTAL_CONFIG_LOCATION_PARAM =
		"portalContextConfigLocation";

	private final Logger _logger;

}