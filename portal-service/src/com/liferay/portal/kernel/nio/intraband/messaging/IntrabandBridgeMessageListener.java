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

package com.liferay.portal.kernel.nio.intraband.messaging;

import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;

/**
 * @author Shuyang Zhou
 */
public class IntrabandBridgeMessageListener implements MessageListener {

	public IntrabandBridgeMessageListener(
		RegistrationReference registrationReference) {

		_registrationReference = registrationReference;

		_intraband = registrationReference.getIntraband();

		SystemDataType systemDataType = SystemDataType.MESSAGE;

		_messageType = systemDataType.getValue();
	}

	public void receive(Message message) {
		Serializer serializer = new Serializer();

		serializer.writeObject(message);

		_intraband.sendDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(
				_messageType, serializer.toByteBuffer()));
	}

	private final Intraband _intraband;
	private final byte _messageType;
	private final RegistrationReference _registrationReference;

}