/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v3_0_0;

import com.liferay.commerce.model.impl.CommerceSubscriptionEntryModelImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Luca Pellizzon
 */
public class CommerceSubscriptionCycleEntryUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(
				CommerceSubscriptionEntryModelImpl.TABLE_NAME,
				"currentCycle")) {

			runSQL(
				"UPDATE CommerceSubscriptionEntry SET currentCycle = 0 WHERE " +
					"currentCycle IS NULL");
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropTables("CSubscriptionCycleEntry")
		};
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CommerceSubscriptionEntry", "currentCycle LONG")
		};
	}

}