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

class ServiceBag {

	public ServiceBag(
		AdvisedSupport advisedSupport, ClassLoader classLoader,
		Class<?> serviceTypeClass, ServiceWrapper<?> serviceWrapper) {

		_advisedSupport = advisedSupport;

		Object nextTarget = ProxyUtil.newProxyInstance(
			classLoader,
			new Class<?>[] {serviceTypeClass, ServiceWrapper.class},
			new ClassLoaderBeanHandler(serviceWrapper, classLoader));

		TargetSource nextTargetSource = new SingletonTargetSource(nextTarget);

		_advisedSupport.setTargetSource(nextTargetSource);

		_serviceWrapper = (ServiceWrapper<?>)nextTarget;
	}

	@SuppressWarnings("unchecked")
	public <T> void replace() throws Exception {
		TargetSource targetSource = _advisedSupport.getTargetSource();

		Object currentService = targetSource.getTarget();
		ServiceWrapper<T> previousService = null;

		// loop through the chain

		while (true) {

			// stop if we match

			if (currentService == _serviceWrapper) {
				Object wrappedService = _serviceWrapper.getWrappedService();

				if (previousService == null) {

					// we're at the root, we need to change the target source

					TargetSource previousTargetSource =
						new SingletonTargetSource(wrappedService);

					_advisedSupport.setTargetSource(previousTargetSource);
				}
				else {

					// take ourselves out of the chain, by setting our
					// wrapped service into the previous, no need to change the
					// target source

					previousService.setWrappedService((T)wrappedService);
				}

				break;
			}

			// every item in the chain is a ServiceWrapper except the original
			// service

			if (!(currentService instanceof ServiceWrapper)) {
				break;
			}

			// we didn't match, check the next

			previousService = (ServiceWrapper<T>)currentService;

			currentService = previousService.getWrappedService();
		}
	}

	private AdvisedSupport _advisedSupport;
	private ServiceWrapper<?> _serviceWrapper;

}