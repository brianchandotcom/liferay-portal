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

import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class TransactionInvokerUtil {

	public static void commit(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		_transactionInvoker.commit(transactionAttribute, transactionStatus);
	}

	public static <T> T invoke(
			TransactionAttribute transactionAttribute, Callable<T> callable)
		throws Throwable {

		return _transactionInvoker.invoke(transactionAttribute, callable);
	}

	public static void rollback(
			Throwable throwable, TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus)
		throws Throwable {

		_transactionInvoker.rollback(
			throwable, transactionAttribute, transactionStatus);
	}

	public static TransactionStatus start(
		TransactionAttribute transactionAttribute) {

		return _transactionInvoker.start(transactionAttribute);
	}

	public void setTransactionInvoker(TransactionInvoker transactionInvoker) {
		_transactionInvoker = transactionInvoker;
	}

	private static TransactionInvoker _transactionInvoker;

}