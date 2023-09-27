/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

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