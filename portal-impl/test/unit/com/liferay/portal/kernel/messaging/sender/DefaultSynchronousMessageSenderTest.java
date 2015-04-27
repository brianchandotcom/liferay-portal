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

package com.liferay.portal.kernel.messaging.sender;

import com.liferay.portal.dao.orm.common.EntityCacheImpl;
import com.liferay.portal.dao.orm.common.FinderCacheImpl;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.messaging.DefaultMessageBus;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.test.rule.PortalExecutorManagerTestRule;
import com.liferay.portal.uuid.PortalUUIDImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Shuyang Zhou
 */
public class DefaultSynchronousMessageSenderTest {

	@ClassRule
	@Rule
	public static final PortalExecutorManagerTestRule aggregateTestRule =
		PortalExecutorManagerTestRule.INSTANCE;

	@Before
	public void setUp() {
		_messageBus = new DefaultMessageBus();

		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName(
			DestinationNames.MESSAGE_BUS_DEFAULT_RESPONSE);

		_messageBus.addDestination(synchronousDestination);

		_defaultSynchronousMessageSender =
			new DefaultSynchronousMessageSender();

		_defaultSynchronousMessageSender.setMessageBus(_messageBus);
		_defaultSynchronousMessageSender.setPortalUUID(new PortalUUIDImpl());
		_defaultSynchronousMessageSender.setTimeout(10000);

		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(MessageBus.class, _messageBus);

		PortalExecutorManager portalExecutorManager = Mockito.mock(
			PortalExecutorManager.class);

		Mockito.when(
			portalExecutorManager.getPortalExecutor(Mockito.anyString())).
			thenReturn(new ThreadPoolExecutor(1, 1));

		registry.registerService(
			PortalExecutorManager.class, portalExecutorManager);

		EntityCacheUtil entityCacheUtil = new EntityCacheUtil();

		entityCacheUtil.setEntityCache(new EntityCacheImpl());

		FinderCacheUtil finderCacheUtil = new FinderCacheUtil();

		finderCacheUtil.setFinderCache(new FinderCacheImpl());
	}

	@After
	public void tearDown() {
		_messageBus.shutdown(true);
	}

	@Test
	public void testSendToAsyncDestination() throws MessageBusException {
		SerialDestination serialDestination = new SerialDestination();

		serialDestination.setName("testSerialDestination");

		serialDestination.afterPropertiesSet();

		doTestSend(serialDestination);
	}

	@Test
	public void testSendToSynchronousDestination() throws MessageBusException {
		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName("testSynchronousDestination");

		synchronousDestination.afterPropertiesSet();

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

	private class ReplayMessageListener implements MessageListener {

		public ReplayMessageListener(Object response) {
			_response = response;
		}

		@Override
		public void receive(Message message) {
			Message responseMessage = MessageBusUtil.createResponseMessage(
				message);

			responseMessage.setPayload(_response);

			_messageBus.sendMessage(
				message.getResponseDestinationName(), responseMessage);
		}

		private final Object _response;

	}

}