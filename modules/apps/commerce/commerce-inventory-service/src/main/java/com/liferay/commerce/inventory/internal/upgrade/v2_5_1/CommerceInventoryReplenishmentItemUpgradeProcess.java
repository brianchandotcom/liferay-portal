/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.upgrade.v2_5_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian I. Kim
 */
public class CommerceInventoryReplenishmentItemUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"delete from CIReplenishmentItem where ",
				"CIReplenishmentItem.sku not in (select CIWarehouseItem.sku ",
				"from CIWarehouseItem where CIReplenishmentItem.companyId = ",
				"CIWarehouseItem.companyId and ",
				"CIReplenishmentItem.commerceInventoryWarehouseId = ",
				"CIWarehouseItem.commerceInventoryWarehouseId)"));
	}

}