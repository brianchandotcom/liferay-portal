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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.concurrent.Callable;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 */
public class TransactionalCallableUtil {

	public static <T> T call(
			TransactionAttribute transactionAttribute, Callable<T> callable)
		throws Throwable {

		return (T)_transactionExecutor.execute(
			_platformTransactionManager, transactionAttribute,
			new CallableMethodInvocation(callable));
	}

	public void setPlatformTransactionManager(
		PlatformTransactionManager platformTransactionManager) {

		_platformTransactionManager = platformTransactionManager;
	}

	public void setTransactionExecutor(
		TransactionExecutor transactionExecutor) {

		_transactionExecutor = transactionExecutor;
	}

	private static PlatformTransactionManager _platformTransactionManager;
	private static TransactionExecutor _transactionExecutor;

	private static class CallableMethodInvocation implements MethodInvocation {

		@Override
		public Object[] getArguments() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Method getMethod() {
			throw new UnsupportedOperationException();
		}

		@Override
		public AccessibleObject getStaticPart() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object getThis() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object proceed() throws Throwable {
			return _callable.call();
		}

		private CallableMethodInvocation(Callable<?> callable) {
			_callable = callable;
		}

		private final Callable<?> _callable;

	}

}