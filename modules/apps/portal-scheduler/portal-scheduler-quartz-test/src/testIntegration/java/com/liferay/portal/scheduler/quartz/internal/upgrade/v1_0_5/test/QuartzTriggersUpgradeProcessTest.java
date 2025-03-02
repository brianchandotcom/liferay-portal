/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_5.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class QuartzTriggersUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_dbInspector = new DBInspector(DataAccess.getConnection());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_186442A4")) {
			_db.runSQL("drop index IX_186442A4 on table QUARTZ_TRIGGERS");
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		_db.runSQL(
			"create index IX_186442A4 on QUARTZ_TRIGGERS (SCHED_NAME, " +
				"TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE)");

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		Assert.assertFalse(
			_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_186442A4"));
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_5." +
			"QuartzTriggersUpgradeProcess";

	private static DB _db;
	private static DBInspector _dbInspector;

	@Inject(
		filter = "(&(component.name=com.liferay.portal.scheduler.quartz.internal.upgrade.registry.QuartzServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}