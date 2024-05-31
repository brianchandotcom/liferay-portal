/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_3;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Mariano Álvaro Sáiz
 */
public class QuartzUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!DBPartition.isPartitionEnabled()) {
			return;
		}

		for (String createIndexSQLStatement : _CREATE_INDEX_SQL_STATEMENTS) {
			_addIndex(createIndexSQLStatement);
		}
	}

	private void _addIndex(String createIndexSQLStatement) throws Exception {
		String[] parts = StringUtil.split(
			createIndexSQLStatement, CharPool.SPACE);

		if (!hasIndex(parts[4], parts[2])) {
			runSQL(createIndexSQLStatement);
		}
	}

	private static final String[] _CREATE_INDEX_SQL_STATEMENTS = {
		"CREATE INDEX IX_4BD722BM ON QUARTZ_FIRED_TRIGGERS (SCHED_NAME, " +
			"TRIGGER_GROUP)",
		"CREATE INDEX IX_339E078M ON QUARTZ_FIRED_TRIGGERS (SCHED_NAME, " +
			"INSTANCE_NAME, REQUESTS_RECOVERY)",
		"CREATE INDEX IX_5005E3AF ON QUARTZ_FIRED_TRIGGERS (SCHED_NAME, " +
			"JOB_NAME, JOB_GROUP)",
		"CREATE INDEX IX_BC2F03B0 ON QUARTZ_FIRED_TRIGGERS (SCHED_NAME, " +
			"JOB_GROUP)",
		"CREATE INDEX IX_BE3835E5 ON QUARTZ_FIRED_TRIGGERS (SCHED_NAME, " +
			"TRIGGER_NAME, TRIGGER_GROUP)",
		"CREATE INDEX IX_CD7132D0 ON QUARTZ_TRIGGERS (SCHED_NAME, " +
			"CALENDAR_NAME)",
		"CREATE INDEX IX_F2DD7C7E ON QUARTZ_TRIGGERS (SCHED_NAME, " +
			"NEXT_FIRE_TIME, TRIGGER_STATE, MISFIRE_INSTR)",
		"CREATE INDEX IX_1F92813C ON QUARTZ_TRIGGERS (SCHED_NAME, " +
			"NEXT_FIRE_TIME, MISFIRE_INSTR)",
		"CREATE INDEX IX_A85822A0 ON QUARTZ_TRIGGERS (SCHED_NAME, JOB_NAME, " +
			"JOB_GROUP)",
		"CREATE INDEX IX_8AA50BE1 ON QUARTZ_TRIGGERS (SCHED_NAME, JOB_GROUP)",
		"CREATE INDEX IX_91CA7CCE ON QUARTZ_TRIGGERS (SCHED_NAME, " +
			"TRIGGER_GROUP, NEXT_FIRE_TIME, TRIGGER_STATE, MISFIRE_INSTR)",
		"CREATE INDEX IX_D219AFDE ON QUARTZ_TRIGGERS (SCHED_NAME, " +
			"TRIGGER_GROUP, TRIGGER_STATE)",
		"CREATE INDEX IX_99108B6E ON QUARTZ_TRIGGERS (SCHED_NAME, " +
			"TRIGGER_STATE)",
		"CREATE INDEX IX_88328984 ON QUARTZ_JOB_DETAILS (SCHED_NAME, " +
			"JOB_GROUP)",
		"CREATE INDEX IX_779BCA37 ON QUARTZ_JOB_DETAILS (SCHED_NAME, " +
			"REQUESTS_RECOVERY)"
	};

}