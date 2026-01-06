/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v6_0_0;

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
					"select Group_.externalReferenceCode, ",
					"Layout.ctCollectionId, Layout.plid, Layout.groupId, ",
					"LayoutPageTemplateEntry.externalReferenceCode, ",
					"LayoutPageTemplateEntry.groupId from Layout inner join ",
					"LayoutPrototype on (Layout.ctCollectionId = ",
					"LayoutPrototype.ctCollectionId or ",
					"LayoutPrototype.ctCollectionId = 0) and ",
					"Layout.layoutPrototypeUuid = LayoutPrototype.uuid_ inner ",
					"join LayoutPageTemplateEntry on ",
					"(LayoutPageTemplateEntry.ctCollectionId = ",
					"LayoutPrototype.ctCollectionId or ",
					"LayoutPageTemplateEntry.ctCollectionId = 0) and ",
					"LayoutPageTemplateEntry.layoutPrototypeId = ",
					"LayoutPrototype.layoutPrototypeId inner join Group_ on ",
					"(Group_.ctCollectionId = ",
					"LayoutPageTemplateEntry.ctCollectionId or ",
					"Group_.ctCollectionId = 0) and Group_.groupId = ",
					"LayoutPageTemplateEntry.groupId where ",
					"Layout.layoutPrototypeUuid is not null"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update Layout set portletLPTEERC = ?, portletLPTESERC = " +
						"? where ctCollectionId = ? and plid = ?")) {

			while (resultSet.next()) {
				String portletLPTESERC = resultSet.getString(1);
				long layoutGroupId = resultSet.getLong(4);
				long layoutPageTemplateEntryGroupId = resultSet.getLong(6);

				if (layoutGroupId == layoutPageTemplateEntryGroupId) {
					portletLPTESERC = null;
				}

				preparedStatement2.setString(1, resultSet.getString(5));
				preparedStatement2.setString(2, portletLPTESERC);
				preparedStatement2.setLong(3, resultSet.getLong(2));
				preparedStatement2.setLong(4, resultSet.getLong(3));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("Layout", "layoutPrototypeUuid")
		};
	}

}