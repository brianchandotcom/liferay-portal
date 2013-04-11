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
import com.liferay.portal.kernel.nio.intraband.IntraBandTestUtil;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand.ReadingCallable;
import com.liferay.portal.kernel.util.Time;

import java.io.File;
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
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ExecutorIntraBandTest {

	@Before
	public void setUp() {
		_executorIntraBand = new ExecutorIntraBand(_DEFAULT_TIMEOUT);
	}

	@After
	public void tearDown() throws Exception {
		_executorIntraBand.close();
	}

	@Test
	public void testDoSendDatagram() {
		Queue<Datagram> sendingQueue = new LinkedList<Datagram>();

		FutureRegistrationReference futureRegistrationReference =
			new FutureRegistrationReference(
				_executorIntraBand, new ChannelContext(sendingQueue), null,
				null) {

				@Override
				public boolean isValid() {
					return true;
				}

			};

		Datagram datagram1 = Datagram.createRequestDatagram(_type, _data);

		_executorIntraBand.sendDatagram(futureRegistrationReference, datagram1);

		Datagram datagram2 = Datagram.createRequestDatagram(_type, _data);

		_executorIntraBand.sendDatagram(futureRegistrationReference, datagram2);

		Datagram datagram3 = Datagram.createRequestDatagram(_type, _data);

		_executorIntraBand.sendDatagram(futureRegistrationReference, datagram3);

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
				new MockRegistrationReference(_executorIntraBand);

			ChannelContext channelContext = new ChannelContext(
				new LinkedList<Datagram>());

			channelContext.setRegistrationReference(mockRegistrationReference);

			ReadingCallable readingCallable =
				_executorIntraBand.new ReadingCallable(
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
			_executorIntraBand.registerChannel(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Channel is null", npe.getMessage());
		}

		// Channel is not of type GatheringByteChannel

		try {
			_executorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(Channel.class));

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Channel is not of type GatheringByteChannel",
				iae.getMessage());
		}

		// Channel is not of type ScatteringByteChannel

		try {
			_executorIntraBand.registerChannel(
				IntraBandTestUtil.<Channel>createProxy(
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
			IntraBandTestUtil.createSocketChannelPeers();

		SocketChannel socketChannel = peerSocketChannels[0];

		socketChannel.configureBlocking(false);

		try {
			_executorIntraBand.registerChannel(socketChannel);

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
				(FutureRegistrationReference)_executorIntraBand.registerChannel(
					socketChannel);

			Assert.assertSame(
				_executorIntraBand, futureRegistrationReference.getIntraBand());
			Assert.assertTrue(futureRegistrationReference.isValid());

			futureRegistrationReference.cancelRegistration();

			Assert.assertFalse(futureRegistrationReference.isValid());

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)_executorIntraBand.executorService;

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

			while (threadPoolExecutor.getActiveCount() != 0);
		}
		finally {
			fileChannel.close();
			tempFile.delete();
		}
	}

	private static final String _DATA_STRING =
		ExecutorIntraBandTest.class.getName();

	private static final long _DEFAULT_TIMEOUT = Time.SECOND;

	private byte[] _data = _DATA_STRING.getBytes(Charset.defaultCharset());

	private ExecutorIntraBand _executorIntraBand;

	private byte _type = 1;

}