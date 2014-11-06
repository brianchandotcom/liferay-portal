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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.nio.intraband.BaseIntraband.SendSyncDatagramCompletionHandler;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class BaseIntrabandTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(ChannelContext.class);
				assertClasses.add(ClosedIntrabandException.class);
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
				_mockIntraband.datagramReceiveHandlersReference;

		DatagramReceiveHandler[] datagramReceiveHandlers =
			datagramReceiveHandlersReference.get();

		Assert.assertEquals(256, datagramReceiveHandlers.length);

		// Copy

		Assert.assertNotSame(
			datagramReceiveHandlers,
			_mockIntraband.getDatagramReceiveHandlers());

		// First register

		DatagramReceiveHandler datagramReceiveHandler1 =
			new RecordDatagramReceiveHandler();

		Assert.assertNull(
			_mockIntraband.registerDatagramReceiveHandler(
				_type, datagramReceiveHandler1));
		Assert.assertSame(
			datagramReceiveHandler1,
			_mockIntraband.getDatagramReceiveHandlers()[_type]);

		// Second register

		DatagramReceiveHandler datagramReceiveHandler2 =
			new RecordDatagramReceiveHandler();

		Assert.assertSame(
			datagramReceiveHandler1,
			_mockIntraband.registerDatagramReceiveHandler(
				_type, datagramReceiveHandler2));
		Assert.assertSame(
			datagramReceiveHandler2,
			_mockIntraband.getDatagramReceiveHandlers()[_type]);

		// Unregister

		Assert.assertSame(
			datagramReceiveHandler2,
			_mockIntraband.unregisterDatagramReceiveHandler(_type));
		Assert.assertNull(_mockIntraband.getDatagramReceiveHandlers()[_type]);

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

			@Override
			public Void call() {
				for (int i = _start; i < _end; i++) {
					DatagramReceiveHandler outputDatagramReceiveHandler =
						_mockIntraband.registerDatagramReceiveHandler(
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
			_mockIntraband.getDatagramReceiveHandlers()[_type]);

		Assert.assertEquals(
			inputDatagramReceiveHandlersCount,
			outputDatagramReceiveHandlers.size());

		for (DatagramReceiveHandler inputDatagramReceiveHandler :
				inputDatagramReceiveHandlers) {

			Assert.assertTrue(
				outputDatagramReceiveHandlers.contains(
					inputDatagramReceiveHandler));
		}

		_mockIntraband.close();

		// Null after close

		Assert.assertNull(datagramReceiveHandlersReference.get());

		// Get after close

		try {
			_mockIntraband.getDatagramReceiveHandlers();

			Assert.fail();
		}
		catch (ClosedIntrabandException cibe) {
		}

		// Register after close

		try {
			_mockIntraband.registerDatagramReceiveHandler(
				_type, new RecordDatagramReceiveHandler());

			Assert.fail();
		}
		catch (ClosedIntrabandException cibe) {
		}

		// Unregister after close

		try {
			_mockIntraband.unregisterDatagramReceiveHandler(_type);

			Assert.fail();
		}
		catch (ClosedIntrabandException cibe) {
		}
	}

	@Test
	public void testGenerateSequenceId() throws Exception {

		// Overflow resetting

		AtomicLong sequenceIdGenerator = _mockIntraband.sequenceIdGenerator;

		sequenceIdGenerator.set(Long.MAX_VALUE);

		Assert.assertEquals(
			Long.MAX_VALUE, _mockIntraband.generateSequenceId());
		Assert.assertEquals(0, _mockIntraband.generateSequenceId());
		Assert.assertEquals(1, _mockIntraband.generateSequenceId());
	}

	@Test
	public void testHandleReading() throws Exception {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraband.class.getName(), Level.FINE);

		try {

			// IOException, new receive datagram, debug log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			ChannelContext channelContext = new ChannelContext(null);

			MockRegistrationReference mockRegistrationReference =
				new MockRegistrationReference(_mockIntraband);

			channelContext.setRegistrationReference(mockRegistrationReference);

			_mockIntraband.handleReading(
				new MockScatteringByteChannel(false), channelContext);

			Assert.assertFalse(mockRegistrationReference.isValid());
			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			assertMessageStartWith(
				logRecord, "Broken read channel, unregister ");

			Assert.assertTrue(logRecord.getThrown() instanceof IOException);

			// IOException, new receive datagram, info log

			logRecords = captureHandler.resetLogLevel(Level.INFO);

			channelContext = new ChannelContext(null);

			mockRegistrationReference = new MockRegistrationReference(
				_mockIntraband);

			channelContext.setRegistrationReference(mockRegistrationReference);

			_mockIntraband.handleReading(
				new MockScatteringByteChannel(true), channelContext);

			Assert.assertFalse(mockRegistrationReference.isValid());
			Assert.assertEquals(1, logRecords.size());

			logRecord = logRecords.get(0);

			assertMessageStartWith(
				logRecord, "Broken read channel, unregister ");

			Assert.assertNull(logRecord.getThrown());

			// IOException, existing receive datagram, without log

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			channelContext = new ChannelContext(null);

			channelContext.setReadingDatagram(Datagram.createReceiveDatagram());

			mockRegistrationReference = new MockRegistrationReference(
				_mockIntraband);

			channelContext.setRegistrationReference(mockRegistrationReference);

			_mockIntraband.handleReading(
				new MockScatteringByteChannel(false), channelContext);

			Assert.assertFalse(mockRegistrationReference.isValid());
			Assert.assertTrue(logRecords.isEmpty());

			// Slow reading of ownerless datagram, with log

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			Pipe pipe = Pipe.open();

			try (SourceChannel sourceChannel = pipe.source();
				final SinkChannel sinkChannel = pipe.sink()) {

				Datagram requestDatagram = Datagram.createRequestDatagram(
					_type, _data);

				requestDatagram.writeTo(sinkChannel);

				final ByteBuffer byteBuffer = ByteBuffer.allocate(
					_data.length + 14);

				while (byteBuffer.hasRemaining()) {
					sourceChannel.read(byteBuffer);
				}

				sourceChannel.configureBlocking(false);
				sinkChannel.configureBlocking(false);

				Thread slowWritingThread = new Thread() {

					@Override
					public void run() {
						try {
							for (byte b : byteBuffer.array()) {
								sinkChannel.write(
									ByteBuffer.wrap(new byte[] {b}));

								Thread.sleep(1);
							}
						}
						catch (Exception e) {
							Assert.fail(e.getMessage());
						}
					}

				};

				slowWritingThread.start();

				channelContext = new ChannelContext(null);

				Datagram receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				while (receiveDatagram == channelContext.getReadingDatagram()) {
					_mockIntraband.handleReading(sourceChannel, channelContext);
				}

				slowWritingThread.join();

				Assert.assertEquals(_type, receiveDatagram.getType());

				ByteBuffer dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());
				Assert.assertEquals(1, logRecords.size());

				logRecord = logRecords.get(0);

				assertMessageStartWith(logRecord, "Dropped ownerless request ");

				// Read ownerless datagram, without log

				logRecords = captureHandler.resetLogLevel(Level.OFF);

				requestDatagram = Datagram.createRequestDatagram(_type, _data);

				requestDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isRequest());
				Assert.assertEquals(_type, receiveDatagram.getType());

				dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());
				Assert.assertTrue(logRecords.isEmpty());

				// Read ownerless ACK response, with log

				logRecords = captureHandler.resetLogLevel(Level.WARNING);

				long sequenceId = 100;

				Datagram ackResponseDatagram =
					Datagram.createACKResponseDatagram(sequenceId);

				ackResponseDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isAckResponse());
				Assert.assertEquals(1, logRecords.size());

				logRecord = logRecords.get(0);

				assertMessageStartWith(
					logRecord, "Dropped ownerless ACK response ");

				// Ownerless ACK response, without log

				logRecords = captureHandler.resetLogLevel(Level.OFF);

				ackResponseDatagram = Datagram.createACKResponseDatagram(
					sequenceId);

				ackResponseDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isAckResponse());
				Assert.assertTrue(logRecords.isEmpty());

				// Normal ACK response

				requestDatagram = Datagram.createRequestDatagram(_type, _data);

				requestDatagram.setSequenceId(sequenceId);

				RecordCompletionHandler<Object> recordCompletionHandler =
					new RecordCompletionHandler<Object>();

				requestDatagram.completionHandler = recordCompletionHandler;

				requestDatagram.timeout = 10000;

				_mockIntraband.addResponseWaitingDatagram(requestDatagram);

				ackResponseDatagram = Datagram.createACKResponseDatagram(
					sequenceId);

				ackResponseDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				recordCompletionHandler.waitUntilDelivered();

				Assert.assertTrue(receiveDatagram.isAckResponse());

				// Ownerless response, with log

				logRecords = captureHandler.resetLogLevel(Level.WARNING);

				Datagram responseDatagram = Datagram.createResponseDatagram(
					requestDatagram, _data);

				responseDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isResponse());
				Assert.assertEquals(0, receiveDatagram.getType());

				dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());
				Assert.assertEquals(1, logRecords.size());

				logRecord = logRecords.get(0);

				assertMessageStartWith(
					logRecord, "Dropped ownerless response ");

				// Ownerless response, without log

				logRecords = captureHandler.resetLogLevel(Level.OFF);

				responseDatagram = Datagram.createResponseDatagram(
					requestDatagram, _data);

				responseDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isResponse());
				Assert.assertEquals(0, receiveDatagram.getType());

				dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());
				Assert.assertTrue(logRecords.isEmpty());

				// Reply response

				requestDatagram = Datagram.createRequestDatagram(_type, _data);

				requestDatagram.setSequenceId(sequenceId);

				recordCompletionHandler = new RecordCompletionHandler<Object>();

				requestDatagram.completionHandler = recordCompletionHandler;

				requestDatagram.completionTypes =
					BaseIntraband.REPLIED_ENUM_SET;
				requestDatagram.timeout = 10000;

				_mockIntraband.addResponseWaitingDatagram(requestDatagram);

				responseDatagram = Datagram.createResponseDatagram(
					requestDatagram, _data);

				responseDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				recordCompletionHandler.waitUntilReplied();

				Assert.assertTrue(receiveDatagram.isResponse());
				Assert.assertEquals(0, receiveDatagram.getType());

				dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());

				// Unconcerned response, with log

				logRecords = captureHandler.resetLogLevel(Level.WARNING);

				requestDatagram = Datagram.createRequestDatagram(_type, _data);

				requestDatagram.setSequenceId(sequenceId);

				recordCompletionHandler = new RecordCompletionHandler<Object>();

				requestDatagram.completionHandler = recordCompletionHandler;

				requestDatagram.completionTypes = EnumSet.noneOf(
					CompletionType.class);
				requestDatagram.timeout = 10000;

				_mockIntraband.addResponseWaitingDatagram(requestDatagram);

				responseDatagram = Datagram.createResponseDatagram(
					requestDatagram, _data);

				responseDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isResponse());
				Assert.assertEquals(0, receiveDatagram.getType());

				dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());
				Assert.assertEquals(1, logRecords.size());

				logRecord = logRecords.get(0);

				assertMessageStartWith(
					logRecord, "Dropped unconcerned response ");

				// Unconcerned response, without log

				logRecords = captureHandler.resetLogLevel(Level.OFF);

				requestDatagram = Datagram.createRequestDatagram(_type, _data);

				requestDatagram.setSequenceId(sequenceId);

				recordCompletionHandler = new RecordCompletionHandler<Object>();

				requestDatagram.completionHandler = recordCompletionHandler;

				requestDatagram.completionTypes = EnumSet.noneOf(
					CompletionType.class);
				requestDatagram.timeout = 10000;

				_mockIntraband.addResponseWaitingDatagram(requestDatagram);

				responseDatagram = Datagram.createResponseDatagram(
					requestDatagram, _data);

				responseDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isResponse());
				Assert.assertEquals(0, receiveDatagram.getType());

				dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());
				Assert.assertTrue(logRecords.isEmpty());

				// Ownerless request with ACK requirement, with log

				logRecords = captureHandler.resetLogLevel(Level.WARNING);

				requestDatagram = Datagram.createRequestDatagram(_type, _data);

				requestDatagram.setAckRequest(true);
				requestDatagram.setSequenceId(sequenceId);

				requestDatagram.writeTo(sinkChannel);

				channelContext = new ChannelContext(null);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				mockRegistrationReference = new MockRegistrationReference(
					_mockIntraband);

				channelContext.setRegistrationReference(
					mockRegistrationReference);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isAckRequest());
				Assert.assertTrue(receiveDatagram.isRequest());
				Assert.assertEquals(_type, receiveDatagram.getType());

				dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());
				Assert.assertEquals(1, logRecords.size());

				logRecord = logRecords.get(0);

				assertMessageStartWith(logRecord, "Dropped ownerless request ");

				Assert.assertSame(
					mockRegistrationReference,
					_mockIntraband.getRegistrationReference());

				Datagram datagram = _mockIntraband.getDatagram();

				Assert.assertEquals(sequenceId, datagram.getSequenceId());
				Assert.assertTrue(datagram.isAckResponse());

				// Request dispatching with failure

				logRecords = captureHandler.resetLogLevel(Level.SEVERE);

				RecordDatagramReceiveHandler recordDatagramReceiveHandler =
					new RecordDatagramReceiveHandler();

				_mockIntraband.registerDatagramReceiveHandler(
					_type, recordDatagramReceiveHandler);

				requestDatagram = Datagram.createRequestDatagram(_type, _data);

				requestDatagram.setAckRequest(true);
				requestDatagram.setSequenceId(sequenceId);

				recordCompletionHandler = new RecordCompletionHandler<Object>();

				requestDatagram.completionHandler = recordCompletionHandler;

				requestDatagram.timeout = 10000;

				_mockIntraband.addResponseWaitingDatagram(requestDatagram);

				requestDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

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

				assertMessageStartWith(logRecord, "Unable to dispatch");

				Assert.assertTrue(
					logRecord.getThrown() instanceof RuntimeException);

				// Request dispatching without failure

				logRecords = captureHandler.resetLogLevel(Level.SEVERE);

				recordDatagramReceiveHandler = new RecordDatagramReceiveHandler(
					false);

				_mockIntraband.registerDatagramReceiveHandler(
					_type, recordDatagramReceiveHandler);

				requestDatagram = Datagram.createRequestDatagram(_type, _data);

				requestDatagram.setAckRequest(true);
				requestDatagram.setSequenceId(sequenceId);

				recordCompletionHandler = new RecordCompletionHandler<Object>();

				requestDatagram.completionHandler = recordCompletionHandler;

				requestDatagram.timeout = 10000;

				_mockIntraband.addResponseWaitingDatagram(requestDatagram);

				requestDatagram.writeTo(sinkChannel);

				receiveDatagram = Datagram.createReceiveDatagram();

				channelContext.setReadingDatagram(receiveDatagram);

				_mockIntraband.handleReading(sourceChannel, channelContext);

				Assert.assertTrue(receiveDatagram.isRequest());
				Assert.assertEquals(_type, receiveDatagram.getType());

				dataByteBuffer = receiveDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());

				recordDatagram =
					recordDatagramReceiveHandler.getReceiveDatagram();

				Assert.assertSame(receiveDatagram, recordDatagram);
				Assert.assertEquals(_type, recordDatagram.getType());

				dataByteBuffer = recordDatagram.getDataByteBuffer();

				Assert.assertArrayEquals(_data, dataByteBuffer.array());
				Assert.assertTrue(logRecords.isEmpty());
			}
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testHandleWriting() throws Exception {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraband.class.getName(), Level.FINE);

		ChannelContext channelContext = null;
		Datagram requestDatagram = null;
		RecordCompletionHandler<Object> recordCompletionHandler = null;

		try {

			// IOException, new send datagram, debug log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			channelContext = new ChannelContext(new LinkedList<Datagram>());

			MockRegistrationReference mockRegistrationReference =
				new MockRegistrationReference(_mockIntraband);

			channelContext.setRegistrationReference(mockRegistrationReference);

			channelContext.setWritingDatagram(
				Datagram.createRequestDatagram(_type, _data));

			Assert.assertFalse(
				_mockIntraband.handleWriting(
					new MockGatheringByteChannel(), channelContext));
			Assert.assertFalse(mockRegistrationReference.isValid());
			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			assertMessageStartWith(
				logRecord, "Broken write channel, unregister ");

			Assert.assertTrue(logRecord.getThrown() instanceof IOException);

			// IOException, new send datagram, info log

			logRecords = captureHandler.resetLogLevel(Level.INFO);

			channelContext = new ChannelContext(new LinkedList<Datagram>());

			mockRegistrationReference = new MockRegistrationReference(
				_mockIntraband);

			channelContext.setRegistrationReference(mockRegistrationReference);

			channelContext.setWritingDatagram(
				Datagram.createRequestDatagram(_type, _data));

			Assert.assertFalse(
				_mockIntraband.handleWriting(
					new MockGatheringByteChannel(), channelContext));
			Assert.assertFalse(mockRegistrationReference.isValid());
			Assert.assertEquals(1, logRecords.size());

			logRecord = logRecords.get(0);

			assertMessageStartWith(
				logRecord, "Broken write channel, unregister ");

			Assert.assertNull(logRecord.getThrown());

			// IOException, exist send datagram, with CompletionHandler,
			// without log

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			channelContext = new ChannelContext(null);

			mockRegistrationReference = new MockRegistrationReference(
				_mockIntraband);

			channelContext.setRegistrationReference(mockRegistrationReference);

			requestDatagram = Datagram.createRequestDatagram(_type, _data);

			recordCompletionHandler = new RecordCompletionHandler<Object>();

			requestDatagram.completionHandler = recordCompletionHandler;

			channelContext.setWritingDatagram(requestDatagram);

			Assert.assertFalse(
				_mockIntraband.handleWriting(
					new MockGatheringByteChannel(), channelContext));
			Assert.assertFalse(mockRegistrationReference.isValid());

			recordCompletionHandler.waitUntilFailed();

			Assert.assertNotNull(recordCompletionHandler.getIOException());
			Assert.assertTrue(logRecords.isEmpty());
		}
		finally {
			captureHandler.close();
		}

		// Huge datagram write

		Pipe pipe = Pipe.open();

		Queue<Datagram> sendingQueue = new LinkedList<Datagram>();

		try (SourceChannel sourceChannel = pipe.source();
			SinkChannel sinkChannel = pipe.sink()) {

			sourceChannel.configureBlocking(false);
			sinkChannel.configureBlocking(false);

			int bufferSize = 1024 * 1024 * 10;

			ByteBuffer sendByteBuffer = ByteBuffer.allocate(bufferSize);
			ByteBuffer receiveByteBuffer = ByteBuffer.allocate(bufferSize + 14);

			channelContext = new ChannelContext(new LinkedList<Datagram>());

			channelContext.setWritingDatagram(
				Datagram.createRequestDatagram(_type, sendByteBuffer));

			int count = 0;

			while (!_mockIntraband.handleWriting(sinkChannel, channelContext)) {
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

			// Submitted callback

			channelContext = new ChannelContext(new LinkedList<Datagram>());

			requestDatagram = Datagram.createRequestDatagram(_type, _data);

			Object attachment = new Object();

			requestDatagram.attachment = attachment;

			recordCompletionHandler = new RecordCompletionHandler<Object>();

			requestDatagram.completionHandler = recordCompletionHandler;

			requestDatagram.completionTypes = EnumSet.of(
				CompletionType.SUBMITTED);

			channelContext.setWritingDatagram(requestDatagram);

			Assert.assertTrue(
				_mockIntraband.handleWriting(sinkChannel, channelContext));

			recordCompletionHandler.waitUntilSubmitted();

			Assert.assertSame(
				attachment, recordCompletionHandler.getAttachment());

			// Replied callback

			channelContext = new ChannelContext(sendingQueue);

			requestDatagram = Datagram.createRequestDatagram(_type, _data);

			requestDatagram.completionTypes = EnumSet.of(
				CompletionType.REPLIED);

			channelContext.setWritingDatagram(requestDatagram);

			Assert.assertTrue(
				_mockIntraband.handleWriting(sinkChannel, channelContext));
			Assert.assertNull(requestDatagram.getDataByteBuffer());

			String requestDatagramString = requestDatagram.toString();

			Assert.assertTrue(requestDatagramString.contains("dataChunk=null"));
		}

		Assert.assertSame(sendingQueue, channelContext.getSendingQueue());
	}

	@Test
	public void testResponseWaiting() throws InterruptedException {

		// Add

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		long sequenceId = 100;

		requestDatagram.setSequenceId(sequenceId);

		requestDatagram.timeout = 10000;

		_mockIntraband.addResponseWaitingDatagram(requestDatagram);

		Map<Long, Datagram> responseWaitingMap =
			_mockIntraband.responseWaitingMap;

		Assert.assertEquals(1, responseWaitingMap.size());
		Assert.assertSame(requestDatagram, responseWaitingMap.get(sequenceId));

		Map<Long, Long> timeoutMap = _mockIntraband.timeoutMap;

		Collection<Long> timeoutSequenceIds = timeoutMap.values();

		Assert.assertEquals(1, timeoutSequenceIds.size());
		Assert.assertTrue(timeoutSequenceIds.contains(sequenceId));

		// Remove, hit

		Datagram responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		Assert.assertFalse(responseDatagram.isRequest());

		_mockIntraband.removeResponseWaitingDatagram(responseDatagram);

		Assert.assertTrue(responseWaitingMap.isEmpty());
		Assert.assertTrue(timeoutSequenceIds.isEmpty());

		// Remove, miss

		_mockIntraband.removeResponseWaitingDatagram(responseDatagram);

		Assert.assertTrue(responseWaitingMap.isEmpty());
		Assert.assertTrue(timeoutSequenceIds.isEmpty());

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraband.class.getName(), Level.WARNING);

		try {

			// Clean up timeout, hit, with log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Datagram requestDatagram1 = Datagram.createRequestDatagram(
				_type, _data);

			requestDatagram1.setSequenceId(sequenceId);

			RecordCompletionHandler<Object> recordCompletionHandler1 =
				new RecordCompletionHandler<Object>();

			requestDatagram1.completionHandler = recordCompletionHandler1;

			requestDatagram1.timeout = 1;

			_mockIntraband.addResponseWaitingDatagram(requestDatagram1);

			Thread.sleep(10);

			Datagram requestDatagram2 = Datagram.createRequestDatagram(
				_type, _data);

			requestDatagram2.setSequenceId(sequenceId + 1);

			RecordCompletionHandler<Object> recordCompletionHandler2 =
				new RecordCompletionHandler<Object>();

			requestDatagram2.completionHandler = recordCompletionHandler2;

			requestDatagram2.timeout = 1;

			_mockIntraband.addResponseWaitingDatagram(requestDatagram2);

			Assert.assertEquals(2, responseWaitingMap.size());
			Assert.assertSame(
				requestDatagram1, responseWaitingMap.get(sequenceId));
			Assert.assertSame(
				requestDatagram2, responseWaitingMap.get(sequenceId + 1));
			Assert.assertEquals(2, timeoutSequenceIds.size());
			Assert.assertTrue(timeoutSequenceIds.contains(sequenceId));
			Assert.assertTrue(timeoutSequenceIds.contains(sequenceId + 1));

			Thread.sleep(10);

			_mockIntraband.cleanUpTimeoutResponseWaitingDatagrams();

			Assert.assertEquals(2, logRecords.size());

			assertMessageStartWith(
				logRecords.get(0),
				"Removed timeout response waiting datagram ");
			assertMessageStartWith(
				logRecords.get(1),
				"Removed timeout response waiting datagram ");

			recordCompletionHandler1.waitUntilTimeouted();
			recordCompletionHandler2.waitUntilTimeouted();

			// Clean up timeout, hit, without log

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			requestDatagram1 = Datagram.createRequestDatagram(_type, _data);

			requestDatagram1.setSequenceId(sequenceId);

			recordCompletionHandler1 = new RecordCompletionHandler<Object>();

			requestDatagram1.completionHandler = recordCompletionHandler1;

			requestDatagram1.timeout = 1;

			_mockIntraband.addResponseWaitingDatagram(requestDatagram1);

			Thread.sleep(10);

			requestDatagram2 = Datagram.createRequestDatagram(_type, _data);

			requestDatagram2.setSequenceId(sequenceId + 1);

			recordCompletionHandler2 = new RecordCompletionHandler<Object>();

			requestDatagram2.completionHandler = recordCompletionHandler2;

			requestDatagram2.timeout = 1;

			_mockIntraband.addResponseWaitingDatagram(requestDatagram2);

			Assert.assertEquals(2, responseWaitingMap.size());
			Assert.assertSame(
				requestDatagram1, responseWaitingMap.get(sequenceId));
			Assert.assertSame(
				requestDatagram2, responseWaitingMap.get(sequenceId + 1));
			Assert.assertEquals(2, timeoutSequenceIds.size());
			Assert.assertTrue(timeoutSequenceIds.contains(sequenceId));
			Assert.assertTrue(timeoutSequenceIds.contains(sequenceId + 1));

			Thread.sleep(10);

			_mockIntraband.cleanUpTimeoutResponseWaitingDatagrams();

			Assert.assertTrue(logRecords.isEmpty());

			recordCompletionHandler1.waitUntilTimeouted();
			recordCompletionHandler2.waitUntilTimeouted();

			// Clean up timeout, miss

			_mockIntraband.cleanUpTimeoutResponseWaitingDatagrams();
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testSendDatagramWithCallback() {

		// Registration reference is null

		try {
			_mockIntraband.sendDatagram(null, null, null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Registration reference is null", npe.getMessage());
		}

		// Registration reference is invalid

		try {
			RegistrationReference registrationReference =
				new MockRegistrationReference(_mockIntraband);

			registrationReference.cancelRegistration();

			_mockIntraband.sendDatagram(
				registrationReference, null, null, null, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Registration reference is invalid", iae.getMessage());
		}

		// Datagram is null

		try {
			_mockIntraband.sendDatagram(
				new MockRegistrationReference(_mockIntraband), null, null, null,
				null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// Completion type set is null

		try {
			_mockIntraband.sendDatagram(
				new MockRegistrationReference(_mockIntraband),
				Datagram.createRequestDatagram(_type, _data), null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Completion type set is null", npe.getMessage());
		}

		// Completion type set is empty

		try {
			_mockIntraband.sendDatagram(
				new MockRegistrationReference(_mockIntraband),
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
			_mockIntraband.sendDatagram(
				new MockRegistrationReference(_mockIntraband),
				Datagram.createRequestDatagram(_type, _data), null,
				EnumSet.of(CompletionHandler.CompletionType.SUBMITTED), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Complete handler is null", npe.getMessage());
		}

		// Time unit is null

		try {
			_mockIntraband.sendDatagram(
				new MockRegistrationReference(_mockIntraband),
				Datagram.createRequestDatagram(_type, _data), null,
				EnumSet.of(CompletionHandler.CompletionType.SUBMITTED),
				new RecordCompletionHandler<Object>(), 1000, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Time unit is null", npe.getMessage());
		}

		// Nonpositive timeout

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		_mockIntraband.sendDatagram(
			new MockRegistrationReference(_mockIntraband), requestDatagram,
			null, EnumSet.of(CompletionHandler.CompletionType.DELIVERED),
			new RecordCompletionHandler<Object>(), 0, TimeUnit.MILLISECONDS);

		Datagram sentDatagram = _mockIntraband.getDatagram();

		Assert.assertEquals(_DEFAULT_TIMEOUT, sentDatagram.timeout);

		Map<Long, Datagram> responseWaitingMap =
			_mockIntraband.responseWaitingMap;

		Assert.assertEquals(1, responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram,
			responseWaitingMap.remove(requestDatagram.getSequenceId()));

		Map<Long, Long> timeoutMap = _mockIntraband.timeoutMap;

		Collection<Long> timeoutSequenceIds = timeoutMap.values();

		Assert.assertEquals(1, timeoutSequenceIds.size());
		Assert.assertTrue(
			timeoutSequenceIds.remove(requestDatagram.getSequenceId()));

		// Covert timeout

		_mockIntraband.sendDatagram(
			new MockRegistrationReference(_mockIntraband), requestDatagram,
			null, EnumSet.of(CompletionHandler.CompletionType.REPLIED),
			new RecordCompletionHandler<Object>(), 2, TimeUnit.SECONDS);

		sentDatagram = _mockIntraband.getDatagram();

		Assert.assertEquals(2000, sentDatagram.timeout);
		Assert.assertEquals(1, responseWaitingMap.size());
		Assert.assertSame(
			requestDatagram,
			responseWaitingMap.remove(requestDatagram.getSequenceId()));
		Assert.assertEquals(1, timeoutSequenceIds.size());
		Assert.assertTrue(
			timeoutSequenceIds.remove(requestDatagram.getSequenceId()));

		// Default timeout

		_mockIntraband.sendDatagram(
			new MockRegistrationReference(_mockIntraband), requestDatagram,
			null, EnumSet.of(CompletionHandler.CompletionType.SUBMITTED),
			new RecordCompletionHandler<Object>());

		sentDatagram = _mockIntraband.getDatagram();

		Assert.assertEquals(_DEFAULT_TIMEOUT, sentDatagram.timeout);
	}

	@Test
	public void testSendDatagramWithoutCallback() {

		// Registration reference is null

		try {
			_mockIntraband.sendDatagram(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Registration reference is null", npe.getMessage());
		}

		// Registration reference is invalid

		try {
			RegistrationReference registrationReference =
				new MockRegistrationReference(_mockIntraband);

			registrationReference.cancelRegistration();

			_mockIntraband.sendDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Registration reference is invalid", iae.getMessage());
		}

		// Datagram is null

		try {
			_mockIntraband.sendDatagram(
				new MockRegistrationReference(_mockIntraband), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// Normal send

		Datagram datagram = Datagram.createRequestDatagram(_type, _data);

		RegistrationReference registrationReference =
			new MockRegistrationReference(_mockIntraband);

		_mockIntraband.sendDatagram(registrationReference, datagram);

		Assert.assertSame(
			registrationReference, _mockIntraband.getRegistrationReference());
		Assert.assertSame(datagram, _mockIntraband.getDatagram());
	}

	@Test
	public void testSendSyncDatagram() throws Exception {

		// Registration reference is null

		try {
			_mockIntraband.sendSyncDatagram(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Registration reference is null", npe.getMessage());
		}

		// Registration reference is invalid

		try {
			RegistrationReference registrationReference =
				new MockRegistrationReference(_mockIntraband);

			registrationReference.cancelRegistration();

			_mockIntraband.sendSyncDatagram(registrationReference, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Registration reference is invalid", iae.getMessage());
		}

		// Datagram is null

		try {
			_mockIntraband.sendSyncDatagram(
				new MockRegistrationReference(_mockIntraband), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Datagram is null", npe.getMessage());
		}

		// Time unit is null

		try {
			_mockIntraband.sendSyncDatagram(
				new MockRegistrationReference(_mockIntraband),
				Datagram.createRequestDatagram(_type, _data), 1000, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Time unit is null", npe.getMessage());
		}

		// Nonpositive timeout

		try {
			_mockIntraband.sendSyncDatagram(
				new MockRegistrationReference(_mockIntraband),
				Datagram.createRequestDatagram(_type, _data), 0,
				TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
			Assert.assertEquals("Result waiting timeout", te.getMessage());
		}

		Assert.assertEquals(
			_DEFAULT_TIMEOUT, _mockIntraband.getDatagram().timeout);

		// Covert timeout

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		try {
			_mockIntraband.sendSyncDatagram(
				new MockRegistrationReference(_mockIntraband), requestDatagram,
				2, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
			Assert.assertEquals("Result waiting timeout", te.getMessage());
		}

		Assert.assertEquals(2000, requestDatagram.timeout);

		// Datagram writing IOException

		IOException ioException = new IOException();

		_mockIntraband.setIOException(ioException);

		try {
			_mockIntraband.sendSyncDatagram(
				new MockRegistrationReference(_mockIntraband),
				Datagram.createRequestDatagram(_type, _data));

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertSame(ioException, ioe);
		}
		finally {
			_mockIntraband.setIOException(null);
		}

		// Replied

		final Datagram expectedDatagram = Datagram.createResponseDatagram(
			requestDatagram, _data);

		Intraband intraband = new MockIntraband(_DEFAULT_TIMEOUT) {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				return expectedDatagram;
			}

		};

		Datagram responseDatagram = intraband.sendSyncDatagram(
			new MockRegistrationReference(_mockIntraband), requestDatagram);

		Assert.assertSame(expectedDatagram, responseDatagram);

		SendSyncDatagramCompletionHandler sendSyncDatagramCompletionHandler =
			new SendSyncDatagramCompletionHandler();

		sendSyncDatagramCompletionHandler.delivered(null);
		sendSyncDatagramCompletionHandler.submitted(null);
		sendSyncDatagramCompletionHandler.timedOut(null);
	}

	protected void assertMessageStartWith(
		LogRecord logRecord, String messagePrefix) {

		String message = logRecord.getMessage();

		Assert.assertTrue(message.startsWith(messagePrefix));
	}

	private static final String _DATA_STRING =
		BaseIntrabandTest.class.getName();

	private static final long _DEFAULT_TIMEOUT = Time.SECOND;

	private byte[] _data = _DATA_STRING.getBytes(Charset.defaultCharset());
	private MockIntraband _mockIntraband = new MockIntraband(_DEFAULT_TIMEOUT);
	private byte _type = 1;

	private static class MockGatheringByteChannel
		implements GatheringByteChannel {

		@Override
		public void close() throws IOException {
			throw new IOException();
		}

		@Override
		public boolean isOpen() {
			return true;
		}

		@Override
		public int write(ByteBuffer byteBuffer) throws IOException {
			throw new IOException();
		}

		@Override
		public long write(ByteBuffer[] byteBuffers) throws IOException {
			throw new IOException();
		}

		@Override
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

		@Override
		public void close() throws IOException {
			throw new IOException();
		}

		@Override
		public boolean isOpen() {
			return true;
		}

		@Override
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

		@Override
		public long read(ByteBuffer[] byteBuffers) {
			return -1;
		}

		@Override
		public long read(ByteBuffer[] byteBuffers, int offset, int length) {
			return -1;
		}

		private final boolean _eofOnDataBufferReading;

	}

}