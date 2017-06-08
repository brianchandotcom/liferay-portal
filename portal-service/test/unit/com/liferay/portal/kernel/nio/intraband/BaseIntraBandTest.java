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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.nio.intraband.BaseIntraBand.SendSyncDatagramCompletionHandler;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

import java.util.ArrayList;
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

	public static final long DEFAULT_TIMEOUT = 1000;

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
		AtomicReference<DatagramReceiveHandler[]>
			datagramReceiveHandlersReference =
				_mockIntraBand.datagramReceiveHandlersReference;

		// 1) Assert size

		Assert.assertEquals(256, datagramReceiveHandlersReference.get().length);

		// 2) Get with copy

		DatagramReceiveHandler[] datagramReceiveHandlers =
			_mockIntraBand.getDatagramReceiveHandlers();

		Assert.assertNotSame(
			datagramReceiveHandlersReference.get(), datagramReceiveHandlers);

		// 3) First register

		DatagramReceiveHandler datagramReceiveHandler1 =
			new RecordDatagramReceiveHandler();

		DatagramReceiveHandler oldDatagramReceiveHandler =
			_mockIntraBand.registerDatagramReceiveHandler(
				_type, datagramReceiveHandler1);

		Assert.assertNull(oldDatagramReceiveHandler);

		Assert.assertSame(
			datagramReceiveHandler1,
			_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		// 4) Second register

		DatagramReceiveHandler datagramReceiveHandler2 =
			new RecordDatagramReceiveHandler();

		oldDatagramReceiveHandler =
			_mockIntraBand.registerDatagramReceiveHandler(
				_type, datagramReceiveHandler2);

		Assert.assertSame(datagramReceiveHandler1, oldDatagramReceiveHandler);

		Assert.assertSame(
			datagramReceiveHandler2,
			_mockIntraBand.getDatagramReceiveHandlers()[_type]);

		// 5) Unregister

		DatagramReceiveHandler removedDatagramReceiveHandler =
			_mockIntraBand.unregisterDatagramReceiveHandler(_type);

		Assert.assertSame(
			datagramReceiveHandler2, removedDatagramReceiveHandler);

		// 6) Concurrent registering

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

			private final int _start;
			private final int _end;
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

		// 7) Assert null after close

		Assert.assertNull(datagramReceiveHandlersReference.get());

		// 8) Get after close

		try {
			_mockIntraBand.getDatagramReceiveHandlers();

			Assert.fail();
		}
		catch (ClosedIntraBandException cibe) {
		}

		// 9) Register after close

		try {
			_mockIntraBand.registerDatagramReceiveHandler(
				_type, new RecordDatagramReceiveHandler());

			Assert.fail();
		}
		catch (ClosedIntraBandException cibe) {
		}

		// 10) Unregister after close

		try {
			_mockIntraBand.unregisterDatagramReceiveHandler(_type);

			Assert.fail();
		}
		catch (ClosedIntraBandException cibe) {
		}
	}

	@Test
	public void testGenerateSequenceId() throws Exception {

		// 1) Overflow resetting

		_mockIntraBand.sequenceIdGenerator.set(Long.MAX_VALUE);

		Assert.assertEquals(1, _mockIntraBand.generateSequenceId());
		Assert.assertEquals(2, _mockIntraBand.generateSequenceId());

		// 2) Concurrent resetting

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
			_mockIntraBand.sequenceIdGenerator.set(Long.MAX_VALUE);

			List<Future<Long>> getSequenceIdFutures = executorService.invokeAll(
				getSequenceIdCallables);

			long sequenceId1 = getSequenceIdFutures.get(0).get();
			long sequenceId2 = getSequenceIdFutures.get(1).get();

			Assert.assertTrue(
				((sequenceId1 == 1) && (sequenceId2 == 2)) ||
				((sequenceId1 == 2) && (sequenceId2 == 1)));
		}

		executorService.shutdownNow();
	}

	@Test
	public void testHandleReading() throws Exception {

		// 1) IOException, new receive Datagram, debug log

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

		Assert.assertTrue(
			logRecord.getMessage().startsWith(
				"Broken read channel, unregister "));
		Assert.assertTrue(logRecord.getThrown() instanceof IOException);

		// 2) IOException, new receive Datagram, info log

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

		Assert.assertTrue(
			logRecord.getMessage().startsWith(
				"Broken read channel, unregister "));
		Assert.assertNull(logRecord.getThrown());

		// 3) IOException, exist receive Datagram, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		mockRegistrationReference = new MockRegistrationReference(
			_mockIntraBand);
		channelContext = new ChannelContext(null);
		channelContext.setRegistrationReference(mockRegistrationReference);
		channelContext.setReadingDatagram(Datagram.createReceiveDatagram());

		_mockIntraBand.handleReading(
			new MockScatteringByteChannel(false), channelContext);

		Assert.assertFalse(mockRegistrationReference.isValid());
		Assert.assertTrue(logRecords.isEmpty());

		// 4) Slow reading ownerless Datagram, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Pipe pipe = Pipe.open();

		SourceChannel sourceChannel = pipe.source();
		final SinkChannel sinkChannel = pipe.sink();

		// Collecting serialized data

		Datagram requestDatagram = Datagram.createRequestDatagram(
			_type, _dataContent);

		requestDatagram.writeTo(sinkChannel);

		final ByteBuffer byteBuffer = ByteBuffer.allocate(
			_dataContent.length + 14);

		while (byteBuffer.hasRemaining()) {
			sourceChannel.read(byteBuffer);
		}

		// Slowly writing data

		sourceChannel.configureBlocking(false);
		sinkChannel.configureBlocking(false);

		Thread slowWritingThread = new Thread() {

			@Override
			public void run() {
				try {
					for (byte b : byteBuffer.array()) {
						sinkChannel.write(ByteBuffer.wrap(new byte[]{b}));

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
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());
		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped ownerless request "));

		// 5) Read ownerless Datagram, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		requestDatagram.writeTo(sinkChannel);
		receiveDatagram = Datagram.createReceiveDatagram();
		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isRequest());
		Assert.assertEquals(_type, receiveDatagram.getType());
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());
		Assert.assertTrue(logRecords.isEmpty());

		// 6) Read ownerless ACK response, with log

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
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped ownerless ACK response "));

		// 7) Ownerless ACK response, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		ackResponseDatagram = Datagram.createACKResponseDatagram(sequenceId);
		ackResponseDatagram.writeTo(sinkChannel);
		receiveDatagram = Datagram.createReceiveDatagram();
		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isAckResponse());
		Assert.assertTrue(logRecords.isEmpty());

		// 8) Normal ACK response

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
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

		// 9) Ownerless response, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Datagram responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _dataContent);
		responseDatagram.writeTo(sinkChannel);
		receiveDatagram = Datagram.createReceiveDatagram();
		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());
		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped ownerless response "));

		// 10) Ownerless response, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _dataContent);
		responseDatagram.writeTo(sinkChannel);
		receiveDatagram = Datagram.createReceiveDatagram();
		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());
		Assert.assertTrue(logRecords.isEmpty());

		// 11) Reply response

		recordCompletionHandler = new RecordCompletionHandler<Object>();
		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		requestDatagram.setSequenceId(sequenceId);
		requestDatagram.completionTypes = BaseIntraBand.REPLIED_ENUM_SET;
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.timeout = 10000;
		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);
		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _dataContent);
		responseDatagram.writeTo(sinkChannel);
		receiveDatagram = Datagram.createReceiveDatagram();
		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		recordCompletionHandler.waitUntilReplied();
		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());

		// 12) Unconcerned response, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		recordCompletionHandler = new RecordCompletionHandler<Object>();
		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		requestDatagram.setSequenceId(sequenceId);
		requestDatagram.completionTypes = EnumSet.noneOf(CompletionType.class);
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.timeout = 10000;
		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);
		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _dataContent);
		responseDatagram.writeTo(sinkChannel);
		receiveDatagram = Datagram.createReceiveDatagram();
		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());
		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped unconcerned response "));

		// 13) Unconcerned response, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		recordCompletionHandler = new RecordCompletionHandler<Object>();
		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		requestDatagram.setSequenceId(sequenceId);
		requestDatagram.completionTypes = EnumSet.noneOf(CompletionType.class);
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.timeout = 10000;
		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);
		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _dataContent);
		responseDatagram.writeTo(sinkChannel);
		receiveDatagram = Datagram.createReceiveDatagram();
		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isResponse());
		Assert.assertEquals(0, receiveDatagram.getType());
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());
		Assert.assertTrue(logRecords.isEmpty());

		// 14) Ownerless request with ACK requirement, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
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
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());
		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped ownerless request "));
		Assert.assertSame(
			mockRegistrationReference,
			_mockIntraBand.getRegistrationReference());
		Assert.assertEquals(
			sequenceId, _mockIntraBand.getDatagram().getSequenceId());
		Assert.assertTrue(_mockIntraBand.getDatagram().isAckResponse());

		// 15) Request dispatching with failure

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.SEVERE);

		recordCompletionHandler = new RecordCompletionHandler<Object>();
		RecordDatagramReceiveHandler recordDatagramReceiveHandler =
			new RecordDatagramReceiveHandler();
		_mockIntraBand.registerDatagramReceiveHandler(
			_type, recordDatagramReceiveHandler);
		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		requestDatagram.setSequenceId(sequenceId);
		requestDatagram.setAckRequest(true);
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.timeout = 10000;
		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);
		requestDatagram.writeTo(sinkChannel);
		receiveDatagram = Datagram.createReceiveDatagram();
		channelContext.setReadingDatagram(receiveDatagram);

		_mockIntraBand.handleReading(sourceChannel, channelContext);

		Assert.assertTrue(receiveDatagram.isRequest());
		Assert.assertEquals(_type, receiveDatagram.getType());
		Assert.assertArrayEquals(
			_dataContent, receiveDatagram.getData().array());
		Datagram recordDatagram =
			recordDatagramReceiveHandler.getReceiveDatagram();
		Assert.assertSame(receiveDatagram, recordDatagram);
		Assert.assertEquals(_type, recordDatagram.getType());
		Assert.assertArrayEquals(
			_dataContent, recordDatagram.getData().array());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertTrue(
			logRecord.getMessage().startsWith("Dispatching failure."));
		Assert.assertTrue(logRecord.getThrown() instanceof RuntimeException);

		sourceChannel.close();
		sinkChannel.close();
	}

	@Test
	public void testHandleWriting() throws Exception {

		// 1) IOException, new send Datagram, debug log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.FINE);

		Datagram requestDatagram = Datagram.createRequestDatagram(
			_type, _dataContent);
		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(_mockIntraBand);

		ChannelContext channelContext = new ChannelContext(
			new LinkedList<Datagram>());
		channelContext.setWritingDatagram(requestDatagram);
		channelContext.setRegistrationReference(mockRegistrationReference);

		boolean stillWritable = _mockIntraBand.handleWriting(
			new MockGatheringByteChannel(), channelContext);

		Assert.assertFalse(stillWritable);
		Assert.assertFalse(mockRegistrationReference.isValid());
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertTrue(
			logRecord.getMessage().startsWith(
				"Broken write channel, unregister "));
		Assert.assertTrue(logRecord.getThrown() instanceof IOException);

		// 2) IOException, new send Datagram, info log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.INFO);

		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		mockRegistrationReference = new MockRegistrationReference(
			_mockIntraBand);

		channelContext = new ChannelContext(new LinkedList<Datagram>());
		channelContext.setWritingDatagram(requestDatagram);
		channelContext.setRegistrationReference(mockRegistrationReference);

		stillWritable = _mockIntraBand.handleWriting(
			new MockGatheringByteChannel(), channelContext);

		Assert.assertFalse(stillWritable);
		Assert.assertFalse(mockRegistrationReference.isValid());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertTrue(
			logRecord.getMessage().startsWith(
				"Broken write channel, unregister "));
		Assert.assertNull(logRecord.getThrown());

		// 3) IOException, exist send Datagram, with CompletionHandler,
		// without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();
		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		requestDatagram.completionHandler = recordCompletionHandler;
		mockRegistrationReference = new MockRegistrationReference(
			_mockIntraBand);
		channelContext = new ChannelContext(null);
		channelContext.setWritingDatagram(requestDatagram);
		channelContext.setRegistrationReference(mockRegistrationReference);

		stillWritable = _mockIntraBand.handleWriting(
			new MockGatheringByteChannel(), channelContext);

		Assert.assertFalse(stillWritable);
		Assert.assertFalse(mockRegistrationReference.isValid());
		recordCompletionHandler.waitUntilFailed();
		Assert.assertNotNull(recordCompletionHandler.getIOException());
		Assert.assertTrue(logRecords.isEmpty());

		// 4) Huge Datagram write

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

		// 5) SUBMITTED callback

		Object attachment = new Object();
		recordCompletionHandler = new RecordCompletionHandler<Object>();
		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		requestDatagram.attachment = attachment;
		requestDatagram.completionHandler = recordCompletionHandler;
		requestDatagram.completionTypes = EnumSet.of(CompletionType.SUBMITTED);
		channelContext = new ChannelContext(new LinkedList<Datagram>());
		channelContext.setWritingDatagram(requestDatagram);

		stillWritable = _mockIntraBand.handleWriting(
			sinkChannel, channelContext);

		Assert.assertTrue(stillWritable);
		recordCompletionHandler.waitUntilSubmitted();
		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());

		// 6) REPLIED callback

		requestDatagram = Datagram.createRequestDatagram(_type, _dataContent);
		requestDatagram.completionTypes = EnumSet.of(CompletionType.REPLIED);

		Queue<Datagram> sendingQueue = new LinkedList<Datagram>();

		channelContext = new ChannelContext(sendingQueue);
		channelContext.setWritingDatagram(requestDatagram);

		stillWritable = _mockIntraBand.handleWriting(
			sinkChannel, channelContext);

		Assert.assertTrue(stillWritable);
		Assert.assertNull(requestDatagram.getData());
		Assert.assertTrue(
			requestDatagram.toString().contains("dataChunk=null"));

		sourceChannel.close();
		sinkChannel.close();

		// Satisfy code coverage

		Assert.assertSame(sendingQueue, channelContext.getSendingQueue());
	}

	@Test
	public void testResponseWaiting() throws Exception {

		// 1) Add

		long sequenceId = 100;
		long timeout = 10000;

		Datagram requestDatagram = Datagram.createRequestDatagram(
			_type, _dataContent);

		requestDatagram.setSequenceId(sequenceId);
		requestDatagram.timeout = timeout;

		_mockIntraBand.addResponseWaitingDatagram(requestDatagram);

		Assert.assertEquals(1, _mockIntraBand.responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram, _mockIntraBand.responseWaitingMap.get(sequenceId));

		Assert.assertEquals(1, _mockIntraBand.timeoutMap.size());
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().contains(sequenceId));

		// 2) Remove, hit

		Datagram responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _dataContent);

		Assert.assertFalse(responseDatagram.isRequest());

		_mockIntraBand.removeResponseWaitingDatagram(responseDatagram);

		Assert.assertTrue(_mockIntraBand.responseWaitingMap.isEmpty());
		Assert.assertTrue(_mockIntraBand.timeoutMap.isEmpty());

		// 3) Remove, miss

		_mockIntraBand.removeResponseWaitingDatagram(responseDatagram);

		Assert.assertTrue(_mockIntraBand.responseWaitingMap.isEmpty());
		Assert.assertTrue(_mockIntraBand.timeoutMap.isEmpty());

		// 4) Cleanup timeout, hit, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Datagram requestDatagram1 = Datagram.createRequestDatagram(
			_type, _dataContent);

		RecordCompletionHandler<Object> recordCompletionHandler1 =
			new RecordCompletionHandler<Object>();

		requestDatagram1.setSequenceId(sequenceId);
		requestDatagram1.completionHandler = recordCompletionHandler1;
		requestDatagram1.timeout = 1;

		Datagram requestDatagram2 = Datagram.createRequestDatagram(
			_type, _dataContent);

		RecordCompletionHandler<Object> recordCompletionHandler2 =
			new RecordCompletionHandler<Object>();

		requestDatagram2.setSequenceId(sequenceId + 1);
		requestDatagram2.completionHandler = recordCompletionHandler2;
		requestDatagram2.timeout = 1;

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
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Removed timeout response waiting Datagram "));
		Assert.assertTrue(
			logRecords.get(1).getMessage().startsWith(
				"Removed timeout response waiting Datagram "));

		recordCompletionHandler1.waitUntilTimeouted();
		recordCompletionHandler2.waitUntilTimeouted();

		// 5) Cleanup timeout, hit, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram1 = Datagram.createRequestDatagram(_type, _dataContent);

		recordCompletionHandler1 = new RecordCompletionHandler<Object>();

		requestDatagram1.setSequenceId(sequenceId);
		requestDatagram1.completionHandler = recordCompletionHandler1;
		requestDatagram1.timeout = 1;

		requestDatagram2 = Datagram.createRequestDatagram(_type, _dataContent);

		recordCompletionHandler2 = new RecordCompletionHandler<Object>();

		requestDatagram2.setSequenceId(sequenceId + 1);
		requestDatagram2.completionHandler = recordCompletionHandler2;
		requestDatagram2.timeout = 1;

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

		// 5) Cleanup timeout, miss

		_mockIntraBand.cleanUpTimeoutResponseWaitingDatagrams();
	}

	@Test
	public void testSendDatagramWithCallback() throws Exception {

		// 1) RegistrationReference is null

		try {
			_mockIntraBand.sendDatagram(null, null, null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"RegistrationReference is null", npe.getMessage());
		}

		// 2) RegistrationReference is cancelled

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
				"RegistrationReference is not valid", iae.getMessage());
		}

		// 3) Datagram is null

		registrationReference = new MockRegistrationReference(_mockIntraBand);

		try {
			_mockIntraBand.sendDatagram(
				registrationReference, null, null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// 4) CompletionType set is null

		try {
			_mockIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _dataContent), null, null,
				null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("CompletionType set is null", npe.getMessage());
		}

		// 5) CompletionType set is empty

		try {
			_mockIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _dataContent), null,
				EnumSet.noneOf(CompletionHandler.CompletionType.class), null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"CompletionType set is empty", iae.getMessage());
		}

		// 6) CompleteHandler is null

		try {
			_mockIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _dataContent), null,
				EnumSet.of(CompletionHandler.CompletionType.SUBMITTED), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("CompleteHandler is null", npe.getMessage());
		}

		// 7) TimeUnit is null

		try {
			_mockIntraBand.sendDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _dataContent), null,
				EnumSet.of(CompletionHandler.CompletionType.SUBMITTED),
				new RecordCompletionHandler<Object>(), 1000, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("TimeUnit is null", npe.getMessage());
		}

		// 8) None positive timeout

		Datagram requestDatagram = Datagram.createRequestDatagram(
			_type, _dataContent);

		_mockIntraBand.sendDatagram(
			registrationReference, requestDatagram, null,
			EnumSet.of(CompletionHandler.CompletionType.DELIVERED),
			new RecordCompletionHandler<Object>(), 0, TimeUnit.MILLISECONDS);

		long sequenceId = requestDatagram.getSequenceId();

		Assert.assertEquals(
			DEFAULT_TIMEOUT, _mockIntraBand.getDatagram().timeout);
		Assert.assertEquals(1, _mockIntraBand.responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram,
			_mockIntraBand.responseWaitingMap.remove(sequenceId));
		Assert.assertEquals(1, _mockIntraBand.timeoutMap.size());
		Assert.assertTrue(
			_mockIntraBand.timeoutMap.values().remove(sequenceId));

		// 9) Coverting timeout

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

		// 10) Default timeout

		_mockIntraBand.sendDatagram(
			registrationReference, requestDatagram, null,
			EnumSet.of(CompletionHandler.CompletionType.SUBMITTED),
			new RecordCompletionHandler<Object>());

		Assert.assertEquals(
			DEFAULT_TIMEOUT, _mockIntraBand.getDatagram().timeout);
	}

	@Test
	public void testSendDatagramWithoutCallback() throws Exception {

		// 1) RegistrationReference is null

		try {
			_mockIntraBand.sendDatagram(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"RegistrationReference is null", npe.getMessage());
		}

		// 2) RegistrationReference is cancelled

		RegistrationReference registrationReference =
			new MockRegistrationReference(_mockIntraBand);

		registrationReference.cancelRegistration();

		try {
			_mockIntraBand.sendDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"RegistrationReference is not valid", iae.getMessage());
		}

		// 3) Datagram is null

		registrationReference = new MockRegistrationReference(_mockIntraBand);

		try {
			_mockIntraBand.sendDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// 4) Normal send

		Datagram datagram = Datagram.createRequestDatagram(_type, _dataContent);

		_mockIntraBand.sendDatagram(registrationReference, datagram);

		Assert.assertSame(
			registrationReference, _mockIntraBand.getRegistrationReference());
		Assert.assertSame(datagram, _mockIntraBand.getDatagram());
	}

	@Test
	public void testSendSyncDatagram() throws Exception {

		// 1) RegistrationReference is null

		try {
			_mockIntraBand.sendSyncDatagram(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"RegistrationReference is null", npe.getMessage());
		}

		// 2) RegistrationReference is cancelled

		RegistrationReference registrationReference =
			new MockRegistrationReference(_mockIntraBand);

		registrationReference.cancelRegistration();

		try {
			_mockIntraBand.sendSyncDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"RegistrationReference is not valid", iae.getMessage());
		}

		// 3) Datagram is null

		registrationReference = new MockRegistrationReference(_mockIntraBand);

		try {
			_mockIntraBand.sendSyncDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// 4) TimeUnit is null

		try {
			_mockIntraBand.sendSyncDatagram(
				registrationReference,
				Datagram.createRequestDatagram(_type, _dataContent), 1000,
				null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("TimeUnit is null", npe.getMessage());
		}

		// 5) None positive timeout

		Datagram requestDatagram = Datagram.createRequestDatagram(
			_type, _dataContent);

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
			DEFAULT_TIMEOUT, _mockIntraBand.getDatagram().timeout);

		// 6) Coverting timeout

		try {
			_mockIntraBand.sendSyncDatagram(
				registrationReference, requestDatagram, 2, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
			Assert.assertEquals("Result waiting timeout", te.getMessage());
		}

		Assert.assertEquals(2000, requestDatagram.timeout);

		// 7) Datagram writing IOException

		final IOException expectedIOException = new IOException(
			"Force to fail");

		IntraBand intraBand = new MockIntraBand(DEFAULT_TIMEOUT) {

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

		// 8) Replied

		final Datagram expectedDatagram = Datagram.createResponseDatagram(
			requestDatagram, _dataContent);

		intraBand = new MockIntraBand(DEFAULT_TIMEOUT) {

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

		// 9) Invoke unused SendSyncDatagramCompletionHandler methods to satisfy
		// code coverage

		SendSyncDatagramCompletionHandler sendSyncDatagramCompletionHandler =
			new SendSyncDatagramCompletionHandler();

		sendSyncDatagramCompletionHandler.delivered(null);
		sendSyncDatagramCompletionHandler.submitted(null);
		sendSyncDatagramCompletionHandler.timeouted(null);
	}

	private byte[] _dataContent = "Datagram Sending Test".getBytes(
		Charset.defaultCharset());
	private MockIntraBand _mockIntraBand = new MockIntraBand(DEFAULT_TIMEOUT);
	private byte _type = 1;

	private static class MockGatheringByteChannel
		implements GatheringByteChannel {

		public void close() throws IOException {
			throw new IOException();
		}

		public boolean isOpen() {
			return true;
		}

		public int write(ByteBuffer src) throws IOException {
			throw new IOException();
		}

		public long write(ByteBuffer[] srcs) throws IOException {
			throw new IOException();
		}

		public long write(ByteBuffer[] srcs, int offset, int length)
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

		public int read(ByteBuffer dst) {
			if (_eofOnDataBufferReading && (dst.capacity() == 14)) {
				BigEndianCodec.putInt(dst.array(), 10, 1);

				dst.position(dst.limit());

				return 14;
			}
			else {
				return -1;
			}
		}

		public long read(ByteBuffer[] dsts) {
			return -1;
		}

		public long read(ByteBuffer[] dsts, int offset, int length) {
			return -1;
		}

		private final boolean _eofOnDataBufferReading;

	}

}