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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class JSONWebServiceScannerUtil {

	public static Method[] scan(Object service) {
		Class<?> clazz = _getTargetClass(service);

		Method[] methods = clazz.getMethods();

		List<Method> serviceMethods = new ArrayList<>(methods.length);

		for (Method method : methods) {
			Class<?> declaringClass = method.getDeclaringClass();

			if (declaringClass != clazz) {
				continue;
			}

			serviceMethods.add(method);
		}

		return serviceMethods.toArray(new Method[0]);
	}

	private static Class<?> _getTargetClass(Object service) {
		while (ProxyUtil.isProxyClass(service.getClass())) {
			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(service);

			if (invocationHandler instanceof ClassLoaderBeanHandler) {
				ClassLoaderBeanHandler classLoaderBeanHandler =
					(ClassLoaderBeanHandler)invocationHandler;

				Object bean = classLoaderBeanHandler.getBean();

				if (bean instanceof ServiceWrapper) {
					ServiceWrapper<?> serviceWrapper = (ServiceWrapper<?>)bean;

					service = serviceWrapper.getWrappedService();
				}
				else {
					service = bean;
				}
			}
			else {
				Class<?> invocationHandlerClass = invocationHandler.getClass();

				try {
					Method method = invocationHandlerClass.getMethod(
						"getTarget");

					service = method.invoke(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					if (_log.isDebugEnabled()) {
						_log.debug(reflectiveOperationException);
					}
				}
			}
		}

		return service.getClass();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONWebServiceScannerUtil.class);

}