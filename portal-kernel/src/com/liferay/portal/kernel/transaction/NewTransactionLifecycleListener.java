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