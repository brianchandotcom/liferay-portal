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

package com.liferay.commerce.product.internal.upgrade.v2_1_0;

import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.model.impl.CPInstanceImpl;

/**
 * @author Luca Pellizzon
 */
public class SubscriptionUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"deliverySubscriptionEnabled", "BOOLEAN");
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"deliverySubscriptionLength", "INTEGER");
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"deliverySubscriptionType", "VARCHAR(75)");
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"deliverySubTypeSettings", "TEXT");
		addColumn(
			CPDefinitionImpl.class, CPDefinitionImpl.TABLE_NAME,
			"deliveryMaxSubscriptionCycles", "LONG");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"deliverySubscriptionEnabled", "BOOLEAN");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"deliverySubscriptionLength", "INTEGER");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"deliverySubscriptionType", "VARCHAR(75)");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"deliverySubTypeSettings", "TEXT");
		addColumn(
			CPInstanceImpl.class, CPInstanceImpl.TABLE_NAME,
			"deliveryMaxSubscriptionCycles", "LONG");
	}

}