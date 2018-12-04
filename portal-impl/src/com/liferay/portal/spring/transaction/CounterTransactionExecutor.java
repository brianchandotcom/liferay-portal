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

/**
 * @author Shuyang Zhou
 */
public class CounterTransactionExecutor
	implements TransactionExecutor, TransactionHandler {

	public CounterTransactionExecutor(
		PlatformTransactionManager platformTransactionManager) {

		_platformTransactionManager = platformTransactionManager;
	}

	@Override
	public void commit(
		TransactionAttributeAdapter transactionAttributeAdapter,
		TransactionStatusAdapter transactionStatusAdapter) {

		_commit(_platformTransactionManager, transactionStatusAdapter);
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

	@Override
	public void rollback(
			Throwable throwable,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter)
		throws Throwable {

		throw _rollback(
			_platformTransactionManager, throwable, transactionAttributeAdapter,
			transactionStatusAdapter);
	}

	@Override
	public TransactionStatusAdapter start(
		TransactionAttributeAdapter transactionAttributeAdapter) {

		return _start(_platformTransactionManager, transactionAttributeAdapter);
	}

	private void _commit(
		PlatformTransactionManager platformTransactionManager,
		TransactionStatusAdapter transactionStatusAdapter) {

		platformTransactionManager.commit(
			transactionStatusAdapter.getTransactionStatus());
	}

	private Object _execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttributeAdapter transactionAttributeAdapter,
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
		throws Throwable {

		TransactionStatusAdapter transactionStatusAdapter = _start(
			platformTransactionManager, transactionAttributeAdapter);

		Object returnValue = null;

		try {
			returnValue = serviceBeanMethodInvocation.proceed();
		}
		catch (Throwable throwable) {
			throw _rollback(
				platformTransactionManager, throwable,
				transactionAttributeAdapter, transactionStatusAdapter);
		}

		_commit(platformTransactionManager, transactionStatusAdapter);

		return returnValue;
	}

	private Throwable _rollback(
			PlatformTransactionManager platformTransactionManager,
			Throwable throwable,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter)
		throws Throwable {

		if (transactionAttributeAdapter.rollbackOn(throwable)) {
			try {
				platformTransactionManager.rollback(
					transactionStatusAdapter.getTransactionStatus());
			}
			catch (Throwable t) {
				t.addSuppressed(throwable);

				throw t;
			}
		}
		else {
			try {
				platformTransactionManager.commit(
					transactionStatusAdapter.getTransactionStatus());
			}
			catch (Throwable t) {
				t.addSuppressed(throwable);

				throw t;
			}
		}

		return throwable;
	}

	private TransactionStatusAdapter _start(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdapter transactionAttributeAdapter) {

		return new TransactionStatusAdapter(
			platformTransactionManager.getTransaction(
				transactionAttributeAdapter));
	}

	private final PlatformTransactionManager _platformTransactionManager;

}