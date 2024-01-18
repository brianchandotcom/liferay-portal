/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_2;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jonathan McCann
 */
public class LayoutUpgradeProcess extends UpgradeProcess {

	public LayoutUpgradeProcess(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"SELECT groupId, plid FROM Layout WHERE privateLayout = ? ",
					"AND (plid IN (SELECT plid FROM LayoutPageTemplateEntry ",
					"WHERE type_ = ?) OR (classPK IN (SELECT plid FROM ",
					"LayoutPageTemplateEntry WHERE type_ = ?) AND classNameId ",
					"= ?))"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update Layout set privateLayout = ?, layoutId = ? where " +
						"plid = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update LayoutFriendlyURL set privateLayout = ? where " +
						"plid = ?")) {

			preparedStatement1.setBoolean(1, false);
			preparedStatement1.setLong(
				2, LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT);
			preparedStatement1.setLong(
				3, LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT);
			preparedStatement1.setLong(
				4, PortalUtil.getClassNameId(Layout.class.getName()));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");
					long plid = resultSet.getLong("plid");

					preparedStatement2.setBoolean(1, true);
					preparedStatement2.setLong(
						2, _layoutLocalService.getNextLayoutId(groupId, true));
					preparedStatement2.setLong(3, plid);

					preparedStatement2.addBatch();

					preparedStatement3.setBoolean(1, true);
					preparedStatement3.setLong(2, plid);

					preparedStatement3.addBatch();
				}
			}

			preparedStatement2.executeBatch();

			preparedStatement3.executeBatch();
		}
	}

	private final LayoutLocalService _layoutLocalService;

}