/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class QuartzUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(!DBPartition.isPartitionEnabled());
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_quartzUpgradeProcessTestHelper = new QuartzUpgradeProcessTestHelper(
			_upgradeStepRegistrator);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_quartzUpgradeProcessTestHelper.rebuildQuartzIndexes();
	}

	@Test
	public void testUpgrade() throws Exception {
		_quartzUpgradeProcessTestHelper.dropQuartzIndexes();

		_quartzUpgradeProcessTestHelper.runUpgrade();

		_quartzUpgradeProcessTestHelper.assertHasAnyQuartzIndex();
	}

	private static QuartzUpgradeProcessTestHelper
		_quartzUpgradeProcessTestHelper;

	@Inject(
		filter = "(&(component.name=com.liferay.portal.scheduler.quartz.internal.upgrade.registry.QuartzServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}