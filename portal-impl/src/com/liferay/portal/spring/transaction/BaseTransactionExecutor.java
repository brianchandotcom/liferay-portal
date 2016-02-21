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

import com.liferay.portal.kernel.transaction.TransactionLifecycleManager;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseTransactionExecutor implements TransactionExecutor {

	protected void fireTransactionCommittedEvent(
		TransactionAttributeAdaptor transactionAttributeAdaptor,
		TransactionStatusAdaptor transactionStatusAdaptor) {

		TransactionLifecycleManager.fireTransactionCommittedEvent(
			transactionAttributeAdaptor, transactionStatusAdaptor);
	}

	protected void fireTransactionCreatedEvent(
		TransactionAttributeAdaptor transactionAttributeAdaptor,
		TransactionStatusAdaptor transactionStatusAdaptor) {

		TransactionLifecycleManager.fireTransactionCreatedEvent(
			transactionAttributeAdaptor, transactionStatusAdaptor);
	}

	protected void fireTransactionRollbackedEvent(
		TransactionAttributeAdaptor transactionAttributeAdaptor,
		TransactionStatusAdaptor transactionStatusAdaptor,
		Throwable throwable) {

		TransactionLifecycleManager.fireTransactionRollbackedEvent(
			transactionAttributeAdaptor, transactionStatusAdaptor, throwable);
	}

}