/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_5;

import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Cristina González
 */
public class UpgradeExpando extends UpgradeProcess {

	protected void deleteOrphanExpandoRow() throws Exception {
		String sql =
			"delete from ExpandoRow where rowId_ not in (select rowId_ from " +
				"ExpandoValue)";

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sql);

		sql =
			"delete from ExpandoRow where not exists (select null from " +
				"ExpandoValue where ExpandoValue.rowId_ = ExpandoRow.rowId_)";

		dbTypeToSQLMap.add(DBType.POSTGRESQL, sql);

		runSQL(dbTypeToSQLMap);
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteOrphanExpandoRow();
	}

}