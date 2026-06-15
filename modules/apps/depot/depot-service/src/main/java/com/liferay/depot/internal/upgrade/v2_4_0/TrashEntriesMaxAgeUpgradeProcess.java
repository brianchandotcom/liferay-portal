/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.upgrade.v2_4_0;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia García
 */
public class TrashEntriesMaxAgeUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				int trashEntriesMaxAge = PrefsPropsUtil.getInteger(
					companyId, PropsKeys.TRASH_ENTRIES_MAX_AGE,
					PropsValues.TRASH_ENTRIES_MAX_AGE);

				if (trashEntriesMaxAge <= 0) {
					return;
				}

				_updateTrashEntriesMaxAge(companyId, trashEntriesMaxAge);
			});
	}

	private void _updateTrashEntriesMaxAge(
			long companyId, int trashEntriesMaxAge)
		throws Exception {

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select Group_.ctCollectionId, Group_.groupId, ",
						"Group_.typeSettings from DepotEntry inner join ",
						"Group_ on DepotEntry.groupId = Group_.groupId and ",
						"DepotEntry.ctCollectionId = Group_.ctCollectionId ",
						"where DepotEntry.companyId = ? and DepotEntry.type_ ",
						"= ?"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update Group_ set typeSettings = ? where ctCollectionId " +
						"= ? and groupId = ?")) {

			selectPreparedStatement.setLong(1, companyId);
			selectPreparedStatement.setInt(2, DepotConstants.TYPE_SPACE);

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					UnicodeProperties typeSettingsUnicodeProperties =
						UnicodePropertiesBuilder.create(
							true
						).fastLoad(
							resultSet.getString("typeSettings")
						).build();

					int currentTrashEntriesMaxAge = GetterUtil.getInteger(
						typeSettingsUnicodeProperties.getProperty(
							"trashEntriesMaxAge"));

					if (currentTrashEntriesMaxAge > 0) {
						continue;
					}

					typeSettingsUnicodeProperties.setProperty(
						"trashEntriesMaxAge",
						String.valueOf(trashEntriesMaxAge));

					updatePreparedStatement.setString(
						1, typeSettingsUnicodeProperties.toString());
					updatePreparedStatement.setLong(
						2, resultSet.getLong("ctCollectionId"));
					updatePreparedStatement.setLong(
						3, resultSet.getLong("groupId"));

					updatePreparedStatement.addBatch();
				}

				updatePreparedStatement.executeBatch();
			}
		}
	}

}