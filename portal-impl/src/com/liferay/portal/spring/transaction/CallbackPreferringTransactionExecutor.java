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

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class CallbackPreferringTransactionExecutor
	extends BaseTransactionExecutor {

	@Override
	public Object execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttribute transactionAttribute,
			MethodInvocation methodInvocation)
		throws Throwable {

		CallbackPreferringPlatformTransactionManager
			callbackPreferringPlatformTransactionManager =
				(CallbackPreferringPlatformTransactionManager)
					platformTransactionManager;

		try {
			Object result =
				callbackPreferringPlatformTransactionManager.execute(
					transactionAttribute,
					createTransactionCallback(
						transactionAttribute, methodInvocation));

			if (result instanceof ThrowableHolder) {
				ThrowableHolder throwableHolder = (ThrowableHolder)result;

				throw throwableHolder.getThrowable();
			}

			return result;
		}
		catch (ThrowableHolderException the) {
			throw the.getCause();
		}
	}

	protected TransactionCallback<Object> createTransactionCallback(
		TransactionAttribute transactionAttribute,
		MethodInvocation methodInvocation) {

		return new CallbackPreferringTransactionCallback(
			transactionAttribute, methodInvocation);
	}

	protected static class ThrowableHolder {

		public ThrowableHolder(Throwable throwable) {
			_throwable = throwable;
		}

		public Throwable getThrowable() {
			return _throwable;
		}

		private final Throwable _throwable;

	}

	protected static class ThrowableHolderException extends RuntimeException {

		public ThrowableHolderException(Throwable cause) {
			super(cause);
		}

	}

	private class CallbackPreferringTransactionCallback
		implements TransactionCallback<Object> {

		@Override
		public Object doInTransaction(TransactionStatus transactionStatus) {
			boolean newTransaction = transactionStatus.isNewTransaction();

			if (newTransaction) {
				fireTransactionCreatedEvent(
					_transactionAttribute, transactionStatus);
			}

			boolean rollback = false;

			try {
				return _methodInvocation.proceed();
			}
			catch (Throwable throwable) {
				if (_transactionAttribute.rollbackOn(throwable)) {
					if (newTransaction) {
						fireTransactionRollbackedEvent(
							_transactionAttribute, transactionStatus,
							throwable);

						rollback = true;
					}

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
			finally {
				if (newTransaction && !rollback) {
					fireTransactionCommittedEvent(
						_transactionAttribute, transactionStatus);
				}
			}
		}

		private CallbackPreferringTransactionCallback(
			TransactionAttribute transactionAttribute,
			MethodInvocation methodInvocation) {

			_transactionAttribute = transactionAttribute;
			_methodInvocation = methodInvocation;
		}

		private final MethodInvocation _methodInvocation;
		private final TransactionAttribute _transactionAttribute;

	}

}