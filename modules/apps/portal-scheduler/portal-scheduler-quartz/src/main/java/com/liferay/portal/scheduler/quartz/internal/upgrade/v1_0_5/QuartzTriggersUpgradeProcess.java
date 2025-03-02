/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_5;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Mariano Álvaro Sáiz
 */
public class QuartzTriggersUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasIndex("QUARTZ_TRIGGERS", "IX_186442A4")) {
			return;
		}

		removePrimaryKey("QUARTZ_TRIGGERS");
		runSQL("drop index IX_186442A4 on QUARTZ_TRIGGERS");
		runSQL(
			"alter table QUARTZ_TRIGGERS add primary key (SCHED_NAME, " +
				"TRIGGER_NAME, TRIGGER_GROUP)");
	}

}