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

package com.liferay.portal.spring.aop;

import com.liferay.portal.transaction.TransactionsUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopInvocationHandler implements InvocationHandler {

	public ServiceBeanAopInvocationHandler(
		Object bean, ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		_target = bean;
		_serviceBeanAopCacheManager = serviceBeanAopCacheManager;
	}

	public Object getTarget() {
		return _target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			new ServiceBeanMethodInvocation(_target, method, arguments);

		if (TransactionsUtil.isEnabled()) {
			serviceBeanMethodInvocation.setMethodInterceptors(
				_serviceBeanAopCacheManager.getMethodInterceptors(method));
		}
		else {
			serviceBeanMethodInvocation.setMethodInterceptors(
				_emptyMethodInterceptors);
		}

		return serviceBeanMethodInvocation.proceed();
	}

	public void setTarget(Object target) {
		_target = target;

		_serviceBeanAopCacheManager.reset();
	}

	private static final MethodInterceptor[] _emptyMethodInterceptors =
		new MethodInterceptor[0];

	private final ServiceBeanAopCacheManager _serviceBeanAopCacheManager;
	private volatile Object _target;

}