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

import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SPISynchronousQueueUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testSPISynchronousQueueUtil() throws Exception {

		// Create

		final String spiUUID = "spiUUID";

		SynchronousQueue<SPI> synchronousQueue =
			SPISynchronousQueueUtil.createSynchronousQueue(spiUUID);

		Map<String, SynchronousQueue<SPI>> synchronizerRegistry =
			_getSynchronousQueues();

		Assert.assertSame(synchronousQueue, synchronizerRegistry.get(spiUUID));

		// Notify not exist spiUUID

		try {
			SPISynchronousQueueUtil.notifySynchronousQueue("not exist", null);

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"No SPI synchronous queue with uuid not exist",
				ise.getMessage());
		}

		// Notify exist spiUUID

		final MockSPI mockSPI = new MockSPI();

		Thread notifyThread = new Thread() {

			@Override
			public void run() {
				try {
					SPISynchronousQueueUtil.notifySynchronousQueue(
						spiUUID, mockSPI);
				}
				catch (InterruptedException ie) {
					Assert.fail(ie.getMessage());
				}
			}

		};

		notifyThread.start();

		Assert.assertSame(mockSPI, synchronousQueue.take());

		// Destroy

		synchronousQueue = SPISynchronousQueueUtil.createSynchronousQueue(
			spiUUID);

		Assert.assertSame(synchronousQueue, synchronizerRegistry.get(spiUUID));

		SPISynchronousQueueUtil.destroySynchronousQueue(spiUUID);

		Assert.assertTrue(synchronizerRegistry.isEmpty());
	}

	private static Map<String, SynchronousQueue<SPI>> _getSynchronousQueues()
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			SPISynchronousQueueUtil.class, "_synchronousQueues");

		return (Map<String, SynchronousQueue<SPI>>)field.get(null);
	}

}