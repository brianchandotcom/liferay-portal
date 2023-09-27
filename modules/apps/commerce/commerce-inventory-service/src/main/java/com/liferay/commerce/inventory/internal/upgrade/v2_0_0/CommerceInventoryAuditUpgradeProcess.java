/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.upgrade.v2_0_0;

import com.liferay.commerce.inventory.internal.upgrade.v2_0_0.util.CommerceInventoryAuditTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryAuditUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(CommerceInventoryAuditTable.TABLE_NAME, "description")) {
			runSQL("delete from CIAudit");

			alterTableDropColumn("CIAudit", "description");
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CIAudit", "logType VARCHAR(75)", "logTypeSettings TEXT")
		};
	}

}