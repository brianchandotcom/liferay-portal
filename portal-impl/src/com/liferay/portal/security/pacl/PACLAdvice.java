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
import com.liferay.portal.service.impl.PortalServiceImpl;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLAdvice extends ChainableMethodAdvice {

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		if (!PACLPolicyManager.isActive()) {
			ServiceBeanAopProxy.removeMethodInterceptor(methodInvocation, this);

			return null;
		}

		Method method = methodInvocation.getMethod();

		boolean debug = false;

		if (_log.isDebugEnabled()) {
			Object thisObject = methodInvocation.getThis();

			Class<?> clazz = thisObject.getClass();

			String className = clazz.getName();

			if (className.equals(PortalServiceImpl.class.getName()) ||
				className.equals(_STATUS_LOCAL_SERVICE_IMPL_CLASS_NAME)) {

				debug = true;

				_log.debug(
					"Intercepting " + className + "#" + method.getName());
			}
		}

		PACLPolicy paclPolicy = PACLClassUtil.getPACLPolicyByReflection(debug);

		if (debug) {
			if (paclPolicy != null) {
				_log.debug(
					"Retrieved PACL policy for " +
						paclPolicy.getServletContextName());
			}
		}

		if (paclPolicy != null) {
			if (!paclPolicy.hasServicePermission(
					methodInvocation.getThis(), method)) {

				throw new SecurityException("Attempted to invoke " + method);
			}
		}

		return null;
	}

	private static final String _STATUS_LOCAL_SERVICE_IMPL_CLASS_NAME =
		"com.liferay.chat.service.impl.StatusLocalServiceImpl";

	private static Log _log = LogFactoryUtil.getLog(PACLAdvice.class);

}