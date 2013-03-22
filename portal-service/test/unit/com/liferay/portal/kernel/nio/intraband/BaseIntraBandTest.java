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

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.nio.intraband.BaseIntraBand.SendSyncDatagramCompletionHandler;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BaseIntraBandTest {

	public static final long _DEFAULT_TIMEOUT = 1000;

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(ChannelContext.class);
				assertClasses.add(ClosedIntraBandException.class);
				assertClasses.add(CompletionHandler.class);
				assertClasses.addAll(
					Arrays.asList(
						CompletionHandler.class.getDeclaredClasses()));
				assertClasses.add(Datagram.class);
				assertClasses.add(DatagramReceiveHandler.class);
			}

		};

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

		datagramReceiveHandlers = _mockIntraBand.getDatagramReceiveHandlers();

		Assert.assertNotSame(
			datagramReceiveHandlersReference.get(), datagramReceiveHandlers);

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

		// Concurrent registering

		final int handlerCount = 10240;
		final int threadCount = 10;

		final DatagramReceiveHandler[] standardDatagramReceiveHandlers =
			new DatagramReceiveHandler[handlerCount];

		final Queue<DatagramReceiveHandler> oldDatagramReceiveHandlers =
			new ConcurrentLinkedQueue<DatagramReceiveHandler>();

		for (int i = 0; i < handlerCount; i++) {
			standardDatagramReceiveHandlers[i] =
				new RecordDatagramReceiveHandler();
		}

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

	@Test
	public void testGenerateSequenceId() throws Exception {

		// Overflow resetting

		AtomicLong sequenceIdGenerator = _mockIntraBand.sequenceIdGenerator;

		sequenceIdGenerator.set(Long.MAX_VALUE);

		Assert.assertEquals(1, _mockIntraBand.generateSequenceId());
		Assert.assertEquals(2, _mockIntraBand.generateSequenceId());

		// Concurrent resetting

		int testCount = 10240;

		Callable<Long> getSequenceIdCallable = new Callable<Long>() {

			public Long call() throws Exception {
				return _mockIntraBand.generateSequenceId();
			}

		};

		List<Callable<Long>> getSequenceIdCallables =
			new ArrayList<Callable<Long>>(2);

		getSequenceIdCallables.add(getSequenceIdCallable);
		getSequenceIdCallables.add(getSequenceIdCallable);

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		for (int i = 0; i < testCount; i++) {
			sequenceIdGenerator.set(Long.MAX_VALUE);

			List<Future<Long>> getSequenceIdFutures = executorService.invokeAll(
				getSequenceIdCallables);

			Future<Long> sequenceIdFuture1 = getSequenceIdFutures.get(0);

			long sequenceId1 = sequenceIdFuture1.get();

			Future<Long> sequenceIdFuture2 = getSequenceIdFutures.get(1);

			long sequenceId2 = sequenceIdFuture2.get();

			Assert.assertTrue(
				((sequenceId1 == 1) && (sequenceId2 == 2)) ||
				((sequenceId1 == 2) && (sequenceId2 == 1)));
		}

		executorService.shutdownNow();
	}

	@Test
	public void testHandleReading() throws Exception {

		// IOException, new receive datagram, debug log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.FINE);

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(_mockIntraBand);

		ChannelContext channelContext = new ChannelContext(null);

		channelContext.setRegistrationReference(mockRegistrationReference);

		_mockIntraBand.handleReading(
			new MockScatteringByteChannel(false), channelContext);

		Assert.assertFalse(mockRegistrationReference.isValid());
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Broken read channel, unregister ");

		Assert.assertTrue(logRecord.getThrown() instanceof IOException);

		// IOException, new receive Datagram, info log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.INFO);

		mockRegistrationReference = new MockRegistrationReference(
			_mockIntraBand);

		channelContext = new ChannelContext(null);

		channelContext.setRegistrationReference(mockRegistrationReference);

		_mockIntraBand.handleReading(
			new MockScatteringByteChannel(true), channelContext);

		Assert.assertFalse(mockRegistrationReference.isValid());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Broken read channel, unregister ");

		Assert.assertNull(logRecord.getThrown());

		// IOException, existing receive datagram, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		mockRegistrationReference = new MockRegistrationReference(
			_mockIntraBand);

		channelContext = new ChannelContext(null);

		channelContext.setReadingDatagram(Datagram.createReceiveDatagram());
		channelContext.setRegistrationReference(mockRegistrationReference);

		_mockIntraBand.handleReading(
			new MockScatteringByteChannel(false), channelContext);

		Assert.assertFalse(mockRegistrationReference.isValid());
		Assert.assertTrue(logRecords.isEmpty());

		// Slow reading of ownerless datagram, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Pipe pipe = Pipe.open();

		SourceChannel sourceChannel = pipe.source();
		final SinkChannel sinkChannel = pipe.sink();

		// Collect serialized data

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.writeTo(sinkChannel);

		final ByteBuffer byteBuffer = ByteBuffer.allocate(_data.length + 14);

		while (byteBuffer.hasRemaining()) {
			sourceChannel.read(byteBuffer);
		}

		// Slowly write data

		sourceChannel.configureBlocking(false);
		sinkChannel.configureBlocking(false);

		Thread slowWritingThread = new Thread() {

			@Override
			public void run() {
				try {
					for (byte b : byteBuffer.array()) {
						sinkChannel.write(ByteBuffer.wrap(new byte[] {b}));

						Thread.sleep(1);
					}
				}
				catch (Exception e) {
					Assert.fail(e.getMessage());
				}
			}

		};

		slowWritingThread.start();

		// Spin reading

		channelContext = new ChannelContext(null);

		Datagram receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		while (receiveDatagram == channelContext.getReadingDatagram()) {
			_mockIntraBand.handleReading(sourceChannel, channelContext);
		}

		slowWritingThread.join();

		Assert.assertEquals(_type, receiveDatagram.getType());

		ByteBuffer dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Dropped ownerless request ");

		// Read ownerless datagram, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isRequest());
		Assert.assertEquals(_type, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertTrue(logRecords.isEmpty());

		// Read ownerless ACK response, with log

		long sequenceId = 100;

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Datagram ackResponseDatagram = Datagram.createACKResponseDatagram(
			sequenceId);

		ackResponseDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isAckResponse());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Dropped ownerless ACK response ");

		// Ownerless ACK response, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		ackResponseDatagram = Datagram.createACKResponseDatagram(sequenceId);

		ackResponseDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isAckResponse());
		Assert.assertTrue(logRecords.isEmpty());

		// Normal ACK response

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.setSequenceId(sequenceId);

		requestDatagram.completionHandler = recordCompletionHandler;

		requestDatagram.timeout = 10000;

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);

		ackResponseDatagram = Datagram.createACKResponseDatagram(sequenceId);

		ackResponseDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		recordCompletionHandler.waitUntilDelivered();

		Assert.assertTrue(receiveDatagram.isAckResponse());

		// Ownerless response, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Datagram responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		responseDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Dropped ownerless response ");

		// Ownerless response, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		responseDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertTrue(logRecords.isEmpty());

		// Reply response

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.completionTypes = BaseIntraBand.REPLIED_ENUM_SET;
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.timeout = 10000;

		requestDatagram.setSequenceId(sequenceId);

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);

		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		responseDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		recordCompletionHandler.waitUntilReplied();

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());

		// Unconcerned response, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.completionTypes = EnumSet.noneOf(CompletionType.class);
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.timeout = 10000;

		requestDatagram.setSequenceId(sequenceId);

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);

		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		responseDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Dropped unconcerned response ");

		// Unconcerned response, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.completionTypes = EnumSet.noneOf(CompletionType.class);
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.timeout = 10000;

		requestDatagram.setSequenceId(sequenceId);

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);

		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		responseDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertTrue(logRecords.isEmpty());

		// Ownerless request with ACK requirement, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.setSequenceId(sequenceId);
		requestDatagram.setAckRequest(true);

		requestDatagram.writeTo(sinkChannel);

		mockRegistrationReference = new MockRegistrationReference(
			_mockIntraBand);

		channelContext = new ChannelContext(null);

		channelContext.setRegistrationReference(mockRegistrationReference);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isAckRequest());
		Assert.assertTrue(receiveDatagram.isRequest());
		Assert.assertEquals(_type, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Dropped ownerless request ");

		Assert.assertSame(
			mockRegistrationReference,
			_mockIntraBand.getRegistrationReference());

		Datagram datagram = _mockIntraBand.getDatagram();

		Assert.assertEquals(sequenceId, datagram.getSequenceId());
		Assert.assertTrue(datagram.isAckResponse());

		// Request dispatching with failure

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.SEVERE);

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		RecordDatagramReceiveHandler recordDatagramReceiveHandler =
			new RecordDatagramReceiveHandler();

		_mockIntraBand.registerDatagramReceiveHandler(
			_type, recordDatagramReceiveHandler);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.timeout = 10000;

		requestDatagram.setSequenceId(sequenceId);
		requestDatagram.setAckRequest(true);

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);

		requestDatagram.writeTo(sinkChannel);

		receiveDatagram = Datagram.createReceiveDatagram();

		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isRequest());
		Assert.assertEquals(_type, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());

		Datagram recordDatagram =
			recordDatagramReceiveHandler.getReceiveDatagram();

		Assert.assertSame(receiveDatagram, recordDatagram);
		Assert.assertEquals(_type, recordDatagram.getType());

		dataByteBuffer = recordDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Unable to dispatch");

		Assert.assertTrue(logRecord.getThrown() instanceof RuntimeException);

		sourceChannel.close();
		sinkChannel.close();
	}

	@Test
	public void testHandleWriting() throws Exception {

		// IOException, new send Datagram, debug log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.FINE);

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(_mockIntraBand);

		ChannelContext channelContext = new ChannelContext(
			new LinkedList<Datagram>());

		channelContext.setWritingDatagram(
			Datagram.createRequestDatagram(_type, _data));
		channelContext.setRegistrationReference(mockRegistrationReference);

		Assert.assertFalse(
			_mockIntraBand.handleWriting(
				new MockGatheringByteChannel(), channelContext));
		Assert.assertFalse(mockRegistrationReference.isValid());
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertTrue(
			logRecord.getMessage().startsWith(
				"Broken write channel, unregister "));
		Assert.assertTrue(logRecord.getThrown() instanceof IOException);

		// IOException, new send Datagram, info log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.INFO);

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);
		mockRegistrationReference = new MockRegistrationReference(
			_mockIntraBand);

		channelContext = new ChannelContext(new LinkedList<Datagram>());

		channelContext.setWritingDatagram(requestDatagram);
		channelContext.setRegistrationReference(mockRegistrationReference);

		Assert.assertFalse(
			_mockIntraBand.handleWriting(
				new MockGatheringByteChannel(), channelContext));
		Assert.assertFalse(mockRegistrationReference.isValid());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		_assertMessageStartWith(logRecord, "Broken write channel, unregister ");

		Assert.assertNull(logRecord.getThrown());

		// IOException, exist send Datagram, with CompletionHandler, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.completionHandler = recordCompletionHandler;

		mockRegistrationReference = new MockRegistrationReference(
			_mockIntraBand);

		channelContext = new ChannelContext(null);

		channelContext.setWritingDatagram(requestDatagram);
		channelContext.setRegistrationReference(mockRegistrationReference);

		Assert.assertFalse(
			_mockIntraBand.handleWriting(
				new MockGatheringByteChannel(), channelContext));
		Assert.assertFalse(mockRegistrationReference.isValid());
		recordCompletionHandler.waitUntilFailed();
		Assert.assertNotNull(recordCompletionHandler.getIOException());
		Assert.assertTrue(logRecords.isEmpty());

		// Huge Datagram write

		Pipe pipe = Pipe.open();

		SourceChannel sourceChannel = pipe.source();
		SinkChannel sinkChannel = pipe.sink();

		sourceChannel.configureBlocking(false);
		sinkChannel.configureBlocking(false);

		// 10MB buffer

		int bufferSize = 1024 * 1024 * 10;

		ByteBuffer sendByteBuffer = ByteBuffer.allocate(bufferSize);
		ByteBuffer receiveByteBuffer = ByteBuffer.allocate(bufferSize + 14);

		requestDatagram = Datagram.createRequestDatagram(_type, sendByteBuffer);

		channelContext = new ChannelContext(new LinkedList<Datagram>());

		channelContext.setWritingDatagram(requestDatagram);

		int count = 0;

		while (!_mockIntraBand.handleWriting(sinkChannel, channelContext)) {
			count++;
			sourceChannel.read(receiveByteBuffer);
			Assert.assertTrue(sendByteBuffer.hasRemaining());
		}

		sourceChannel.read(receiveByteBuffer);

		Assert.assertFalse(sendByteBuffer.hasRemaining());
		Assert.assertFalse(receiveByteBuffer.hasRemaining());
		Assert.assertTrue(count > 0);

		sourceChannel.configureBlocking(true);
		sinkChannel.configureBlocking(true);

		// SUBMITTED callback

		Object attachment = new Object();

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.attachment = attachment;
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.completionTypes = EnumSet.of(CompletionType.SUBMITTED);

		channelContext = new ChannelContext(new LinkedList<Datagram>());

		channelContext.setWritingDatagram(requestDatagram);

		Assert.assertTrue(
			_mockIntraBand.handleWriting(sinkChannel, channelContext));

		recordCompletionHandler.waitUntilSubmitted();

		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());

		// REPLIED callback

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.completionTypes = EnumSet.of(CompletionType.REPLIED);

		Queue<Datagram> sendingQueue = new LinkedList<Datagram>();

		channelContext = new ChannelContext(sendingQueue);

		channelContext.setWritingDatagram(requestDatagram);

		Assert.assertTrue(
			_mockIntraBand.handleWriting(sinkChannel, channelContext));

		Assert.assertNull(requestDatagram.getDataByteBuffer());

		String requestDatagramString = requestDatagram.toString();

		Assert.assertTrue(requestDatagramString.contains("dataChunk=null"));

		sourceChannel.close();
		sinkChannel.close();

		// Satisfy code coverage

		Assert.assertSame(sendingQueue, channelContext.getSendingQueue());
	}

	@Test
	public void testResponseWaiting() throws Exception {

		// Add

		long sequenceId = 100;
		long timeout = 10000;

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		requestDatagram.setSequenceId(sequenceId);
		requestDatagram.timeout = timeout;

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);

		Assert.assertEquals(1, _mockIntraBand.responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram, _mockIntraBand.responseWaitingMap.get(sequenceId));
		Assert.assertEquals(1, _mockIntraBand.timeoutMap.size());
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().contains(sequenceId));

		// Remove, hit

		Datagram responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		Assert.assertFalse(responseDatagram.isRequest());

		_mockIntraBand.removeResponseWaitingDatagram(responseDatagram);

		Assert.assertTrue(_mockIntraBand.responseWaitingMap.isEmpty());
		Assert.assertTrue(_mockIntraBand.timeoutMap.isEmpty());

		// Remove, miss

		_mockIntraBand.removeResponseWaitingDatagram(responseDatagram);

		Assert.assertTrue(_mockIntraBand.responseWaitingMap.isEmpty());
		Assert.assertTrue(_mockIntraBand.timeoutMap.isEmpty());

		// Cleanup timeout, hit, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Datagram requestDatagram1 = Datagram.createRequestDatagram(
			_type, _data);

		RecordCompletionHandler<Object> recordCompletionHandler1 =
			new RecordCompletionHandler<Object>();

		requestDatagram1.completionHandler = recordCompletionHandler1;
		requestDatagram1.timeout = 1;

		requestDatagram1.setSequenceId(sequenceId);

		Datagram requestDatagram2 = Datagram.createRequestDatagram(
			_type, _data);

		RecordCompletionHandler<Object> recordCompletionHandler2 =
			new RecordCompletionHandler<Object>();

		requestDatagram2.completionHandler = recordCompletionHandler2;
		requestDatagram2.timeout = 1;

		requestDatagram2.setSequenceId(sequenceId + 1);

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram1);

		Thread.sleep(10);

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram2);

		Assert.assertEquals(2, _mockIntraBand.responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram1,
			_mockIntraBand.responseWaitingMap.get(sequenceId));
		Assert.assertSame(
			requestDatagram2,
			_mockIntraBand.responseWaitingMap.get(sequenceId + 1));
		Assert.assertEquals(2, _mockIntraBand.timeoutMap.size());
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().contains(sequenceId));
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().contains(sequenceId + 1));

		Thread.sleep(10);

		_mockIntraBand.cleanUpTimeoutResponseWaitingDatagrams();

		Assert.assertEquals(2, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		_assertMessageStartWith(
			logRecord, "Removed timeout response waiting datagram ");

		logRecord = logRecords.get(1);

		_assertMessageStartWith(
			logRecord, "Removed timeout response waiting datagram ");

		recordCompletionHandler1.waitUntilTimeouted();
		recordCompletionHandler2.waitUntilTimeouted();

		// Cleanup timeout, hit, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram1 = Datagram.createRequestDatagram(_type, _data);

		recordCompletionHandler1 = new RecordCompletionHandler<Object>();

		requestDatagram1.completionHandler = recordCompletionHandler1;
		requestDatagram1.timeout = 1;

		requestDatagram1.setSequenceId(sequenceId);

		requestDatagram2 = Datagram.createRequestDatagram(_type, _data);

		recordCompletionHandler2 = new RecordCompletionHandler<Object>();

		requestDatagram2.completionHandler = recordCompletionHandler2;
		requestDatagram2.timeout = 1;

		requestDatagram2.setSequenceId(sequenceId + 1);

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram1);

		Thread.sleep(10);

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram2);

		Assert.assertEquals(2, _mockIntraBand.responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram1,
			_mockIntraBand.responseWaitingMap.get(sequenceId));
		Assert.assertSame(
			requestDatagram2,
			_mockIntraBand.responseWaitingMap.get(sequenceId + 1));
		Assert.assertEquals(2, _mockIntraBand.timeoutMap.size());
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().contains(sequenceId));
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().contains(sequenceId + 1));

		Thread.sleep(10);

		_mockIntraBand.cleanUpTimeoutResponseWaitingDatagrams();

		Assert.assertTrue(logRecords.isEmpty());

		recordCompletionHandler1.waitUntilTimeouted();
		recordCompletionHandler2.waitUntilTimeouted();

		// Cleanup timeout, miss

		_mockIntraBand.cleanUpTimeoutResponseWaitingDatagrams();
	}

	@Test
	public void testSendDatagramWithCallback() throws Exception {

		// RegistrationReference is null

		try {
			_mockIntraBand.sendDatagram(null, null, null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Registration reference is null", npe.getMessage());
		}

		// RegistrationReference is cancelled

		RegistrationReference registrationReference =
			new MockRegistrationReference(_mockIntraBand);

		registrationReference.cancelRegistration();

		try {
			_mockIntraBand.sendDatagram(
				registrationReference, null, null, null, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Registration reference is invalid", iae.getMessage());
		}

		// Datagram is null

		registrationReference = new MockRegistrationReference(_mockIntraBand);

		try {
			_mockIntraBand.sendDatagram(
				registrationReference, null, null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// Completion type set is null

		try {
			_mockIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _data), null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Completion type set is null", npe.getMessage());
		}

		// Completion type set is empty

		try {
			_mockIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _data), null,
				EnumSet.noneOf(CompletionHandler.CompletionType.class), null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Completion type set is empty", iae.getMessage());
		}

		// Complete handler is null

		try {
			_mockIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _data), null,
				EnumSet.of(CompletionHandler.CompletionType.SUBMITTED), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Complete handler is null", npe.getMessage());
		}

		// TimeUnit is null

		try {
			_mockIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _data), null,
				EnumSet.of(CompletionHandler.CompletionType.SUBMITTED),
				new RecordCompletionHandler<Object>(), 1000, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Time unit is null", npe.getMessage());
		}

		// None positive timeout

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		_mockIntraBand.sendDatagram(
			registrationReference, requestDatagram, null,
			EnumSet.of(CompletionHandler.CompletionType.DELIVERED),
			new RecordCompletionHandler<Object>(), 0, TimeUnit.MILLISECONDS);

		long sequenceId = requestDatagram.getSequenceId();

		Assert.assertEquals(
			_DEFAULT_TIMEOUT, _mockIntraBand.getDatagram().timeout);
		Assert.assertEquals(1, _mockIntraBand.responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram,
			_mockIntraBand.responseWaitingMap.remove(sequenceId));
		Assert.assertEquals(1, _mockIntraBand.timeoutMap.size());
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().remove(sequenceId));

		// Coverting timeout

		_mockIntraBand.sendDatagram(
			registrationReference, requestDatagram, null,
			EnumSet.of(CompletionHandler.CompletionType.REPLIED),
			new RecordCompletionHandler<Object>(), 2, TimeUnit.SECONDS);

		sequenceId = requestDatagram.getSequenceId();

		Assert.assertEquals(2000, _mockIntraBand.getDatagram().timeout);
		Assert.assertEquals(1, _mockIntraBand.responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram,
			_mockIntraBand.responseWaitingMap.remove(sequenceId));
		Assert.assertEquals(1, _mockIntraBand.timeoutMap.size());
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().remove(sequenceId));

		// Default timeout

		_mockIntraBand.sendDatagram(
			registrationReference, requestDatagram, null,
			EnumSet.of(CompletionHandler.CompletionType.SUBMITTED),
			new RecordCompletionHandler<Object>());

		Assert.assertEquals(
			_DEFAULT_TIMEOUT, _mockIntraBand.getDatagram().timeout);
	}

	@Test
	public void testSendDatagramWithoutCallback() throws Exception {

		// RegistrationReference is null

		try {
			_mockIntraBand.sendDatagram(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Registration reference is null", npe.getMessage());
		}

		// RegistrationReference is cancelled

		RegistrationReference registrationReference =
			new MockRegistrationReference(_mockIntraBand);

		registrationReference.cancelRegistration();

		try {
			_mockIntraBand.sendDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Registration reference is invalid", iae.getMessage());
		}

		// Datagram is null

		registrationReference = new MockRegistrationReference(_mockIntraBand);

		try {
			_mockIntraBand.sendDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// Normal send

		Datagram datagram = Datagram.createRequestDatagram(_type, _data);

		_mockIntraBand.sendDatagram(registrationReference, datagram);

		Assert.assertSame(
			registrationReference, _mockIntraBand.getRegistrationReference());
		Assert.assertSame(datagram, _mockIntraBand.getDatagram());
	}

	@Test
	public void testSendSyncDatagram() throws Exception {

		// RegistrationReference is null

		try {
			_mockIntraBand.sendSyncDatagram(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Registration reference is null", npe.getMessage());
		}

		// RegistrationReference is cancelled

		RegistrationReference registrationReference =
			new MockRegistrationReference(_mockIntraBand);

		registrationReference.cancelRegistration();

		try {
			_mockIntraBand.sendSyncDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Registration reference is invalid", iae.getMessage());
		}

		// Datagram is null

		registrationReference = new MockRegistrationReference(_mockIntraBand);

		try {
			_mockIntraBand.sendSyncDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// TimeUnit is null

		try {
			_mockIntraBand.sendSyncDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _data), 1000, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Time unit is null", npe.getMessage());
		}

		// None positive timeout

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		try {
			_mockIntraBand.sendSyncDatagram(
				registrationReference, requestDatagram, 0,
				TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
			Assert.assertEquals("Result waiting timeout", te.getMessage());
		}

		Assert.assertEquals(
			_DEFAULT_TIMEOUT, _mockIntraBand.getDatagram().timeout);

		// Coverting timeout

		try {
			_mockIntraBand.sendSyncDatagram(
				registrationReference, requestDatagram, 2, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
			Assert.assertEquals("Result waiting timeout", te.getMessage());
		}

		Assert.assertEquals(2000, requestDatagram.timeout);

		// Datagram writing IOException

		final IOException expectedIOException = new IOException(
			"Force to fail");

		IntraBand intraBand = new MockIntraBand(_DEFAULT_TIMEOUT) {

			@Override
			protected void doSendDatagram(
				RegistrationReference registrationReference,
				Datagram datagram) {

				datagram.completionHandler.failed(null, expectedIOException);
			}

		};

		try {
			intraBand.sendSyncDatagram(registrationReference, requestDatagram);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertSame(expectedIOException, ioe);
		}

		// Replied

		final Datagram expectedDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		intraBand = new MockIntraBand(_DEFAULT_TIMEOUT) {

			@Override
			protected void doSendDatagram(
				RegistrationReference registrationReference,
				Datagram datagram) {

				datagram.completionHandler.replied(null, expectedDatagram);
			}

		};

		Datagram responseDatagram = intraBand.sendSyncDatagram(
			registrationReference, requestDatagram);

		Assert.assertSame(expectedDatagram, responseDatagram);

		// Satisfy code coverage

		SendSyncDatagramCompletionHandler sendSyncDatagramCompletionHandler =
			new SendSyncDatagramCompletionHandler();

		sendSyncDatagramCompletionHandler.delivered(null);
		sendSyncDatagramCompletionHandler.submitted(null);
		sendSyncDatagramCompletionHandler.timeouted(null);
	}

	private void _assertMessageStartWith(
		LogRecord logRecord, String startWith) {

		String message = logRecord.getMessage();

		Assert.assertTrue(message.startsWith(startWith));
	}

	private static final String _DATA_STRING =
		BaseIntraBandTest.class.getName();

	private byte[] _data = _DATA_STRING.getBytes(Charset.defaultCharset());
	private MockIntraBand _mockIntraBand = new MockIntraBand(_DEFAULT_TIMEOUT);
	private byte _type = 1;

	private static class MockGatheringByteChannel
		implements GatheringByteChannel {

		public void close() throws IOException {
			throw new IOException();
		}

		public boolean isOpen() {
			return true;
		}

		public int write(ByteBuffer byteBuffer) throws IOException {
			throw new IOException();
		}

		public long write(ByteBuffer[] byteBuffers) throws IOException {
			throw new IOException();
		}

		public long write(ByteBuffer[] byteBuffers, int offset, int length)
			throws IOException {

			throw new IOException();
		}

	}

	private static class MockScatteringByteChannel
		implements ScatteringByteChannel {

		public MockScatteringByteChannel(boolean eofOnDataBufferReading) {
			_eofOnDataBufferReading = eofOnDataBufferReading;
		}

		public void close() throws IOException {
			throw new IOException();
		}

		public boolean isOpen() {
			return true;
		}

		public int read(ByteBuffer byteBuffer) {
			if (_eofOnDataBufferReading && (byteBuffer.capacity() == 14)) {
				BigEndianCodec.putInt(byteBuffer.array(), 10, 1);

				byteBuffer.position(byteBuffer.limit());

				return 14;
			}
			else {
				return -1;
			}
		}

		public long read(ByteBuffer[] byteBuffers) {
			return -1;
		}

		public long read(ByteBuffer[] byteBuffers, int offset, int length) {
			return -1;
		}

		private final boolean _eofOnDataBufferReading;

	}

}