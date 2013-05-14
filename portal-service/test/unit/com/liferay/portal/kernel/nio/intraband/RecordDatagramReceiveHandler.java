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

/**
 * @author Shuyang Zhou
 */
public class RecordDatagramReceiveHandler implements DatagramReceiveHandler {

	public Datagram getReceiveDatagram() {
		return _datagram;
	}

	@Override
	public void receive(
		RegistrationReference registrationReference, Datagram datagram) {

		_datagram = datagram;

		throw new RuntimeException("RecordDatagramReceiveHandler");
	}

	private volatile Datagram _datagram;

}