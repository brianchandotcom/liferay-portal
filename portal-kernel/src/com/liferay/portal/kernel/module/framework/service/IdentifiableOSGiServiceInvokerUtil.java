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

package com.liferay.portal.kernel.module.framework.service;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class IdentifiableOSGiServiceInvokerUtil {

	public static MethodHandler createMethodHandler(
		Object targetObject, Method method, Object[] args) {

		MethodHandler methodHandler = new MethodHandler(method, args);

		Thread currentThread = Thread.currentThread();

		String contextName = ClassLoaderPool.getContextName(
			currentThread.getContextClassLoader());

		IdentifiableOSGiService identifiableOSGiService =
			(IdentifiableOSGiService)targetObject;

		return new MethodHandler(
			_invokeMethodKey, methodHandler, contextName,
			identifiableOSGiService.getOSGiServiceIdentifier());
	}

	@SuppressWarnings("unused")
	private static Object _invoke(
			MethodHandler methodHandler, String contextName,
			String osgiServiceIdentifier)
		throws Exception {

		Object osgiService =
			IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
				osgiServiceIdentifier);

		if (osgiService == null) {
			throw new Exception(
				"Unable to load OSGi service " + osgiServiceIdentifier);
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			ClassLoaderPool.getClassLoader(contextName));

		try {
			return methodHandler.invoke(osgiService);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private static final MethodKey _invokeMethodKey = new MethodKey(
		IdentifiableOSGiServiceInvokerUtil.class, "_invoke",
		MethodHandler.class, String.class, String.class);

}