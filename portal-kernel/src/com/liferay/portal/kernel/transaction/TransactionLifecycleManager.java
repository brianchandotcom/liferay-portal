/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.transaction;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Shuyang Zhou
 */
public class TransactionLifecycleManager {

	public static void fireTransactionCommittedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListenersReference.get()) {

			try {
				transactionLifecycleListener.committed(
					transactionAttribute, transactionStatus);
			}
			catch (Throwable throwable) {
				transactionStatus.suppressLifecycleListenerThrowable(throwable);
			}
		}
	}

	public static void fireTransactionCreatedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListenersReference.get()) {

			try {
				transactionLifecycleListener.created(
					transactionAttribute, transactionStatus);
			}
			catch (Throwable throwable) {
				transactionStatus.suppressLifecycleListenerThrowable(throwable);
			}
		}
	}

	public static void fireTransactionRollbackedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable1) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListenersReference.get()) {

			try {
				transactionLifecycleListener.rollbacked(
					transactionAttribute, transactionStatus, throwable1);
			}
			catch (Throwable throwable2) {
				transactionStatus.suppressLifecycleListenerThrowable(
					throwable2);
			}
		}
	}

	public static TransactionLifecycleListener[]
		getRegisteredTransactionLifecycleListeners() {

		TransactionLifecycleListener[] transactionLifecycleListeners =
			_transactionLifecycleListenersReference.get();

		return transactionLifecycleListeners.clone();
	}

	public static boolean register(
		TransactionLifecycleListener transactionLifecycleListener) {

		while (true) {
			TransactionLifecycleListener[] transactionLifecycleListeners =
				_transactionLifecycleListenersReference.get();

			if (ArrayUtil.contains(
					transactionLifecycleListeners,
					transactionLifecycleListener)) {

				return false;
			}

			TransactionLifecycleListener[] newTransactionLifecycleListeners =
				ArrayUtil.append(
					transactionLifecycleListeners,
					transactionLifecycleListener);

			if (_transactionLifecycleListenersReference.compareAndSet(
					transactionLifecycleListeners,
					newTransactionLifecycleListeners)) {

				return true;
			}
		}
	}

	public static boolean unregister(
		TransactionLifecycleListener transactionLifecycleListener) {

		while (true) {
			TransactionLifecycleListener[] transactionLifecycleListeners =
				_transactionLifecycleListenersReference.get();

			TransactionLifecycleListener[] newTransactionLifecycleListeners =
				ArrayUtil.remove(
					transactionLifecycleListeners,
					transactionLifecycleListener);

			if (transactionLifecycleListeners ==
					newTransactionLifecycleListeners) {

				return false;
			}

			if (_transactionLifecycleListenersReference.compareAndSet(
					transactionLifecycleListeners,
					newTransactionLifecycleListeners)) {

				return true;
			}
		}
	}

	private static final AtomicReference<TransactionLifecycleListener[]>
		_transactionLifecycleListenersReference = new AtomicReference<>(
			new TransactionLifecycleListener[0]);

}