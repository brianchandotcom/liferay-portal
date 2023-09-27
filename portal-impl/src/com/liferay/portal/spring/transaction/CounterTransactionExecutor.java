/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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