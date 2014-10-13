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

package com.liferay.portal.kernel.resiliency.spi.provider;

import com.liferay.portal.kernel.resiliency.spi.SPI;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * @author Shuyang Zhou
 */
public class SPISynchronousQueueUtil {

	public static SynchronousQueue<SPI> createSynchronousQueue(String spiUUID) {
		SynchronousQueue<SPI> synchronousQueue = new SynchronousQueue<SPI>();

		_synchronousQueues.put(spiUUID, synchronousQueue);

		return synchronousQueue;
	}

	public static void destroySynchronousQueue(String spiUUID) {
		_synchronousQueues.remove(spiUUID);
	}

	public static void notifySynchronousQueue(String spiUUID, SPI spi)
		throws InterruptedException {

		SynchronousQueue<SPI> synchronousQueue = _synchronousQueues.remove(
			spiUUID);

		if (synchronousQueue == null) {
			throw new IllegalStateException(
				"No SPI synchronous queue with uuid " + spiUUID);
		}

		synchronousQueue.put(spi);
	}

	private static final Map<String, SynchronousQueue<SPI>> _synchronousQueues =
		new ConcurrentHashMap<String, SynchronousQueue<SPI>>();

}