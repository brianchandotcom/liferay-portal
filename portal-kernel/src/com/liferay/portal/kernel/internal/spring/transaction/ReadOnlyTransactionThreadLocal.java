/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

				Deque<Boolean> strictReadOnlyDeque = _strictReadOnlyDeque.get();

				strictReadOnlyDeque.pop();

				Deque<Boolean> readOnlyDeque = _readOnlyDeque.get();

				readOnlyDeque.pop();
			}

			@Override
			protected void doCreated(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				Deque<Boolean> strictReadOnlyDeque = _strictReadOnlyDeque.get();

				if (transactionAttribute.isStrictReadOnly()) {
					if (!transactionAttribute.isReadOnly()) {
						throw new IllegalStateException(
							"Strict read only transaction is not writable");
					}

					strictReadOnlyDeque.push(Boolean.TRUE);
				}
				else if (strictReadOnlyDeque.peek() == Boolean.TRUE) {
					if (!transactionAttribute.isReadOnly()) {
						throw new IllegalStateException(
							"Nested strict read only transaction is not " +
								"writable");
					}

					strictReadOnlyDeque.push(Boolean.TRUE);
				}
				else {
					strictReadOnlyDeque.push(Boolean.FALSE);
				}

				Deque<Boolean> readOnlyDeque = _readOnlyDeque.get();

				readOnlyDeque.push(transactionAttribute.isReadOnly());
			}

			@Override
			protected void doRollbacked(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus, Throwable throwable) {

				Deque<Boolean> strictReadOnlyDeque = _strictReadOnlyDeque.get();

				strictReadOnlyDeque.pop();

				Deque<Boolean> readOnlyDeque = _readOnlyDeque.get();

				readOnlyDeque.pop();
			}

		};

	public static boolean isReadOnly() {
		Deque<Boolean> deque = _readOnlyDeque.get();

		Boolean readOnly = deque.peek();

		if (readOnly == null) {
			return false;
		}

		return readOnly;
	}

	private static final ThreadLocal<Deque<Boolean>> _readOnlyDeque =
		new CentralizedThreadLocal<>(
			ReadOnlyTransactionThreadLocal.class + "._readOnlyDeque",
			ArrayDeque::new, false);
	private static final ThreadLocal<Deque<Boolean>> _strictReadOnlyDeque =
		new CentralizedThreadLocal<>(
			ReadOnlyTransactionThreadLocal.class + "._strictReadOnlyDeque",
			ArrayDeque::new, false);

}