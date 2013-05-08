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
import com.liferay.portal.kernel.nio.intraband.BaseAsyncDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class RPCDatagramReceiveHandler extends BaseAsyncDatagramReceiveHandler {

	@Override
	protected void doReceive(
			RegistrationReference registrationReference, Datagram datagram)
		throws Exception {

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

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

}