/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class QuartzUpgradeProcessDBPartitionTest
	extends BaseDBPartitionTestCase {

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

		_quartzUpgradeProcessTestHelper.assertHasAllQuartzIndexes();
	}

	private static QuartzUpgradeProcessTestHelper
		_quartzUpgradeProcessTestHelper;

	@Inject(
		filter = "(&(component.name=com.liferay.portal.scheduler.quartz.internal.upgrade.registry.QuartzServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}