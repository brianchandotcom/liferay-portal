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
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author Shuyang Zhou
 */
public class ProxyUtil {

	public static <T extends InvocationHandler> T fetchInvocationHandler(
		Object proxy, Class<T> clazz) {

		if (!isProxyClass(proxy.getClass())) {
			return null;
		}

		try {
			InvocationHandler invocationHandler =
				(InvocationHandler)_invocationHandlerField.get(proxy);

			if (clazz.isInstance(invocationHandler)) {
				return clazz.cast(invocationHandler);
			}

			return null;
		}
		catch (IllegalAccessException iae) {
			throw new IllegalArgumentException(iae);
		}
	}

	public static InvocationHandler getInvocationHandler(Object proxy) {
		if (!isProxyClass(proxy.getClass())) {
			throw new IllegalArgumentException("Not a proxy instance");
		}

		try {
			return (InvocationHandler)_invocationHandlerField.get(proxy);
		}
		catch (IllegalAccessException iae) {
			throw new IllegalArgumentException(iae);
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

		_constructorReferences.putIfAbsent(clazz, new ConstructorReference());

		return clazz;
	}

	public static <T> Function<InvocationHandler, T> getProxyProviderFunction(
		Class<?>... interfaceClasses) {

		ClassLoader classLoader = interfaceClasses[0].getClassLoader();

		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}

		Class<?> proxyClass = getProxyClass(classLoader, interfaceClasses);

		try {
			Constructor<T> constructor =
				(Constructor<T>)proxyClass.getConstructor(_ARGUMENTS_CLASS);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	public static boolean isProxyClass(Class<?> clazz) {
		if (clazz == null) {
			throw new NullPointerException();
		}

		return _constructorReferences.containsKey(clazz);
	}

	public static Object newProxyInstance(
		ClassLoader classLoader, Class<?>[] interfaces,
		InvocationHandler invocationHandler) {

		Class<?> proxyClass = getProxyClass(classLoader, interfaces);

		ConstructorReference constructorHolder = _constructorReferences.get(
			proxyClass);

		return constructorHolder.newInstance(proxyClass, invocationHandler);
	}

	private static final Class<?>[] _ARGUMENTS_CLASS = {
		InvocationHandler.class
	};

	private static final ConcurrentMap
		<ClassLoader, ConcurrentMap<LookupKey, Class<?>>> _classReferences =
			new ConcurrentReferenceKeyHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);
	private static final ConcurrentMap<Class<?>, ConstructorReference>
		_constructorReferences = new ConcurrentReferenceKeyHashMap<>(
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

	private static class ConstructorReference {

		public Object newInstance(
			Class<?> proxyClass, InvocationHandler invocationHandler) {

			Constructor<?> constructor = null;

			Reference<Constructor<?>> reference = _reference;

			try {
				if ((reference == null) ||
					((constructor = reference.get()) == null)) {

					constructor = proxyClass.getConstructor(_ARGUMENTS_CLASS);

					constructor.setAccessible(true);

					_reference = new WeakReference<>(constructor);
				}

				return constructor.newInstance(
					new Object[] {invocationHandler});
			}
			catch (ReflectiveOperationException roe) {
				throw new InternalError(roe);
			}
		}

		private volatile Reference<Constructor<?>> _reference;

	}

	private static class LookupKey {

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}

			LookupKey lookupKey = (LookupKey)obj;

			Reference<?>[] references = lookupKey._references;

			if (_references.length != references.length) {
				return false;
			}

			for (int i = 0; i < _references.length; i++) {
				Reference<?> reference = _references[i];
				Reference<?> otherReference = references[i];

				if (reference.get() != otherReference.get()) {
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
			int hashCode = 0;

			_references = new Reference<?>[interfaces.length];

			for (int i = 0; i < interfaces.length; i++) {
				Class<?> clazz = interfaces[i];

				hashCode = HashUtil.hash(hashCode, clazz.getName());

				_references[i] = new WeakReference<>(clazz);
			}

			_hashCode = hashCode;
		}

		private final int _hashCode;
		private final Reference<?>[] _references;

	}

}