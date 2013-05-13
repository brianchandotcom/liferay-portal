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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.hibernate.LastSessionRecorderUtil;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class DefaultTransactionExecutor extends BaseTransactionExecutor {

	@Override
	public Object execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttribute transactionAttribute,
			MethodInvocation methodInvocation)
		throws Throwable {

		TransactionStatus transactionStatus =
			platformTransactionManager.getTransaction(transactionAttribute);

		boolean newTransaction = transactionStatus.isNewTransaction();

		if (newTransaction) {
			TransactionalPortalCacheHelper.begin();

			TransactionCommitCallbackUtil.pushCallbackList();
		}

		Object returnValue = null;

		try {
			if (newTransaction) {
				LastSessionRecorderUtil.syncLastSessionState();
			}

			returnValue = methodInvocation.proceed();
		}
		catch (Throwable throwable) {
			processThrowable(
				platformTransactionManager, throwable, transactionAttribute,
				transactionStatus);
		}

		processCommit(platformTransactionManager, transactionStatus);

		return returnValue;
	}

	protected void processCommit(
		PlatformTransactionManager platformTransactionManager,
		TransactionStatus transactionStatus) {

		boolean hasError = false;

		try {
			platformTransactionManager.commit(transactionStatus);
		}
		catch (TransactionSystemException tse) {
			_log.error(
				"Application exception overridden by commit exception", tse);

			hasError = true;

			throw tse;
		}
		catch (RuntimeException re) {
			_log.error(
				"Application exception overridden by commit exception", re);

			hasError = true;

			throw re;
		}
		catch (Error e) {
			_log.error("Application exception overridden by commit error", e);

			hasError = true;

			throw e;
		}
		finally {
			if (transactionStatus.isNewTransaction()) {
				if (hasError) {
					TransactionalPortalCacheHelper.rollback();

					TransactionCommitCallbackUtil.popCallbackList();

					EntityCacheUtil.clearLocalCache();
					FinderCacheUtil.clearLocalCache();
				}
				else {
					TransactionalPortalCacheHelper.commit();

					invokeCallbacks();
				}
			}
		}
	}

	protected void processThrowable(
			PlatformTransactionManager platformTransactionManager,
			Throwable throwable, TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus)
		throws Throwable {

		if (transactionAttribute.rollbackOn(throwable)) {
			try {
				platformTransactionManager.rollback(transactionStatus);
			}
			catch (TransactionSystemException tse) {
				_log.error(
					"Application exception overridden by rollback exception",
					tse);

				throw tse;
			}
			catch (RuntimeException re) {
				_log.error(
					"Application exception overridden by rollback exception",
					re);

				throw re;
			}
			catch (Error e) {
				_log.error(
					"Application exception overridden by rollback error", e);

				throw e;
			}
			finally {
				if (transactionStatus.isNewTransaction()) {
					TransactionalPortalCacheHelper.rollback();

					TransactionCommitCallbackUtil.popCallbackList();

					EntityCacheUtil.clearLocalCache();
					FinderCacheUtil.clearLocalCache();
				}
			}
		}
		else {
			processCommit(platformTransactionManager, transactionStatus);
		}

		throw throwable;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultTransactionExecutor.class);

}