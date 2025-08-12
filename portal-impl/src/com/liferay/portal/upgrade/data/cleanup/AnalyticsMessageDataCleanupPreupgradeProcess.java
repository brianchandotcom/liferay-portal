/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;

/**
 * @author Luis Ortiz
 */
public class AnalyticsMessageDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DBInspector dbInspector = new DBInspector(connection);

		if (!dbInspector.hasTable("AnalyticsMessage") ||
			!hasRows("AnalyticsMessage")) {

			return;
		}

		runSQL("truncate table AnalyticsMessage");

		if (_log.isInfoEnabled()) {
			_log.info(
				"Deleted content of table " +
					dbInspector.normalizeName("AnalyticsMessage"));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsMessageDataCleanupPreupgradeProcess.class);

}