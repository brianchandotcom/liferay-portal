/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.transaction;

/**
 * @author Shuyang Zhou
 */
public class NewTransactionLifecycleListener
	implements TransactionLifecycleListener {

	@Override
	public void committed(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		if (transactionStatus.isNewTransaction()) {
			doCommitted(transactionAttribute, transactionStatus);
		}
	}

	@Override
	public void created(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		if (transactionStatus.isNewTransaction()) {
			doCreated(transactionAttribute, transactionStatus);
		}
	}

	@Override
	public void rollbacked(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable) {

		if (transactionStatus.isNewTransaction()) {
			doRollbacked(transactionAttribute, transactionStatus, throwable);
		}
	}

	protected void doCommitted(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {
	}

	protected void doCreated(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {
	}

	protected void doRollbacked(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable) {
	}

}