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
import com.liferay.portal.upgrade.v7_4_x.util.RegionTable;

/**
 * @author Drew Brokke
 */
public class UpgradeRegion extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("Region", "companyId")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("companyId", "LONG"));
		}

		if (!hasColumn("Region", "userId")) {
			alter(RegionTable.class, new AlterTableAddColumn("userId", "LONG"));
		}

		if (!hasColumn("Region", "userName")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("userName", "VARCHAR(75) null"));
		}

		if (!hasColumn("Region", "createDate")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("createDate", "DATE null"));
		}

		if (!hasColumn("Region", "modifiedDate")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("modifiedDate", "DATE null"));
		}

		if (!hasColumn("Region", "position")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("position", "DOUBLE"));
		}

		if (!hasColumn("Region", "lastPublishDate")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("lastPublishDate", "DATE null"));
		}
	}

}