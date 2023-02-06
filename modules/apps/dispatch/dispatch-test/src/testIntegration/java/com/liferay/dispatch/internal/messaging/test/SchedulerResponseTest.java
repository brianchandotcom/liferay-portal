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

package com.liferay.dispatch.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dispatch.scheduler.SchedulerResponseManager;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class SchedulerResponseTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testScheduledJobManualExecutionCompanyId() throws Exception {
		_company = CompanyTestUtil.addCompany();

		TestMessageListener testMessageListener = new TestMessageListener();

		testMessageListener.init();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					_company.getCompanyId())) {

			_schedulerResponseManager.run(
				CompanyThreadLocal.getCompanyId(),
				TestMessageListener.class.getName(),
				TestMessageListener.class.getName(),
				StorageType.MEMORY_CLUSTERED);
		}
		finally {
			testMessageListener.destroy();
			CompanyLocalServiceUtil.deleteCompany(_company);
		}
	}

	private static Company _company;

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Inject
	private SchedulerResponseManager _schedulerResponseManager;

	@Inject
	private TriggerFactory _triggerFactory;

	private class TestMessageListener extends BaseMessageListener {

		public void destroy() {
			_schedulerEngineHelper.unregister(this);
		}

		public void init() {
			Class<?> clazz = getClass();

			String className = clazz.getName();

			Trigger trigger = _triggerFactory.createTrigger(
				className, className, null, null, 60, TimeUnit.MINUTE);

			SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
				className, trigger);

			_schedulerEngineHelper.register(
				this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
		}

		@Override
		protected void doReceive(Message message) throws Exception {
			long companyId = CompanyThreadLocal.getCompanyId();

			long messageCompanyId = message.getLong("companyId");

			Assert.assertEquals(_company.getCompanyId(), companyId);

			Assert.assertEquals(_company.getCompanyId(), messageCompanyId);
		}

	}

}