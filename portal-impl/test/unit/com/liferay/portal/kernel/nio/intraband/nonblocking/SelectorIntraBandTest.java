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

package com.liferay.portal.kernel.nio.intraband.nonblocking;

import com.liferay.portal.kernel.nio.intraband.BaseIntraBand;
import com.liferay.portal.kernel.nio.intraband.BaseIntraBandHelper;
import com.liferay.portal.kernel.nio.intraband.ChannelContext;
import com.liferay.portal.kernel.nio.intraband.ClosedIntraBandException;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.IntraBandTestUtil;
import com.liferay.portal.kernel.nio.intraband.RecordCompletionHandler;
import com.liferay.portal.kernel.nio.intraband.RecordDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

import java.util.EnumSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class SelectorIntraBandTest {

	public static final long DEFAULT_TIMEOUT = 1000;

	@Before
	public void setUp() throws Exception {
		_selectorIntraBand = new SelectorIntraBand(DEFAULT_TIMEOUT);
	}

	@After
	public void tearDown() throws Exception {
		_selectorIntraBand.close();
	}

	@Test
	public void testCreationAndDestroy() throws Exception {

		// 1) Close selector causing polling thread exit, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SelectorIntraBand.class.getName(), Level.INFO);

		Thread wakeupThread = new Thread(
			new WakeupRunnable(_selectorIntraBand));

		wakeupThread.start();

		Thread pollingThread = _selectorIntraBand.pollingThread;

		Selector selector = _selectorIntraBand.selector;

		synchronized (selector) {
			wakeupThread.interrupt();
			wakeupThread.join();

			while (pollingThread.getState() != Thread.State.BLOCKED);

			selector.close();
		}

		pollingThread.join();

		Assert.assertEquals(1, logRecords.size());
		Assert.assertEquals(
			pollingThread.getName().concat(
				" exiting gracefully on Selector closure"),
			logRecords.get(0).getMessage());

		// 2) Close selector causing polling thread exit, without log

		_selectorIntraBand = new SelectorIntraBand(1000);

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SelectorIntraBand.class.getName(), Level.OFF);

		wakeupThread = new Thread(new WakeupRunnable(_selectorIntraBand));

		wakeupThread.start();

		pollingThread = _selectorIntraBand.pollingThread;

		selector = _selectorIntraBand.selector;

		synchronized (selector) {
			wakeupThread.interrupt();
			wakeupThread.join();

			while (pollingThread.getState() != Thread.State.BLOCKED);

			selector.close();
		}

		pollingThread.join();

		Assert.assertTrue(logRecords.isEmpty());
	}

	@AdviseWith(adviceClasses = {Jdk14LogImplAdvice.class})
	@Test
	public void testReceiveDatagram() throws Exception {
		Pipe inputPipe = Pipe.open();

		SourceChannel bandSourceChannel = inputPipe.source();
		SinkChannel blockingSinkChannel = inputPipe.sink();

		Pipe outputPipe = Pipe.open();

		SourceChannel blockingSourceChannel = outputPipe.source();
		SinkChannel bandSinkChannel = outputPipe.sink();

		SelectionKeyRegistrationReference registrationReference =
			(SelectionKeyRegistrationReference)
				_selectorIntraBand.registerChannel(
					bandSourceChannel, bandSinkChannel);

		byte type = 10;

		byte[] dataContent = "This is a test content".getBytes(StringPool.UTF8);

		long sequenceId = 100;

		// 1) Receive ACK Response, no ACK Request, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Jdk14LogImplAdvice.reset();

		try {
			Datagram ackResponseDatagram =
				DatagramHelper.createACKResponseDatagram(sequenceId);

			DatagramHelper.writeTo(ackResponseDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped ownerless ACK response "));

		// 2) Receive ACK Response, no ACK Request, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		Jdk14LogImplAdvice.reset();

		try {
			Datagram ackResponseDatagram =
				DatagramHelper.createACKResponseDatagram(sequenceId);

			DatagramHelper.writeTo(ackResponseDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// 3) Receive ACK Response, with ACK Request

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();

		Datagram requestDatagram = Datagram.createRequestDatagram(
			type, dataContent);

		DatagramHelper.setAttachment(requestDatagram, new Object());
		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		Datagram ackResponseDatagram = DatagramHelper.createACKResponseDatagram(
			sequenceId);

		DatagramHelper.writeTo(ackResponseDatagram, blockingSinkChannel);

		recordCompletionHandler.waitUntilDelivered();

		Assert.assertSame(
			DatagramHelper.getAttachment(requestDatagram),
			recordCompletionHandler.getAttachment());

		// 4) Receive Response, no Request, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Jdk14LogImplAdvice.reset();

		try {
			Datagram responseDatagram = Datagram.createResponseDatagram(
				requestDatagram, dataContent);

			DatagramHelper.writeTo(responseDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped ownerless response "));

		// 5) Receive Response, no Request, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		Jdk14LogImplAdvice.reset();

		try {
			requestDatagram = Datagram.createRequestDatagram(type, dataContent);
			DatagramHelper.setSequenceId(requestDatagram, sequenceId);

			Datagram responseDatagram = Datagram.createResponseDatagram(
				requestDatagram, dataContent);

			DatagramHelper.writeTo(responseDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// 6) Receive Response, with Request, with REPLIED CompletionHandler

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(type, dataContent);

		DatagramHelper.setAttachment(requestDatagram, new Object());
		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);
		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.of(CompletionType.REPLIED));
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		Datagram responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, dataContent);

		DatagramHelper.writeTo(responseDatagram, blockingSinkChannel);

		recordCompletionHandler.waitUntilReplied();

		Assert.assertSame(
			DatagramHelper.getAttachment(requestDatagram),
			recordCompletionHandler.getAttachment());

		// 7) Receive Response, with Request, without REPLIED CompletionHandler,
		// with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(type, dataContent);
		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.noneOf(CompletionType.class));
		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, dataContent);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(responseDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped unconcerned response "));

		// 8) Receive Response, with Request, without REPLIED CompletionHandler,
		// without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		recordCompletionHandler = new RecordCompletionHandler<Object>();
		requestDatagram = Datagram.createRequestDatagram(type, dataContent);
		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.noneOf(CompletionType.class));
		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		responseDatagram = Datagram.createResponseDatagram(
			requestDatagram, dataContent);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(responseDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// 9) Receive Request requires ACK, no DatagramReceiveHandler, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		requestDatagram = Datagram.createRequestDatagram(type, dataContent);

		DatagramHelper.setAckRequest(requestDatagram);
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		ackResponseDatagram = _readDatagramFully(blockingSourceChannel);

		Assert.assertEquals(
			sequenceId, DatagramHelper.getSequenceId(ackResponseDatagram));
		Assert.assertTrue(DatagramHelper.isAckResponse(ackResponseDatagram));
		Assert.assertEquals(0, ackResponseDatagram.getData().capacity());

		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Dropped ownerless Datagram "));

		// 10) Receive Request, no DatagramReceiveHandler, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram = Datagram.createRequestDatagram(type, dataContent);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// 11) Receive Request, with DatagramReceiveHandler,

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.SEVERE);

		requestDatagram = Datagram.createRequestDatagram(type, dataContent);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		RecordDatagramReceiveHandler recordDatagramReceiveHandler =
			new RecordDatagramReceiveHandler();

		_selectorIntraBand.registerDatagramReceiveHandler(
			type, recordDatagramReceiveHandler);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, blockingSinkChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilErrorCalled();
		}

		Datagram receiveDatagram =
			recordDatagramReceiveHandler.getReceiveDatagram();

		Assert.assertEquals(
			sequenceId, DatagramHelper.getSequenceId(receiveDatagram));
		Assert.assertEquals(type, receiveDatagram.getType());
		Assert.assertArrayEquals(
			dataContent, receiveDatagram.getData().array());

		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith("Dispatching failure."));

		_unregisterChannels(registrationReference);

		bandSourceChannel.close();
		blockingSinkChannel.close();
		blockingSourceChannel.close();
		bandSinkChannel.close();
	}

	@Test
	public void testRegisterChannelDuplex() throws Exception {

		// 1) Duplex channel is null

		try {
			_selectorIntraBand.registerChannel(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Duplex SelectableChannel is null", npe.getMessage());
		}

		// 2) Duplex channel is not type of ScatteringByteChannel

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(Channel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Duplex Channel is not type of ScatteringByteChannel",
				iae.getMessage());
		}

		// 3) Duplex channel is not type of GatheringByteChannel

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(
					ScatteringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Duplex Channel is not type of GatheringByteChannel",
				iae.getMessage());
		}

		// 4) Duplex channel is not selectable

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(
					ScatteringByteChannel.class, GatheringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Duplex Channel is not type of SelectableChannel",
				iae.getMessage());
		}

		// 5) Duplex channel is not readable

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(false, true));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Duplex SelectableChannel is not valid for reading",
				iae.getMessage());
		}

		// 6) Duplex channel is not writable

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(true, false));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Duplex SelectableChannel is not valid for writing",
				iae.getMessage());
		}

		SocketChannel[] socketChannelPeers =
			IntraBandTestUtil.createSocketChannelPeers();

		try {
			SocketChannel socketChannel = socketChannelPeers[0];

			// 7) Interruptted on register waiting

			final Thread mainThread = Thread.currentThread();

			Thread wakeupThread = new Thread(
				new WakeupRunnable(_selectorIntraBand));

			Thread interruptThread = new Thread() {

				public void run() {

					// Busy waiting main thread to start waiting on registering

					while (mainThread.getState() != Thread.State.WAITING);

					// Interrup registering

					mainThread.interrupt();
				}

			};

			wakeupThread.start();

			synchronized (_selectorIntraBand.selector) {
				wakeupThread.interrupt();
				wakeupThread.join();

				interruptThread.start();

				try {
					_selectorIntraBand.registerChannel(socketChannel);

					Assert.fail();
				}
				catch (IOException ioe) {
					Throwable cause = ioe.getCause();

					Assert.assertTrue(cause instanceof InterruptedException);
				}

				interruptThread.join();
			}

			// 8) Normal register

			SelectionKeyRegistrationReference registrationReference =
				(SelectionKeyRegistrationReference)
					_selectorIntraBand.registerChannel(socketChannel);

			Assert.assertNotNull(registrationReference);

			Assert.assertSame(
				registrationReference.readSelectionKey,
				registrationReference.writeSelectionKey);

			Assert.assertTrue(registrationReference.readSelectionKey.isValid());
			Assert.assertEquals(
				SelectionKey.OP_READ | SelectionKey.OP_WRITE,
				registrationReference.readSelectionKey.interestOps());
			Assert.assertNotNull(
				registrationReference.readSelectionKey.attachment());

			// 9) Register after close

			_selectorIntraBand.close();

			try {
				_selectorIntraBand.registerChannel(socketChannel);

				Assert.fail();
			}
			catch (ClosedIntraBandException cibe) {
			}
		}
		finally {
			socketChannelPeers[0].close();
			socketChannelPeers[1].close();
		}
	}

	@Test
	public void testRegisterChannelReadWrite() throws Exception {

		// 1) ScatteringByteChannel is null

		try {
			_selectorIntraBand.registerChannel(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"ScatteringByteChannel is null", npe.getMessage());
		}

		// 2) GatheringByteChannel is null

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<ScatteringByteChannel>createProxy(
					ScatteringByteChannel.class), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"GatheringByteChannel is null", npe.getMessage());
		}

		// 3) ScatteringByteChannel is not selectable

		try {
			_selectorIntraBand.registerChannel(
				IntraBandTestUtil.<ScatteringByteChannel>createProxy(
					ScatteringByteChannel.class),
				IntraBandTestUtil.<GatheringByteChannel>createProxy(
					GatheringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"ScatteringByteChannel is not type of SelectableChannel",
				iae.getMessage());
		}

		// 4) GatheringByteChannel is not selectable

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(false, false),
				IntraBandTestUtil.<GatheringByteChannel>createProxy(
					GatheringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"GatheringByteChannel is not type of SelectableChannel",
				iae.getMessage());
		}

		// 5) Read channel is not readable

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(false, true),
				new MockDuplexSelectableChannel(true, true));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Read SelectableChannel is not valid for reading",
				iae.getMessage());
		}

		// 6) Write channel is not writable

		try {
			_selectorIntraBand.registerChannel(
				new MockDuplexSelectableChannel(true, true),
				new MockDuplexSelectableChannel(true, false));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Write SelectableChannel is not valid for writing",
				iae.getMessage());
		}

		// 7) Interruptted on register waiting

		Pipe pipe = Pipe.open();

		SourceChannel sourceChannel = pipe.source();
		SinkChannel sinkChannel = pipe.sink();

		final Thread mainThread = Thread.currentThread();

		Thread wakeupThread = new Thread(
			new WakeupRunnable(_selectorIntraBand));

		Thread interruptThread = new Thread() {

			public void run() {

				// Busy waiting main thread to start waiting on registering

				while (mainThread.getState() != Thread.State.WAITING);

				// Interrup registering

				mainThread.interrupt();
			}

		};

		wakeupThread.start();

		synchronized (_selectorIntraBand.selector) {
			wakeupThread.interrupt();
			wakeupThread.join();

			interruptThread.start();

			try {
				_selectorIntraBand.registerChannel(sourceChannel, sinkChannel);

				Assert.fail();
			}
			catch (IOException ioe) {
				Throwable cause = ioe.getCause();

				Assert.assertTrue(cause instanceof InterruptedException);
			}

			interruptThread.join();
		}

		// 8) Normal register

		SelectionKeyRegistrationReference registrationReference =
			(SelectionKeyRegistrationReference)
				_selectorIntraBand.registerChannel(sourceChannel, sinkChannel);

		Assert.assertNotNull(registrationReference);

		Assert.assertTrue(registrationReference.readSelectionKey.isValid());
		Assert.assertEquals(
			SelectionKey.OP_READ,
			registrationReference.readSelectionKey.interestOps());
		Assert.assertNotNull(
			registrationReference.readSelectionKey.attachment());

		Assert.assertTrue(registrationReference.writeSelectionKey.isValid());
		Assert.assertEquals(
			SelectionKey.OP_WRITE,
			registrationReference.writeSelectionKey.interestOps());
		Assert.assertNotNull(
			registrationReference.writeSelectionKey.attachment());

		Assert.assertSame(
			registrationReference.readSelectionKey.attachment(),
			registrationReference.writeSelectionKey.attachment());

		_unregisterChannels(registrationReference);

		// 9) Register after close

		_selectorIntraBand.close();

		try {
			_selectorIntraBand.registerChannel(sourceChannel, sinkChannel);

			Assert.fail();
		}
		catch (ClosedIntraBandException cibe) {
		}

		sourceChannel.close();
		sinkChannel.close();
	}

	@AdviseWith(adviceClasses={Jdk14LogImplAdvice.class})
	@Test
	public void testSendDatagramWithCallback() throws Exception {
		Pipe inputPipe = Pipe.open();

		SourceChannel bandSourceChannel = inputPipe.source();
		SinkChannel blockingSinkChannel = inputPipe.sink();

		Pipe outputPipe = Pipe.open();

		SourceChannel blockingSourceChannel = outputPipe.source();
		SinkChannel bandSinkChannel = outputPipe.sink();

		RegistrationReference registrationReference =
			_selectorIntraBand.registerChannel(
				bandSourceChannel, bandSinkChannel);

		byte type = 10;

		byte[] dataContent = "Datagram Sending Test".getBytes(StringPool.UTF8);

		Object attachment = new Object();

		// 1) Submitted callback

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();

		Datagram requestDatagram = Datagram.createRequestDatagram(
			type, dataContent);

		_selectorIntraBand.sendDatagram(
			registrationReference, requestDatagram, attachment,
			EnumSet.of(CompletionType.SUBMITTED), recordCompletionHandler);

		Datagram receiveDatagram = _readDatagramFully(blockingSourceChannel);

		recordCompletionHandler.waitUntilSubmitted();

		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());

		Assert.assertEquals(type, receiveDatagram.getType());
		Assert.assertArrayEquals(
			dataContent, receiveDatagram.getData().array());

		// 2) Callback timeout with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		requestDatagram = Datagram.createRequestDatagram(type, dataContent);

		_selectorIntraBand.sendDatagram(
			registrationReference, requestDatagram, attachment,
			EnumSet.of(CompletionType.DELIVERED), recordCompletionHandler, 10,
			TimeUnit.MILLISECONDS);

		recordCompletionHandler.waitUntilTimeouted(_selectorIntraBand.selector);

		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());

		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				"Removed timeout response waiting Datagram"));

		// 3) Callback timeout without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		requestDatagram = Datagram.createRequestDatagram(type, dataContent);

		_selectorIntraBand.sendDatagram(
			registrationReference, requestDatagram, attachment,
			EnumSet.of(CompletionType.DELIVERED), recordCompletionHandler, 10,
			TimeUnit.MILLISECONDS);

		recordCompletionHandler.waitUntilTimeouted(_selectorIntraBand.selector);

		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());

		Assert.assertTrue(logRecords.isEmpty());

		// 4) Callback timeout, CompletionHandler throws a NPE kills poller

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SelectorIntraBand.class.getName(), Level.SEVERE);

		recordCompletionHandler = new RecordCompletionHandler<Object>() {

			@Override
			public void timeouted(Object attachment) {
				super.timeouted(attachment);

				throw new NullPointerException();
			}

		};

		Jdk14LogImplAdvice.reset();

		try {
			requestDatagram = Datagram.createRequestDatagram(type, dataContent);

			_selectorIntraBand.sendDatagram(
				registrationReference, requestDatagram, attachment,
				EnumSet.of(CompletionType.DELIVERED), recordCompletionHandler,
				10, TimeUnit.MILLISECONDS);
		}
		finally {
			recordCompletionHandler.waitUntilTimeouted(
				_selectorIntraBand.selector);
			Jdk14LogImplAdvice.waitUntilErrorCalled();
		}

		Assert.assertEquals(1, logRecords.size());
		Assert.assertTrue(
			logRecords.get(0).getMessage().startsWith(
				SelectorIntraBand.class.getSimpleName().concat(
					"-SelectorPoller-1 exiting exceptionally")));

		Assert.assertFalse(_selectorIntraBand.selector.isOpen());

		bandSourceChannel.close();
		blockingSinkChannel.close();
		blockingSourceChannel.close();
		bandSinkChannel.close();
	}

	@Test
	public void testSendDatagramWithoutCallback() throws Exception {
		byte type = 1;

		byte[] dataContent1 = "Datagram Sending Test 1".getBytes(
			StringPool.UTF8);
		byte[] dataContent2 = "Datagram Sending Test 2".getBytes(
			StringPool.UTF8);

		Pipe inputPipe = Pipe.open();

		SourceChannel bandSourceChannel = inputPipe.source();
		SinkChannel blockingSinkChannel = inputPipe.sink();

		Pipe outputPipe = Pipe.open();

		SourceChannel blockingSourceChannel = outputPipe.source();
		SinkChannel bandSinkChannel = outputPipe.sink();

		SelectionKeyRegistrationReference registrationReference =
			(SelectionKeyRegistrationReference)
				_selectorIntraBand.registerChannel(
					bandSourceChannel, bandSinkChannel);

		// 1) Single Datagram sending

		Datagram requestDatagram = Datagram.createRequestDatagram(
			type, dataContent1);

		Thread wakeupThread = new Thread(
			new WakeupRunnable(_selectorIntraBand));

		wakeupThread.start();

		synchronized (_selectorIntraBand.selector) {
			wakeupThread.interrupt();
			wakeupThread.join();

			// Submit a sending, it should be enqueued, but not develived, as
			// this block is holding selector monitor

			_selectorIntraBand.sendDatagram(
				registrationReference, requestDatagram);

			ChannelContext channelContext =
				(ChannelContext)
					registrationReference.writeSelectionKey.attachment();

			Assert.assertEquals(1, channelContext.getSendingQueue().size());
			Assert.assertSame(
				requestDatagram, channelContext.getSendingQueue().peek());
		}

		Datagram receiveDatagram = _readDatagramFully(blockingSourceChannel);

		Assert.assertEquals(type, receiveDatagram.getType());
		Assert.assertArrayEquals(
			dataContent1, receiveDatagram.getData().array());

		// 2) Two Datagrams continuous sending

		Datagram requestDatagram1 = Datagram.createRequestDatagram(
			type, dataContent1);
		Datagram requestDatagram2 = Datagram.createRequestDatagram(
			type, dataContent2);

		wakeupThread = new Thread(new WakeupRunnable(_selectorIntraBand));

		wakeupThread.start();

		synchronized (_selectorIntraBand.selector) {
			wakeupThread.interrupt();
			wakeupThread.join();

			// Submit two sendings, they should be enqueued, but not develived,
			// as this block is holding selector monitor

			_selectorIntraBand.sendDatagram(
				registrationReference, requestDatagram1);
			_selectorIntraBand.sendDatagram(
				registrationReference, requestDatagram2);

			ChannelContext channelContext =
				(ChannelContext)
					registrationReference.writeSelectionKey.attachment();

			Assert.assertEquals(2, channelContext.getSendingQueue().size());
			Datagram[] datagrams = channelContext.getSendingQueue().toArray(
				new Datagram[2]);

			Assert.assertSame(requestDatagram1, datagrams[0]);
			Assert.assertSame(requestDatagram2, datagrams[1]);
		}

		Datagram receiveDatagram1 = _readDatagramFully(blockingSourceChannel);
		Datagram receiveDatagram2 = _readDatagramFully(blockingSourceChannel);

		Assert.assertEquals(type, receiveDatagram1.getType());
		Assert.assertArrayEquals(
			dataContent1, receiveDatagram1.getData().array());
		Assert.assertEquals(type, receiveDatagram2.getType());
		Assert.assertArrayEquals(
			dataContent2, receiveDatagram2.getData().array());

		// 3) Two Datagrams delay sending

		requestDatagram1 = Datagram.createRequestDatagram(type, dataContent1);
		requestDatagram2 = Datagram.createRequestDatagram(type, dataContent2);

		wakeupThread = new Thread(new WakeupRunnable(_selectorIntraBand));

		wakeupThread.start();

		SelectionKey writeSelectionKey =
			registrationReference.writeSelectionKey;

		ChannelContext channelContext =
			(ChannelContext)writeSelectionKey.attachment();

		Queue<Datagram> sendingQueue = channelContext.getSendingQueue();

		synchronized (writeSelectionKey) {
			synchronized (_selectorIntraBand.selector) {
				wakeupThread.interrupt();
				wakeupThread.join();

				// Submit a sending, it should be enqueued, but not develived,
				// as this block is holding selector monitor

				_selectorIntraBand.sendDatagram(
					registrationReference, requestDatagram1);

				Assert.assertEquals(1, sendingQueue.size());
				Assert.assertSame(requestDatagram1, sendingQueue.peek());
			}

			receiveDatagram1 = _readDatagramFully(blockingSourceChannel);
			Assert.assertEquals(type, receiveDatagram1.getType());
			Assert.assertArrayEquals(
				dataContent1, receiveDatagram1.getData().array());

			// Wait until polling thread is blocking on writeSelectionKey

			while (
				_selectorIntraBand.pollingThread.getState() ==
					Thread.State.RUNNABLE);

			_selectorIntraBand.sendDatagram(
				registrationReference, requestDatagram2);

			Assert.assertEquals(1, sendingQueue.size());
			Assert.assertSame(requestDatagram2, sendingQueue.peek());
		}

		receiveDatagram2 = _readDatagramFully(blockingSourceChannel);

		Assert.assertEquals(type, receiveDatagram2.getType());
		Assert.assertArrayEquals(
			dataContent2, receiveDatagram2.getData().array());

		// 4) Huge Datagram sending

		int hugeBufferSize = 1024 * 1024 * 10;

		ByteBuffer hugeBuffer = ByteBuffer.allocate(hugeBufferSize);

		for (int i = 0; i < hugeBufferSize; i++) {
			hugeBuffer.put(i, (byte)i);
		}

		Datagram hugeDatagram = Datagram.createRequestDatagram(
			type, hugeBuffer);

		_selectorIntraBand.sendDatagram(registrationReference, hugeDatagram);

		receiveDatagram = DatagramHelper.createReceiveDatagram();

		channelContext =
			(ChannelContext)
				registrationReference.writeSelectionKey.attachment();

		int count = 0;

		while (!DatagramHelper.readFrom(
			receiveDatagram, blockingSourceChannel)) {

			count++;
		}

		Assert.assertTrue(count > 0);
		Assert.assertTrue(channelContext.getSendingQueue().isEmpty());

		Assert.assertArrayEquals(
			hugeBuffer.array(), receiveDatagram.getData().array());

		_unregisterChannels(registrationReference);

		bandSourceChannel.close();
		blockingSinkChannel.close();
		blockingSourceChannel.close();
		bandSinkChannel.close();
	}

	@Aspect
	public static class DatagramAdvice {

		public static void reset(
			boolean readIOException, boolean writeIOException) {

			_readFromCalledCountDownLatch = new CountDownLatch(1);
			_writeToCalledCountDownLatch = new CountDownLatch(1);
			_readIOException = readIOException;
			_writeIOException = writeIOException;
		}

		public static void waitUntilReadFromCalled()
			throws InterruptedException {

			_readFromCalledCountDownLatch.await();
		}

		public static void waitUntilWriteToCalled()
			throws InterruptedException {

			_writeToCalledCountDownLatch.await();
		}

		@Around(
			"execution(* " +
			"com.liferay.portal.kernel.nio.intraband.Datagram." +
			"readFrom(java.nio.channels.ScatteringByteChannel))")
		public Object readFrom(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			_readFromCalledCountDownLatch.countDown();

			if (_readIOException) {
				throw new IOException("Force read to fail");
			}
			else {
				return proceedingJoinPoint.proceed();
			}
		}

		@Around(
			"execution(* " +
			"com.liferay.portal.kernel.nio.intraband.Datagram." +
			"writeTo(java.nio.channels.GatheringByteChannel))")
		public Object writeTo(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			_writeToCalledCountDownLatch.countDown();

			if (_writeIOException) {
				throw new IOException("Force write to fail");
			}
			else {
				return proceedingJoinPoint.proceed();
			}
		}

		private static volatile CountDownLatch _readFromCalledCountDownLatch =
			new CountDownLatch(1);
		private static volatile boolean _readIOException;
		private static volatile boolean _writeIOException;
		private static volatile CountDownLatch _writeToCalledCountDownLatch =
			new CountDownLatch(1);

	}

	@Aspect
	public static class Jdk14LogImplAdvice {

		public static void reset() {
			_errorCalledCountDownLatch = new CountDownLatch(1);
			_isWarnEnabledCalledCountDownLatch = new CountDownLatch(1);
			_warnCalledCountDownLatch = new CountDownLatch(1);
		}

		public static void waitUntilErrorCalled() throws InterruptedException {
			_errorCalledCountDownLatch.await();
		}

		public static void waitUntilIsWarnEnableCalled()
			throws InterruptedException {

			_isWarnEnabledCalledCountDownLatch.await();
		}

		public static void waitUntilWarnCalled() throws InterruptedException {
			_warnCalledCountDownLatch.await();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* " +
			"com.liferay.portal.kernel.log.Jdk14LogImpl." +
			"error(Object, Throwable))")
		public void error() {
			_errorCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* " +
			"com.liferay.portal.kernel.log.Jdk14LogImpl.isWarnEnabled())")
		public void isWarnEnabled() {
			_isWarnEnabledCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* " +
			"com.liferay.portal.kernel.log.Jdk14LogImpl.warn(Object))")
		public void warn1() {
			_warnCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* " +
			"com.liferay.portal.kernel.log.Jdk14LogImpl." +
			"warn(Object, Throwable))")
		public void warn2() {
			_warnCalledCountDownLatch.countDown();
		}

		public static volatile CountDownLatch _errorCalledCountDownLatch =
			new CountDownLatch(1);
		public static volatile CountDownLatch
			_isWarnEnabledCalledCountDownLatch = new CountDownLatch(1);
		public static volatile CountDownLatch
			_warnCalledCountDownLatch = new CountDownLatch(1);

	}

	private Datagram _readDatagramFully(
			ScatteringByteChannel scatteringByteChannel)
		throws IOException {

		Datagram datagram = DatagramHelper.createReceiveDatagram();

		while (!DatagramHelper.readFrom(datagram, scatteringByteChannel));

		return datagram;
	}

	private void _unregisterChannels(
			SelectionKeyRegistrationReference registrationReference)
		throws Exception {

		SelectableChannel readSelectableChannel =
			registrationReference.readSelectionKey.channel();
		SelectableChannel writeSelectableChannel =
			registrationReference.writeSelectionKey.channel();

		registrationReference.cancelRegistration();

		SelectorIntraBand defaultIntraBand =
			(SelectorIntraBand)registrationReference.getIntraBand();

		Selector selector = defaultIntraBand.selector;

		// Wait until selector has unregistered all keys

		while (!selector.keys().isEmpty()) {
			selector.wakeup();
		}

		// Wait until SelectableChannel keys are purged

		while (readSelectableChannel.keyFor(selector) != null);
		while (writeSelectableChannel.keyFor(selector) != null);

		registrationReference.closeChannels();
	}

	private static ServerSocketConfigurator _serverSocketConfigurator =
		new ServerSocketConfigurator() {

		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setReuseAddress(true);
		}
	};

	private SelectorIntraBand _selectorIntraBand;

	private static class MockDuplexSelectableChannel
		extends SelectableChannel
		implements ScatteringByteChannel, GatheringByteChannel {

		public MockDuplexSelectableChannel(boolean readable, boolean writable) {
			_readable = readable;
			_writable = writable;
		}

		@Override
		public SelectorProvider provider() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int validOps() {
			int ops = 0;

			if (_readable) {
				ops |= SelectionKey.OP_READ;
			}

			if (_writable) {
				ops |= SelectionKey.OP_WRITE;
			}

			return ops;
		}

		@Override
		public boolean isRegistered() {
			throw new UnsupportedOperationException();
		}

		@Override
		public SelectionKey keyFor(Selector selector) {
			throw new UnsupportedOperationException();
		}

		@Override
		public SelectionKey register(
				Selector selector, int ops, Object attachment)
			throws ClosedChannelException {

			throw new UnsupportedOperationException();
		}

		@Override
		public SelectableChannel configureBlocking(boolean block)
			throws IOException {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isBlocking() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object blockingLock() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected void implCloseChannel() throws IOException {
			throw new UnsupportedOperationException();
		}

		public long read(ByteBuffer[] dsts, int offset, int length)
			throws IOException {

			throw new UnsupportedOperationException();
		}

		public long read(ByteBuffer[] dsts) throws IOException {
			throw new UnsupportedOperationException();
		}

		public int read(ByteBuffer dst) throws IOException {
			throw new UnsupportedOperationException();
		}

		public long write(ByteBuffer[] srcs, int offset, int length)
			throws IOException {

			throw new UnsupportedOperationException();
		}

		public long write(ByteBuffer[] srcs) throws IOException {
			throw new UnsupportedOperationException();
		}

		public int write(ByteBuffer src) throws IOException {
			throw new UnsupportedOperationException();
		}

		private boolean _readable;
		private boolean _writable;

	}

	private static class WakeupRunnable implements Runnable {

		public WakeupRunnable(SelectorIntraBand defaultIntraBand) {
			_defaultIntraBand = defaultIntraBand;
		}

		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				_defaultIntraBand.selector.wakeup();
			}
		}

		private final SelectorIntraBand _defaultIntraBand;

	}

}