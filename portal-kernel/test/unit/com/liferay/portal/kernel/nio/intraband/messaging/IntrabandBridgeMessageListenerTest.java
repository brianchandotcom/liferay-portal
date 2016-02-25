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

package com.liferay.portal.kernel.nio.intraband.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntrabandBridgeMessageListenerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		IntrabandBridgeMessageListener intrabandBridgeMessageListener =
			new IntrabandBridgeMessageListener(_mockRegistrationReference);

		Assert.assertSame(
			_mockIntraband,
			ReflectionTestUtil.getFieldValue(
				intrabandBridgeMessageListener, "_intraband"));
		Assert.assertSame(
			_mockRegistrationReference,
			ReflectionTestUtil.getFieldValue(
				intrabandBridgeMessageListener, "_registrationReference"));
	}

	@Test
	public void testReceive() throws ClassNotFoundException {
		PortalClassLoaderUtil.setClassLoader(
			IntrabandBridgeMessageListenerTest.class.getClassLoader());

		IntrabandBridgeMessageListener intrabandBridgeMessageListener =
			new IntrabandBridgeMessageListener(_mockRegistrationReference);

		Message message = new Message();

		message.setDestinationName(
			IntrabandBridgeMessageListenerTest.class.getName());

		String payload = "payload";

		message.setPayload(payload);

		intrabandBridgeMessageListener.receive(message);

		Datagram datagram = _mockIntraband.getDatagram();

		ByteBuffer byteBuffer = datagram.getDataByteBuffer();

		MessageRoutingBag receivedMessageRoutingBag =
			MessageRoutingBag.fromByteArray(byteBuffer.array());

		Assert.assertNotNull(receivedMessageRoutingBag);

		Message receivedMessage = receivedMessageRoutingBag.getMessage();

		Assert.assertEquals(payload, receivedMessage.getPayload());
	}

	private final MockIntraband _mockIntraband = new MockIntraband();
	private final MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraband);

}