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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_4_x.util.AddressTable;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeAddress extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("Address", "externalReferenceCode")) {
			alter(
				AddressTable.class,
				new AlterTableAddColumn(
					"externalReferenceCode", "VARCHAR(75) null"));
		}

		if (!hasColumn("Address", "name")) {
			alter(
				AddressTable.class,
				new AlterTableAddColumn("name", "VARCHAR(255) null"));
		}

		if (!hasColumn("Address", "description")) {
			alter(
				AddressTable.class,
				new AlterTableAddColumn("description", "STRING null"));
		}

		if (hasColumn("Address", "street1")) {
			alter(
				AddressTable.class,
				new AlterColumnType("street1", "VARCHAR(255) null"));
		}

		if (hasColumn("Address", "street2")) {
			alter(
				AddressTable.class,
				new AlterColumnType("street2", "VARCHAR(255) null"));
		}

		if (hasColumn("Address", "street3")) {
			alter(
				AddressTable.class,
				new AlterColumnType("street3", "VARCHAR(255) null"));
		}

		if (!hasColumn("Address", "latitude")) {
			alter(
				AddressTable.class,
				new AlterTableAddColumn("latitude", "DOUBLE"));
		}

		if (!hasColumn("Address", "longitude")) {
			alter(
				AddressTable.class,
				new AlterTableAddColumn("longitude", "DOUBLE"));
		}
	}

}