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

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.service.ServiceWrapper;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * @author Raymond Augé
 */
public class ServiceBag {

	public ServiceBag(
		ClassLoader classLoader, AdvisedSupport advisedSupport,
		Class<?> serviceTypeClass, final ServiceWrapper<?> serviceWrapper) {

		_advisedSupport = advisedSupport;

		Object nextTarget = ProxyUtil.newProxyInstance(
			classLoader,
			new Class<?>[] {serviceTypeClass, ServiceWrapper.class},
			new ClassLoaderBeanHandler(serviceWrapper, classLoader));

		TargetSource nextTargetSource = new SingletonTargetSource(nextTarget) {

			@Override
			public Class<?> getTargetClass() {
				return serviceWrapper.getClass();
			}

		};

		_advisedSupport.setTargetSource(nextTargetSource);

		_serviceWrapper = (ServiceWrapper<?>)nextTarget;
	}

	@SuppressWarnings("unchecked")
	public <T> void replace() throws Exception {
		TargetSource targetSource = _advisedSupport.getTargetSource();

		Object currentService = targetSource.getTarget();
		ServiceWrapper<T> previousService = null;

		// Loop through services

		while (true) {

			// A matching service was found

			if (currentService == _serviceWrapper) {
				Object wrappedService = _serviceWrapper.getWrappedService();

				if (previousService == null) {

					// There is no previous service, so we need to change the
					// target source

					TargetSource previousTargetSource =
						new SingletonTargetSource(wrappedService);

					_advisedSupport.setTargetSource(previousTargetSource);
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
	}

	private AdvisedSupport _advisedSupport;
	private ServiceWrapper<?> _serviceWrapper;

}