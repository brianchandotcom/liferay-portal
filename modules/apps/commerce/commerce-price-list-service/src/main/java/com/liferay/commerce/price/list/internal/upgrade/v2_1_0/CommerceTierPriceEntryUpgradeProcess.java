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
				"discountLevel1 DECIMAL(30,16)",
				"discountLevel2 DECIMAL(30,16)",
				"discountLevel3 DECIMAL(30,16)",
				"discountLevel4 DECIMAL(30,16)", "displayDate DATE",
				"expirationDate DATE", "status INTEGER", "statusByUserId LONG",
				"statusByUserName VARCHAR(75)", "statusDate DATE")
		};
	}

}