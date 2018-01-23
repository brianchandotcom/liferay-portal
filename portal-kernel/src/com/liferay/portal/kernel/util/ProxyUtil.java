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

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class ProxyUtil {

	public static InvocationHandler getInvocationHandler(Object proxy) {
		if (!isProxyClass(proxy.getClass())) {
			throw new IllegalArgumentException("Not a proxy instance");
		}

		try {
			return (InvocationHandler)_invocationHandlerField.get(proxy);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Class<?> getProxyClass(
		ClassLoader classLoader, Class<?>... interfaceClasses) {

		ConcurrentMap<LookupKey, Class<?>> classReferences =
			_classReferences.get(classLoader);

		if (classReferences == null) {
			classReferences = new ConcurrentReferenceValueHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);

			ConcurrentMap<LookupKey, Class<?>> oldClassReferences =
				_classReferences.putIfAbsent(classLoader, classReferences);

			if (oldClassReferences != null) {
				classReferences = oldClassReferences;
			}
		}

		LookupKey lookupKey = new LookupKey(interfaceClasses);

		Class<?> clazz = classReferences.get(lookupKey);

		if (clazz == null) {
			synchronized (classReferences) {
				clazz = classReferences.get(lookupKey);

				if (clazz == null) {
					clazz = Proxy.getProxyClass(classLoader, interfaceClasses);

					classReferences.put(lookupKey, clazz);
				}
			}
		}

		if (!_constructors.containsKey(clazz)) {
			Constructor<?> constructor = null;

			try {
				constructor = clazz.getConstructor(_ARGUMENTS_CLASS);

				constructor.setAccessible(true);
			}
			catch (Exception e) {
				throw new InternalError(e.toString());
			}

			_constructors.putIfAbsent(clazz, constructor);
		}

		return clazz;
	}

	public static boolean isProxyClass(Class<?> clazz) {
		if (clazz == null) {
			throw new NullPointerException();
		}

		return _constructors.containsKey(clazz);
	}

	public static Object newProxyInstance(
		ClassLoader classLoader, Class<?>[] interfaces,
		InvocationHandler invocationHandler) {

		Constructor<?> constructor = _constructors.get(
			getProxyClass(classLoader, interfaces));

		try {
			return constructor.newInstance(new Object[] {invocationHandler});
		}
		catch (Exception e) {
			throw new InternalError(e.toString());
		}
	}

	private static final Class<?>[] _ARGUMENTS_CLASS =
		{InvocationHandler.class};

	private static final ConcurrentMap
		<ClassLoader, ConcurrentMap<LookupKey, Class<?>>> _classReferences =
			new ConcurrentReferenceKeyHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);
	private static final ConcurrentMap<Class<?>, Constructor<?>> _constructors =
		new ConcurrentReferenceKeyHashMap<>(
			new ConcurrentReferenceValueHashMap<Class<?>, Constructor<?>>(
				FinalizeManager.WEAK_REFERENCE_FACTORY),
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private static final Field _invocationHandlerField;

	static {
		try {
			_invocationHandlerField = ReflectionUtil.getDeclaredField(
				Proxy.class, "h");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private static class LookupKey {

		@Override
		public boolean equals(Object obj) {
			LookupKey lookupKey = (LookupKey)obj;

			if (_interfaces.length != lookupKey._interfaces.length) {
				return false;
			}

			for (int i = 0; i < _interfaces.length; i++) {
				if (_interfaces[i] != lookupKey._interfaces[i]) {
					return false;
				}
			}

			return true;
		}

		@Override
		public int hashCode() {
			return _hashCode;
		}

		private LookupKey(Class<?>[] interfaces) {
			_interfaces = interfaces;

			int hashCode = 0;

			for (Class<?> clazz : interfaces) {
				hashCode = HashUtil.hash(hashCode, clazz.getName());
			}

			_hashCode = hashCode;
		}

		private final int _hashCode;
		private final Class<?>[] _interfaces;

	}

}