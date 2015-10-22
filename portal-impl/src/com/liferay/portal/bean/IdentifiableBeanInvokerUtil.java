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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.util.IdentifiableOSGIService;
import com.liferay.portal.util.IdentifiableOSGIServiceUtil;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class IdentifiableBeanInvokerUtil {

	public static MethodHandler createMethodHandler(
		MethodInvocation methodInvocation) {

		MethodHandler methodHandler = new MethodHandler(
			methodInvocation.getMethod(), methodInvocation.getArguments());

		String threadContextServletContextName = ClassLoaderPool.getContextName(
			ClassLoaderUtil.getContextClassLoader());

		IdentifiableOSGIService identifiableOSGIService =
			(IdentifiableOSGIService)methodInvocation.getThis();

		return new MethodHandler(
			_invokeMethodKey, methodHandler, threadContextServletContextName,
			identifiableOSGIService.getOSGIServiceIdentifier());
	}

	@SuppressWarnings("unused")
	private static Object _invoke(
			MethodHandler methodHandler, String threadContextServletContextName,
			String osgiServiceIdentifier)
		throws Exception {

		Object osgiService =
			IdentifiableOSGIServiceUtil.getIdentifiableOSGIService(
				osgiServiceIdentifier);

		if (osgiService == null) {
			throw new Exception(
				"Unable to load osgi service " + osgiServiceIdentifier);
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		ClassLoader classLoader = ClassLoaderPool.getClassLoader(
			threadContextServletContextName);

		ClassLoaderUtil.setContextClassLoader(classLoader);

		try {
			return methodHandler.invoke(osgiService);
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}

	private static final MethodKey _invokeMethodKey = new MethodKey(
		IdentifiableBeanInvokerUtil.class, "_invoke", MethodHandler.class,
		String.class, String.class);

}