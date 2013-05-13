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

import java.nio.channels.Channel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/**
 * @author Shuyang Zhou
 */
public class MockIntraband extends BaseIntraband {

	public MockIntraband() {
		this(10000);
	}

	public MockIntraband(long defaultTimeout) {
		super(defaultTimeout);
	}

	public Datagram getDatagram() {
		return _datagram;
	}

	public RegistrationReference getRegistrationReference() {
		return _registrationReference;
	}

	@Override
	public RegistrationReference registerChannel(Channel duplexChannel) {
		return new MockRegistrationReference(
			(ScatteringByteChannel)duplexChannel,
			(GatheringByteChannel)duplexChannel);
	}

	@Override
	public RegistrationReference registerChannel(
		ScatteringByteChannel scatteringByteChannel,
		GatheringByteChannel gatheringByteChannel) {

		return new MockRegistrationReference(
			scatteringByteChannel, gatheringByteChannel);
	}

	@Override
	protected void doSendDatagram(
		RegistrationReference registrationReference, Datagram datagram) {

		_registrationReference = registrationReference;
		_datagram = datagram;
	}

	private Datagram _datagram;
	private RegistrationReference _registrationReference;

}