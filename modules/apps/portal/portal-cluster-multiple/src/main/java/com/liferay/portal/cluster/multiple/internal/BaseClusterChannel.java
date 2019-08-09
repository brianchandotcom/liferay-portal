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

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author Tina Tian
 */
public abstract class BaseClusterChannel implements ClusterChannel {

	public BaseClusterChannel(ExecutorService executorService) {
		if (executorService == null) {
			throw new NullPointerException("Executor service is null");
		}

		_executorService = executorService;
	}

	public void sendMulticastMessage(Serializable message) {
		if (message == null) {
			throw new IllegalArgumentException(
				"Message sent to cluster is null");
		}

		try {
			_executorService.execute(new SendMessageRunnable(message, null));
		}
		catch (RejectedExecutionException ree) {
			_log.error("Unable to send multicast message " + message, ree);
		}
	}

	public void sendUnicastMessage(Serializable message, Address address) {
		if (message == null) {
			throw new IllegalArgumentException(
				"Message sent to cluster is null");
		}

		if (address == null) {
			throw new IllegalArgumentException("Target address is null");
		}

		try {
			_executorService.execute(new SendMessageRunnable(message, address));
		}
		catch (RejectedExecutionException ree) {
			_log.error(
				StringBundler.concat(
					"Unable to send unitcast message ", message, " to ",
					address),
				ree);
		}
	}

	protected abstract void doSendMessage(
		Serializable message, Address address);

	private static final Log _log = LogFactoryUtil.getLog(
		BaseClusterChannel.class);

	private final ExecutorService _executorService;

	private class SendMessageRunnable implements Runnable {

		@Override
		public void run() {
			doSendMessage(_message, _address);
		}

		private SendMessageRunnable(Serializable message, Address srcAddress) {
			_message = message;
			_address = srcAddress;
		}

		private final Address _address;
		private final Serializable _message;

	}

}