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

package com.liferay.dispatch.scheduler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dispatch.scheduler.SchedulerResponseManager;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class SchedulerResponseManagerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testRun() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			SchedulerResponseManagerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Destination destination = _destinationFactory.createDestination(
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				_TEST_DESTINATION_NAME));

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				Destination.class, destination,
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name", _TEST_DESTINATION_NAME
				).build());

		TestMessageListener testMessageListener = new TestMessageListener();

		destination.register(testMessageListener);

		_schedulerEngineHelper.schedule(
			_triggerFactory.createTrigger(
				_TEST_NAME, _TEST_NAME, null, null, 60, TimeUnit.MINUTE),
			StorageType.MEMORY_CLUSTERED, null, _TEST_DESTINATION_NAME, null);

		_company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					_company.getCompanyId())) {

			_schedulerResponseManager.run(
				CompanyThreadLocal.getCompanyId(), _TEST_NAME, _TEST_NAME,
				StorageType.MEMORY_CLUSTERED);

			Assert.assertEquals(
				_company.getCompanyId(),
				testMessageListener.getCompanyIdFromCompanyThreadLocal());
			Assert.assertEquals(
				_company.getCompanyId(),
				testMessageListener.getCompanyIdFromMessage());
		}
		finally {
			serviceRegistration.unregister();

			_schedulerEngineHelper.delete(
				_TEST_NAME, _TEST_NAME, StorageType.MEMORY_CLUSTERED);

			CompanyLocalServiceUtil.deleteCompany(_company);
		}
	}

	private static final String _TEST_DESTINATION_NAME =
		RandomTestUtil.randomString();

	private static final String _TEST_NAME =
		SchedulerResponseManagerTest.class.getName();

	private static Company _company;

	@Inject
	private DestinationFactory _destinationFactory;

	@Inject
	private MessageBus _messageBus;

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Inject
	private SchedulerResponseManager _schedulerResponseManager;

	@Inject
	private TriggerFactory _triggerFactory;

	private class TestMessageListener extends BaseMessageListener {

		public long getCompanyIdFromCompanyThreadLocal() {
			return _companyIdFromCompanyThreadLocal;
		}

		public long getCompanyIdFromMessage() {
			return _companyIdFromMessage;
		}

		@Override
		protected void doReceive(Message message) {
			_companyIdFromCompanyThreadLocal =
				CompanyThreadLocal.getCompanyId();

			_companyIdFromMessage = message.getLong("companyId");
		}

		private long _companyIdFromCompanyThreadLocal;
		private long _companyIdFromMessage;

	}

}