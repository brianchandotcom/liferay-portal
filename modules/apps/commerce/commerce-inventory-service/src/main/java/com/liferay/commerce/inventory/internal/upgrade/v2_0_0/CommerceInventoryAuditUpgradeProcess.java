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

package com.liferay.commerce.inventory.internal.upgrade.v2_0_0;

import com.liferay.commerce.inventory.internal.upgrade.v2_0_0.util.CommerceInventoryAuditTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryAuditUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableAddColumn("CIAudit", "logType", "VARCHAR(75)");
		alterTableAddColumn("CIAudit", "logTypeSettings", "TEXT");

		if (hasColumn(CommerceInventoryAuditTable.TABLE_NAME, "description")) {
			runSQL("delete from CIAudit");

			alterTableDropColumn("CIAudit", "description");
		}
	}

}