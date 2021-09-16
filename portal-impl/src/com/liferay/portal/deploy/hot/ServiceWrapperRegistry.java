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
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopInvocationHandler;

import java.lang.reflect.Method;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
public class ServiceWrapperRegistry {

	public ServiceWrapperRegistry() {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, ServiceWrapper.class.getName(),
			new ServiceWrapperServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceWrapperRegistry.class);

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private final ServiceTracker<ServiceWrapper<?>, ServiceBag<?>>
		_serviceTracker;

	private class ServiceWrapperServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ServiceWrapper<?>, ServiceBag<?>> {

		@Override
		public ServiceBag<?> addingService(
			ServiceReference<ServiceWrapper<?>> serviceReference) {

			ServiceWrapper<?> serviceWrapper = _bundleContext.getService(
				serviceReference);

			try {
				return _getServiceBag(serviceWrapper);
			}
			catch (Throwable throwable) {
				_log.error(
					"Unable to get service bag for " +
						serviceWrapper.getClass(),
					throwable);
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

			_bundleContext.ungetService(serviceReference);

			try {
				serviceBag.replace();
			}
			catch (Exception exception) {
				_log.error(exception, exception);
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

			try {
				service = PortalBeanLocatorUtil.locate(
					serviceTypeClass.getName());
			}
			catch (BeanLocatorException beanLocatorException) {
				if (_log.isDebugEnabled()) {
					_log.debug(beanLocatorException, beanLocatorException);
				}

				serviceReference = _bundleContext.getServiceReference(
					serviceTypeClass);

				service = _bundleContext.getService(serviceReference);
			}

			Object serviceProxy = service;

			if (!ProxyUtil.isProxyClass(serviceProxy.getClass())) {
				_log.error(
					"Service hooks require Spring to be configured to use " +
						"JdkDynamicProxy and will not work with CGLIB");

				if (serviceReference != null) {
					_bundleContext.ungetService(serviceReference);
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
					_bundleContext.ungetService(serviceReference);
				}
			}
		}

	}

}