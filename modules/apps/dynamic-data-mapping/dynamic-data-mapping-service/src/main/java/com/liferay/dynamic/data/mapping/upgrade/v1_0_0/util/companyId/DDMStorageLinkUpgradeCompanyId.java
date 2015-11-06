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

package com.liferay.dynamic.data.mapping.upgrade.v1_0_0.util.companyId;

import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdInTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdUtil;

/**
 * @author Cristina González
 */
public class DDMStorageLinkUpgradeCompanyId implements UpgradeCompanyIdInTable {

	@Override
	public String getTableName() {
		return "DDMStorageLink";
	}

	@Override
	public void upgradeProcess() throws Exception {
		String select =
			"select ds.companyId, dsl.structureId from DDMStructure ds, " +
				"DDMStorageLink dsl where ds.structureId=dsl.structureId";

		String update =
			"update DDMStorageLink set companyId = ? where structureId = ?";

		UpgradeCompanyIdUtil.updateCompanyColumnOnTable(
			"DDMStorageLink", select, update, "companyId", "structureId");
	}

}