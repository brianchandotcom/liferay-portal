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

import com.liferay.portal.kernel.util.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
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

		Assert.assertNotSame(
			datagramReceiveHandlers,
			_mockIntraBand.getDatagramReceiveHandlers());

		// First register

		DatagramReceiveHandler datagramReceiveHandler1 =
			new RecordDatagramReceiveHandler();

		Assert.assertNull(
			_mockIntraBand.registerDatagramReceiveHandler(
				_type, datagramReceiveHandler1));
		Assert.assertSame(
			datagramReceiveHandler1,
			_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		// Second register

		DatagramReceiveHandler datagramReceiveHandler2 =
			new RecordDatagramReceiveHandler();

		Assert.assertSame(
			datagramReceiveHandler1,
			_mockIntraBand.registerDatagramReceiveHandler(
				_type, datagramReceiveHandler2));
		Assert.assertSame(
			datagramReceiveHandler2,
			_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		// Unregister

		Assert.assertSame(
			datagramReceiveHandler2,
			_mockIntraBand.unregisterDatagramReceiveHandler(_type));
		Assert.assertNull(_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		// Concurrent registering

		final int inputDatagramReceiveHandlersCount = 10240;
		final int threadCount = 10;

		final DatagramReceiveHandler[] inputDatagramReceiveHandlers =
			new DatagramReceiveHandler[inputDatagramReceiveHandlersCount];

		for (int i = 0; i < inputDatagramReceiveHandlersCount; i++) {
			inputDatagramReceiveHandlers[i] =
				new RecordDatagramReceiveHandler();
		}

		final Queue<DatagramReceiveHandler> outputDatagramReceiveHandlers =
			new ConcurrentLinkedQueue<DatagramReceiveHandler>();

		class RegisterJob implements Callable<Void> {

			public RegisterJob(int cur) {
				int delta = inputDatagramReceiveHandlersCount / threadCount;

				_start = cur * delta;

				if ((_start + delta) > inputDatagramReceiveHandlersCount) {
					_end = inputDatagramReceiveHandlersCount;
				}
				else {
					_end = _start + delta;
				}
			}

			public Void call() {
				for (int i = _start; i < _end; i++) {
					DatagramReceiveHandler outputDatagramReceiveHandler =
						_mockIntraBand.registerDatagramReceiveHandler(
							_type, inputDatagramReceiveHandlers[i]);

					if (outputDatagramReceiveHandler != null) {
						outputDatagramReceiveHandlers.offer(
							outputDatagramReceiveHandler);
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

		outputDatagramReceiveHandlers.offer(
			_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		Assert.assertEquals(
			inputDatagramReceiveHandlersCount,
			outputDatagramReceiveHandlers.size());

		for (DatagramReceiveHandler inputDatagramReceiveHandler :
				inputDatagramReceiveHandlers) {

			Assert.assertTrue(
				outputDatagramReceiveHandlers.contains(
					inputDatagramReceiveHandler));
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

	@Test
	public void testGenerateSequenceId() throws Exception {

		// Overflow resetting

		AtomicLong sequenceIdGenerator = _mockIntraBand.sequenceIdGenerator;

		sequenceIdGenerator.set(Long.MAX_VALUE);

		Assert.assertEquals(1, _mockIntraBand.generateSequenceId());
		Assert.assertEquals(2, _mockIntraBand.generateSequenceId());

		// Concurrent resetting

		List<Callable<Long>> getSequenceIdCallables =
			new ArrayList<Callable<Long>>(2);

		Callable<Long> getSequenceIdCallable = new Callable<Long>() {

			public Long call() {
				return _mockIntraBand.generateSequenceId();
			}

		};

		getSequenceIdCallables.add(getSequenceIdCallable);
		getSequenceIdCallables.add(getSequenceIdCallable);

		int testCount = 10240;

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		for (int i = 0; i < testCount; i++) {
			sequenceIdGenerator.set(Long.MAX_VALUE);

			List<Future<Long>> getSequenceIdFutures = executorService.invokeAll(
				getSequenceIdCallables);

			Future<Long> sequenceIdFuture1 = getSequenceIdFutures.get(0);
			Future<Long> sequenceIdFuture2 = getSequenceIdFutures.get(1);

			long sequenceId1 = sequenceIdFuture1.get();
			long sequenceId2 = sequenceIdFuture2.get();

			Assert.assertTrue(
				((sequenceId1 == 1) && (sequenceId2 == 2)) ||
				((sequenceId1 == 2) && (sequenceId2 == 1)));
		}

		executorService.shutdownNow();
	}

	private static final long _DEFAULT_TIMEOUT = Time.SECOND;

	private MockIntraBand _mockIntraBand = new MockIntraBand(_DEFAULT_TIMEOUT);
	private byte _type = 1;

}