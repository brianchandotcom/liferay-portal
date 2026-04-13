/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v4_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alberto Chaparro
 */
public class LayoutUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select LayoutPageTemplateEntry.externalReferenceCode, ",
					"Layout.ctCollectionId, Layout.plid from Layout inner ",
					"join LayoutPageTemplateEntry on Layout.masterLayoutPlid ",
					"= LayoutPageTemplateEntry.plid and (",
					"LayoutPageTemplateEntry.ctCollectionId = Layout.",
					"ctCollectionId or LayoutPageTemplateEntry.ctCollectionId ",
					"= 0) where Layout.masterLayoutPlid > 0"));

			ResultSet resultSet = preparedStatement1.executeQuery();

			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update Layout set masterLPTEERC = ? where " +
						"ctCollectionId = ? and plid = ? ")) {

			while (resultSet.next()) {
				preparedStatement2.setString(
					1, resultSet.getString("externalReferenceCode"));
				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(3, resultSet.getLong("plid"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("Layout", "masterLayoutPlid")
		};
	}

}