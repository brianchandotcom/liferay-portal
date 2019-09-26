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

package com.liferay.portal.kernel.nio.intraband.blocking;

import com.liferay.portal.kernel.nio.intraband.BaseIntraband;
import com.liferay.portal.kernel.nio.intraband.ChannelContext;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.IntrabandTestUtil;
import com.liferay.portal.kernel.nio.intraband.RecordCompletionHandler;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband.ReadingCallable;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband.WritingCallable;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.SyncThrowableThread;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.Time;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ExecutorIntrabandTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(FutureRegistrationReference.class);
			}

		};

	@Before
	public void setUp() {
		_executorIntraband = new ExecutorIntraband(_DEFAULT_TIMEOUT);
	}

	@After
	public void tearDown() throws Exception {
		_executorIntraband.close();
	}

	@Test
	public void testDoSendDatagram() {
		Queue<Datagram> sendingQueue = new LinkedList<>();

		FutureRegistrationReference futureRegistrationReference =
			new FutureRegistrationReference(
				_executorIntraband, new ChannelContext(sendingQueue), null,
				null) {

				@Override
				public boolean isValid() {
					return true;
				}

			};

		Datagram datagram1 = Datagram.createRequestDatagram(_TYPE, _DATA);

		_executorIntraband.sendDatagram(futureRegistrationReference, datagram1);

		Datagram datagram2 = Datagram.createRequestDatagram(_TYPE, _DATA);

		_executorIntraband.sendDatagram(futureRegistrationReference, datagram2);

		Datagram datagram3 = Datagram.createRequestDatagram(_TYPE, _DATA);

		_executorIntraband.sendDatagram(futureRegistrationReference, datagram3);

		Assert.assertEquals(3, sendingQueue.size());
		Assert.assertSame(datagram1, sendingQueue.poll());
		Assert.assertSame(datagram2, sendingQueue.poll());
		Assert.assertSame(datagram3, sendingQueue.poll());
	}

	@Test
	public void testReadingCallable() throws Exception {

		// Exit gracefully on close

		Pipe pipe = Pipe.open();

		final Pipe.SourceChannel sourceChannel = pipe.source();
		Pipe.SinkChannel sinkChannel = pipe.sink();

		try {
			MockRegistrationReference mockRegistrationReference =
				new MockRegistrationReference(_executorIntraband);

			ChannelContext channelContext = new ChannelContext(
				new LinkedList<Datagram>());

			channelContext.setRegistrationReference(mockRegistrationReference);

			ReadingCallable readingCallable =
				_executorIntraband.new ReadingCallable(
					sourceChannel, channelContext);

			SyncThrowableThread<Void> syncThrowableThread =
				new SyncThrowableThread<>(
					new Callable<Void>() {

						@Override
						public Void call() throws Exception {
							Thread.sleep(100);

							sourceChannel.close();

							return null;
						}

					});

			syncThrowableThread.start();

			readingCallable.openLatch();

			Void result = readingCallable.call();

			syncThrowableThread.sync();

			Assert.assertNull(result);
			Assert.assertFalse(mockRegistrationReference.isValid());
		}
		finally {
			sourceChannel.close();
			sinkChannel.close();
		}
	}

	@Test
	public void testRegisterChannelDuplexWithErrors() throws Exception {

		// Channel is null

		try {
			_executorIntraband.registerChannel(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Channel is null", npe.getMessage());
		}

		// Channel is not of type GatheringByteChannel

		try {
			_executorIntraband.registerChannel(
				IntrabandTestUtil.<Channel>createProxy(Channel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is not of type GatheringByteChannel",
				iae.getMessage());
		}

		// Channel is not of type ScatteringByteChannel

		try {
			_executorIntraband.registerChannel(
				IntrabandTestUtil.<Channel>createProxy(
					GatheringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is not of type ScatteringByteChannel",
				iae.getMessage());
		}

		// Channel is of type SelectableChannel and configured in nonblocking
		// mode

		SocketChannel[] peerSocketChannels =
			IntrabandTestUtil.createSocketChannelPeers();

		SocketChannel socketChannel = peerSocketChannels[0];

		socketChannel.configureBlocking(false);

		try {
			_executorIntraband.registerChannel(socketChannel);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is of type SelectableChannel and configured in " +
					"nonblocking mode",
				iae.getMessage());
		}
	}

	@Test
	public void testRegisterChannelDuplexWithNonselectableChannel()
		throws Exception {

		// Normal register, with unselectable channel

		File tempFile = new File("tempFile");

		tempFile.deleteOnExit();

		RandomAccessFile randomAccessFile = new RandomAccessFile(
			tempFile, "rw");

		randomAccessFile.setLength(Integer.MAX_VALUE);

		try (FileChannel fileChannel = randomAccessFile.getChannel()) {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraband.registerChannel(
					fileChannel);

			Assert.assertSame(
				_executorIntraband, futureRegistrationReference.getIntraband());
			Assert.assertTrue(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());
		}
		finally {
			tempFile.delete();
		}
	}

	@Test
	public void testRegisterChannelDuplexWithSelectableChannel()
		throws Exception {

		// Normal register, with selectable channel

		SocketChannel[] peerSocketChannels =
			IntrabandTestUtil.createSocketChannelPeers();

		SocketChannel socketChannel = peerSocketChannels[0];

		socketChannel.configureBlocking(true);

		try {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraband.registerChannel(
					socketChannel);

			Assert.assertSame(
				_executorIntraband, futureRegistrationReference.getIntraband());
			Assert.assertTrue(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());
		}
		finally {
			peerSocketChannels[0].close();
			peerSocketChannels[1].close();
		}
	}

	@Test
	public void testRegisterChannelReadWriteWithErrors() throws Exception {

		// Gathering byte channel is null

		try {
			_executorIntraband.registerChannel(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Gathering byte channel is null", npe.getMessage());
		}

		// Scattering byte channel is null

		try {
			_executorIntraband.registerChannel(
				null,
				IntrabandTestUtil.<GatheringByteChannel>createProxy(
					GatheringByteChannel.class));

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Scattering byte channel is null", npe.getMessage());
		}

		// Scattering byte channel is of type SelectableChannel and configured
		// in nonblocking mode

		Pipe pipe = Pipe.open();

		Pipe.SourceChannel sourceChannel = pipe.source();
		Pipe.SinkChannel sinkChannel = pipe.sink();

		sourceChannel.configureBlocking(false);

		try {
			_executorIntraband.registerChannel(sourceChannel, sinkChannel);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Scattering byte channel is of type SelectableChannel and " +
					"configured in nonblocking mode",
				iae.getMessage());
		}

		// Gathering byte channel is of type SelectableChannel and configured in
		// nonblocking mode

		sourceChannel.configureBlocking(true);
		sinkChannel.configureBlocking(false);

		try {
			_executorIntraband.registerChannel(sourceChannel, sinkChannel);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Gathering byte channel is of type SelectableChannel and " +
					"configured in nonblocking mode",
				iae.getMessage());
		}
	}

	@Test
	public void testRegisterChannelReadWriteWithNonselectableChannel()
		throws Exception {

		// Normal register, with unselectable channel

		File tempFile = new File("tempFile");

		tempFile.deleteOnExit();

		try (RandomAccessFile randomAccessFile = new RandomAccessFile(
				tempFile, "rw")) {

			randomAccessFile.setLength(Integer.MAX_VALUE);
		}

		FileInputStream fileInputStream = new FileInputStream(tempFile);
		FileOutputStream fileOutputStream = new FileOutputStream(
			tempFile, true);

		FileChannel readFileChannel = fileInputStream.getChannel();
		FileChannel writeFileChannel = fileOutputStream.getChannel();

		try {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraband.registerChannel(
					readFileChannel, writeFileChannel);

			Assert.assertSame(
				_executorIntraband, futureRegistrationReference.getIntraband());
			Assert.assertTrue(futureRegistrationReference.isValid());

			futureRegistrationReference.writeFuture.cancel(true);

			Assert.assertFalse(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());
		}
		finally {
			readFileChannel.close();
			writeFileChannel.close();
		}
	}

	@Test
	public void testRegisterChannelReadWriteWithSelectableChannel()
		throws Exception {

		// Normal register, with selectable channel

		Pipe pipe = Pipe.open();

		Pipe.SourceChannel sourceChannel = pipe.source();
		Pipe.SinkChannel sinkChannel = pipe.sink();

		sourceChannel.configureBlocking(true);
		sinkChannel.configureBlocking(true);

		try {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraband.registerChannel(
					sourceChannel, sinkChannel);

			Assert.assertSame(
				_executorIntraband, futureRegistrationReference.getIntraband());
			Assert.assertTrue(futureRegistrationReference.isValid());

			futureRegistrationReference.writeFuture.cancel(true);

			Assert.assertFalse(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());
		}
		finally {
			sourceChannel.close();
			sinkChannel.close();
		}
	}

	@Test
	public void testSendDatagramWithCallback() throws Exception {

		// Submitted callback

		Pipe readPipe = Pipe.open();

		ScatteringByteChannel scatteringByteChannel = readPipe.source();

		Pipe writePipe = Pipe.open();

		GatheringByteChannel gatheringByteChannel = writePipe.sink();

		FutureRegistrationReference futureRegistrationReference =
			(FutureRegistrationReference)_executorIntraband.registerChannel(
				writePipe.source(), readPipe.sink());

		Object attachment = new Object();

		RecordCompletionHandler<Object> recordCompletionHandler =
			new RecordCompletionHandler<>();

		_executorIntraband.sendDatagram(
			futureRegistrationReference,
			Datagram.createRequestDatagram(_TYPE, _DATA), attachment,
			EnumSet.of(CompletionHandler.CompletionType.SUBMITTED),
			recordCompletionHandler);

		Datagram receiveDatagram = IntrabandTestUtil.readDatagramFully(
			scatteringByteChannel);

		recordCompletionHandler.waitUntilSubmitted();

		Assert.assertSame(attachment, recordCompletionHandler.getAttachment());
		Assert.assertEquals(_TYPE, receiveDatagram.getType());

		ByteBuffer dataByteBuffer = receiveDatagram.getDataByteBuffer();

		Assert.assertArrayEquals(_DATA, dataByteBuffer.array());

		// Callback timeout, with log

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					BaseIntraband.class.getName(), Level.WARNING)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			recordCompletionHandler = new RecordCompletionHandler<>();

			_executorIntraband.sendDatagram(
				futureRegistrationReference,
				Datagram.createRequestDatagram(_TYPE, _DATA), attachment,
				EnumSet.of(CompletionHandler.CompletionType.REPLIED),
				recordCompletionHandler, 10, TimeUnit.MILLISECONDS);

			Thread.sleep(20);

			_executorIntraband.sendDatagram(
				futureRegistrationReference,
				Datagram.createRequestDatagram(_TYPE, _DATA), attachment,
				EnumSet.of(CompletionHandler.CompletionType.DELIVERED),
				recordCompletionHandler, 10, TimeUnit.MILLISECONDS);

			while (logRecords.isEmpty());

			IntrabandTestUtil.assertMessageStartWith(
				logRecords.get(0), "Removed timeout response waiting datagram");

			gatheringByteChannel.close();
			scatteringByteChannel.close();
		}
	}

	@Test
	public void testWritingCallable() throws Exception {

		// Normal writing

		Pipe pipe = Pipe.open();

		Pipe.SourceChannel sourceChannel = pipe.source();
		Pipe.SinkChannel sinkChannel = pipe.sink();

		BlockingQueue<Datagram> sendingQueue = new SynchronousQueue<>();

		ChannelContext channelContext = new ChannelContext(sendingQueue);

		channelContext.setRegistrationReference(
			new MockRegistrationReference(_executorIntraband));

		WritingCallable writingCallable =
			_executorIntraband.new WritingCallable(sinkChannel, channelContext);

		writingCallable.openLatch();

		FutureTask<Void> futureTask = new FutureTask<>(writingCallable);

		Thread writingThread = new Thread(futureTask);

		writingThread.start();

		Datagram datagram1 = Datagram.createRequestDatagram(_TYPE, _DATA);

		sendingQueue.put(datagram1);

		Datagram datagram2 = Datagram.createRequestDatagram(_TYPE, _DATA);

		sendingQueue.put(datagram2);

		Assert.assertTrue(
			DatagramHelper.readFrom(
				DatagramHelper.createReceiveDatagram(), sourceChannel));

		Assert.assertTrue(
			DatagramHelper.readFrom(
				DatagramHelper.createReceiveDatagram(), sourceChannel));

		// Interrupt on blocking take

		while (writingThread.getState() != Thread.State.WAITING);

		writingThread.interrupt();

		Void result = futureTask.get();

		Assert.assertNull(result);

		writingThread.join();

		sourceChannel.close();
		sinkChannel.close();

		// Interrupt on blocking write

		pipe = Pipe.open();

		sourceChannel = pipe.source();

		sinkChannel = pipe.sink();

		writingCallable = _executorIntraband.new WritingCallable(
			sinkChannel, channelContext);

		writingCallable.openLatch();

		futureTask = new FutureTask<>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		int counter = 0;

		while (sendingQueue.offer(
					Datagram.createRequestDatagram(_TYPE, _DATA), 1,
					TimeUnit.SECONDS)) {

			counter++;
		}

		Assert.assertTrue(counter > 0);

		writingThread.interrupt();

		result = futureTask.get();

		Assert.assertNull(result);

		writingThread.join();

		sourceChannel.close();
		sinkChannel.close();

		// Asynchronous close on blocking write

		pipe = Pipe.open();

		sourceChannel = pipe.source();

		sinkChannel = pipe.sink();

		writingCallable = _executorIntraband.new WritingCallable(
			sinkChannel, channelContext);

		writingCallable.openLatch();

		futureTask = new FutureTask<>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		counter = 0;

		while (sendingQueue.offer(
					Datagram.createRequestDatagram(_TYPE, _DATA), 1,
					TimeUnit.SECONDS)) {

			counter++;
		}

		Assert.assertTrue(counter > 0);

		sinkChannel.close();

		result = futureTask.get();

		Assert.assertNull(result);

		writingThread.join();

		sourceChannel.close();
		sinkChannel.close();

		// Change to nonblocking at runtime

		pipe = Pipe.open();

		sourceChannel = pipe.source();

		sinkChannel = pipe.sink();

		sinkChannel.configureBlocking(false);

		writingCallable = _executorIntraband.new WritingCallable(
			sinkChannel, channelContext);

		writingCallable.openLatch();

		futureTask = new FutureTask<>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		counter = 0;

		while (sendingQueue.offer(
					Datagram.createRequestDatagram(_TYPE, _DATA), 1,
					TimeUnit.SECONDS) ||
			   writingThread.isAlive()) {

			counter++;
		}

		Assert.assertTrue(counter > 0);

		try {
			futureTask.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable cause = ee.getCause();

			Assert.assertEquals(IllegalStateException.class, cause.getClass());
		}

		writingThread.join();

		sourceChannel.close();
		sinkChannel.close();
	}

	private static final byte[] _DATA;

	private static final long _DEFAULT_TIMEOUT = Time.SECOND;

	private static final byte _TYPE = 1;

	static {
		String dataString = ExecutorIntrabandTest.class.getName();

		_DATA = dataString.getBytes(Charset.defaultCharset());
	}

	private ExecutorIntraband _executorIntraband;

}