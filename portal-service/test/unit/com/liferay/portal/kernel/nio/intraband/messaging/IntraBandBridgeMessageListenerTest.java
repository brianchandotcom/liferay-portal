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

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntraBandBridgeMessageListenerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() throws Exception {
		IntraBandBridgeMessageListener intraBandBridgeMessageListener =
			new IntraBandBridgeMessageListener(_mockRegistrationReference);

		Assert.assertSame(
			_mockIntraBand, getIntraBand(intraBandBridgeMessageListener));
		Assert.assertSame(
			_mockRegistrationReference,
			getRegistrationReference(intraBandBridgeMessageListener));
	}

	@Test
	public void testReceive() throws ClassNotFoundException {
		PortalClassLoaderUtil.setClassLoader(
			IntraBandBridgeMessageListenerTest.class.getClassLoader());

		IntraBandBridgeMessageListener intraBandBridgeMessageListener =
			new IntraBandBridgeMessageListener(_mockRegistrationReference);

		Message message = new Message();

		String testPayload = "testPayload";

		message.setPayload(testPayload);

		intraBandBridgeMessageListener.receive(message);

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		Message receivedMessage = deserializer.readObject();

		Assert.assertNotNull(receivedMessage);
		Assert.assertEquals(testPayload, receivedMessage.getPayload());
	}

	private static MockIntraBand getIntraBand(
			IntraBandBridgeMessageListener intraBandBridgeMessageListener)
		throws Exception {

		Field intraBandField = ReflectionUtil.getDeclaredField(
			IntraBandBridgeMessageListener.class, "_intraBand");

		return (MockIntraBand)intraBandField.get(
			intraBandBridgeMessageListener);
	}

	private static MockRegistrationReference getRegistrationReference(
			IntraBandBridgeMessageListener intraBandBridgeMessageListener)
		throws Exception {

		Field registrationReferenceField = ReflectionUtil.getDeclaredField(
			IntraBandBridgeMessageListener.class, "_registrationReference");

		return (MockRegistrationReference)registrationReferenceField.get(
			intraBandBridgeMessageListener);
	}

	private MockIntraBand _mockIntraBand = new MockIntraBand();
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraBand);

}