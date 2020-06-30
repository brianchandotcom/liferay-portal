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

package com.liferay.commerce.product.internal.upgrade.v1_2_0;

import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.model.impl.CPInstanceImpl;

/**
 * @author Marco Leo
 */
public class ProductSubscriptionUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"subscriptionEnabled", "BOOLEAN");
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"subscriptionLength", "INTEGER");
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"subscriptionType", "VARCHAR(75)");
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"subscriptionTypeSettings", "TEXT");
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"maxSubscriptionCycles", "LONG");

		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"overrideSubscriptionInfo", "BOOLEAN");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"subscriptionEnabled", "BOOLEAN");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"subscriptionLength", "INTEGER");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME, "subscriptionType",
			"VARCHAR(75)");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"subscriptionTypeSettings", "TEXT");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"maxSubscriptionCycles", "LONG");
	}

}