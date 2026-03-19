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
					"select Group_.externalReferenceCode as ",
					"externalReferenceCode1, Layout.ctCollectionId, Layout.",
					"plid, Layout.groupId as groupId1, ",
					"LayoutPageTemplateEntry.externalReferenceCode as ",
					"externalReferenceCode2, LayoutPageTemplateEntry.groupId ",
					"as groupId2 from Layout inner join LayoutPrototype on (",
					"Layout.ctCollectionId = LayoutPrototype.ctCollectionId ",
					"or LayoutPrototype.ctCollectionId = 0) and Layout.",
					"layoutPrototypeUuid = LayoutPrototype.uuid_ inner join ",
					"LayoutPageTemplateEntry on (LayoutPageTemplateEntry.",
					"ctCollectionId = LayoutPrototype.ctCollectionId or ",
					"LayoutPageTemplateEntry.ctCollectionId = 0) and ",
					"LayoutPageTemplateEntry.layoutPrototypeId = ",
					"LayoutPrototype.layoutPrototypeId inner join Group_ on ",
					"(Group_.ctCollectionId = LayoutPageTemplateEntry.",
					"ctCollectionId or Group_.ctCollectionId = 0) and Group_.",
					"groupId = LayoutPageTemplateEntry.groupId where Layout.",
					"layoutPrototypeUuid is not null"));

			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update Layout set portletLPTEERC = ?, portletLPTESERC = " +
						"? where ctCollectionId = ? and plid = ?")) {

			while (resultSet.next()) {
				String portletLPTESERC = resultSet.getString(
					"externalReferenceCode1");
				long layoutGroupId = resultSet.getLong("groupId1");
				long layoutPageTemplateEntryGroupId = resultSet.getLong(
					"groupId2");

				if (layoutGroupId == layoutPageTemplateEntryGroupId) {
					portletLPTESERC = null;
				}

				preparedStatement2.setString(
					1, resultSet.getString("externalReferenceCode2"));
				preparedStatement2.setString(2, portletLPTESERC);
				preparedStatement2.setLong(
					3, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(4, resultSet.getLong("plid"));

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