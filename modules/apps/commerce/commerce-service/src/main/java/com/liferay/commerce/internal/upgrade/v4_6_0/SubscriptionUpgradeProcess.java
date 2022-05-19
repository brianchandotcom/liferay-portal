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

package com.liferay.commerce.internal.upgrade.v4_6_0;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;

/**
 * @author Luca Pellizzon
 */
public class SubscriptionUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			"CommerceSubscriptionEntry", "deliverySubscriptionLength",
			"INTEGER");

		addColumn(
			"CommerceSubscriptionEntry", "deliverySubscriptionType",
			"VARCHAR(75)");

		addColumn(
			"CommerceSubscriptionEntry", "deliverySubTypeSettings", "TEXT");

		addColumn("CommerceSubscriptionEntry", "deliveryCurrentCycle", "LONG");

		addColumn(
			"CommerceSubscriptionEntry", "deliveryMaxSubscriptionCycles",
			"LONG");

		addColumn(
			"CommerceSubscriptionEntry", "deliverySubscriptionStatus",
			"INTEGER");

		addColumn(
			"CommerceSubscriptionEntry", "deliveryLastIterationDate", "DATE");

		addColumn(
			"CommerceSubscriptionEntry", "deliveryNextIterationDate", "DATE");

		addColumn("CommerceSubscriptionEntry", "deliveryStartDate", "DATE");
	}

}