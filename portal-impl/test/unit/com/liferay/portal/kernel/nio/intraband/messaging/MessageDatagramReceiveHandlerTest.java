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

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerInvocationHandler;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.nio.ByteBuffer;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Shuyang Zhou
 */
public class MessageDatagramReceiveHandlerTest {

	@ClassRule
	@Rule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			PortalExecutorManager.class,
			(PortalExecutorManager)ProxyUtil.newProxyInstance(
				MessageDatagramReceiveHandlerTest.class.getClassLoader(),
				new Class<?>[] {PortalExecutorManager.class},
				new PortalExecutorManagerInvocationHandler()));
	}

	@Test
	public void testDeprecatedConstructor() {
		new MessageDatagramReceiveHandler(null);
	}

	@Test
	public void testDoReceive1() throws Exception {

		// No such destination, not synchronized

		PortalClassLoaderUtil.setClassLoader(
			MessageDatagramReceiveHandlerTest.class.getClassLoader());

		Registry registry = RegistryUtil.getRegistry();

		MessageBus messageBus = Mockito.mock(MessageBus.class);

		registry.registerService(MessageBus.class, messageBus);

		MessageDatagramReceiveHandler messageDatagramReceiveHandler =
			new MessageDatagramReceiveHandler();

		SystemDataType systemDataType = SystemDataType.MESSAGE;

		Message message = new Message();

		message.setDestinationName(
			MessageDatagramReceiveHandlerTest.class.getName());

		MessageRoutingBag messageRoutingBag = new MessageRoutingBag(
			message, false);

		Datagram datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), messageRoutingBag.toByteArray());

		messageDatagramReceiveHandler.doReceive(
			_mockRegistrationReference, datagram);

		// No such destination, synchronized

		messageRoutingBag = new MessageRoutingBag(message, true);

		datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), messageRoutingBag.toByteArray());

		messageDatagramReceiveHandler.doReceive(
			_mockRegistrationReference, datagram);

		Datagram responseDatagram = _mockIntraband.getDatagram();

		ByteBuffer byteBuffer = responseDatagram.getDataByteBuffer();

		MessageRoutingBag responseMessageRoutingBag =
			MessageRoutingBag.fromByteArray(byteBuffer.array());

		assertMessageRoutingBagEquals(
			messageRoutingBag, responseMessageRoutingBag);

		// Normal destination, synchronized, no listener

		BaseDestination baseDestination =
			new SynchronousDestinationTestRule.TestSynchronousDestination();

		baseDestination.setName(
			MessageDatagramReceiveHandlerTest.class.getName());

		Mockito.when(
			messageBus.getDestination(Matchers.anyString())
		).thenReturn(
			baseDestination
		);

		datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), messageRoutingBag.toByteArray());

		messageDatagramReceiveHandler.doReceive(
			_mockRegistrationReference, datagram);

		responseDatagram = _mockIntraband.getDatagram();

		byteBuffer = responseDatagram.getDataByteBuffer();

		responseMessageRoutingBag = MessageRoutingBag.fromByteArray(
			byteBuffer.array());

		assertMessageRoutingBagEquals(
			messageRoutingBag, responseMessageRoutingBag);

		// Normal destination, synchronized, with listener

		final AtomicReference<Message> messageReference =
			new AtomicReference<>();

		baseDestination.register(
			new MessageListener() {

				@Override
				public void receive(Message message) {
					messageReference.set(message);
				}

			});

		datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), messageRoutingBag.toByteArray());

		messageDatagramReceiveHandler.doReceive(
			_mockRegistrationReference, datagram);

		responseDatagram = _mockIntraband.getDatagram();

		byteBuffer = responseDatagram.getDataByteBuffer();

		responseMessageRoutingBag = MessageRoutingBag.fromByteArray(
			byteBuffer.array());

		assertMessageRoutingBagEquals(
			messageRoutingBag, responseMessageRoutingBag);

		Message receivedMessage = messageReference.get();

		Assert.assertNotNull(receivedMessage);

		// Normal destination, synchronized, with listener that throws an
		// exception

		final MessageListenerException messageListenerException =
			new MessageListenerException();

		baseDestination.register(
			new MessageListener() {

				@Override
				public void receive(Message message)
					throws MessageListenerException {

					throw messageListenerException;
				}

			});

		datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), messageRoutingBag.toByteArray());

		try {
			messageDatagramReceiveHandler.doReceive(
				_mockRegistrationReference, datagram);

			Assert.fail();
		}
		catch (MessageBusException mbe) {
			Assert.assertSame(messageListenerException, mbe.getCause());
		}

		// Intraband bridge destination, not synchronized, no listener

		baseDestination =
			new SynchronousDestinationTestRule.TestSynchronousDestination();

		baseDestination.setName(
			MessageDatagramReceiveHandlerTest.class.getName());

		final AtomicReference<MessageRoutingBag> messageRoutingBagReference =
			new AtomicReference<>();

		IntrabandBridgeDestination intrabandBridgeDestination =
			new IntrabandBridgeDestination(baseDestination) {

				@Override
				public void sendMessageRoutingBag(
					MessageRoutingBag messageRoutingBag) {

					messageRoutingBagReference.set(messageRoutingBag);
				}

			};

		Mockito.when(
			messageBus.getDestination(Matchers.anyString())
		).thenReturn(
			intrabandBridgeDestination
		);

		datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), messageRoutingBag.toByteArray());

		messageDatagramReceiveHandler.doReceive(
			_mockRegistrationReference, datagram);

		assertMessageRoutingBagEquals(
			messageRoutingBag, messageRoutingBagReference.get());

		// Intraband bridge destination, not synchronized, with listener

		messageReference.set(null);

		baseDestination.register(
			new MessageListener() {

				@Override
				public void receive(Message message) {
					messageReference.set(message);
				}

			});

		datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), messageRoutingBag.toByteArray());

		messageDatagramReceiveHandler.doReceive(
			_mockRegistrationReference, datagram);

		Assert.assertNotNull(messageReference.get());
	}

	protected void assertMessageRoutingBagEquals(
		MessageRoutingBag expectedMessageRoutingBag,
		MessageRoutingBag actualMessageRoutingBag) {

		Assert.assertEquals(
			expectedMessageRoutingBag.getDestinationName(),
			actualMessageRoutingBag.getDestinationName());
		Assert.assertEquals(
			expectedMessageRoutingBag.isRoutingDowncast(),
			actualMessageRoutingBag.isRoutingDowncast());
		Assert.assertEquals(
			ReflectionTestUtil.<ArrayList<String>>getFieldValue(
				expectedMessageRoutingBag, "_routingTrace"),
			ReflectionTestUtil.<ArrayList<String>>getFieldValue(
				actualMessageRoutingBag, "_routingTrace"));
	}

	private final MockIntraband _mockIntraband = new MockIntraband();
	private final MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraband);

}