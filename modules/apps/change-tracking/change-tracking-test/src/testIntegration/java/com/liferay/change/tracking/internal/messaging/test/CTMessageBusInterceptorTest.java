/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTMessageLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.DestinationWrapper;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class CTMessageBusInterceptorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, CTMessageBusInterceptorTest.class.getSimpleName(), null);

		_testDestination = new TestDestination(
			_destinationFactory.createDestination(
				new DestinationConfiguration(
					DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
					DestinationNames.SUBSCRIPTION_SENDER)));

		_serviceRegistration = _bundleContext.registerService(
			Destination.class, _testDestination,
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", DestinationNames.SUBSCRIPTION_SENDER
			).put(
				"service.ranking", Integer.MAX_VALUE
			).build());
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	@Test
	public void testInterceptSubscriptionSenderMessage() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(companyId);
		subscriptionSender.setMailId(
			CTMessageBusInterceptorTest.class.getName(), "test");

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			subscriptionSender.flushNotificationsAsync();
		}

		Assert.assertNull(_testDestination.getReceivedMessage());

		List<Message> messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertSame(messages.toString(), 1, messages.size());

		Message deserializedMessage = messages.get(0);

		Assert.assertEquals(
			DestinationNames.SUBSCRIPTION_SENDER,
			deserializedMessage.getDestinationName());

		SubscriptionSender deserializedSubscriptionSender =
			(SubscriptionSender)deserializedMessage.getPayload();

		Assert.assertEquals(
			companyId, deserializedSubscriptionSender.getCompanyId());
	}

	@Test
	public void testPublishSubscriptionSenderMessage() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Message message = new Message();

		message.setDestinationName(DestinationNames.SUBSCRIPTION_SENDER);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(companyId);
		subscriptionSender.setMailId(
			CTMessageBusInterceptorTest.class.getName(), "test");

		message.setPayload(subscriptionSender);

		_ctMessageLocalService.addCTMessage(
			_ctCollection.getCtCollectionId(), message);

		Assert.assertNull(_testDestination.getReceivedMessage());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());
		}

		Message receivedMessage = _testDestination.getReceivedMessage();

		SubscriptionSender deserializedSubscriptionSender =
			(SubscriptionSender)receivedMessage.getPayload();

		Assert.assertEquals(
			companyId, deserializedSubscriptionSender.getCompanyId());

		List<Message> messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertSame(messages.toString(), 1, messages.size());

		Message deserializedMessage = messages.get(0);

		Assert.assertEquals(
			DestinationNames.SUBSCRIPTION_SENDER,
			deserializedMessage.getDestinationName());

		deserializedSubscriptionSender =
			(SubscriptionSender)receivedMessage.getPayload();

		Assert.assertEquals(
			companyId, deserializedSubscriptionSender.getCompanyId());

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);

		messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertTrue(messages.toString(), messages.isEmpty());

		_ctCollection = null;
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTMessageLocalService _ctMessageLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	@Inject
	private static DestinationFactory _destinationFactory;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	private ServiceRegistration<Destination> _serviceRegistration;
	private TestDestination _testDestination;

	private static class TestDestination extends DestinationWrapper {

		public TestDestination(Destination destination) {
			super(destination);
		}

		public Message getReceivedMessage() {
			return _message;
		}

		@Override
		public void send(Message message) {
			_message = message;
		}

		private Message _message;

	}

}