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
			catch (Throwable t) {
				transactionStatus.suppressLifecycleListenerThrowable(t);
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
			catch (Throwable t) {
				transactionStatus.suppressLifecycleListenerThrowable(t);
			}
		}
	}

	public static void fireTransactionRollbackedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListenersReference.get()) {

			try {
				transactionLifecycleListener.rollbacked(
					transactionAttribute, transactionStatus, throwable);
			}
			catch (Throwable t) {
				transactionStatus.suppressLifecycleListenerThrowable(t);
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