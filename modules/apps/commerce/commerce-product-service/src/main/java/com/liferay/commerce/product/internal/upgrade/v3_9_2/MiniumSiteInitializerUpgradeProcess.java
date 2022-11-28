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

package com.liferay.commerce.product.internal.upgrade.v3_9_2;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Crescenzo Rega
 * @author Andrea Sbarra
 */
public class MiniumSiteInitializerUpgradeProcess extends UpgradeProcess {

	public MiniumSiteInitializerUpgradeProcess(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select siteGroupId from CommerceChannel where siteGroupId " +
					"in (select groupId from LayoutSet where privateLayout = " +
						"? and themeId = 'minium_WAR_miniumtheme')")) {

			preparedStatement.setBoolean(1, true);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long siteGroupId = resultSet.getLong("siteGroupId");

					_updateLayouts(siteGroupId);

					_updateLayoutPriorities(siteGroupId);
				}
			}
		}
	}

	private void _updateLayoutPriorities(long siteGroupId) throws SQLException {
		PreparedStatement preparedStatement1 =
			AutoBatchPreparedStatementUtil.autoBatch(
				connection,
				"select plid from Layout where friendlyURL != '/login' and " +
					"groupId = ? and parentPlid = 0 and type_ = 'portlet' " +
						"order by priority");
		PreparedStatement preparedStatement2 =
			AutoBatchPreparedStatementUtil.autoBatch(
				connection,
				"update Layout set priority = ? where groupId = ? and plid = " +
					"?");

		preparedStatement1.setLong(1, siteGroupId);

		try (ResultSet resultSet = preparedStatement1.executeQuery()) {
			long priority = 0;

			while (resultSet.next()) {
				priority++;

				preparedStatement2.setLong(1, priority);
				preparedStatement2.setLong(2, siteGroupId);
				preparedStatement2.setLong(3, resultSet.getLong("plid"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private void _updateLayouts(long siteGroupId) throws SQLException {
		PreparedStatement preparedStatement1 =
			AutoBatchPreparedStatementUtil.autoBatch(
				connection,
				"select plid from Layout where groupId = ? and privateLayout " +
					"= ? ORDER by layoutId");
		PreparedStatement preparedStatement2 =
			AutoBatchPreparedStatementUtil.autoBatch(
				connection,
				"update Layout set layoutId = ?, privateLayout = ? where " +
					"groupId = ? and plid = ? and privateLayout = ?");
		PreparedStatement preparedStatement3 =
			AutoBatchPreparedStatementUtil.autoBatch(
				connection,
				"update Layout set parentLayoutId = ? where groupId = ? and " +
					"parentLayoutId > 0 and parentPlid = ?");
		PreparedStatement preparedStatement4 =
			AutoBatchPreparedStatementUtil.autoBatch(
				connection,
				"update LayoutFriendlyURL set privateLayout = ? where " +
					"groupId = ? and plid = ? and privateLayout = ?");

		preparedStatement1.setLong(1, siteGroupId);
		preparedStatement1.setBoolean(2, true);

		try (ResultSet resultSet = preparedStatement1.executeQuery()) {
			while (resultSet.next()) {
				long newLayoutId = _counterLocalService.increment(
					StringBundler.concat(
						Layout.class.getName(), StringPool.POUND, siteGroupId,
						StringPool.POUND, false));

				long plid = resultSet.getLong("plid");

				preparedStatement2.setLong(1, newLayoutId);
				preparedStatement2.setBoolean(2, false);
				preparedStatement2.setLong(3, siteGroupId);
				preparedStatement2.setLong(4, plid);
				preparedStatement2.setBoolean(5, true);

				preparedStatement2.addBatch();

				preparedStatement3.setLong(1, newLayoutId);
				preparedStatement3.setLong(2, siteGroupId);
				preparedStatement3.setLong(3, plid);
				preparedStatement3.addBatch();

				preparedStatement4.setBoolean(1, false);
				preparedStatement4.setLong(2, siteGroupId);
				preparedStatement4.setLong(3, plid);
				preparedStatement4.setBoolean(4, true);

				preparedStatement4.addBatch();
			}
		}

		preparedStatement2.executeBatch();

		preparedStatement3.executeBatch();

		preparedStatement4.executeBatch();
	}

	private final CounterLocalService _counterLocalService;

}