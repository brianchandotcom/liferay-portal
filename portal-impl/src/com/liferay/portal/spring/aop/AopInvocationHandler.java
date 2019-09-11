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
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionHandler;
import com.liferay.portal.spring.transaction.TransactionInterceptor;
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

	public synchronized void setTarget(Object target) {
		_target = target;

		_aopMethodInvocations.clear();
	}

	protected AopInvocationHandler(
		Object target, ChainableMethodAdvice[] chainableMethodAdvices,
		TransactionHandler transactionHandler) {

		_target = target;
		_chainableMethodAdvices = chainableMethodAdvices;
		_transactionInterceptor = new TransactionInterceptor(
			transactionHandler);
	}

	protected synchronized void reset() {
		_aopMethodInvocations.clear();
	}

	protected synchronized void setChainableMethodAdvices(
		ChainableMethodAdvice[] chainableMethodAdvices) {

		_chainableMethodAdvices = chainableMethodAdvices;

		_aopMethodInvocations.clear();
	}

	private static AopMethodInvocation _createAopMethodInvocation(
		Object target, Method method,
		ChainableMethodAdvice[] chainableMethodAdvices,
		TransactionInterceptor transactionInterceptor) {

		AopMethodInvocation aopMethodInvocation = null;

		ChainableMethodAdvice nextChainableMethodAdvice = null;

		Class<?> targetClass = target.getClass();

		Map<Class<? extends Annotation>, Annotation> annotations =
			AnnotationLocator.index(method, targetClass);

		TransactionAttributeAdapter transactionAttributeAdapter =
			transactionInterceptor.createMethodContext(
				targetClass, method, annotations);

		if (transactionAttributeAdapter != null) {
			aopMethodInvocation = new AopMethodInvocationImpl(
				target, method, transactionAttributeAdapter,
				nextChainableMethodAdvice, aopMethodInvocation);

			nextChainableMethodAdvice = transactionInterceptor;
		}

		for (int i = chainableMethodAdvices.length - 1; i >= 0; i--) {
			ChainableMethodAdvice chainableMethodAdvice =
				chainableMethodAdvices[i];

			Object methodContext = chainableMethodAdvice.createMethodContext(
				targetClass, method, annotations);

			if (methodContext != null) {
				aopMethodInvocation = new AopMethodInvocationImpl(
					target, method, methodContext, nextChainableMethodAdvice,
					aopMethodInvocation);

				nextChainableMethodAdvice = chainableMethodAdvice;
			}
		}

		return new AopMethodInvocationImpl(
			target, method, null, nextChainableMethodAdvice,
			aopMethodInvocation);
	}

	private AopMethodInvocation _getAopMethodInvocation(Method method) {
		if (TransactionsUtil.isEnabled()) {
			AopMethodInvocation aopMethodInvocation = _aopMethodInvocations.get(
				method);

			if (aopMethodInvocation == null) {
				AopMethodInvocation previousAopMethodInvocation = null;

				synchronized (this) {
					aopMethodInvocation = _createAopMethodInvocation(
						_target, method, _chainableMethodAdvices,
						_transactionInterceptor);

					previousAopMethodInvocation =
						_aopMethodInvocations.putIfAbsent(
							method, aopMethodInvocation);
				}

				if (previousAopMethodInvocation != null) {
					aopMethodInvocation = previousAopMethodInvocation;
				}
			}

			return aopMethodInvocation;
		}

		return new AopMethodInvocationImpl(_target, method, null, null, null);
	}

	private final Map<Method, AopMethodInvocation> _aopMethodInvocations =
		new ConcurrentHashMap<>();
	private ChainableMethodAdvice[] _chainableMethodAdvices;
	private volatile Object _target;
	private final TransactionInterceptor _transactionInterceptor;

}