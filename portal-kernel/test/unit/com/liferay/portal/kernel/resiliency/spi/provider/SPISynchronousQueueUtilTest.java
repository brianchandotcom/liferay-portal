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

import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.SyncThrowableThread;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SPISynchronousQueueUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new SPISynchronousQueueUtil();
	}

	@Test
	public void testSPISynchronousQueueUtil() throws InterruptedException {

		// Create

		final String spiUUID = "spiUUID";

		SynchronousQueue<SPI> synchronousQueue =
			SPISynchronousQueueUtil.createSynchronousQueue(spiUUID);

		Map<String, SynchronousQueue<SPI>> synchronizerRegistry =
			ReflectionTestUtil.getFieldValue(
				SPISynchronousQueueUtil.class, "_synchronousQueues");

		Assert.assertSame(synchronousQueue, synchronizerRegistry.get(spiUUID));

		// Notify nonexistent

		try {
			SPISynchronousQueueUtil.notifySynchronousQueue("nonexistent", null);

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"No SPI synchronous queue with uuid nonexistent",
				ise.getMessage());
		}

		// Notify existent

		final MockSPI mockSPI = new MockSPI();

		SyncThrowableThread<Void> syncThrowableThread =
			new SyncThrowableThread<>(
				new Callable<Void>() {

					@Override
					public Void call() throws InterruptedException {
						SPISynchronousQueueUtil.notifySynchronousQueue(
							spiUUID, mockSPI);

						return null;
					}

				});

		syncThrowableThread.start();

		Assert.assertSame(mockSPI, synchronousQueue.take());

		syncThrowableThread.sync();

		// Destroy

		synchronousQueue = SPISynchronousQueueUtil.createSynchronousQueue(
			spiUUID);

		Assert.assertSame(synchronousQueue, synchronizerRegistry.get(spiUUID));

		SPISynchronousQueueUtil.destroySynchronousQueue(spiUUID);

		Assert.assertTrue(
			synchronizerRegistry.toString(), synchronizerRegistry.isEmpty());
	}

}