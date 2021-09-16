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

package com.liferay.portal.xmlrpc;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.xmlrpc.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
public class XmlRpcMethodUtil {

	public static Method getMethod(String token, String methodName) {
		Method method = null;

		Map<String, Method> methods = _methodRegistry.get(token);

		if (methods != null) {
			method = methods.get(methodName);
		}

		return method;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		XmlRpcMethodUtil.class);

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final Map<String, Map<String, Method>> _methodRegistry =
		new ConcurrentHashMap<>();
	private static final ServiceTracker<Method, Method> _serviceTracker;

	private static class MethodServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Method, Method> {

		@Override
		public Method addingService(ServiceReference<Method> serviceReference) {
			Method method = _bundleContext.getService(serviceReference);

			String token = method.getToken();

			Map<String, Method> methods = _methodRegistry.get(token);

			if (methods == null) {
				methods = new HashMap<>();

				_methodRegistry.put(token, methods);
			}

			String methodName = method.getMethodName();

			Method registeredMethod = methods.get(methodName);

			if (registeredMethod != null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"There is already an XML-RPC method registered ",
							"with name ", methodName, " at ", token));
				}
			}
			else {
				methods.put(methodName, method);
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

			_bundleContext.ungetService(serviceReference);

			String token = method.getToken();

			Map<String, Method> methods = _methodRegistry.get(token);

			if (methods == null) {
				return;
			}

			methods.remove(method.getMethodName());

			if (methods.isEmpty()) {
				_methodRegistry.remove(token);
			}
		}

	}

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, Method.class, new MethodServiceTrackerCustomizer());

		_serviceTracker.open();
	}

}