/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassLoaderProxy {

	public ClassLoaderProxy(Object object, ClassLoader classLoader) {
		this(object, object.getClass().getName(), classLoader);
	}

	public ClassLoaderProxy(
		Object object, String className, ClassLoader classLoader) {

		_object = object;
		_className = className;
		_classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public String getClassName() {
		return _className;
	}

	public Object invoke(MethodHandler methodHandler) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			return _invoke(methodHandler);
		}
		catch (InvocationTargetException invocationTargetException) {
			throw translateThrowable(
				invocationTargetException.getCause(), contextClassLoader);
		}
		catch (Throwable throwable) {
			_log.error(throwable, throwable);

			throw throwable;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected Throwable translateThrowable(
		Throwable throwable, ClassLoader contextClassLoader) {

		try {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					unsyncByteArrayOutputStream)) {

				objectOutputStream.writeObject(throwable);

				objectOutputStream.flush();
			}

			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				new UnsyncByteArrayInputStream(
					unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
					unsyncByteArrayOutputStream.size());

			try (ObjectInputStream objectInputStream =
					new ClassLoaderObjectInputStream(
						unsyncByteArrayInputStream, contextClassLoader)) {

				return (Throwable)objectInputStream.readObject();
			}
		}
		catch (Throwable throwable2) {
			_log.error(throwable2, throwable2);

			return throwable2;
		}
	}

	private Object _invoke(MethodHandler methodHandler) throws Exception {
		try {
			return methodHandler.invoke(_object);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			MethodKey methodKey = methodHandler.getMethodKey();

			String name = methodKey.getMethodName();

			Class<?>[] parameterTypes = methodKey.getParameterTypes();

			Class<?> clazz = Class.forName(_className, true, _classLoader);

			for (Method method : clazz.getMethods()) {
				String curName = method.getName();
				Class<?>[] curParameterTypes = method.getParameterTypes();

				if (!curName.equals(name) ||
					(curParameterTypes.length != parameterTypes.length)) {

					continue;
				}

				boolean correctParams = true;

				for (int j = 0; j < parameterTypes.length; j++) {
					Class<?> a = parameterTypes[j];
					Class<?> b = curParameterTypes[j];

					if (!ClassUtil.isSubclass(a, b.getName())) {
						correctParams = false;

						break;
					}
				}

				if (correctParams) {
					return method.invoke(_object, methodHandler.getArguments());
				}
			}

			throw noSuchMethodException;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassLoaderProxy.class);

	private final ClassLoader _classLoader;
	private final String _className;
	private final Object _object;

}