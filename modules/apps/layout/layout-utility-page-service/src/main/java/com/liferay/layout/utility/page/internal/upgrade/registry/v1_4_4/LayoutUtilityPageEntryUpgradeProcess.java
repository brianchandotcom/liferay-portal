/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.upgrade.registry.v1_4_4;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Víctor Galán
 */
public class LayoutUtilityPageEntryUpgradeProcess extends UpgradeProcess {

	public LayoutUtilityPageEntryUpgradeProcess(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update Layout set layoutId = ?, privateLayout = ?," +
						"type_ = 'utility' where plid = ?")) {

			try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(
					StringBundler.concat(
						"select groupId, plid from Layout where classPK in ",
						"(select plid from LayoutUtilityPageEntry) or plid in ",
						"(select plid from LayoutUtilityPageEntry)"))) {

				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");
					long plid = resultSet.getLong("plid");

					preparedStatement.setLong(
						1, _layoutLocalService.getNextLayoutId(groupId, false));
					preparedStatement.setBoolean(2, false);
					preparedStatement.setLong(3, plid);

					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
			}
		}
	}

	private final LayoutLocalService _layoutLocalService;

}