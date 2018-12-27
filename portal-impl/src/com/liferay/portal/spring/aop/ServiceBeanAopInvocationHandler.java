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

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.transaction.TransactionsUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 * @author Preston Crary
 */
public class ServiceBeanAopInvocationHandler implements InvocationHandler {

	public Object getTarget() {
		return _target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			_getServiceBeanMethodInvocation(method);

		return serviceBeanMethodInvocation.proceed(arguments);
	}

	public void setTarget(Object target) {
		_target = target;

		_serviceBeanMethodInvocations.clear();
	}

	protected ServiceBeanAopInvocationHandler(
		Object target, ChainableMethodAdvice[] chainableMethodAdvices) {

		_target = target;
		_chainableMethodAdvices = chainableMethodAdvices;
	}

	protected void reset() {
		_serviceBeanMethodInvocations.clear();
	}

	private ServiceBeanMethodInvocation _createServiceBeanMethodInvocation(
		Method method) {

		ServiceBeanMethodInvocation serviceBeanMethodInvocation = null;

		ChainableMethodAdvice nextChainableMethodAdvice = null;

		Object target = _target;

		Class<?> targetClass = target.getClass();

		Map<Class<? extends Annotation>, Annotation> annotations =
			AnnotationLocator.index(method, targetClass);

		for (int i = _chainableMethodAdvices.length - 1; i >= 0; i--) {
			ChainableMethodAdvice chainableMethodAdvice =
				_chainableMethodAdvices[i];

			Object methodContext = chainableMethodAdvice.createMethodContext(
				targetClass, method, annotations);

			if (methodContext != null) {
				serviceBeanMethodInvocation = new ServiceBeanMethodInvocation(
					target, method, methodContext, nextChainableMethodAdvice,
					serviceBeanMethodInvocation);

				nextChainableMethodAdvice = chainableMethodAdvice;
			}
		}

		return new ServiceBeanMethodInvocation(
			target, method, null, nextChainableMethodAdvice,
			serviceBeanMethodInvocation);
	}

	private ServiceBeanMethodInvocation _getServiceBeanMethodInvocation(
		Method method) {

		if (TransactionsUtil.isEnabled()) {
			return _serviceBeanMethodInvocations.computeIfAbsent(
				method, this::_createServiceBeanMethodInvocation);
		}

		return new ServiceBeanMethodInvocation(
			_target, method, null, null, null);
	}

	private final ChainableMethodAdvice[] _chainableMethodAdvices;
	private final Map<Method, ServiceBeanMethodInvocation>
		_serviceBeanMethodInvocations = new ConcurrentHashMap<>();
	private volatile Object _target;

}