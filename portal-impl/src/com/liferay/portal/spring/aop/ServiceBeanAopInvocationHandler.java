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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 * @author Preston Crary
 */
public class ServiceBeanAopInvocationHandler implements InvocationHandler {

	public AopMethod getAopMethod(Method method) {
		if (TransactionsUtil.isEnabled()) {
			return _aopMethods.computeIfAbsent(method, this::_createAopMethod);
		}

		return new AopMethod(
			_target, method, _emptyChainableMethodAdvices, null);
	}

	public Object getTarget() {
		return _target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			new ServiceBeanMethodInvocation(getAopMethod(method), arguments);

		return serviceBeanMethodInvocation.proceed();
	}

	public void setTarget(Object target) {
		_target = target;

		_aopMethods.clear();
	}

	protected ServiceBeanAopInvocationHandler(
		Object target, ChainableMethodAdvice[] chainableMethodAdvices) {

		_target = target;
		_chainableMethodAdvices = chainableMethodAdvices;
	}

	protected void reset() {
		_aopMethods.clear();
	}

	private AopMethod _createAopMethod(Method method) {
		Object target = _target;

		Class<?> targetClass = target.getClass();

		List<ChainableMethodAdvice> filteredChainableMethodAdvices =
			new ArrayList<>();
		List<Object> filteredAdviceMethodContexts = new ArrayList<>();

		Map<Class<? extends Annotation>, Annotation> annotations =
			AnnotationLocator.index(method, targetClass);

		for (ChainableMethodAdvice chainableMethodAdvice :
				_chainableMethodAdvices) {

			Object methodContext = chainableMethodAdvice.createMethodContext(
				targetClass, method, annotations);

			if (methodContext != null) {
				filteredChainableMethodAdvices.add(chainableMethodAdvice);

				filteredAdviceMethodContexts.add(methodContext);
			}
		}

		ChainableMethodAdvice[] chainableMethodAdvices =
			_emptyChainableMethodAdvices;
		Object[] adviceMethodContexts = null;

		if (!filteredChainableMethodAdvices.isEmpty()) {
			chainableMethodAdvices = filteredChainableMethodAdvices.toArray(
				new ChainableMethodAdvice
					[filteredChainableMethodAdvices.size()]);

			adviceMethodContexts = filteredAdviceMethodContexts.toArray(
				new Object[filteredAdviceMethodContexts.size()]);
		}

		return new AopMethod(
			target, method, chainableMethodAdvices, adviceMethodContexts);
	}

	private static final ChainableMethodAdvice[] _emptyChainableMethodAdvices =
		new ChainableMethodAdvice[0];

	private final Map<Method, AopMethod> _aopMethods =
		new ConcurrentHashMap<>();
	private final ChainableMethodAdvice[] _chainableMethodAdvices;
	private volatile Object _target;

}