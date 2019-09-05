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

import com.liferay.petra.function.UnsafeSupplier;

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
	public <T> T execute(
			TransactionAttributeAdapter transactionAttributeAdapter,
			UnsafeSupplier<T, Throwable> unsafeSupplier)
		throws Throwable {

		TransactionStatusAdapter transactionStatusAdapter = start(
			transactionAttributeAdapter);

		T returnValue = null;

		try {
			returnValue = unsafeSupplier.get();
		}
		catch (Throwable throwable) {
			rollback(
				throwable, transactionAttributeAdapter,
				transactionStatusAdapter);
		}

		_commit(_platformTransactionManager, transactionStatusAdapter);

		return returnValue;
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

		if (transactionAttributeAdapter.rollbackOn(throwable)) {
			try {
				_platformTransactionManager.rollback(
					transactionStatusAdapter.getTransactionStatus());
			}
			catch (Throwable t) {
				t.addSuppressed(throwable);

				throw t;
			}
		}
		else {
			try {
				_platformTransactionManager.commit(
					transactionStatusAdapter.getTransactionStatus());
			}
			catch (Throwable t) {
				t.addSuppressed(throwable);

				throw t;
			}
		}

		throw throwable;
	}

	@Override
	public TransactionStatusAdapter start(
		TransactionAttributeAdapter transactionAttributeAdapter) {

		return new TransactionStatusAdapter(
			_platformTransactionManager.getTransaction(
				transactionAttributeAdapter));
	}

	private void _commit(
		PlatformTransactionManager platformTransactionManager,
		TransactionStatusAdapter transactionStatusAdapter) {

		platformTransactionManager.commit(
			transactionStatusAdapter.getTransactionStatus());
	}

	private final PlatformTransactionManager _platformTransactionManager;

}