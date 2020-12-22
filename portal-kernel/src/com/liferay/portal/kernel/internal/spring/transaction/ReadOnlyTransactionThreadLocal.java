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

package com.liferay.portal.kernel.internal.spring.transaction;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.transaction.NewTransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Preston Crary
 */
public class ReadOnlyTransactionThreadLocal {

	public static final TransactionLifecycleListener
		TRANSACTION_LIFECYCLE_LISTENER = new NewTransactionLifecycleListener() {

			@Override
			protected void doCommitted(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				Deque<Boolean> strictReadOnlyDeque =
					_strictReadOnlyTransactionThreadLocal.get();

				strictReadOnlyDeque.pop();

				Deque<Boolean> readOnlyDeque =
					_readOnlyTransactionThreadLocal.get();

				readOnlyDeque.pop();
			}

			@Override
			protected void doCreated(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				Deque<Boolean> strictReadOnlyDeque =
					_strictReadOnlyTransactionThreadLocal.get();

				if (transactionAttribute.isStrictReadOnly()) {

					// Open strict readonly scope

					if (!transactionAttribute.isReadOnly()) {
						throw new IllegalStateException(
							"Strict-readonly transaction is not readonly");
					}

					strictReadOnlyDeque.push(Boolean.TRUE);
				}
				else if (strictReadOnlyDeque.peek() == Boolean.TRUE) {

					// Under strict readonly scope

					if (!transactionAttribute.isReadOnly()) {
						throw new IllegalStateException(
							"Denied non-readonly nested transaction under " +
								"strict-readonly transaction");
					}

					strictReadOnlyDeque.push(Boolean.TRUE);
				}
				else {

					// Not under strict readonly scope

					strictReadOnlyDeque.push(Boolean.FALSE);
				}

				Deque<Boolean> readOnlyDeque =
					_readOnlyTransactionThreadLocal.get();

				readOnlyDeque.push(transactionAttribute.isReadOnly());
			}

			@Override
			protected void doRollbacked(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus, Throwable throwable) {

				Deque<Boolean> strictReadOnlyDeque =
					_strictReadOnlyTransactionThreadLocal.get();

				strictReadOnlyDeque.pop();

				Deque<Boolean> readOnlyDeque =
					_readOnlyTransactionThreadLocal.get();

				readOnlyDeque.pop();
			}

		};

	public static boolean isReadOnly() {
		Deque<Boolean> deque = _readOnlyTransactionThreadLocal.get();

		Boolean readOnly = deque.peek();

		if (readOnly == null) {
			return false;
		}

		return readOnly;
	}

	private static final ThreadLocal<Deque<Boolean>>
		_readOnlyTransactionThreadLocal = new CentralizedThreadLocal<>(
			ReadOnlyTransactionThreadLocal.class +
				"._readOnlyTransactionThreadLocal",
			ArrayDeque::new, false);
	private static final ThreadLocal<Deque<Boolean>>
		_strictReadOnlyTransactionThreadLocal = new CentralizedThreadLocal<>(
			ReadOnlyTransactionThreadLocal.class +
				"._strictReadOnlyTransactionThreadLocal",
			ArrayDeque::new, false);

}