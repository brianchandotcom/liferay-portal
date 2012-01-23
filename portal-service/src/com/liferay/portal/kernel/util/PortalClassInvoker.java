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

import com.liferay.portal.kernel.exception.SystemException;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PortalClassInvoker {

	public static Object invoke(
			boolean newInstance, MethodKey methodKey, Object... arguments)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			MethodHandler methodHandler = new MethodHandler(
				methodKey, arguments);

			return methodHandler.invoke(newInstance);
		}
		catch (InvocationTargetException ite) {
			Throwable cause = ite.getCause();

			if (cause instanceof Error) {
				throw new SystemException(ite);
			}
			else {
				throw (Exception)cause;
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public static Object invoke(
			boolean newInstance, String className, String methodName,
			String[] parameterTypeNames, Object... arguments)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			MethodKey methodKey = new MethodKey(
				className, methodName, parameterTypeNames);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, arguments);

			return methodHandler.invoke(newInstance);
		}
		catch (InvocationTargetException ite) {
			Throwable cause = ite.getCause();

			if (cause instanceof Error) {
				throw new SystemException(ite);
			}
			else {
				throw (Exception)cause;
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(String className, String methodName)
		throws Exception {

		return invoke(className, methodName, new Object[] {});
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
		String className, String methodName, boolean newInstance)
		throws Exception {

		return invoke(className, methodName, new Object[] {}, newInstance);
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(String className, String methodName, Object arg)
		throws Exception {

		return invoke(className, methodName, new Object[] {arg});
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object arg,
			boolean newInstance)
		throws Exception {

		return invoke(className, methodName, new Object[] {arg}, newInstance);
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object arg1, Object arg2)
		throws Exception {

		return invoke(className, methodName, new Object[] {arg1, arg2});
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object arg1, Object arg2,
			boolean newInstance)
		throws Exception {

		return invoke(
			className, methodName, new Object[] {arg1, arg2}, newInstance);
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object arg1, Object arg2,
			Object arg3)
		throws Exception {

		return invoke(className, methodName, new Object[] {arg1, arg2, arg3});
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object arg1, Object arg2,
			Object arg3, boolean newInstance)
		throws Exception {

		return invoke(
			className, methodName, new Object[] {arg1, arg2, arg3},
			newInstance);
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object arg1, Object arg2,
			Object arg3, Object arg4, boolean newInstance)
		throws Exception {

		return invoke(
			className, methodName, new Object[] {arg1, arg2, arg3, arg4},
			newInstance);
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, boolean newInstance)
		throws Exception {

		return invoke(
			className, methodName, new Object[] {arg1, arg2, arg3, arg4, arg5},
			newInstance);
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object[] args)
		throws Exception {

		return invoke(className, methodName, args, true);
	}

	/**
	 * @deprecated
	 */
	public static Object invoke(
			String className, String methodName, Object[] args,
			boolean newInstance)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			MethodWrapper methodWrapper = new MethodWrapper(
				className, methodName, args);

			return MethodInvoker.invoke(methodWrapper, newInstance);
		}
		catch (InvocationTargetException ite) {
			throw (Exception)ite.getCause();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

}