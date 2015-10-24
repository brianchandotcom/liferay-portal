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

package com.liferay.portal.wab.generator;

import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Raymond Augé
 */
public class ServiceBeanPostProcessor
	implements ApplicationContextAware, ApplicationListener<ContextClosedEvent>,
			   BeanPostProcessor {

	@Override
	public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		Class<?>[] interfaces = ReflectionUtil.getInterfaces(
			bean, _classLoader);

		String[] classNames = new String[interfaces.length];

		for (int i = 0; i < interfaces.length; i++) {
			classNames[i] = interfaces[i].getName();
		}

		if (classNames.length == 0) {
			return bean;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("bean.id", beanName);

		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(classNames, bean, properties);

		_serviceRegistrations.add(serviceRegistration);

		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {

		WebApplicationContext webApplicationContext =
			(WebApplicationContext)applicationContext;

		ServletContext servletContext =
			webApplicationContext.getServletContext();

		_bundleContext = (BundleContext)servletContext.getAttribute(
			"osgi-bundlecontext");

		Bundle bundle = _bundleContext.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();
	}

	private BundleContext _bundleContext;
	private ClassLoader _classLoader;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new CopyOnWriteArrayList<>();

}