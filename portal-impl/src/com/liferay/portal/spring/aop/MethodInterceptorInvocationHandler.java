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

package com.liferay.portal.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Shuyang Zhou
 */
public class MethodInterceptorInvocationHandler implements InvocationHandler {

	public MethodInterceptorInvocationHandler(
		Object target, List<MethodInterceptor> methodInterceptors) {

		if (target == null) {
			throw new NullPointerException("Target is null");
		}

		_target = target;
		_targetClass = target.getClass();

		if (methodInterceptors == null) {
			throw new NullPointerException("Method interceptors is null");
		}

		if (methodInterceptors.isEmpty()) {
			throw new IllegalArgumentException("Method interceptors is empty");
		}

		for (int i = 0; i < methodInterceptors.size(); i++) {
			if (methodInterceptors.get(i) == null) {
				throw new IllegalArgumentException(
					"Method interceptor " + i + " is null");
			}
		}

		_methodInterceptors = methodInterceptors;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			new ServiceBeanMethodInvocation(
				_target, _targetClass, method, arguments);

		serviceBeanMethodInvocation.setMethodInterceptors(_methodInterceptors);

		return serviceBeanMethodInvocation.proceed();
	}

	private List<MethodInterceptor> _methodInterceptors;
	private Object _target;
	private Class<?> _targetClass;

}