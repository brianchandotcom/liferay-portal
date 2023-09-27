/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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