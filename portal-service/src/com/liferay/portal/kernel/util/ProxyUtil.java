/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.memory.EqualityWeakReference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import java.util.concurrent.ConcurrentHashMap;
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

		EqualityWeakReference<ClassLoader> classLoaderReference =
			new EqualityWeakReference<ClassLoader>(classLoader);

		ConcurrentMap<LookupKey, Reference<Class<?>>> classReferences =
			_classReferences.get(classLoaderReference);

		if (classReferences == null) {
			classReferences =
				new ConcurrentHashMap<LookupKey, Reference<Class<?>>>();

			classLoaderReference = new EqualityWeakReference<ClassLoader>(
				classLoader, _classLoaderReferenceQueue);

			ConcurrentMap<LookupKey, Reference<Class<?>>> oldClassReferences =
				_classReferences.putIfAbsent(
					classLoaderReference, classReferences);

			if (oldClassReferences != null) {
				classReferences = oldClassReferences;

				classLoaderReference.enqueue();
			}
		}

		LookupKey lookupKey = new LookupKey(interfaceClasses);

		Reference<Class<?>> classReference = classReferences.get(lookupKey);

		Class<?> clazz = null;

		if ((classReference == null) ||
			((clazz = classReference.get()) == null)) {

			synchronized(classReferences) {
				classReference = classReferences.get(lookupKey);

				if ((classReference == null) ||
					((clazz = classReference.get()) == null)) {

					clazz = Proxy.getProxyClass(classLoader, interfaceClasses);

					classReferences.put(
						lookupKey, new WeakReference<Class<?>>(clazz));
				}
			}
		}

		Constructor<?> constructor = null;

		try {
			constructor = clazz.getConstructor(_argumentsClazz);
		}
		catch (Exception e) {
			throw new InternalError(e.toString());
		}

		EqualityWeakReference<Class<?>> proxyClassReference =
			new EqualityWeakReference<Class<?>>(
				clazz, _proxyClassReferenceQueue);

		_constructors.putIfAbsent(proxyClassReference, constructor);

		while (true) {
			EqualityWeakReference<ClassLoader> staleClassLoaderReference =
				(EqualityWeakReference<ClassLoader>)
					_classLoaderReferenceQueue.poll();

			if (staleClassLoaderReference == null) {
				break;
			}

			_classReferences.remove(staleClassLoaderReference);
		}

		while (true) {
			EqualityWeakReference<Class<?>> staleProxyClassReference =
				(EqualityWeakReference<Class<?>>)
					_proxyClassReferenceQueue.poll();

			if (staleProxyClassReference == null) {
				break;
			}

			_constructors.remove(staleProxyClassReference);
		}

		return clazz;
	}

	public static boolean isProxyClass(Class<?> clazz) {
		if (clazz == null) {
			throw new NullPointerException();
		}

		EqualityWeakReference<Class<?>> equalityWeakReference =
			new EqualityWeakReference<Class<?>>(clazz);

		return _constructors.containsKey(equalityWeakReference);
	}

	public static Object newProxyInstance(
		ClassLoader classLoader, Class<?>[] interfaces,
		InvocationHandler invocationHandler) {

		Class<?> clazz = getProxyClass(classLoader, interfaces);

		EqualityWeakReference<Class<?>> proxyClassReference =
			new EqualityWeakReference<Class<?>>(clazz);

		Constructor<?> constructor = _constructors.get(proxyClassReference);

		try {
			return constructor.newInstance(new Object[] {invocationHandler});
		}
		catch (Exception e) {
			throw new InternalError(e.toString());
		}
	}

	private static Class<?>[] _argumentsClazz = {InvocationHandler.class};
	private static ReferenceQueue<ClassLoader> _classLoaderReferenceQueue =
		new ReferenceQueue<ClassLoader>();
	private static ConcurrentMap
		<EqualityWeakReference<ClassLoader>,
			ConcurrentMap<LookupKey, Reference<Class<?>>>> _classReferences =
				new ConcurrentHashMap<EqualityWeakReference<ClassLoader>,
					ConcurrentMap<LookupKey, Reference<Class<?>>>>();
	private static ConcurrentMap
		<EqualityWeakReference<Class<?>>, Constructor<?>> _constructors =
			new ConcurrentHashMap
				<EqualityWeakReference<Class<?>>, Constructor<?>>();
	private static Field _invocationHandlerField;
	private static ReferenceQueue<Class<?>> _proxyClassReferenceQueue =
		new ReferenceQueue<Class<?>>();

	private static class LookupKey {

		public LookupKey(Class<?>[] interfaces) {
			_interfaces = interfaces;

			_hashCode = 1;

			for (Class<?> clazz : interfaces) {
				String name = clazz.getName();

				_hashCode = HashUtil.hash(_hashCode, name.hashCode());
			}
		}

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

		private int _hashCode;
		private final Class<?>[] _interfaces;

	}

	static {
		try {
			_invocationHandlerField = ReflectionUtil.getDeclaredField(
				Proxy.class, "h");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}