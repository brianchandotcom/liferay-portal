/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_5;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Roberto Díaz
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateParentGroup();
	}

	protected void updateParentGroup() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						StringBundler.concat(
							"select stagingGroup_.groupId, ",
							"liveGroup_.parentGroupId from Group_ ",
							"stagingGroup_ inner join Group_ liveGroup_ on ",
							"(liveGroup_.groupId = stagingGroup_.liveGroupId) ",
							"where (stagingGroup_.remoteStagingGroupCount = ",
							"0) and (liveGroup_.parentGroupId != ",
							"stagingGroup_.parentGroupId)"));
				PreparedStatement preparedStatement2 =
					connection.prepareStatement(
						"select treePath from Group_ where groupId = ?");
				PreparedStatement preparedStatement3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update Group_ set parentGroupId = ?, treePath = ? " +
							"where groupId = ?");
				ResultSet resultSet1 = preparedStatement1.executeQuery()) {

				while (resultSet1.next()) {
					long groupId = resultSet1.getLong(1);

					long parentGroupId = resultSet1.getLong(2);

					preparedStatement2.setLong(1, parentGroupId);

					try (ResultSet resultSet2 =
							preparedStatement2.executeQuery()) {

						String treePath = null;

						if (resultSet2.next()) {
							treePath = resultSet2.getString("treePath");

							treePath = treePath.concat(String.valueOf(groupId));

							treePath = treePath.concat(StringPool.SLASH);
						}
						else {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Unable to find group " + parentGroupId);
							}

							treePath = StringBundler.concat(
								StringPool.SLASH, parentGroupId,
								StringPool.SLASH, groupId, StringPool.SLASH);
						}

						preparedStatement3.setLong(1, parentGroupId);
						preparedStatement3.setString(2, treePath);
						preparedStatement3.setLong(3, groupId);

						preparedStatement3.addBatch();
					}
				}

				preparedStatement3.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeGroup.class);

	private class GroupTreeModel implements TreeModel {

		public GroupTreeModel(PreparedStatement preparedStatement) {
			_preparedStatement = preparedStatement;
		}

		@Override
		public String buildTreePath() throws PortalException {
			return null;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return _groupId;
		}

		@Override
		public String getTreePath() {
			return null;
		}

		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
			_groupId = (Long)primaryKeyObj;
		}

		@Override
		public void updateTreePath(String treePath) {
			try {
				_preparedStatement.setString(1, treePath);
				_preparedStatement.setLong(2, _groupId);

				_preparedStatement.addBatch();
			}
			catch (SQLException sqlException) {
				_log.error(
					"Unable to update tree path: " + treePath, sqlException);
			}
		}

		private long _groupId;
		private final PreparedStatement _preparedStatement;

	}

}