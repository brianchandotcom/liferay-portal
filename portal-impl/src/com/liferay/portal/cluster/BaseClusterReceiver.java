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
import com.liferay.portal.kernel.cluster.ClusterReceiver;
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
			clazz.getDeclaredMethod("doViewAccepted", List.class, List.class);

			hasDoViewAccepted = true;
		}
		catch (ReflectiveOperationException roe) {
		}

		_hasDoViewAccepted = hasDoViewAccepted;
	}

	@Override
	public List<Address> getAddresses() {
		return Collections.unmodifiableList(_addresses);
	}

	@Override
	public void openLatch() {
		_countDownLatch.countDown();
	}

	@Override
	public void receive(
		Object messagePayload, Address srcAddress, Address destAddress) {

		try {
			_countDownLatch.await();

			_executorService.execute(
				new MessageCallBackJob(
					messagePayload, srcAddress, destAddress));
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

	@Override
	public void viewAccepted(List<Address> addresses) {
		if (_addresses == null) {
			_addresses = addresses;

			return;
		}

		List<Address> oldAddresses = _addresses;

		try {
			_countDownLatch.await();

			_addresses = addresses;

			if (_hasDoViewAccepted) {
				_executorService.execute(
					new ViewCallBackJob(oldAddresses, addresses));
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

	protected abstract void doReceive(
		Object messagePayload, Address srcAddress, Address destAddress);

	protected void doViewAccepted(
		List<Address> oldAddresses, List<Address> newAddresses) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseClusterReceiver.class);

	private volatile List<Address> _addresses;
	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private final ExecutorService _executorService;
	private final boolean _hasDoViewAccepted;

	private class MessageCallBackJob implements Runnable {

		@Override
		public void run() {
			doReceive(_messagePayload, _srcAddress, _destAddress);
		}

		private MessageCallBackJob(
			Object messagePayload, Address srcAddress, Address destAddress) {

			_messagePayload = messagePayload;
			_srcAddress = srcAddress;
			_destAddress = destAddress;
		}

		private final Address _destAddress;
		private final Object _messagePayload;
		private final Address _srcAddress;

	}

	private class ViewCallBackJob implements Runnable {

		@Override
		public void run() {
			doViewAccepted(_oldAddresses, _newAddresses);
		}

		private ViewCallBackJob(
			List<Address> oldAddresses, List<Address> newAddresses) {

			_oldAddresses = oldAddresses;
			_newAddresses = newAddresses;
		}

		private final List<Address> _newAddresses;
		private final List<Address> _oldAddresses;

	}

}