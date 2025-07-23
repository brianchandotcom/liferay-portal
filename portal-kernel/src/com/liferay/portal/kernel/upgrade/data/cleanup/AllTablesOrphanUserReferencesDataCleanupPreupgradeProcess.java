/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.data.cleanup;

import com.liferay.portal.kernel.upgrade.data.cleanup.util.OrphanReferencesDataCleanupUtil;

/**
 * @author Luis Ortiz
 */
public class AllTablesOrphanUserReferencesDataCleanupPreupgradeProcess
	extends BaseAllTablesOrphanReferencesDataCleanupPreupgradeProcess {

	public AllTablesOrphanUserReferencesDataCleanupPreupgradeProcess() {
		super("userId", "User_");
	}

	@Override
	protected void executeLogic(
			String sourceColumnName, String sourceTableName,
			String targetColumnName, String targetTableName)
		throws Exception {

		OrphanReferencesDataCleanupUtil.fixOrphanUsers(
			connection, sourceColumnName, sourceTableName, targetColumnName,
			targetTableName);
	}

}