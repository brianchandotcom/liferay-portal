/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.search.tuning.rankings.constants.ResultRankingsConstants;
import com.liferay.portal.search.tuning.rankings.index.Ranking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Almir Ferreira
 */
public class JSONStorageEntryRankingUpgradeProcess extends UpgradeProcess {

	public JSONStorageEntryRankingUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("JSONStorageEntry")) {
			return;
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, jsonStorageEntryId, valueString from " +
					"JSONStorageEntry where classNameId = ? and key_ = ?");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JSONStorageEntry set key_ = ?, valueString = ? " +
						"where ctCollectionId = ? and jsonStorageEntryId = " +
							"?")) {

			preparedStatement1.setLong(
				1, _classNameLocalService.getClassNameId(Ranking.class));

			preparedStatement1.setString(2, "inactive");

			ResultSet resultSet = preparedStatement1.executeQuery();

			preparedStatement2.setString(1, "status");

			while (resultSet.next()) {
				String inactiveValueString = resultSet.getString("valueString");

				if (Boolean.valueOf(inactiveValueString)) {
					preparedStatement2.setString(
						2,
						StringBundler.concat(
							StringPool.QUOTE,
							ResultRankingsConstants.STATUS_INACTIVE,
							StringPool.QUOTE));
				}
				else {
					preparedStatement2.setString(
						2,
						StringBundler.concat(
							StringPool.QUOTE,
							ResultRankingsConstants.STATUS_ACTIVE,
							StringPool.QUOTE));
				}

				preparedStatement2.setLong(
					3, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(
					4, resultSet.getLong("jsonStorageEntryId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}