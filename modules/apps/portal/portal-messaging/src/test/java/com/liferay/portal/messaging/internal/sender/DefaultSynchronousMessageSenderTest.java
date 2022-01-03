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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.messaging.internal.DefaultMessageBus;
import com.liferay.portal.messaging.internal.SerialDestination;
import com.liferay.portal.messaging.internal.SynchronousDestination;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

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
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_messageBus = new DefaultMessageBus();

		ReflectionTestUtil.setFieldValue(
			_messageBus, "_serviceTrackerList",
			new ServiceTrackerList() {

				@Override
				public void close() {
				}

				@Override
				public Iterator iterator() {
					return Collections.emptyIterator();
				}

				@Override
				public int size() {
					return 0;
				}

			});

		_destinations = ReflectionTestUtil.getFieldValue(
			_messageBus, "_destinations");

		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName(
			DestinationNames.MESSAGE_BUS_DEFAULT_RESPONSE);

		_destinations.put(
			synchronousDestination.getName(), synchronousDestination);

		_defaultSynchronousMessageSender =
			new DefaultSynchronousMessageSender();

		ReflectionTestUtil.setFieldValue(
			_defaultSynchronousMessageSender, "_entityCache",
			Mockito.mock(EntityCache.class));
		ReflectionTestUtil.setFieldValue(
			_defaultSynchronousMessageSender, "_finderCache",
			Mockito.mock(FinderCache.class));
		ReflectionTestUtil.setFieldValue(
			_defaultSynchronousMessageSender, "_messageBus", _messageBus);
		ReflectionTestUtil.setFieldValue(
			_defaultSynchronousMessageSender, "_timeout", 10000);

		_portalExecutorManager = Mockito.mock(PortalExecutorManager.class);

		synchronousDestination.open();
	}

	@After
	public void tearDown() {
		_messageBus.shutdown(true);
	}

	@Test
	public void testSendToAsyncDestination() throws MessageBusException {
		SerialDestination serialDestination = new SerialDestination();

		serialDestination.setName("testSerialDestination");
		serialDestination.setPortalExecutorManager(_portalExecutorManager);

		serialDestination.afterPropertiesSet();

		serialDestination.open();

		_testSend(serialDestination);
	}

	@Test
	public void testSendToSynchronousDestination() throws MessageBusException {
		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName("testSynchronousDestination");

		synchronousDestination.afterPropertiesSet();

		synchronousDestination.open();

		_testSend(synchronousDestination);
	}

	private void _testSend(Destination destination) throws MessageBusException {
		Object response = new Object();

		destination.register(new ReplayMessageListener(response));

		_destinations.put(destination.getName(), destination);

		try {
			Assert.assertSame(
				response,
				_defaultSynchronousMessageSender.send(
					destination.getName(), new Message()));
		}
		finally {
			_destinations.remove(destination.getName());

			destination.destroy();
		}
	}

	private DefaultSynchronousMessageSender _defaultSynchronousMessageSender;
	private Map<String, Destination> _destinations;
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