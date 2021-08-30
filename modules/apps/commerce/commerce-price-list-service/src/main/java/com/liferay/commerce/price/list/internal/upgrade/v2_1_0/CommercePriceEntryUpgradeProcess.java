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

import com.liferay.commerce.price.list.internal.upgrade.base.BaseCommercePriceListUpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Riccardo Alberti
 */
public class CommercePriceEntryUpgradeProcess
	extends BaseCommercePriceListUpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		addColumn("CommercePriceEntry", "discountDiscovery", "BOOLEAN");
		addColumn("CommercePriceEntry", "discountLevel1", "DECIMAL(30,16)");
		addColumn("CommercePriceEntry", "discountLevel2", "DECIMAL(30,16)");
		addColumn("CommercePriceEntry", "discountLevel3", "DECIMAL(30,16)");
		addColumn("CommercePriceEntry", "discountLevel4", "DECIMAL(30,16)");
		addColumn("CommercePriceEntry", "bulkPricing", "BOOLEAN");
		addColumn("CommercePriceEntry", "displayDate", "DATE");
		addColumn("CommercePriceEntry", "expirationDate", "DATE");
		addColumn("CommercePriceEntry", "status", "INTEGER");
		addColumn("CommercePriceEntry", "statusByUserId", "LONG");
		addColumn("CommercePriceEntry", "statusByUserName", "VARCHAR(75)");
		addColumn("CommercePriceEntry", "statusDate", "DATE");

		runSQL("UPDATE CommercePriceEntry SET bulkPricing = [$TRUE$]");
		runSQL("UPDATE CommercePriceEntry SET displayDate = lastPublishDate");
		runSQL(
			"UPDATE CommercePriceEntry SET status = " +
				WorkflowConstants.STATUS_APPROVED);
		runSQL("UPDATE CommercePriceEntry SET statusByUserId = userId");
		runSQL("UPDATE CommercePriceEntry SET statusByUserName = userName");
		runSQL("UPDATE CommercePriceEntry SET statusDate = modifiedDate");
	}

}