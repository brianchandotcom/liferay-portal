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
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

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

	public CallbackPreferringTransactionExecutor(
		PlatformTransactionManager platformTransactionManager) {

		_platformTransactionManager = platformTransactionManager;
	}

	@Override
	public Object execute(
			TransactionAttributeAdapter transactionAttributeAdapter,
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
		throws Throwable {

		return _execute(
			_platformTransactionManager, transactionAttributeAdapter,
			serviceBeanMethodInvocation);
	}

	@Override
	public PlatformTransactionManager getPlatformTransactionManager() {
		return _platformTransactionManager;
	}

	protected TransactionCallback<Object> createTransactionCallback(
		CallbackPreferringPlatformTransactionManager
			callbackPreferringPlatformTransactionManager,
		TransactionAttributeAdapter transactionAttributeAdapter,
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		return new CallbackPreferringTransactionCallback(
			callbackPreferringPlatformTransactionManager,
			transactionAttributeAdapter, serviceBeanMethodInvocation);
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

	private Object _execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttributeAdapter transactionAttributeAdapter,
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
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
						transactionAttributeAdapter,
						serviceBeanMethodInvocation));

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

	private final PlatformTransactionManager _platformTransactionManager;

	private class CallbackPreferringTransactionCallback
		implements TransactionCallback<Object> {

		@Override
		public Object doInTransaction(TransactionStatus transactionStatus) {
			TransactionStatusAdapter transactionStatusAdapter =
				new TransactionStatusAdapter(transactionStatus);

			TransactionExecutorThreadLocal.pushTransactionExecutor(
				CallbackPreferringTransactionExecutor.this);

			TransactionLifecycleManager.fireTransactionCreatedEvent(
				_transactionAttributeAdapter, transactionStatusAdapter);

			boolean rollback = false;

			try {
				return _serviceBeanMethodInvocation.proceed();
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

				TransactionExecutorThreadLocal.popTransactionExecutor();
			}
		}

		private CallbackPreferringTransactionCallback(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttributeAdapter transactionAttributeAdapter,
			ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

			_platformTransactionManager = platformTransactionManager;
			_transactionAttributeAdapter = transactionAttributeAdapter;
			_serviceBeanMethodInvocation = serviceBeanMethodInvocation;
		}

		private final PlatformTransactionManager _platformTransactionManager;
		private final ServiceBeanMethodInvocation _serviceBeanMethodInvocation;
		private final TransactionAttributeAdapter _transactionAttributeAdapter;

	}

}