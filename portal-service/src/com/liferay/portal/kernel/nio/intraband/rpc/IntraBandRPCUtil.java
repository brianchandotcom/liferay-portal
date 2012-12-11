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

package com.liferay.portal.kernel.nio.intraband.rpc;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class IntraBandRPCUtil {

	public static <V extends Serializable> V execute(
			RegistrationReference registrationReference,
			ProcessCallable<V> processCallable)
		throws IntraBandRPCException {

		Serializer serializer = new Serializer();

		serializer.writeObject(processCallable);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Datagram requestDatagram = Datagram.createRequestDatagram(
			SystemDataType.RPC.getValue(), byteBuffer);

		IntraBand intraBand = registrationReference.getIntraBand();

		try {
			Datagram responseDatagram = intraBand.sendSyncDatagram(
				registrationReference, requestDatagram);

			Deserializer deserializer = new Deserializer(
				responseDatagram.getData());

			return deserializer.readObject();
		}
		catch (Exception e) {
			throw new IntraBandRPCException(e);
		}
	}

	public static <V extends Serializable> V execute(
			RegistrationReference registrationReference,
			ProcessCallable<V> processCallable, long timeout, TimeUnit timeUnit)
		throws IntraBandRPCException {

		Serializer serializer = new Serializer();

		serializer.writeObject(processCallable);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Datagram requestDatagram = Datagram.createRequestDatagram(
			SystemDataType.RPC.getValue(), byteBuffer);

		IntraBand intraBand = registrationReference.getIntraBand();

		try {
			Datagram responseDatagram = intraBand.sendSyncDatagram(
				registrationReference, requestDatagram, timeout, timeUnit);

			Deserializer deserializer = new Deserializer(
				responseDatagram.getData());

			return deserializer.readObject();
		}
		catch (Exception e) {
			throw new IntraBandRPCException(e);
		}
	}

}