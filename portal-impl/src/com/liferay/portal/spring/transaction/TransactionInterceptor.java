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

package com.liferay.portal.spring.transaction;

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.transaction.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 */
public class TransactionInterceptor extends ChainableMethodAdvice {

	public TransactionInterceptor(TransactionHandler transactionHandler) {
		_transactionHandler = transactionHandler;
	}

	@Override
	public TransactionAttributeAdapter createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		Transactional transactional = (Transactional)annotations.get(
			Transactional.class);

		TransactionAttribute transactionAttribute =
			TransactionAttributeBuilder.build(transactional);

		if (transactionAttribute == null) {
			return null;
		}

		return new TransactionAttributeAdapter(transactionAttribute);
	}

	@Override
	public Object invoke(
			AopMethodInvocation aopMethodInvocation, Object[] arguments)
		throws Throwable {

		TransactionAttributeAdapter transactionAttributeAdapter =
			aopMethodInvocation.getAdviceMethodContext();

		TransactionStatusAdapter transactionStatusAdapter =
			_transactionHandler.start(transactionAttributeAdapter);

		Object returnValue = null;

		try {
			returnValue = aopMethodInvocation.proceed(arguments);
		}
		catch (Throwable throwable) {
			_transactionHandler.rollback(
				throwable, transactionAttributeAdapter,
				transactionStatusAdapter);
		}

		_transactionHandler.commit(
			transactionAttributeAdapter, transactionStatusAdapter);

		return returnValue;
	}

	private final TransactionHandler _transactionHandler;

}