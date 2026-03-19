/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.upgrade.v1_4_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

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
		try (PreparedStatement preparedStatement1 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update Layout set layoutId = ?, privateLayout = ?," +
						"type_ = 'utility', typeSettings = ?  where plid = ?");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update LayoutFriendlyURL set privateLayout = ? where " +
						"plid = ?")) {

			try (Statement statement = connection.createStatement();

				ResultSet resultSet = statement.executeQuery(
					StringBundler.concat(
						"select groupId, plid, typeSettings from Layout where ",
						"classPK in (select plid from LayoutUtilityPageEntry) ",
						"or plid in (select plid from ",
						"LayoutUtilityPageEntry)"))) {

				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");

					preparedStatement1.setLong(
						1, _layoutLocalService.getNextLayoutId(groupId, false));

					preparedStatement1.setBoolean(2, false);

					UnicodeProperties typeSettingsUnicodeProperties =
						UnicodePropertiesBuilder.create(
							true
						).load(
							resultSet.getString("typeSettings")
						).build();

					typeSettingsUnicodeProperties.remove("privateLayout");

					preparedStatement1.setString(
						3, typeSettingsUnicodeProperties.toString());

					long plid = resultSet.getLong("plid");

					preparedStatement1.setLong(4, plid);

					preparedStatement1.addBatch();

					preparedStatement2.setBoolean(1, false);
					preparedStatement2.setLong(2, plid);

					preparedStatement2.addBatch();
				}

				preparedStatement1.executeBatch();

				preparedStatement2.executeBatch();
			}
		}
	}

	private final LayoutLocalService _layoutLocalService;

}