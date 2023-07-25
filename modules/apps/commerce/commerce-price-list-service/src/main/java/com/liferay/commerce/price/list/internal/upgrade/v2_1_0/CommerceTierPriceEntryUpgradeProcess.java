/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.upgrade.v2_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Riccardo Alberti
 */
public class CommerceTierPriceEntryUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		runSQL(
			"update CommerceTierPriceEntry set displayDate = lastPublishDate");
		runSQL(
			"update CommerceTierPriceEntry set status = " +
				WorkflowConstants.STATUS_APPROVED);
		runSQL("update CommerceTierPriceEntry set statusByUserId = userId");
		runSQL("update CommerceTierPriceEntry set statusByUserName = userName");
		runSQL("update CommerceTierPriceEntry set statusDate = modifiedDate");
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CommerceTierPriceEntry", "discountDiscovery BOOLEAN",
				"discountLevel1 BIGDECIMAL", "discountLevel2 BIGDECIMAL",
				"discountLevel3 BIGDECIMAL", "discountLevel4 BIGDECIMAL",
				"displayDate DATE", "expirationDate DATE", "status INTEGER",
				"statusByUserId LONG", "statusByUserName VARCHAR(75)",
				"statusDate DATE")
		};
	}

}