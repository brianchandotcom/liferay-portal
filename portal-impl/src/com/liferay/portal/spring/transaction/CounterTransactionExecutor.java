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

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
@OSGiBeanProperties(service = CounterTransactionExecutor.class)
public class CounterTransactionExecutor extends BaseTransactionExecutor {

	public CounterTransactionExecutor(
		PlatformTransactionManager platformTransactionManager) {

		_platformTransactionManager = platformTransactionManager;
	}

	@Override
	public void commit(
		TransactionAttributeAdapter transactionAttributeAdapter,
		TransactionStatusAdapter transactionStatusAdapter) {

		_platformTransactionManager.commit(
			transactionStatusAdapter.getTransactionStatus());
	}

	@Override
	public PlatformTransactionManager getPlatformTransactionManager() {
		return _platformTransactionManager;
	}

	@Override
	public void rollback(
			Throwable throwable1,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter)
		throws Throwable {

		if (transactionAttributeAdapter.rollbackOn(throwable1)) {
			try {
				_platformTransactionManager.rollback(
					transactionStatusAdapter.getTransactionStatus());
			}
			catch (Throwable throwable2) {
				throwable2.addSuppressed(throwable1);

				throw throwable2;
			}
		}
		else {
			try {
				_platformTransactionManager.commit(
					transactionStatusAdapter.getTransactionStatus());
			}
			catch (Throwable throwable2) {
				throwable2.addSuppressed(throwable1);

				throw throwable2;
			}
		}

		throw throwable1;
	}

	@Override
	public TransactionStatusAdapter start(
		TransactionAttributeAdapter transactionAttributeAdapter) {

		return new TransactionStatusAdapter(
			_platformTransactionManager.getTransaction(
				transactionAttributeAdapter));
	}

	private final PlatformTransactionManager _platformTransactionManager;

}