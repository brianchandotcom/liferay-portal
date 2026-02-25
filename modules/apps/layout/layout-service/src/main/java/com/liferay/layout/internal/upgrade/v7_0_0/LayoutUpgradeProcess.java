/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Georgel Pop
 */
public class LayoutUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select Image.externalReferenceCode, ",
					"Layout.ctCollectionId, Layout.plid from Layout inner ",
					"join Image on (Image.ctCollectionId = ",
					"Layout.ctCollectionId or Image.ctCollectionId = 0) and ",
					"Image.companyId = Layout.companyId and Image.imageId = ",
					"Layout.iconImageId where Layout.iconImageId > 0 order by ",
					"Layout.plid asc, Layout.ctCollectionId desc, ",
					"Image.ctCollectionId desc"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update Layout set iconImageERC = ? where ctCollectionId " +
						"= ? and plid = ?")) {

			long lastPlid = -1;
			long lastCtCollectionId = -1;

			while (resultSet.next()) {
				long ctCollectionId = resultSet.getLong(2);
				long plid = resultSet.getLong(3);

				if ((plid == lastPlid) &&
					(ctCollectionId == lastCtCollectionId)) {

					continue;
				}

				lastCtCollectionId = ctCollectionId;
				lastPlid = plid;

				preparedStatement2.setString(1, resultSet.getString(1));
				preparedStatement2.setLong(2, ctCollectionId);
				preparedStatement2.setLong(3, plid);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("Layout", "iconImageId")
		};
	}

}