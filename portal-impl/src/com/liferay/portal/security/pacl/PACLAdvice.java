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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.service.impl.PortalServiceImpl;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLAdvice extends ChainableMethodAdvice {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {

			// Proceed so that we do not remove the advice

			try {
				return methodInvocation.proceed();
			}
			catch (Throwable throwable) {
				throw throwable;
			}
		}

		if (!PACLPolicyManager.isActive()) {
			serviceBeanAopCacheManager.removeMethodInterceptor(
				methodInvocation, this);

			try {
				return methodInvocation.proceed();
			}
			catch (Throwable throwable) {
				throw throwable;
			}
		}

		Object thisObject = methodInvocation.getThis();
		Method method = methodInvocation.getMethod();
		Object[] arguments = methodInvocation.getArguments();

		boolean debug = false;

		if (_log.isDebugEnabled()) {
			Class<?> clazz = thisObject.getClass();

			String className = clazz.getName();

			if (className.equals(PortalServiceImpl.class.getName()) ||
				className.equals(_ENTRY_LOCAL_SERVICE_IMPL_CLASS_NAME) ||
				className.equals(_STATUS_LOCAL_SERVICE_IMPL_CLASS_NAME)) {

				debug = true;

				_log.debug(
					"Intercepting " + className + "#" + method.getName());
			}
		}

		if (method.getDeclaringClass() == Object.class) {
			String methodName = method.getName();

			if (methodName.equals("equals")) {
				if (thisObject == arguments[0]) {
					return true;
				}
				else {
					return false;
				}
			}
			else if (methodName.equals("toString")) {
				return method.invoke(thisObject, arguments);
			}
		}

		if (!PACLPolicyManager.isActive()) {
			return method.invoke(thisObject, arguments);
		}

		PACLPolicy paclPolicy = PACLClassUtil.getPACLPolicy(false, debug);

		if (debug) {
			if (paclPolicy != null) {
				_log.debug(
					"Retrieved PACL policy for " +
						paclPolicy.getServletContextName());
			}
		}

		if (paclPolicy == null) {
			return methodInvocation.proceed();
		}

		if (!paclPolicy.hasPortalService(thisObject, method, arguments)) {
			throw new SecurityException("Attempted to invoke " + method);
		}

		boolean checkSQL = PortalSecurityManagerThreadLocal.isCheckSQL();

		try {
			Class<?> thisObjectClass = thisObject.getClass();

			if (paclPolicy.getClassLoader() !=
					PACLClassLoaderUtil.getClassLoader(thisObjectClass)) {

				// Disable the portal security manager so that PACLDataSource
				// does not try to check access to tables that can be accessed
				// since the service is already approved

				PortalSecurityManagerThreadLocal.setCheckSQL(false);
			}

			return methodInvocation.proceed();
		}
		finally {
			PortalSecurityManagerThreadLocal.setCheckSQL(checkSQL);
		}
	}

	private static final String _ENTRY_LOCAL_SERVICE_IMPL_CLASS_NAME =
		"com.liferay.chat.service.impl.EntryLocalServiceImpl";

	private static final String _STATUS_LOCAL_SERVICE_IMPL_CLASS_NAME =
		"com.liferay.chat.service.impl.StatusLocalServiceImpl";

	private static Log _log = LogFactoryUtil.getLog(PACLAdvice.class);

}