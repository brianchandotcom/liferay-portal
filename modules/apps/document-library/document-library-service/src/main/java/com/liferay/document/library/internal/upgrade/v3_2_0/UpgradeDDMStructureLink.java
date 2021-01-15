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

package com.liferay.document.library.internal.upgrade.v3_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia García
 */
public class UpgradeDDMStructureLink extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("select DLFileEntryType.fileEntryTypeId, ");
		sb.append("DLFileEntryType.dataDefinitionId from DLFileEntryType ");
		sb.append("INNER JOIN DDMStructureLink on ");
		sb.append("DDMStructureLink.structureId = ");
		sb.append("DLFileEntryType.dataDefinitionId AND ");
		sb.append("DDMStructureLink.classPK = DLFileEntryType.fileEntryTypeId");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"DELETE from DDMStructureLink where classPK = ? AND " +
						"structureId = ?"));
			ResultSet rs1 = ps1.executeQuery()) {

			while (rs1.next()) {
				ps2.setLong(1, rs1.getLong(1));
				ps2.setLong(2, rs1.getLong(2));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}