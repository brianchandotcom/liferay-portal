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

/**
 * @author Marco Leo
 */
public class ProductSubscriptionUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn("CPDefinition", "subscriptionEnabled", "BOOLEAN");
		addColumn("CPDefinition", "subscriptionLength", "INTEGER");
		addColumn("CPDefinition", "subscriptionType", "VARCHAR(75)");
		addColumn("CPDefinition", "subscriptionTypeSettings", "TEXT");
		addColumn("CPDefinition", "maxSubscriptionCycles", "LONG");

		addColumn("CPInstance", "overrideSubscriptionInfo", "BOOLEAN");
		addColumn("CPInstance", "subscriptionEnabled", "BOOLEAN");
		addColumn("CPInstance", "subscriptionLength", "INTEGER");
		addColumn("CPInstance", "subscriptionType", "VARCHAR(75)");
		addColumn("CPInstance", "subscriptionTypeSettings", "TEXT");
		addColumn("CPInstance", "maxSubscriptionCycles", "LONG");
	}

}