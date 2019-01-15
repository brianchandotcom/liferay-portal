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
public class AopInvocationHandler implements InvocationHandler {

	public Object getTarget() {
		return _target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		AopMethodInvocation aopMethodInvocation = _getAopMethodInvocation(
			method);

		return aopMethodInvocation.proceed(arguments);
	}

	public void setTarget(Object target) {
		_target = target;

		_aopMethodInvocations.clear();
	}

	protected AopInvocationHandler(
		Object target, ChainableMethodAdvice[] chainableMethodAdvices) {

		_target = target;
		_chainableMethodAdvices = chainableMethodAdvices;
	}

	protected void reset() {
		_aopMethodInvocations.clear();
	}

	private AopMethodInvocation _createAopMethodInvocation(Method method) {
		AopMethodInvocation aopMethodInvocation = null;

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
				aopMethodInvocation = new AopMethodInvocation(
					target, method, methodContext, nextChainableMethodAdvice,
					aopMethodInvocation);

				nextChainableMethodAdvice = chainableMethodAdvice;
			}
		}

		return new AopMethodInvocation(
			target, method, null, nextChainableMethodAdvice,
			aopMethodInvocation);
	}

	private AopMethodInvocation _getAopMethodInvocation(Method method) {
		if (TransactionsUtil.isEnabled()) {
			return _aopMethodInvocations.computeIfAbsent(
				method, this::_createAopMethodInvocation);
		}

		return new AopMethodInvocation(_target, method, null, null, null);
	}

	private final Map<Method, AopMethodInvocation> _aopMethodInvocations =
		new ConcurrentHashMap<>();
	private final ChainableMethodAdvice[] _chainableMethodAdvices;
	private volatile Object _target;

}