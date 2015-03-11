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

import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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
			new SyntheticBundleRule("bundle.schedulerentryregistry"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		_schedulerEntryRegistry = new SchedulerEntryRegistry();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_schedulerEntryRegistry.close();
	}

	@Test
	public void testSchedulerWasReplaced() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		SchedulerEntry schedulerEntry = registry.getService(
			SchedulerEntry.class);

		Assert.assertEquals(
			"TEST_SCHEDULER_ENTRY_EVENT_LISTENER_CLASS",
			schedulerEntry.getEventListenerClass());
	}

	private static SchedulerEntryRegistry _schedulerEntryRegistry;

}