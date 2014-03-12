/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.xmlrpc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class XmlRpcMethodUtil {

	public static Method getMethod(String token, String methodName) {
		return _instance._getMethod(token, methodName);
	}

	public static void registerMethod(Method method) {
		if (method == null) {
			return;
		}

		_instance._registerMethod(method);
	}

	public static void unregisterMethod(Method method) {
		if (method == null) {
			return;
		}

		_instance._unregisterMethod(method);
	}

	private XmlRpcMethodUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			Method.class, new MethodServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	protected Method _getMethod(String token, String methodName) {
		Method method = null;

		Map<String, Method> tokenMethods = _methodRegistry.get(token);

		if (tokenMethods != null) {
			method = tokenMethods.get(methodName);
		}

		return method;
	}

	private void _registerMethod(Method method) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Method> serviceRegistration =
			registry.registerService(Method.class, method);

		_serviceRegistrations.put(method, serviceRegistration);
	}

	private void _unregisterMethod(Method method) {
		ServiceRegistration<Method> serviceRegistration =
			_serviceRegistrations.remove(method);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(XmlRpcMethodUtil.class);

	private static XmlRpcMethodUtil _instance = new XmlRpcMethodUtil();

	private Map<String, Map<String, Method>> _methodRegistry =
		new ConcurrentHashMap<String, Map<String, Method>>();
	private ServiceRegistrationMap<Method> _serviceRegistrations =
		new ServiceRegistrationMap<Method>();
	private ServiceTracker<Method, Method> _serviceTracker;

	private class MethodServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Method, Method> {

		@Override
		public Method addingService(
			ServiceReference<Method> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			Method method = registry.getService(serviceReference);

			String token = method.getToken();
			String methodName = method.getMethodName();

			Map<String, Method> tokenMethods = _methodRegistry.get(token);

			if (tokenMethods == null) {
				tokenMethods = new HashMap<String, Method>();

				_methodRegistry.put(token, tokenMethods);
			}

			Method registeredMethod = tokenMethods.get(methodName);

			if (registeredMethod != null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"There is already an XML-RPC method registered with " +
							"name " + methodName + " at " + token);
				}
			}
			else {
				tokenMethods.put(methodName, method);
			}

			return method;
		}

		@Override
		public void modifiedService(
			ServiceReference<Method> serviceReference, Method method) {
		}

		@Override
		public void removedService(
			ServiceReference<Method> serviceReference, Method method) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String token = method.getToken();
			String methodName = method.getMethodName();

			Map<String, Method> tokenMethods = _methodRegistry.get(token);

			if (tokenMethods == null) {
				return;
			}

			tokenMethods.remove(methodName);

			if (tokenMethods.isEmpty()) {
				_methodRegistry.remove(token);
			}
		}

	}

}