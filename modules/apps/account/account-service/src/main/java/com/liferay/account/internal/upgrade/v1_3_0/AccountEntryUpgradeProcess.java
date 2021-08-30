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

package com.liferay.account.internal.upgrade.v1_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Pei-Jung Lan
 */
public class AccountEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("AccountEntry", "defaultBillingAddressId")) {
			alterTableAddColumn(
				"AccountEntry", "defaultBillingAddressId", "LONG");
		}

		if (!hasColumn("AccountEntry", "defaultShippingAddressId")) {
			alterTableAddColumn(
				"AccountEntry", "defaultShippingAddressId", "LONG");
		}

		if (!hasColumn("AccountEntry", "emailAddress")) {
			alterTableAddColumn(
				"AccountEntry", "emailAddress", "VARCHAR(254) null");
		}

		if (!hasColumn("AccountEntry", "taxExemptionCode")) {
			alterTableAddColumn(
				"AccountEntry", "taxExemptionCode", "VARCHAR(75) null");
		}
	}

}