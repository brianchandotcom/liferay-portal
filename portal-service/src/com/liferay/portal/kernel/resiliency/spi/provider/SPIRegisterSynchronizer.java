/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
public class SPIRegisterSynchronizer {

	public static SynchronousQueue<SPI> createSynchronizer(String spiUUID) {
		SynchronousQueue<SPI> spiSynchronizer = new SynchronousQueue<SPI>();

		_synchronizerRegistry.put(spiUUID, spiSynchronizer);

		return spiSynchronizer;
	}

	public static void destroySynchronizer(String spiUUID) {
		_synchronizerRegistry.remove(spiUUID);
	}

	public static void notifySynchronizer(String spiUUID, SPI spi)
		throws InterruptedException {

		SynchronousQueue<SPI> spiSynchronizer = _synchronizerRegistry.remove(
			spiUUID);

		if (spiSynchronizer == null) {
			throw new IllegalStateException(
				"No such SPI synchronizer with uuid : " + spiUUID);
		}

		spiSynchronizer.put(spi);
	}

	private static final Map<String, SynchronousQueue<SPI>>
		_synchronizerRegistry =
			new ConcurrentHashMap<String, SynchronousQueue<SPI>>();

}