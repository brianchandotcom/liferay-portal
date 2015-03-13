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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.deploy.hot.bundle.schedulerentryregistry.TestSchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class SchedulerEntryRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.schedulerentryregistry"));

	@AfterClass
	public static void tearDownClass() throws Exception {
		_schedulerEntryRegistry.close();
	}

	@Sync
	@Test
	public void testJobIsScheduled() throws Exception {
		_schedulerEntryRegistry = new SchedulerEntryRegistry();

		List<SchedulerResponse> scheduledJobs =
			SchedulerEngineHelperUtil.getScheduledJobs();

		String name = TestSchedulerEntry.class.getName();
		boolean registered = false;

		for (SchedulerResponse scheduledJob : scheduledJobs) {
			String description = scheduledJob.getDescription();

			if (description.equals(name)) {
				registered = true;
			}
		}

		if (!registered) {
			Assert.fail("Job has not been properly scheduled");
		}
	}

	private static SchedulerEntryRegistry _schedulerEntryRegistry;

}