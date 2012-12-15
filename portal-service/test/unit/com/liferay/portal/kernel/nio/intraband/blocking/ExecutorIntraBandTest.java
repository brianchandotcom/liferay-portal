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

package com.liferay.portal.kernel.nio.intraband.blocking;

import com.liferay.portal.kernel.nio.intraband.ChannelContext;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.IntraBandTestUtil;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand.ReadingCallable;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand.WritingCallable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import java.util.LinkedList;
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
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ExecutorIntraBandTest {

	public static final long DEFAULT_TIMEOUT = 1000;

	@Before
	public void setUp() throws Exception {
		_executorIntraBand = new ExecutorIntraBand(DEFAULT_TIMEOUT);
	}

	@After
	public void tearDown() throws Exception {
		_executorIntraBand.close();
	}

	@Test
	public void testDoSendDatagram() throws Exception {
		Queue<Datagram> sendingQueue = new LinkedList<Datagram>();

		ChannelContext channelContext = new ChannelContext(sendingQueue);

		FutureRegistrationReference futureRegistrationReference =
			new FutureRegistrationReference(
				_executorIntraBand, channelContext, null, null, null, null) {

				@Override
				public boolean isValid() {
					return true;
				}

			};

		Datagram datagram1 = Datagram.createRequestDatagram(
			_type, _dataContent);
		Datagram datagram2 = Datagram.createRequestDatagram(
			_type, _dataContent);
		Datagram datagram3 = Datagram.createRequestDatagram(
			_type, _dataContent);

		_executorIntraBand.sendDatagram(futureRegistrationReference, datagram1);
		_executorIntraBand.sendDatagram(futureRegistrationReference, datagram2);
		_executorIntraBand.sendDatagram(futureRegistrationReference, datagram3);

		Assert.assertEquals(3, sendingQueue.size());
		Assert.assertSame(datagram1, sendingQueue.poll());
		Assert.assertSame(datagram2, sendingQueue.poll());
		Assert.assertSame(datagram3, sendingQueue.poll());
	}

	@Test
	public void testReadingCallable() throws Exception {

		// Gracefully exit on closure

		Pipe pipe = Pipe.open();

		final SourceChannel sourceChannel = pipe.source();
		Pipe.SinkChannel sinkChannel = pipe.sink();

		try {
			MockRegistrationReference mockRegistrationReference =
				new MockRegistrationReference(_executorIntraBand);
			ChannelContext channelContext = new ChannelContext(
				new LinkedList<Datagram>());
			channelContext.setRegistrationReference(mockRegistrationReference);

			ReadingCallable readingCallable =
				_executorIntraBand.new ReadingCallable(
					sourceChannel, channelContext);

			Thread closeThread = new Thread() {

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

		// 1) Duplex channel is null

		try {
			_executorIntraBand.registerChannel(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Duplex Channel is null", npe.getMessage());
		}

		// 2) Duplex channel is not type of ScatteringByteChannel

		try {
			_executorIntraBand.registerChannel(
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
			_executorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(
					ScatteringByteChannel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Duplex Channel is not type of GatheringByteChannel",
				iae.getMessage());
		}

		// 4) Duplex channel is type of SelectableChannel and in non-blocking
		// mode

		SocketChannel[] socketChannelPeers =
			IntraBandTestUtil.createSocketChannelPeers();

		SocketChannel socketChannel = socketChannelPeers[0];

		socketChannel.configureBlocking(false);

		try {
			_executorIntraBand.registerChannel(socketChannel);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Duplex Channel is type of SelectableChannel and configured " +
				"in non-blocking mode", iae.getMessage());
		}

		// 5) Normal register, with SelectableChannel

		socketChannel.configureBlocking(true);

		try {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraBand.registerChannel(
					socketChannel);

			Assert.assertSame(
				_executorIntraBand, futureRegistrationReference.getIntraBand());
			Assert.assertTrue(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraBand.executorService;

			// Wait polling threads terminated before entering finally block

			while (threadPoolExecutor.getActiveCount() != 0);

			futureRegistrationReference.closeChannels();
		}
		finally {
			socketChannelPeers[0].close();
			socketChannelPeers[1].close();
		}

		// 6) Normal register, with non-SelectableChannel

		File tempFile = new File("tempFile");
		tempFile.deleteOnExit();

		RandomAccessFile randomAccessFile =
			new RandomAccessFile(tempFile, "rw");

		FileChannel fileChannel = randomAccessFile.getChannel();

		try {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraBand.registerChannel(
					fileChannel);

			Assert.assertSame(
				_executorIntraBand, futureRegistrationReference.getIntraBand());
			Assert.assertTrue(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraBand.executorService;

			// Wait polling threads terminated before entering finally block

			while (threadPoolExecutor.getActiveCount() != 0);

			futureRegistrationReference.closeChannels();
		}
		finally {
			fileChannel.close();
			tempFile.delete();
		}
	}

	@Test
	public void testRegisterChannelReadWrite() throws Exception {

		// 1) ScatteringByteChannel is null

		try {
			_executorIntraBand.registerChannel(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"ScatteringByteChannel is null", npe.getMessage());
		}

		// 2) GatheringByteChannel is null

		try {
			_executorIntraBand.registerChannel(
				IntraBandTestUtil.<ScatteringByteChannel>createProxy(
					ScatteringByteChannel.class), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"GatheringByteChannel is null", npe.getMessage());
		}

		// 3) ScatteringByteChannel is type of SelectableChannel and in
		// non-blocking mode

		Pipe pipe = Pipe.open();

		SourceChannel sourceChannel = pipe.source();
		Pipe.SinkChannel sinkChannel = pipe.sink();

		sourceChannel.configureBlocking(false);

		try {
			_executorIntraBand.registerChannel(sourceChannel, sinkChannel);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"ScatteringByteChannel is type of SelectableChannel and " +
				"configured in non-blocking mode", iae.getMessage());
		}

		// 4) GatheringByteChannel is type of SelectableChannel and in
		// non-blocking mode

		sourceChannel.configureBlocking(true);
		sinkChannel.configureBlocking(false);

		try {
			_executorIntraBand.registerChannel(sourceChannel, sinkChannel);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"GatheringByteChannel is type of SelectableChannel and " +
				"configured in non-blocking mode", iae.getMessage());
		}

		// 5) Normal register, with SelectableChannel

		sourceChannel.configureBlocking(true);
		sinkChannel.configureBlocking(true);

		try {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraBand.registerChannel(
					sourceChannel, sinkChannel);

			Assert.assertSame(
				_executorIntraBand, futureRegistrationReference.getIntraBand());
			Assert.assertTrue(futureRegistrationReference.isValid());

			// Take care FutureRegistrationReference.isValid() code coverage

			futureRegistrationReference.writeFuture.cancel(true);

			Assert.assertFalse(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraBand.executorService;

			// Wait polling threads terminated before entering finally block

			while (threadPoolExecutor.getActiveCount() != 0);

			futureRegistrationReference.closeChannels();
		}
		finally {
			sourceChannel.close();
			sinkChannel.close();
		}

		// 6) Normal register, with non-SelectableChannel

		File tempFile = new File("tempFile");
		tempFile.createNewFile();
		tempFile.deleteOnExit();

		FileChannel readFileChannel =
			new FileInputStream(tempFile).getChannel();
		FileChannel writeFileChannel =
			new FileOutputStream(tempFile).getChannel();

		try {
			FutureRegistrationReference futureRegistrationReference =
				(FutureRegistrationReference)_executorIntraBand.registerChannel(
					readFileChannel, writeFileChannel);

			Assert.assertSame(
				_executorIntraBand, futureRegistrationReference.getIntraBand());
			Assert.assertTrue(futureRegistrationReference.isValid());

			// Take care FutureRegistrationReference.isValid() code coverage

			futureRegistrationReference.writeFuture.cancel(true);

			Assert.assertFalse(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraBand.executorService;

			// Wait polling threads terminated before entering finally block

			while (threadPoolExecutor.getActiveCount() != 0);

			futureRegistrationReference.closeChannels();
		}
		finally {
			readFileChannel.close();
			writeFileChannel.close();
		}
	}

	@Test
	public void testWritingCallable() throws Exception {

		// 1) Normal writing

		Pipe pipe = Pipe.open();

		SourceChannel sourceChannel = pipe.source();
		Pipe.SinkChannel sinkChannel = pipe.sink();

		BlockingQueue<Datagram> sendingQueue = new SynchronousQueue<Datagram>();
		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(_executorIntraBand);
		ChannelContext channelContext = new ChannelContext(sendingQueue);
		channelContext.setRegistrationReference(mockRegistrationReference);

		WritingCallable writingCallable =
			_executorIntraBand.new WritingCallable(sinkChannel, channelContext);

		writingCallable.openLatch();

		FutureTask<Void> futureTask = new FutureTask<Void>(writingCallable);

		Thread writingThread = new Thread(futureTask);

		writingThread.start();

		Datagram datagram1 = Datagram.createRequestDatagram(
			_type, _dataContent);

		sendingQueue.put(datagram1);

		Datagram datagram2 = Datagram.createRequestDatagram(
			_type, _dataContent);

		sendingQueue.put(datagram2);

		Datagram receiveDatagram1 = DatagramHelper.createReceiveDatagram();
		Assert.assertTrue(
			DatagramHelper.readFrom(receiveDatagram1, sourceChannel));
		Datagram receiveDatagram2 = DatagramHelper.createReceiveDatagram();
		Assert.assertTrue(
			DatagramHelper.readFrom(receiveDatagram2, sourceChannel));

		// 2) Interrupt on blocking take

		while (writingThread.getState() != Thread.State.WAITING);

		writingThread.interrupt();

		Void result = futureTask.get();

		Assert.assertNull(result);

		writingThread.join();

		sourceChannel.close();
		sinkChannel.close();

		// 3) Interrupt on blocking write

		pipe = Pipe.open();

		sourceChannel = pipe.source();
		sinkChannel = pipe.sink();

		writingCallable =
			_executorIntraBand.new WritingCallable(sinkChannel, channelContext);

		writingCallable.openLatch();

		futureTask = new FutureTask<Void>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		// Fill up pipe buffer

		int counter = 0;

		while (sendingQueue.offer(
			Datagram.createRequestDatagram(
				_type, _dataContent), 1, TimeUnit.SECONDS)) {

			counter++;
		}

		Assert.assertTrue(counter > 0);

		writingThread.interrupt();

		result = futureTask.get();

		Assert.assertNull(result);

		writingThread.join();

		sourceChannel.close();
		sinkChannel.close();

		// 4) Async close on on blocking write

		pipe = Pipe.open();

		sourceChannel = pipe.source();
		sinkChannel = pipe.sink();

		writingCallable =
			_executorIntraBand.new WritingCallable(sinkChannel, channelContext);

		writingCallable.openLatch();

		futureTask = new FutureTask<Void>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		// Fill up pipe buffer

		counter = 0;

		while (sendingQueue.offer(
			Datagram.createRequestDatagram(
				_type, _dataContent), 1, TimeUnit.SECONDS)) {

			counter++;
		}

		Assert.assertTrue(counter > 0);

		sinkChannel.close();

		result = futureTask.get();

		Assert.assertNull(result);

		writingThread.join();

		sourceChannel.close();
		sinkChannel.close();

		// 5) Change to non-blocking at runtime

		pipe = Pipe.open();

		sourceChannel = pipe.source();
		sinkChannel = pipe.sink();

		sinkChannel.configureBlocking(false);

		writingCallable =
			_executorIntraBand.new WritingCallable(sinkChannel, channelContext);

		writingCallable.openLatch();

		futureTask = new FutureTask<Void>(writingCallable);

		writingThread = new Thread(futureTask);

		writingThread.start();

		// Fill up pipe buffer

		counter = 0;

		while (sendingQueue.offer(
				Datagram.createRequestDatagram(
					_type, _dataContent), 1, TimeUnit.SECONDS) ||
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

	private byte[] _dataContent = "Datagram Sending Test".getBytes(
		Charset.defaultCharset());
	private ExecutorIntraBand _executorIntraBand;
	private byte _type = 1;

}