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

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;

import java.nio.ByteBuffer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public class IntrabandBridgeDestinationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@Before
	public void setUp() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_messageBus = Mockito.mock(MessageBus.class);

		_serviceRegistration = bundleContext.registerService(
			MessageBus.class, _messageBus, null);

		_baseDestination =
			new SynchronousDestinationTestRule.TestSynchronousDestination();

		_baseDestination.setName(
			IntrabandBridgeDestinationTest.class.getName());

		Mockito.when(
			_messageBus.getDestination(_baseDestination.getName())
		).thenReturn(
			_baseDestination
		);

		_intrabandBridgeDestination = new IntrabandBridgeDestination(
			_baseDestination);

		_mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				ByteBuffer byteBuffer = datagram.getDataByteBuffer();

				try {
					MessageRoutingBag receivedMessageRoutingBag =
						MessageRoutingBag.fromByteArray(byteBuffer.array());

					Message receivedMessage =
						receivedMessageRoutingBag.getMessage();

					receivedMessage.put(_RECEIVE_KEY, _RECEIVE_VALUE);

					return Datagram.createResponseDatagram(
						datagram, receivedMessageRoutingBag.toByteArray());
				}
				catch (ClassNotFoundException classNotFoundException) {
					throw new RuntimeException(classNotFoundException);
				}
			}

		};

		_mockRegistrationReference = new MockRegistrationReference(
			_mockIntraband);

		ClassLoaderPool.unregister(ClassLoaderPool.class.getClassLoader());
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testMisc() throws Exception {
		IntrabandBridgeDestination intrabandBridgeDestination =
			new IntrabandBridgeDestination(null);

		intrabandBridgeDestination.sendMessageRoutingBag(null, null);

		intrabandBridgeDestination.toRoutingId(null);
	}

	@Test
	public void testSendMessage() throws ClassNotFoundException {

		// Local message

		final AtomicBoolean throwRuntimeException = new AtomicBoolean();

		final AtomicReference<Message> messageReference =
			new AtomicReference<>();

		MessageListener messageListener = new MessageListener() {

			@Override
			public void receive(Message message) {
				if (throwRuntimeException.get()) {
					throw new RuntimeException();
				}

				messageReference.set(message);
			}

		};

		_baseDestination.register(messageListener);

		Message message = new Message();

		message.put(MessagingProxy.LOCAL_MESSAGE, Boolean.TRUE);

		_intrabandBridgeDestination.send(message);

		Assert.assertNull(message.get(MessageRoutingBag.MESSAGE_ROUTING_BAG));

		Assert.assertSame(message, messageReference.get());

		// Automatically create message routing bag

		message = new Message();

		_intrabandBridgeDestination.send(message);

		Assert.assertNotNull(
			message.get(MessageRoutingBag.MESSAGE_ROUTING_BAG));

		Assert.assertSame(message, messageReference.get());

		// Existing message routing bag

		MessageRoutingBag messageRoutingBag = _createMessageRoutingBag();

		message = messageRoutingBag.getMessage();

		message.put(MessageRoutingBag.MESSAGE_ROUTING_BAG, messageRoutingBag);

		_intrabandBridgeDestination.send(message);

		Assert.assertSame(
			messageRoutingBag,
			message.get(MessageRoutingBag.MESSAGE_ROUTING_BAG));

		// Unserializable message

		messageRoutingBag = _createMessageRoutingBag();

		message = messageRoutingBag.getMessage();

		message.put(MessageRoutingBag.MESSAGE_ROUTING_BAG, messageRoutingBag);

		messageRoutingBag.getMessageData();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			new ClassLoader() {

				@Override
				public Class<?> loadClass(String name)
					throws ClassNotFoundException {

					if (name.equals(Message.class.getName())) {
						throw new ClassNotFoundException();
					}

					return super.loadClass(name);
				}

			});

		try {
			_intrabandBridgeDestination.send(message);

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Throwable throwable = runtimeException.getCause();

			Assert.assertSame(
				ClassNotFoundException.class, throwable.getClass());
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		// Throw runtime exception

		throwRuntimeException.set(true);

		try {
			_intrabandBridgeDestination.send(new Message());

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Throwable throwable = runtimeException.getCause();

			Assert.assertSame(RuntimeException.class, throwable.getClass());
		}
	}

	private MessageRoutingBag _createMessageRoutingBag() {
		Message message = new Message();

		message.setDestinationName(
			IntrabandBridgeMessageListenerTest.class.getName());

		return new MessageRoutingBag(message, true);
	}

	private static final String _RECEIVE_KEY = "RECEIVE_KEY";

	private static final String _RECEIVE_VALUE = "RECEIVE_VALUE";

	private BaseDestination _baseDestination;
	private IntrabandBridgeDestination _intrabandBridgeDestination;
	private MessageBus _messageBus;
	private MockIntraband _mockIntraband;
	private MockRegistrationReference _mockRegistrationReference;
	private ServiceRegistration<?> _serviceRegistration;

}