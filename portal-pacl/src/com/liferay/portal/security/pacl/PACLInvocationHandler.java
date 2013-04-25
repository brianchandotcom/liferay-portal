/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalServicePermission;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PACLInvocationHandler implements InvocationHandler {

	public PACLInvocationHandler(InvocationHandler invocationHandler) {
		this(invocationHandler, null);
	}

	public PACLInvocationHandler(
		InvocationHandler invocationHandler, AdvisedSupport advisedSupport) {

		_invocationHandler = invocationHandler;
		_advisedSupport = advisedSupport;
	}

	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			return doInvoke(proxy, method, arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	protected Object doInvoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		if (!PACLPolicyManager.isActive()) {
			return _invocationHandler.invoke(proxy, method, arguments);
		}

		if (method.getDeclaringClass() == Object.class) {
			String methodName = method.getName();

			if (methodName.equals("equals")) {
				if (proxy == arguments[0]) {
					return true;
				}

				return false;
			}
			else if (methodName.equals("toString")) {
				return _invocationHandler.invoke(proxy, method, arguments);
			}
		}

		PortalServicePermission.checkService(proxy, method, arguments);

		try {
			return AccessController.doPrivileged(
				new InvokePrivilegedExceptionAction(
					_invocationHandler, proxy, method, arguments));
		}
		catch (PrivilegedActionException pae) {
			throw pae.getException().getCause();
		}
	}

	@SuppressWarnings("unused")
	private AdvisedSupport _advisedSupport;

	private InvocationHandler _invocationHandler;

	private class InvokePrivilegedExceptionAction
		implements PrivilegedExceptionAction<Object> {

		public InvokePrivilegedExceptionAction(
			InvocationHandler invocationHandler, Object proxy, Method method,
			Object[] arguments) {

			_invocationHandler = invocationHandler;
			_proxy = proxy;
			_method = method;
			_arguments = arguments;
		}

		public Object run() throws Exception {
			try {
				return _invocationHandler.invoke(_proxy, _method, _arguments);
			}
			catch (Throwable t) {
				throw new Exception(t);
			}
		}

		private Object[] _arguments;
		private InvocationHandler _invocationHandler;
		private Method _method;
		private Object _proxy;

	}

}