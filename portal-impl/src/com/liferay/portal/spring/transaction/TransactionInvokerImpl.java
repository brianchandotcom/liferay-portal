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

import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvoker;
import com.liferay.portal.kernel.transaction.TransactionStatus;

import java.util.concurrent.Callable;

import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author Shuyang Zhou
 */
public class TransactionInvokerImpl implements TransactionInvoker {

	@Override
	public void commit(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		TransactionHandlerUtil.commit(
			TransactionAttributeBuilder.build(
				true, transactionAttribute.getIsolation(),
				transactionAttribute.getPropagation(),
				transactionAttribute.isReadOnly(),
				transactionAttribute.getTimeout(),
				transactionAttribute.getRollbackForClasses(),
				transactionAttribute.getRollbackForClassNames(),
				transactionAttribute.getNoRollbackForClasses(),
				transactionAttribute.getNoRollbackForClassNames()),
			toTransactionStatus(transactionStatus));
	}

	@Override
	public <T> T invoke(
			TransactionAttribute transactionAttribute, Callable<T> callable)
		throws Throwable {

		return TransactionHandlerUtil.invoke(
			TransactionAttributeBuilder.build(
				true, transactionAttribute.getIsolation(),
				transactionAttribute.getPropagation(),
				transactionAttribute.isReadOnly(),
				transactionAttribute.getTimeout(),
				transactionAttribute.getRollbackForClasses(),
				transactionAttribute.getRollbackForClassNames(),
				transactionAttribute.getNoRollbackForClasses(),
				transactionAttribute.getNoRollbackForClassNames()),
			callable);
	}

	@Override
	public void rollback(
			Throwable throwable, TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus)
		throws Throwable {

		TransactionHandlerUtil.rollback(
			throwable,
			TransactionAttributeBuilder.build(
				true, transactionAttribute.getIsolation(),
				transactionAttribute.getPropagation(),
				transactionAttribute.isReadOnly(),
				transactionAttribute.getTimeout(),
				transactionAttribute.getRollbackForClasses(),
				transactionAttribute.getRollbackForClassNames(),
				transactionAttribute.getNoRollbackForClasses(),
				transactionAttribute.getNoRollbackForClassNames()),
			toTransactionStatus(transactionStatus));
	}

	@Override
	public TransactionStatus start(TransactionAttribute transactionAttribute) {
		org.springframework.transaction.TransactionStatus transactionStatus =
			TransactionHandlerUtil.start(
				TransactionAttributeBuilder.build(
					true, transactionAttribute.getIsolation(),
					transactionAttribute.getPropagation(),
					transactionAttribute.isReadOnly(),
					transactionAttribute.getTimeout(),
					transactionAttribute.getRollbackForClasses(),
					transactionAttribute.getRollbackForClassNames(),
					transactionAttribute.getNoRollbackForClasses(),
					transactionAttribute.getNoRollbackForClassNames()));

		return new TransactionStatus(
			transactionStatus.isNewTransaction(),
			transactionStatus.isRollbackOnly(),
			transactionStatus.isCompleted());
	}

	protected static org.springframework.transaction.TransactionStatus
		toTransactionStatus(TransactionStatus transactionStatus) {

		DefaultTransactionStatus defaultTransactionStatus =
			new DefaultTransactionStatus(
				null, transactionStatus.isNewTransaction(), false, false, false,
				null);

		if (transactionStatus.isCompleted()) {
			defaultTransactionStatus.setCompleted();
		}

		if (transactionStatus.isRollbackOnly()) {
			defaultTransactionStatus.setRollbackOnly();
		}

		return defaultTransactionStatus;
	}

}