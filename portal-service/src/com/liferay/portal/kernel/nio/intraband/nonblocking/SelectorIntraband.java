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
import com.liferay.portal.kernel.nio.intraband.BaseIntraband;
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
 * @author Shuyang Zhou
 */
public class SelectorIntraband extends BaseIntraband {

	public SelectorIntraband(long defaultTimeout) throws IOException {
		super(defaultTimeout);

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

		if (!(channel instanceof GatheringByteChannel)) {
			throw new IllegalArgumentException(
				"Channel is not of type GatheringByteChannel");
		}

		if (!(channel instanceof ScatteringByteChannel)) {
			throw new IllegalArgumentException(
				"Channel is not of type ScatteringByteChannel");
		}

		if (!(channel instanceof SelectableChannel)) {
			throw new IllegalArgumentException(
				"Channel is not of type SelectableChannel");
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
				"Scattering byte channel is not of type SelectableChannel");
		}

		if (!(gatheringByteChannel instanceof SelectableChannel)) {
			throw new IllegalArgumentException(
				"Gathering byte channel is not of type SelectableChannel");
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

	@Override
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

	protected static final ThreadFactory threadFactory =
		new NamedThreadFactory(
			SelectorIntraband.class + ".threadFactory", Thread.NORM_PRIORITY,
			SelectorIntraband.class.getClassLoader());

	protected final Thread pollingThread = threadFactory.newThread(
		new PollingJob());
	protected final Queue<FutureTask<RegistrationReference>> registerQueue =
		new ConcurrentLinkedQueue<FutureTask<RegistrationReference>>();
	protected final Selector selector = Selector.open();

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
				// before channel context is ready. This ensures thread safe
				// publication for ChannelContext#_registrationReference.

				SelectionKey selectionKey = _readSelectableChannel.register(
					selector, 0);

				SelectionKeyRegistrationReference
					selectionKeyRegistrationReference =
						new SelectionKeyRegistrationReference(
							SelectorIntraband.this, selectionKey, selectionKey);

				ChannelContext channelContext = new ChannelContext(
					new ConcurrentLinkedQueue<Datagram>());

				channelContext.setRegistrationReference(
					selectionKeyRegistrationReference);

				selectionKey.attach(channelContext);

				// Alter interest ops after preparing the channel context

				selectionKey.interestOps(SelectionKey.OP_READ);

				return selectionKeyRegistrationReference;
			}
			else {

				// Register channels with zero interest, no dispatch will happen
				// before channel contexts are ready. This ensures thread safe
				// publication for ChannelContext#_registrationReference.

				SelectionKey readSelectionKey = _readSelectableChannel.register(
					selector, 0);

				SelectionKey writeSelectionKey =
					_writeSelectableChannel.register(selector, 0);

				SelectionKeyRegistrationReference
					selectionKeyRegistrationReference =
						new SelectionKeyRegistrationReference(
							SelectorIntraband.this, readSelectionKey,
							writeSelectionKey);

				ChannelContext channelContext = new ChannelContext(
					new ConcurrentLinkedQueue<Datagram>());

				channelContext.setRegistrationReference(
					selectionKeyRegistrationReference);

				readSelectionKey.attach(channelContext);
				writeSelectionKey.attach(channelContext);

				// Alter interest ops after ChannelContexts preparation

				readSelectionKey.interestOps(SelectionKey.OP_READ);

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

		if (channelContext.getWritingDatagram() == null) {
			channelContext.setWritingDatagram(sendingQueue.poll());
		}

		boolean backOff = false;

		if (channelContext.getWritingDatagram() != null) {
			if (handleWriting(gatheringByteChannel, channelContext)) {
				if (sendingQueue.isEmpty()) {
					backOff = true;
				}
			}
		}
		else {
			backOff = true;
		}

		if (backOff) {

			// Channel is still writable, but there is nothing to send, back off
			// to prevent unnecessary busy spinning.

			int ops = selectionKey.interestOps();

			ops &= ~SelectionKey.OP_WRITE;

			synchronized (selectionKey) {
				if (sendingQueue.isEmpty()) {
					selectionKey.interestOps(ops);
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SelectorIntraband.class);

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
					selector.close();
				}
			}
			catch (ClosedSelectorException cse) {
				if (_log.isInfoEnabled()) {
					Thread currentThread = Thread.currentThread();

					_log.info(
						currentThread.getName() +
							" exiting gracefully on selector closure");
				}
			}
			catch (Throwable t) {
				Thread currentThread = Thread.currentThread();

				_log.error(
					currentThread.getName() + " exiting exceptionally", t);
			}

			// Flush out pending register requests to unblock their invokers,
			// this will cause them to receive a ClosedSelectorException

			registerChannels();

			responseWaitingMap.clear();
			timeoutMap.clear();
		}

	}

}