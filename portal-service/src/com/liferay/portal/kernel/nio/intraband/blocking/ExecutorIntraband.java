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

import com.liferay.portal.kernel.nio.intraband.BaseIntraband;
import com.liferay.portal.kernel.nio.intraband.ChannelContext;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.io.IOException;

import java.nio.channels.Channel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectableChannel;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class ExecutorIntraband extends BaseIntraband {

	public ExecutorIntraband(long defaultTimeout) {
		super(defaultTimeout);
	}

	@Override
	public void close() throws InterruptedException, IOException {
		executorService.shutdownNow();

		executorService.awaitTermination(defaultTimeout, TimeUnit.MILLISECONDS);

		super.close();
	}

	@Override
	public RegistrationReference registerChannel(Channel channel) {
		if (channel == null) {
			throw new NullPointerException("Channel is null");
		}

		if (!(channel instanceof GatheringByteChannel)) {
			throw new IllegalArgumentException(
				"Channel is not of type GatheringByteChannel");
		}

		if (!(channel instanceof ScatteringByteChannel)) {
			throw new IllegalArgumentException(
				"Channel is not of type ScatteringByteChannel");
		}

		if (channel instanceof SelectableChannel) {
			SelectableChannel selectableChannel = (SelectableChannel)channel;

			if (!selectableChannel.isBlocking()) {
				throw new IllegalArgumentException(
					"Channel is of type SelectableChannel and " +
						"configured in nonblocking mode");
			}
		}

		ensureOpen();

		return doRegisterChannel(
			(ScatteringByteChannel)channel, (GatheringByteChannel)channel);
	}

	@Override
	public RegistrationReference registerChannel(
		ScatteringByteChannel scatteringByteChannel,
		GatheringByteChannel gatheringByteChannel) {

		if (gatheringByteChannel == null) {
			throw new NullPointerException("Gathering byte channel is null");
		}

		if (scatteringByteChannel == null) {
			throw new NullPointerException("Scattering byte channel is null");
		}

		if (scatteringByteChannel instanceof SelectableChannel) {
			SelectableChannel selectableChannel =
				(SelectableChannel)scatteringByteChannel;

			if (!selectableChannel.isBlocking()) {
				throw new IllegalArgumentException(
					"Scattering byte channel is of type SelectableChannel " +
						"and configured in nonblocking mode");
			}
		}

		if (gatheringByteChannel instanceof SelectableChannel) {
			SelectableChannel selectableChannel =
				(SelectableChannel)gatheringByteChannel;

			if (!selectableChannel.isBlocking()) {
				throw new IllegalArgumentException(
					"Gathering byte channel is of type SelectableChannel and " +
						"configured in nonblocking mode");
			}
		}

		ensureOpen();

		return doRegisterChannel(scatteringByteChannel, gatheringByteChannel);
	}

	protected RegistrationReference doRegisterChannel(
		ScatteringByteChannel scatteringByteChannel,
		GatheringByteChannel gatheringByteChannel) {

		BlockingQueue<Datagram> sendingQueue =
			new LinkedBlockingQueue<Datagram>();

		ChannelContext channelContext = new ChannelContext(sendingQueue);

		ReadingCallable readingCallable = new ReadingCallable(
			scatteringByteChannel, channelContext);
		WritingCallable writingCallable = new WritingCallable(
			gatheringByteChannel, channelContext);

		// Submit the polling jobs, no dispatch will happen until latches are
		// open. This ensures a thread safe publication of
		// ChannelContext#_registrationReference.

		Future<Void> readFuture = executorService.submit(readingCallable);
		Future<Void> writeFuture = executorService.submit(writingCallable);

		FutureRegistrationReference futureRegistrationReference =
			new FutureRegistrationReference(
				this, channelContext, readFuture, writeFuture);

		channelContext.setRegistrationReference(futureRegistrationReference);

		readingCallable.openLatch();
		writingCallable.openLatch();

		return futureRegistrationReference;
	}

	@Override
	protected void doSendDatagram(
		RegistrationReference registrationReference, Datagram datagram) {

		FutureRegistrationReference futureRegistrationReference =
			(FutureRegistrationReference)registrationReference;

		ChannelContext channelContext =
			futureRegistrationReference.channelContext;

		Queue<Datagram> sendingQueue = channelContext.getSendingQueue();

		sendingQueue.offer(datagram);
	}

	protected static final ThreadFactory THREAD_FACTORY =
		new NamedThreadFactory(
			ExecutorIntraband.class + ".threadFactory", Thread.NORM_PRIORITY,
			ExecutorIntraband.class.getClassLoader());

	protected final ExecutorService executorService =
		Executors.newCachedThreadPool(THREAD_FACTORY);

	protected class ReadingCallable implements Callable<Void> {

		public ReadingCallable(
			ScatteringByteChannel scatteringByteChannel,
			ChannelContext channelContext) {

			_scatteringByteChannel = scatteringByteChannel;
			_channelContext = channelContext;

			_countDownLatch = new CountDownLatch(1);
		}

		public Void call() throws Exception {
			_countDownLatch.await();

			while (_scatteringByteChannel.isOpen()) {
				handleReading(_scatteringByteChannel, _channelContext);
			}

			return null;
		}

		public void openLatch() {
			_countDownLatch.countDown();
		}

		private final ChannelContext _channelContext;
		private final CountDownLatch _countDownLatch;
		private final ScatteringByteChannel _scatteringByteChannel;

	}

	protected class WritingCallable implements Callable<Void> {

		public WritingCallable(
			GatheringByteChannel gatheringByteChannel,
			ChannelContext channelContext) {

			_gatheringByteChannel = gatheringByteChannel;
			_channelContext = channelContext;

			_countDownLatch = new CountDownLatch(1);
		}

		public Void call() throws Exception {
			_countDownLatch.await();

			try {
				BlockingQueue<Datagram> sendingQueue =
					(BlockingQueue<Datagram>)_channelContext.getSendingQueue();

				while (true) {
					Datagram datagram = sendingQueue.take();

					_channelContext.setWritingDatagram(datagram);

					if (!handleWriting(
							_gatheringByteChannel, _channelContext)) {

						if (_gatheringByteChannel.isOpen()) {

							// Still open but no longer writable, typical
							// behavior of nonblocking channel

							throw new IllegalStateException(
								_gatheringByteChannel +
									" behaved in nonblocking way.");
						}
						else {
							break;
						}
					}
				}
			}
			catch (InterruptedException ie) {
			}

			return null;
		}

		public void openLatch() {
			_countDownLatch.countDown();
		}

		private final ChannelContext _channelContext;
		private final CountDownLatch _countDownLatch;
		private final GatheringByteChannel _gatheringByteChannel;

	}

}