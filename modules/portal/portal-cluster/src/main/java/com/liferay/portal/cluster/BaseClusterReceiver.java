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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author Tina Tian
 */
public abstract class BaseClusterReceiver implements ClusterReceiver {

	public BaseClusterReceiver(ExecutorService executorService) {
		if (executorService == null) {
			throw new NullPointerException("Executor service is null");
		}

		_executorService = executorService;

		boolean hasDoViewAccepted = false;

		Class<?> clazz = getClass();

		try {
			clazz.getDeclaredMethod(
				"doAddressesUpdated", List.class, List.class);

			hasDoViewAccepted = true;
		}
		catch (ReflectiveOperationException roe) {
		}

		_hasDoViewAccepted = hasDoViewAccepted;

		boolean hasDoCoordinatorUpdated = false;

		try {
			clazz.getDeclaredMethod(
				"doCoordinatorUpdated", Address.class, Address.class);

			hasDoCoordinatorUpdated = true;
		}
		catch (ReflectiveOperationException roe) {
		}

		_hasDoCoordinatorUpdated = hasDoCoordinatorUpdated;
	}

	@Override
	public void addressesUpdated(List<Address> addresses) {
		if (_addresses == null) {
			_addresses = addresses;

			return;
		}

		List<Address> oldAddresses = null;

		try {
			_countDownLatch.await();

			oldAddresses = _addresses;

			_addresses = addresses;

			if (_hasDoViewAccepted) {
				_executorService.execute(
					new AddressesUpdatedRunnable(oldAddresses, addresses));
			}
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}
		catch (RejectedExecutionException ree) {
			_log.error(
				"Unable to handle view update from " + oldAddresses + " to " +
					addresses,
				ree);
		}
	}

	@Override
	public void coordinatorUpdated(Address coordinator) {
		if (_coordinator == null) {
			_coordinator = coordinator;

			return;
		}

		Address oldCoordinator = null;

		try {
			_countDownLatch.await();

			oldCoordinator = _coordinator;

			_coordinator = coordinator;

			if (_hasDoCoordinatorUpdated) {
				_executorService.execute(
					new CoordinatorUpdatedRunnable(
						oldCoordinator, coordinator));
			}
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}
		catch (RejectedExecutionException ree) {
			_log.error(
				"Unable to handle coordinator update from " + oldCoordinator +
					" to " + coordinator,
				ree);
		}
	}

	@Override
	public List<Address> getAddresses() {
		return Collections.unmodifiableList(_addresses);
	}

	@Override
	public Address getCoordinator() {
		return _coordinator;
	}

	@Override
	public void openLatch() {
		_countDownLatch.countDown();
	}

	@Override
	public void receive(Object messagePayload, Address srcAddress) {
		try {
			_countDownLatch.await();

			_executorService.execute(
				new ReceiveRunnable(messagePayload, srcAddress));
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}
		catch (RejectedExecutionException ree) {
			_log.error(
				"Unable to handle received message " + messagePayload, ree);
		}
	}

	protected void doAddressesUpdated(
		List<Address> oldAddresses, List<Address> newAddresses) {
	}

	protected void doCoordinatorUpdated(
		Address oldCoordinator, Address newCoordinator) {
	}

	protected abstract void doReceive(
		Object messagePayload, Address srcAddress);

	private static final Log _log = LogFactoryUtil.getLog(
		BaseClusterReceiver.class);

	private volatile List<Address> _addresses;
	private volatile Address _coordinator;
	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private final ExecutorService _executorService;
	private final boolean _hasDoCoordinatorUpdated;
	private final boolean _hasDoViewAccepted;

	private class AddressesUpdatedRunnable implements Runnable {

		@Override
		public void run() {
			doAddressesUpdated(_oldAddresses, _newAddresses);
		}

		private AddressesUpdatedRunnable(
			List<Address> oldAddresses, List<Address> newAddresses) {

			_oldAddresses = oldAddresses;
			_newAddresses = newAddresses;
		}

		private final List<Address> _newAddresses;
		private final List<Address> _oldAddresses;

	}

	private class CoordinatorUpdatedRunnable implements Runnable {

		@Override
		public void run() {
			doCoordinatorUpdated(_oldCoordinator, _newCoordinator);
		}

		private CoordinatorUpdatedRunnable(
			Address oldCoordinator, Address newCoordinator) {

			_oldCoordinator = oldCoordinator;
			_newCoordinator = newCoordinator;
		}

		private final Address _newCoordinator;
		private final Address _oldCoordinator;

	}

	private class ReceiveRunnable implements Runnable {

		@Override
		public void run() {
			doReceive(_messagePayload, _srcAddress);
		}

		private ReceiveRunnable(Object messagePayload, Address srcAddress) {
			_messagePayload = messagePayload;
			_srcAddress = srcAddress;
		}

		private final Object _messagePayload;
		private final Address _srcAddress;

	}

}