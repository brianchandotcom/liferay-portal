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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.util.PropsValues;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author Miguel Pastor
 */
public class ApplicationContextServicePublisher {

	public ApplicationContextServicePublisher(
		ApplicationContext applicationContext, BundleContext bundleContext) {

		_applicationContext = applicationContext;
		_bundleContext = bundleContext;
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
				_log.error("Unable to register service " + beanName, e);
			}

			if (bean != null) {
				registerService(bean);
			}
		}

		Bundle bundle = _bundleContext.getBundle();

		HashMapDictionary<String, Object> properties =
			new HashMapDictionary<>();

		properties.put(
			"org.springframework.context.service.name",
			bundle.getSymbolicName());

		ServiceRegistration<ApplicationContext> serviceRegistration =
			_bundleContext.registerService(
				ApplicationContext.class, _applicationContext, properties);

		_serviceRegistrations.add(serviceRegistration);
	}

	public void unregister() {
		for (ServiceRegistration<?> serviceReference : _serviceRegistrations) {
			serviceReference.unregister();
		}

		_serviceRegistrations.clear();
	}

	protected Class<?> getTargetClass(Object service) throws Exception {
		Class<?> clazz = service.getClass();

		if (ProxyUtil.isProxyClass(clazz)) {
			AdvisedSupport advisedSupport =
				ServiceBeanAopProxy.getAdvisedSupport(service);

			if (advisedSupport != null) {
				TargetSource targetSource = advisedSupport.getTargetSource();

				Object target = targetSource.getTarget();

				clazz = target.getClass();
			}
		}

		return clazz;
	}

	protected void registerService(Object bean) {
		OSGiBeanProperties osgiBeanProperties = null;

		try {
			osgiBeanProperties = AnnotationUtils.findAnnotation(
				getTargetClass(bean), OSGiBeanProperties.class);
		}
		catch (Exception e) {
			_log.error("Unable to register service " + bean, e);
		}

		Set<String> names = OSGiBeanProperties.Service.interfaces(
			bean, osgiBeanProperties,
			PropsValues.MODULE_FRAMEWORK_SERVICES_IGNORED_INTERFACES);

		if (names.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping registration because of an empty list of " +
						"interfaces");
			}

			return;
		}

		Bundle bundle = _bundleContext.getBundle();

		HashMapDictionary<String, Object> properties =
			new HashMapDictionary<>();

		properties.put("origin.bundle.symbolic.name", bundle.getSymbolicName());

		if (osgiBeanProperties != null) {
			properties.putAll(
				OSGiBeanProperties.Convert.toMap(osgiBeanProperties));
		}

		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				names.toArray(new String[names.size()]), bean, properties);

		_serviceRegistrations.add(serviceRegistration);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplicationContextServicePublisher.class);

	private final ApplicationContext _applicationContext;
	private final BundleContext _bundleContext;
	private final Set<ServiceRegistration<?>> _serviceRegistrations =
		new HashSet<>();

}