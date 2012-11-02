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

}