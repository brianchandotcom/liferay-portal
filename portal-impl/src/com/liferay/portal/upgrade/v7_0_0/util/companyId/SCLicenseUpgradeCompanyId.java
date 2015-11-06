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

package com.liferay.portal.upgrade.v7_0_0.util.companyId;

import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdInTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdUtil;

/**
 * @author Cristina González
 */
public class SCLicenseUpgradeCompanyId implements UpgradeCompanyIdInTable {

	@Override
	public String getTableName() {
		return "SCLicense";
	}

	@Override
	public void upgradeProcess() throws Exception {
		String select =
			"select pe.companyId, lpe.licenseId from SCLicense l, " +
				"SCLicenses_SCProductEntries lpe, SCProductEntry pe " +
				"where l.licenseId=lpe.licenseId and " +
				"lpe.productEntryId=pe.productEntryId";

		String update =
			"update SCLicense set companyId = ? where licenseId = ?";

		UpgradeCompanyIdUtil.updateCompanyColumnOnTable(
			"SCLicense", select, update, "companyId", "licenseId");
	}

}