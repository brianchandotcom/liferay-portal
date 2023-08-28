/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.AfterClass;
import org.junit.Assert;
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
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class SchedulerResponseManagerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testRun() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			SchedulerResponseManagerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Destination destination = _destinationFactory.createDestination(
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				_TEST_DESTINATION_NAME));

		Dictionary<String, Object> dictionary = MapUtil.singletonDictionary(
			"destination.name", _TEST_DESTINATION_NAME);

		ServiceRegistration<?> serviceRegistration1 =
			bundleContext.registerService(
				Destination.class, destination, dictionary);

		TestMessageListener testMessageListener = new TestMessageListener();

		ServiceRegistration<?> serviceRegistration2 =
			bundleContext.registerService(
				MessageListener.class, testMessageListener, dictionary);

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
			serviceRegistration2.unregister();

			serviceRegistration1.unregister();

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
	private static String _originalName;

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