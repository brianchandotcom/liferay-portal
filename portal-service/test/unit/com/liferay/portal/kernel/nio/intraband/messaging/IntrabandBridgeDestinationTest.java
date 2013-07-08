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

import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.DefaultMessageBus;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.lang.reflect.Field;

import java.nio.ByteBuffer;

import java.rmi.RemoteException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class IntrabandBridgeDestinationTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		_messageBus = new DefaultMessageBus();

		MessageBusUtil.init(_messageBus, null, null);

		_baseDestination = new SynchronousDestination();

		_baseDestination.setName(
			IntrabandBridgeDestinationTest.class.getName());

		_intrabandBridgeDestination = new IntrabandBridgeDestination(
			_baseDestination);

		_mockIntraband = new MockIntraband() {

			@Override
			public Datagram sendSyncDatagram(
					RegistrationReference registrationReference,
					Datagram datagram)
				throws InterruptedException, IOException, TimeoutException {

				if (_failSend) {
					throw new IOException("Unable to send");
				}

				ByteBuffer byteBuffer = datagram.getDataByteBuffer();

				try {
					MessageRoutingBag receiveMessageRoutingBag =
						MessageRoutingBag.fromByteArray(byteBuffer.array());

					Message receivedMessage =
						receiveMessageRoutingBag.getMessage();

					receivedMessage.put(_RECEIVE_KEY, _RECEIVE_VALUE);

					return Datagram.createResponseDatagram(
						datagram, receiveMessageRoutingBag.toByteArray());
				}
				catch (ClassNotFoundException cnfe) {
					throw new IOException(cnfe);
				}
			}

		};

		_mockRegistrationReference = new MockRegistrationReference(
			_mockIntraband);
	}

	@Test
	public void testSendMessage() throws ClassNotFoundException {

		// Automatically create MessageRoutingBag

		final AtomicBoolean failToSend = new AtomicBoolean();

		final AtomicReference<Message> messageReference =
			new AtomicReference<Message>();

		MessageListener messageListener = new MessageListener() {

			@Override
			public void receive(Message message) {
				if (failToSend.get()) {
					throw new RuntimeException();
				}

				messageReference.set(message);
			}
		};

		_baseDestination.register(messageListener);

		Message message = new Message();

		_intrabandBridgeDestination.send(message);

		Assert.assertNotNull(
			message.get(MessageRoutingBag.MESSAGE_ROUTING_BAG));

		Assert.assertSame(message, messageReference.get());

		// Exist MessageRoutingBag

		MessageRoutingBag messageRoutingBag = _createMessageRoutingBag();

		message = messageRoutingBag.getMessage();

		message.put(MessageRoutingBag.MESSAGE_ROUTING_BAG, messageRoutingBag);

		_intrabandBridgeDestination.send(message);

		Assert.assertSame(
			messageRoutingBag,
			message.get(MessageRoutingBag.MESSAGE_ROUTING_BAG));

		// Unable to deserialize message

		messageRoutingBag = _createMessageRoutingBag();

		message = messageRoutingBag.getMessage();

		message.put(MessageRoutingBag.MESSAGE_ROUTING_BAG, messageRoutingBag);

		messageRoutingBag.getMessageData();

		ClassLoaderPool.register(StringPool.BLANK, new ClassLoader() {

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
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertSame(
				ClassNotFoundException.class, throwable.getClass());
		}
		finally {
			ClassLoaderPool.unregister(StringPool.BLANK);
		}

		// Fail to send

		failToSend.set(true);

		try {
			_intrabandBridgeDestination.send(new Message());

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertSame(RuntimeException.class, throwable.getClass());
		}
	}

	@Test
	public void testSendMessageBag1() {

		// Is not spi, without child spi

		_intrabandBridgeDestination.sendMessageBag(null);

		Assert.assertSame(
			_baseDestination,
			_messageBus.getDestination(_baseDestination.getName()));
	}

	@Test
	public void testSendMessageBag2() throws Exception {

		// Is not spi, with child spi, not visited, fail to send

		MockSPI mockSPI = _createMockSPI("SPIProvider", "SPI1");

		_installSPIs(mockSPI);

		_failSend = true;

		try {
			MessageRoutingBag messageRoutingBag = _createMessageRoutingBag();

			_intrabandBridgeDestination.sendMessageBag(messageRoutingBag);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertEquals(RuntimeException.class, throwable.getClass());

			throwable = throwable.getCause();

			Assert.assertEquals(IOException.class, throwable.getClass());
		}
		finally {
			_failSend = false;
		}

		// Is not spi, with child spi, not visited, success to send

		MessageRoutingBag messageRoutingBag = _createMessageRoutingBag();

		_intrabandBridgeDestination.sendMessageBag(messageRoutingBag);

		Message message = messageRoutingBag.getMessage();

		Assert.assertEquals(_RECEIVE_VALUE, message.get(_RECEIVE_KEY));

		// Is not spi, with child spi, visited

		messageRoutingBag = _createMessageRoutingBag();

		messageRoutingBag.appendRoutingId(_toRoutingId(mockSPI));

		_intrabandBridgeDestination.sendMessageBag(messageRoutingBag);

		message = messageRoutingBag.getMessage();

		Assert.assertNull(message.get(_RECEIVE_KEY));
	}

	@Test
	public void testSendMessageBag3() throws Exception {

		// Is spi, without child spi, downcast

		ConcurrentMap<String, Object> attributes =
			ProcessExecutor.ProcessContext.getAttributes();

		MockSPI mockSPI1 = _createMockSPI("SPIProvider", "SPI1");

		attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, mockSPI1);

		MessageRoutingBag messageRoutingBag = _createMessageRoutingBag();

		messageRoutingBag.setRoutingDowncast(true);

		_intrabandBridgeDestination.sendMessageBag(messageRoutingBag);

		Assert.assertTrue(messageRoutingBag.isVisited(_toRoutingId(mockSPI1)));

		Message message = messageRoutingBag.getMessage();

		Assert.assertNull(message.get(_RECEIVE_KEY));

		// Is spi. without child spi, upcast, fail to send

		_failSend = true;

		try {
			messageRoutingBag = _createMessageRoutingBag();

			_intrabandBridgeDestination.sendMessageBag(messageRoutingBag);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertEquals(RuntimeException.class, throwable.getClass());

			throwable = throwable.getCause();

			Assert.assertEquals(IOException.class, throwable.getClass());
		}
		finally {
			_failSend = false;
		}

		Assert.assertTrue(messageRoutingBag.isVisited(_toRoutingId(mockSPI1)));

		// Is spi. without child spi, upcast, success to send

		messageRoutingBag = _createMessageRoutingBag();

		_intrabandBridgeDestination.sendMessageBag(messageRoutingBag);

		Assert.assertTrue(messageRoutingBag.isVisited(_toRoutingId(mockSPI1)));

		message = messageRoutingBag.getMessage();

		Assert.assertEquals(_RECEIVE_VALUE, message.get(_RECEIVE_KEY));

		// Is spi, with child spi, not visited, downcast

		MockSPI mockSPI2 = _createMockSPI("SPIProvider", "SPI2");

		_installSPIs(mockSPI2);

		messageRoutingBag = _createMessageRoutingBag();

		messageRoutingBag.setRoutingDowncast(true);

		_intrabandBridgeDestination.sendMessageBag(messageRoutingBag);

		Assert.assertTrue(messageRoutingBag.isVisited(_toRoutingId(mockSPI1)));

		message = messageRoutingBag.getMessage();

		Assert.assertEquals(_RECEIVE_VALUE, message.get(_RECEIVE_KEY));

		// Is spi, with child spi, visited, downcast

		messageRoutingBag = _createMessageRoutingBag();

		messageRoutingBag.setRoutingDowncast(true);
		messageRoutingBag.appendRoutingId(_toRoutingId(mockSPI2));

		_intrabandBridgeDestination.sendMessageBag(messageRoutingBag);

		Assert.assertTrue(messageRoutingBag.isVisited(_toRoutingId(mockSPI1)));

		message = messageRoutingBag.getMessage();

		Assert.assertNull(message.get(_RECEIVE_KEY));
	}

	private MessageRoutingBag _createMessageRoutingBag() {
		Message message = new Message();

		message.setDestinationName(
			IntrabandBridgeMessageListenerTest.class.getName());

		return new MessageRoutingBag(message, true);
	}

	private MockSPI _createMockSPI(String spiProviderName, String spiId) {
		MockSPI mockSPI = new MockSPI() {

			@Override
			public RegistrationReference getRegistrationReference() {
				return _mockRegistrationReference;
			}

		};

		mockSPI.spiProviderName = spiProviderName;
		mockSPI.spiConfiguration = new SPIConfiguration(
			spiId, null, -1, null, null, null);

		return mockSPI;
	}

	private void _installSPIs(SPI... spis) throws Exception {
		Map<String, SPI> spiMap = new ConcurrentHashMap<String, SPI>();

		for (SPI spi : spis) {
			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();
			spiMap.put(spiConfiguration.getSPIId(), spi);
		}

		Field spisField = ReflectionUtil.getDeclaredField(
			MPIHelperUtil.class, "_spis");

		spisField.set(null, spiMap);
	}

	private String _toRoutingId(SPI spi) throws RemoteException {
		String spiProviderName = spi.getSPIProviderName();

		SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

		String spiId = spiConfiguration.getSPIId();

		return spiProviderName.concat(StringPool.POUND).concat(spiId);
	}

	private static final String _RECEIVE_KEY = "RECEIVE_KEY";

	private static final String _RECEIVE_VALUE = "RECEIVE_VALUE";

	private BaseDestination _baseDestination;
	private boolean _failSend;
	private IntrabandBridgeDestination _intrabandBridgeDestination;
	private MessageBus _messageBus;
	private MockIntraband _mockIntraband;
	private MockRegistrationReference _mockRegistrationReference;

}