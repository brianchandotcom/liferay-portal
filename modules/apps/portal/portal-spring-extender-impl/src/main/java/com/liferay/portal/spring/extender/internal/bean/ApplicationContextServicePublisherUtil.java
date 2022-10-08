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

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ModuleFrameworkPropsValues;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Miguel Pastor
 * @author Preston Crary
 */
public class ApplicationContextServicePublisherUtil {

	public static List<ServiceRegistration<?>> registerContext(
		ConfigurableApplicationContext configurableApplicationContext,
		BundleContext bundleContext) {

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		ConfigurableListableBeanFactory configurableListableBeanFactory =
			configurableApplicationContext.getBeanFactory();

		Iterator<String> iterator =
			configurableListableBeanFactory.getBeanNamesIterator();

		iterator.forEachRemaining(
			beanName -> {
				try {
					ServiceRegistration<?> serviceRegistration =
						_registerService(
							bundleContext, beanName,
							configurableApplicationContext.getBean(beanName));

					if (serviceRegistration != null) {
						serviceRegistrations.add(serviceRegistration);
					}
				}
				catch (Exception exception) {
					_log.error(
						"Unable to register service " + beanName, exception);
				}
			});

		Bundle bundle = bundleContext.getBundle();

		ServiceRegistration<ApplicationContext> serviceRegistration =
			bundleContext.registerService(
				ApplicationContext.class, configurableApplicationContext,
				HashMapDictionaryBuilder.<String, Object>put(
					"org.springframework.context.service.name",
					bundle.getSymbolicName()
				).build());

		serviceRegistrations.add(serviceRegistration);

		return serviceRegistrations;
	}

	public static void unregisterContext(
		List<ServiceRegistration<?>> serviceRegistrations) {

		if (serviceRegistrations != null) {
			for (ServiceRegistration<?> serviceReference :
					serviceRegistrations) {

				serviceReference.unregister();
			}

			serviceRegistrations.clear();
		}
	}

	private static ServiceRegistration<?> _registerService(
		BundleContext bundleContext, String beanName, Object bean) {

		Class<?> clazz = bean.getClass();

		if (ProxyUtil.isProxyClass(clazz)) {
			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(bean);

			Class<?> invocationHandlerClass = invocationHandler.getClass();

			try {
				Method method = invocationHandlerClass.getMethod("getTarget");

				Object target = method.invoke(invocationHandler);

				clazz = target.getClass();
			}
			catch (ReflectiveOperationException reflectiveOperationException) {
				if (_log.isDebugEnabled()) {
					_log.debug(reflectiveOperationException);
				}
			}
		}

		OSGiBeanProperties osgiBeanProperties = AnnotationLocator.locate(
			clazz, OSGiBeanProperties.class);

		Set<String> names = OSGiBeanProperties.Service.interfaceNames(
			bean, osgiBeanProperties,
			ModuleFrameworkPropsValues.
				MODULE_FRAMEWORK_SERVICES_IGNORED_INTERFACES);

		if (names.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping registration because of an empty list of " +
						"interfaces");
			}

			return null;
		}

		HashMapDictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"bean.id", beanName
			).put(
				"origin.bundle.symbolic.name",
				() -> {
					Bundle bundle = bundleContext.getBundle();

					return bundle.getSymbolicName();
				}
			).build();

		if (osgiBeanProperties != null) {
			properties.putAll(
				OSGiBeanProperties.Convert.toMap(osgiBeanProperties));
		}

		return bundleContext.registerService(
			names.toArray(new String[0]), bean, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplicationContextServicePublisherUtil.class);

}