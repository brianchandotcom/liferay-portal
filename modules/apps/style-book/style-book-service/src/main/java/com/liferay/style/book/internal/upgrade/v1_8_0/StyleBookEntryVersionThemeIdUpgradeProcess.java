/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.upgrade.v1_8_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Thiago Buarque
 */
public class StyleBookEntryVersionThemeIdUpgradeProcess extends UpgradeProcess {

	public StyleBookEntryVersionThemeIdUpgradeProcess(
		GroupLocalService groupLocalService) {

		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select groupId, ctCollectionId, styleBookEntryId, themeId " +
					"from StyleBookEntry");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update StyleBookEntryVersion set themeId = ? where " +
						"ctCollectionId = ? and styleBookEntryId = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from StyleBookEntryVersion where " +
						"styleBookEntryId = ?");
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from StyleBookEntry where styleBookEntryId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				long styleBookEntryId = resultSet.getLong("styleBookEntryId");

				Group group = _groupLocalService.fetchGroup(groupId);

				if (group == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Deleting style book entry version for style ",
								"book entry ", styleBookEntryId,
								" because group ", groupId, " does not exist"));
					}

					preparedStatement3.setLong(1, styleBookEntryId);

					preparedStatement3.addBatch();

					preparedStatement4.setLong(1, styleBookEntryId);

					preparedStatement4.addBatch();

					continue;
				}

				preparedStatement2.setString(1, resultSet.getString("themeId"));
				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(3, styleBookEntryId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();

			preparedStatement3.executeBatch();

			preparedStatement4.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StyleBookEntryVersionThemeIdUpgradeProcess.class);

	private final GroupLocalService _groupLocalService;

}