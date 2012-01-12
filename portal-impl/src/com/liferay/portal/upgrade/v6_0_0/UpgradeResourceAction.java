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
import com.liferay.portal.upgrade.v6_0_0.util.ResourceActionTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeResourceAction extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnType()) {
			runSQL("alter_column_type ResourceAction name VARCHAR(255) null");
		}
		else {

			// Resource

			upgradeTable(
				ResourceActionTable.TABLE_NAME,
				ResourceActionTable.TABLE_COLUMNS,
				ResourceActionTable.TABLE_SQL_CREATE,
				ResourceActionTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}