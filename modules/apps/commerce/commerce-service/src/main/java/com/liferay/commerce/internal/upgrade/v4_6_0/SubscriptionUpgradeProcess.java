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
import com.liferay.commerce.internal.upgrade.v4_6_0.util.CommerceSubscriptionEntryTable;

/**
 * @author Luca Pellizzon
 */
public class SubscriptionUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionLength")) {

			addColumn(
				"CommerceSubscriptionEntry", "deliverySubscriptionLength",
				"INTEGER");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionType")) {

			addColumn(
				"CommerceSubscriptionEntry", "deliverySubscriptionType",
				"VARCHAR(75)");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubTypeSettings")) {

			addColumn(
				"CommerceSubscriptionEntry", "deliverySubTypeSettings", "TEXT");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryCurrentCycle")) {

			addColumn(
				"CommerceSubscriptionEntry", "deliveryCurrentCycle", "LONG");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryMaxSubscriptionCycles")) {

			addColumn(
				"CommerceSubscriptionEntry", "deliveryMaxSubscriptionCycles",
				"LONG");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliverySubscriptionStatus")) {

			addColumn(
				"CommerceSubscriptionEntry", "deliverySubscriptionStatus",
				"INTEGER");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryLastIterationDate")) {

			addColumn(
				"CommerceSubscriptionEntry", "deliveryLastIterationDate",
				"DATE");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryNextIterationDate")) {

			addColumn(
				"CommerceSubscriptionEntry", "deliveryNextIterationDate",
				"DATE");
		}

		if (!hasColumn(
				CommerceSubscriptionEntryTable.TABLE_NAME,
				"deliveryStartDate")) {

			addColumn("CommerceSubscriptionEntry", "deliveryStartDate", "DATE");
		}
	}

}