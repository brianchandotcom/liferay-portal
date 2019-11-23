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

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTMessageLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class MessageBusCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(MessageBusCTTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setUp() {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			_ctCollectionLocalService.createCTCollection(
				_counterLocalService.increment(CTCollection.class.getName())));

		_asyncDestinationServiceRegistration = _registerDestination(
			_TEST_ASYNC_DESTINATION_NAME,
			DestinationConfiguration.DESTINATION_TYPE_SERIAL);

		_syncDestinationServiceRegistration = _registerDestination(
			_TEST_SYNC_DESTINATION_NAME,
			DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS);
	}

	@After
	public void tearDown() {
		_asyncDestinationServiceRegistration.unregister();

		_syncDestinationServiceRegistration.unregister();
	}

	@Test
	public void testInterceptMessageToAsyncDestination() {
		Message message = new Message();

		message.setPayload(_TEST_MESSAGE_PAYLOAD);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			_messageBus.sendMessage(_TEST_ASYNC_DESTINATION_NAME, message);
		}

		List<Message> messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertSame(messages.toString(), 1, messages.size());

		Message deserializedMessage = messages.get(0);

		Assert.assertEquals(
			_TEST_ASYNC_DESTINATION_NAME,
			deserializedMessage.getDestinationName());
		Assert.assertEquals(
			message.getPayload(), deserializedMessage.getPayload());

		Assert.assertNull(_testMessageListener.getReceivedMessage());
	}

	@Test
	public void testInterceptMessageToNotExistedDestination() {
		Message message = new Message();

		message.setPayload(_TEST_MESSAGE_PAYLOAD);

		String nonExistDestinationName = "Non-existed Destination";

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			_messageBus.sendMessage(nonExistDestinationName, message);
		}

		List<Message> messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertSame(messages.toString(), 1, messages.size());

		Message deserializedMessage = messages.get(0);

		Assert.assertEquals(
			nonExistDestinationName, deserializedMessage.getDestinationName());
		Assert.assertEquals(
			message.getPayload(), deserializedMessage.getPayload());

		Assert.assertNull(_testMessageListener.getReceivedMessage());
	}

	@Test
	public void testInterceptMessageToSyncDestination() {
		Message message = new Message();

		message.setPayload(_TEST_MESSAGE_PAYLOAD);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			_messageBus.sendMessage(_TEST_SYNC_DESTINATION_NAME, message);
		}

		List<Message> messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertTrue(messages.toString(), messages.isEmpty());

		Assert.assertSame(message, _testMessageListener.getReceivedMessage());
	}

	@Test
	public void testPublishMessage() throws Exception {
		Message message = new Message();

		message.setPayload(_TEST_MESSAGE_PAYLOAD);
		message.setDestinationName(_TEST_SYNC_DESTINATION_NAME);

		_ctMessageLocalService.addCTMessage(
			_ctCollection.getCtCollectionId(), message);

		Assert.assertNull(_testMessageListener.getReceivedMessage());

		_backgroundTaskExecutor.execute(
			(BackgroundTask)ProxyUtil.newProxyInstance(
				BackgroundTask.class.getClassLoader(),
				new Class<?>[] {BackgroundTask.class},
				(proxy, method, args) -> {
					if (Objects.equals("getTaskContextMap", method.getName())) {
						return Collections.singletonMap(
							"ctCollectionId",
							_ctCollection.getCtCollectionId());
					}
					else if (Objects.equals("getUserId", method.getName())) {
						return _ctCollection.getUserId();
					}

					return null;
				}));

		Message receivedMessage = _testMessageListener.getReceivedMessage();

		Assert.assertEquals(message.getPayload(), receivedMessage.getPayload());

		List<Message> messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertSame(messages.toString(), 1, messages.size());

		Message deserializedMessage = messages.get(0);

		Assert.assertEquals(
			_TEST_SYNC_DESTINATION_NAME,
			deserializedMessage.getDestinationName());
		Assert.assertEquals(
			message.getPayload(), deserializedMessage.getPayload());

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);

		messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertTrue(messages.toString(), messages.isEmpty());

		_ctCollection = null;
	}

	private ServiceRegistration<Destination> _registerDestination(
		String destinationName, String destinationType) {

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(destinationType, destinationName);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_testMessageListener = new TestMessageListener();

		destination.register(_testMessageListener);

		return _bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary("destination.name", destinationName));
	}

	private static final String _TEST_ASYNC_DESTINATION_NAME =
		"_TEST_ASYNC_DESTINATION_NAME";

	private static final String _TEST_MESSAGE_PAYLOAD = "TEST_MESSAGE_PAYLOAD";

	private static final String _TEST_SYNC_DESTINATION_NAME =
		"_TEST_SYNC_DESTINATION_NAME";

	private static BundleContext _bundleContext;

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTMessageLocalService _ctMessageLocalService;

	@Inject
	private static DestinationFactory _destinationFactory;

	@Inject
	private static MessageBus _messageBus;

	private ServiceRegistration<Destination>
		_asyncDestinationServiceRegistration;

	@Inject(
		filter = "background.task.executor.class.name=com.liferay.change.tracking.internal.background.task.CTPublishBackgroundTaskExecutor",
		type = BackgroundTaskExecutor.class
	)
	private BackgroundTaskExecutor _backgroundTaskExecutor;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	private ServiceRegistration<Destination>
		_syncDestinationServiceRegistration;
	private TestMessageListener _testMessageListener;

	private class TestMessageListener implements MessageListener {

		public Message getReceivedMessage() {
			return _message;
		}

		@Override
		public void receive(Message message) {
			_message = message;
		}

		private Message _message;

	}

}