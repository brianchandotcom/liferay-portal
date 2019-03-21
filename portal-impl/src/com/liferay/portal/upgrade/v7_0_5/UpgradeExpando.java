/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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