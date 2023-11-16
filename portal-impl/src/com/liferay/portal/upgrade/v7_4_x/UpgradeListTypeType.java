/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalInstances;

import java.sql.PreparedStatement;

/**
 * @author Luis Ortiz
 */
public class UpgradeListTypeType extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (DBPartition.isPartitionEnabled()) {
			_updateListType(CompanyThreadLocal.getCompanyId(), "intranet");
			_updateListType(CompanyThreadLocal.getCompanyId(), "public");

			return;
		}

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			_updateListType(companyId, "intranet");
			_updateListType(companyId, "public");
		}
	}

	private void _updateListType(long companyId, String name) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ListType set type_ = ? where companyId = ? and name " +
					"= ? and type_ = ?")) {

			preparedStatement.setString(
				1, "com.liferay.portal.kernel.model.Company.website");
			preparedStatement.setLong(2, companyId);
			preparedStatement.setString(3, name);
			preparedStatement.setString(
				4, "com.liferay.account.model.AccountEntry.address");

			preparedStatement.executeUpdate();
		}
	}

}