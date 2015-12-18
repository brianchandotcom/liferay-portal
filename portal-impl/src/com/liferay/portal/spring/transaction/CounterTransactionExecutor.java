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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 */
public class CounterTransactionExecutor
	implements TransactionExecutor, TransactionHandler {

	@Override
	public void commit(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		try {
			platformTransactionManager.commit(transactionStatus);
		}
		catch (RuntimeException re) {
			_log.error(
				"Application exception overridden by commit exception", re);

			throw re;
		}
		catch (Error e) {
			_log.error("Application exception overridden by commit error", e);

			throw e;
		}
	}

	@Override
	public Object execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttribute transactionAttribute,
			MethodInvocation methodInvocation)
		throws Throwable {

		TransactionStatus transactionStatus = start(
			platformTransactionManager, transactionAttribute);

		Object returnValue = null;

		try {
			returnValue = methodInvocation.proceed();
		}
		catch (Throwable throwable) {
			rollback(
				platformTransactionManager, throwable, transactionAttribute,
				transactionStatus);
		}

		commit(
			platformTransactionManager, transactionAttribute,
			transactionStatus);

		return returnValue;
	}

	@Override
	public void rollback(
			PlatformTransactionManager platformTransactionManager,
			Throwable throwable, TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus)
		throws Throwable {

		if (transactionAttribute.rollbackOn(throwable)) {
			try {
				platformTransactionManager.rollback(transactionStatus);
			}
			catch (RuntimeException re) {
				re.addSuppressed(throwable);

				_log.error(
					"Application exception overridden by rollback exception",
					re);

				throw re;
			}
			catch (Error e) {
				e.addSuppressed(throwable);

				_log.error(
					"Application exception overridden by rollback error", e);

				throw e;
			}
		}
		else {
			commit(
				platformTransactionManager, transactionAttribute,
				transactionStatus);
		}

		throw throwable;
	}

	@Override
	public TransactionStatus start(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttribute transactionAttribute) {

		return platformTransactionManager.getTransaction(transactionAttribute);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CounterTransactionExecutor.class);

}