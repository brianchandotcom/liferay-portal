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
				_instance._transactionLifecycleListeners) {

			transactionLifecycleListener.committed(
				transactionAttribute, transactionStatus);
		}
	}

	public static void fireTransactionCreatedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_instance._transactionLifecycleListeners) {

			transactionLifecycleListener.created(
				transactionAttribute, transactionStatus);
		}
	}

	public static void fireTransactionRollbackedEvent(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				_instance._transactionLifecycleListeners) {

			transactionLifecycleListener.rollbacked(
				transactionAttribute, transactionStatus, throwable);
		}
	}

	public static Set<TransactionLifecycleListener>
		getRegisteredTransactionLifecycleListeners() {

		return new LinkedHashSet<>(_instance._transactionLifecycleListeners);
	}

	public static boolean register(
		TransactionLifecycleListener transactionLifecycleListener) {

		return _instance._transactionLifecycleListeners.add(
			transactionLifecycleListener);
	}

	public static boolean unregister(
		TransactionLifecycleListener transactionLifecycleListener) {

		return _instance._transactionLifecycleListeners.remove(
			transactionLifecycleListener);
	}

	private TransactionLifecycleManager() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TransactionLifecycleListener.class,
			new TransactionLifecycleListenerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final TransactionLifecycleManager _instance =
		new TransactionLifecycleManager();

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