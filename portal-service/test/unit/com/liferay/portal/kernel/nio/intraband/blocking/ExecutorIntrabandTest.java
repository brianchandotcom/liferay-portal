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

package com.liferay.portal.kernel.nio.intraband.blocking;

import com.liferay.portal.kernel.nio.intraband.ChannelContext;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.IntrabandTestUtil;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband.ReadingCallable;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraband.WritingCallable;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.Time;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
	public static CodeCoverageAssertor codeCoverageAssertor =
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
		Queue<Datagram> sendingQueue = new LinkedList<Datagram>();

		FutureRegistrationReference futureRegistrationReference =
			new FutureRegistrationReference(
				_executorIntraband, new ChannelContext(sendingQueue), null,
				null) {

				@Override
				public boolean isValid() {
					return true;
				}

			};

		Datagram datagram1 = Datagram.createRequestDatagram(_type, _data);

		_executorIntraband.sendDatagram(futureRegistrationReference, datagram1);

		Datagram datagram2 = Datagram.createRequestDatagram(_type, _data);

		_executorIntraband.sendDatagram(futureRegistrationReference, datagram2);

		Datagram datagram3 = Datagram.createRequestDatagram(_type, _data);

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

		final SourceChannel sourceChannel = pipe.source();
		SinkChannel sinkChannel = pipe.sink();

		try {
			MockRegistrationReference mockRegistrationReference =
				new MockRegistrationReference(_executorIntraband);

			ChannelContext channelContext = new ChannelContext(
				new LinkedList<Datagram>());

			channelContext.setRegistrationReference(mockRegistrationReference);

			ReadingCallable readingCallable =
				_executorIntraband.new ReadingCallable(
					sourceChannel, channelContext);

			Thread closeThread = new Thread() {

				@Override
				public void run() {
					try {
						sleep(100);

						sourceChannel.close();
					}
					catch (Exception e) {
						Assert.fail(e.getMessage());
					}
				}

			};

			closeThread.start();

			readingCallable.openLatch();

			Void result = readingCallable.call();

			closeThread.join();

			Assert.assertNull(result);
			Assert.assertFalse(mockRegistrationReference.isValid());
		}
		finally {
			sourceChannel.close();
			sinkChannel.close();
		}
	}

	@Test
	public void testRegisterChannelDuplex() throws Exception {

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
					"nonblocking mode", iae.getMessage());
		}

		// Normal register, with SelectableChannel

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

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraband.executorService;

			while (threadPoolExecutor.getActiveCount() != 0);
		}
		finally {
			peerSocketChannels[0].close();
			peerSocketChannels[1].close();
		}

		// Normal register, with non-SelectableChannel

		File tempFile = new File("tempFile");

		tempFile.deleteOnExit();

		RandomAccessFile randomAccessFile = new RandomAccessFile(
			tempFile, "rw");

		randomAccessFile.setLength(Integer.MAX_VALUE);

		FileChannel fileChannel = randomAccessFile.getChannel();

		try {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraband.registerChannel(
					fileChannel);

			Assert.assertSame(
				_executorIntraband, futureRegistrationReference.getIntraband());
			Assert.assertTrue(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraband.executorService;

			while (threadPoolExecutor.getActiveCount() != 0);
		}
		finally {
			fileChannel.close();
			tempFile.delete();
		}
	}

	@Test
	public void testRegisterChannelReadWrite() throws Exception {

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
				null, IntrabandTestUtil.<GatheringByteChannel>createProxy(
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

		SourceChannel sourceChannel = pipe.source();
		SinkChannel sinkChannel = pipe.sink();

		sourceChannel.configureBlocking(false);

		try {
			_executorIntraband.registerChannel(sourceChannel, sinkChannel);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Scattering byte channel is of type SelectableChannel and " +
					"configured in nonblocking mode", iae.getMessage());
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
					"configured in nonblocking mode", iae.getMessage());
		}

		// Normal register, with SelectableChannel

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

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraband.executorService;

			while (threadPoolExecutor.getActiveCount() != 0);
		}
		finally {
			sourceChannel.close();
			sinkChannel.close();
		}

		// Normal register, with non-SelectableChannel

		File tempFile = new File("tempFile");

		tempFile.deleteOnExit();

		RandomAccessFile randomAccessFile = new RandomAccessFile(
			tempFile, "rw");

		randomAccessFile.setLength(Integer.MAX_VALUE);

		randomAccessFile.close();

		FileInputStream fileInputStream = new FileInputStream(tempFile);
		FileOutputStream fileOutputStream = new FileOutputStream(tempFile);

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

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraband.executorService;

			while (threadPoolExecutor.getActiveCount() != 0);
		}
		finally {
			readFileChannel.close();
			writeFileChannel.close();
		}
	}

	@Test
	public void testWritingCallable() throws Exception {

		// Normal writing

		Pipe pipe = Pipe.open();

		SourceChannel sourceChannel = pipe.source();
		SinkChannel sinkChannel = pipe.sink();

		BlockingQueue<Datagram> sendingQueue = new SynchronousQueue<Datagram>();

		ChannelContext channelContext = new ChannelContext(sendingQueue);

		channelContext.setRegistrationReference(
			new MockRegistrationReference(_executorIntraband));

		WritingCallable writingCallable =
			_executorIntraband.new WritingCallable(sinkChannel, channelContext);

		writingCallable.openLatch();

		FutureTask<Void> futureTask = new FutureTask<Void>(writingCallable);

		Thread writingThread = new Thread(futureTask);

		writingThread.start();

		Datagram datagram1 = Datagram.createRequestDatagram(_type, _data);

		sendingQueue.put(datagram1);

		Datagram datagram2 = Datagram.createRequestDatagram(_type, _data);

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

		futureTask = new FutureTask<Void>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		int counter = 0;

		while (sendingQueue.offer(
					Datagram.createRequestDatagram(_type, _data), 1,
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

		futureTask = new FutureTask<Void>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		counter = 0;

		while (sendingQueue.offer(
					Datagram.createRequestDatagram(_type, _data), 1,
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

		futureTask = new FutureTask<Void>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		counter = 0;

		while (sendingQueue.offer(
					Datagram.createRequestDatagram(_type, _data), 1,
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
			Assert.assertEquals(
				IllegalStateException.class, ee.getCause().getClass());
		}

		writingThread.join();

		sourceChannel.close();
		sinkChannel.close();
	}

	private static final String _DATA_STRING =
		ExecutorIntrabandTest.class.getName();

	private static final long _DEFAULT_TIMEOUT = Time.SECOND;

	private byte[] _data = _DATA_STRING.getBytes(Charset.defaultCharset());

	private ExecutorIntraband _executorIntraband;

	private byte _type = 1;

}