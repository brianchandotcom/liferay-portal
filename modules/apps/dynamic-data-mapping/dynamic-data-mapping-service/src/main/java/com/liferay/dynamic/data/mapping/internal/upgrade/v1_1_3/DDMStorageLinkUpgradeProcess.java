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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class DDMStorageLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("DDMStorageLink", "structureVersionId")) {
			alterTableAddColumn("DDMStorageLink", "structureVersionId", "LONG");
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select distinct DDMStorageLink.structureId, ",
					"TEMP_TABLE.structureVersionId from DDMStorageLink inner ",
					"join (select structureId, max(structureVersionId) as ",
					"structureVersionId from DDMStructureVersion group by ",
					"DDMStructureVersion.structureId) TEMP_TABLE on ",
					"DDMStorageLink.structureId = TEMP_TABLE.structureId"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStorageLink set structureVersionId = ? where " +
						"structureId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long ddmStructureVersionId = resultSet.getLong(
					"structureVersionId");

				if (ddmStructureVersionId > 0) {
					preparedStatement2.setLong(1, ddmStructureVersionId);
					preparedStatement2.setLong(
						2, resultSet.getLong("structureId"));

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

}