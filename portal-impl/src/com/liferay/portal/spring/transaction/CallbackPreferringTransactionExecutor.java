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

import com.liferay.portal.kernel.transaction.TransactionLifecycleManager;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class CallbackPreferringTransactionExecutor
	implements TransactionExecutor {

	@Override
	public Object execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttributeAdapter transactionAttributeAdapter,
			MethodInvocation methodInvocation)
		throws Throwable {

		CallbackPreferringPlatformTransactionManager
			callbackPreferringPlatformTransactionManager =
				(CallbackPreferringPlatformTransactionManager)
					platformTransactionManager;

		try {
			Object result =
				callbackPreferringPlatformTransactionManager.execute(
					transactionAttributeAdapter,
					createTransactionCallback(
						callbackPreferringPlatformTransactionManager,
						transactionAttributeAdapter, methodInvocation));

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
		CallbackPreferringPlatformTransactionManager
			callbackPreferringPlatformTransactionManager,
		TransactionAttributeAdapter transactionAttributeAdapter,
		MethodInvocation methodInvocation) {

		return new CallbackPreferringTransactionCallback(
			callbackPreferringPlatformTransactionManager,
			transactionAttributeAdapter, methodInvocation);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #createTransactionCallback(
	 *             CallbackPreferringPlatformTransactionManager,
	 *             TransactionAttributeAdapter, MethodInvocation)}
	 */
	@Deprecated
	protected TransactionCallback<Object> createTransactionCallback(
		TransactionAttributeAdapter transactionAttributeAdapter,
		MethodInvocation methodInvocation) {

		return new CallbackPreferringTransactionCallback(
			null, transactionAttributeAdapter, methodInvocation);
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
			TransactionStatusAdapter transactionStatusAdapter =
				new TransactionStatusAdapter(
					_platformTransactionManager, transactionStatus);

			TransactionLifecycleManager.fireTransactionCreatedEvent(
				_transactionAttributeAdapter, transactionStatusAdapter);

			boolean rollback = false;

			try {
				return _methodInvocation.proceed();
			}
			catch (Throwable throwable) {
				if (_transactionAttributeAdapter.rollbackOn(throwable)) {
					TransactionLifecycleManager.fireTransactionRollbackedEvent(
						_transactionAttributeAdapter, transactionStatusAdapter,
						throwable);

					if (transactionStatus.isNewTransaction()) {
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
				if (!rollback) {
					TransactionLifecycleManager.fireTransactionCommittedEvent(
						_transactionAttributeAdapter, transactionStatusAdapter);
				}
			}
		}

		private CallbackPreferringTransactionCallback(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttributeAdapter transactionAttributeAdapter,
			MethodInvocation methodInvocation) {

			_platformTransactionManager = platformTransactionManager;
			_transactionAttributeAdapter = transactionAttributeAdapter;
			_methodInvocation = methodInvocation;
		}

		private final MethodInvocation _methodInvocation;
		private final PlatformTransactionManager _platformTransactionManager;
		private final TransactionAttributeAdapter _transactionAttributeAdapter;

	}

}