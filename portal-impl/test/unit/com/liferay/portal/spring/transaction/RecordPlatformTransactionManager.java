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

import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.junit.Assert;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Shuyang Zhou
 */
public class RecordPlatformTransactionManager
	implements PlatformTransactionManager {

	public static final TransactionStatus TRANSACTION_STATUS =
		(TransactionStatus)ProxyUtil.newProxyInstance(
			TransactionStatus.class.getClassLoader(),
			new Class<?>[] {TransactionStatus.class},
			new InvocationHandler() {

				@Override
				public Object invoke(
					Object proxy, Method method, Object[] args) {

					throw new UnsupportedOperationException(method.toString());
				}

			});

	@Override
	public void commit(TransactionStatus transactionStatus) {
		_commitTransactionStatus = transactionStatus;
	}

	@Override
	public TransactionStatus getTransaction(
		TransactionDefinition transactionDefinition) {

		_transactionDefinition = transactionDefinition;

		return TRANSACTION_STATUS;
	}

	@Override
	public void rollback(TransactionStatus transactionStatus) {
		_rollbackTransactionStatus = transactionStatus;
	}

	public void setCommitTransactionStatus(
		TransactionStatus commitTransactionStatus) {

		_commitTransactionStatus = commitTransactionStatus;
	}

	public void setRollbackTransactionStatus(
		TransactionStatus rollbackTransactionStatus) {

		_rollbackTransactionStatus = rollbackTransactionStatus;
	}

	public void verify(
		TransactionDefinition transactionDefinition,
		TransactionStatus commitTransactionStatus,
		TransactionStatus rollbackTransactionStatus) {

		Assert.assertSame(transactionDefinition, _transactionDefinition);
		Assert.assertSame(commitTransactionStatus, _commitTransactionStatus);
		Assert.assertSame(
			rollbackTransactionStatus, _rollbackTransactionStatus);
	}

	private TransactionStatus _commitTransactionStatus;
	private TransactionStatus _rollbackTransactionStatus;
	private TransactionDefinition _transactionDefinition;

}