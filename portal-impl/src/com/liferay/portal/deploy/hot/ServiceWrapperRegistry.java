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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.lang.reflect.Method;

/**
 * @author Raymond Augé
 */
public class ServiceWrapperRegistry {

	public ServiceWrapperRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ServiceWrapper.class.getName(),
			new ServiceWrapperServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceWrapperRegistry.class);

	private final ServiceTracker<ServiceWrapper<?>, ServiceBag<?>>
		_serviceTracker;

	private static class ServiceWrapperServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ServiceWrapper<?>, ServiceBag<?>> {

		@Override
		public ServiceBag<?> addingService(
			ServiceReference<ServiceWrapper<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ServiceWrapper<?> serviceWrapper = registry.getService(
				serviceReference);

			try {
				return _getServiceBag(serviceWrapper);
			}
			catch (Throwable t) {
				_log.error(
					"Unable to get service bag for " +
						serviceWrapper.getClass(),
					t);
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<ServiceWrapper<?>> serviceReference,
			ServiceBag<?> serviceHolder) {
		}

		@Override
		public void removedService(
			ServiceReference<ServiceWrapper<?>> serviceReference,
			ServiceBag<?> serviceBag) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			try {
				serviceBag.replace();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		private <T> ServiceBag<?> _getServiceBag(
				ServiceWrapper<T> serviceWrapper)
			throws NoSuchMethodException {

			Class<?> clazz = serviceWrapper.getClass();

			Method method = clazz.getMethod(
				"getWrappedService", new Class<?>[0]);

			Class<?> serviceTypeClass = method.getReturnType();

			Object service = null;
			ServiceReference<?> serviceReference = null;

			Registry registry = RegistryUtil.getRegistry();

			try {
				service = PortalBeanLocatorUtil.locate(
					serviceTypeClass.getName());
			}
			catch (BeanLocatorException ble) {
				serviceReference = registry.getServiceReference(
					serviceTypeClass);

				service = registry.getService(serviceReference);
			}

			Object serviceProxy = service;

			if (!ProxyUtil.isProxyClass(serviceProxy.getClass())) {
				_log.error(
					"Service hooks require Spring to be configured to use " +
						"JdkDynamicProxy and will not work with CGLIB");

				if (serviceReference != null) {
					registry.ungetService(serviceReference);
				}

				return null;
			}

			ClassLoader classLoader = clazz.getClassLoader();

			try {
				AopInvocationHandler aopInvocationHandler =
					ProxyUtil.fetchInvocationHandler(
						serviceProxy, AopInvocationHandler.class);

				serviceWrapper.setWrappedService(
					(T)aopInvocationHandler.getTarget());

				return new ServiceBag<>(
					classLoader, aopInvocationHandler, serviceTypeClass,
					serviceWrapper);
			}
			finally {
				if (serviceReference != null) {
					registry.ungetService(serviceReference);
				}
			}
		}

	}

}