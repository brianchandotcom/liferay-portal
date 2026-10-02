/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author Cheryl Tang
 */
public class GroupCompanyGroupKeyUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select companyId, ctCollectionId, groupId, groupKey from " +
					"Group_ where classNameId = ? and classPK = companyId");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update Group_ set groupKey = ? where ctCollectionId = ? " +
						"and groupId = ?")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(Company.class));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String companyIdString = String.valueOf(
						resultSet.getLong("companyId"));

					if (Objects.equals(
							companyIdString, resultSet.getString("groupKey"))) {

						continue;
					}

					preparedStatement2.setString(1, companyIdString);
					preparedStatement2.setLong(
						2, resultSet.getLong("ctCollectionId"));
					preparedStatement2.setLong(3, resultSet.getLong("groupId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}