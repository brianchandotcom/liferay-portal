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

package com.liferay.portal.kernel.nio.intraband.rpc;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class BootstrapRPCDatagramReceiveHandler
	implements DatagramReceiveHandler {

	@Override
	public void receive(
		RegistrationReference registrationReference, Datagram datagram) {

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		try {
			ProcessCallable<? extends Serializable> processCallable =
				deserializer.readObject();

			Serializer serializer = new Serializer();

			Serializable result = processCallable.call();

			serializer.writeObject(result);

			Intraband intraband = registrationReference.getIntraband();

			intraband.sendDatagram(
				registrationReference,
				Datagram.createResponseDatagram(
					datagram, serializer.toByteBuffer()));
		}
		catch (Exception e) {
			_log.error("Unable to execute", e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BootstrapRPCDatagramReceiveHandler.class);

}