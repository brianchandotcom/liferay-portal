/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_3.test;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;

/**
 * @author Mariano Álvaro Sáiz
 */
public class QuartzUpgradeProcessTestHelper {

	public QuartzUpgradeProcessTestHelper(
			UpgradeStepRegistrator upgradeStepRegistrator)
		throws Exception {

		_upgradeStepRegistrator = upgradeStepRegistrator;

		_db = DBManagerUtil.getDB();

		_dbInspector = new DBInspector(DataAccess.getConnection());
	}

	public void assertHasAllQuartzIndexes() throws Exception {
		for (Index index : _QUARTZ_INDEXES) {
			Assert.assertTrue(
				_dbInspector.hasIndex(
					index.getTableName(), index.getIndexName()));
		}
	}

	public void assertHasAnyQuartzIndex() throws Exception {
		for (Index index : _QUARTZ_INDEXES) {
			Assert.assertFalse(
				_dbInspector.hasIndex(
					index.getTableName(), index.getIndexName()));
		}
	}

	public void dropQuartzIndexes() throws Exception {
		for (Index index : _QUARTZ_INDEXES) {
			_db.runSQL(
				StringBundler.concat(
					"drop index ", index.getIndexName(), " on ",
					index.getTableName()));
		}
	}

	public void rebuildQuartzIndexes() throws Exception {
		if (!_dbInspector.hasIndex("QUARTZ_JOB_DETAILS", "IX_88328984")) {
			_db.runSQLTemplateString(
				"create index IX_88328984 on QUARTZ_JOB_DETAILS (SCHED_NAME, " +
					"JOB_GROUP);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_JOB_DETAILS", "IX_779BCA37")) {
			_db.runSQLTemplateString(
				"create index IX_779BCA37 on QUARTZ_JOB_DETAILS (SCHED_NAME, " +
					"REQUESTS_RECOVERY);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_BE3835E5")) {
			_db.runSQLTemplateString(
				"create index IX_BE3835E5 on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_4BD722BM")) {
			_db.runSQLTemplateString(
				"create index IX_4BD722BM on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, TRIGGER_GROUP);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_339E078M")) {
			_db.runSQLTemplateString(
				"create index IX_339E078M on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_5005E3AF")) {
			_db.runSQLTemplateString(
				"create index IX_5005E3AF on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, JOB_NAME, JOB_GROUP);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_BC2F03B0")) {
			_db.runSQLTemplateString(
				"create index IX_BC2F03B0 on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, JOB_GROUP);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_91CA7CCE")) {
			_db.runSQLTemplateString(
				"create index IX_91CA7CCE on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"TRIGGER_GROUP, NEXT_FIRE_TIME, TRIGGER_STATE, " +
						"MISFIRE_INSTR);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_D219AFDE")) {
			_db.runSQLTemplateString(
				"create index IX_D219AFDE on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"TRIGGER_GROUP, TRIGGER_STATE);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_A85822A0")) {
			_db.runSQLTemplateString(
				"create index IX_A85822A0 on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"JOB_NAME, JOB_GROUP);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_8AA50BE1")) {
			_db.runSQLTemplateString(
				"create index IX_8AA50BE1 on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"JOB_GROUP);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_F2DD7C7E")) {
			_db.runSQLTemplateString(
				"create index IX_F2DD7C7E on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"NEXT_FIRE_TIME, TRIGGER_STATE, MISFIRE_INSTR);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_1F92813C")) {
			_db.runSQLTemplateString(
				"create index IX_1F92813C on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"NEXT_FIRE_TIME, MISFIRE_INSTR);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_99108B6E")) {
			_db.runSQLTemplateString(
				"create index IX_99108B6E on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"TRIGGER_STATE);",
				false);
		}

		if (!_dbInspector.hasIndex("QUARTZ_TRIGGERS", "IX_CD7132D0")) {
			_db.runSQLTemplateString(
				"create index IX_CD7132D0 on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"CALENDAR_NAME);",
				false);
		}
	}

	public void runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_3." +
			"QuartzUpgradeProcess";

	private static final Index[] _QUARTZ_INDEXES = {
		new Index("IX_88328984", "QUARTZ_JOB_DETAILS", false),
		new Index("IX_779BCA37", "QUARTZ_JOB_DETAILS", false),
		new Index("IX_BE3835E5", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_4BD722BM", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_339E078M", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_5005E3AF", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_BC2F03B0", "QUARTZ_FIRED_TRIGGERS", false),
		new Index("IX_91CA7CCE", "QUARTZ_TRIGGERS", false),
		new Index("IX_D219AFDE", "QUARTZ_TRIGGERS", false),
		new Index("IX_A85822A0", "QUARTZ_TRIGGERS", false),
		new Index("IX_8AA50BE1", "QUARTZ_TRIGGERS", false),
		new Index("IX_F2DD7C7E", "QUARTZ_TRIGGERS", false),
		new Index("IX_1F92813C", "QUARTZ_TRIGGERS", false),
		new Index("IX_99108B6E", "QUARTZ_TRIGGERS", false),
		new Index("IX_CD7132D0", "QUARTZ_TRIGGERS", false)
	};

	private final DB _db;
	private final DBInspector _dbInspector;
	private final UpgradeStepRegistrator _upgradeStepRegistrator;

}