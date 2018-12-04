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

import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;

/**
 * @author Shuyang Zhou
 */
public class CounterCallbackPreferringTransactionExecutor
	extends CallbackPreferringTransactionExecutor {

	public CounterCallbackPreferringTransactionExecutor(
		PlatformTransactionManager platformTransactionManager) {

		super(platformTransactionManager);
	}

	@Override
	protected TransactionCallback<Object> createTransactionCallback(
		CallbackPreferringPlatformTransactionManager
			callbackPreferringPlatformTransactionManager,
		TransactionAttributeAdapter transactionAttributeAdapter,
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		return new CounterCallbackPreferringTransactionCallback(
			transactionAttributeAdapter, serviceBeanMethodInvocation);
	}

	private static class CounterCallbackPreferringTransactionCallback
		implements TransactionCallback<Object> {

		@Override
		public Object doInTransaction(TransactionStatus transactionStatus) {
			try {
				return _serviceBeanMethodInvocation.proceed();
			}
			catch (Throwable throwable) {
				if (_transactionAttributeAdapter.rollbackOn(throwable)) {
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
			TransactionAttributeAdapter transactionAttributeAdapter,
			ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

			_transactionAttributeAdapter = transactionAttributeAdapter;
			_serviceBeanMethodInvocation = serviceBeanMethodInvocation;
		}

		private final ServiceBeanMethodInvocation _serviceBeanMethodInvocation;
		private final TransactionAttributeAdapter _transactionAttributeAdapter;

	}

}