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

			Class<?> asmWrapperUtilClass = classLoader.loadClass(
				"com.liferay.portal.asm.ASMWrapperUtil");

			_createASMWrapperMethod = asmWrapperUtilClass.getMethod(
				"createASMWrapper", ClassLoader.class, Class.class,
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
			return (T)_createASMWrapperMethod.invoke(
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

	private final Method _createASMWrapperMethod;
	private final Method _newProxyInstanceMethod;

}