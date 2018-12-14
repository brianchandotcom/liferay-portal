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

import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvoker;

import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class TransactionInvokerImpl implements TransactionInvoker {

	@Override
	public <T> T invoke(
			TransactionConfig transactionConfig, Callable<T> callable)
		throws Throwable {

		TransactionExecutor transactionExecutor =
			TransactionExecutorThreadLocal.getCurrentTransactionExecutor();

		if (transactionExecutor == null) {
			transactionExecutor = _transactionExecutor;
		}

		return transactionExecutor.execute(
			new TransactionAttributeAdapter(
				TransactionAttributeBuilder.build(
					true, transactionConfig.getIsolation(),
					transactionConfig.getPropagation(),
					transactionConfig.isReadOnly(),
					transactionConfig.getTimeout(),
					transactionConfig.getRollbackForClasses(),
					transactionConfig.getRollbackForClassNames(),
					transactionConfig.getNoRollbackForClasses(),
					transactionConfig.getNoRollbackForClassNames())),
			callable::call);
	}

	public void setTransactionExecutor(
		TransactionExecutor transactionExecutor) {

		_transactionExecutor = transactionExecutor;
	}

	private static TransactionExecutor _transactionExecutor;

}