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

package com.liferay.portal.messaging.internal.sender;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.messaging.internal.DefaultMessageBus;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Shuyang Zhou
 */
public class DefaultSynchronousMessageSenderTest {

	@Before
	public void setUp() {
		Registry registry = Mockito.mock(Registry.class);

		Mockito.when(
			registry.getRegistry()
		).thenReturn(
			registry
		);

		Mockito.when(
			registry.setRegistry(registry)
		).thenReturn(
			registry
		);

		ServiceTracker<Object, Object> serviceTracker = Mockito.mock(
			ServiceTracker.class);

		Mockito.when(
			registry.trackServices(
				(Class<Object>)Matchers.any(),
				(ServiceTrackerCustomizer<Object, Object>)Matchers.any())
		).thenReturn(
			serviceTracker
		);

		RegistryUtil.setRegistry(null);
		RegistryUtil.setRegistry(registry);

		_messageBus = new DefaultMessageBus();

		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName(
			DestinationNames.MESSAGE_BUS_DEFAULT_RESPONSE);

		_messageBus.addDestination(synchronousDestination);

		_defaultSynchronousMessageSender =
			new DefaultSynchronousMessageSender();

		_defaultSynchronousMessageSender.setEntityCache(
			Mockito.mock(EntityCache.class));
		_defaultSynchronousMessageSender.setFinderCache(
			Mockito.mock(FinderCache.class));
		_defaultSynchronousMessageSender.setMessageBus(_messageBus);
		_defaultSynchronousMessageSender.setTimeout(10000);

		_portalExecutorManager = Mockito.mock(PortalExecutorManager.class);

		Mockito.when(
			_portalExecutorManager.getPortalExecutor(Mockito.anyString())
		).thenReturn(
			new ThreadPoolExecutor(1, 1)
		);

		Mockito.when(
			serviceTracker.getService()
		).thenReturn(
			_portalExecutorManager
		);

		synchronousDestination.open();
	}

	@After
	public void tearDown() {
		_messageBus.shutdown(true);
	}

	@Test
	public void testSendToAsyncDestination() throws MessageBusException {
		SerialDestination serialDestination = new SerialDestination() {

			@Override
			public void open() {
				portalExecutorManager = _portalExecutorManager;

				super.open();
			}

		};

		serialDestination.setName("testSerialDestination");

		serialDestination.afterPropertiesSet();

		serialDestination.open();

		doTestSend(serialDestination);
	}

	@Test
	public void testSendToSynchronousDestination() throws MessageBusException {
		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName("testSynchronousDestination");

		synchronousDestination.afterPropertiesSet();

		synchronousDestination.open();

		doTestSend(synchronousDestination);
	}

	protected void doTestSend(Destination destination)
		throws MessageBusException {

		Object response = new Object();

		destination.register(new ReplayMessageListener(response));

		_messageBus.addDestination(destination);

		try {
			Assert.assertSame(
				response,
				_defaultSynchronousMessageSender.send(
					destination.getName(), new Message()));
		}
		finally {
			_messageBus.removeDestination(destination.getName());

			destination.close(true);
		}
	}

	private DefaultSynchronousMessageSender _defaultSynchronousMessageSender;
	private MessageBus _messageBus;
	private PortalExecutorManager _portalExecutorManager;

	private class ReplayMessageListener implements MessageListener {

		public ReplayMessageListener(Object response) {
			_response = response;
		}

		@Override
		public void receive(Message message) {
			Message responseMessage = new Message();

			responseMessage.setDestinationName(
				message.getResponseDestinationName());
			responseMessage.setResponseId(message.getResponseId());

			responseMessage.setPayload(_response);

			_messageBus.sendMessage(
				message.getResponseDestinationName(), responseMessage);
		}

		private final Object _response;

	}

}