/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					transactionConfig.getNoRollbackForClassNames()),
				transactionConfig.isStrictReadOnly()),
			callable::call);
	}

	public void setTransactionExecutor(
		TransactionExecutor transactionExecutor) {

		_transactionExecutor = transactionExecutor;
	}

	private static TransactionExecutor _transactionExecutor;

}