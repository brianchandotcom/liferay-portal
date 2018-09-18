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

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Preston Crary
 */
public class TransactionExecutorThreadLocal {

	public static TransactionExecutor getCurrentTransactionExecutor() {
		Deque<TransactionExecutor> transactionExecutors =
			_transactionExecutorThreadLocal.get();

		return transactionExecutors.peek();
	}

	protected static TransactionExecutor popTransactionExecutor() {
		Deque<TransactionExecutor> transactionExecutors =
			_transactionExecutorThreadLocal.get();

		return transactionExecutors.pop();
	}

	protected static void pushTransactionExecutor(
		TransactionExecutor transactionExecutor) {

		Deque<TransactionExecutor> transactionExecutors =
			_transactionExecutorThreadLocal.get();

		transactionExecutors.push(transactionExecutor);
	}

	private TransactionExecutorThreadLocal() {
	}

	private static final ThreadLocal<Deque<TransactionExecutor>>
		_transactionExecutorThreadLocal = new CentralizedThreadLocal<>(
			TransactionExecutorThreadLocal.class +
				"._transactionExecutorThreadLocal",
			ArrayDeque::new, false);

}