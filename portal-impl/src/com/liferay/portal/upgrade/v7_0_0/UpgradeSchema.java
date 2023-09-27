/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Julio Camarero
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_runSQLTemplate(
			"update-6.2.0-7.0.0.sql", "update-6.2.0-7.0.0-asset.sql",
			"update-6.2.0-7.0.0-group.sql", "update-6.2.0-7.0.0-layoutset.sql",
			"update-6.2.0-7.0.0-layoutsetbranch.sql");

		upgrade(new UpgradeMVCCVersion());
	}

	private void _runSQLTemplate(String... sqlFileNames) throws Exception {
		for (String sqlFileName : sqlFileNames) {
			try (LoggingTimer loggingTimer = new LoggingTimer(sqlFileName)) {
				runSQLTemplate(sqlFileName, false);
			}
		}
	}

}