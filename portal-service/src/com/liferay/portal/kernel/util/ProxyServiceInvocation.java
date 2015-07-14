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

package com.liferay.portal.kernel.util;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Marcellus Tavares
 */
public class ProxyServiceInvocation {

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> service) {
		return (T)ProxyUtil.newProxyInstance(
			service.getClassLoader(), new Class[] {service},
			new ManagerInvocationHandler<T>(service));
	}

	private static class ManagerInvocationHandler<T>
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			T service = _serviceTracker.getService();

			if (service != null) {
				return method.invoke(service, arguments);
			}

			Class<?> returnType = method.getReturnType();

			if (returnType.equals(boolean.class)) {
				return GetterUtil.DEFAULT_BOOLEAN;
			}
			else if (returnType.equals(byte.class)) {
				return GetterUtil.DEFAULT_BYTE;
			}
			else if (returnType.equals(double.class)) {
				return GetterUtil.DEFAULT_DOUBLE;
			}
			else if (returnType.equals(float.class)) {
				return GetterUtil.DEFAULT_FLOAT;
			}
			else if (returnType.equals(int.class)) {
				return GetterUtil.DEFAULT_INTEGER;
			}
			else if (returnType.equals(long.class)) {
				return GetterUtil.DEFAULT_LONG;
			}
			else if (returnType.equals(short.class)) {
				return GetterUtil.DEFAULT_SHORT;
			}

			return method.getDefaultValue();
		}

		private ManagerInvocationHandler(Class<T> managerService) {
			Registry registry = RegistryUtil.getRegistry();

			_serviceTracker = registry.trackServices(managerService);

			_serviceTracker.open();
		}

		private final ServiceTracker<T, T> _serviceTracker;

	}

}