/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassLoaderProxy {

	public ClassLoaderProxy(Object obj, ClassLoader classLoader) {
		this(obj, obj.getClass().getName(), classLoader);
	}

	public ClassLoaderProxy(
		Object obj, String className, ClassLoader classLoader) {

		_obj = obj;
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
		catch (InvocationTargetException ite) {
			throw translateThrowable(ite.getCause(), contextClassLoader);
		}
		catch (Throwable t) {
			_log.error(t, t);

			throw t;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	/**
	 * @deprecated
	 */
	public Object invoke(String methodName, Object[] args) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			Class<?> clazz = Class.forName(_className, true, _classLoader);

			List<Class<?>> parameterTypes = new ArrayList<Class<?>>();

			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];

				Class<?> argClass = Class.forName(
					arg.getClass().getName(), true, _classLoader);

				if (ClassUtil.isSubclass(argClass, PrimitiveWrapper.class)) {
					MethodKey methodKey = new MethodKey(argClass, "getValue");

					Method method = methodKey.getMethod();

					args[i] = method.invoke(arg, (Object[])null);

					argClass = (Class<?>)argClass.getField("TYPE").get(arg);
				}

				if (ClassUtil.isSubclass(argClass, NullWrapper.class)) {
					NullWrapper nullWrapper = (NullWrapper)arg;

					argClass = Class.forName(
						nullWrapper.getClassName(), true, _classLoader);

					args[i] = null;
				}

				parameterTypes.add(argClass);
			}

			Method method = null;

			try {
				method = clazz.getMethod(
					methodName,
					parameterTypes.toArray(new Class[parameterTypes.size()]));
			}
			catch (NoSuchMethodException nsme) {
				Method[] methods = ((Class<?>)clazz).getMethods();

				for (int i = 0; i < methods.length; i++) {
					Class<?>[] methodParameterTypes =
						methods[i].getParameterTypes();

					if (methods[i].getName().equals(methodName) &&
						(methodParameterTypes.length ==
							parameterTypes.size())) {

						boolean correctParams = true;

						for (int j = 0; j < parameterTypes.size(); j++) {
							Class<?> a = parameterTypes.get(j);
							Class<?> b = methodParameterTypes[j];

							if (!ClassUtil.isSubclass(a, b)) {
								correctParams = false;

								break;
							}
						}

						if (correctParams) {
							method = methods[i];

							break;
						}
					}
				}

				if (method == null) {
					throw nsme;
				}
			}

			return method.invoke(_obj, args);
		}
		catch (InvocationTargetException ite) {
			throw translateThrowable(ite.getCause(), contextClassLoader);
		}
		catch (Throwable t) {
			_log.error(t, t);

			throw t;
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
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream);

			objectOutputStream.writeObject(throwable);

			objectOutputStream.flush();
			objectOutputStream.close();

			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				new UnsyncByteArrayInputStream(
					unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
					unsyncByteArrayOutputStream.size());
			ObjectInputStream objectInputStream =
				new ClassLoaderObjectInputStream(
					unsyncByteArrayInputStream, contextClassLoader);

			throwable = (Throwable)objectInputStream.readObject();

			objectInputStream.close();

			return throwable;
		}
		catch (Throwable throwable2) {
			_log.error(throwable2, throwable2);

			return throwable2;
		}
	}

	private Object _invoke(MethodHandler methodHandler) throws Exception {
		try {
			return methodHandler.invoke(_obj);
		}
		catch (NoSuchMethodException nsme) {
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
					return method.invoke(_obj, methodHandler.getArguments());
				}
			}

			throw nsme;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ClassLoaderProxy.class);

	private ClassLoader _classLoader;
	private String _className;
	private Object _obj;

}