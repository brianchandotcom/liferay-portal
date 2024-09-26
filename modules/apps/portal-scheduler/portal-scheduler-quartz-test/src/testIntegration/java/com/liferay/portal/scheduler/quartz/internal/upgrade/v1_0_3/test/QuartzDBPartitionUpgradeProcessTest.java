/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class QuartzDBPartitionUpgradeProcessTest
	extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_dbInspector = new DBInspector(DataAccess.getConnection());
	}

	@Test
	public void testUpgrade() throws Exception {
		_dropQuartzIndexes();

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		_assertHasAllQuartzIndexes();
	}

	private void _assertHasAllQuartzIndexes() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					PortalUtil.getDefaultCompanyId())) {

			for (Index index : _QUARTZ_INDEXES) {
				Assert.assertTrue(
					_dbInspector.hasIndex(
						index.getTableName(), index.getIndexName()));
			}
		}
	}

	private void _dropQuartzIndexes() throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"drop index ", _QUARTZ_INDEXES[0].getIndexName(), " on ",
				_QUARTZ_INDEXES[0].getTableName()));
		_db.runSQL(
			StringBundler.concat(
				"drop index ", _QUARTZ_INDEXES[5].getIndexName(), " on ",
				_QUARTZ_INDEXES[5].getTableName()));
		_db.runSQL(
			StringBundler.concat(
				"drop index ", _QUARTZ_INDEXES[7].getIndexName(), " on ",
				_QUARTZ_INDEXES[7].getTableName()));
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_3." +
			"QuartzDBPartitionUpgradeProcess";

	private static final Index[] _QUARTZ_INDEXES = {
		new Index("IX_339E078M", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_BC2F03B0", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_5005E3AF", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_4BD722BM", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_BE3835E5", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_88328984", "QUARTZ_JOB_DETAILS", false),
		new Index("IX_779BCA37", "QUARTZ_JOB_DETAILS", false),
		new Index("IX_CD7132D0", "QUARTZ_TRIGGERS", false),
		new Index("IX_8AA50BE1", "QUARTZ_TRIGGERS", false),
		new Index("IX_A85822A0", "QUARTZ_TRIGGERS", false),
		new Index("IX_1F92813C", "QUARTZ_TRIGGERS", false),
		new Index("IX_F2DD7C7E", "QUARTZ_TRIGGERS", false),
		new Index("IX_91CA7CCE", "QUARTZ_TRIGGERS", false),
		new Index("IX_D219AFDE", "QUARTZ_TRIGGERS", false),
		new Index("IX_99108B6E", "QUARTZ_TRIGGERS", false)
	};

	private static DB _db;
	private static DBInspector _dbInspector;

	@Inject(
		filter = "(&(component.name=com.liferay.portal.scheduler.quartz.internal.upgrade.registry.QuartzServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}