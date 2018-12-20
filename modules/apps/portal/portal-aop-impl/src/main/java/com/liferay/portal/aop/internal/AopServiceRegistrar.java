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

package com.liferay.portal.aop.internal;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopCacheManager;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.spring.transaction.TransactionExecutor;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
public class AopServiceRegistrar {

	public AopServiceRegistrar(
		ServiceReference<AopService> serviceReference, AopService aopService,
		Class<?>[] aopServiceInterfaces, String[] aopServiceNames,
		Dictionary<String, Object> properties) {

		_serviceReference = serviceReference;
		_aopService = aopService;
		_aopServiceInterfaces = aopServiceInterfaces;
		_aopServiceNames = aopServiceNames;
		_properties = properties;
	}

	public void register(
		TransactionExecutor transactionExecutor,
		ServiceMonitoringControl serviceMonitoringControl) {

		_aopInvocationHandler = AopCacheManager.create(
			_aopService,
			AopCacheManager.createChainableMethodAdvices(
				transactionExecutor, serviceMonitoringControl));

		Class<? extends AopService> aopServiceClass = _aopService.getClass();

		Object aopProxy = ProxyUtil.newProxyInstance(
			aopServiceClass.getClassLoader(), _aopServiceInterfaces,
			_aopInvocationHandler);

		_aopService.setAopProxy(aopProxy);

		Bundle bundle = _serviceReference.getBundle();

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			_aopServiceNames, aopProxy, _properties);
	}

	public void setProperties(Dictionary<String, Object> properties) {
		_properties = properties;

		if (_serviceRegistration != null) {
			_serviceRegistration.setProperties(properties);
		}
	}

	public void unregister() {
		if (_serviceRegistration != null) {
			AopCacheManager.destroy(_aopInvocationHandler);

			_aopInvocationHandler = null;

			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	private AopInvocationHandler _aopInvocationHandler;
	private final AopService _aopService;
	private final Class<?>[] _aopServiceInterfaces;
	private final String[] _aopServiceNames;
	private Dictionary<String, Object> _properties;
	private final ServiceReference<AopService> _serviceReference;
	private ServiceRegistration<?> _serviceRegistration;

}