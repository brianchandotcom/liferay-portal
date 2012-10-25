/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SPIRegisterSynchronizerTest {

	@Test
	public void testSPIRegisterSynchronizer() throws Exception {
		Map<String, SynchronousQueue<SPI>> synchronizerRegistry =
			_getSynchronizerRegistry();

		final String testUUID = "testUUID";

		// 1) Create

		SynchronousQueue<SPI> synchronousQueue =
			SPIRegisterSynchronizer.createSynchronizer(testUUID);

		Assert.assertSame(synchronizerRegistry.get(testUUID), synchronousQueue);

		// 2) Notify a non-exist spiUUID

		try {
			SPIRegisterSynchronizer.notifySynchronizer("not exist", null);

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"No such SPI Synchronizer with uuid : not exist",
				ise.getMessage());
		}

		// 3) Notify an exist spiUUID

		final MockSPI mockSPI = new MockSPI();

		new Thread() {

			public void run() {
				try {
					SPIRegisterSynchronizer.notifySynchronizer(
						testUUID, mockSPI);
				}
				catch (InterruptedException ie) {
					Assert.fail(ie.getMessage());
				}
			}

		}.start();

		Assert.assertSame(mockSPI, synchronousQueue.take());

		// 4) Destroy

		synchronousQueue = SPIRegisterSynchronizer.createSynchronizer(testUUID);

		Assert.assertSame(synchronizerRegistry.get(testUUID), synchronousQueue);

		SPIRegisterSynchronizer.destroySynchronizer(testUUID);

		Assert.assertTrue(synchronizerRegistry.isEmpty());
	}

	private static Map<String, SynchronousQueue<SPI>>
		_getSynchronizerRegistry() throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			SPIRegisterSynchronizer.class, "_synchronizerRegistry");

		return (Map<String, SynchronousQueue<SPI>>)field.get(null);
	}

}