/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalInstances;

import java.sql.PreparedStatement;

/**
 * @author Luis Ortiz
 */
public class UpgradeMissingListTypes extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (DBPartition.isPartitionEnabled()) {
			_upgradeCompany(CompanyThreadLocal.getCompanyId());

			return;
		}

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			_upgradeCompany(companyId);
		}
	}

	private void _updateListType(long companyId, String name) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"update ListType set type_ = ",
					"'com.liferay.portal.kernel.model.Company.website' where ",
					"companyId = ? and name = ? and type_ = ",
					"'com.liferay.account.model.AccountEntry.address'"))) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, name);

			preparedStatement.executeUpdate();
		}
	}

	private void _upgradeCompany(long companyId) throws Exception {
		_updateListType(companyId, "intranet");
		_updateListType(companyId, "public");
	}

}