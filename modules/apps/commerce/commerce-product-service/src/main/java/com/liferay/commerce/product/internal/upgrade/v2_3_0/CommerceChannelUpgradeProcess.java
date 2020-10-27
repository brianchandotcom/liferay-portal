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

package com.liferay.commerce.product.internal.upgrade.v2_3_0;

import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_3_0.util.CommerceChannelTable;

/**
 * @author Riccardo Alberti
 */
public class CommerceChannelUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		addColumn(
			CommerceChannelTable.class, CommerceChannelTable.TABLE_NAME,
			"priceDisplayType", "VARCHAR(75)");

		addColumn(
			CommerceChannelTable.class, CommerceChannelTable.TABLE_NAME,
			"discountsTargetNetPrice", "BOOLEAN");

		runSQL(
			"update CommerceChannel set priceDisplayType = '" +
				CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE + "'");

		runSQL("update CommerceChannel set discountsTargetNetPrice = [$TRUE$]");
	}

}