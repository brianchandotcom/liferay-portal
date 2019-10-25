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

package com.liferay.flags.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.flags.service.FlagsEntryService;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HtmlEscapableObject;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class FlagsEntryServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(FlagsEntryServiceTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			Destination destination = _bundleContext.getService(
				_serviceRegistration.getReference());

			_serviceRegistration.unregister();

			destination.destroy();
		}
	}

	@Test
	public void testContentAttributesEscaped() throws Exception {
		MockSubscriptionSenderMessageListener
			mockSubscriptionSenderMessageListener =
				new MockSubscriptionSenderMessageListener();

		_registerDestination(mockSubscriptionSenderMessageListener);

		Map<String, String> contentAttributes = new HashMap<>();

		contentAttributes.put(
			"[$CONTENT_TITLE$]", "<img src=x onerror=alert(1)>");
		contentAttributes.put(
			"[$CONTENT_URL$]", "<img src=x onerror=alert(2)>");
		contentAttributes.put("[$REASON$]", "<img src=x onerror=alert(3)>");

		_flagsEntryService.addEntry(
			RandomTestUtil.randomString(), 0L,
			RandomTestUtil.randomString() + "@liferay.com",
			TestPropsValues.getUserId(),
			contentAttributes.get("[$CONTENT_TITLE$]"),
			contentAttributes.get("[$CONTENT_URL$]"),
			contentAttributes.get("[$REASON$]"),
			ServiceContextTestUtil.getServiceContext());

		mockSubscriptionSenderMessageListener.block();

		_assertContentAttributesEscaped(
			contentAttributes,
			mockSubscriptionSenderMessageListener.getSubscriptionSender());
	}

	private void _assertContentAttributesEscaped(
		Map<String, String> contentAttributes,
		SubscriptionSender subscriptionSender) {

		for (Map.Entry<String, String> entry : contentAttributes.entrySet()) {
			HtmlEscapableObject htmlEscapableObject =
				(HtmlEscapableObject)subscriptionSender.getContextAttribute(
					entry.getKey());

			Assert.assertTrue(
				"Attribute " + entry.getKey() + " should be escaped.",
				htmlEscapableObject.isEscape());
			Assert.assertEquals(
				HtmlUtil.escape(
					String.valueOf(htmlEscapableObject.getOriginalValue())),
				htmlEscapableObject.getEscapedValue());
		}
	}

	private void _registerDestination(MessageListener messageListener) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				DestinationNames.SUBSCRIPTION_SENDER);

		Destination destination = DestinationFactoryUtil.createDestination(
			destinationConfiguration);

		destination.register(messageListener);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", destination.getName());

		_serviceRegistration = _bundleContext.registerService(
			Destination.class, destination, properties);
	}

	private BundleContext _bundleContext;

	@Inject
	private FlagsEntryService _flagsEntryService;

	private ServiceRegistration<Destination> _serviceRegistration;

	private class MockSubscriptionSenderMessageListener
		extends BaseMessageListener {

		public void block() throws Exception {
			_countDownLatch.await();
		}

		public SubscriptionSender getSubscriptionSender() {
			return _subscriptionSender.get();
		}

		@Override
		protected void doReceive(Message message) {
			_subscriptionSender.set((SubscriptionSender)message.getPayload());

			_countDownLatch.countDown();
		}

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);
		private final AtomicReference<SubscriptionSender> _subscriptionSender =
			new AtomicReference<>();

	}

}