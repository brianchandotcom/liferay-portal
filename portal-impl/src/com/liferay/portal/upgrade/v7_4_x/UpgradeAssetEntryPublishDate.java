/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Saurasish Basak
 */
public class UpgradeAssetEntryPublishDate extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			DLFileEntry.class.getName());

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select AssetEntry.entryId, AssetEntry.ctCollectionId, ",
					"DLFileEntry.displayDate from AssetEntry inner join ",
					"DLFileEntry on DLFileEntry.fileEntryId = ",
					"AssetEntry.classPK and DLFileEntry.ctCollectionId = ",
					"AssetEntry.ctCollectionId where AssetEntry.classNameId = ",
					"? and DLFileEntry.displayDate is not null"))) {

			preparedStatement1.setLong(1, classNameId);

			try (ResultSet resultSet = preparedStatement1.executeQuery();
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection,
						"update AssetEntry set publishDate = ? where entryId " +
							"= ? and ctCollectionId = ?")) {

				while (resultSet.next()) {
					long ctCollectionId = resultSet.getLong("ctCollectionId");

					Timestamp displayDate = resultSet.getTimestamp(
						"displayDate");

					long entryId = resultSet.getLong("entryId");

					preparedStatement2.setTimestamp(1, displayDate);

					preparedStatement2.setLong(2, entryId);

					preparedStatement2.setLong(3, ctCollectionId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}