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

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class DefaultTransactionExecutor
	extends BaseTransactionExecutor implements TransactionHandler {

	@Override
	public void commit(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdaptor transactionAttributeAdaptor,
		TransactionStatusAdaptor transactionStatusAdaptor) {

		Throwable throwable = null;

		try {
			platformTransactionManager.commit(
				transactionStatusAdaptor.getTransactionStatus());
		}
		catch (RuntimeException re) {
			_log.error(
				"Application exception overridden by commit exception", re);

			throwable = re;

			throw re;
		}
		catch (Error e) {
			_log.error("Application exception overridden by commit error", e);

			throwable = e;

			throw e;
		}
		finally {
			if (throwable != null) {
				fireTransactionRollbackedEvent(
					transactionAttributeAdaptor, transactionStatusAdaptor,
					throwable);
			}
			else {
				fireTransactionCommittedEvent(
					transactionAttributeAdaptor, transactionStatusAdaptor);
			}
		}
	}

	@Override
	public Object execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttributeAdaptor transactionAttributeAdaptor,
			MethodInvocation methodInvocation)
		throws Throwable {

		TransactionStatusAdaptor transactionStatusAdaptor = start(
			platformTransactionManager, transactionAttributeAdaptor);

		Object returnValue = null;

		try {
			returnValue = methodInvocation.proceed();
		}
		catch (Throwable throwable) {
			rollback(
				platformTransactionManager, throwable,
				transactionAttributeAdaptor, transactionStatusAdaptor);
		}

		commit(
			platformTransactionManager, transactionAttributeAdaptor,
			transactionStatusAdaptor);

		return returnValue;
	}

	@Override
	public void rollback(
			PlatformTransactionManager platformTransactionManager,
			Throwable throwable,
			TransactionAttributeAdaptor transactionAttributeAdaptor,
			TransactionStatusAdaptor transactionStatusAdaptor)
		throws Throwable {

		if (transactionAttributeAdaptor.rollbackOn(throwable)) {
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
			finally {
				fireTransactionRollbackedEvent(
					transactionAttributeAdaptor, transactionStatusAdaptor,
					throwable);
			}
		}
		else {
			commit(
				platformTransactionManager, transactionAttributeAdaptor,
				transactionStatusAdaptor);
		}

		throw throwable;
	}

	@Override
	public TransactionStatusAdaptor start(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdaptor transactionAttributeAdaptor) {

		TransactionStatusAdaptor transactionStatusAdaptor =
			new TransactionStatusAdaptor(
				platformTransactionManager.getTransaction(
					transactionAttributeAdaptor));

		fireTransactionCreatedEvent(
			transactionAttributeAdaptor, transactionStatusAdaptor);

		return transactionStatusAdaptor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultTransactionExecutor.class);

}