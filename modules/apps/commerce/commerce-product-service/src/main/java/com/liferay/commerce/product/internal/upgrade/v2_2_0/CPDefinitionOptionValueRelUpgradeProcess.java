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

package com.liferay.commerce.product.internal.upgrade.v2_2_0;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.internal.upgrade.v2_2_0.util.CPDefinitionOptionRelTable;
import com.liferay.commerce.product.internal.upgrade.v2_2_0.util.CPDefinitionOptionValueRelTable;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionValueRelUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CPDefinitionOptionValueRelTable.class,
			CPDefinitionOptionValueRelTable.TABLE_NAME, "CPInstanceUuid",
			"VARCHAR(75)");

		addColumn(
			CPDefinitionOptionValueRelTable.class,
			CPDefinitionOptionValueRelTable.TABLE_NAME, "CProductId", "LONG");

		addColumn(
			CPDefinitionOptionValueRelTable.class,
			CPDefinitionOptionValueRelTable.TABLE_NAME, "quantity", "INTEGER");

		addColumn(
			CPDefinitionOptionValueRelTable.class,
			CPDefinitionOptionValueRelTable.TABLE_NAME, "price",
			"DECIMAL(30, 16)");

		addColumn(
			CPDefinitionOptionRelTable.class,
			CPDefinitionOptionRelTable.TABLE_NAME, "priceType", "VARCHAR(75)");

		runSQL(
			String.format(
				"update %s set priceType = '%s'",
				CPDefinitionOptionRelTable.TABLE_NAME,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC));

		runSQL(
			String.format(
				"update %s set price = 0",
				CPDefinitionOptionValueRelTable.TABLE_NAME));
	}

}