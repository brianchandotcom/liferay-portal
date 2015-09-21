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

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.service.configuration.configurator.ServiceConfigurator;
import com.liferay.portal.spring.bean.BeanReferenceRefreshUtil;
import com.liferay.portal.spring.extender.internal.bean.ApplicationContextServicePublisher;
import com.liferay.portal.spring.extender.internal.bundle.CompositeResourceLoaderBundle;
import com.liferay.portal.spring.extender.internal.classloader.BundleResolverClassLoader;
import com.liferay.portal.spring.extender.loader.ModuleResourceLoader;

import java.beans.Introspector;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Miguel Pastor
 */
public class ModuleApplicationContextRegistrator {

	public ModuleApplicationContextRegistrator(
		Bundle extendeeBundle, Bundle extenderBundle,
		ServiceConfigurator serviceConfigurator) {

		_extendeeBundle = extendeeBundle;
		_extenderBundle = extenderBundle;
		_serviceConfigurator = serviceConfigurator;

		BundleWiring bundleWiring = _extendeeBundle.adapt(BundleWiring.class);

		_extendeeClassLoader = bundleWiring.getClassLoader();

		_logger = new Logger(_extendeeBundle.getBundleContext());
	}

	protected void start() throws Exception {
		try {
			ConfigurableApplicationContext configurableApplicationContext =
				_createApplicationContext(_extenderBundle, _extendeeBundle);

			_registerBeanLocator(
				_extendeeBundle, configurableApplicationContext);

			_refreshBeanFactory(configurableApplicationContext);

			_serviceConfigurator.initServices(
				new ModuleResourceLoader(_extendeeBundle),
				_extendeeClassLoader);

			_configurableApplicationContext = configurableApplicationContext;

			_applicationContextServicePublisher =
				new ApplicationContextServicePublisher(
					_configurableApplicationContext,
				_extendeeBundle.getBundleContext());

			_applicationContextServicePublisher.register();
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				"Unable to start " + _extendeeBundle.getSymbolicName(), e);

			throw e;
		}
	}

	protected void stop() throws Exception {
		_cleanInstropectionCaches(_extendeeBundle);

		_applicationContextServicePublisher.unregister();

		PortletBeanLocatorUtil.setBeanLocator(
			_extendeeBundle.getSymbolicName(), null);

		_serviceConfigurator.destroyServices(
			new ModuleResourceLoader(_extendeeBundle), _extendeeClassLoader);

		_configurableApplicationContext.close();

		_configurableApplicationContext = null;

		_extendeeBundle = null;
		_extenderBundle = null;
	}

	private void _cleanInstropectionCaches(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		CachedIntrospectionResults.clearClassLoader(
			bundleWiring.getClassLoader());

		Introspector.flushCaches();
	}

	private ConfigurableApplicationContext _createApplicationContext(
			Bundle extender, Bundle extendee)
		throws RuntimeException {

		SpringContextHeaderParser springContextHeaderParser =
			new SpringContextHeaderParser(extendee);

		String[] beanDefinitionFileNames =
			springContextHeaderParser.getBeanDefinitionFileNames();

		if (ArrayUtil.isEmpty(beanDefinitionFileNames)) {
			return null;
		}

		ClassLoader classLoader = new BundleResolverClassLoader(
			extendee, extender);

		Bundle compositeResourceLoaderBundle =
			new CompositeResourceLoaderBundle(extendee, extender);

		ModuleApplicationContext moduleApplicationContext =
			new ModuleApplicationContext(
				compositeResourceLoaderBundle, classLoader,
				beanDefinitionFileNames);

		moduleApplicationContext.addBeanFactoryPostProcessor(
			new ModuleBeanFactoryPostProcessor(
				classLoader, extendee.getBundleContext()));

		moduleApplicationContext.refresh();

		return moduleApplicationContext;
	}

	private void _refreshBeanFactory(ApplicationContext applicationContext) {
		try {
			BeanReferenceRefreshUtil.refresh(
				applicationContext.getAutowireCapableBeanFactory());
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				"Unable to refresh " + applicationContext.getDisplayName() +
					". This may result in memory leaks on multiple " +
						"redeployments.");
		}
	}

	private void _registerBeanLocator(
		Bundle bundle, ApplicationContext applicationContext) {

		ClassLoader classLoader = new BundleResolverClassLoader(bundle);

		PortletBeanLocatorUtil.setBeanLocator(
			bundle.getSymbolicName(),
			new BeanLocatorImpl(classLoader, applicationContext));
	}

	private ApplicationContextServicePublisher
		_applicationContextServicePublisher;
	private ConfigurableApplicationContext _configurableApplicationContext;
	private Bundle _extendeeBundle;
	private final ClassLoader _extendeeClassLoader;
	private Bundle _extenderBundle;
	private final Logger _logger;
	private final ServiceConfigurator _serviceConfigurator;

}