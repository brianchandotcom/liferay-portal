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

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntrabandBridgeMessageListenerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() throws Exception {
		IntrabandBridgeMessageListener intrabandBridgeMessageListener =
			new IntrabandBridgeMessageListener(_mockRegistrationReference);

		Assert.assertSame(
			_mockIntraband, getIntraband(intrabandBridgeMessageListener));
		Assert.assertSame(
			_mockRegistrationReference,
			getRegistrationReference(intrabandBridgeMessageListener));
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

	private static MockIntraband getIntraband(
			IntrabandBridgeMessageListener intrabandBridgeMessageListener)
		throws Exception {

		Field intrabandField = ReflectionUtil.getDeclaredField(
			IntrabandBridgeMessageListener.class, "_intraband");

		return (MockIntraband)intrabandField.get(
			intrabandBridgeMessageListener);
	}

	private static MockRegistrationReference getRegistrationReference(
			IntrabandBridgeMessageListener intrabandBridgeMessageListener)
		throws Exception {

		Field registrationReferenceField = ReflectionUtil.getDeclaredField(
			IntrabandBridgeMessageListener.class, "_registrationReference");

		return (MockRegistrationReference)registrationReferenceField.get(
			intrabandBridgeMessageListener);
	}

	private MockIntraband _mockIntraband = new MockIntraband();
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraband);

}