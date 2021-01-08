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

import com.liferay.account.internal.upgrade.v1_3_0.util.AccountEntryTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeAccountEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("AccountEntry", "defaultBillingAddressId")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn("defaultBillingAddressId", "LONG"));
		}

		if (!hasColumn("AccountEntry", "defaultShippingAddressId")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn("defaultShippingAddressId", "LONG"));
		}

		if (!hasColumn("AccountEntry", "emailAddress")) {
			alter(
				AccountEntryTable.class,
				new AlterTableAddColumn("emailAddress", "VARCHAR(254) null"));
		}
	}

}