/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.upgrade.v4_6_4;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Carlos Correa
 */
public class DeleteUnlinkedBatchEngineDataUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			String.format(
				_DELETE_UNLINKED_COMPANY_ID_ROWS_SQL, "BatchEngineExportTask"));
		runSQL(
			String.format(
				_DELETE_UNLINKED_COMPANY_ID_ROWS_SQL, "BatchEngineImportTask"));
		runSQL(
			String.format(
				_DELETE_UNLINKED_COMPANY_ID_ROWS_SQL,
				"BatchEngineImportTaskError"));
	}

	private static final String _DELETE_UNLINKED_COMPANY_ID_ROWS_SQL =
		"delete from %s where not exists (select 1 from Company where " +
			"Company.companyId = %1$s.companyId)";

}