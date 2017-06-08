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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;

import java.io.IOException;

import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseIntraBand implements IntraBand {

	public BaseIntraBand(long defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
		responseWaitingMap = new ConcurrentHashMap<Long, Datagram>();
		timeoutMap = new ConcurrentSkipListMap<Long, Long>();
		datagramReceiveHandlersReference =
			new AtomicReference<DatagramReceiveHandler[]>(
				new DatagramReceiveHandler[256]);
		open = true;
		sequenceIdGenerator = new AtomicLong();
	}

	public void close() throws InterruptedException, IOException {
		open = false;
		datagramReceiveHandlersReference.set(null);
	}

	public DatagramReceiveHandler[] getDatagramReceiveHandlers() {
		ensureOpen();

		DatagramReceiveHandler[] datagramReceiveHandlers =
			datagramReceiveHandlersReference.get();

		DatagramReceiveHandler[] copyDatagramReceiveHandlers =
			new DatagramReceiveHandler[256];

		System.arraycopy(
			datagramReceiveHandlers, 0, copyDatagramReceiveHandlers, 0, 256);

		return copyDatagramReceiveHandlers;
	}

	public boolean isOpen() {
		return open;
	}

	public DatagramReceiveHandler registerDatagramReceiveHandler(
		byte type, DatagramReceiveHandler datagramReceiveHandler) {

		ensureOpen();

		int index = ((int)type) & 0xFF;

		DatagramReceiveHandler oldDatagramReceiveHandler = null;
		DatagramReceiveHandler[] datagramReceiveHandlers = null;
		DatagramReceiveHandler[] copyDatagramReceiveHandlers = null;

		// CAS registering

		do {
			datagramReceiveHandlers = datagramReceiveHandlersReference.get();

			copyDatagramReceiveHandlers = new DatagramReceiveHandler[256];

			System.arraycopy(
				datagramReceiveHandlers, 0, copyDatagramReceiveHandlers, 0,
				256);

			oldDatagramReceiveHandler = copyDatagramReceiveHandlers[index];

			copyDatagramReceiveHandlers[index] = datagramReceiveHandler;
		}
		while (
			!datagramReceiveHandlersReference.compareAndSet(
				datagramReceiveHandlers, copyDatagramReceiveHandlers));

		return oldDatagramReceiveHandler;
	}

	public void sendDatagram(
		RegistrationReference registrationReference, Datagram datagram) {

		if (registrationReference == null) {
			throw new NullPointerException("RegistrationReference is null");
		}

		if (!registrationReference.isValid()) {
			throw new IllegalArgumentException(
				"RegistrationReference is not valid");
		}

		if (datagram == null) {
			throw new NullPointerException("Datagram is null");
		}

		ensureOpen();

		doSendDatagram(registrationReference, datagram);
	}

	public <A> void sendDatagram(
		RegistrationReference registrationReference, Datagram datagram,
		A attachment, EnumSet<CompletionHandler.CompletionType> completionTypes,
		CompletionHandler<A> completionHandler) {

		sendDatagram(
			registrationReference, datagram, attachment, completionTypes,
			completionHandler, defaultTimeout, TimeUnit.MILLISECONDS);
	}

	public <A> void sendDatagram(
		RegistrationReference registrationReference, Datagram datagram,
		A attachment, EnumSet<CompletionType> completionTypes,
		CompletionHandler<A> completionHandler, long timeout,
		TimeUnit timeUnit) {

		if (registrationReference == null) {
			throw new NullPointerException("RegistrationReference is null");
		}

		if (!registrationReference.isValid()) {
			throw new IllegalArgumentException(
				"RegistrationReference is not valid");
		}

		if (datagram == null) {
			throw new NullPointerException("Datagram is null");
		}

		if (completionTypes == null) {
			throw new NullPointerException("CompletionType set is null");
		}

		if (completionTypes.isEmpty()) {
			throw new IllegalArgumentException("CompletionType set is empty");
		}

		if (completionHandler == null) {
			throw new NullPointerException("CompleteHandler is null");
		}

		if (timeUnit == null) {
			throw new NullPointerException("TimeUnit is null");
		}

		if (timeout <= 0) {
			timeout = defaultTimeout;
		}
		else {
			timeout = timeUnit.toMillis(timeout);
		}

		ensureOpen();

		datagram.setAckRequest(
			completionTypes.contains(CompletionType.DELIVERED));
		datagram.attachment = attachment;
		datagram.completionHandler =
			(CompletionHandler<Object>)completionHandler;
		datagram.completionTypes = completionTypes;

		if (datagram.getSequenceId() == 0) {
			datagram.setSequenceId(generateSequenceId());
		}

		datagram.timeout = timeout;

		if (completionTypes.contains(CompletionType.DELIVERED) ||
			completionTypes.contains(CompletionType.REPLIED)) {

			addResponseWaitingDatagram(datagram);
		}

		doSendDatagram(registrationReference, datagram);
	}

	public Datagram sendSyncDatagram(
			RegistrationReference registrationReference, Datagram datagram)
		throws InterruptedException, IOException, TimeoutException {

		return sendSyncDatagram(
			registrationReference, datagram, defaultTimeout,
			TimeUnit.MILLISECONDS);
	}

	public Datagram sendSyncDatagram(
			RegistrationReference registrationReference, Datagram datagram,
			long timeout, TimeUnit timeUnit)
		throws InterruptedException, IOException, TimeoutException {

		if (registrationReference == null) {
			throw new NullPointerException("RegistrationReference is null");
		}

		if (!registrationReference.isValid()) {
			throw new IllegalArgumentException(
				"RegistrationReference is not valid");
		}

		if (datagram == null) {
			throw new NullPointerException("Datagram is null");
		}

		if (timeUnit == null) {
			throw new NullPointerException("TimeUnit is null");
		}

		if (timeout <= 0) {
			timeout = defaultTimeout;
		}
		else {
			timeout = timeUnit.toMillis(timeout);
		}

		ensureOpen();

		return doSendSyncDatagram(registrationReference, datagram, timeout);
	}

	public DatagramReceiveHandler unregisterDatagramReceiveHandler(byte type) {
		return registerDatagramReceiveHandler(type, null);
	}

	protected void addResponseWaitingDatagram(Datagram requestDatagram) {
		long sequenceId = requestDatagram.getSequenceId();

		long expireTime = System.currentTimeMillis() + requestDatagram.timeout;

		requestDatagram.expireTime = expireTime;

		responseWaitingMap.put(sequenceId, requestDatagram);

		timeoutMap.put(expireTime, sequenceId);
	}

	protected void cleanUpTimeoutResponseWaitingDatagrams() {
		long currentTime = System.currentTimeMillis();

		Map<Long, Long> removeMap = timeoutMap.headMap(currentTime, true);

		if (!removeMap.isEmpty()) {
			Iterator<Map.Entry<Long, Long>> removeIterator =
				removeMap.entrySet().iterator();

			while (removeIterator.hasNext()) {
				Map.Entry<Long, Long> removeEntry = removeIterator.next();

				removeIterator.remove();

				Long sequenceId = removeEntry.getValue();

				Datagram datagram = responseWaitingMap.remove(sequenceId);

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Removed timeout response waiting Datagram " +
						datagram);
				}

				datagram.completionHandler.timeouted(datagram.attachment);
			}
		}
	}

	protected abstract void doSendDatagram(
		RegistrationReference registrationReference, Datagram datagram);

	protected Datagram doSendSyncDatagram(
			RegistrationReference registrationReference, Datagram datagram,
			long timeout)
		throws InterruptedException, IOException, TimeoutException {

		SendSyncDatagramCompletionHandler sendSyncDatagramCompletionHandler =
			new SendSyncDatagramCompletionHandler();

		datagram.completionHandler = sendSyncDatagramCompletionHandler;
		datagram.completionTypes = REPLIED_ENUM_SET;

		if (datagram.getSequenceId() == 0) {
			datagram.setSequenceId(generateSequenceId());
		}

		datagram.timeout = timeout;

		addResponseWaitingDatagram(datagram);

		doSendDatagram(registrationReference, datagram);

		return sendSyncDatagramCompletionHandler.waitResult(timeout);
	}

	protected void ensureOpen() {
		if (!isOpen()) {
			throw new ClosedIntraBandException();
		}
	}

	protected long generateSequenceId() {
		long sequenceId = sequenceIdGenerator.incrementAndGet();

		if (sequenceId <= 0) {

			// overflow, reset

			// Here we assume long number scope is large enough to keep a huge
			// window time between the oldest and latest response waiting
			// Datagrams.
			// In a real system, far before latest response waiting Datagram's
			// id catching up the oldest one causing id confliction, we shall
			// see OOM already. Assume the system has no other pieces costing
			// memory (which is so not true), to see an id confliction, we need
			// to hold up 2^63 references to Datagram(Positive id only). Even we
			// ignore the data inside Datagram, just by references themselves,
			// we need 2^65 byte = 32 EB(Exbibyte) memory to hold up, which is
			// impossible to any current exist computer system in the world.

			// Spinning reseting negative id generator back to start

			while (true) {
				if (sequenceIdGenerator.compareAndSet(sequenceId, 1)) {
					return 1;
				}

				sequenceId = sequenceIdGenerator.incrementAndGet();

				// Another concurrent reset just happened

				if (sequenceId > 0) {

					return sequenceId;
				}

			}
		}

		return sequenceId;
	}

	protected void handleReading(
		ScatteringByteChannel scatteringByteChannel,
		ChannelContext channelContext) {

		Datagram datagram = channelContext.getReadingDatagram();

		if (datagram == null) {
			datagram = Datagram.createReceiveDatagram();

			channelContext.setReadingDatagram(datagram);
		}

		try {
			if (datagram.readFrom(scatteringByteChannel)) {
				channelContext.setReadingDatagram(
					Datagram.createReceiveDatagram());

				if (datagram.isAckResponse()) {

					// 1) ACK response

					Datagram requestDatagram = removeResponseWaitingDatagram(
						datagram);

					if (requestDatagram == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Dropped ownerless ACK response " + datagram);
						}
					}
					else {
						requestDatagram.completionHandler.delivered(
							requestDatagram.attachment);
					}
				}
				else if (datagram.isResponse()) {

					// 2) Normal response

					Datagram requestDatagram = removeResponseWaitingDatagram(
						datagram);

					if (requestDatagram == null) {
						if (_log.isWarnEnabled()) {
							_log.warn("Dropped ownerless response " + datagram);
						}
					}
					else if (requestDatagram.completionTypes.contains(
						CompletionType.REPLIED)) {

						requestDatagram.completionHandler.replied(
							requestDatagram.attachment, datagram);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn("Dropped unconcerned response " + datagram);
					}
				}
				else {

					// 3) ACK request

					if (datagram.isAckRequest()) {
						Datagram ackResponseDatagram =
							Datagram.createACKResponseDatagram(
								datagram.getSequenceId());

						doSendDatagram(
							channelContext.getRegistrationReference(),
							ackResponseDatagram);
					}

					// 4) Normal request, DatagramReceiveHandler dispatching

					int index = ((int)datagram.getType()) & 0xFF;

					DatagramReceiveHandler datagramReceiveHandler =
						datagramReceiveHandlersReference.get()[index];

					if (datagramReceiveHandler == null) {
						if (_log.isWarnEnabled()) {
							_log.warn("Dropped ownerless request " + datagram);
						}
					}
					else {
						try {
							datagramReceiveHandler.receive(
								channelContext.getRegistrationReference(),
								datagram);
						}
						catch (Throwable t) {
							_log.error("Dispatching failure.", t);
						}
					}
				}
			}
		}
		catch (IOException ioe) {
			RegistrationReference registrationReference =
				channelContext.getRegistrationReference();

			registrationReference.cancelRegistration();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Broken read channel, unregister " + registrationReference,
					ioe);
			}
			else if (_log.isInfoEnabled()) {
				_log.info(
					"Broken read channel, unregister " + registrationReference);
			}
		}
	}

	protected boolean handleWriting(
		GatheringByteChannel gatheringByteChannel,
		ChannelContext channelContext) {

		Datagram datagram = channelContext.getWritingDatagram();

		try {
			if (datagram.writeTo(gatheringByteChannel)) {
				channelContext.setWritingDatagram(null);

				// Notify CompleteHandler if there is any.

				EnumSet<CompletionType> interestCompletionTypes =
					datagram.completionTypes;

				if (interestCompletionTypes != null) {
					if (interestCompletionTypes.contains(
							CompletionType.SUBMITTED)) {

						Object attachment = datagram.attachment;

						CompletionHandler<Object> completeHandler =
							datagram.completionHandler;

						completeHandler.submitted(attachment);
					}
				}

				return true;
			}
			else {

				// Channel is no longer writable

				return false;
			}
		}
		catch (IOException ioe) {
			RegistrationReference registrationReference =
				channelContext.getRegistrationReference();

			registrationReference.cancelRegistration();

			if (datagram.completionHandler != null) {
				datagram.completionHandler.failed(datagram.attachment, ioe);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Broken write channel, unregister " + registrationReference,
					ioe);
			}
			else if (_log.isInfoEnabled()) {
				_log.info(
					"Broken write channel, unregister " +
					registrationReference);
			}

			return false;
		}
	}

	protected Datagram removeResponseWaitingDatagram(
		Datagram responseDatagram) {

		long sequenceId = responseDatagram.getSequenceId();

		Datagram requestDatagram = responseWaitingMap.remove(sequenceId);

		if (requestDatagram != null) {
			timeoutMap.remove(requestDatagram.expireTime);
		}

		return requestDatagram;
	}

	protected static final EnumSet<CompletionType> REPLIED_ENUM_SET =
		EnumSet.of(CompletionType.REPLIED);

	protected final long defaultTimeout;
	protected final AtomicReference<DatagramReceiveHandler[]>
		datagramReceiveHandlersReference;
	protected volatile boolean open;
	protected final Map<Long, Datagram> responseWaitingMap;
	protected final AtomicLong sequenceIdGenerator;
	protected final NavigableMap<Long, Long> timeoutMap;

	protected static class SendSyncDatagramCompletionHandler
		implements CompletionHandler<Object> {

		public void delivered(Object attachment) {
		}

		public void failed(Object attachment, IOException ioe) {

			// Must set before count down to ensure memory visibility

			_ioe = ioe;
			_countDownLatch.countDown();
		}

		public void replied(Object attachment, Datagram datagram) {

			// Must set before count down to ensure memory visibility

			_datagram = datagram;
			_countDownLatch.countDown();
		}

		public void submitted(Object attachment) {
		}

		public void timeouted(Object attachment) {
		}

		public Datagram waitResult(long timeout)
			throws InterruptedException, IOException, TimeoutException {

			boolean result = _countDownLatch.await(
				timeout, TimeUnit.MILLISECONDS);

			if (!result) {
				throw new TimeoutException("Result waiting timeout");
			}

			if (_ioe != null) {
				throw _ioe;
			}

			return _datagram;
		}

		// This final is crucial for thread safety

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);

		// Piggyback memory visibility on _countDownLatch

		private Datagram _datagram;
		private IOException _ioe;

	}

	private static Log _log = LogFactoryUtil.getLog(BaseIntraBand.class);

}