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
import java.util.Set;
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
public abstract class BaseIntraband implements Intraband {

	public BaseIntraband(long defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}

	@Override
	@SuppressWarnings("unused")
	public void close() throws InterruptedException, IOException {
		datagramReceiveHandlersReference.set(null);

		open = false;
	}

	@Override
	public DatagramReceiveHandler[] getDatagramReceiveHandlers() {
		ensureOpen();

		DatagramReceiveHandler[] datagramReceiveHandlers =
			datagramReceiveHandlersReference.get();

		return datagramReceiveHandlers.clone();
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public DatagramReceiveHandler registerDatagramReceiveHandler(
		byte type, DatagramReceiveHandler datagramReceiveHandler) {

		ensureOpen();

		int index = type & 0xFF;

		DatagramReceiveHandler oldDatagramReceiveHandler = null;
		DatagramReceiveHandler[] datagramReceiveHandlers = null;
		DatagramReceiveHandler[] copyDatagramReceiveHandlers = null;

		do {
			datagramReceiveHandlers = datagramReceiveHandlersReference.get();

			copyDatagramReceiveHandlers = datagramReceiveHandlers.clone();

			oldDatagramReceiveHandler = copyDatagramReceiveHandlers[index];

			copyDatagramReceiveHandlers[index] = datagramReceiveHandler;
		}
		while (
			!datagramReceiveHandlersReference.compareAndSet(
				datagramReceiveHandlers, copyDatagramReceiveHandlers));

		return oldDatagramReceiveHandler;
	}

	@Override
	public void sendDatagram(
		RegistrationReference registrationReference, Datagram datagram) {

		if (registrationReference == null) {
			throw new NullPointerException("Registration reference is null");
		}

		if (!registrationReference.isValid()) {
			throw new IllegalArgumentException(
				"Registration reference is invalid");
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
			throw new NullPointerException("Registration reference is null");
		}

		if (!registrationReference.isValid()) {
			throw new IllegalArgumentException(
				"Registration reference is invalid");
		}

		if (datagram == null) {
			throw new NullPointerException("Datagram is null");
		}

		if (completionTypes == null) {
			throw new NullPointerException("Completion type set is null");
		}

		if (completionTypes.isEmpty()) {
			throw new IllegalArgumentException("Completion type set is empty");
		}

		if (completionHandler == null) {
			throw new NullPointerException("Complete handler is null");
		}

		if (timeUnit == null) {
			throw new NullPointerException("Time unit is null");
		}

		if (timeout <= 0) {
			timeout = defaultTimeout;
		}
		else {
			timeout = timeUnit.toMillis(timeout);
		}

		ensureOpen();

		datagram.attachment = attachment;
		datagram.completionHandler =
			(CompletionHandler<Object>)completionHandler;
		datagram.completionTypes = completionTypes;
		datagram.timeout = timeout;

		datagram.setAckRequest(
			completionTypes.contains(CompletionType.DELIVERED));

		if (datagram.getSequenceId() == 0) {
			datagram.setSequenceId(generateSequenceId());
		}

		if (completionTypes.contains(CompletionType.DELIVERED) ||
			completionTypes.contains(CompletionType.REPLIED)) {

			addResponseWaitingDatagram(datagram);
		}

		doSendDatagram(registrationReference, datagram);
	}

	@Override
	public Datagram sendSyncDatagram(
			RegistrationReference registrationReference, Datagram datagram)
		throws InterruptedException, IOException, TimeoutException {

		return sendSyncDatagram(
			registrationReference, datagram, defaultTimeout,
			TimeUnit.MILLISECONDS);
	}

	@Override
	public Datagram sendSyncDatagram(
			RegistrationReference registrationReference, Datagram datagram,
			long timeout, TimeUnit timeUnit)
		throws InterruptedException, IOException, TimeoutException {

		if (registrationReference == null) {
			throw new NullPointerException("Registration reference is null");
		}

		if (!registrationReference.isValid()) {
			throw new IllegalArgumentException(
				"Registration reference is invalid");
		}

		if (datagram == null) {
			throw new NullPointerException("Datagram is null");
		}

		if (timeUnit == null) {
			throw new NullPointerException("Time unit is null");
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

	@Override
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
		Map<Long, Long> map = timeoutMap.headMap(
			System.currentTimeMillis(), true);

		if (map.isEmpty()) {
			return;
		}

		Set<Map.Entry<Long, Long>> set = map.entrySet();

		Iterator<Map.Entry<Long, Long>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Long, Long> entry = iterator.next();

			iterator.remove();

			Long sequenceId = entry.getValue();

			Datagram datagram = responseWaitingMap.remove(sequenceId);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Removed timeout response waiting datagram " + datagram);
			}

			datagram.completionHandler.timeouted(datagram.attachment);
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
		datagram.timeout = timeout;

		if (datagram.getSequenceId() == 0) {
			datagram.setSequenceId(generateSequenceId());
		}

		addResponseWaitingDatagram(datagram);

		doSendDatagram(registrationReference, datagram);

		return sendSyncDatagramCompletionHandler.waitResult(timeout);
	}

	protected void ensureOpen() {
		if (!isOpen()) {
			throw new ClosedIntrabandException();
		}
	}

	protected long generateSequenceId() {
		long sequenceId = sequenceIdGenerator.getAndIncrement();

		if (sequenceId < 0) {

			// We assume a long primitive type can hold enough numbers to keep a
			// large window time between the earliest and the latest response
			// waiting datagrams. In a real system, we will run out of memory
			// long before the latest response waiting datagram's ID can catch
			// up to the earliest response waiting datagram's ID to cause an ID
			// conflict. Even if the sequence ID generator was the only code to
			// use memory (which will never be true), to see an ID conflict, we
			// need to hold up 2^63 references. Even if we did not factor in the
			// data inside the datagram, and considered just the references
			// themselves, we would need 2^65 byte or 32 EB (exbibyte) of
			// memory, which is impossible in existing computer systems.

			sequenceId += Long.MIN_VALUE;
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
					Datagram requestDatagram = removeResponseWaitingDatagram(
						datagram);

					if (requestDatagram == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Dropped ownerless ACK response " + datagram);
						}
					}
					else {
						CompletionHandler<Object> completionHandler =
							requestDatagram.completionHandler;

						completionHandler.delivered(requestDatagram.attachment);
					}
				}
				else if (datagram.isResponse()) {
					Datagram requestDatagram = removeResponseWaitingDatagram(
						datagram);

					if (requestDatagram == null) {
						if (_log.isWarnEnabled()) {
							_log.warn("Dropped ownerless response " + datagram);
						}
					}
					else {
						EnumSet<CompletionType> completionTypes =
							requestDatagram.completionTypes;

						if (completionTypes.contains(CompletionType.REPLIED)) {
							CompletionHandler<Object> completionHandler =
								requestDatagram.completionHandler;

							completionHandler.replied(
								requestDatagram.attachment, datagram);
						}
						else if (_log.isWarnEnabled()) {
							_log.warn(
								"Dropped unconcerned response " + datagram);
						}
					}
				}
				else {
					if (datagram.isAckRequest()) {
						Datagram ackResponseDatagram =
							Datagram.createACKResponseDatagram(
								datagram.getSequenceId());

						doSendDatagram(
							channelContext.getRegistrationReference(),
							ackResponseDatagram);
					}

					int index = datagram.getType() & 0xFF;

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
							_log.error("Unable to dispatch", t);
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

				EnumSet<CompletionType> completionTypes =
					datagram.completionTypes;

				if (completionTypes != null) {
					if (completionTypes.contains(CompletionType.SUBMITTED)) {
						CompletionHandler<Object> completeHandler =
							datagram.completionHandler;

						completeHandler.submitted(datagram.attachment);
					}
				}

				return true;
			}
			else {
				return false;
			}
		}
		catch (IOException ioe) {
			RegistrationReference registrationReference =
				channelContext.getRegistrationReference();

			registrationReference.cancelRegistration();

			CompletionHandler<Object> completionHandler =
				datagram.completionHandler;

			if (completionHandler != null) {
				completionHandler.failed(datagram.attachment, ioe);
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
		datagramReceiveHandlersReference =
			new AtomicReference<DatagramReceiveHandler[]>(
				new DatagramReceiveHandler[256]);
	protected volatile boolean open = true;
	protected final Map<Long, Datagram> responseWaitingMap =
		new ConcurrentHashMap<Long, Datagram>();
	protected final AtomicLong sequenceIdGenerator = new AtomicLong();
	protected final NavigableMap<Long, Long> timeoutMap =
		new ConcurrentSkipListMap<Long, Long>();

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

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);
		private Datagram _datagram;
		private IOException _ioe;

	}

	private static Log _log = LogFactoryUtil.getLog(BaseIntraband.class);

}