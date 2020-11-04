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
import com.liferay.portal.upgrade.v7_4_x.util.CountryTable;

/**
 * @author Albert Lee
 */
public class UpgradeCountry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQLTemplate("update-7.3.0-7.4.0.sql", false);

		if (!hasColumn("Country", "uuid_")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("uuid_", "VARCHAR(75) null"));
		}

		if (!hasColumn("Country", "companyId")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("companyId", "LONG"));
		}

		if (!hasColumn("Country", "userId")) {
			alter(
				CountryTable.class, new AlterTableAddColumn("userId", "LONG"));
		}

		if (!hasColumn("Country", "userName")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("userName", "VARCHAR(75) null"));
		}

		if (!hasColumn("Country", "createDate")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("createDate", "DATE null"));
		}

		if (!hasColumn("Country", "modifiedDate")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("modifiedDate", "DATE null"));
		}

		if (hasColumn("Country", "billingAllowed")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("billingAllowed", "BOOLEAN"));
		}

		if (hasColumn("Country", "shippingAllowed")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("shippingAllowed", "BOOLEAN"));
		}

		if (hasColumn("Country", "subjectToVAT")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("subjectToVAT", "BOOLEAN"));
		}

		if (!hasColumn("Country", "position")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("position", "DOUBLE"));
		}

		if (!hasColumn("Country", "groupFilterEnabled")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("groupFilterEnabled", "BOOLEAN"));
		}

		if (!hasColumn("Country", "lastPublishDate")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("lastPublishDate", "DATE null"));
		}
	}

}