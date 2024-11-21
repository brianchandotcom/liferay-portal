/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.upgrade.v4_6_4;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Carlos Correa
 */
public class DeleteStaleBatchEngineDataUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_delete("BatchEngineExportTask");
		_delete("BatchEngineImportTask");
		_delete("BatchEngineImportTaskError");
	}

	private void _delete(String tableName) throws Exception {
		runSQL(
			StringBundler.concat(
				"delete from ", tableName,
				" where not exists (select 1 from Company where ",
				"Company.companyId = ", tableName, ".companyId)"));
	}

}