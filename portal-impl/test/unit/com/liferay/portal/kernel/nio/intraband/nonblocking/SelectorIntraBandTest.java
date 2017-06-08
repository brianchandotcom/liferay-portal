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

package com.liferay.portal.kernel.nio.intraband.nonblocking;

import com.liferay.portal.kernel.nio.intraband.BaseIntraBand;
import com.liferay.portal.kernel.nio.intraband.BaseIntraBandHelper;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.RecordCompletionHandler;
import com.liferay.portal.kernel.nio.intraband.RecordDatagramReceiveHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.LogRecord;

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

	@Before
	public void setUp() throws Exception {
		_selectorIntraBand = new SelectorIntraBand(_DEFAULT_TIMEOUT);
	}

	@After
	public void tearDown() throws Exception {
		_selectorIntraBand.close();
	}

	@AdviseWith(adviceClasses = {Jdk14LogImplAdvice.class})
	@Test
	public void testReceiveDatagram() throws Exception {
		Pipe readPipe = Pipe.open();
		Pipe writePipe = Pipe.open();

		GatheringByteChannel gatheringByteChannel = writePipe.sink();
		ScatteringByteChannel scatteringByteChannel = readPipe.source();

		SelectionKeyRegistrationReference registrationReference =
			(SelectionKeyRegistrationReference)
				_selectorIntraBand.registerChannel(
					writePipe.source(), readPipe.sink());

		long sequenceId = 100;

		// Receive ACK response, no ACK request, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				DatagramHelper.createACKResponseDatagram(sequenceId),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(
			logRecords.get(0), "Dropped ownerless ACK response ");

		// Receive ACK response, no ACK request, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				DatagramHelper.createACKResponseDatagram(sequenceId),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// Receive ACK response, with ACK request

		Datagram requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setAttachment(requestDatagram, new Object());

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<Object>();

		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		DatagramHelper.writeTo(
			DatagramHelper.createACKResponseDatagram(sequenceId),
			gatheringByteChannel);

		recordCompletionHandler.waitUntilDelivered();

		Assert.assertSame(
			DatagramHelper.getAttachment(requestDatagram),
			recordCompletionHandler.getAttachment());

		// Receive response, no request, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				Datagram.createResponseDatagram(requestDatagram, _data),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(
			logRecords.get(0), "Dropped ownerless response ");

		// Receive response, no request, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		Jdk14LogImplAdvice.reset();

		try {
			requestDatagram = Datagram.createRequestDatagram(_type, _data);

			DatagramHelper.setSequenceId(requestDatagram, sequenceId);

			DatagramHelper.writeTo(
				Datagram.createResponseDatagram(requestDatagram, _data),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// Receive response, with request, with replied completion handler

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setAttachment(requestDatagram, new Object());

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);

		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.of(CompletionType.REPLIED));
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		DatagramHelper.writeTo(
			Datagram.createResponseDatagram(requestDatagram, _data),
			gatheringByteChannel);

		recordCompletionHandler.waitUntilReplied();

		Assert.assertSame(
			DatagramHelper.getAttachment(requestDatagram),
			recordCompletionHandler.getAttachment());

		// Receive response, with request, without replied completion handler,
		// with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.noneOf(CompletionType.class));

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				Datagram.createResponseDatagram(requestDatagram, _data),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(
			logRecords.get(0), "Dropped unconcerned response ");

		// Receive response, with request, without replied completion handler,
		// without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setCompletionTypes(
			requestDatagram, EnumSet.noneOf(CompletionType.class));

		recordCompletionHandler = new RecordCompletionHandler<Object>();

		DatagramHelper.setCompletionHandler(
			requestDatagram, recordCompletionHandler);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);
		DatagramHelper.setTimeout(requestDatagram, 10000);

		BaseIntraBandHelper.addResponseWaitingDatagram(
			_selectorIntraBand, requestDatagram);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(
				Datagram.createResponseDatagram(requestDatagram, _data),
				gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// Receive request, requires ACK, no datagram receive handler, with log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.WARNING);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setAckRequest(requestDatagram);
		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilWarnCalled();
		}

		Datagram ackResponseDatagram = readDatagramFully(scatteringByteChannel);

		Assert.assertEquals(
			sequenceId, DatagramHelper.getSequenceId(ackResponseDatagram));
		Assert.assertTrue(DatagramHelper.isAckResponse(ackResponseDatagram));

		ByteBuffer dataByteBuffer = ackResponseDatagram.getDataByteBuffer();

		Assert.assertEquals(0, dataByteBuffer.capacity());

		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(logRecords.get(0), "Dropped ownerless request ");

		// Receive request, no datagram receive handler, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.OFF);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilIsWarnEnableCalled();
		}

		Assert.assertTrue(logRecords.isEmpty());

		// Receive request, with datagram receive handler,

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BaseIntraBand.class.getName(), Level.SEVERE);

		requestDatagram = Datagram.createRequestDatagram(_type, _data);

		DatagramHelper.setSequenceId(requestDatagram, sequenceId);

		RecordDatagramReceiveHandler recordDatagramReceiveHandler =
			new RecordDatagramReceiveHandler();

		_selectorIntraBand.registerDatagramReceiveHandler(
			_type, recordDatagramReceiveHandler);

		Jdk14LogImplAdvice.reset();

		try {
			DatagramHelper.writeTo(requestDatagram, gatheringByteChannel);
		}
		finally {
			Jdk14LogImplAdvice.waitUntilErrorCalled();
		}

		Datagram receiveDatagram =
			recordDatagramReceiveHandler.getReceiveDatagram();

		Assert.assertEquals(
			sequenceId, DatagramHelper.getSequenceId(receiveDatagram));
		Assert.assertEquals(_type, receiveDatagram.getType());

		dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_data, dataByteBuffer.array());
		Assert.assertEquals(1, logRecords.size());

		assertMessageStartWith(logRecords.get(0), "Unable to dispatch");

		unregisterChannels(registrationReference);

		gatheringByteChannel.close();
		scatteringByteChannel.close();
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
			"execution(* com.liferay.portal.kernel.log.Jdk14LogImpl.error(" +
				"Object, Throwable))")
		public void error() {
			_errorCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* com.liferay.portal.kernel.log.Jdk14LogImpl." +
				"isWarnEnabled())")
		public void isWarnEnabled() {
			_isWarnEnabledCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* com.liferay.portal.kernel.log.Jdk14LogImpl.warn(" +
				"Object))")
		public void warn1() {
			_warnCalledCountDownLatch.countDown();
		}

		@org.aspectj.lang.annotation.After(
			"execution(* com.liferay.portal.kernel.log.Jdk14LogImpl.warn(" +
				"Object, Throwable))")
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

	protected void assertMessageStartWith(
		LogRecord logRecord, String messagePrefix) {

		String message = logRecord.getMessage();

		Assert.assertTrue(message.startsWith(messagePrefix));
	}

	protected Datagram readDatagramFully(
			ScatteringByteChannel scatteringByteChannel)
		throws IOException {

		Datagram datagram = DatagramHelper.createReceiveDatagram();

		while (!DatagramHelper.readFrom(datagram, scatteringByteChannel));

		return datagram;
	}

	void unregisterChannels(
			SelectionKeyRegistrationReference registrationReference)
		throws Exception {

		registrationReference.cancelRegistration();

		SelectorIntraBand defaultIntraBand =
			(SelectorIntraBand)registrationReference.getIntraBand();

		Selector selector = defaultIntraBand.selector;

		Set<SelectionKey> keys = selector.keys();

		while (!keys.isEmpty()) {
			selector.wakeup();
		}

		SelectionKey readSelectionKey = registrationReference.readSelectionKey;
		SelectionKey writeSelectionKey =
			registrationReference.writeSelectionKey;

		SelectableChannel readSelectableChannel = readSelectionKey.channel();
		SelectableChannel writeSelectableChannel = writeSelectionKey.channel();

		while (readSelectableChannel.keyFor(selector) != null);
		while (writeSelectableChannel.keyFor(selector) != null);

		writeSelectableChannel.close();
		readSelectableChannel.close();
	}

	private static final String _DATA_STRING =
		SelectorIntraBandTest.class.getName();

	private static final long _DEFAULT_TIMEOUT = Time.SECOND;

	private byte[] _data = _DATA_STRING.getBytes(Charset.defaultCharset());

	private SelectorIntraBand _selectorIntraBand;

	private byte _type = 1;

}