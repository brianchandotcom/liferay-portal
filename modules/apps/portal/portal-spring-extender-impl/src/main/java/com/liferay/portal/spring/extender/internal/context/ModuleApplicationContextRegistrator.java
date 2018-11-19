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

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;
import com.liferay.portal.spring.extender.internal.bean.ApplicationContextServicePublisherUtil;
import com.liferay.portal.spring.extender.internal.classloader.BundleResolverClassLoader;

import java.beans.Introspector;

import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Miguel Pastor
 */
public class ModuleApplicationContextRegistrator {

	public ModuleApplicationContextRegistrator(
		ConfigurableApplicationContextConfigurator
			configurableApplicationContextConfigurator,
		Bundle extendeeBundle, Bundle extenderBundle) {

		_configurableApplicationContextConfigurator =
			configurableApplicationContextConfigurator;
		_extendeeBundle = extendeeBundle;
		_extenderBundle = extenderBundle;
	}

	protected void start() throws Exception {
		try {
			ClassLoader classLoader = new BundleResolverClassLoader(
				_extendeeBundle, _extenderBundle);

			Dictionary<String, String> headers = _extendeeBundle.getHeaders(
				StringPool.BLANK);

			_configurableApplicationContext = new ModuleApplicationContext(
				_extendeeBundle, classLoader,
				StringUtil.split(
					headers.get("Liferay-Spring-Context"), CharPool.COMMA));

			_configurableApplicationContext.addBeanFactoryPostProcessor(
				new ModuleBeanFactoryPostProcessor(
					_extendeeBundle.getBundleContext()));

			ApplicationContext parentApplicationContext =
				ParentModuleApplicationContextHolder.getApplicationContext(
					_extendeeBundle);

			if (parentApplicationContext != null) {
				_configurableApplicationContext.setParent(
					parentApplicationContext);
			}

			_configurableApplicationContextConfigurator.configure(
				_configurableApplicationContext);

			_configurableApplicationContext.refresh();

			BundleWiring bundleWiring = _extendeeBundle.adapt(
				BundleWiring.class);

			PortletBeanLocatorUtil.setBeanLocator(
				_extendeeBundle.getSymbolicName(),
				new BeanLocatorImpl(
					bundleWiring.getClassLoader(),
					_configurableApplicationContext));

			_serviceRegistrations =
				ApplicationContextServicePublisherUtil.registerContext(
					_configurableApplicationContext,
					_extendeeBundle.getBundleContext(), false);
		}
		catch (Exception e) {
			throw new Exception(
				"Unable to start " + _extendeeBundle.getSymbolicName(), e);
		}
	}

	protected void stop() throws Exception {
		BundleWiring bundleWiring = _extendeeBundle.adapt(BundleWiring.class);

		CachedIntrospectionResults.clearClassLoader(
			bundleWiring.getClassLoader());

		Introspector.flushCaches();

		ApplicationContextServicePublisherUtil.unregisterContext(
			_serviceRegistrations);

		PortletBeanLocatorUtil.setBeanLocator(
			_extendeeBundle.getSymbolicName(), null);

		_configurableApplicationContext.close();

		_configurableApplicationContext = null;
	}

	private ConfigurableApplicationContext _configurableApplicationContext;
	private final ConfigurableApplicationContextConfigurator
		_configurableApplicationContextConfigurator;
	private final Bundle _extendeeBundle;
	private final Bundle _extenderBundle;
	private List<ServiceRegistration<?>> _serviceRegistrations;

}