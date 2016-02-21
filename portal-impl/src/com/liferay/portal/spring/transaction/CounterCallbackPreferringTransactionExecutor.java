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

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

/**
 * @author Shuyang Zhou
 */
public class CounterCallbackPreferringTransactionExecutor
	extends CallbackPreferringTransactionExecutor {

	@Override
	protected TransactionCallback<Object> createTransactionCallback(
		TransactionAttributeAdaptor transactionAttributeAdaptor,
		MethodInvocation methodInvocation) {

		return new CounterCallbackPreferringTransactionCallback(
			transactionAttributeAdaptor, methodInvocation);
	}

	private static class CounterCallbackPreferringTransactionCallback
		implements TransactionCallback<Object> {

		@Override
		public Object doInTransaction(TransactionStatus transactionStatus) {
			try {
				return _methodInvocation.proceed();
			}
			catch (Throwable throwable) {
				if (_transactionAttributeAdaptor.rollbackOn(throwable)) {
					if (throwable instanceof RuntimeException) {
						throw (RuntimeException)throwable;
					}
					else {
						throw new ThrowableHolderException(throwable);
					}
				}
				else {
					return new ThrowableHolder(throwable);
				}
			}
		}

		private CounterCallbackPreferringTransactionCallback(
			TransactionAttributeAdaptor transactionAttributeAdaptor,
			MethodInvocation methodInvocation) {

			_transactionAttributeAdaptor = transactionAttributeAdaptor;
			_methodInvocation = methodInvocation;
		}

		private final MethodInvocation _methodInvocation;
		private final TransactionAttributeAdaptor _transactionAttributeAdaptor;

	}

}