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

package com.liferay.portal.spring.extender.internal.bean;

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.ApplicationContext;

/**
 * @author Miguel Pastor
 */
public class ApplicationContextServicePublisher {

	public ApplicationContextServicePublisher(
		ApplicationContext applicationContext, BundleContext bundleContext) {

		_applicationContext = applicationContext;
		_bundleContext = bundleContext;

		_log = new Logger(bundleContext);
	}

	public void register() {
		for (String beanName : _applicationContext.getBeanDefinitionNames()) {
			Object bean = null;

			try {
				bean = _applicationContext.getBean(beanName);
			}
			catch (BeanIsAbstractException biae) {
			}
			catch (Exception e) {
				_log.log(
					Logger.LOG_ERROR, "Unable to register service " + beanName,
					e);
			}

			if (bean != null) {
				registerService(_bundleContext, bean);
			}
		}

		Bundle bundle = _bundleContext.getBundle();

		registerApplicationContext(
			_applicationContext, bundle.getSymbolicName());
	}

	public void unregister() {
		for (ServiceRegistration<?> serviceReference : _serviceRegistrations) {
			serviceReference.unregister();
		}

		_serviceRegistrations.clear();
	}

	protected Dictionary<String, Object> getBeanProperties(Object bean) {
		HashMapDictionary<String, Object> properties =
			new HashMapDictionary<>();

		Map<String, Object> osgiBeanProperties =
			OSGiBeanProperties.Convert.fromObject(bean);

		if (osgiBeanProperties != null) {
			properties.putAll(osgiBeanProperties);
		}

		return properties;
	}

	protected void registerApplicationContext(
		ApplicationContext applicationContext, String bundleSymbolicName) {

		HashMapDictionary<String, Object> properties =
			new HashMapDictionary<>();

		properties.put(
			"org.springframework.context.service.name", bundleSymbolicName);

		registerService(
			_bundleContext, applicationContext,
			Arrays.asList(ApplicationContext.class.getName()), properties);
	}

	protected void registerService(BundleContext bundleContext, Object bean) {
		Set<Class<?>> interfaces = OSGiBeanProperties.Service.interfaces(bean);

		interfaces.add(bean.getClass());

		List<String> names = new ArrayList<>(interfaces.size());

		for (Class<?> interfaceClass : interfaces) {
			names.add(interfaceClass.getName());
		}

		registerService(bundleContext, bean, names, getBeanProperties(bean));
	}

	protected void registerService(
		BundleContext bundleContext, Object bean, List<String> interfaces,
		Dictionary<String, Object> properties) {

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				interfaces.toArray(new String[interfaces.size()]), bean,
				properties);

		_serviceRegistrations.add(serviceRegistration);
	}

	private final ApplicationContext _applicationContext;
	private final BundleContext _bundleContext;
	private final Logger _log;
	private final Set<ServiceRegistration<?>> _serviceRegistrations =
		new HashSet<>();

}