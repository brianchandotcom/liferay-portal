/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_3;

import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Mariano Álvaro Sáiz
 */
public class QuartzUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!DBPartition.isPartitionEnabled()) {
			return;
		}

		if (!hasIndex("QUARTZ_JOB_DETAILS", "IX_88328984")) {
			runSQLTemplateString(
				"create index IX_88328984 on QUARTZ_JOB_DETAILS (SCHED_NAME, " +
					"JOB_GROUP);",
				false);
		}

		if (!hasIndex("QUARTZ_JOB_DETAILS", "IX_779BCA37")) {
			runSQLTemplateString(
				"create index IX_779BCA37 on QUARTZ_JOB_DETAILS (SCHED_NAME, " +
					"REQUESTS_RECOVERY);",
				false);
		}

		if (!hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_BE3835E5")) {
			runSQLTemplateString(
				"create index IX_BE3835E5 on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);",
				false);
		}

		if (!hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_4BD722BM")) {
			runSQLTemplateString(
				"create index IX_4BD722BM on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, TRIGGER_GROUP);",
				false);
		}

		if (!hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_339E078M")) {
			runSQLTemplateString(
				"create index IX_339E078M on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);",
				false);
		}

		if (!hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_5005E3AF")) {
			runSQLTemplateString(
				"create index IX_5005E3AF on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, JOB_NAME, JOB_GROUP);",
				false);
		}

		if (!hasIndex("QUARTZ_FIRED_TRIGGERS", "IX_BC2F03B0")) {
			runSQLTemplateString(
				"create index IX_BC2F03B0 on QUARTZ_FIRED_TRIGGERS (" +
					"SCHED_NAME, JOB_GROUP);",
				false);
		}

		if (!hasIndex("QUARTZ_TRIGGERS", "IX_91CA7CCE")) {
			runSQLTemplateString(
				"create index IX_91CA7CCE on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"TRIGGER_GROUP, NEXT_FIRE_TIME, TRIGGER_STATE, " +
						"MISFIRE_INSTR);",
				false);
		}

		if (!hasIndex("QUARTZ_TRIGGERS", "IX_D219AFDE")) {
			runSQLTemplateString(
				"create index IX_D219AFDE on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"TRIGGER_GROUP, TRIGGER_STATE);",
				false);
		}

		if (!hasIndex("QUARTZ_TRIGGERS", "IX_A85822A0")) {
			runSQLTemplateString(
				"create index IX_A85822A0 on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"JOB_NAME, JOB_GROUP);",
				false);
		}

		if (!hasIndex("QUARTZ_TRIGGERS", "IX_8AA50BE1")) {
			runSQLTemplateString(
				"create index IX_8AA50BE1 on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"JOB_GROUP);",
				false);
		}

		if (!hasIndex("QUARTZ_TRIGGERS", "IX_F2DD7C7E")) {
			runSQLTemplateString(
				"create index IX_F2DD7C7E on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"NEXT_FIRE_TIME, TRIGGER_STATE, MISFIRE_INSTR);",
				false);
		}

		if (!hasIndex("QUARTZ_TRIGGERS", "IX_1F92813C")) {
			runSQLTemplateString(
				"create index IX_1F92813C on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"NEXT_FIRE_TIME, MISFIRE_INSTR);",
				false);
		}

		if (!hasIndex("QUARTZ_TRIGGERS", "IX_99108B6E")) {
			runSQLTemplateString(
				"create index IX_99108B6E on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"TRIGGER_STATE);",
				false);
		}

		if (!hasIndex("QUARTZ_TRIGGERS", "IX_CD7132D0")) {
			runSQLTemplateString(
				"create index IX_CD7132D0 on QUARTZ_TRIGGERS (SCHED_NAME, " +
					"CALENDAR_NAME);",
				false);
		}
	}

}