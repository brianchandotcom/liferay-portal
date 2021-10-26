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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

/**
 * @author Michael C. Han
 */
public class TransactionLifecycleNotifier {

	public static final TransactionLifecycleListener
		TRANSACTION_LIFECYCLE_LISTENER = new NewTransactionLifecycleListener() {

			@Override
			protected void doCommitted(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				fireTransactionCommittedEvent(
					transactionAttribute, transactionStatus);
			}

			@Override
			protected void doCreated(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				fireTransactionCreatedEvent(
					transactionAttribute, transactionStatus);
			}

			@Override
			protected void doRollbacked(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus, Throwable throwable) {

				fireTransactionRollbackedEvent(
					transactionAttribute, transactionStatus, throwable);
			}

		};

	protected static void fireTransactionCommittedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListeners) {

			transactionLifecycleListener.committed(
				transactionAttribute, transactionStatus);
		}
	}

	protected static void fireTransactionCreatedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListeners) {

			transactionLifecycleListener.created(
				transactionAttribute, transactionStatus);
		}
	}

	protected static void fireTransactionRollbackedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleListeners) {

			transactionLifecycleListener.rollbacked(
				transactionAttribute, transactionStatus, throwable);
		}
	}

	private TransactionLifecycleNotifier() {
	}

	private static final ServiceTrackerList<TransactionLifecycleListener>
		_transactionLifecycleListeners = ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(),
			TransactionLifecycleListener.class);

}