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
import com.liferay.portal.security.pacl.permission.PortalServicePermission;
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
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (!PACLPolicyManager.isActive()) {
			ServiceBeanAopProxy.removeMethodInterceptor(methodInvocation, this);

			try {
				return methodInvocation.proceed();
			}
			catch (Throwable throwable) {
				throw throwable;
			}
		}

		Method method = methodInvocation.getMethod();

		if (_log.isDebugEnabled()) {
			Object thisObject = methodInvocation.getThis();

			Class<?> clazz = thisObject.getClass();

			String className = clazz.getName();

			if (className.equals(PortalServiceImpl.class.getName()) ||
				className.equals(_ENTRY_LOCAL_SERVICE_IMPL_CLASS_NAME) ||
				className.equals(_STATUS_LOCAL_SERVICE_IMPL_CLASS_NAME)) {

				_log.debug(
					"Intercepting " + className + "#" + method.getName());
			}
		}

		SecurityManager sm = System.getSecurityManager();

		if (sm != null) {
			sm.checkPermission(
				new PortalServicePermission(
					"hasService", null, methodInvocation.getThis(), method));
		}

		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			return methodInvocation.proceed();
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	private static final String _ENTRY_LOCAL_SERVICE_IMPL_CLASS_NAME =
		"com.liferay.chat.service.impl.EntryLocalServiceImpl";

	private static final String _STATUS_LOCAL_SERVICE_IMPL_CLASS_NAME =
		"com.liferay.chat.service.impl.StatusLocalServiceImpl";

	private static Log _log = LogFactoryUtil.getLog(PACLAdvice.class);

}