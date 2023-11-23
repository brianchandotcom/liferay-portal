/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.module.framework.service;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;
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

		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				ClassLoaderPool.getClassLoader(contextName))) {

			return methodHandler.invoke(osgiService);
		}
	}

	private static final MethodKey _invokeMethodKey = new MethodKey(
		IdentifiableOSGiServiceInvokerUtil.class, "_invoke",
		MethodHandler.class, String.class, String.class);

}