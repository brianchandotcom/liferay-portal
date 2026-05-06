/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Balazs Breier
 */
public class RegionExternalReferenceCodeUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select Region.regionId, Region.regionCode, ",
						"Country.a2 from Region inner join Country on ",
						"Country.countryId = Region.countryId where ",
						"Region.ctCollectionId = 0 and ",
						"(Region.externalReferenceCode is null or ",
						"Region.externalReferenceCode = '' or ",
						"Region.externalReferenceCode = Region.uuid_)"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update Region set externalReferenceCode = ? where " +
						"regionId = ? and ctCollectionId = 0");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				updatePreparedStatement.setString(
					1,
					resultSet.getString("a2") + "_" +
						resultSet.getString("regionCode"));
				updatePreparedStatement.setLong(
					2, resultSet.getLong("regionId"));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}

		runSQL("update Region set status = 0");
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"Region", "externalReferenceCode VARCHAR(75)", "status INTEGER")
		};
	}

}