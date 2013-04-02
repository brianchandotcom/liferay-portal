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

package com.liferay.portal.kernel.nio.intraband;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BaseIntraBandTest {

	@Test
	public void testDatagramReceiveHandlerRegister() throws Exception {

		// Length

		AtomicReference<DatagramReceiveHandler[]>
			datagramReceiveHandlersReference =
				_mockIntraBand.datagramReceiveHandlersReference;

		DatagramReceiveHandler[] datagramReceiveHandlers =
			datagramReceiveHandlersReference.get();

		Assert.assertEquals(256, datagramReceiveHandlers.length);

		// Copy

		DatagramReceiveHandler[] newDatagramReceiveHandlers =
			_mockIntraBand.getDatagramReceiveHandlers();

		Assert.assertNotSame(
			datagramReceiveHandlers, newDatagramReceiveHandlers);

		// First register

		DatagramReceiveHandler datagramReceiveHandler1 =
			new RecordDatagramReceiveHandler();

		DatagramReceiveHandler oldDatagramReceiveHandler =
			_mockIntraBand.registerDatagramReceiveHandler(
				_type, datagramReceiveHandler1);

		Assert.assertNull(oldDatagramReceiveHandler);
		Assert.assertSame(
			datagramReceiveHandler1,
			_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		// Second register

		DatagramReceiveHandler datagramReceiveHandler2 =
			new RecordDatagramReceiveHandler();

		oldDatagramReceiveHandler =
			_mockIntraBand.registerDatagramReceiveHandler(
				_type, datagramReceiveHandler2);

		Assert.assertSame(datagramReceiveHandler1, oldDatagramReceiveHandler);
		Assert.assertSame(
			datagramReceiveHandler2,
			_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		// Unregister

		DatagramReceiveHandler removedDatagramReceiveHandler =
			_mockIntraBand.unregisterDatagramReceiveHandler(_type);

		Assert.assertSame(
			datagramReceiveHandler2, removedDatagramReceiveHandler);
		Assert.assertNull(_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		// Concurrent registering

		final int handlerCount = 10240;
		final int threadCount = 10;

		final DatagramReceiveHandler[] standardDatagramReceiveHandlers =
			new DatagramReceiveHandler[handlerCount];

		for (int i = 0; i < handlerCount; i++) {
			standardDatagramReceiveHandlers[i] =
				new RecordDatagramReceiveHandler();
		}

		final Queue<DatagramReceiveHandler> oldDatagramReceiveHandlers =
			new ConcurrentLinkedQueue<DatagramReceiveHandler>();

		class RegisterJob implements Callable<Void> {

			public RegisterJob(int offset) {
				int groupSize = handlerCount / threadCount;

				_start = offset * groupSize;

				if ((_start + groupSize) > handlerCount) {
					_end = handlerCount;
				}
				else {
					_end = _start + groupSize;
				}
			}

			public Void call() {
				for (int i = _start; i < _end; i++) {
					DatagramReceiveHandler oldDatagramReceiveHandler =
						_mockIntraBand.registerDatagramReceiveHandler(
							_type, standardDatagramReceiveHandlers[i]);

					if (oldDatagramReceiveHandler != null) {
						oldDatagramReceiveHandlers.offer(
							oldDatagramReceiveHandler);
					}
				}

				return null;
			}

			private final int _end;
			private final int _start;

		}

		List<RegisterJob> registerJobs = new ArrayList<RegisterJob>(
			threadCount);

		for (int i = 0; i < threadCount; i++) {
			registerJobs.add(new RegisterJob(i));
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			threadCount);

		List<Future<Void>> futures = executorService.invokeAll(registerJobs);

		for (Future<Void> future : futures) {
			future.get();
		}

		executorService.shutdownNow();

		oldDatagramReceiveHandlers.offer(
			_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		Assert.assertEquals(handlerCount, oldDatagramReceiveHandlers.size());

		for (DatagramReceiveHandler datagramReceiveHandler :
				standardDatagramReceiveHandlers) {

			Assert.assertTrue(
				oldDatagramReceiveHandlers.contains(datagramReceiveHandler));
		}

		_mockIntraBand.close();

		// Null after close

		Assert.assertNull(datagramReceiveHandlersReference.get());

		// Get after close

		try {
			_mockIntraBand.getDatagramReceiveHandlers();

			Assert.fail();
		}
		catch (ClosedIntraBandException cibe) {
		}

		// Register after close

		try {
			_mockIntraBand.registerDatagramReceiveHandler(
				_type, new RecordDatagramReceiveHandler());

			Assert.fail();
		}
		catch (ClosedIntraBandException cibe) {
		}

		// Unregister after close

		try {
			_mockIntraBand.unregisterDatagramReceiveHandler(_type);

			Assert.fail();
		}
		catch (ClosedIntraBandException cibe) {
		}
	}

	private static final long _DEFAULT_TIMEOUT = 1000;

	private MockIntraBand _mockIntraBand = new MockIntraBand(_DEFAULT_TIMEOUT);
	private byte _type = 1;

}