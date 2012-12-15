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

import com.liferay.portal.kernel.nio.intraband.BaseIntraBand;
import com.liferay.portal.kernel.nio.intraband.ChannelContext;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.io.IOException;

import java.nio.channels.Channel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectableChannel;

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
 * An {@link ExecutorService} based implementation for {@link IntraBand}. Works
 * in blocking way with any {@link GatheringByteChannel}/
 * {@link ScatteringByteChannel}.
 *
 * @author Shuyang Zhou
 */
public class ExecutorIntraBand extends BaseIntraBand {

	public ExecutorIntraBand(long defaultTimeout) {
		super(defaultTimeout);

		executorService = Executors.newCachedThreadPool(THREAD_FACTORY);
	}

	public void close() throws InterruptedException, IOException {
		executorService.shutdownNow();
		executorService.awaitTermination(defaultTimeout, TimeUnit.MILLISECONDS);

		super.close();
	}

	public RegistrationReference registerChannel(Channel duplexChannel)
		throws IOException {

		if (duplexChannel == null) {
			throw new NullPointerException("Duplex Channel is null");
		}

		if (!(duplexChannel instanceof ScatteringByteChannel)) {
			throw new IllegalArgumentException(
				"Duplex Channel is not type of ScatteringByteChannel");
		}

		if (!(duplexChannel instanceof GatheringByteChannel)) {
			throw new IllegalArgumentException(
				"Duplex Channel is not type of GatheringByteChannel");
		}

		if (duplexChannel instanceof SelectableChannel) {
			SelectableChannel selectableChannel =
				(SelectableChannel)duplexChannel;

			if (!selectableChannel.isBlocking()) {
				throw new IllegalArgumentException(
					"Duplex Channel is type of SelectableChannel and " +
						"configured in non-blocking mode");
			}
		}

		ensureOpen();

		return doRegisterChannel(
			(ScatteringByteChannel)duplexChannel,
			(GatheringByteChannel)duplexChannel);
	}

	public RegistrationReference registerChannel(
			ScatteringByteChannel scatteringByteChannel,
			GatheringByteChannel gatheringByteChannel)
		throws IOException {

		if (scatteringByteChannel == null) {
			throw new NullPointerException("ScatteringByteChannel is null");
		}

		if (gatheringByteChannel == null) {
			throw new NullPointerException("GatheringByteChannel is null");
		}

		if (scatteringByteChannel instanceof SelectableChannel) {
			SelectableChannel selectableChannel =
				(SelectableChannel)scatteringByteChannel;

			if (!selectableChannel.isBlocking()) {
				throw new IllegalArgumentException(
					"ScatteringByteChannel is type of SelectableChannel and " +
						"configured in non-blocking mode");
			}
		}

		if (gatheringByteChannel instanceof SelectableChannel) {
			SelectableChannel selectableChannel =
				(SelectableChannel)gatheringByteChannel;

			if (!selectableChannel.isBlocking()) {
				throw new IllegalArgumentException(
					"GatheringByteChannel is type of SelectableChannel and " +
						"configured in non-blocking mode");
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

		// Submit the polling jobs, no dispatch will happen before latches are
		// open
		// This is also ensuring ChannelContext._registrationReference
		// thread safe publication

		Future<Void> readFuture = executorService.submit(readingCallable);
		Future<Void> writeFuture = executorService.submit(writingCallable);

		FutureRegistrationReference futureRegistrationReference =
			new FutureRegistrationReference(
				this, channelContext, readFuture, writeFuture,
				scatteringByteChannel, gatheringByteChannel);

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

		channelContext.getSendingQueue().offer(datagram);
	}

	protected static final ThreadFactory THREAD_FACTORY =
		new NamedThreadFactory(
			ExecutorIntraBand.class.getSimpleName().concat("-Executor"),
			Thread.NORM_PRIORITY, ExecutorIntraBand.class.getClassLoader());

	protected final ExecutorService executorService;

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

				BlockingTake :
				while (true) {
					Datagram datagram = sendingQueue.take();

					// At least one Datagram is ready to be sent, save it into
					// ChannelContext

					_channelContext.setWritingDatagram(datagram);

					if (!handleWriting(
							_gatheringByteChannel, _channelContext)) {

						if (!_gatheringByteChannel.isOpen()) {

							// Stop writing on close

							break BlockingTake;
						}
						else {

							// Still open but no longer writable,
							// typical behavior of non-blocking channel

							throw new IllegalStateException(
								_gatheringByteChannel +
									" behaved in non-blocking way.");
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