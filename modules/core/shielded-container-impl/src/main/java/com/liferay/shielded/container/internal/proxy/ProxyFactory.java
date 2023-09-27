/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.shielded.container.internal.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class ProxyFactory {

	public ProxyFactory(ClassLoader classLoader) {
		try {
			Class<?> proxyUtilClass = classLoader.loadClass(
				"com.liferay.portal.kernel.util.ProxyUtil");

			_newProxyInstanceMethod = proxyUtilClass.getMethod(
				"newProxyInstance", ClassLoader.class, Class[].class,
				InvocationHandler.class);

			_newDelegateProxyInstanceMethod = proxyUtilClass.getMethod(
				"newDelegateProxyInstance", ClassLoader.class, Class.class,
				Object.class, Object.class);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	public <T> T createASMWrapper(
		ClassLoader classLoader, Class<T> interfaceClass, Object delegateObject,
		T defaultObject) {

		try {
			return (T)_newDelegateProxyInstanceMethod.invoke(
				null, classLoader, interfaceClass, delegateObject,
				defaultObject);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	public <T> T newProxyInstance(
		ClassLoader classLoader, Class<?>[] interfaces,
		InvocationHandler invocationHandler) {

		try {
			return (T)_newProxyInstanceMethod.invoke(
				null, classLoader, interfaces, invocationHandler);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	private final Method _newDelegateProxyInstanceMethod;
	private final Method _newProxyInstanceMethod;

}