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

import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_3.util.DDMStorageLinkTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDMStorageLink extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("DDMStorageLink", "structureVersionId")) {
			alter(
				DDMStorageLinkTable.class,
				new AlterTableAddColumn("structureVersionId LONG"));
		}

		try (PreparedStatement ps1 = connection.prepareStatement(
				StringBundler.concat(
					"select structureId, structureVersionId from ",
					"DDMStructureVersion parentStructureVersion where ",
					"parentStructureVersion.structureId in (select ",
					"structureId from DDMStorageLink) and version in (select ",
					"max(childStructureVersion.version) from ",
					"DDMStructureVersion childStructureVersion where ",
					"childStructureVersion.structureId = ",
					"parentStructureVersion.structureId)"));
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStorageLink set structureVersionId = ? where " +
						"structureId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long ddmStructureId = rs.getLong("structureId");

				long ddmStructureVersionId = rs.getLong("structureVersionId");

				if (ddmStructureVersionId > 0) {
					ps2.setLong(1, ddmStructureVersionId);
					ps2.setLong(2, ddmStructureId);

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

}