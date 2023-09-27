/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public void sendMulticastMessage(Serializable message) {
		if (message == null) {
			throw new IllegalArgumentException("Message is null");
		}

		try {
			_executorService.execute(() -> doSendMessage(message, null));
		}
		catch (RejectedExecutionException rejectedExecutionException) {
			_log.error(
				"Unable to send multicast message " + message,
				rejectedExecutionException);
		}
	}

	@Override
	public void sendUnicastMessage(Serializable message, Address address) {
		if (message == null) {
			throw new IllegalArgumentException("Message is null");
		}

		if (address == null) {
			throw new IllegalArgumentException("Address is null");
		}

		try {
			_executorService.execute(() -> doSendMessage(message, address));
		}
		catch (RejectedExecutionException rejectedExecutionException) {
			_log.error(
				StringBundler.concat(
					"Unable to send unitcast message ", message, " to ",
					address),
				rejectedExecutionException);
		}
	}

	protected abstract void doSendMessage(
		Serializable message, Address address);

	private static final Log _log = LogFactoryUtil.getLog(
		BaseClusterChannel.class);

	private final ExecutorService _executorService;

}