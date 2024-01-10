/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopInvocationHandler;

import java.lang.reflect.InvocationHandler;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Raymond Augé
 */
public class ServiceBag<V> {

	public ServiceBag(
		AopInvocationHandler aopInvocationHandler, Class<?> serviceTypeClass,
		ServiceWrapper<V> serviceWrapper, BundleContext bundleContext,
		ServiceReference<?> serviceReference) {

		_aopInvocationHandler = aopInvocationHandler;
		_bundleContext = bundleContext;
		_serviceReference = serviceReference;

		Object previousService = serviceWrapper.getWrappedService();

		if (!(previousService instanceof ServiceWrapper)) {
			Class<?> previousServiceClass = previousService.getClass();

			ClassLoader previousServiceAggregateClassLoader =
				AggregateClassLoader.getAggregateClassLoader(
					previousServiceClass.getClassLoader(),
					IdentifiableOSGiService.class.getClassLoader());

			previousService = ProxyUtil.newProxyInstance(
				previousServiceAggregateClassLoader,
				new Class<?>[] {
					serviceTypeClass, IdentifiableOSGiService.class
				},
				new ClassLoaderBeanHandler(
					previousService, previousServiceAggregateClassLoader));

			serviceWrapper.setWrappedService((V)previousService);
		}

		Class<?> clazz = serviceWrapper.getClass();

		Object nextTarget = ProxyUtil.newProxyInstance(
			AggregateClassLoader.getAggregateClassLoader(
				serviceTypeClass.getClassLoader(),
				IdentifiableOSGiService.class.getClassLoader()),
			new Class<?>[] {
				serviceTypeClass, ServiceWrapper.class,
				IdentifiableOSGiService.class
			},
			new ClassLoaderBeanHandler(serviceWrapper, clazz.getClassLoader()));

		_aopInvocationHandler.setTarget(nextTarget);

		_serviceWrapper = (ServiceWrapper<?>)nextTarget;
	}

	@SuppressWarnings("unchecked")
	public <T> void replace() {
		Object currentService = _aopInvocationHandler.getTarget();

		ServiceWrapper<T> previousService = null;

		// Loop through services

		while (true) {

			// A matching service was found

			if (currentService == _serviceWrapper) {
				Object wrappedService = _serviceWrapper.getWrappedService();

				if (previousService == null) {

					// There is no previous service, so we need to unwrap the
					// portal class loader bean handler and change the target
					// source

					if (!(wrappedService instanceof ServiceWrapper) &&
						ProxyUtil.isProxyClass(wrappedService.getClass())) {

						InvocationHandler invocationHandler =
							ProxyUtil.getInvocationHandler(wrappedService);

						if (invocationHandler instanceof
								ClassLoaderBeanHandler) {

							ClassLoaderBeanHandler classLoaderBeanHandler =
								(ClassLoaderBeanHandler)invocationHandler;

							wrappedService = classLoaderBeanHandler.getBean();
						}
					}

					_aopInvocationHandler.setTarget(wrappedService);
				}
				else {

					// Take ourselves out of the chain by setting our
					// wrapped service as the previous without changing the
					// target source

					previousService.setWrappedService((T)wrappedService);
				}

				break;
			}

			// Every item in the chain is a ServiceWrapper except the original
			// service

			if (!(currentService instanceof ServiceWrapper)) {
				break;
			}

			// Check the next service because no matching service was found

			previousService = (ServiceWrapper<T>)currentService;

			currentService = previousService.getWrappedService();
		}

		if (_serviceReference != null) {
			_bundleContext.ungetService(_serviceReference);
		}
	}

	private final AopInvocationHandler _aopInvocationHandler;
	private final BundleContext _bundleContext;
	private final ServiceReference<?> _serviceReference;
	private final ServiceWrapper<?> _serviceWrapper;

}