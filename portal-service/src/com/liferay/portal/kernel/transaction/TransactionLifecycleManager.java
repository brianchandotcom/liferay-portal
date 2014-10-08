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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Shuyang Zhou
 */
public class TransactionLifecycleManager {

	public static void fireTransactionCommittedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListeners) {

			transactionLifecycleListener.committed(
				transactionAttribute, transactionStatus);
		}
	}

	public static void fireTransactionCreatedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListeners) {

			transactionLifecycleListener.created(
				transactionAttribute, transactionStatus);
		}
	}

	public static void fireTransactionRollbackedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListeners) {

			transactionLifecycleListener.rollbacked(
				transactionAttribute, transactionStatus, throwable);
		}
	}

	public static Set<TransactionLifecycleListener>
		getRegisteredTransactionLifecycleListeners() {

		return new LinkedHashSet<TransactionLifecycleListener>(
			_transactionLifecycleListeners);
	}

	public static boolean register(
		TransactionLifecycleListener transactionLifecycleListener) {

		return _transactionLifecycleListeners.add(transactionLifecycleListener);
	}

	public static boolean unregister(
		TransactionLifecycleListener transactionLifecycleListener) {

		return _transactionLifecycleListeners.remove(
			transactionLifecycleListener);
	}

	private static final Set<TransactionLifecycleListener>
		_transactionLifecycleListeners =
			new CopyOnWriteArraySet<TransactionLifecycleListener>();

}