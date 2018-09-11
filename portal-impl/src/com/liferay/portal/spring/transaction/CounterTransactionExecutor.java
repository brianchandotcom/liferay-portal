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

/**
 * @author Shuyang Zhou
 */
public class CounterTransactionExecutor
	implements TransactionExecutor, TransactionHandler {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #CounterTransactionExecutor(PlatformTransactionManager)}
	 */
	@Deprecated
	public CounterTransactionExecutor() {
		_platformTransactionManager = null;
	}

	public CounterTransactionExecutor(
		PlatformTransactionManager platformTransactionManager) {

		_platformTransactionManager = platformTransactionManager;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #commit(
	 *             TransactionAttributeAdapter, TransactionStatusAdapter)}
	 */
	@Deprecated
	@Override
	public void commit(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdapter transactionAttributeAdapter,
		TransactionStatusAdapter transactionStatusAdapter) {

		_commit(platformTransactionManager, transactionStatusAdapter);
	}

	@Override
	public void commit(
		TransactionAttributeAdapter transactionAttributeAdapter,
		TransactionStatusAdapter transactionStatusAdapter) {

		_commit(_platformTransactionManager, transactionStatusAdapter);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #execute(
	 *             TransactionAttributeAdapter, MethodInvocation)}
	 */
	@Deprecated
	@Override
	public Object execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttributeAdapter transactionAttributeAdapter,
			MethodInvocation methodInvocation)
		throws Throwable {

		return _execute(
			platformTransactionManager, transactionAttributeAdapter,
			methodInvocation);
	}

	@Override
	public Object execute(
			TransactionAttributeAdapter transactionAttributeAdapter,
			MethodInvocation methodInvocation)
		throws Throwable {

		return _execute(
			_platformTransactionManager, transactionAttributeAdapter,
			methodInvocation);
	}

	@Override
	public PlatformTransactionManager getPlatformTransactionManager() {
		return _platformTransactionManager;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #rollback(
	 *             Throwable, TransactionAttributeAdapter,
	 *             TransactionStatusAdapter)}
	 */
	@Deprecated
	@Override
	public void rollback(
			PlatformTransactionManager platformTransactionManager,
			Throwable throwable,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter)
		throws Throwable {

		_rollback(
			platformTransactionManager, throwable, transactionAttributeAdapter,
			transactionStatusAdapter);

		throw throwable;
	}

	@Override
	public void rollback(
			Throwable throwable,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter)
		throws Throwable {

		_rollback(
			_platformTransactionManager, throwable, transactionAttributeAdapter,
			transactionStatusAdapter);

		throw throwable;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #start(
	 *             TransactionAttributeAdapter)}
	 */
	@Deprecated
	@Override
	public TransactionStatusAdapter start(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdapter transactionAttributeAdapter) {

		return _start(platformTransactionManager, transactionAttributeAdapter);
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
			MethodInvocation methodInvocation)
		throws Throwable {

		TransactionStatusAdapter transactionStatusAdapter = _start(
			platformTransactionManager, transactionAttributeAdapter);

		Object returnValue = null;

		try {
			returnValue = methodInvocation.proceed();
		}
		catch (Throwable throwable) {
			_rollback(
				platformTransactionManager, throwable,
				transactionAttributeAdapter, transactionStatusAdapter);

			throw throwable;
		}

		_commit(platformTransactionManager, transactionStatusAdapter);

		return returnValue;
	}

	private void _rollback(
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