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

import java.io.IOException;

import java.nio.channels.Channel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Shuyang Zhou
 */
public interface Intraband {

	public void close() throws InterruptedException, IOException;

	public DatagramReceiveHandler[] getDatagramReceiveHandlers();

	public boolean isOpen();

	public RegistrationReference registerChannel(Channel channel)
		throws IOException;

	public RegistrationReference registerChannel(
			ScatteringByteChannel scatteringByteChannel,
			GatheringByteChannel gatheringByteChannel)
		throws IOException;

	public DatagramReceiveHandler registerDatagramReceiveHandler(
		byte type, DatagramReceiveHandler datagramReceiveHandler);

	public void sendDatagram(
		RegistrationReference registrationReference, Datagram datagram);

	public <A> void sendDatagram(
		RegistrationReference registrationReference, Datagram datagram,
		A attachment, EnumSet<CompletionHandler.CompletionType> completionTypes,
		CompletionHandler<A> completionHandler);

	public <A> void sendDatagram(
		RegistrationReference registrationReference, Datagram datagram,
		A attachment, EnumSet<CompletionHandler.CompletionType> completionTypes,
		CompletionHandler<A> completionHandler, long timeout,
		TimeUnit timeUnit);

	public Datagram sendSyncDatagram(
			RegistrationReference registrationReference, Datagram datagram)
		throws InterruptedException, IOException, TimeoutException;

	public Datagram sendSyncDatagram(
			RegistrationReference registrationReference, Datagram datagram,
			long timeout, TimeUnit timeUnit)
		throws InterruptedException, IOException, TimeoutException;

	public DatagramReceiveHandler unregisterDatagramReceiveHandler(byte type);

}