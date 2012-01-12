/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v6_0_0.util.WikiPageResourceTable;
import com.liferay.portal.upgrade.v6_0_0.util.WikiPageTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeWiki extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnType()) {
			runSQL("alter_column_type WikiPage parentTitle varchar(255) null");
			runSQL(
				"alter_column_type WikiPage redirectTitle varchar(255) null");

			runSQL(
				"alter_column_type WikiPageResource title varchar(255) null");
		}
		else {

			// WikiPage

			upgradeTable(
				WikiPageTable.TABLE_NAME, WikiPageTable.TABLE_COLUMNS,
				WikiPageTable.TABLE_SQL_CREATE,
				WikiPageTable.TABLE_SQL_ADD_INDEXES);

			// WikiPageResource

			upgradeTable(
				WikiPageResourceTable.TABLE_NAME,
				WikiPageResourceTable.TABLE_COLUMNS,
				WikiPageResourceTable.TABLE_SQL_CREATE,
				WikiPageResourceTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}