/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRequestContext;
import com.liferay.portal.kernel.audit.AuditRequestContextThreadLocal;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Álvaro Saugar
 */
@RunWith(Arquillian.class)
public class AuditMessageTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@FeatureFlag("LPD-6417")
	@Test
	public void testConstructorOnMessageBusThread() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(AuditMessageTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String destinationName = RandomTestUtil.randomString();

		ServiceRegistration<Destination> serviceRegistration1 =
			bundleContext.registerService(
				Destination.class,
				_destinationFactory.createDestination(
					DestinationConfiguration.
						createSerialDestinationConfiguration(destinationName)),
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name", destinationName
				).build());

		List<AuditMessage> auditMessages = new ArrayList<>();
		long companyId = TestPropsValues.getCompanyId();
		CountDownLatch countDownLatch = new CountDownLatch(2);
		String sessionID = RandomTestUtil.randomString();
		long userId = TestPropsValues.getUserId();

		ServiceRegistration<MessageListener> serviceRegistration2 =
			bundleContext.registerService(
				MessageListener.class,
				message -> {
					if (auditMessages.isEmpty()) {
						AuditRequestContext auditRequestContext =
							AuditRequestContextThreadLocal.
								getAuditRequestContext();

						auditRequestContext.setSessionID(sessionID);
					}

					auditMessages.add(
						new AuditMessage(
							RandomTestUtil.randomLong(), companyId, userId,
							RandomTestUtil.randomString(),
							RandomTestUtil.nextDate(),
							JSONFactoryUtil.createJSONObject(),
							RandomTestUtil.randomString(),
							RandomTestUtil.randomString(),
							RandomTestUtil.randomString(),
							RandomTestUtil.randomString()));

					countDownLatch.countDown();
				},
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name", destinationName
				).build());

		try {
			_messageBus.sendMessage(destinationName, new Message());
			_messageBus.sendMessage(destinationName, new Message());

			Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));
		}
		finally {
			serviceRegistration2.unregister();
			serviceRegistration1.unregister();
		}

		AuditMessage auditMessage1 = auditMessages.get(0);

		Assert.assertNotNull(auditMessage1.getRequestId());
		Assert.assertTrue(auditMessage1.isRequestIdGenerated());
		Assert.assertEquals(sessionID, auditMessage1.getSessionID());

		AuditMessage auditMessage2 = auditMessages.get(1);

		Assert.assertNotNull(auditMessage2.getRequestId());
		Assert.assertTrue(auditMessage2.isRequestIdGenerated());

		Assert.assertNotEquals(
			auditMessage1.getRequestId(), auditMessage2.getRequestId());
		Assert.assertNull(auditMessage2.getSessionID());
	}

	@Inject
	private DestinationFactory _destinationFactory;

	@Inject
	private MessageBus _messageBus;

}