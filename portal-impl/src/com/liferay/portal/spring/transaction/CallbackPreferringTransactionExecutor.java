/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.cache.transactional.TransactionalPortalCacheHelper;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.spring.hibernate.LastSessionRecorderUtil;

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
					new CallbackPreferringTransactionCallback(
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

	protected static class ThrowableHolder {

		public ThrowableHolder(Throwable throwable) {
			_throwable = throwable;
		}

		public Throwable getThrowable() {
			return _throwable;
		}

		private Throwable _throwable;

	}

	protected static class ThrowableHolderException extends RuntimeException {

		public ThrowableHolderException(Throwable cause) {
			super(cause);
		}

	}

	private class CallbackPreferringTransactionCallback
		implements TransactionCallback<Object> {

		private CallbackPreferringTransactionCallback(
			TransactionAttribute transactionAttribute,
			MethodInvocation methodInvocation) {

			_transactionAttribute = transactionAttribute;
			_methodInvocation = methodInvocation;
		}

		@Override
		public Object doInTransaction(TransactionStatus transactionStatus) {
			boolean newTransaction = transactionStatus.isNewTransaction();

			if (newTransaction) {
				TransactionalPortalCacheHelper.begin();

				TransactionCommitCallbackUtil.pushCallbackList();
			}

			boolean rollback = false;

			try {
				if (newTransaction) {
					LastSessionRecorderUtil.syncLastSessionState();
				}

				return _methodInvocation.proceed();
			}
			catch (Throwable throwable) {
				if (_transactionAttribute.rollbackOn(throwable)) {
					if (newTransaction) {
						TransactionalPortalCacheHelper.rollback();

						TransactionCommitCallbackUtil.popCallbackList();

						EntityCacheUtil.clearLocalCache();
						FinderCacheUtil.clearLocalCache();

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
					TransactionalPortalCacheHelper.commit();

					invokeCallbacks();
				}
			}
		}

		private MethodInvocation _methodInvocation;
		private TransactionAttribute _transactionAttribute;

	}

}