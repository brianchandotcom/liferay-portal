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
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand.ReadingCallable;
import com.liferay.portal.kernel.util.Time;

import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.Pipe;
import java.nio.charset.Charset;

import java.util.LinkedList;
import java.util.Queue;

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

	private static final String _DATA_STRING =
		ExecutorIntraBandTest.class.getName();

	private static final long _DEFAULT_TIMEOUT = Time.SECOND;

	private byte[] _data = _DATA_STRING.getBytes(Charset.defaultCharset());

	private ExecutorIntraBand _executorIntraBand;

	private byte _type = 1;

}