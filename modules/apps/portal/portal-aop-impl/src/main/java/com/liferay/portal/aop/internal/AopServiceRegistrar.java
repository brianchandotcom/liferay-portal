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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopCacheManager;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.spring.transaction.TransactionHandler;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentConstants;

/**
 * @author Preston Crary
 */
public class AopServiceRegistrar {

	public AopServiceRegistrar(
		ServiceReference<AopService> serviceReference, AopService aopService,
		Class<?>[] aopServiceInterfaces) {

		_serviceReference = serviceReference;
		_aopService = aopService;
		_aopServiceInterfaces = aopServiceInterfaces;

		Bundle bundle = serviceReference.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Liferay-Service") == null) {
			_liferayService = false;
		}
		else {
			_liferayService = true;
		}
	}

	public boolean isLiferayService() {
		return _liferayService;
	}

	public void register(TransactionHandler transactionHandler) {
		Bundle bundle = _serviceReference.getBundle();

		BundleContext bundleContext = bundle.getBundleContext();

		String[] aopServiceNames = new String[_aopServiceInterfaces.length];

		for (int i = 0; i < _aopServiceInterfaces.length; i++) {
			aopServiceNames[i] = _aopServiceInterfaces[i].getName();
		}

		_serviceRegistration = bundleContext.registerService(
			aopServiceNames, _getService(bundleContext, transactionHandler),
			_getProperties(_serviceReference));
	}

	public void unregister() {
		if (_serviceRegistration != null) {
			if (_aopInvocationHandler != null) {
				AopCacheManager.destroy(_aopInvocationHandler);

				_aopInvocationHandler = null;
			}

			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	public void updateProperties() {
		if (_serviceRegistration != null) {
			_serviceRegistration.setProperties(
				_getProperties(_serviceReference));
		}
	}

	private Dictionary<String, Object> _getProperties(
		ServiceReference<AopService> serviceReference) {

		Dictionary<String, Object> properties = null;

		for (String key : serviceReference.getPropertyKeys()) {
			if (_frameworkKeys.contains(key)) {
				continue;
			}

			if (properties == null) {
				properties = new HashMapDictionary<>();
			}

			properties.put(key, serviceReference.getProperty(key));
		}

		return properties;
	}

	private Object _getService(
		BundleContext bundleContext, TransactionHandler transactionHandler) {

		Object serviceScope = _serviceReference.getProperty(
			Constants.SERVICE_SCOPE);

		if (Constants.SCOPE_PROTOTYPE.equals(serviceScope)) {
			return new AopServicePrototypeServiceFactory(
				bundleContext.getServiceObjects(_serviceReference),
				transactionHandler);
		}

		_aopInvocationHandler = AopCacheManager.create(
			_aopService, transactionHandler);

		Class<? extends AopService> aopServiceClass = _aopService.getClass();

		Object aopProxy = ProxyUtil.newProxyInstance(
			aopServiceClass.getClassLoader(), _aopServiceInterfaces,
			_aopInvocationHandler);

		_aopService.setAopProxy(aopProxy);

		return aopProxy;
	}

	private static final Set<String> _frameworkKeys = new HashSet<>(
		Arrays.asList(
			ComponentConstants.COMPONENT_ID, ComponentConstants.COMPONENT_NAME,
			Constants.OBJECTCLASS, Constants.SERVICE_BUNDLEID,
			Constants.SERVICE_ID, Constants.SERVICE_SCOPE));

	private AopInvocationHandler _aopInvocationHandler;
	private final AopService _aopService;
	private final Class<?>[] _aopServiceInterfaces;
	private final boolean _liferayService;
	private final ServiceReference<AopService> _serviceReference;
	private ServiceRegistration<?> _serviceRegistration;

	private class AopServicePrototypeServiceFactory
		implements PrototypeServiceFactory<Object> {

		@Override
		public Object getService(
			Bundle bundle, ServiceRegistration<Object> serviceRegistration) {

			AopService aopService = _serviceObjects.getService();

			Class<?>[] aopInterfaces = aopService.getAopInterfaces();

			Class<? extends AopService> aopServiceClass = aopService.getClass();

			if (ArrayUtil.isEmpty(aopInterfaces)) {
				aopInterfaces = ArrayUtil.remove(
					aopServiceClass.getInterfaces(), AopService.class);
			}

			if (!Arrays.equals(_aopServiceInterfaces, aopInterfaces)) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"Prototype AopService ", aopService,
						" must have immutable AOP interfaces, expected ",
						Arrays.toString(_aopServiceInterfaces), " but was ",
						Arrays.toString(aopInterfaces)));
			}

			AopInvocationHandler aopInvocationHandler = AopCacheManager.create(
				aopService, _transactionHandler);

			_aopServices.put(aopInvocationHandler, aopService);

			Object aopProxy = ProxyUtil.newProxyInstance(
				aopServiceClass.getClassLoader(), _aopServiceInterfaces,
				aopInvocationHandler);

			aopService.setAopProxy(aopProxy);

			return aopProxy;
		}

		@Override
		public void ungetService(
			Bundle bundle, ServiceRegistration<Object> serviceRegistration,
			Object aopProxy) {

			AopInvocationHandler aopInvocationHandler =
				ProxyUtil.fetchInvocationHandler(
					aopProxy, AopInvocationHandler.class);

			AopCacheManager.destroy(aopInvocationHandler);

			_serviceObjects.ungetService(
				_aopServices.remove(aopInvocationHandler));
		}

		private AopServicePrototypeServiceFactory(
			ServiceObjects<AopService> serviceObjects,
			TransactionHandler transactionHandler) {

			_serviceObjects = serviceObjects;
			_transactionHandler = transactionHandler;
		}

		private final Map<AopInvocationHandler, AopService> _aopServices =
			new ConcurrentHashMap<>();
		private final ServiceObjects<AopService> _serviceObjects;
		private final TransactionHandler _transactionHandler;

	}

}