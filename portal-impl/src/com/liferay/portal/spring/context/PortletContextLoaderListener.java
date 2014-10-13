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
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Brian Wing Shun Chan
 * @see    PortletApplicationContext
 */
public class PortletContextLoaderListener extends ContextLoaderListener {

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

		Object parentApplicationContext = servletContext.getAttribute(
			_PARENT_APPLICATION_CONTEXT_KEY);

		if (parentApplicationContext instanceof
				ConfigurableApplicationContext) {

			servletContext.removeAttribute(_PARENT_APPLICATION_CONTEXT_KEY);

			ConfigurableApplicationContext configurableApplicationContext =
				(ConfigurableApplicationContext)parentApplicationContext;

			configurableApplicationContext.close();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		MethodCache.reset();

		ServletContext servletContext = servletContextEvent.getServletContext();

		Object previousApplicationContext = servletContext.getAttribute(
			WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		servletContext.removeAttribute(
			WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

		super.contextInitialized(servletContextEvent);

		PortletBeanFactoryCleaner.readBeans();

		ApplicationContext applicationContext =
			WebApplicationContextUtils.getWebApplicationContext(servletContext);

		BeanLocatorImpl beanLocatorImpl = new BeanLocatorImpl(
			classLoader, applicationContext);

		beanLocatorImpl.setPACLServletContextName(
			servletContext.getServletContextName());

		try {
			Class<?> beanLocatorUtilClass = Class.forName(
				"com.liferay.util.bean.PortletBeanLocatorUtil", true,
				classLoader);

			Method setBeanLocatorMethod = beanLocatorUtilClass.getMethod(
				"setBeanLocator", new Class[] {BeanLocator.class});

			setBeanLocatorMethod.invoke(
				beanLocatorUtilClass, new Object[] {beanLocatorImpl});

			PortletBeanLocatorUtil.setBeanLocator(
				servletContext.getServletContextName(), beanLocatorImpl);
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
	protected void customizeContext(
		ServletContext servletContext,
		ConfigurableWebApplicationContext configurableWebApplicationContext) {

		String configLocation = servletContext.getInitParameter(
			_PORTAL_CONFIG_LOCATION_PARAM);

		configurableWebApplicationContext.setConfigLocation(configLocation);

		configurableWebApplicationContext.addBeanFactoryPostProcessor(
			new PortletBeanFactoryPostProcessor());
	}

	@Override
	protected Class<?> determineContextClass(ServletContext servletContext) {
		return PortletApplicationContext.class;
	}

	@Override
	protected ApplicationContext loadParentContext(
		ServletContext servletContext) {

		try {
			ConfigurationFactoryUtil.getConfiguration(
				PortletClassLoaderUtil.getClassLoader(), "service");
		}
		catch (Exception e) {
			return null;
		}

		ApplicationContext applicationContext =
			new ClassPathXmlApplicationContext(
				PropsValues.SPRING_PORTLET_CONFIGS, true);

		servletContext.setAttribute(
			_PARENT_APPLICATION_CONTEXT_KEY, applicationContext);

		return applicationContext;
	}

	private static final String _PARENT_APPLICATION_CONTEXT_KEY =
		PortletContextLoaderListener.class.getName() +
			"#parentApplicationContext";

	private static final String _PORTAL_CONFIG_LOCATION_PARAM =
		"portalContextConfigLocation";

	private static final Log _log = LogFactoryUtil.getLog(
		PortletContextLoaderListener.class);

}