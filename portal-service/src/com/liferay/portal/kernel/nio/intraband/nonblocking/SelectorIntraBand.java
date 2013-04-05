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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.BaseIntraBand;
import com.liferay.portal.kernel.nio.intraband.ChannelContext;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.io.IOException;

import java.nio.channels.CancelledKeyException;
import java.nio.channels.Channel;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

/**
 * A NIO {@link Selector} based implementation for {@link IntraBand}. Requires
 * to work with {@link SelectableChannel}.
 *
 * @author Shuyang Zhou
 */
public class SelectorIntraBand extends BaseIntraBand {

	public SelectorIntraBand(long defaultTimeout) throws IOException {
		super(defaultTimeout);

		selector = Selector.open();
		registerQueue =
			new ConcurrentLinkedQueue<FutureTask<RegistrationReference>>();
		pollingThread = THREAD_FACTORY.newThread(new PollingJob());

		pollingThread.start();
	}

	@Override
	public void close() throws InterruptedException, IOException {
		selector.close();

		pollingThread.interrupt();
		pollingThread.join(defaultTimeout);

		super.close();
	}

	public RegistrationReference registerChannel(Channel channel)
		throws IOException {

		if (channel == null) {
			throw new NullPointerException("Channel is null");
		}

		if (!(channel instanceof ScatteringByteChannel)) {
			throw new IllegalArgumentException(
				"Channel is not type of ScatteringByteChannel");
		}

		if (!(channel instanceof GatheringByteChannel)) {
			throw new IllegalArgumentException(
				"Channel is not type of GatheringByteChannel");
		}

		if (!(channel instanceof SelectableChannel)) {
			throw new IllegalArgumentException(
				"Channel is not type of SelectableChannel");
		}

		SelectableChannel selectableChannel = (SelectableChannel)channel;

		if ((selectableChannel.validOps() & SelectionKey.OP_READ) == 0) {
			throw new IllegalArgumentException(
				"Channel is not valid for reading");
		}

		if ((selectableChannel.validOps() & SelectionKey.OP_WRITE) == 0) {
			throw new IllegalArgumentException(
				"Channel is not valid for writing");
		}

		ensureOpen();

		selectableChannel.configureBlocking(false);

		FutureTask<RegistrationReference> registerFutureTask =
			new FutureTask<RegistrationReference>(
				new RegisterCallable(selectableChannel, selectableChannel));

		registerQueue.offer(registerFutureTask);

		selector.wakeup();

		try {
			return registerFutureTask.get();
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	public RegistrationReference registerChannel(
			ScatteringByteChannel scatteringByteChannel,
			GatheringByteChannel gatheringByteChannel)
		throws IOException {

		if (scatteringByteChannel == null) {
			throw new NullPointerException("Scattering byte channel is null");
		}

		if (gatheringByteChannel == null) {
			throw new NullPointerException("Gathering byte channel is null");
		}

		if (!(scatteringByteChannel instanceof SelectableChannel)) {
			throw new IllegalArgumentException(
				"Scattering byte channel is not type of SelectableChannel");
		}

		if (!(gatheringByteChannel instanceof SelectableChannel)) {
			throw new IllegalArgumentException(
				"Gathering byte channel is not type of SelectableChannel");
		}

		SelectableChannel readSelectableChannel =
			(SelectableChannel)scatteringByteChannel;
		SelectableChannel writeSelectableChannel =
			(SelectableChannel)gatheringByteChannel;

		if ((readSelectableChannel.validOps() & SelectionKey.OP_READ) == 0) {
			throw new IllegalArgumentException(
				"Scattering byte channel is not valid for reading");
		}

		if ((writeSelectableChannel.validOps() & SelectionKey.OP_WRITE) == 0) {
			throw new IllegalArgumentException(
				"Gathering byte channel is not valid for writing");
		}

		ensureOpen();

		readSelectableChannel.configureBlocking(false);
		writeSelectableChannel.configureBlocking(false);

		FutureTask<RegistrationReference> registerFutureTask =
			new FutureTask<RegistrationReference>(
				new RegisterCallable(
					readSelectableChannel, writeSelectableChannel));

		registerQueue.offer(registerFutureTask);

		selector.wakeup();

		try {
			return registerFutureTask.get();
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected void doSendDatagram(
		RegistrationReference registrationReference, Datagram datagram) {

		SelectionKeyRegistrationReference selectionKeyRegistrationReference =
			(SelectionKeyRegistrationReference)registrationReference;

		SelectionKey writeSelectionKey =
			selectionKeyRegistrationReference.writeSelectionKey;

		ChannelContext channelContext =
			(ChannelContext)writeSelectionKey.attachment();

		Queue<Datagram> sendingQueue = channelContext.getSendingQueue();

		sendingQueue.offer(datagram);

		synchronized (writeSelectionKey) {
			int ops = writeSelectionKey.interestOps();

			if ((ops & SelectionKey.OP_WRITE) == 0) {
				ops |= SelectionKey.OP_WRITE;

				writeSelectionKey.interestOps(ops);

				selector.wakeup();
			}
		}
	}

	protected void registerChannels() {
		FutureTask<RegistrationReference> registerFuturetask = null;

		while ((registerFuturetask = registerQueue.poll()) != null) {
			registerFuturetask.run();
		}
	}

	protected static final ThreadFactory THREAD_FACTORY =
		new NamedThreadFactory(
			SelectorIntraBand.class.getSimpleName().concat("-SelectorPoller"),
			Thread.NORM_PRIORITY, SelectorIntraBand.class.getClassLoader());

	protected final Thread pollingThread;
	protected final Queue<FutureTask<RegistrationReference>> registerQueue;
	protected final Selector selector;

	protected class RegisterCallable
		implements Callable<RegistrationReference> {

		public RegisterCallable(
			SelectableChannel readSelectableChannel,
			SelectableChannel writeSelectableChannel) {

			_readSelectableChannel = readSelectableChannel;
			_writeSelectableChannel = writeSelectableChannel;
		}

		public RegistrationReference call() throws Exception {
			if (_readSelectableChannel == _writeSelectableChannel) {

				// Register channel with zero interest, no dispatch will happen
				// before ChannelContext is ready. This ensures thread safe
				// publication for ChannelContext._registrationReference

				SelectionKey selectionKey = _readSelectableChannel.register(
					selector, 0);

				SelectionKeyRegistrationReference
					selectionKeyRegistrationReference =
						new SelectionKeyRegistrationReference(
							SelectorIntraBand.this, selectionKey, selectionKey);

				ChannelContext channelContext = new ChannelContext(
					new ConcurrentLinkedQueue<Datagram>());

				channelContext.setRegistrationReference(
					selectionKeyRegistrationReference);

				selectionKey.attach(channelContext);

				// Alter interest ops after ChannelContext preparation

				selectionKey.interestOps(
					SelectionKey.OP_READ | SelectionKey.OP_WRITE);

				return selectionKeyRegistrationReference;
			}
			else {

				// Register channels with zero interest, no dispatch will happen
				// before ChannelContexts are ready. This ensures thread safe
				// publication for ChannelContext._registrationReference

				SelectionKey readSelectionKey = _readSelectableChannel.register(
					selector, 0);

				SelectionKey writeSelectionKey =
					_writeSelectableChannel.register(selector, 0);

				SelectionKeyRegistrationReference
					selectionKeyRegistrationReference =
						new SelectionKeyRegistrationReference(
							SelectorIntraBand.this, readSelectionKey,
							writeSelectionKey);

				ChannelContext channelContext = new ChannelContext(
					new ConcurrentLinkedQueue<Datagram>());

				channelContext.setRegistrationReference(
					selectionKeyRegistrationReference);

				readSelectionKey.attach(channelContext);
				writeSelectionKey.attach(channelContext);

				// Alter interest ops after ChannelContexts preparation

				readSelectionKey.interestOps(SelectionKey.OP_READ);
				writeSelectionKey.interestOps(SelectionKey.OP_WRITE);

				return selectionKeyRegistrationReference;
			}
		}

		private final SelectableChannel _readSelectableChannel;
		private final SelectableChannel _writeSelectableChannel;

	}

	private void _processReading(SelectionKey selectionKey) {
		ScatteringByteChannel scatteringByteChannel =
			(ScatteringByteChannel)selectionKey.channel();

		ChannelContext channelContext =
			(ChannelContext)selectionKey.attachment();

		handleReading(scatteringByteChannel, channelContext);
	}

	private void _processWriting(SelectionKey selectionKey) {
		GatheringByteChannel gatheringByteChannel =
			(GatheringByteChannel)selectionKey.channel();

		ChannelContext channelContext =
			(ChannelContext)selectionKey.attachment();

		Queue<Datagram> sendingQueue = channelContext.getSendingQueue();

		boolean ready = false;

		if (channelContext.getWritingDatagram() == null) {
			Datagram datagram = sendingQueue.poll();

			if (datagram != null) {
				channelContext.setWritingDatagram(datagram);

				ready = true;
			}
		}
		else {
			ready = true;
		}

		if (ready) {
			if (handleWriting(gatheringByteChannel, channelContext)) {
				if (sendingQueue.isEmpty()) {

					// Channel is still writable, but there is nothing to send,
					// backoff to prevent unnecessary busy spinning.

					int ops = selectionKey.interestOps();

					ops &= ~SelectionKey.OP_WRITE;

					synchronized (selectionKey) {
						if (sendingQueue.isEmpty()) {
							selectionKey.interestOps(ops);
						}
					}
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SelectorIntraBand.class);

	private class PollingJob implements Runnable {

		public void run() {
			try {
				try {
					while (true) {
						int readyCount = selector.select();

						if (readyCount > 0) {
							Set<SelectionKey> selectionKeys =
								selector.selectedKeys();

							Iterator<SelectionKey> iterator =
								selectionKeys.iterator();

							while (iterator.hasNext()) {
								SelectionKey selectionKey = iterator.next();

								iterator.remove();

								try {
									if (selectionKey.isReadable()) {
										_processReading(selectionKey);
									}

									if (selectionKey.isWritable()) {
										_processWriting(selectionKey);
									}
								}
								catch (CancelledKeyException cke) {

									// Concurrent cancelling, move to next key

								}
							}
						}
						else if (!selector.isOpen()) {
							break;
						}

						registerChannels();
						cleanUpTimeoutResponseWaitingDatagrams();
					}
				}
				finally {

					// Protection, no matter for what reason, leaving the above
					// while loop must follow by selector closure.

					selector.close();
				}
			}
			catch (ClosedSelectorException cse) {
				if (_log.isInfoEnabled()) {
					_log.info(
						Thread.currentThread().getName() +
							" exiting gracefully on Selector closure");
				}
			}
			catch (Throwable t) {
				_log.error(
					Thread.currentThread().getName() + " exiting exceptionally",
					t);
			}

			// Flush out pending register requests to unblock their invokers,
			// this will cause them to receive ClosedSelectorException

			registerChannels();

			responseWaitingMap.clear();
			timeoutMap.clear();
		}

	}

}