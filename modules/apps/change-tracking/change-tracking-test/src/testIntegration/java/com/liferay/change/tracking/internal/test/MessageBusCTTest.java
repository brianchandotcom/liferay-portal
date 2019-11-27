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
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
	public void setUp() throws Exception {
		long ctCollectionId = _counterLocalService.increment(
			CTCollection.class.getName());

		_ctCollection = _ctCollectionLocalService.createCTCollection(
			ctCollectionId);

		_ctCollection.setUserId(TestPropsValues.getUserId());
		_ctCollection.setName(String.valueOf(ctCollectionId));
		_ctCollection.setStatus(WorkflowConstants.STATUS_DRAFT);

		_ctCollection = _ctCollectionLocalService.updateCTCollection(
			_ctCollection);

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

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());
		}

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
	private static CTProcessLocalService _ctProcessLocalService;

	@Inject
	private static DestinationFactory _destinationFactory;

	@Inject
	private static MessageBus _messageBus;

	private ServiceRegistration<Destination>
		_asyncDestinationServiceRegistration;

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