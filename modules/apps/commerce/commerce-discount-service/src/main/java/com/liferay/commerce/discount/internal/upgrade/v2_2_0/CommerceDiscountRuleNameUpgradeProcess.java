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

package com.liferay.commerce.discount.internal.upgrade.v2_2_0;

import com.liferay.commerce.discount.internal.upgrade.base.BaseCommerceDiscountUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.util.CommerceDiscountRuleTable;

/**
 * @author Riccardo Alberti
 */
public class CommerceDiscountRuleNameUpgradeProcess
	extends BaseCommerceDiscountUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CommerceDiscountRuleTable.class,
			CommerceDiscountRuleTable.TABLE_NAME, "name", "VARCHAR(75)");

		runSQL("update CommerceDiscountRule set name = type_");
	}

}