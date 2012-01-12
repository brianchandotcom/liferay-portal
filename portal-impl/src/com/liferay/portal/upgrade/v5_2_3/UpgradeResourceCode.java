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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v5_2_3.util.ResourceCodeTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeResourceCode extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnType()) {
			runSQL("alter_column_type ResourceCode name VARCHAR(255) null");
		}
		else {

			// ResourceCode

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				ResourceCodeTable.TABLE_NAME, ResourceCodeTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(ResourceCodeTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(ResourceCodeTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}
	}

}