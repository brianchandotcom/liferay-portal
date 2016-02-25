/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import java.util.Queue;

/**
 * @author Shuyang Zhou
 */
public class ChannelContext {

	public ChannelContext(Queue<Datagram> sendingQueue) {
		_sendingQueue = sendingQueue;
	}

	public Datagram getReadingDatagram() {
		return _readingDatagram;
	}

	public RegistrationReference getRegistrationReference() {
		return _registrationReference;
	}

	public Queue<Datagram> getSendingQueue() {
		return _sendingQueue;
	}

	public Datagram getWritingDatagram() {
		return _writingDatagram;
	}

	public void setReadingDatagram(Datagram readingDatagram) {
		_readingDatagram = readingDatagram;
	}

	public void setRegistrationReference(
		RegistrationReference registrationReference) {

		_registrationReference = registrationReference;
	}

	public void setWritingDatagram(Datagram writingDatagram) {
		_writingDatagram = writingDatagram;
	}

	// All nonfinal fields are not thread safe. They depend on external logic to
	// do thread safe publication and must be accessed solely by polling threads
	// to remain thread safety.

	private Datagram _readingDatagram;
	private RegistrationReference _registrationReference;
	private final Queue<Datagram> _sendingQueue;
	private Datagram _writingDatagram;

}