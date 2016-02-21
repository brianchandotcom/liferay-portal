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
		TransactionStatusAdaptor transactionStatusAdaptor) {

		try {
			platformTransactionManager.commit(
				transactionStatusAdaptor.getTransactionStatus());
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

		TransactionStatusAdaptor transactionStatusAdaptor = start(
			platformTransactionManager, transactionAttribute);

		Object returnValue = null;

		try {
			returnValue = methodInvocation.proceed();
		}
		catch (Throwable throwable) {
			rollback(
				platformTransactionManager, throwable, transactionAttribute,
				transactionStatusAdaptor);
		}

		commit(
			platformTransactionManager, transactionAttribute,
			transactionStatusAdaptor);

		return returnValue;
	}

	@Override
	public void rollback(
			PlatformTransactionManager platformTransactionManager,
			Throwable throwable, TransactionAttribute transactionAttribute,
			TransactionStatusAdaptor transactionStatusAdaptor)
		throws Throwable {

		if (transactionAttribute.rollbackOn(throwable)) {
			try {
				platformTransactionManager.rollback(
					transactionStatusAdaptor.getTransactionStatus());
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
				transactionStatusAdaptor);
		}

		throw throwable;
	}

	@Override
	public TransactionStatusAdaptor start(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttribute transactionAttribute) {

		return new TransactionStatusAdaptor(
			platformTransactionManager.getTransaction(transactionAttribute));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CounterTransactionExecutor.class);

}