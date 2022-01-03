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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;
import com.liferay.portal.spring.extender.internal.bean.ApplicationContextServicePublisherUtil;
import com.liferay.portal.spring.extender.internal.loader.ModuleAggregareClassLoader;

import java.beans.Introspector;

import java.util.Dictionary;
import java.util.List;

import javax.sql.DataSource;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;

import org.springframework.beans.CachedIntrospectionResults;

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

		BundleWiring extendeeBundleWiring = _extendeeBundle.adapt(
			BundleWiring.class);

		_extendeeClassLoader = extendeeBundleWiring.getClassLoader();

		_classLoader = new ModuleAggregareClassLoader(
			_extendeeClassLoader, _extendeeBundle.getSymbolicName());

		Dictionary<String, String> headers = _extendeeBundle.getHeaders(
			StringPool.BLANK);

		_moduleApplicationContext = new ModuleApplicationContext(
			_extendeeBundle, _extendeeClassLoader, _classLoader,
			StringUtil.split(
				headers.get("Liferay-Spring-Context"), CharPool.COMMA));

		_moduleApplicationContext.addBeanFactoryPostProcessor(
			beanFactory -> ModuleApplicationContext.registerDataSourceBean(
				beanFactory, _extendeeClassLoader));

		_moduleApplicationContext.addBeanFactoryPostProcessor(
			new ModuleBeanFactoryPostProcessor(
				_extendeeBundle.getBundleContext()));

		_configurableApplicationContextConfigurator.configure(
			_moduleApplicationContext);

		_registerDataSource();
	}

	protected void start() throws Exception {
		try {
			_moduleApplicationContext.refresh();

			_registerDataSource();

			BundleWiring bundleWiring = _extendeeBundle.adapt(
				BundleWiring.class);

			PortletBeanLocatorUtil.setBeanLocator(
				_extendeeBundle.getSymbolicName(),
				new BeanLocatorImpl(
					bundleWiring.getClassLoader(), _moduleApplicationContext));

			_serviceRegistrations =
				ApplicationContextServicePublisherUtil.registerContext(
					_moduleApplicationContext,
					_extendeeBundle.getBundleContext());
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to start " + _extendeeBundle.getSymbolicName(),
				exception);
		}
		finally {
			CachedIntrospectionResults.clearClassLoader(_classLoader);

			CachedIntrospectionResults.clearClassLoader(_extendeeClassLoader);

			BundleWiring extenderBundleWiring = _extenderBundle.adapt(
				BundleWiring.class);

			CachedIntrospectionResults.clearClassLoader(
				extenderBundleWiring.getClassLoader());

			Introspector.flushCaches();
		}
	}

	private void _registerDataSource() {
		if (_dataSourceServiceRegistration == null) {
			BundleContext bundleContext = _extendeeBundle.getBundleContext();

			_dataSourceServiceRegistration = bundleContext.registerService(
				DataSource.class, _moduleApplicationContext.getDataSource(),
				MapUtil.singletonDictionary(
					"origin.bundle.symbolic.name",
					_extendeeBundle.getSymbolicName()));
		}
	}

	private void _stop() {
		ApplicationContextServicePublisherUtil.unregisterContext(
			_serviceRegistrations);

		PortletBeanLocatorUtil.setBeanLocator(
			_extendeeBundle.getSymbolicName(), null);

		_dataSourceServiceRegistration.unregister();

		_dataSourceServiceRegistration = null;

		_moduleApplicationContext.close();
	}

	private final ClassLoader _classLoader;
	private final ConfigurableApplicationContextConfigurator
		_configurableApplicationContextConfigurator;
	private volatile ServiceRegistration<DataSource>
		_dataSourceServiceRegistration;
	private final Bundle _extendeeBundle;
	private final ClassLoader _extendeeClassLoader;
	private final Bundle _extenderBundle;
	private final ModuleApplicationContext _moduleApplicationContext;
	private List<ServiceRegistration<?>> _serviceRegistrations;

}