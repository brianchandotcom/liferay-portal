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
import com.liferay.portal.service.persistence.GroupPersistenceImpl;
import com.liferay.portal.service.persistence.UserPersistenceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLBeanHandler implements InvocationHandler {

	public PACLBeanHandler(Object bean) {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating handler for " + bean);
		}

		_bean = bean;
	}

	public Object getBean() {
		return _bean;
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

		boolean debug = false;

		if (_log.isDebugEnabled()) {
			Class<?> clazz = _bean.getClass();

			String className = clazz.getName();

			if (className.equals(GroupPersistenceImpl.class.getName()) ||
				className.equals(UserPersistenceImpl.class.getName())) {

				debug = true;

				_log.debug(
					"Intercepting " + className + "#" + method.getName());
			}
		}

		if (method.getDeclaringClass() == Object.class) {
			String methodName = method.getName();

			if (methodName.equals("equals")) {
				if (proxy == arguments[0]) {
					return true;
				}
				else {
					return false;
				}
			}
			else if (methodName.equals("toString")) {
				return method.invoke(_bean, arguments);
			}
		}

		if (!PACLPolicyManager.isActive()) {
			return method.invoke(_bean, arguments);
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
			return method.invoke(_bean, arguments);
		}

		if (!paclPolicy.hasPortalService(_bean, method, arguments)) {
			throw new SecurityException("Attempted to invoke " + method);
		}

		boolean checkSQL = PortalSecurityManagerThreadLocal.isCheckSQL();

		try {
			Class<?> beanClass = _bean.getClass();

			if (paclPolicy.getClassLoader() !=
					PACLClassLoaderUtil.getClassLoader(beanClass)) {

				// Disable the portal security manager so that PACLDataSource
				// does not try to check access to tables that can be accessed
				// since the service is already approved

				PortalSecurityManagerThreadLocal.setCheckSQL(false);
			}

			return method.invoke(_bean, arguments);
		}
		finally {
			PortalSecurityManagerThreadLocal.setCheckSQL(checkSQL);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PACLBeanHandler.class);

	private Object _bean;

}