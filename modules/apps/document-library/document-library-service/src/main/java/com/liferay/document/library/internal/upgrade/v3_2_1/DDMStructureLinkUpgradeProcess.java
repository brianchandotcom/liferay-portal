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

package com.liferay.document.library.internal.upgrade.v3_2_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia García
 */
public class DDMStructureLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DLFileEntryType.fileEntryTypeId, ",
					"DLFileEntryType.dataDefinitionId from DLFileEntryType ",
					"inner join DDMStructureLink on ",
					"DDMStructureLink.structureId = ",
					"DLFileEntryType.dataDefinitionId and ",
					"DDMStructureLink.classPK = ",
					"DLFileEntryType.fileEntryTypeId"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from DDMStructureLink where classPK = ? and " +
						"structureId = ?");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			while (resultSet1.next()) {
				preparedStatement2.setLong(1, resultSet1.getLong(1));
				preparedStatement2.setLong(2, resultSet1.getLong(2));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}