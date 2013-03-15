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

package com.liferay.portal.security.lang;

import com.liferay.portal.kernel.security.pacl.NotPrivileged;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * @author Raymond Augé
 */
public class DoPrivilegedHandler
	implements DoPrivilegedBean, InvocationHandler {

	public DoPrivilegedHandler(Object bean) {
		_bean = bean;
	}

	public Object getActualBean() {
		return _bean;
	}

	public Object invoke(
			Object object, Method method, Object[] arguments)
		throws Throwable {

		if (method.getName().equals("getActualBean") &&
			method.getDeclaringClass().equals(DoPrivilegedBean.class)) {

			return getActualBean();
		}
		else if (isNotPrivileged(_bean.getClass(), method)) {
			return method.invoke(_bean, arguments);
		}

		try {
			return AccessController.doPrivileged(
				new InvokeAction(method, _bean, arguments));
		}
		catch (PrivilegedActionException pae) {
			Exception e = pae.getException();

			if (e instanceof InvocationTargetException) {
				throw ((InvocationTargetException)e).getTargetException();
			}

			throw e;
		}
	}

	private boolean isNotPrivileged(Class<?> beanClass, Method method)
		throws SecurityException {

		NotPrivileged annotation = method.getAnnotation(NotPrivileged.class);

		while((annotation == null) && (beanClass != null)) {

			try {
				method = beanClass.getMethod(
					method.getName(), method.getParameterTypes());
			}
			catch (NoSuchMethodException e) {
				break;
			}

			annotation = method.getAnnotation(NotPrivileged.class);

			if (annotation == null) {
				beanClass = beanClass.getSuperclass();
			}
		}

		if (annotation != null) {
			return true;
		}

		return false;
	}

	private Object _bean;

	public class InvokeAction implements PrivilegedExceptionAction<Object> {

		public InvokeAction(Method method, Object bean, Object[] arguments) {
			_method = method;
			_bean = bean;
			_arguments = arguments;
		}

		public Object run() throws Exception {
			return _method.invoke(_bean, _arguments);
		}

		private Object[] _arguments;
		private Object _bean;
		private Method _method;

	}

}