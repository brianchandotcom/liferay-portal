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

package com.liferay.portal.upgrade.v6_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v6_0_1.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v6_0_1.util.DLFileVersionTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnType()) {
			runSQL("alter_column_type DLFileEntry size_ LONG");

			runSQL("alter_column_type DLFileVersion size_ LONG");
		}
		else {

			// DLFileEntry

			upgradeTable(
				DLFileEntryTable.TABLE_NAME, DLFileEntryTable.TABLE_COLUMNS,
				DLFileEntryTable.TABLE_SQL_CREATE,
				DLFileEntryTable.TABLE_SQL_ADD_INDEXES);

			// DLFileVersion

			upgradeTable(
				DLFileVersionTable.TABLE_NAME,
				DLFileVersionTable.TABLE_COLUMNS,
				DLFileVersionTable.TABLE_SQL_CREATE,
				DLFileVersionTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}