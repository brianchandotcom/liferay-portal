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
		StringBuilder sb = new StringBuilder(2);

		sb.append("delete from ExpandoRow where rowId_ not in (select ");
		sb.append("rowId_ from ExpandoValue)");

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sb.toString());

		sb = new StringBuilder(2);

		sb.append("delete from ExpandoRow er where not exists (select null ");
		sb.append("from ExpandoValue ev where ev.rowId_ = er.rowId_)");

		String sql = sb.toString();

		dbTypeToSQLMap.add(DBType.POSTGRESQL, sql);

		runSQL(dbTypeToSQLMap);
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteOrphanExpandoRow();
	}

}