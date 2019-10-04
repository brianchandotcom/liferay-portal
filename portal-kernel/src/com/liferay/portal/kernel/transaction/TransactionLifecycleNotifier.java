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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

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
				_transactionLifecycleNotifier._transactionLifecycleListeners) {

			transactionLifecycleListener.committed(
				transactionAttribute, transactionStatus);
		}
	}

	protected static void fireTransactionCreatedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleNotifier._transactionLifecycleListeners) {

			transactionLifecycleListener.created(
				transactionAttribute, transactionStatus);
		}
	}

	protected static void fireTransactionRollbackedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_transactionLifecycleNotifier._transactionLifecycleListeners) {

			transactionLifecycleListener.rollbacked(
				transactionAttribute, transactionStatus, throwable);
		}
	}

	private TransactionLifecycleNotifier() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TransactionLifecycleListener.class,
			new TransactionLifecycleListenerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final TransactionLifecycleNotifier
		_transactionLifecycleNotifier = new TransactionLifecycleNotifier();

	private final ServiceTracker
		<TransactionLifecycleListener, TransactionLifecycleListener>
			_serviceTracker;
	private final Set<TransactionLifecycleListener>
		_transactionLifecycleListeners = new CopyOnWriteArraySet<>();

	private class TransactionLifecycleListenerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<TransactionLifecycleListener, TransactionLifecycleListener> {

		@Override
		public TransactionLifecycleListener addingService(
			ServiceReference<TransactionLifecycleListener> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			TransactionLifecycleListener transactionLifecycleListener =
				registry.getService(serviceReference);

			_transactionLifecycleListeners.add(transactionLifecycleListener);

			return transactionLifecycleListener;
		}

		@Override
		public void modifiedService(
			ServiceReference<TransactionLifecycleListener> serviceReference,
			TransactionLifecycleListener transactionLifecycleListener) {
		}

		@Override
		public void removedService(
			ServiceReference<TransactionLifecycleListener> serviceReference,
			TransactionLifecycleListener transactionLifecycleListener) {

			_transactionLifecycleListeners.remove(transactionLifecycleListener);
		}

	}

}